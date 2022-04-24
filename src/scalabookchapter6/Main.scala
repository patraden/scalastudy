package scalabookchapter6

object Main extends App {
  import myclasses.Rational
  import scala.language.implicitConversions

  implicit def intoRational(x: Int): Rational = new Rational(x)

  val a = new Rational(1,2)
  val b = new Rational(66,42)
  val r = (5 / b).toString :: (a / b).toString :: (a * 2).toString :: a.toString :: b.toString :: Nil
  r.foreach(e => println(e))
}