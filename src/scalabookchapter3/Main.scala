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

  greetStrings.update(2, "C")
  /*
  * Array object is mutable while list is not!
  */
  greetStrings(2) = "CC"
  //for (i <- 0 to 2) println(greetStrings(i))
  for (i <- 0 to 2) println(greetStrings.apply(i))

  // proper array init
  //val numNames = Array("zero", "one", "two")
  val numNames = Array.apply("zero", "one", "two")

  //val oneTwoThree = List[Int](1,2,3)
  val oneTwoThree: List[Int] = 1 :: 2 :: 3 :: Nil
  val fourFive = List[Int](4, 5)
  val oneTwoThreeFourFive = oneTwoThree ::: fourFive
  println(oneTwoThree + " and " + fourFive + " have not changed")
  println("meaning " + oneTwoThreeFourFive + " is indeed a new list!")
  // ::: list concatenation method

  val twoThree = List[Int](2, 3)
  val _oneTwoThree = 1 :: twoThree
  println(_oneTwoThree)
  // :: method named "cons" adding an element at the head of a list

  /*
  * tuple is another immutable collection object but despite list it can contain elements of different types
  * they happened to be very useful when you need to return several objects from a method
  */

  val pair: (Int, String) = (99, "Softballs")
  println(pair._1)
  println(pair._2)

  /*
  * set can be mutable and immutable
  * default set is immutable
  * another similar collection is Map
  */

  // fr immutable set below we add an element and then assign the new set to jetSet var
  var jetSet = Set("Boeing", "Airbus")
  //jetSet = jetSet.+("Leaf")
  jetSet += "Leaf"
  println(jetSet.contains("Leaf"))

  import scala.collection.mutable

  val movieSet = mutable.Set("Hitch", "Poltergeist")
  // movieSet.+=("Shrek")
  movieSet += "Shrek"
  println(movieSet)

  import scala.collection.immutable.HashSet

  val hashSet = HashSet("Tomatoes", "Chilies")
  println(hashSet + "Coriander")

  val treasureMap = mutable.Map[Int, String]() // init empty map and thus need to specify types explicitly
  // method -> below is an implicit method which applied to int and returns a tuple, thus implicitly changes types
  treasureMap += (1 -> "Go to island")
  treasureMap += (2 -> "Find big X on ground")
  treasureMap += 3.->("Dig.")
  println(treasureMap(2))

}