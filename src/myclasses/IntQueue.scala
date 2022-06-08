package myclasses

import scala.collection.mutable.ArrayBuffer

abstract class IntQueue {
  def get(): Int
  def put(x: Int): Unit
}

class BasicIntQueue extends IntQueue {
  private val buf = new ArrayBuffer[Int]
  def get(): Int = buf.remove(0)
  def put(x: Int): Unit = {buf += x}
}

/**
 * Interestingly, we call here an abstract method from abstract super class.
 * For normal class this would cause an exception, because during the runtime execution they would definitely cause runtime error.
 * While in trait such calls might be executed successfully as super call is dynamically referenced and will start
 * working only when trait is mixed into class where this method has concrete implementation.
 * Also it is highly recommended to let compiler know about such methods by placing:
 * "abstract override" clause which is allowed in traits only.
 */

trait Doubling extends IntQueue {
  abstract override def put(x: Int): Unit = { super.put(2 * x) }
}

trait Incrementing extends IntQueue {
  abstract override def put(x: Int): Unit = { super.put(x + 1) }
}

trait Filtering extends IntQueue {
  abstract override def put(x: Int): Unit = {
    if (x >= 0) super.put(x)
  }
}