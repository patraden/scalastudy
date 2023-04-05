package concurrency

/**
 * https://jenkov.com/tutorials/java-concurrency/index.html
 * https://stackoverflow.com/questions/34877487/how-to-use-synchronized-in-scala
 */
object Lecture2Example1 extends App {

  private class MyThread extends Thread {
    override def run(): Unit = {
      val name = Thread.currentThread().getName
      println(s"$name is running")
      Thread.sleep(200L)
      println(s"$name is finished")
    }
  }

  private class MyRunnable extends Runnable {
    override def run(): Unit = {
      val name = Thread.currentThread().getName
      println(s"$name is running")
      Thread.sleep(100L)
      println(s"$name is finished")
    }

  }
  val runnable = new Runnable {
    override def run(): Unit = {
      val name = Thread.currentThread().getName
      println(s"$name is running")
      Thread.sleep(10L)
      println(s"$name is finished")
    }
  }

  private class StoppableRunnable extends Runnable {
    private var stopRequested = false
    override def run(): Unit = {
      val name = Thread.currentThread().getName
      println(s"StoppableRunnable $name is running")
      while (!isStopRequested) {
        sleep(1000L)
        println("...")
      }
      println(s"StoppableRunnable $name is stopped")
    }
    def requestStop(): Unit = this.synchronized(this.stopRequested = true)
    private def isStopRequested: Boolean = this.synchronized(this.stopRequested)
    private def sleep(millis: Long): Unit = try {
      Thread.sleep(millis)
    } catch {
      case e: InterruptedException => e.printStackTrace()
    }
  }

  private val myThread = new MyThread()
  myThread.setName("t1")
  myThread.start()

  private val myRunnable = new Thread(new MyRunnable(), "t2")
  myRunnable.start()

  private val runnableThread = new Thread(runnable, "t3")
  runnableThread.start()

  private val stoppableRunnable = new StoppableRunnable()
  private val stoppableRunnableThread = new Thread(stoppableRunnable, "t4")
  stoppableRunnableThread.start()

  try {
    Thread.sleep(5000L)
  } catch {
    case e: InterruptedException => e.printStackTrace()
  }

  println("requesting stop")
  stoppableRunnable.requestStop()
  println("stop requested")

}
