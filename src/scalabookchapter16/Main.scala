package scalabookchapter16

import scala.collection.mutable

object Main extends App {

  /**
   * List resemble Arrays a lot bu have two key differences:
   * 1. Lists are immutable structures, their values cannot be changed
   * 2. List have recursive structure (linked list), while Array have linear structure
   * Like Arrays they are homogeneous (all elements have the same type): List[T]
   * In Scala Lists are co-variant, which means that:
   * For any pair of S and T, if S is a subtype of T then List[S] is a subtype of List[T]
   * (List is considered as list of objects)
   * Thus empty List is List[Nothing]
   * Nothing is a subtype of Any thus empty List is a subtype of List[T] for any T.
   */
  val fruit: List[String] = List("apples", "oranges", "pears")
  val nums: List[Int] = List(1, 2, 3, 4)
  val diag3: List[List[Int]] =
    List(
      List(1, 0, 0),
      List(0, 1, 0),
      List(0, 0, 1)
    )
  val empty: List[Nothing] = List()

  /**
   * List are created using two contraction blocks: Nil and ::
   * Given :: is associated to the right we can skip brackets ()
   */
  val fruitA: List[String] = "apples" :: ("oranges" :: ("pears" :: Nil))
  val nums2A: List[Int] = 1 :: (2 :: (3 :: (4 :: Nil)))
  val diag3A: List[List[Int]] =
    (1 :: (0 :: (0 :: Nil))) ::
    (0 :: (1 :: (0 :: Nil))) ::
    (0 :: (0 :: (1 :: Nil))) :: Nil
  val emptyA: List[Nothing] = Nil

  /**
   * All actions with list are based on 3 basic operations (List basis):
   * 1. head - returns the first element in a List
   * 2. tail - returns the list without first element
   * 3. isEmpty - returns true if list is empty, otherwise false
   *
   * Let's consider an example of sorting List[Int] in ascending order.
   */

//  def isort(xs: List[Int]): List[Int] =
//    if (xs.isEmpty) Nil
//    else insert(xs.head, isort(xs.tail))
//
//  def insert(x: Int, xs: List[Int]): List[Int] =
//    if (xs.isEmpty || x <= xs.head) x :: xs
//    else xs.head :: insert(x, xs.tail)
//
//  println(isort(10 :: 1 :: 5 :: 8 :: 17 :: 2 :: 3 :: 2 :: Nil))

  /**
   * Pattern matching can be used for List.
   */

  val List(a, b ,c) = fruit
  println(a, b, c)
  val d :: e :: rest = fruit
  println(d, e)

  def isort(xs: List[Int]): List[Int] = xs match {
    case List()   => List()
    case x :: xs1 => insert(x, isort(xs1))
  }

  def insert(x: Int, xs: List[Int]): List[Int] = xs match {
    case List()  => List(x)
    case y :: ys =>
      if (x <= y) x :: xs
      else y :: insert(x, ys)
  }

  println(isort(10 :: 1 :: 5 :: 8 :: 17 :: 2 :: 3 :: 2 :: Nil))

  /**
   * Various useful List methods.
   */

  // Concat two lists :::
  // xs ::: ys ::: zs = xs ::: (ys ::: zs)
  println(List(1, 2, 3) ::: List(3, 4 ,5))

  // Let's make ::: manually
  def append[T](xs: List[T], ys: List[T]): List[T] =
    xs match {
      case List() => ys
      case x :: xs1 => x :: append(xs1, ys)
    }

  // init and last (reverted tail and head)
  // tail and head are o(1) while init and last are o(n)
  val abcde = List('a', 'b', 'c', 'd', 'e')
  println(abcde.last)
  println(abcde.init)

  println("-" * 10 + " Reverse " + "-" * 10)
  // reverse
  println(abcde.reverse)

  // heavy example due to concat method
  // n + (n - 1) + ... + 1 = (1 + n) * n / 2 ~ O(n**2)
  // standard reverse is much faster

