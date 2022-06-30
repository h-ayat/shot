package nothing.shot

final case class DateTime(date: DateOnly, time: Option[TimeOnly])
final case class TimeOnly(
    hour: Int,
    minute: Int,
    second: Option[Int],
    millis: Option[Int]
)
final case class DateOnly(year: Int, month: Int, day: Int)
