- 01.Using Apache Spark 2.0 to Analyze the City of Sanfrancisco's Open Data {
	https://data.sfgov.org
		fire incidents
}
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
- 03.Apache Spark Programming Guide: {
	3.1.Overview {
		Driver program
		RDD: a collection of elements partitioned 
			across the nodes, can be operated in parallel
		Shared variable: broadcast variables, accumulators
	}
	3.2.Linking with Spark {
		groupId = org.apache.spark
		artifactId = spark-core_2.11
		version = 2.1.0
		
		import org.apache.spark.api.java.JavaSparkContext
		import org.apache.spark.api.java.JavaRDD
		import org.apache.spark.SparkConf
	}
	3.3.Initializing Spark {
		SparkConf conf = new SparkConf().setAppName(appName).setMaster(master);
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		3.3.1.Using the Shell: {
			$./bin/spark-shell --master local[4]
			$ ./bin/spark-shell --master local[4] 
						--jars code.jar
			$ ./bin/spark-shell --master local[4] 
						--packages "org.example:example:0.1"
		}
	}
	3.4.Resilient Distributed Datasets (RDDs)
		3.4.1.Parallelized Collections {
			List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
			JavaRDD<Integer> distData = sc.parallelize(data);
			distData.reduce((a, b) -> a + b);
		}
		3.4.2.External Datasets {
			JavaRDD<String> distFile = sc.textFile("data.txt");
			distFile.map(s -> s.length()).reduce((a, b) -> a + b);
			
			sc.wholeTextFiles()
			sc.sequenceFile()
			sc.hadoopRDD()
			sc.newAPIHadoopRDD()
			
			JavaRDD.saveAsObjectFile();
			sc.objectFile()
		}
		3.4.3.RDD Operations {
			- Transformation & Actions
			3.4.3.1.Basics {
				JavaRDD<String> lines = sc.textFile("data.txt");
				JavaRDD<Integer> lineLengths = lines.map(s -> s.length());
				lineLenghts.persist(StorageLevel.MEMORY_ONLY());
				int totalLength = lineLengths.reduce((a, b) -> a + b);
			}
			3.4.3.2.Passing Function to Spark {
				{
					JavaRDD<String> lines = sc.textFile("data.txt");
					JavaRDD<Integer> lineLengths =
						lines.map(new Function<String, Integer>() {
							public Integer call(String s) { return s.length(); }
						});
					int togalLength = 
						lineLengths.reduce(new Function2<Integer, Integer, Integer>() {
							public Integer call(Integer a, Integer b) { return a + b; }
						});
				}
				
				{
					class GetLength implements Function<String, Integer> {
						public Integer call(String s) { return s.length(); }
					}
					class Sum implements Function2<Integer, Integer, Integer> {
						public Integer call(Integer a, Integer b) { return a + b; }
					}
					JavaRDD<String> lines = sc.textFile("data.txt");
					JavaRDD<Integer> lineLengths = lines.map(new GetLength());
					int totalLength = lineLenghts.reduce(new Sum());
				}
			}
			3.4.3.3.Understanding closures {
				- understanding the SCOPE and the LIFE CYCLE of variables
				1.Example: {
					SO WRONG!!!: {					
						int counter = 0;
						JavaRDD<Integer> rdd = sc.parallelize(data);
						rdd.foreach(x -> counter += x);
						println("Counter value: " + counter);
					}
					---> Should user Accumulator
				}
				2.Local vs. cluster modes: {
						closure( variables & methods those are 
					visible for the executors) are all COPIED (SERIALIZED)
					to each executor.
						-> in each EXECUTOR counter above is NOT counter in
					the driver node
				}
				3.Printing elements of an RDD: {
					rdd.foreach(println) -> to stdout of EXECUTOR
					rdd.map(println) -> to stdout of EXECUTOR
					
					rdd.collect().foreach(println)
					---> may be OutOfMemory coz collect() fetches entire
					RDD to a single machie(driver node)
					---> should be rdd.take(100).foreach(println);
				}
			}
			3.4.3.4.Working with Key-Value Pairs {
				JavaRDD<String> lines = sc.textFile("data.txt");
				JavaPairRDD<String, Integer> pairs =
					lines.mapToPair(s -> new Tuple2(s, 1));
				JavaPairRDD<String, Integer> counts = pairs.reduceByKey((a, b) -> a + b);
				counts.sortByKey();
			}
			3.4.5.Transformations: {
				map(func), filter(func), flatMap(func)
				
				mapPartitions(func), mapPartitionsWithIndex(func)
				
				sample(withReplacement, fraction, seed)
				
				union(otherDataset), intersection(otherDataset)
				
				distinct([numTasks])
				
				groupByKey([numTasks]) (K, V) -> (K, Iterable<V>), 
				reduceByKey(func, [numTasks]) -> (K, V) -> (K, V)
				aggregateByKey(zeroValue)(seqOp, combOp, [numTasks])
					(K, V) -> (K, U)
				sortByKey([ascending], [numTasks])
				
				join(otherDataset), (K, V)*(K, W) -> (K, (V,W))
				cogroup(otherDataset): (K, V) * (K, W) -> (K, (Iterable<V>, Iterable<W>))
				pipe(command): 
				coalesce(numPartitions): Decrease the number of partitions
				repartition(numPartitions): 
				repartitionAndSortWithinPartitions(partitioner)
			}
			3.4.6.Actions {
				reduce(func), collect(), count()
				first(), take(n), 
				takeSample(withReplacement, num, [seed]),
				takeOrdered(n, [ordering]),
				
				saveAsTextFile(path);
				saveAsSequenceFile(path);
				saveAsObjectFile(path);
				countByKey()
				foreach(func);
			}
			3.4.7.Shuffle operations {
				Background
				Performance Impact
			}
		}
		3.4.4.RDD Persistence {
			- MEMORY_ONLY
			- MEMORY_AND_DISK
			- MEMORY_ONLY_SER
			- MEMORY_AND_DISK_SER
			- DISK_ONLY
			- MEMORY_ONLY_2, MEMORY_AND_DISK_2, etc.
			- OFF_HEAP
			
			cache() = persist(StorageLevel.MEMORY_ONLY)
			3.4.4.1.Which Storage Level to Choose? {
				MEMORY_ONLY,
				MEMORY_ONLY_SER,
				...
			}
			3.4.4.2.Removing Data {
				RDD.unpersist();
			}
		}
	3.5.Shared Variables {
		3.5.1.Broadcast Variables {
		}
		3.5.2.Accumulators {
		}
	}
	Deploying to a Cluster
	Launching Spark jobs from Java/ Scala
	Unit Testing
	Where to Go from Here
}
- 04.Apache Spark DataFrames, Datasets & SQL: {
}
- 05.Apache Spark Structured Streaming: {
}
- 06.Apache GraphX (Graph Processing): {
}
