package concurrency

/**
 * https://www.youtube.com/watch?v=kirhhcFAGB4&list=PLL8woMHwr36EDxjUoCzboZjedsnhLP1j4&index=3
 * virtual thread apparently supported in special Java versions (19 and it was a preview feature)
 */
object Lecture3Example1 extends App {

  val runnable = new Runnable {
    override def run(): Unit = {
      val name = Thread.currentThread().getName
      println(s"$name is running")
      Thread.sleep(10L)
      println(s"$name is finished")
    }
  }


}
