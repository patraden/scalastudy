package scalabookchapter17

import scala.collection.immutable.Map

object Main extends App {

  /**
   * Array
   * Allow to store sequence of elements and get any by index
   * Arrays in scala are the same as in Java, thus all Java methods are applicable in Scala.
   */
  // init array of a fixed size
  val fiveInts = new Array[Int](5)
  // when elems are known
  val fiveToOne = Array(5,4,3,2,1)
  fiveInts(0) = fiveToOne(4)
  println(fiveInts.mkString("Array(", ", ", ")"))

  /**
   * scala.collection.mutable.ListBuffer
   * Allows to deal with limitation ofs List structure which is ineffective append.
   * ListBuffer provides O(1) time to add an element to the START and END of the collection.
   * to Add element to the start: +=:
   * to the end: +=
   * When buffer is build we can easily convert it to list using toList method.
   * One more reason to use istBuffer is an opportunity to avoid having stack overflow exceptions.
   */
  import scala.collection.mutable.ListBuffer
  val buf = new ListBuffer[Int]
  buf += 1
  buf += 2
  3 +=: buf
  println(buf.toList)

  /**
   * scala.collection.mutable.ArrayBuffer
   * Very similar with Array with an exception that it additionally allows to add and remove elements
   * to the start and the end of the collection.
   * All Array methods also available, however they work slightly slower as there is some wrapper class.
   * Add/remove operations are generally O(1) but sometimes it require O(n) time as implementation
   * requires to create a new Array to store buffer elements.
   * Also ArrayBuffer automatically manages memory consumption.
   */
  import scala.collection.mutable.ArrayBuffer
  val abuf = new ArrayBuffer[Int]()
  abuf += 12
  abuf += 15
  println(abuf, abuf(0))

  /**
   * Predef.StringOps
   * This is also a useful collection as it has a lot of collections methods and there is an implicit conversion to String.
   * Thus we can work with String as with collection.
   * In Below example happens exactly this. String gets converted to StringOps and we can call method exists.
   */
  def hasUpperCase(s: String) = s.exists(_.isUpper)
  println(hasUpperCase("Robert Frost"))
  println(hasUpperCase("e e comings"))

  /**
   * import Predef.Set - variable which by default refers to immutable.Set object
   * Key characteristic of a Set is that it can have an object only once.
   */

  // let's count unique works in a string using set.
  import scala.collection.mutable
  val text = "See Sport run. Run, Spot. Run!"
  val wordsArray = text.split("[ !,.]+")
  val words = mutable.Set.empty[String]
  for (word <- wordsArray)
    words += word.toLowerCase

  println(words)

  // Some useful methods of Set
  val nums = Set(1,2,3)
  println("immutable")
  println("add elem", nums + 5)
  println("remove elem", nums - 3)
  println("add elems", nums ++ List(5,6))
  println("remove elems", nums -- List(1,2))
  println("intersect", nums & Set(1,3,5,7))
  println("size", nums.size)
  println("contains", nums.contains(3))
  println("mutable")
  println("empty set", mutable.Set.empty[String])
  println("add elem", words += "the")
  println("remove elem", words -= "the")
  println("add elems", words ++= List("do", "re", "me"))
  println("remove elems", words --= List("do", "re", "me"))
  println("remove all", words.clear())

  /**
   * import Predef.Map - variable which by default refers to immutable.Map object
   * Maps are similar with Arrays with only difference that to access an element we can use
   * non-numeric indexes = keys.
   */
  val map = mutable.Map.empty[String, Int]
  map("hello") = 1
  map("there") = 2
  println(map("hello"))

  def countWords(text: String): mutable.Map[String, Int] = {
    val counts = mutable.Map.empty[String, Int]
    for (rawWord <- text.split("[ ,!.]+")) {
      val word = rawWord.toLowerCase
      val oldCount =
        if (counts.contains(word)) counts(word)
        else 0
      counts += (word -> (oldCount + 1))
    }
    counts
  }
  println(countWords("See Spot run! Run, Spot. Run!"))

  /**
   * scala.collection.immutable.TreeSet
   * Sometimes we might require to work with sorted Sets or Maps.
   * For this scala has two traits: [[SortedSet]] and [[SortedMap]]
   * They in turn implemented using [[TreeSet]] and [[TreeMap]].
   * Which in turn store their element in the form of RedBlackTrees for [[TreeSet]] or keys for [[TreeMap]]
   * The ordering itself is defined using implicit trait [[Ordering]]
   * Black-red tree for sorted sets
   */
  import scala.collection.immutable.TreeSet
  val ts = TreeSet(9,3,1,8,0,2,7,4,6,5)
  val cs = TreeSet('f', 'u', 'n')
  println(ts)
  println(cs)
  import scala.collection.immutable.TreeMap
  var tm = TreeMap(3 -> 'x', 1 -> 'x', 4 -> 'x')
  tm += (2 -> 'x')
  println(tm)

  /**
   * Mutable or Immutable collections?
   * Some problems solved better with mutable collections some with immutable.
   * If you do not know which one to choose - start with immutable.
   * Sometimes if code with mutable collection becomes complex - we need to consider refactoring to immutable.
   * Besides complexity immutable collections are more compact.
   * Thus to store mutable Map in the form of [[HashMap]] we need 80 bytes and 16 extra bytes to add new elements.
   * At the same time empty immutable [[Map]] is just a single object which can be referred by others.
   * Moreover at the moment scala collections have up to 4 immutable Map and Set in single object,
   * which require from 16 to 40 bytes (Set1, Set2, Set3, Set4, Map1, Map2, Map3, Map4).
   * Thus for small maps and set immutable version occupy much less memory than mutable.
   * Given many collections are indeed small refactoring to their immutable version may give good performance advantages.
   * Thus to convert mutable to immutable and vise versa scala provides a lot of syntax sugars.
   * Examples:
   * Immutable Maps and Sets do not support +=
   * However, scala provides alternative interpretation for +=.
   * when we use expression a += b and a does not support +=, then Scala interprets it as a = a + b
   * Same idea is applicable to any method which ends with = like: -= or ++=
   */

  val people = Set("Nancy", "Jane")
//  people += "Bob" gives an error
  // but if we declare people as var
  println(people)
  var peopleVar = Set("Nancy", "Jane")
  peopleVar += "Bob" // operation creates new collection with extra element and then variable is linked to a new immutable Set
  println(peopleVar)

  peopleVar -= "Jane"
  peopleVar ++= List("Tom", "Harry")
  println(peopleVar)

  // let's see why it is so useful
  // below we use immutable collection
  var capitals = Map("US" -> "Washington", "France" -> "Paris")
  capitals += ("Japan" -> "Tokyo")
  println(capitals("France"))
  // if we want to change it to mutable then we just need to import mutable Map
  var capitalsM = scala.collection.mutable.Map("US" -> "Washington", "France" -> "Paris")
  capitalsM += ("Japan" -> "Tokyo")
  println(capitalsM("France"))
  // = methods interpretation works not only with collections:
  var roughlyPi = 3.0
  roughlyPi += 0.1
  roughlyPi += 0.04
  println(roughlyPi)

}
