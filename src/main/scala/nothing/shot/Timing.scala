package nothing.shot

import com.ibm.icu.util.ULocale
import com.ibm.icu.util.TimeZone
import com.ibm.icu.util.Calendar
import com.ibm.icu.text.DateFormatSymbols
import com.ibm.icu.text.SimpleDateFormat
import java.util.Date

object Timing {

  lazy val persian = new ULocale("fa_IR@calendar=persian")
  lazy val english = ULocale.ENGLISH
  private lazy val timeZone = TimeZone.getTimeZone("Asia/Tehran")

  private lazy val timeFormat = new SimpleDateFormat("EEE HH:mm:ss.SSS z")
  private lazy val persianFormat = new SimpleDateFormat("yyyy/MM/dd")
  private lazy val englishFormat = new SimpleDateFormat("yyyy/MM/dd MMM")

  def timestampToCalendar(timestamp: Long, locale: ULocale): Calendar = {
    val c = Calendar.getInstance(timeZone, locale)
    c.setTimeInMillis(timestamp)
    c
  }

  def persianFormatDate(timestamp: Long): String = {
    val cal = timestampToCalendar(timestamp, persian)
    val year = cal.get(Calendar.YEAR)
    val month = cal.get(Calendar.MONTH) + 1
    val day = cal.get(Calendar.DAY_OF_MONTH)
    s"$year/$month/$day"
  }

  def englishFormatDate(timestamp: Long): String = {
    formatDateTime(timestamp, english, englishFormat)
  }

  def formatTime(timestamp: Long): String = {
    formatDateTime(timestamp, persian, timeFormat)
  }

  def formatDateTime(
      timestamp: Long,
      loc: ULocale,
      format: SimpleDateFormat
  ): String = {
    val cal = timestampToCalendar(timestamp, loc)
    format.format(new Date(timestamp))

  }

  def toMillis(loc: ULocale)(
      year: Int,
      month: Int,
      day: Int,
      hour: Int,
      min: Int,
      sec: Int,
      millis: Int
  ): Long = {
    val cal = Calendar.getInstance(timeZone, loc)
    cal.set(year, month, day)
    cal.set(year, month, day, hour, min, sec)
    cal.set(Calendar.MILLISECOND, millis)
    cal.getTimeInMillis()
  }

  def diff(in: Long): String = {
    val d = System.currentTimeMillis() - in
    val md = Math.abs(d)
    val suffix = if (d > 0) "ago" else "ahead"

    val units =
      List("second" -> 1000, "minute" -> 60, "hour" -> 60, "day" -> 24)
        .foldLeft[(List[(String, Int)], Int)](Nil -> 1) { (acc, curr) =>
          val (name, unit) = curr
          val newVal = unit * acc._2

          ((name -> newVal) :: acc._1) -> newVal
        }
        ._1

    val result = units.foldLeft[(List[String], Long)](Nil -> md) {
      (acc, curr) =>
        val (prev, value) = acc
        value / curr._2 match {
          case 0 =>
            prev -> value
          case 1 =>
            val message = "a " + curr._1
            (message :: prev) -> (value % curr._2)
          case any =>
            val message = s"$any ${curr._1}s"
            (message :: prev) -> (value % curr._2)
        }
    }

    result._1.reverse.mkString(" ") + " " + suffix
  }
}
