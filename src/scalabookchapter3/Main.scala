package scalabookchapter3

object Main extends App {
  /*
  * Scala is completely OOP!
  * All scala operations are methods
  */
  val x: Int = 1.+(2)
  val greetStrings: Array[String] = new Array[String](3)
  // val greetStrings = new Array[String](3)
  // greetStrings(0) = "A"
  greetStrings.update(0, "A")
  // greetStrings(1) = "B"
  greetStrings.update(1, "B")
  // greetStrings(2) = "C"
  greetStrings.update(2, "C")
  //for (i <- 0 to 2) println(greetStrings(i))
  for (i <- 0 to 2) println(greetStrings.apply(i))

  // proper array init
  //val numNames = Array("zero", "one", "two")
  val numNames = Array.apply("zero", "one", "two")
  /*
  * Array object is mutable whil list is not!
  */

  //val oneTwoThree = List[Int](1,2,3)
  val oneTwoThree = 1 :: 2 :: 3 :: Nil //List()
  val fourFive = List[Int](4,5)
  val oneTwoThreeFourFive = oneTwoThree ::: fourFive
  println(oneTwoThree + " and " + fourFive + " have not changed")
  println("meaning " + oneTwoThreeFourFive + " is indeed a new list!")
  // ::: list concatenation method

  val twoThree = List[Int](2,3)
  val _oneTwoThree = 1 :: twoThree
  println(_oneTwoThree)
  // :: method named "cons" adding an element at the head of a list

  /*
  * tupple is another immutable collection object but despite list it can contain elements of different types
  * they happened to be very useful when you need to return several objects from a method
  */

  val pair = (99, "Luftballons")
  println(pair._1)
  println(pair._2)

  /*
  * set can be mutable and immutable
  * default set is immutable
  * another similar collection is Map
  */

  var jetSet = Set("Boeing", "Airbus")
  //jetSet = jetSet.+("Leaf")
  jetSet += "Leaf"
  println(jetSet.contains("Cessna"))

  import scala.collection.mutable
  val movieSet = mutable.Set("Hitch", "Poltergeist")
  // movieSet.+=("Shrek")
  movieSet += "Shrek"
  println(movieSet)

  import scala.collection.immutable.HashSet
  val hashSet = HashSet("Tomatoes", "Chilies")
  println(hashSet + "Coriander")

  val treaSureMap = mutable.Map[Int, String]() // init empty map and thus need to specify types explicitely
  treaSureMap.+=(1.->("Go to island"))
  treaSureMap.+=(2.->("Find big X on ground"))
  treaSureMap.+=(3.->("Dig."))
  println(treaSureMap(2))
}
