package scalabookchapter9

import java.io.{File, PrintWriter}

object Main extends App {
  import myclasses.FileMatcher

  val filesEnding = FileMatcher.filesRegex(".*[bB]ook.*")
  for (file <- filesEnding) println(file)

  println("--- Carrying ---")
  /**
  * Каррирование так же как и в лямбда исчислении
  * превращает функцию нескольких аргументов в последовательностей применения лямбда-термов (функций одного аргумента)
  */

  // classic 2 args function definition
  def plainOldSum = (_: Int) + (_: Int)
  println(plainOldSum(1, 2))

  // carried function in scala
  def carriedSum(x: Int)(y: Int) = x + y
  println(carriedSum(1)(2))

  // first return lambda function closed on y with x as free variable
  // technically speaking first is a lambda function itself by x: first =(β equivalent)= λx.(λy.(x + y))
  def first(x: Int) = (y: Int) => x + y
  val second = first(1)
  println(first(1))
  println(second(2))

  // or we can use partial args substitution to explain the same
  val onePlus = carriedSum(1)_
  println(onePlus(2))

  def twice(op: Double => Double, x: Double) = op(op(x))
  println(twice(_ + 1, 6))

  // пример управляющей конструкции (шаблон) открытие-закрытие ресурса, широко применяемый в программировнии
  // приимуществом такого кода является то, что закрытие файла гарантируется самим методом, а не кодом пользователя
  // данная технология назывыется шаблоно временного пользования (loan pattern)
  // если сделать карринг для двух аргументов, то для каждого из них можно будет использовать {} при вызове

  def withPrinterWriter(file: File)(op: PrintWriter => Unit): Unit = {
      val writer = new PrintWriter(file)
      try {
          op(writer)
      } finally {
          writer.close()
      }
  }
  val file = new File("ScalaStudy/src/scalabookchapter9/Main.scala")
  withPrinterWriter(file) {
      writer => writer.println(new java.util.Date)
      }

  // named parameters
  // (by-name) parameters can simplify () => into => making code look more natural

  // val assertionEnabled = true
  def myAssert(predicate: () => Boolean): Unit =
    if (assertionEnabled && !predicate())
      throw new AssertionError


  def byNameAssert(predicate: => Boolean): Unit =
    if (assertionEnabled && !predicate)
      throw new AssertionError

  def boolAssert(predicate: Boolean): Unit =
    if (assertionEnabled && !predicate)
      throw new AssertionError

  val x = 5
  val assertionEnabled = false
  // this code will throw an exception
  // println(boolAssert(x / 0 == 0))
  // this code will NOT throw an exception
  println(byNameAssert(x / 0 == 0))
}