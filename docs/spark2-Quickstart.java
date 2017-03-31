- 02.Apache Spark Quick Start: {
	- Download Spark 2.1: http://spark.apache.org/downloads.html
	2.1. Interactive Analysis with the Spark Shell: {
		+ Basic: {
			./bin/spark-shell
			val textFile = sc.textFile("README.md");
			textFile.count();
			textFile.first();
			
			val linesWithSpark = textFile.filter(line => line.contains("Spark"));
			linesWithSpark.count();
		}
		+ More on RDD Operations: {
			textFile.map(line => line.split(" ").size)
				   .reduce((a, b) => if ( a > b) a else b)
			
			textFile.map(line => line.split(" ").size)
				   .reduce((a, b) => Math.max(a, b))
				   
			val wordCounts = 
			   textFile.flatMap(line => line.split(" "))
					.map(word => (word, 1))
					.reduceByKey((a, b) => a + b)
			wordCounts.collect();
		}
		+ Caching: {
			linesWithSpark.cache();
			linesWithSpark.count();
			linesWithSpark.count();
		}
	}
	2.2.Self-Contained Applications: {
		- SimpleApp.java: {
			import org.apache.spark.api.java.*;
			import org.apache.spark.SparkConf;
			import org.apache.spark.api.java.function.Function;
			
			public class SimpleApp {
				public static void main(String[] args) {
					String logFile = "SPARK_HOME/README.md";
					
					SparkConf conf = new SparkConf().setAppName("Simple App");
					JavaSparkContext sc = new JavaSparkContext(conf);
					JavaRDD<String> logData = sc.textFile(logFile).cache();
					
					long numAs = logData.filter(s -> s.contains("a")).count();
					long numBs = logData.filter(s -> s.contains("b")).count();
					
					System.out.println("Lines with a: " + numAs);
					System.out.println("Lines with b: " + numBs);
					
					sc.stop();
				}
			}
		}
		- Maven: {
			<project>
			  <groupId>edu.berkeley</groupId>
			  <artifactId>simple-project</artifactId>
			  <modelVersion>4.0.0</modelVersion>
			  <name>Simple Project</name>
			  <packaging>jar</packaging>
			  <version>1.0</version>
			  <dependencies>
			    <dependency> <!-- Spark dependency -->
			      <groupId>org.apache.spark</groupId>
			      <artifactId>spark-core_2.11</artifactId>
			      <version>2.1.0</version>
			    </dependency>
			  </dependencies>
			</project>			
		}
		- Run: {
			bin/spark-submit 
				--class "SimpleApp"
				--master local[4]
				simple-project-1.0.jar			
		}
	}
	2.3. Where to Go from Here: {
		- Spark programming guide:
			http://spark.apache.org/docs/latest/programming-guide.html
		- Deployment Overview:
			http://spark.apache.org/docs/latest/cluster-overview.html
		- Java examples: 
			https://github.com/apache/spark/tree/master/examples/src/main/java/org/apache/spark/examples
	}
	
	
}
