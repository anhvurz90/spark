import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * 
 * Run Spark:
 * 
 * anhvu@Lenovo-Y70:~/01.Work/15.Spark/spark-2.1.0-bin-hadoop2.7/bin$ ./spark-submit --class LineCount 
 *                      --master local[2] 
 *        ~/01.Work/03.Projects/git/tutorials/spark/line-count/target/samples-0.0.1-SNAPSHOT.jar 
 * 
 */
public class LineCount {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("Line Count");
        JavaSparkContext ctx = new JavaSparkContext(conf);
        
        JavaRDD<String> textLoad = ctx.textFile("/home/anhvu/01.Work/15.Spark/spark-2.1.0-bin-hadoop2.7/README.md");
        
        System.out.println(textLoad.count());
        
        ctx.close();
    }
}
