package myclasses

import scala.annotation.tailrec

/*
* (n, d) are class parameters
* if class has not body then {} can be skipped
* compiler takes class parameters (n, d) and will create initial class constructor with same parameters
* compiler will compile any code in class body, thus we can add some debug code where required
* override before method definition shows that previous method is re-defined
* require - is the pre-condition for constructors and methods which helps to guarantee encapsulated data consistency
*/

class Rational(n: Int, d: Int)
{
  Predef.require(d != 0)

  private val g = gcd(n.abs, d.abs)
  val numer: Int = n / g
  val denom: Int = d / g

  override def toString: String = s"""|$numer/$denom""".stripMargin // override def toString: String = s"""|$n/$d""".stripMargin

  def this(n: Int) = this(n, 1) // aux contructor for natural numbers

  def add(that: Rational): Rational =
    new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )

  def + (that:Rational): Rational =
    this.add(that)

  def + (i: Int): Rational =
    new Rational(this.numer + i * this.denom, this.denom)

  def substract(that: Rational): Rational =
    new Rational(
      numer * that.denom - that.numer * denom,
      denom * that.denom
    )

  def - (that:Rational): Rational =
    this.substract(that)

  def - (i: Int): Rational =
    new Rational(this.numer - i * this.denom, this.denom)

  def multiply(that: Rational): Rational =
    new Rational(
      this.numer * that.numer,
      this.denom * that.denom
    )

  def * (that:Rational): Rational =
    this.multiply(that)

  def * (i: Int): Rational =
    new Rational(this.numer * i, this.denom)

  def divide(that: Rational): Rational =
    new Rational(
      this.numer * that.denom,
      this.denom * that.numer
    )

  def / (that:Rational): Rational =
    this.divide(that)

  def / (i: Int): Rational =
    new Rational(this.numer, this.denom * i)

  def lessThan(that: Rational): Boolean =
    this.numer * that.denom < that.numer * this.denom

  def max(that: Rational): Rational =
    if (this.lessThan(that)) that else this

  @tailrec
  private def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)
}