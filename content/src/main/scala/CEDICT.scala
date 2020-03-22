import org.apache.spark.sql.SparkSession
import org.elasticsearch.spark.sql._

case class CEDICTDefinition(subdefinitions: Array[String], simplified: String, traditional: String, pinyin: String, language: String, token: String, source: String)

object CEDICT {
  def main(args: Array[String]) {
    val spark = SparkSession.builder
      .appName("CEDICT")
      .config("es.net.http.auth.user", "user")
      .config("es.net.http.auth.pass", "pass")
      .getOrCreate()

    // Allows creating a row from a case class
    import spark.implicits._

    val cedict = spark
      .read.textFile("cedict_ts.u8").cache()
      // Strip out the long header with license and FAQ
      .filter(_.startsWith("#"))
      .map(line => parseLine(line))


    cedict.saveToEs("cedict")

    spark.stop()
  }

  private def parseLine(line: String): CEDICTDefinition = {
    /*
     * The format is:
     * traditional simplified [pinyin[ /definition1/definition2/definition3/
     */

    // Split the string into thirds by brackets
    // Because they are the only formatting character that isn't repeated
    val cutAtPinyinFront = line.split('[')
    val cutAtPinyinBack = cutAtPinyinFront(1).split(']')

    val characterSection = cutAtPinyinFront(0).stripSuffix(" ").split(' ')
    val definitionSection = cutAtPinyinBack(1).stripPrefix(" ").stripPrefix("/").stripSuffix("/")

    CEDICTDefinition(traditional = characterSection(0), simplified = characterSection(1), pinyin = cutAtPinyinBack(0), subdefinitions = definitionSection.split('/'), language = "CHINESE", token = characterSection(1), source = "CEDICT")
  }
}
