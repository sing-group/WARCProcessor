package com.warcgenerator.core.helper;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.Constants;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.exception.config.ConfigException;
import com.warcgenerator.core.exception.config.ValidateXMLSchemaException;

/**
 * Read the app configuration from xml config
 * 
 * @author Miguel Callon
 *
 */
public class XMLConfigHelper {
	private static Logger logger = Logger.getLogger(XMLConfigHelper.class);
	
	private static void validateSchema(Document document, String schemaFilePath) 
			throws ValidateXMLSchemaException {
		 // create a SchemaFactory capable of understanding WXS schemas
	    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

	    // load a WXS schema, represented by a Schema instance
	    Source schemaFile = new StreamSource(new File(schemaFilePath));
	    Schema schema;
		try {
			schema = factory.newSchema(schemaFile);
		} catch (SAXException e) {
			throw new ValidateXMLSchemaException(e);
		}

	    // create a Validator instance, which can be used to validate an instance document
	    Validator validator = schema.newValidator();

	    // validate the DOM tree
	    try {
			validator.validate(new DOMSource(document));
		} catch (SAXException e) {
			throw new ValidateXMLSchemaException(e);
		} catch (IOException e) {
			throw new ValidateXMLSchemaException(e);
		}
	}
	
	public static void configure(String path, AppConfig config) {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(path));
			
			// At the moment we are not to validate Scheme
			validateSchema(doc, Constants.configSchemaFilePath);
			
			// normalize text representation
			doc.getDocumentElement().normalize();
			
			config.setCorpusDirPath(getValueFromElement(doc, "corpusDirPath"));
			config.setSpamDirName(getValueFromElement(doc, "spamDirName"));
			config.setHamDirName(getValueFromElement(doc, "hamDirName"));
			config.setDomainsLabeledFileName(
					getValueFromElement(doc, "domainsLabeledFileName"));
			config.setDomainsNotFoundFileName(
					getValueFromElement(doc, "domainsNotFoundFileName"));
			String flushOutputDir = getValueFromElement(doc, "flushOutputDir");
			if (flushOutputDir != null) {
				config.setFlushOutputDir(Boolean.valueOf(flushOutputDir));
			}
			config.setMaxDepthOfCrawling(
					getValueFromElement(doc, "maxDepthOfCrawling"));
			config.setNumCrawlers(
					getValueFromElement(doc, "numCrawlers"));
			config.setWebCrawlerTmpStorePath(
					getValueFromElement(doc, "webCrawlerDirTmpStorePath"));
			
			// Read datasources
			NodeList listOfDS = doc.getElementsByTagName("dataSource");
			int totalDS = listOfDS.getLength();
			logger.info("Total no of datasouces : " + totalDS);

			for (int s = 0; s < listOfDS.getLength(); s++) {
				Node dataSourceNode = listOfDS.item(s);
				DataSourceConfig ds = new DataSourceConfig();
				
				if (dataSourceNode.getNodeType() == Node.ELEMENT_NODE) {
					Element dataSourceElement = (Element) dataSourceNode;
					ds.setName(dataSourceElement.getAttribute("name"));
					ds.setDsClassName(dataSourceElement.getAttribute("class"));
			
					// Check if isSpam parameter exists
					ds.setSpamOrHam(false);
					if (dataSourceElement.getAttribute("isSpam") != null) {
						String isSpam = dataSourceElement.getAttribute("isSpam");
						if (isSpam.toLowerCase().equals("true")) {
							ds.setSpamOrHam(true);
						} 
					} 
					
					if (dataSourceElement.getAttribute("maxElements") != null
							&& !dataSourceElement.getAttribute("maxElements").equals("")) {
						String maxElements = dataSourceElement.getAttribute("maxElements");
						// TODO Test if maxElements is a int number
						ds.setMaxElements(Integer.valueOf(maxElements));
					}
					
					//NodeList nodeList = dataSourceElement.getChildNodes();
					NodeList dataSourceInfoNode = dataSourceNode.getChildNodes();
					
					for (int i=0; i < dataSourceInfoNode.getLength(); i++) {
						Node nodeAux = (Node)dataSourceInfoNode.item(i);
						if (nodeAux.getNodeName().equals("customParams")) {
							NodeList customParamsInfoNode = nodeAux.getChildNodes();
							for(int j=0; j < customParamsInfoNode.getLength(); j++) {
								Node nodeCustomParamAux = (Node)customParamsInfoNode.item(j);
								ds.getCustomParams().put(nodeCustomParamAux.getNodeName(),
										nodeCustomParamAux.getTextContent().trim());
							}
						} else if (nodeAux.getNodeName().equals("srcDirPath")) {
							ds.setFilePath(nodeAux.getTextContent().trim());
						} else if (nodeAux.getNodeName().equals("handler")) {
							ds.setHandlerClassName(nodeAux.getTextContent().trim());
						}
					}
					
					logger.info(ds);
					
					config.getDataSourceConfigs().put(ds.getName(), ds);
				}
			}
		} catch (SAXParseException err) {
			logger.error("** Parsing error" + ", line "
					+ err.getLineNumber() + ", uri " + err.getSystemId());
			logger.error(" " + err.getMessage());
			throw new ConfigException(err);

		} catch (SAXException e) {
			throw new ConfigException(e);
		} catch (Throwable t) {
			throw new ConfigException(t);
		}
		// System.exit (0);

	}// end of main
	
	
	/**
	 * Get value from first child node element
	 * @param doc XML document
	 * @param field Field to read
	 * @return Value from the first child node element
	 */
	public static String getValueFromElement(Document doc, String field) {	
		Element element = (Element) doc.getElementsByTagName(field).item(0);
		NodeList textLNList = element.getChildNodes();
		return ((Node)textLNList.item(0)).getNodeValue().trim();
	}
}



