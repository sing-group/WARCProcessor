Modo de empleo
=======================
java -jar WarcProject-4.X.X-X.jar [--help] [--nogui] [--config <path>]

--help: Muestra las opciones de arranque.
--nogui: Ejecuta la aplicación sin interface de usuario.
--config <path>: Arranca la aplicación con el fichero de configuración especificado.

Actualizaciones
=======================
--> Versión 4

- Modificaciones en la vista que incluyen accesos directos al directorio
  de entrada del DS y al directorio de salida. Se incluye el
  control por teclado para desplazarse en el árbol del menú y
  un enlace desde el menú principal para crear orígenes de datos.
- Opción de deshabilitar DS a través de la vista
- Corpus a partir de CSV + múltiples WARC ó WARC.gz
- Ejecutar sin guí (Usando GetOpt)
- Filtrar por idioma
- Un simple jar ejecutable con java -jar
- Unificar nombres en Datasource CSV y ARFF
- Incluir en ARFF el atributo SpamValue
- Guardar la configuración con una extensión del programa

--> Versión 3

- Añadido interfaz gráfico.
- Añadido DataSource para directorios Warc (WarcDS). A diferencia de CorpusDS,
este busca en un directorio ficheros .warc, y delega al usuario que configura
el DS la responsabilidad de indicar si los ficheros Warc que encontrará
en dicho directorio son Spam o Ham. 
- (Incidencia Cod. 2.01) Cuando se establece una profuncidad mayor que 0 en
el crawler, los enlaces rastreados sólo serán los que pertenezcan a los
sitios web originales.
- (Incidencia Cod. 2.02) En el DataSource se pueden establecer el
 parámetro “spamCol” con el valor de un campo del CSV que indique si la url
 de esa fila corresponde a un sitio web spam o ham, así pues en el
 parámetro “spamColSpamValue” se puede indicar el valor de ese campo
 que se tomará como spam. Cualquier otro valor corresponderá a ham.
- (Incidencia Cod. 2.03) Corregido. En el DataSource CSVDS se ha
 establecido el parámetro “fieldSeparator” donde se puede configurar el
 caracter que corresponderá al separador de columnas.


--> Version 2

- Añadido soporte para nuevos formatos de entrada: Arff, CSV, Corpus Warc
- Modificado el fichero de configuracion de la aplicacion para personalizar los
  origenes de datos
  

Creditos
=======================
Iconos: www.aha-soft.com - Creative Commons Attribution-Share Alike 3.0 License

Dependencias
=======================
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
# https://code.google.com/p/kryo/
# Used to serialization for Warc reader
kryo-1.04-all.jar

# Dependencias Weka
# http://weka.wikispaces.com/
weka.jar

# Dependencias Lucene
luceneTrigramsLanguageGuesser.jar

# Dependencias Jsoup
# http://jsoup.org/
# Used to extract text from html
jsoup-1.8.1.jar

# Dependencias GetOpt
# www.urbanophile.com/arenn/hacking/download.html
# Used to get options from command line
java-getopt-1.0.13.jar