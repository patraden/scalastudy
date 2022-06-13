package myclasses

import Element.elem

sealed abstract class Expr
case class Var(name: String) extends Expr
case class Number(num: Double) extends Expr
case class UnOp(operator: String, arg: Expr) extends Expr
case class BinOp(operator: String, left: Expr, right: Expr) extends Expr

class ExprFormatter {
  /**
   * Contains operators with inclining level of priority.
   */
  private val opGroups =
    Array(
      Set("|", "||"),
      Set("&", "&&"),
      Set("^"),
      Set("==", "!="),
      Set("<", "<=", ">", ">="),
      Set("+", "-"),
      Set("*", "%")
    )

  /**
   * A Map of operators to their level of priority.
   */
  private val precedence = {
    val assoc = {
      for {
        i <- opGroups.indices
        op <- opGroups(i)
      } yield op -> i
    }
    assoc.toMap
  }

  private val unaryPrecendence = opGroups.length // priority of unary operator is higher than binary
  private val fractionPrecendence = -1 // special priority as we use division as special operator in fraction markdown

  private def format(e: Expr, enclPrec: Int): Element =
    e match {

      case Var(name) => elem(name)

      case Number(num) =>
        def stripDot(s: String): String =
          if (s.endsWith(".0")) s.substring(0, s.length - 2)
          else s
        elem(stripDot(num.toString))

      case UnOp(op, arg) =>
        elem(op) beside format(arg, unaryPrecendence)

      case BinOp("/", left, right) =>
        val top = format(left, fractionPrecendence)
        val bot = format(right, fractionPrecendence)
        val line = elem('-', top.width max bot.width, 1)
        val frac = top above line above bot
        if (enclPrec != fractionPrecendence) frac
        else elem(" ") beside frac beside elem(" ")

      case BinOp(op, left, right) =>
        val opPrec = precedence(op)
        val l = format(left, opPrec)
        val r = format(right, opPrec + 1)
        val oper = l beside elem(" " + op + " ") beside r
        if (enclPrec <= opPrec) oper
        else elem("(") beside oper beside elem(")")
    }

  def format(e: Expr): Element = format(e, 0)

}
