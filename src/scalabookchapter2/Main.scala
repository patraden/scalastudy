package scalabookchapter2

object Main extends App {

  def max(x: Int, y: Int): Int = if (x > y) x else y
  def greet(): Unit = println("_")

  println(max(4,5))

  // imperative programming style
  var i: Int = 0
  while (i < args.length) {
    println(args(i))
    i += 1
  }

  // functional style
  //  args.foreach((arg: String) => println(arg))
  //  args.foreach(arg => println(arg))
  //  args.foreach(println)
  // <- is read is arg "in" args
  //  for (arg <- args) println(arg)
}
