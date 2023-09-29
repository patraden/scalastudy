package scalabookchapter5

object Literals extends App {

  // Byte 8-bit number in additional code -2**7 to 2**7-1
  // Short 16-bit number in additional code -2**15 to 2**15-1
  // Int 32-bit number in additional code -2**31 to 2**31-1

  // literal - a way to declare a constant value directly in code
  // int literals used in decimal and hex forms

  val a = -0x2
  val p = 0xCAFEBABEL
  val t = 35L
  val o = 31l
  println(p)
  println(t)
  println(o)

  // float number literals

  val big = 1.2345
  val bigger = 1.2345e1
  val biggerStill = 123E45
  val little = 1.2345F
  val littleBigger = 3e5f
  val anotherDouble = 3e5
  val yetAnother = 3e5D

  println(big, bigger, biggerStill)
  println(little, littleBigger, anotherDouble, yetAnother)

  // symbolic literal

  val la = 'A'
  val d = '\u0041'
  val f = '\u0044'
//  println(la, d, f)

  //special symbolic literal
  import collection.immutable.Map
  val map = Map(
    ("n", "\\u000A"),
    ("b", "\\u0008"),
    ("t", "\\u0009"),
    ("f", "\\u000C"),
    ("r", "\\u000D"),
    ("q", "\\u0022"),
    ("qq", "\\u0027"),
    ("sl", "\\u005C")
  )

//  map.foreach(pair => println(pair._2))

  // string literal
  val hello = "Hello"
  val escapes = "\\\"\'"
  println(escapes)

  // ident literal
  val s = 'aSymbol
  val sname = s.name
  println(s, sname)

  // Boolean literal
  val bool = true
  val fool = false
}
