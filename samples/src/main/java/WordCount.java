import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import scala.Tuple2;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;

public class WordCount {
    
    private static final Pattern SPACE = Pattern.compile(" ");
    
    public static void main(String[] args) {
        new WordCount().run(args);
    }

    private void run(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: WordCount <file>");
            System.exit(1);
        }
        SparkSession sparkSession = 
            SparkSession.builder()
                        .appName("JavaWordCount")
                        .getOrCreate();
        
        JavaRDD<String> lines = sparkSession.read().textFile(args[0]).javaRDD();
        JavaRDD<String> words = lines.flatMap(s -> Arrays.asList(SPACE.split(s)).iterator());
       
        JavaPairRDD<String, Integer> ones = words.mapToPair(s -> new Tuple2<>(s, 1));
        JavaPairRDD<String, Integer> counts = ones.reduceByKey((i1, i2) -> i1 + i2);
        
        List<Tuple2<String, Integer>> output = counts.collect();
        
        for (Tuple2<?,?> tuple : output) {
            System.out.println(tuple._1() + ": " + tuple._2());
        }
        sparkSession.stop();
    }
}
