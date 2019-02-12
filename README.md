# WARCProcessor

## Usage
java -jar WarcProject-4.X.X-X.jar [--help] [--nogui] [--config <path>]

* --help: Show the startup options
* --nogui: Executes WARCProcessor without gui
* --config <path>: Starts WARCProcessor using the specified configuration file

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
# WarcProject dependencies
commons-beanutils-1.9.2.jar
commons-validator-1.4.0.jar

# crawler4j dependencies
httpcore-4.2.2.jar
commons-logging-1.1.1.jar
httpclient-4.2.3.jar
commons-codec-1.6.jar
je-4.0.92.jar
tika-parsers-1.0.jar
boilerpipe-1.1.0.jar
tagsoup-1.2.1.jar
metadata-extractor-2.4.0-beta-1.jar
asm-3.1.jar
geronimo-stax-api_1.0_spec-1.0.1.jar
commons-compress-1.3.jar
apache-mime4j-dom-0.7.jar
apache-mime4j-core-0.7.jar
tika-core-1.0.jar
log4j-1.2.14.jar

# heritrix dependencies
# http://sourceforge.net/projects/archive-crawler/files/heritrix3
heritrix-commons-3.1.0.jar
fastutil-5.0.7.jar
commons-io-1.4.jar

# Kryo dependencies
# https://code.google.com/p/kryo/
# Used to serialization for Warc reader
kryo-1.04-all.jar

# Weka dependencies
# http://weka.wikispaces.com/
weka.jar

# Lucene dependencies
luceneTrigramsLanguageGuesser.jar

# Jsoup dependencies
# http://jsoup.org/
# Used to extract text from html
jsoup-1.8.1.jar

# GetOpt dependencies
# www.urbanophile.com/arenn/hacking/download.html
# Used to get options from command line
java-getopt-1.0.13.jar
```

## License

See LICENSE.md

## Authors
* Miguel Cayon Álvarez
* Ivan Ferrant
* Rosalía Laza Fidalgo
* Reyes Pavón Rial
* José Ramón Méndez Reboredo
* David Ruano Ordás
* José Ramón Méndez Reboredo
* Florentino Fernández Riverola
