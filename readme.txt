--> Version 2

- Añadido soporte para nuevos formatos de entrada: Arff, CSV, Corpus Warc
- Modificado el fichero de configuracion de la aplicacion para personalizar los
  origenes de datos

Dependencias
-------------------------------------------------------------------------------
# Dependencias WarcProject
commons-beanutils-1.9.2.jar
commons-validator-1.4.0.jar

# Dependencias crawler4j
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

# Dependencias heritrix
# http://sourceforge.net/projects/archive-crawler/files/heritrix3
heritrix-commons-3.1.0.jar
fastutil-5.0.7.jar
commons-io-1.4.jar

# Dependencias Kryo
https://code.google.com/p/kryo/
# Used to serialization for Warc reader
kryo-1.04-all.jar

# Dependencias Weka
# http://weka.wikispaces.com/
weka.jar