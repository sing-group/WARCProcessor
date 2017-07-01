package com.warcgenerator.core.plugin.webcrawler;

import com.warcgenerator.core.config.OutputCorpusConfig;
import com.warcgenerator.core.datasource.IDataSource;

public class WebCrawlerBean {
	private IDataSource domainsSpamDS;
	private IDataSource domainsHamDS;
	private IDataSource domainsLabeledDS;
	private IDataSource domainsNotFoundDS;
	private boolean isSpam;
	private OutputCorpusConfig outputCorpusConfig;
	
	public WebCrawlerBean(IDataSource domainsSpamDS,
						  IDataSource domainsHamDS,
						  IDataSource domainsLabeledDS,
						  IDataSource domainsNotFoundDS,
						  boolean isSpam,
						  OutputCorpusConfig outputCorpusConfig) {
	    this.domainsSpamDS = domainsSpamDS;
	    this.domainsHamDS = domainsHamDS;
		this.domainsLabeledDS = domainsLabeledDS;
		this.domainsNotFoundDS = domainsNotFoundDS;
		this.isSpam = isSpam;
		this.setOutputCorpusConfig(outputCorpusConfig);
	}

    public IDataSource getDomainsSpamDS() {
        return domainsSpamDS;
    }

    public void setDomainsSpamDS(IDataSource domainsSpamDS) {
        this.domainsSpamDS = domainsSpamDS;
    }

    public IDataSource getDomainsHamDS() {
        return domainsHamDS;
    }

    public void setDomainsHamDS(IDataSource domainsHamDS) {
        this.domainsHamDS = domainsHamDS;
    }

    public IDataSource getDomainsLabeledDS() {
		return domainsLabeledDS;
	}
	public void setDomainsLabeledDS(IDataSource domainsLabeledDS) {
		this.domainsLabeledDS = domainsLabeledDS;
	}
	public IDataSource getDomainsNotFoundDS() {
		return domainsNotFoundDS;
	}
	public void setDomainsNotFoundDS(IDataSource domainsNotFoundDS) {
		this.domainsNotFoundDS = domainsNotFoundDS;
	}
	public boolean isSpam() {
		return isSpam;
	}
	public void setSpam(boolean isSpam) {
		this.isSpam = isSpam;
	}

	public OutputCorpusConfig getOutputCorpusConfig() {
		return outputCorpusConfig;
	}

	public void setOutputCorpusConfig(OutputCorpusConfig outputCorpusConfig) {
		this.outputCorpusConfig = outputCorpusConfig;
	}
}
