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
			
			SparkSession session = SparkSession
				.builder()
				.appName("Java Spark SQL basic example")
				.config("spark.some.config.option", "some-value")
				.getOrCreate();
		}
		4.2.2.Creating DataFrames: {
			import org.apache.spark.sql.Dataset;
			import org.apache.spark.sql.Row;
			
			Dataset<Row> df = session.read().json("examples/src/main/resources/people.json");
			df.show();
			//age | name	|
			//30	 | Andy	|
		}
		4.2.3.Untyped Dataset Operations (aka DataFrame Opertaions) {
			import static org.apache.spark.sql.functions.col;
			
			df.printSchema();
			df.select("name").show();
			df.select(col("name"), col("age").plus(1)).show();
			df.filter(col("age").gt(21)).show();
			df.groupBy("age").count().show();
			
			API: http://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/Dataset.html
				http://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/functions.html
		}
		4.2.4.Running SQL Queries Programmatically: {
			import org.apache.spark.sql.Dataset;
			import org.apache.spark.sql.Row;
			
			df.createOrReplaceTempView("people");
			Dataset<Row> sqlDF = spark.sql("SELECT * FROM people");
			sqlDF.show();
		}
		4.2.5.Global Temporary View {
		}
		Creating Datasets
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
