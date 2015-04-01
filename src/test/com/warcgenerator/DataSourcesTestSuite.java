package com.warcgenerator;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.warcgenerator.datasources.arff.ArffDSTest;
import com.warcgenerator.datasources.csv.CSVDSTest;
import com.warcgenerator.datasources.file.FileDSTest;
import com.warcgenerator.datasources.warc.WarcDSTest;
import com.warcgenerator.datasources.warccsv.WarcCSVDSTest;

@SuiteClasses({ ArffDSTest.class, CSVDSTest.class, WarcDSTest.class,
		WarcCSVDSTest.class, FileDSTest.class })
@RunWith(Suite.class)
public class DataSourcesTestSuite {
}
