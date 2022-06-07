package scalabookchapter11

object Main extends App {

  // Any class (super class of all classes) has the following methods:
  // final def == (that: Any): Boolean
  // final def != (that: Any): Boolean
  // def equals(that: Any): Boolean
  // def ##: Int
  // def hashCode: Int
  // def toString: String
  // There are two subclasses of Any: AnyVal and AnyRef

  // AnyVal - class of values (built in value classes: Byte, Short, Char, Int, Long, Float, Double, Boolean, Unit)
  // All built-in classes except Unit correspond to relevant Java primitives
  // All instances of these classes represented as literals
  // It is impossible to create new instance using key word "new"
  // Because all these classes are defined as abstract and final simultaneously
  // new Int // returns:  class Int is abstract; cannot be instantiated
  // Unit is loosely connected with Java's Void
  // Unit has single value ()

  // scala.AnyVal space is flat (all classes are direct subclasses of AnyVal)
  // All conversions are preformed through implicit conversions
  // Examples:

  42 max 43
  42 min 43
  1 until 5
  1 to 5
  3.abs
  (-3).abs

  // min, max, until, to, abs defined in scala.runtime.RichInt
  // And there is an implicit conversion between Int and RichInt
  // make import scala.runtime.RichInt to learn more
  // Similar there are same enhancement classes for all built-in value classes

  // AnyRef - base class for all referential classes.
  // AnyRef is more or less an alias for java.lang.Object
  // Thus any Classes written in Scala or Java are subclasses of AnyRef

  // How primitives are realized?
  // Integers in Scala stored as in Java as 32-bit words which is required for effectiveness of JVM
  // as well as overall Scala-Java compatibility
  // However Scala uses duplicate class java.lang.Integer when int value should look like Java object.
  // java.lang.Integer is a reference type in Java, while int is a primitive.
  // This is Java code which return false
  // boolean isEqual(int x, int y) {
  //   return x == y
  //}
  // System.out.println(isEqual(421, 421))
  // For example, when we assign int to val of type Any, or use method toString
  // This helps to properly imitate method for class instance equality
  // In Java method == validate the equality of references, while in scala it works
  // towards representation of a Class

  // This is Java code which return true
  // boolean isEqual(int x, int y) {
  //   return x == y
  //}
  // System.out.println(isEqual(421, 421))

  // This is Java code which return false
  // boolean isEqual(Integer x, Integer y) {
  //   return x == y
  //}
  // System.out.println(isEqual(421, 421))

  // Same code in Scala:
//  def isEqual(x: Int, y: Int) = x == y
//  println(isEqual(421, 421))
  def isEqual(x: Any, y: Any) = x == y
  println(isEqual(421, 421))

  val m = "abcd".substring(2)
  val n = "abcd".substring(2)
  println(m == n) // this code returns false in java because different references are created

  // Sometimes it might be required to check references anyway.
  // For these case there method eq and ne defined in clas AnyRef
  // This method behaves similar to == in Java reference classes

  val x = new String("abc")
  val y = new String("abc")
  println(x == y)
  println(x eq y)
  println(x ne y)

  // Nothing and Null
  // Null - type of null reference, it represents a subclass of any AnyRef subclass
  // Null is not compatible to AnyVal
//  val i: Int = null // will not compile

  // Nothing is a subclass of Any class subclass
  // Nothing is used specifically for error cases
  def error(message: String): Nothing =
    throw new RuntimeException(message)
  // For example below code works because Nothing is subclass of Int
  def divide(x: Int, y: Int): Int =
    if (y != 0) x / y
    else sys.error("division by zero!")

  // In Additional to built-in AnyVal classes we can define our own.
  // The instances of your val classes will compile directly into bytecode which will not use class shell.
  // There are several important requirements for val classes:
  // 1. There should be only 1 parameter (value)
  // 2. There should be only "def" definitions
  // 3. It cannot be extended by any other classes
  // 4. methods equals and hashCode cannot be re-defined
  // Examples:

  class Dollar(val amount: Int) extends AnyVal {
    override def toString() = "$" + amount
  }
  val money = new Dollar(1000000)
  println(money)
  println(money.amount)

  // example with html
  class Anchor(val value: String) extends AnyVal
  class Style(val value: String) extends AnyVal
  class Text(val value: String) extends AnyVal
  class Html(val value: String) extends AnyVal

  def title(text: Text, anchor: Anchor, style: Style): Html =
    new Html(
      s"""
         |<a id='${anchor.value}'>
         |<h1 class='${style.value}'>
         |${text.value}
         |""".stripMargin
    )
  println(title(new Text("Value Classes"), new Anchor("chap.vcsl"), new Style("bold")))
}