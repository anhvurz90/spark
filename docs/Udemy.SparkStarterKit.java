UDEMY - SPARK STARTER KIT
1.Let's Get Started: {
  1.1.Prerequisites {
    - Hadoop Starter Kit
    - A Short Chapter on Scala
  }
  1.2.Say Hi to Spark: {
    - Spark vs Hadoop - Who Wins?
    - Challenges Spark tries to address
    - How Spark is faster than Hadoop?
  }
  1.3.Introduction to RDD: {
    - The need for RDD
    - What is RDD?
    - What an RDD is not?
  }
  1.4.Spark's execution behind the scenes: {
    - First program in Spark
    - What are denendencies & why they are important?
    - Program to Execution (Part1 & 2)
  }
  1.5.Fault tolerance in In-memory computing: {
    - Fault Tolerance
    - Memory Management
  }
  1.6.A Short chapter on Scala: {
    - Introduction to Scale
    - First program in Scala (not hello world)
    - Scala functions
  }
}
2.Running Spark on your computer: {
  - Download Spark
  - $SPARK_HOME\bin\spark-shell
}
3.Spark vs. Hadoop who wins: {
		Hadoop:			Spark:
  - Storage:	+ HDFS			+ Leverage Existing
  - MapReduce:	+ Yes			+ Yes
  - Speed:	+ Fast			+ 10 - 100x faster
  - Resource	+ Yarn			+ Standalone
  Management:
  
  - Does Spark replace Hadoop?
    + Spark enhances but replace Hadoop stack
}
4.Challenges Spark tries to address: {
  4.1.Spark solves inefficiencies in 2 areas: {
    - Iterative machine learning
    - Iterative data mining
  }
  4.2.Iterative example: Degree of separation: {
    - You: Sam, Emily
    - Emily: John, Riley
    - Riley: Jim, Steve

    Need to run the program MULTIPLE time to reach the output
  }
  4.3.Iterative machine learning with Hadoop: {
    - Input -> Hadoop MapReduce -> intermediate output1(in disk, HDFS)
    - Intermediate output1 -> Hadoop MapReduce -> intermediate output2(in disk, HDFS)
    - ...
    - Intermediate outputn -> Hadoop MapReduce -> output
  }
  4.4.Iterative machine learning with Spark: {
    - The same steps as with Hadoop, but intermediate outputs are kept in MEMORY
  }
}
5.How Spark is faster than Hadoop? {
  5.1.In-Memory Computing:
    - Not a new concept. So what's special about Spark?
    - Spark offers in-memory computing at distributed scale ( many machines)
  }
  5.2.Spark Execution Engine: {
    - Commands -> Logical plan -> Physical plan -> Tasks -> Spark cluster
  }
  5.3.Resilient Distributed Datasets: {
    - Core of Spark
    - RDD enables Spark to keep track of all operations & transformations
  }
  5.4.Spark Architecture: {
    - Built from the ground up for speed & efficiency
  }
}
6.The need for RDD: {
  6.1.How does Hadoop handle fault-tolerance: {
    - Hadoop stores results of MapReduce jobs in disks.
    - Node 2 dies -> no problem, node 7 reads result of node 2 from disks (HDFS)
  }
  6.2.Sparks: { 
    - Stores results of Spark jobs in memory.
    - Node 7 needs node 2 to be alive to read result from its memory
    - So if node 2 DIES, ...?
    - 10 -> John(+10) -> Emily(+20) -> Sam(+5) -> Riley(+40) -> 85
      A	    B = A + 10   C = B + 20    D = C+5	  E =D + 40
	If Sam dies, ask someone else to calculate D from Emily, then pass the result to Riley
	+ Emily -> Jim -> Riley
    - Spark KEEPS TRACK OF EVERY SINGLE OPERATION, TRANSFORMATION.
    - Via RDD. 
      We can NOT work with data in Spark WITHOUT RDD.
      We do use Spark function which ALWALS create RDD behind the scenes.
  }
}
7.What is RDD? {
  7.1.Sample: {
    - val logfile = sc.textFile("hdfs://node1:8020/user/hirw/log")
      val errors = logfile.filter(_.startsWith("ERROR"))
      val hdfs = errors.filter(_.contains("HDFS"))
      hdfs.count()

    - Lineage:
    hdfs://node1.. ---> logfile ---> errors ---> hdfs
			  RDD	      RDD	 RDD
    - Run example: spark/bin/spark-shell
  }
  7.2.Definition: {
    - RDD: the basic abstraction in Spark, representing an immutable, partitioned collection of elements that can be operated on in parallel.
	+ Property#1: List of partition
	+ Property#2: Compute Function ( textFile(), filter()...)
		Each function is applied to ALL elements.
	+ Property#3: List of dependencies:
		HDFS depends on ERRORS depends on LOGFILE depends on INPUT DATASET
    - RDD lets Spark keep track of all the opeations on the data set, helps to achieve fault-tolerance
  }
}
8.What an RDD is not? {
  - All RDDs are in memory: FALSE
  - Spark creates all RDDs in real: FALSE, Logical plan != Actual execution plan.
}
9.First Program in Spark: {
  --Max volume stocks:
    val stocks = sc.textFile("/user/osboxes/stocks-dataset")
    val splits = stocks.map(record => record.split(",")) - transformation, lazy, does nothing.
    val symvol = splits(arr => (arr(1), arr(7).toInt())  - transformation, lazy, does nothing.
    val maxvol = symvol.reduceByKey((vol1, vol2) => Math.max(vol1, vol2)) - transformation, lazy, does nothing.
    maxvol.collect().foreach(println) - action, starts execution.
  
  --Spark shell
    spark-shell --master spark://node1:7077
    
}
10.What are dependencies & why they are important: {
  - Narrow Dependency VS Wide Dependency: A depends on B
    + Depend on everthing B has: NarrowDep: 
      Child partition depends on ENTIRE parent partition
    + Depend on a portion of B: WideDep
      Child partition depends on a PORTION OF EACH of the parent partitions
  - c = a.cartesian(b);
  - You can still have a narrow dependency even when a child partition has more than one parent partition.
  - As long as the child partition depends on entire parent partitions.
  - Why we have to care about dependencies?
    + Type of dependency determines the number of tasks
    + Type of dependency has an impact on fault tolerance    
}
11.Program to Execution.Part1:{
  - Simple plan:
    + Too many tasks
    + Lot of intermediate data
    + Poor performance
  - Fewer tasks
  - Group all consecutive narrow dev tasks into one: pipeline
    + 2 stages:
      * Stage0: task0, task1, task2, task3
      * Stage1: task4, task5, task6, task7
}
12.Program to Execution.Part2:{
  - Spark Master at spark://192.168.56.101:7077
	Browser	Url: 192.168.56.101:18080
  - Workers, Running Applications, Completed Applications
  - Login to spark shell: 
	spark-shell -- master spark://node1:7077
  - Application contains many Jobs, each of which contains many Stages, 
				    each of which contains many Tasks.
  - 05:30
}
