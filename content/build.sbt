name := "content"
version := "0.1"
scalaVersion := "2.11.12"
val sparkVersion = "2.4.5"

libraryDependencies += "org.apache.spark" % "spark-core_2.11" % sparkVersion
libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % sparkVersion