  def rev[T](xs: List[T]): List[T] = xs match {
    case List() => xs
    case x :: xs1 => rev(xs1) ::: List(x)
  }
  println(rev(abcde))

  // prefixes and suffixes
  // drop and take extend and generalize tail and init

  println("-" * 10 + " Prefixes and Suffixes " + "-" * 10)
  println(abcde take 2)
  println(abcde take 6)
  println(abcde drop 2)
  println(abcde drop 6)

  // sx split n = (xs take n, xs drop n)
  println(abcde splitAt 2)

  println("-" * 10 + " apply " + "-" * 10)
  // choosing elements
  // apply should not be used much for list as it is O(i) where i is an index
  // xs apply n = (xs drop n).head
  println(abcde apply 2)
  println(abcde(2))
  println(abcde.indices)

  println("-" * 10 + " flatten " + "-" * 10)
  // flatten takes a List of List and linearize it
  println(List(List(1, 2), List(3), List(), List(4, 5, 6)).flatten)

  println("-" * 10 + " zip & unzip " + "-" * 10)
  println(abcde.indices zip abcde)
  println(abcde.zipWithIndex)
  val zipped = abcde zip List(1, 2, 3)
  println(zipped.unzip)

  println("-" * 10 + " mkString & addString " + "-" * 10)
  println(abcde.mkString("[", ",", "]"))
  println(abcde.mkString(""))
  // there is also an option called addString,
  // this method does not return string but adds it to a buffer (StringBuilder)
  val buf = new StringBuilder
  abcde.addString(buf, "(", ";", ")")

  println("-" * 10 + " iterator & toArray & copyToArray " + "-" * 10)
  val arr = abcde.toArray
  println(arr.toList)

  // copyToArray will copy elements into a new array from a starting position
  // xs.copyToArray(arr, start)

  val arr2 = new Array[Int](10)
  List(1, 2, 3).copyToArray(arr2, 3)
  arr2.foreach(println)
  println(abcde.iterator.next)

  println("-" * 10 + " Example of merge sort " + "-" * 10)
  import myclasses.MergeSort.{sortRec, mergeRec}
  val intSort = sortRec((x: Int, y: Int) => x < y) _
  val reverseIntSort = sortRec((x: Int, y: Int) => x > y) _
  val mixedInts = List(4, 1, 9, 0, 5, 8, 3, 6, 2, 7)
  println(mergeRec((x: Int, y: Int) => x < y)(List(4,30,20), List(2, 4, 3, 5)))
  println(intSort(mixedInts))
  println(reverseIntSort(mixedInts))


  println("-" * 10 + " map, flatMap and foreach " + "-" * 10)
  // List[T] Map f: T => A
  val words = List("the", "quick", "brown", "fox")
  println(List(1, 2, 3) map (_ + 1))
  println(words map (_.length))
  println(words map (_.toList.reverse.mkString))

  // List[T] flatMap f: T => Iterable
  println(words map(_.toList) )
  println(words.map(_.toList).flatten)
  println(words flatMap (_.toList))

  // create list of all pairs (i, j)
  val rng = List.range(1, 5) flatMap (i => List.range(1, i) map (j => (i, j)))
  // classic for
  val rng2 = for (i <- List.range(1, 5); j <- List.range(1, i)) yield (i, j)
  println(rng)
  println(rng2)

  // List[T] foreach f: T => Unit
  var sum = 0
  List(1, 2, 3, 4, 5) foreach (sum += _)
  println(sum)

  println("-" * 10 + " filter, partition, find, takeWhile, dropWhile and span " + "-" * 10)

  // List[T] filter f: T => Boolean
  println(List(1, 2, 5, 3, 4) filter (_ % 2 == 0))
  println(words filter (_.length == 3))

  // partition is similar to find but returns 2 lists, both filtered and unfiltered
  // xs partition p = (xs filter p, xs filter (!p(_)))
  println(List(1, 2, 3, 4, 5) partition (_ % 2 == 0))

