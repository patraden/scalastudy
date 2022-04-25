package scalabookchapter4

import myclasses.CheckSumAccumulator.calculate

object Main extends App {

  // when ";" is required:
  // two or more instructions are at the same line
  val s = "Hello"; println(s)
  // end of line considered as ";" unless on of the following conditions is true:
  // 1. Line ends with an element which cannot be placed at the end of the line, e.g. "." or infix operation
  // 2. Next line starts with an element which cannot be a start of an instruction
  // 3. Line ends within (...) or [...] parentheses which suspect no multiple instructions in its body


  println(calculate("hello, world!"))
  println(calculate("hello, world, this is Scala!"))

}
