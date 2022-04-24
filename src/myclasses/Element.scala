package myclasses
import Element.elem

abstract class Element {
  // the direction "abstracts" helps to define abstract method without implementation
  // below method is an abstract method
  // it would not be possible to create an instance of abstract class using "new"
  def contents: Array[String]
  // we do not define method with () as it does not have side effects
  // alternatively we could define these methods as class fields and it would be faster, but it will consume memory
  // overall rule: if function gives access to some class resource then no (), else if it modifies something then ()
  def height: Int = contents.length
  def width: Int = if (height == 0) 0 else contents(0).length
  def demo(): Unit = {
    println("calling method implemented for class Element...")
  }

  def above (that: Element): Element = {
    val this1 = this widen that.width
    val that1 = that widen this.width
    elem(this1.contents ++ that1.contents)
  }

  def beside(that: Element): Element = {
    val this1 = this heighten that.height
    val that1 = that heighten this.height
    elem (
      for (
        (line1, line2) <- this1.contents zip that1.contents
      ) yield line1 + line2
    )
  }

  def widen(w: Int): Element =
    if (w <= width) this
    else {
      val left = elem(' ', (w - width) / 2, height)
      val right = elem(' ', w - width - left.width, height)
      left beside this beside right
    }

  def heighten(h: Int): Element =
    if (h <= height) this
    else {
      val top = elem(' ', width, (h - height) / 2)
      val bot = elem(' ', width, h - height - top.height)
      top above this above bot
    }

  override def toString: String = contents mkString "\n"
}

// создадим фабричный объект, который будет содержать методы, конструирующие другие классы нашей библиотеки
// формально мы можем тоже самое делать и через определения класса фабрики, но создание объекта-компаньна есть самый простой способ

object Element {

  def elem(contents: Array[String]): Element =
    new ArrayElement(contents)

  def elem(chr: Char, width: Int, height: Int): Element =
    new UniformElement(chr, width, height)

  def elem(line: String): Element =
    new LineElement(line)

  // extends forces class ArrayElement to inherit all public elements of class Element
  // ArrayElements - subtype of Element
  // Element class - super class of ArrayElements

  // class ArrayElement(conts: Array[String]) extends Element { // original definition without parametric field
  private class ArrayElement(
                              val contents: Array[String]
                            ) extends Element { // with parametric field
    // def contents: Array[String] = conts
    // val contents: Array[String] = conts // alternative way to define contents as a field
    // Java has 4 namespaces for filed, methods, types and packages. but scala has 2: Values (fields, methods, packages and objects) and Types (class names and treats)
    override def demo(): Unit = {
      println("calling method implemented for class ArrayElement...")
    }
  }

  private class LineElement(s: String) extends Element {
    val contents: Array[String] = Array(s)
    override def width: Int = s.length
    override def height = 1
    final override def demo(): Unit = { // modifier final is used to block further extension of this method
      println("calling method implemented for class LineElement...")
    }
  }

  private class UniformElement (
                                 ch: Char,
                                 override val width: Int,
                                 override val height: Int,
                               ) extends Element {
    private val line = ch.toString * width
    def contents: Array[String] = Array.fill(height)(line)
  }
}