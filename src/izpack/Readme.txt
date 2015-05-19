WARCProcessor Readme
====================

WARCProcessor is a platform independent integrative tool providing
specific support to scientists that need to perform experiments in the
field of web spam research. In detail, the developed application is
specialized in the generation of curated corpus for training and
validation purposes, widely supports the WARC format and allows the
execution of user workflows using both GUI and command line.

WARCProcessor is open-source, being freely available to the scientific
community, provides transparent deployment of new versions and can be
executed on any computer without the need of downloading and installing
additional packages.

Last updates
============

4.2.2_beta
  - Minor bugs
  - Added acceptance test

4.2.1_beta
  - Added a Setup Installer.
  - Bug fix. Bug storing the configuration file through GUI.
 
4.2_beta
  - Add a brief information at the end of the generation process
    through the GUI to sum it up the SPAM/RATIO included in
    the output corpus.
  - Bug fix. Output corpus data is different when the application is
             run several times.
  - Bug fix. If the program detects the desired SPAM/HAM ratio has
    not been successfully fulfilled, it try to do it again by getting
    new URLs from DataSources.
  - Bug fix. WarcCSVDS has been optimizated. 

4.1_beta
  - Added I18n support (English and Spanish).
  - Moved log4j to config folder, to make debug easier.
  - Bug fix. Minor corrections in WarcCSVDS and others.

Licensing
=========

WARCProcessor is published under the terms of the GNU GPL, Version 3.0, 
meaning that you can adapt it to your needs without any constraints.

Some third-party components (e.g., look and feel libraries) may be
released under different terms.

Community
=========

WARCProcessor is part of the SING Group <http://sing.ei.uvigo.es>

* Web site: <http://sing.ei.uvigo.es/warcprocessor>
* Icons: <www.aha-soft.com> - Creative C. A.-Share Alike 3.0 License
