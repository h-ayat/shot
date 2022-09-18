package nothing.shot

import scala.io.StdIn
import scala.util.Try
import scala.util.parsing.combinator.RegexParsers

object Runner extends Handler {

  val handlers: List[Handler] =
    EpochTimeMillisHandler ::
      EpochTimeSecHandler ::
      DateTimeHandler :: Nil

  def main(args: Array[String]): Unit = {

    val result = args.toList match {
      case Nil =>
        handle(System.currentTimeMillis().toString())
      case "now" :: Nil =>
        handle(System.currentTimeMillis().toString)
      case "-i" :: Nil =>
        val input = StdIn.readLine()
        handle(input)
      case _ =>
        handle(args.mkString(" ").replace("  ", " "))
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
    val code = "#MahsaAmini"
    val t = Timing.formatTime(in)
    val p = Timing.persianFormatDate(in)
    val e = Timing.englishFormatDate(in)
    val d = Timing.diff(in)
    (code :: in :: t :: p :: e :: d :: Nil).map(_.toString).mkString("\n")
  }

}
