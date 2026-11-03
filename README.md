# WARCProcessor

## Requirements

* Java 21 or later
* Maven 3.9 or later, to build from source

## Build

```
mvn clean package
```

This creates the executable jar `target/WARCProcessor-<version>.jar`. The tests
take a few minutes; add `-DskipTests` to skip them.

## Usage
java -jar WARCProcessor-4.X.X-X.jar [--help] [--nogui] [--config <path>]

* --help: Show the startup options
* --nogui: Executes WARCProcessor without gui
* --config <path>: Starts WARCProcessor using the specified configuration file

Logging is configured in `./config/log4j2.xml`, created on the first run. Any
`./config/log4j.xml` from an earlier version is ignored.

### Example

`src/test/resources/config/config_in_sample.wpg` reads the sample URL lists in
`in/` and writes the corpus to `target/sample-out`. Run it from the project
directory. It crawls the listed websites, so it needs internet access:

```
java -jar target/WARCProcessor-<version>.jar --nogui --config src/test/resources/config/config_in_sample.wpg
```

## Updates

### Version 4

* Modifications in the view to include links to the directory of the 
  datasource and to the output dir. A keyboard control is included to 
  move through the menu tree and there is a link from the main menu to create
  datasources
* Datasources can be disables through gui
* A corpus can be created from CSV and multiple WARC or WARC.gz files
* Executing without gui (using GetOpt)
* Filtering data using idiom
* A simple executable that can be executed through java -jar
* Unified property names in CSV and ARFF datasources.
* The SpamValue is now included in ARFF file
* Save the config with a own extension for filenames

### Version 3

* Graphical interface added
* Added DataSource for Wark directories (WarcDS). In contrast to CorpusDS, 
  this datasource search warc files in a directory and let user to indicate whether
  the warc files included in the directory are ham or spam
* Solved issue Cod. 2.01. When the dept is greater than 0 in the crawler, the searched 
  links are the ones that belongs to the original websitees.
* Solved issue Cod. 2.02. In the datasource we should be able to 
  stablish the parameter "spamCol" with the value of a CSV field indicating whether
  the URL of this row belongs to a ham or spam website. Additionally, in the parameter
  "spamColValue" we should speficy the value for the field when the ROW is spam 
  (distinct values will be considered as ham)
* Solved issue Cod. 2.03. In the datasource CSVDS we have included the parameter
 "fieldSeparator" where we can configure the filed used to keep columns separated.

### Version 2

* Added support for new input formats (datasources): Arff, CSV, Corpus Warc
* Modified the configuration file of the application to adjust the Datasources
  

## Third party files

Icons: www.aha-soft.com - Creative Commons Attribution-Share Alike 3.0 License
Thanks for the work.

## Dependencies
```
# Crawler: crawler4j fork, which brings Tika 3, HttpClient 5 and Berkeley DB JE 18.3
# https://github.com/rzo1/crawler4j
de.hs-heilbronn.mi:crawler4j-core:5.1.4
de.hs-heilbronn.mi:crawler4j-frontier-sleepycat:5.1.4
org.apache.httpcomponents.client5:httpclient5:5.6.5
org.apache.httpcomponents.core5:httpcore5:5.4.4
org.apache.httpcomponents.core5:httpcore5-h2:5.4.4

# WARC reader and writer
org.netpreserve.commons:webarchive-commons:3.0.5

# ARFF and CSV datasources
nz.ac.waikato.cms.weka:weka-dev:3.9.7

# Text extraction from html
org.jsoup:jsoup:1.24.1

# Language filter (in lib/)
lucene:language_guesser:1.1

# Command line options
gnu.getopt:java-getopt:1.0.13

# Logging
org.apache.logging.log4j:log4j-api:2.26.1
org.apache.logging.log4j:log4j-core:2.26.1
org.apache.logging.log4j:log4j-slf4j2-impl:2.26.1

# Utilities
com.google.guava:guava:33.8.0-jre
commons-beanutils:commons-beanutils:1.11.0
commons-io:commons-io:2.22.0
commons-validator:commons-validator:1.11.0
org.apache.commons:commons-lang3:3.20.0

# Tests
junit:junit:4.13.2
org.wiremock:wiremock-standalone:3.13.2
```

## License

See LICENSE

## Authors
* Miguel Cayon Álvarez
* Ivan Ferrant
* Rosalía Laza Fidalgo
* Reyes Pavón Rial
* José Ramón Méndez Reboredo
* David Ruano Ordás
* José Ramón Méndez Reboredo
* Florentino Fernández Riverola
