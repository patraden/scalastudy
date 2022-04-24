package scalabookchapter4

import myclasses.CheckSumAccumulator.calculate

object Main extends App {

  println(calculate("hello, world!"))
  println(calculate("hello, world, this is Scala!"))

}
