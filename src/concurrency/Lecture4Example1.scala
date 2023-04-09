package concurrency

/**
 * https://www.youtube.com/watch?v=LCSqZyjBwWA&list=PLL8woMHwr36EDxjUoCzboZjedsnhLP1j4&index=4*
 */
object Lecture4Example1 extends App {

  class MyRunnable extends Runnable {

    // each runnable has its own count field
    // and this field will be the same in thread1 and thread2
    // because both thread reference this object from heap (so it is shared)
    private var count: Int = 0
    override def run(): Unit = {
      // this variable however will be locally instantiated and thus will not be shared between threads
      val obj = new Object()
      println(obj)
      val name = Thread.currentThread().getName
      println(s"$name is running")
      (1 to 1_000_000).foreach {
        _ => count += 1
//        _ => synchronized(count += 1)
      }
      println(s"$name is finished with count: $count")
    }
  }

  val runnable = new MyRunnable()

  /**
   * example with problems
   * (2 threads share the runnable)
   * */
  private val thread1 = new Thread(runnable, "Thread 1")
  private val thread2 = new Thread(runnable, "Thread 2")

  /**
   * example without problems
   * (2 threads do not share the runnable)
   * */
//  private val thread1 = new Thread(new MyRunnable(), "Thread 1")
//  private val thread2 = new Thread(new MyRunnable(), "Thread 2")

  thread1.start()
  thread2.start()

  /**
   * race conditions:
   * This occur when two threads share some data on a heap
   * and they both read and write it and they do not do it in a synchronizes fashion
   */

  /**
   * data write visibility:
   * thread 1 copies count = 1 to CPU register and increment it by 1
   * thread 2 also copies count = 1 to CPU and it does not know that thread 1 is being increasing it now by 1
   * so update of thread 1 is NOT YET VISIBLE to thread 2
   *
   * Sometimes in sme architectures L1, L2, L3 cashes can see object in other CPU cashes ("cache coherence")
   * and thus find write update earlier than if they access it from RAM/Heap
   * But still there is no guarantee when thread 1 updates count value in L1/L2/L3 cashes anyways...
   */

}
