package scalabookchapter8

import java.util.concurrent.CountDownLatch
import scala.annotation.tailrec

object Main extends App {
  import myclasses.LongLines
  val width = args(0).toInt
  for (arg <- args.drop(1))
    LongLines.processFile(arg, width)

  // функциональные литералы и функциональные значения

  // var literal increase
  var increase = (x: Int) => x + 1
  println(increase(10))

  // mutated literal increase
  increase = (x: Int) => x + 9999
  println(increase(10))

  // complex literal with more than 1 line of body in {}
  increase = (x: Int) =>  {
    println("We")
    println("are")
    println("Here!")
    x + 1 // last line gives the result
  }
  println(increase(10))

  val someNumbers = List(-11, -10, -5, 0, 5, 10)
  // someNumbers.foreach((x: Int) => println(x)) // full notation
  // someNumbers.foreach(println) // simple notation
  // someNumbers.filter( x => x > 0).foreach(println) // simple notation
  someNumbers.filter( _ > 0).foreach(println _) // simple notation

  val f = (_ : Int) + (_ : Int)
  println(f(10,14))

  // частично применненая функция и передача набора аргументов
  def sum(a: Int, b: Int, c: Int): Int = a + b + c
  println(sum(1,2,3))
  val a = sum _ // generates function3 class with method apply which takes all 3 arguments and make a call to sum function
  // println(a(1,2,5)) // use function3 class apply method to create an instance
  println(a.apply(1,2,5)) // use function3 class apply method to create an instance

  val b = sum(1, _: Int, 4) // now we create a new class function1 which has 1 parameter
  println(b.apply(7))


  println("--- замыкания ---")
  /*
  * замыкания и свободные переменные (отсылка на lambda исчисление)
  * замыкание способно "видеть" изменения свободной переменной
  * то же самое справедливо и в обратном направлении
   */

  var more = 1 // free variable
  val addMore = (_: Int) + more // свободная переменная в открытом терме замыкается на свободную переменную more
  println(addMore(10))

  more = 110 // free variable
  println(addMore(10))

  // сумма меняется в обходе, но тем не менее изменения остаются в области видимости замыкания x => x + s
  var s = 0
  someNumbers.foreach(s += _)
  println(s)

  // что если переменная ссылается на функцию, которая вызывается несколько раз
  // замыкание происходит на значение свободной переменной активной в момент создания
  def makeIncreaser(more: Int) = (x: Int) => x + more // при каждом вызове данныя функция будет создавать новое замыкание и каждое замыкание будет обращаться к переменной more
  val inc1 = makeIncreaser(1)
  val inc9999 = makeIncreaser(9999)
  println(inc1(10))
  println(inc9999(10))

  /*
  * repeatable parameters
  * inside repetable parameters is a Seq type, e.g. String* = Seq[String]
  * to call list of parameters from random seq you call it with _*
  * name arguments, can name, can ignore name, can do any order with names, can mix - positional first, names second
  * default value for arg
  */

  println("--- function variations ---")
  def echo(args: String*): Unit = for (arg <- args) println(arg)
  println(echo())
  println(echo("One"))
  println(echo("Hello", "World"))

  val par = Seq("What's ", "up ", "Doc?")
  println(echo(par: _*))

  def speed(distance: Float, time: Float): Float =  distance / time
  println(speed(100, 10))
  println(speed(distance = 100, time = 10))
  println(speed(time = 10, distance = 100))

  def printTime(out: java.io.PrintStream = Console.out, divisor: Int = 1): Unit =
    out.println("time = " + System.currentTimeMillis() / divisor)
  printTime()
  printTime(out = Console.err)
  printTime(divisor = 1000)

  /*
  * reccursion and tail reccursion
  * functions which call itself as last action in their body are called tailed-reccursive
  * new stack frame is not created for tailed-reccursive function. The previous one is used
  * scala/scalac option -g:notailcalls can help better read traces
  * usage of tailed reccursive is quit limited
  */

  println("--- recursion ---")

  //functional style
  /*
  def approximate(guess: Double): Double =
      if (isGoodEnough(guess)) guess
      else approximate(improve(guess)) //reccursive call is at the end!!!

  // imperative style
  def approximateLoop(initialGuess: Double): Double = {
      var guess = initialGuess
      while (!isGoodEnough(guess))
          guess = improve(guess)
      guess
  }
  */

  // tail reccursion will not be applied
  def boom(x: Int): Int =
    if (x == 0) throw new Exception("boom!")
    else boom(x - 1) + 1
  //println(boom(4))

  // tail reccursion will be applied
  @tailrec
  def bang(x: Int): Int =
    if (x == 0) throw new Exception("boom!")
    else bang(x - 1)
  println(bang(4))

  // in this example tail-reccursion optimization is not possible
  def isEven(x: Int): Boolean =
    if (x == 0) true else isOdd(x - 1)
  def isOdd(x: Int): Boolean =
    if (x == 0) true else isEven(x - 1)

  // same applies to calls to functional values. example below will not work!
  val funValue = nestedFun _
  def nestedFun(x: Int) : Unit = {
    if (x != 0) { println(x); funValue(x - 1) }
  }
}