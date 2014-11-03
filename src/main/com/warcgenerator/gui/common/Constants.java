package com.warcgenerator.gui.common;

public interface Constants {
	String APP_NAME = "WARC Processor";
	String APP_VERSION = "4.0";
	
	String DATASOURCE_FORM_SESSION_KEY = "datasource_form_session_key";
	String GUI_CONFIG_SESSION_KEY = "gui_config_session_key";
	String DEFAULT_GUI_CONFIG_XML= "/config/gui-config-default.xml";
	String DEFAULT_DIR_CUSTOM_GUI_CONFIG_XML = "./config/";
	String CUSTOM_GUI_CONFIG_XML = "gui-config.xml";
	String CONFIG_SCHEMA_FILE_PATH = "/config/schema/gui-config.xsd";
	String FORM_MODIFIED_SESSION_KEY = "form_modified_session_key";
	Integer NUM_MAX_RECENT_CONFIG_FILES = 4;
}
