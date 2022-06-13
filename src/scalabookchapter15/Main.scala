package scalabookchapter15

import myclasses.{Expr, Number, Var, UnOp, BinOp}

object Main extends App {

  /**
   * Case classes are special classes with some syntax sugar.
   * But the biggest advantage is that they allow the pattern matching.
   * (1) First syntax advantage is that the fabric method for creation of an instance with name of the class.
   * (2) Secondly, all arguments will automatically become (val) fields.
   * (3) Third advantage is that compiler automatically adds "natural" realization of "toString", "hashCode", "equals" methods.
   * Given method "==" always refers to "equals" all instances of a case class will automatically be comparable.
   * (4) And finally, compiler automatically adds "copy" method to instantiate a new object.
   * Copy uses by name and default parameters to create a copy with few modifications (like more fields etc).
   * Specifically by name parameter allows us to defined required modifications.
   * (5) But the biggest advantage is that they support pattern matching! (recursively)
   */

  val v = Var("x") // constructor is available by default
  val op = BinOp("+", Number(1), v)
  println(v.name)
  println(op.left)
  println(op.copy(operator = "-"))

  /**
   * Pattern matching syntax:
   * <selection> match { alternatives }
   * alternatives matched consequently.
   * In case of no matches it throws [[MatchError]]
   * Patterns have the following types:
   * 1. Substitution pattern (_)
   * 2. Constant patterns
   * 3. Variable patterns
   * 4. Patterns constructors (the most powerful type!)
   * 5. Pattern sequences
   * 6. Tuple patterns
   * 7. Typed patterns
   */

  def simplifyTop(expr: Expr): Expr = expr match {
    case UnOp("-", UnOp("-", e)) => e // double negation
    case BinOp("+", e, Number(0)) => e // adding zero
    case BinOp("*", e, Number(1)) => e // multiply by one
    case _ => expr // Substitution pattern (_)
  }
  println(simplifyTop(UnOp("-", UnOp("-", Var("x")))))

  // Substitution pattern (_)
  def isBinOp(expr: Expr): Boolean = expr match {
    case BinOp(_, _, _) => true
    case _ => false
  }

  // Constant patterns
  def describe(x: Any): String = x match {
    case 5 => "five"
    case true => "true"
    case "hello" => "hello!"
    case Nil => "Nil list"
    case _ => "something else"
  }

  println(describe(5))
  println(describe(true))
  println(describe("hello"))
  println(describe(Nil))
  println(describe(List(1,2,3)))

  // Variable patterns
  def isZero(expr: Any) = expr match {
    case 0 => "zero"
    case somethingElse => "not zero: " + somethingElse
  }

  /**
   * Compile differs constants and variables by names.
   * Thus if name start with lower case char it is considered as variable (see example).
   * However if required there are several ways to match constant by lower case char.
   * 1. If constant is a field of an object we can match it as this.pi, obj.pi
   * 2. Alternatively to quote it into `pi`
   */

  import math.{E, Pi}

  println(
    E match {
      case Pi => "math incident? Pi = " + Pi
      case _ => "OK"
    }
  )

  val pi = math.Pi
  println(
    E match {
      case pi => "math incident? Pi = " + pi
      case _ => "OK"
    }
  )

  println(
    E match {
      case `pi` => "math incident? Pi = " + pi
      case _ => "OK"
    }
  )
  // Patterns constructors (Single line of code but 3 levels of pattern matching validated)
  def deepMatch(expr: Expr): Unit = expr match {
    case BinOp("+", e, Number(0)) => println("deep pattern matching")
    case _ =>
  }

  // Pattern sequences
  def startWithZero(expr: Any): Unit = expr match {
    case List(0, _*) => println("match found!") // List first element is 0 and its length is arbitrary
    case _ =>
  }

  // Tuple patterns
  def tupleDemo(expr: Any): Unit = expr match {
    case (a, b, c) => println("matches " + a + b + c)
    case _ =>
  }
  println(tupleDemo(("a ", 3, "-tuple")))

  // Typed patterns

  def generalSize(x: Any): Int = x match {
    case s: String => s.length
    case m: Map[_, _] => m.size
    case _ => -1
  }

  println(generalSize("abc"))
  println(generalSize(Map( 1 -> 'a', 2 -> 'b')))
  println(generalSize(math.Pi))

  /**
   * Equivalent but more wordy way to match and convert types is the following operators:
   * _.isInstanceOf[T]
   * _.asInstanceOf[T]
   * Both of them are predefined for class Any
   */

  // alternative way to define generalSize:
  def generalSizeA(x: Any): Int =
    if (x.isInstanceOf[String]) {
      val s = x.asInstanceOf[String]
      s.length
    } else {
      if (x.isInstanceOf[Map[Any, Any]]) {
        val m = x.asInstanceOf[Map[Any, Any]]
        m.size
      } else {
        -1
      }
    }
  println(generalSizeA("abc"))
  println(generalSizeA(Map( 1 -> 'a', 2 -> 'b')))
  println(generalSizeA(math.Pi))

  /**
   * Type override, system cannot recognize type of general type arguments.
   * Thus below code will compile with warning and it will not be able
   * to recognize type arguments.
   * There is only one exception though which is Array.
   * Because in Java as well as in Scala they store values together with value type.
   */
  def isIntIntMap(x: Any) = x match {
    case m: Map[Int, Int] => true
    case _ => false
  }
  println(isIntIntMap(Map(1 -> 1))) // true
  println(isIntIntMap(Map("abc" -> "abc"))) // true

