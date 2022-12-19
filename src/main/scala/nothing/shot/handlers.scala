package nothing.shot

import scala.util.Try

trait Handler {
  def handle(input: String): Option[Long]
}

object DateTimeHandler extends Handler {

  override def handle(input: String): Option[Long] = {
    Parser(input) match {
      case None => None
      case Some(result) =>
        import result.{date, time}
        import date.{year, month, day}
        val zeroTime = time.getOrElse(TimeOnly(0, 0, Some(0), Some(0)))
        import zeroTime.{hour, minute}
        val sec = zeroTime.second.getOrElse(0)
        val mil = zeroTime.millis.getOrElse(0)

        val loc = if (year > 1800) Timing.english else Timing.persian
        Some(Timing.toMillis(loc)(year, month - 1, day, hour, minute, sec, mil))
    }
  }
}

object EpochTimeMillisHandler extends Handler {
  private val pattern = """^\D*(\d{11,13})\D*$""".r

  override def handle(input: String): Option[Long] =
    pattern.findFirstMatchIn(input) match {
      case Some(value) =>
        Try {
          value.group(1).toLong
        }.toOption
      case _ => None
    }
}

object EpochTimeSecHandler extends Handler {
  private val pattern = """^\D*(\d{8,10})\D*$""".r

  override def handle(input: String): Option[Long] =
    pattern.findFirstMatchIn(input) match {
      case Some(value) =>
        Try {
          value.group(1).toLong * 1000
        }.toOption
      case _ => None
    }
}
