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
