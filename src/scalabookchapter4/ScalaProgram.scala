package scalabookchapter4

import myclasses.CheckSumAccumulator.calculate

object ScalaProgram {

  def main(args: Array[String]): Unit = {
    for (arg <- args)
      println(arg + ": " + calculate(arg))
  }
}
