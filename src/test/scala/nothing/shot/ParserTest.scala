package nothing.shot

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ParserTest extends AnyFlatSpec with Matchers {
  "Parser" should "parse simple date" in {
    Parser("2000/01/01") shouldBe Some(DateTime(DateOnly(2000, 1, 1), None))
  }

  it should "parse not clean dates" in {
    Parser("asone husaoe ut2000/01/01 asehut") shouldBe Some(
      DateTime(DateOnly(2000, 1, 1), None)
    )

    Parser("""something "2000/01/01";., """) shouldBe Some(
      DateTime(DateOnly(2000, 1, 1), None)
    )
  }

  it should "parse persian date" in {
    Parser("1400/1/1") shouldBe Some(DateTime(DateOnly(1400, 1, 1), None))
  }

  it should "parse date time" in {
    Parser("1400/1/1 20:20:20") shouldBe Some(
      DateTime(DateOnly(1400, 1, 1), Some(TimeOnly(20, 20, Some(20), None)))
    )
  }

  it should "parse time without sec" in {
    Parser("2020/01/01 20:20").flatMap(_.time) shouldBe Some(
       TimeOnly(20, 20, None, None)
    )
  }

  it should "parse time with sec" in {
    Parser("2020/01/01 20:20:20").flatMap(_.time) shouldBe Some(
      TimeOnly(20, 20, Some(20), None)
    )
  }

  it should "parse time with millis" in {
    Parser("2020/01/01 20:20:20.200").flatMap(_.time) shouldBe Some(
      TimeOnly(20, 20, Some(20), Some(200))
    )
  }

}
