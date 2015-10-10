GEO2CEDAR
=========

Custom utility for converting the metadata in GEO spreadsheets to instances in the CEDAR studies model.

#### Building Prerequisites

To build this library you must have the following items installed:

+ A tool for checking out a [Git](http://git-scm.com/) repository.
+ Apache's [Maven](http://maven.apache.org/index.html).
+ [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

#### Building

Get a copy of the latest code:

    git clone https://github.com/metadatacenter/geo2cedar.git 

Change into the geo2cedar directory:

    cd geo2cedar

Then build it with Maven:

    mvn clean install

On build completion your local Maven repository will contain the generated geo2cedar-${version}.jar file.

