GEO Ingestor
============

Custom utility for converting the metadata in [GEOmedatdb](http://gbnci.abcc.ncifcrf.gov/geo/) 
to instances of the CEDAR Investigation Model.

#### Building

To build this library you must have the following items installed:

+ [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
+ A tool for checking out a [Git](http://git-scm.com/) repository.
+ Apache's [Maven](http://maven.apache.org/index.html).

Get a copy of the latest code:

    git clone https://github.com/metadatacenter/geo-ingestor.git 

Change into the geo-ingestor directory:

    cd geo-ingestor

Then build it with Maven:

    mvn clean install

On build completion your local Maven repository will contain the generated ```geo-ingestor-${version}.jar``` and a fat JAR called geo-ingestor-${version}-jar-with-dependencies.jar.

#### Running


The latest ```GEOmetadb.sqlite``` should first be downloaded.

To run using the fat JAR:

```
   java -jar ./target/geo-ingestor-${version}-jar-with-dependencies.jar <GEOmetadb Database File> <CEDAR JSON Instances Output Directory> <Start Series Index> <Number of Series>
```

A class called ```GEOmetadb2GEOFlat``` produces a simplified flat CEDAR templates that can be used for indexing purposes.

To run ```GEOmetadb2GEOFlat``` with Maven:

```
   mvn exec:java -Dexec.args="<GEOmetadb Database File> <CEDAR JSON Instances Output Directory> <Start Series Index> <Number of Series>"
```

A small script to process 60,000 series could look as follows:


```
for ((i=0;i<=60000;i=i+1000));  
do 
mvn exec:java -Dexec.args="GEOmetadb.sqlite /tmp/geoo $i 1000"; 
done
```
