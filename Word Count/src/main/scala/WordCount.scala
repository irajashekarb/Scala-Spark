import org.apache.spark.SparkContext

object WordCount {
  def main(args: Array[String]): Unit = {
    val inpath = "input/shakespeare.txt"
    val outpath = "output/word_count"

    val sc = new SparkContext("local[*]", "Word Count")
    try {
      val input = sc.textFile(inpath)
      val wc = input
        .map(_.toLowerCase)
        .flatMap(text => text.split("""\W+"""))
        .map(word => (word, 1))
        .reduceByKey((n1, n2) => n1 + n2)

      println("Writing output to: $outpath")
      wc.saveAsTextFile(outpath)
      println("Enter any key to finish the job....")
      Console.in.read()
    } finally {
      sc.stop()
    }
  }
}