  def isStringArray(x: Any) = x match {
    case a: Array[String] => "yes"
    case _ => "no"
  }
  val as = Array("abc")
  val ai = Array(1, 2, 3)
  println(isStringArray(as))
  println(isStringArray(ai))

  /**
   * Linkage of variable in patterns.
   * Except overall pattern variables, we can link variable name to any other pattern by adding @.
   */
//  expr match {
//    case UnOp("abc", e @ UnOp("abc", _)) => e
//  }

  // Sometimes pattern matching might not be precise enough.
  // Example with expressions when we need two operands to be summed.
  // There are two patterns for that:
  // 1. BinOp("+", Var("x"), Var("x")) // e + e
  // 2. BinOp("*", Var("x"), Number(2) // e * 2
  // Thus below coe will not compile as pattern variables must be linear and
  // pattern variables might present only once in an expression.
  // For this case there is a "barrier" in the form of "if"
  // expression which uses pattern variables.

//def simplifyAdd(e: Expr) = e match {
//  case BinOp("+", x, x) => BinOp("*", x, Number(2))
//  case _ => e
//}

  def simplifyAdd(e: Expr) = e match {
  case BinOp("+", x, y) if x ==y => {
    BinOp("*", x, Number(2))
  }
  case _ => e
}

  /**
   * Below version in comparison to simplifyTop
   * will apply simplifications in any part of the expression.
   * @param expr
   * @return
   */
  def simplifyAll(expr: Expr): Expr = expr match {
    case UnOp("-", UnOp("-", e)) => simplifyAll(e)
    case BinOp("+", e, Number(0)) => simplifyAll(e)
    case BinOp("*", e, Number(1)) => simplifyAll(e)
    case UnOp(op, e) => UnOp(op, simplifyAll(e))
    case BinOp(op, l, r) => BinOp(op, simplifyAll(l), simplifyAll(r))
    case _ => expr
  }

  /**
   * Sealed classes cannot have any subclasses except those placed to the same file with sealed Class.
   * This is quit useful when working with pattern matching as it helps us to ensure all patterns have been counted.
   * Compiler will automatically notify you about missed patterns.
   * Thus if you specifically create class hierarchy which should be pattern matched
   * then you need to consider "sealing" for its super Class.
   */

  // Example where some patterns have been missed.
  // However we notify compiler to ignore full pattern check.

  def describe(e: Expr): String = (e: @unchecked) match {
    case Number(_) => "number"
    case Var(_) => "variable"
  } // compiler will warn you

  /**
   * Type Option have two value types:
   * 1. Some(x), where x is a true value
   * 2. None object which represents a missing value
   * Some Scala collection methods, like get return Some(x) or None.
   * The most known way to get values from Option is to use pattern matching.
   */

  val capitals = Map("France" -> "Paris", "Japan" -> "Tokyo")
  println(capitals get "France") // Some(Paris)
  println(capitals get "North Pole") // None

  def show(x: Option[String]) = x match {
    case Some(s) => println(s)
    case None => println("?")
  }
  show(capitals get "France")
  show(capitals get "North Pole")

  /**
   * Patterns present in many Scala constructions.
   * 1. In definitions of variables (especially useful with case classes)
   * 2. "Sequence of alternatives" can be used everywhere where we use functional literal (in fact it is a functional literal)
   * "Sequence of alternatives" gives you a partially applied function, this is important to remember.
   * To handle potential problems with undefined scope we better defined it as PartialFunction.
   * PartialFunction has method "isDefinedAt" which can be used as test to ensure that function is
   * indeed defined for a particular input.
   * This is very useful in akka Actors library as it helps to define "receive" method.
   * "receive" also gets defined as PartialFunction (akka framework take care of that)
   * akka makes tests with isDefinedAt everytime it applies this function.
   * 3. Patterns used in "for" expressions
   */

    // variable definitions
  val myTuple = (123, "abc")
  val (number, string) = myTuple
  val exp = new BinOp("*", Number(5), Number(1))
  val BinOp(operator, left, right) = exp

  // Sequence of alternatives
  val withDefault: Option[Int] => Int = {
    case Some(x) => x
    case None => 0
  }
  println(withDefault(Some(10)))
  println(withDefault(None))

  // Akka Actors code snippet
//  var sum = 0
//  def receive = {
//    case Data(Byte) => sum += byte
//    case GetCheckSum(requester) =>
//      val checksum = ~(sum & 0xFF) + 1
//      requester ! checksum
//  }

  val second: List[Int] => Int = {
    case x :: y :: _ => y
  } // compile will give a warning

  val secondPartial: PartialFunction[List[Int], Int] = {
    case x :: y :: _ => y
  } // compile will give a warning

  println(secondPartial.isDefinedAt(List(5,6,7)))
  println(secondPartial.isDefinedAt(List()))

  for ((country, city) <- capitals)
    println("Capital of " + country + " is " + city)

  val results = List(Some("apple"), None, Some("orange"))
  // if value does not correspond to a pattern it gets skipped.
  for (Some(fruit) <- results) println(fruit)




}