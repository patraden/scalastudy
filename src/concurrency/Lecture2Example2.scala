package concurrency

/**
* All demon threads will be forced to stop when main thread is stopped.
 */
object Lecture2Example2 extends App {

  val runnable = new Runnable {
    override def run(): Unit = {
      val name = Thread.currentThread().getName
      while (true) {
        Thread.sleep(1000L)
        println(s"$name is running")
      }
    }
  }

  val thread = new Thread(runnable, "t1")
  thread.setDaemon(true)
  thread.start()
  Thread.sleep(3100L)

}

