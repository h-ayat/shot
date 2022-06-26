package nothing.timer

import scala.io.StdIn
import scala.util.Try

object Runner extends Handler {

  val handlers: List[Handler] = EpochTimeHandler :: Nil
  def main(args: Array[String]): Unit = {

    // args.foreach(println)

    val result = args.toList match {
      case "-i" :: Nil =>
        val input = StdIn.readLine()
        // println("---" + input)
        handle(input)
      case "-p" :: input :: Nil =>
        handle(input)
      case _ =>
        None
    }

    println(result.map(prettyPrint).getOrElse("Could not handle"))

  }

  override def handle(input: String): Option[Long] =
    handlers.foldLeft[Option[Long]](None) { (acc, handler) =>
      acc match {
        case None  => handler.handle(input)
        case value => value
      }
    }

  private def prettyPrint(in: Long): String = {
    val t = Timing.formatTime(in)
    val p = Timing.persianFormatDate(in)
    val e = Timing.englishFormatDate(in)
    (in :: t :: p :: e :: Nil).map(_.toString).mkString("\n")
  }

}

trait Handler {
  def handle(input: String): Option[Long]
}

object EpochTimeHandler extends Handler {
  private val pattern = """^\D*(\d{13})\D*$""".r

  override def handle(input: String): Option[Long] =
    pattern.findFirstMatchIn(input) match {
      case Some(value) =>
        Try {
          value.group(1).toLong
        }.toOption
      case _ => None
    }
}
