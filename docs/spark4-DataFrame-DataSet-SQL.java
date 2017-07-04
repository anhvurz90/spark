- 04.Apache Spark DataFrames, Datasets & SQL: {
	4.1.Overview {
		- The interfaces provided by Spark SQL provide Spark 
			with more information about the structure 
			of both the data and the computation being performed
		4.1.1.SQL {
			One use of Spark SQL is to execute SQL queries
		}
		4.1.2.Datasets & DataFrames {
			A Dataset is a distributed collection of data, added in Spark 1.6.
			A DataFrame is a Dataset organized into named columns.
				= DataSet<Row>
		}
	}
	4.2.Getting Started {
		4.2.1.Starting Point: SparkSession {
			import org.apache.spark.sql.SparkSession;
			
			SparkSession spark = SparkSession
				.builder()
				.appName("Java Spark SQL basic example")
				.config("spark.some.config.option", "some-value")
				.getOrCreate();
		}
		4.2.2.Creating DataFrames: {
			import org.apache.spark.sql.Dataset;
			import org.apache.spark.sql.Row;
			
			Dataset<Row> df = spark.read().json("examples/src/main/resources/people.json");
			
			// Displays the content of the DataFrame to stdout
			df.show();
			//age | name	|
			//30	 | Andy	|
		}
		4.2.3.Untyped Dataset Operations (aka DataFrame Opertaions) {
			import static org.apache.spark.sql.functions.col;
			
			// Print the schema in a tree format
			df.printSchema();
			
			// Select only the "name" column
			df.select("name").show();
			
			// Select everybody, but increment the 'age' by 1
			df.select(col("name"), col("age").plus(1)).show();
			
			// Select people older than 21
			df.filter(col("age").gt(21)).show();
			
			// Count people by age
			df.groupBy("age").count().show();
			
			API: http://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/Dataset.html
				http://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/functions.html
		}
		4.2.4.Running SQL Queries Programmatically: {
			import org.apache.spark.sql.Dataset;
			import org.apache.spark.sql.Row;
			
			// Register the DataFrame as a SQL temporary view
			df.createOrReplaceTempView("people");
			
			Dataset<Row> sqlDF = spark.sql("SELECT * FROM people");
			sqlDF.show();
		}
		4.2.5.Global Temporary View {
			// Register the DataFrame as a global temporary view
			df.createGlobalTempView("people");
			
			// Global temporary view is tied to a system preserved database 'global_temp'
			spark.sql("SELECT * FROM global_temp.people").show();
			
			// Global temporary view is cross-session
			spark.newSession().sql("SELECT * FROM global_temp.people").show();
		}
		4.2.6.Creating Datasets {
			import org.apache.spark.api.java.function.MapFunction;
			import org.apache.spark.sql.Dataset;
			import org.apacke.spark.sql.Row;
			import org.apache.spark.sql.Encoder;
			import org.apache.spark.sql.Encoders;
			
			public static class Person implements Serializable {
				String name; int age;
			}
			
			Person person = new Person("Andy", 32);
			
			// Encoders are created for Java beans
			Encoder<Person> personEncoder = Encoders.bean(Person.class);
			Dataset<Person> javaBeanDS = spark.createDataset(
				Collections.singletonList(person),
				personEncoder
			);
			
			javaBeanDS.show();

			// Encoders for most common types are provided in class Encoders
			Encoder<Integer> integerEncoder = Encoders.INT();
			Dataset<Integer> primitiveDS = spark.createDataset(Arrays.asList(1, 2, 3), integerEncoder);
			
			Dataset<Integer> transformedDS = primitiveDS.map(
				new MapFunction<Integer, Integer>() {
					@Override
					public Integer call(Integer value) throws Exception {
						return value + 1;
					}
				}, integerEncoder);				
			transformedDS.collect(); // Returns [2, 3, 4]
				
			// DataFrames can be converted to a Dataset by providing a class. Mapping based on name
			String path = "examples/src/main/resources/people.json";
			Dataset<Person> peopleDS = spark.read().json(path).as(personEncoder);
			peopleDS.show();
		}
		Interoperating with RDDs
			Inferring the Schema Using Reflection
			Programmatically Specifying the Schema
	}
	Data Sources
		Generic Load/ Save Functions
			Manually Specifying Options
			Run SQL on files directly
			Save Modes
			Saving to Persistent Tables
		Parquet Files
			Loading Data Programmatically
			Partition Discovery
			Schema Merging
			Hive metastore Parquet table conversion
				Hive/Parquet Schema Reconciliation
				Metadata Refresing
				
}
