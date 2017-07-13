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
