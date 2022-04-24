package scalabookchapter2

object Main extends App {
  def max(x: Int, y: Int): Int = if (x > y) x else y
  def greet(): Unit = println("_")

  println(max(4,5))
}
