package scalabookchapter7

object Main extends App {
  val filesHere = new java.io.File(".").listFiles

  // file listing in current directory
  for (file <- filesHere) println(file)

  // filtering files with .md extension
  println("---")
  for (file <- filesHere if file.getName.endsWith(".md")) println(file)

  // another way
  println("---")
  for (file <- filesHere) if (file.getName.endsWith(".md")) println(file)
  println("---")

  // several filters with if
  for (
    file <- filesHere
    if file.isFile
    if file.getName.endsWith(".md")
  ) println(file)

  // nested example
  println("---")
  def fileLines(file: java.io.File) = {
    scala.io.Source.fromFile(file).getLines().toArray
  }

  def grep(pattern: String): Unit =
    for {
      file <- filesHere
      if file.getName.endsWith(".scala");
      line <- fileLines(file)
      trimmed = line.trim // свзная переменная, val не требуется
      if trimmed.matches(pattern)
    } println(s"$file:  $trimmed") //grep(".*gcd.*")

  // creating new collections with yield
  def scalaFiles =
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
  val n = 4
  val half =
    if (n % 2 == 0)
      n / 2
    else
      throw new RuntimeException("n must be even number")

  import java.io.FileReader
  import java.io.FileNotFoundException
  import java.io.IOException
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

  import java.net.URL
  import java.net.MalformedURLException

  def urlFor(path: String) =
    try {
      new URL(path)
    } catch {
      case e: MalformedURLException => new URL("https://www.scala-lang.org/")
    }

  println(urlFor("http://google.com"))

  // important example for understanding the returned values
  // def f(): Int = try return 1 finally return 2 // 2
  // def g(): Int = try return 1 finally 2 // 1

  // match construct for "switch" like usage
  val firstArg = if (args.length > 0) args(0) else ""
  val friend =
    firstArg match {
      case "salt" => "pepper"
      case "chips" => "salsa"
      case "eggs" => "bacon"
      case _ => "huh?"
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