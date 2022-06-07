package scalabookchapter10

object Main extends App {
  import myclasses.Element.elem
  import myclasses.Element

  // parametric fields can be passed through class parameters:
  class Cat {
    val dangerous = false
  }
  class Tiger(
             override val dangerous: Boolean,
             private var age: Int
             ) extends Cat

  val ae = elem(Array("Hello", "world"))
  println(ae.width)
  println(ae.height)

  // Создание подтипов означает, что значение подкласса может быть использовано там, где требуется значение суперкласса
  val e: Element = elem(Array("Hello")) // this is normal because ArrayElement extends Element. Это и есть полифорфизм (подтипов)
  val e1: Element = elem(Array("Hello", "world"))
  val ae2: Element = elem("Hello")
  val e2: Element = ae2
  val e3: Element = elem('X', 2, 3)

  object Spiral {

    val space: Element = elem(" ")
    val corner: Element = elem("+")

    def spiral(nEdges: Int, direction: Int): Element = {
      if (nEdges == 1)
        elem("+")
      else {
        val sp = spiral(nEdges - 1, (direction + 3) % 4)
        def verticalBar = elem('|', 1, sp.height)
        def horizontalBar = elem('-', sp.width, 1)
        if (direction == 0)
          (corner beside horizontalBar) above (sp beside space)
        else if (direction == 1)
          (sp above space) beside (corner above verticalBar)
        else if (direction == 2)
          (space beside sp) above (horizontalBar beside corner)
        else
          (verticalBar above corner) beside (space above sp)
      }
    }

  }

  def invokeDemo (e: Element): Unit = {
    e.demo()
  }

  invokeDemo(e1)
  invokeDemo(ae2)
  invokeDemo(e3)

  val nSides = args(0).toInt
  println(Spiral.spiral(nSides, 0))

}