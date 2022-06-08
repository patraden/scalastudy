package scalabookchapter12

/**
 * Traits are fundamental repeatedly used code blocks.
 * One or more traits can be mixed into a class.
 * One class can be subclass of only one super class while
 * unlimited number of traits can be mixed up.
 */

object Main extends App {

  /**
   * This trait has not super class (by default it is AnyRef)
   * Now it can be mixed with any class either through "extends" or "with"
   */
  trait Philosophical {
    def philosophize(): Unit = {
      println("На меня тратится память, следовательно, я существую!")
    }
  }

  /**
   * Automatically this class became a subclass of AnyRef as we extended AnyRef through Philosophical.
   * Trait itself as type can be used to initialize variables.
   */

  class Frog extends Philosophical {
    override def toString = "зеленая"
  }

  val frog = new Frog
  frog.philosophize()

  val phil: Philosophical = frog
  phil.philosophize()

  /**
   * If we need to mix trait into a class which extends another one, we use "with".
   */

  class Animal
  trait HasTwoLegs

  class AnotherFrog extends Animal with Philosophical with HasTwoLegs{
    override def toString = "зеленая"
    override def philosophize(): Unit = {
      println(s"Мне живется нелегко, потому что я $toString !")
    }
  }

  val frg: Philosophical = new AnotherFrog
  frg.philosophize()

  /**
   * Traits are similar to Java interfaces with concrete methods,
   * And overall their capabilities are much broader to Java interfaces.
   * Traits are capable to keep fields and states.
   * In general everything applicable for class is applied to Trait with 2 exceptions:
   * 1. There are no parametrization for class constructor.
   * 2. calls of "super" in class have static reference while in Traits they are dynamic.
   * Thus if you call "super" in class you would always know certain realization of this method.
   * When you do it in Trait then specific realization of this method is not defined yet.
   * It will be available though on mix up with class (up on any mix up).
   * This allows Traits to work as increasing modifications.
   */

  /**
   * Example with rectangles
   */

  class Point(val x: Int, val y: Int)

  trait Rectangular {
    def topLeft: Point // abstract
    def bottomRight: Point // abstract

    def left: Int = topLeft.x
    def right: Int = bottomRight.x
    def width: Int = right - left
    // many other methods
  }

  abstract class Component extends Rectangular {
    // other methods
  }

  class Rectangle(val topLeft: Point, val bottomRight: Point)
    extends Rectangular {
    // other methods
  }

  val rect = new Rectangle(new Point(1,1), new Point(10,10))
  println(rect.left, rect. width)

  /**
   * Let's enhance our class Rational and mix standard trait Ordered[T]
   */

  import myclasses.Rational
  val half = new Rational(1, 2)
  val third = new Rational(1, 3)
  println(half < third)
  println(half > third)

  /**
   * Another application of trait (except thin and fat interfaces) is enhance/increase modifications.
   * Consider example of a FIFO queue of integers.
   */

  import myclasses.{BasicIntQueue, Doubling, Incrementing, Filtering}
  val queue = new BasicIntQueue
  queue.put(100)
  queue.put(200)
  println(queue.get())
  println(queue.get())

  class MyQueue extends BasicIntQueue with Doubling
  val myQueue = new MyQueue
  val myQueue2 = new BasicIntQueue with Doubling // it is possible to mix trait during instantiating
  myQueue.put(10)
  println(myQueue.get())

  /**
   * Let's enhance Class with trait modifications.
   * Поскольку trait примешивается к классу, его можно называть "примесью".
   */

  println("-" * 20 + " Trait modifications " + "-" * 20)
  val q = (new BasicIntQueue with Incrementing with Filtering)
  q.put(-1); q.put(0); q.put(1)
  println(q.get())
  println(q.get())

  val q2 = (new BasicIntQueue with Filtering with Incrementing)
  q2.put(-1); q2.put(0); q2.put(1)
  println(q2.get())
  println(q2.get())
  println(q2.get())

  /**
   * Above we considered so call "lineralization":
   * How it works:
   * Scala takes Class with with all its superclasses and traits and puts them into a linear order.
   * When we call "super" inside such class it calls a method in above order.
   * If "super" call presents in all traits and super classes then method gets modified.
   * let's consider example:
   */

  trait Furry extends Animal
  trait HasLegs extends Animal
  trait FourLegged extends HasLegs
  class Cat extends Animal with Furry with FourLegged

  /**
   * IMPORTANT: Through any lineralisation defined Class goes first!
   * linearisation for Cat is read right to left.
   * Thus linerasation for Animal goes last and looks like:
   * Animal -> AnyRef -> Any (as Animal extends AnyRef which extends Any by default)
   * linerasation for Furry goes before last:
   * Furry -> Animal (which expands into) -> AnyRef -> Any
   * When there goes linerasation for FourLegged:
   * FourLegged -> HasLegs -> Furry -> Animal -> AnyRef -> Any
   * And Finally first in linearisation is Cat:
   * Cat -> FourLegged -> HasLegs -> Furry -> Animal -> AnyRef -> Any
   */

  /**
   * Finally the question is: should we use abstract class or trait?
   * Three questions to answer:
   * 1. Behaviour will not be repeated? (yes -> Abstract Class)
   * 2. Will the behavior be repeated by irrelevant classes? (Yes -> Trait)
   * 3. Would you like to use the behaviour in Java code? (Yes -> Abstract Class)
   * 4. Do you plan to distribute code in compiled form? (Yes - Abstract Class)
   * 5. I have not decided yet... (Start with Trait)
   */



}
