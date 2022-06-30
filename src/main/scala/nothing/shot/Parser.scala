package nothing.shot

import scala.util.parsing.combinator.RegexParsers

object Parser extends RegexParsers {
  import scala.util.parsing.combinator._

  private val before: Parser[String] = """^\D*""".r ^^ { _.toString() }
  private val after: Parser[String] = """\D*$""".r ^^ { _.toString() }
  private val seps: Parser[String] = "[ -/_:]".r ^^ { _.toString() }
  private val year: Parser[Int] = """\d{2,4}""".r ^^ { _.toInt }
  private val num: Parser[Int] = """\d\d?""".r ^^ { _.toInt }
  private val longNum: Parser[Int] = """\d+""".r ^^ { _.toInt }

  private val dateTime: Parser[DateTime] =
    before ~ year ~ seps ~ num ~ seps ~ num ~ (seps ~ time).? ~ after ^^ {
      case _ ~ y ~ _ ~ m ~ _ ~ d ~ Some(_ ~ time) ~ _ =>
        DateTime(DateOnly(y, m, d), Some(time))
      case _ ~ y ~ _ ~ m ~ _ ~ d ~ maybeT ~ _ =>
        DateTime(DateOnly(y, m, d), maybeT.map { case _ ~ b => b })
    }

  private val time: Parser[TimeOnly] =
    num ~ seps ~ num ~ (seps ~ num ~ (seps ~ longNum).?).? ^^ {
      case h ~ _ ~ m ~ None               => TimeOnly(h, m, None, None)
      case h ~ _ ~ m ~ Some(_ ~ s ~ None) => TimeOnly(h, m, Some(s), None)
      case h ~ _ ~ m ~ Some(_ ~ s ~ Some(_ ~ mil)) =>
        TimeOnly(h, m, Some(s), Some(mil))
    }

  def apply(input: String): Option[DateTime] =
    parse(dateTime, input.replace("  ", " ").replace(" ", "-")) match {
      case Success(result, _) =>
        Some(result)
      case _ =>
        None
    }

}