  // find similar to filter but returns 1st filtered value
  println(List(1, 2, 3, 4, 5) find (_ % 2 == 0))
  println(List(1, 2, 3, 4, 5) find (_ < 0))

  // takeWhile and dropWhile also take predicate and return either the largest prefix
  // of a List with elements compiling to predicate.
  // dropWhile remove the largest prefix.
  // span combines both as:
  // sx span p = (xs takeWhile p, xs dropWhile p)

  println(List(1, 2, 3, -4, 5) takeWhile (_ > 0))
  println(words dropWhile (_ startsWith "t"))
  println(List(1, 2, 3, -4, 5) span (_ > 0))

  println("-" * 10 + " forall and exists " + "-" * 10)
  def hasZeroRow(m: List[List[Int]]) = m exists (row => row forall (_ == 0))
  println(hasZeroRow(diag3))

  println("-" * 10 + " foldLeft and foldRight " + "-" * 10)
  // List(a, b, c).forldLeft(z)(op) = op(op(op(z, a), b), c)
  // List(a, b, c).forldRight(z)(op) = op(a, op(b, op(c, z)))

  def sum(xs: List[Int]): Int = xs.foldLeft(0)(_ + _)
  def product(xs: List[Int]): Int = xs.foldLeft(1)(_ * _)
  println(words.tail.foldLeft(words.head)(_ + " " + _))

  // for associative operation foldLeft and foldRight are equivalent
  // but their effectiveness might differ.
  // Let's consider flatten - ing using folds.

  def flattenLeft[T](xss: List[List[T]]) =
    xss.foldLeft(List[T]())(_ ::: _)

  def flattenRight[T](xss: List[List[T]]) =
    xss.foldRight(List[T]())(_ ::: _) // is faster as concat takes time equivalent to left.size

  // Another example is reverse
  // This time we apply operation call snoc (rerted cons)
  // This time complexity is O(n)
  def reverseLeft[T](xs: List[T]) =
    xs.foldLeft(List[T]()){ (ys, y) => y :: ys}

  println("-" * 10 + " sortWith " + "-" * 10)
  // xs sortWith before, where before is a comparator
  // ATTENTION! SortWith uses Merge Sort as we implemented in myclasses.MergeSort

  println(List(1, -3, 4, 2, 6) sortWith (_ < _))
  println(words sortWith (_.length < _.length))

  /**
   * Methods from List companion object.
   */
  println("-" * 10 + " scala.List companion object methods " + "-" * 10)
  println(List.apply(1, 2 ,3))
  println(List.range(1, 5))
  println(List.range(1, 9, 2))
  println(List.range(9, 1, -3))
  println(List.fill(5)('a'))
  println(List.fill(3)("hello"))
  println(List.fill(2, 3)('b')) // List of Lists
  println(List.tabulate(5)(n => n * n))
  println(List.tabulate(5, 5)(_ * _))
  println(List.concat(List('a', 'b'), List('c')))
  println(List.concat())

  println("-" * 10 + " lazyZip " + "-" * 10)
  // Handling several lists.
  // Example where zip might not be efficient:
  val example = (List(10, 20) zip List(3, 4 ,5)).
    map {case (x, y) => x * y}
  println(example)

  // Two problems here:
  // 1. We create a new zipped List which is dropped when we call map.
  // 2. Map is being applied to tuples which created by zip and thus "_" syntax is not applicable to elements of Lists.
  // Thus for such case it is better to use lazyZip.
  // Besides map lazyZip also have analogues for exists and forall
  val example2 = (List(10, 20) lazyZip List(3, 4 ,5)).map(_ * _)
  println(example2)

  println((List("abc", "de") lazyZip List(3, 2)).forall(_.length == _))
  println((List("abc", "de") lazyZip List(3, 2)).exists(_.length != _))


}
