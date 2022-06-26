package nothing.timer

import com.ibm.icu.util.ULocale
import com.ibm.icu.util.TimeZone
import com.ibm.icu.util.Calendar
import com.ibm.icu.text.DateFormatSymbols
import com.ibm.icu.text.SimpleDateFormat
import java.util.Date

object Timing {

  private lazy val persian = Loc(
    new ULocale("fa_IR@calendar=persian")
  )
  private lazy val english = Loc(ULocale.ENGLISH)
  private lazy val timeZone = TimeZone.getTimeZone("Asia/Tehran")

  private lazy val timeFormat = new SimpleDateFormat("HH:mm:ss.SSS z")
  private lazy val persianFormat = new SimpleDateFormat("yyyy/MM/dd")
  private lazy val englishFormat = new SimpleDateFormat("yyyy/MM/dd MMM")

  def persianFormatDate(timestamp: Long): String = {
    val cal = persian.cal(timestamp)
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
      loc: Loc,
      format: SimpleDateFormat
  ): String = {
    val cal = loc.cal(timestamp)
    format.format(new Date(timestamp))

  }

  case class Loc(locale: ULocale) {
    val dateSymbols: DateFormatSymbols = new DateFormatSymbols(locale)

    def cal(timestamp: Long): Calendar = {
      val c = Calendar.getInstance(timeZone, locale)
      c.setTimeInMillis(timestamp)
      c
    }
  }
}
