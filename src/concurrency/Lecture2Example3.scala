package concurrency

/**
* Main thread will be waiting.
 */
object Lecture2Example3 extends App {

  val runnable = new Runnable {
    override def run(): Unit = {
      val name = Thread.currentThread().getName
      (1 to 5).foreach{ _ =>
        Thread.sleep(1000L)
        println(s"$name is running")
      }
    }
  }

  val thread = new Thread(runnable, "t1")
  thread.setDaemon(true)
  thread.start()

  try {
    thread.join()
  } catch {
    case e: InterruptedException => e.printStackTrace()
  }

}

