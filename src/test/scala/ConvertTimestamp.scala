import java.sql.Timestamp
import java.time.Instant

import com.holdenkarau.spark.testing.DatasetSuiteBase
import org.apache.spark.sql.{AnalysisException, Dataset}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.OptionValues
import test.TestData
import timestamp.TestMessage

class ConvertTimestamp extends AnyFlatSpec with DatasetSuiteBase with Matchers with OptionValues {

  it should "throw analysis exception when trying to convert timestamps" in {
    import spark.implicits._

    val testData = Seq(TestData(Timestamp.from(Instant.now)))
    assertThrows[AnalysisException] {
      spark
        .createDataset(testData)
        .transform(toProtobuf)
        .collect()
    }
  }

  private def toProtobuf(in: Dataset[TestData]) = {
    import scalapb.spark.Implicits._
    in.as[TestMessage]
  }

}
