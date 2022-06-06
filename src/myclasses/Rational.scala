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
  // any code directly placed into class body will be added to constructor
  Predef.require(d != 0)

  private val g = gcd(n.abs, d.abs)
  val numer: Int = n / g
  val denom: Int = d / g

  override def toString: String =
    s"""|$numer/$denom
        |""".stripMargin

  // additional constructor for natural numbers
  // in scala any additional constructor must call another constructor as its first action
  // in scala super class constructor can be called only in primary constructor
  def this(n: Int) = this(n, 1)

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

  // this - is a self-link
  def lessThan(that: Rational): Boolean =
    this.numer * that.denom < that.numer * this.denom

  // self-link for this method is mandatory
  def max(that: Rational): Rational =
    if (lessThan(that)) that else this

  @tailrec
  private def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)
}