package myclasses

import scala.io.Source

object LongLines {

  // one way to use private function is to define a private method like below:
  /*
  def processFiles(filename: String, width: Int) = {
      val source = Source.fromFile(filename)
      for (line <- source.getLines())
          processLine(filename, width, line)
  }

  private def processLine(filename: String, width: Int, line: String) = {
      if (line.length > width)
          println(filename + ": " + line.trim)
  */

  // another way is to define aux function within another function

  def processFile(filename: String, width: Int): Unit = {

    def processLine(line: String): Unit = {
      if (line.length > width)
        println(filename + ": " + line.trim)
    }

    val source = Source.fromFile(filename)
    for (line <- source.getLines())
      processLine(line)
  }
}