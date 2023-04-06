package futures


import scala.concurrent.duration.{Duration, DurationInt}
import scala.util.{Failure, Success}
import scala.concurrent.{Await, ExecutionContext, Future, blocking}

object Example1 extends App {
  implicit val ec: ExecutionContext = ExecutionContext.Implicits.global

  @volatile var totalA = 0
  val text = Future {
    Thread.sleep(3000L)
    println(Thread.currentThread().getName)
    "na" * 16 + "BATMAN!!!"
  }
  println(Thread.currentThread().getName, "start", totalA)

  text.foreach { txt =>
    totalA += txt.count(_ == 'a')
    println(Thread.currentThread().getName, "a", totalA)
  }

  text.foreach { txt =>
    totalA += txt.count(_ == 'A')
    println(Thread.currentThread().getName, "A", totalA)
  }


  Await.result(text, Duration.Inf)
  println(Thread.currentThread().getName, "end", totalA)

}
