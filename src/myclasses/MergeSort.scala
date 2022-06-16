package myclasses
object MergeSort {

  def mergeRec[T](less: (T, T) => Boolean)
              (xs: List[T], ys: List[T]): List[T] =
    (xs, ys) match {
      case (Nil, _) => ys
      case (_, Nil) => xs
      case (x :: xs1, y :: ys1) =>
        if (less(x, y)) x :: mergeRec(less)(xs1, ys)
        else y :: mergeRec(less)(xs, ys1)
    }

  /**
   * List Merge Sort Reccursively.
   * Complexity is n*log(n):
   * merge complexity is O(n) - quit obvious.
   * Let's assume T(n) - time required for sorting.
   * Then T(n) = 2*T(n/2) + O(n) - time required to sort split List + time to merge.
   * Then T(n) = 4*T(n/4) + 2*O(n) = ... = T(1) + log(n)O(n) = O(n*long(n))
   * @param less - stored values comparator
   * @param xs - input List
   * @tparam T - type of List elements
   * @return sorted [[List]]
   */
  def sortRec[T](less: (T, T) => Boolean)
              (xs: List[T]): List[T] = {
    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (ys, zs) = xs splitAt n
      mergeRec(less)(sortRec(less)(ys), sortRec(less)(zs))
    }
  }
}
