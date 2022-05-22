package scalabookchapter7

import java.io.File
import java.io.FileReader
import java.io.FileNotFoundException
import java.io.IOException
import scala.io.StdIn.readLine
import java.net.URL
import java.net.MalformedURLException

object Main extends App {

  // alaways try to use val variables!
  val filename: String =
    if (!args.isEmpty) args(0)
    else "default.txt"


  // while loops
  // gcd algo in imperative style
  def gcdLoop(x: Long, y: Long): Long = {
    var a = x
    var b = y
    while (a != 0) {
      val temp = a
      a = b % a
      b = temp
    }
    b
  }

  // scala also have do-while loops
  var line = ""
  do {
    line = readLine()
    println("Read: " + line)
  } while (line != "")

  // as result while loops return a value of type Unit.
  // in fact there is only 1 value of type Unit named ()

  def greet(): Unit = { println("hi") }
  val whatAmI: Unit = greet() // whatAmI will have a value of () of type Unit

  // another construction that returns Unit is assignment of var variables
  // below code will not work as comparison of Unit and String will always return true:
  // in Java for example, this assignment would work because it returns the type of assignee
  //  while ((line = readLine()) != "")
  //    println("Read :" + line)

  // finally in scala we try to avoid using loops inline with functional style
  def gcd(x: Long, y: Long): Long =
    if (y == 0) x else gcd(y, x % y)


  // for loops
  val filesHere = new java.io.File("./src/scalabookchapter7").listFiles

  // file listing in current directory
  for (file <- filesHere) println(file)

  // ranges are quite convenient
  for (i <- 1 to 4)
    println("Iteration " + i)

  for (i <- 1 until 4)
    println("Iteration " + i)

  // filtering files with .scala extension
  println("--File Filtering--")
  for (file <- filesHere if file.getName.endsWith(".scala"))
    println(file)

  // another way
  println("--File Filtering--")
  for (file <- filesHere)
    if (file.getName.endsWith(".scala"))
      println(file)

  // several filters with if
  println("--File Filtering--")
  for (
    file <- filesHere
    if file.isFile
    if file.getName.endsWith(".scala")
  ) println(file)

  // nested example
  def fileLines(file: java.io.File) = {
    scala.io.Source.fromFile(file).getLines().toArray
  }

  println("--Nested for--")
  def grep(pattern: String): Unit =
    for {
      file <- filesHere
      if file.getName.endsWith(".scala")
      line <- fileLines(file)
      trimmed = line.trim // свзная переменная, val не требуется
      if trimmed.matches(pattern)
    } println(s"$file:  $trimmed") // grep(".*gcd.*")

  grep(".*gcd.*")

  // creating new collections with yield
  // type of the new collection is inherited from type of iterated collection
  def scalaFiles: Array[File] =
    for {
      file <- filesHere
      if file.getName.endsWith(".scala")
    } yield file

  // be carefully with yield syntax (for <condition> yield <body>), below example demonstrates type change
  val forLineLengths =
    for {
      file <- filesHere
      if file.getName.endsWith(".scala")
      line <- fileLines(file)
      trimmed = line.trim
      if trimmed.matches(".*for.*")
    } yield trimmed.length //forLineLengths.foreach(x => println(x))

  // exception handling
  // exceptions like in example below are taken the type of if body
  // as in case n is odd the exception gets generated before n gets value assigned
  // thus typing is absolutely safe here
  // However, technically generated exception has type Nothing

  val n = 4
  val half: Int =
    if (n % 2 == 0)
      n / 2
    else
      throw new RuntimeException("n must be even number")


  // catching exceptions

  try {
    val f = new FileReader("ScalaStudy.iml") // use and close file
  } catch {
    case ex: FileNotFoundException => println("file not found") // handling file not found exception
    case ex: IOException => println("IO exception")             // handling IO exception
  }

  // with finally
  val file = new FileReader("ScalaStudy.iml")
  try {
    println("file used")
  } finally {
    file.close() // file closed guaranteed
  }
  // try as any other lang construction returns some value
  // example below demonstrate URL parsing trough try

  def urlFor(path: String) =
    try {
      new URL(path)
    } catch {
      case e: MalformedURLException => new URL("https://www.scala-lang.org/")
    }

  println(urlFor("http://google.com"))

  // important example for understanding the returned values
  // thus it is important to avoid returning anything in finally clause
  // this is a clause preferred for guaranteed execution of some side effect like closing or wrapping objects used in try body

  // def f(): Int = try return 1 finally return 2 // 2
  // def g(): Int = try return 1 finally 2 // 1

  // match construct for "switch" like usage
  // the most important is that match also returns some value

  val firstArg = if (args.length > 0) args(0) else ""
  val friend =
    firstArg match {
      case "salt" => "pepper"
      case "chips" => "salsa"
      case "eggs" => "bacon"
      case _ => "huh?" // _ - подстановочное значение, используемое для неизвестного
    }
  println(friend)

  // functional style to return multiplication table
  // returns multiplication row as sequence
  def makeRowSeq(row: Int) =
    for (col <- 1 to 10) yield {
      val prod = (row * col).toString
      val padding = " " * (4 - prod.length)
      padding + prod
    }
  // returns lines as string
  def makeRow(row: Int) = makeRowSeq(row).mkString

  def multiTable() = {
    val tableSeq =
      for (row <- 1 to 10) yield makeRow(row)
    tableSeq.mkString("\n")
  }
  println(multiTable())
}