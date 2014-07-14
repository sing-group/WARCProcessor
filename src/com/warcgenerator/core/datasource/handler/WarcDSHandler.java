package com.warcgenerator.core.datasource.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.DataBean;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.datasources.bean.CorpusFile;

public class WarcDSHandler extends DSHandler implements IDSHandler {
	//private List<CorpusFile> corpuses;
	//private CorpusFile currentCorpus;
	
	public WarcDSHandler(IDataSource ds, AppConfig config) {
		super(ds, config);
	
		/*File file = new File(ds.getDataSourceConfig().getFilePath());
		
		corpuses = new ArrayList<CorpusFile>();
		// Read corpus
		for(File f:file.listFiles()) {
			corpuses.add(new CorpusFile(f.getPath())); 
		}*/
	}
	
	/*
	 * Each time it calls this method
	 */
	/*public IDSHandler getFileHandler() {
		IDSHandler dsHandler = null;
		// Check if currently there is some  
		if (currentCorpus == null) {
			// Get a new Corpus
			currentCorpus = corpuses.remove(0);
		}
		
		if (currentCorpus != null) {
			// Read first spam
			if (currentCorpus.getSpam().hasNext()) {
				File f = currentCorpus.getSpam().next();
				DataSourceConfig dsConfig = new DataSourceConfig();
				dsConfig.setFilePath(f.getPath());
				dsConfig.setSpamOrHam(DataSourceConfig.IS_SPAM);
				
				dsHandler = new WarcDSHandler(this.ds,this.config);
			} else if (currentCorpus.getHam().hasNext()) {
				File f = currentCorpus.getHam().next();
				DataSourceConfig dsConfig = new DataSourceConfig();
				dsConfig.setFilePath(f.getPath());
				dsConfig.setSpamOrHam(DataSourceConfig.IS_HAM);
				
				dsHandler = new WarcDSHandler(this.ds,this.config);
			}
		}
		return dsHandler;
	}*/
	
	public void handle(DataBean data) {
	}
}
