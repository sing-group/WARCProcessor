package com.warcgenerator.core.helper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

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
import com.warcgenerator.core.config.CustomParamConfig;
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
		SchemaFactory factory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		// load a WXS schema, represented by a Schema instance
		Source schemaFile = new StreamSource(new File(schemaFilePath));
		Schema schema;
		try {
			schema = factory.newSchema(schemaFile);
		} catch (SAXException e) {
			throw new ValidateXMLSchemaException(e);
		}

		// create a Validator instance, which can be used to validate an
		// instance document
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

	public static void getAppConfigFromXml(String path, AppConfig config) {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(path));

			// At the moment we are not to validate Scheme
			validateSchema(doc, Constants.configSchemaFilePath);

			// normalize text representation
			doc.getDocumentElement().normalize();

			config.setNumSites(Integer.parseInt(getValueFromElement(doc, "numSites")));
			config.setOnlyActiveSites(Boolean.valueOf(
					getAttributeFromElement(doc, "numSites",
					"onlyActiveSites")));
			config.setDownloadAgain(Boolean.valueOf(
					getAttributeFromElement(doc, "numSites",
					"downloadAgain")));
			config.setRatioIsPercentage(Boolean.valueOf(
					getAttributeFromElement(doc, "ratio",
					"isPercentage")));
			NodeList listRatio = doc.getElementsByTagName("ratio").
					item(0).getChildNodes();
			
			for (int s = 0; s < listRatio.getLength(); s++) {
				Node nodeAux = listRatio.item(s);
				if (nodeAux.getNodeType() == Node.ELEMENT_NODE) {
					System.out.println("nodename es " + nodeAux.getNodeName());
					if (nodeAux.getNodeName().equals("spam")) {
						config.setRatioSpam(Integer.parseInt(
								nodeAux.getTextContent().trim()));
					} else if (nodeAux.getNodeName().equals("ham")) {
						config.setRatioHam(Integer.parseInt(
								nodeAux.getTextContent().trim()));
					}
				}
			}

			config.setCorpusDirPath(getValueFromElement(doc, "corpusDirPath"));
			config.setSpamDirName(getValueFromElement(doc, "spamDirName"));
			config.setHamDirName(getValueFromElement(doc, "hamDirName"));
			config.setDomainsLabeledFileName(getValueFromElement(doc,
					"domainsLabeledFileName"));
			config.setDomainsNotFoundFileName(getValueFromElement(doc,
					"domainsNotFoundFileName"));
			String flushOutputDir = getValueFromElement(doc, "flushOutputDir");
			if (flushOutputDir != null) {
				config.setFlushOutputDir(Boolean.valueOf(flushOutputDir));
			}
			config.setMaxDepthOfCrawling(Integer.parseInt(getValueFromElement(doc,
					"maxDepthOfCrawling")));
			config.setNumCrawlers(Integer.parseInt(getValueFromElement(doc,
					"numCrawlers")));
			config.setWebCrawlerTmpStorePath(getValueFromElement(doc,
					"webCrawlerDirTmpStorePath"));

			// Read datasources
			NodeList listOfDS = doc.getElementsByTagName("dataSource");
			int totalDS = listOfDS.getLength();
			logger.info("Total no of datasouces : " + totalDS);

			for (int s = 0; s < listOfDS.getLength(); s++) {
				Node dataSourceNode = listOfDS.item(s);
				DataSourceConfig ds = new DataSourceConfig();
				ds.setId(DataSourceConfig.getNextId());
				
				if (dataSourceNode.getNodeType() == Node.ELEMENT_NODE) {
					Element dataSourceElement = (Element) dataSourceNode;
					ds.setName(dataSourceElement.getAttribute("name"));
					ds.setDsClassName(dataSourceElement.getAttribute("class"));

					// Check if isSpam parameter exists
					ds.setSpamOrHam(false);
					if (dataSourceElement.getAttribute("isSpam") != null) {
						String isSpam = dataSourceElement
								.getAttribute("isSpam");
						if (isSpam.toLowerCase().equals("true")) {
							ds.setSpamOrHam(true);
						}
					}

					if (dataSourceElement.getAttribute("maxElements") != null
							&& !dataSourceElement.getAttribute("maxElements")
									.equals("")) {
						String maxElements = dataSourceElement
								.getAttribute("maxElements");
						// TODO Test if maxElements is a int number
						ds.setMaxElements(Integer.valueOf(maxElements));
					}

					// NodeList nodeList = dataSourceElement.getChildNodes();
					NodeList dataSourceInfoNode = dataSourceNode
							.getChildNodes();

					for (int i = 0; i < dataSourceInfoNode.getLength(); i++) {
						Node nodeAux = (Node) dataSourceInfoNode.item(i);
						if (nodeAux.getNodeName().equals("customParams")) {
							NodeList customParamsInfoNode = nodeAux
									.getChildNodes();
							for (int j = 0; j < customParamsInfoNode
									.getLength(); j++) {
								Node nodeCustomParamAux = (Node) customParamsInfoNode
										.item(j);
								
								CustomParamConfig customParam =
										new CustomParamConfig();
								customParam.setName(nodeCustomParamAux.getNodeName());
								customParam.setValue(nodeCustomParamAux.getTextContent()
												.trim());
								// Caution!! We are not getting the defaultValue
								// and type because already know it of datasources.xml
			
								ds.getCustomParams().put(
										nodeCustomParamAux.getNodeName(),
										customParam);
							}
						} else if (nodeAux.getNodeName().equals("srcDirPath")) {
							ds.setFilePath(nodeAux.getTextContent().trim());
						} else if (nodeAux.getNodeName().equals("handler")) {
							ds.setHandlerClassName(nodeAux.getTextContent()
									.trim());
						}
					}

					logger.info(ds);

					config.getDataSourceConfigs().put(ds.getId(), ds);
				}
			}
		} catch (SAXParseException err) {
			logger.error("** Parsing error" + ", line " + err.getLineNumber()
					+ ", uri " + err.getSystemId());
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
	 * Get information about of datasources available
	 * 
	 * @param path
	 * @param config
	 */
	public static void getDataSources(String path,
			Map<String, DataSourceConfig> dataSourcesTypes)
			throws ConfigException {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(path));

			// normalize text representation
			doc.getDocumentElement().normalize();

			NodeList listOfDS = doc.getElementsByTagName("dataSource");
			int totalDS = listOfDS.getLength();

			System.out.println("path es: " + path);
			
			System.out.println("total ds: " + totalDS);
			
			System.out.println("dataSourcesTypes es: " + dataSourcesTypes);
			
			for (int s = 0; s < listOfDS.getLength(); s++) {
				Node dataSourceNode = listOfDS.item(s);
				DataSourceConfig ds = new DataSourceConfig();

				if (dataSourceNode.getNodeType() == Node.ELEMENT_NODE) {
					Element dataSourceElement = (Element) dataSourceNode;
					ds.setName(dataSourceElement.getAttribute("name"));
					ds.setDsClassName(dataSourceElement.getAttribute("class"));

					System.out.println("name es " + ds.getName());
					System.out.println("class " + ds.getDsClassName());
					
					// NodeList nodeList = dataSourceElement.getChildNodes();
					NodeList dataSourceInfoNode = dataSourceNode
							.getChildNodes();

					for (int i = 0; i < dataSourceInfoNode.getLength(); i++) {
						Node nodeAux = (Node) dataSourceInfoNode.item(i);
						if (nodeAux.getNodeName().equals("customParams")) {
							NodeList customParamsInfoNode = nodeAux
									.getChildNodes();
							for (int j = 0; j < customParamsInfoNode
									.getLength(); j++) {
								Node nodeCustomParamAux = (Node) customParamsInfoNode
										.item(j);
								
								if (nodeCustomParamAux.getNodeType() == Node.ELEMENT_NODE) {
									// Extract the type of the parameter
									Element nodeElement = (Element) nodeCustomParamAux;
									
									
									CustomParamConfig customParam =
											new CustomParamConfig();
									customParam.setName(nodeCustomParamAux.getNodeName());
									customParam.setType(nodeElement.getAttribute("type"));
									customParam.setValue(nodeCustomParamAux.getTextContent()
													.trim());
									customParam.setDefaultValue(nodeCustomParamAux.
											getTextContent().trim());
									
									ds.getCustomParams().put(
											nodeCustomParamAux.getNodeName(),
											customParam);
								}
							}
						} else if (nodeAux.getNodeName().equals("handler")) {
							ds.setHandlerClassName(nodeAux.getTextContent()
									.trim());
						}
					}
				}
				dataSourcesTypes.put(ds.getName(), ds);
				logger.info("Reading data source types");
				logger.info(ds);
			}

		} catch (SAXParseException err) {
			logger.error("** Parsing error" + ", line " + err.getLineNumber()
					+ ", uri " + err.getSystemId());
			logger.error(" " + err.getMessage());
			throw new ConfigException(err);

		} catch (SAXException e) {
			throw new ConfigException(e);
		} catch (Throwable t) {
			
			t.printStackTrace();
			throw new ConfigException(t);
		}
	}

	/**
	 * Get value from first child node element
	 * 
	 * @param doc
	 *            XML document
	 * @param field
	 *            Field to read
	 * @return Value from the first child node element
	 */
	public static String getValueFromElement(Document doc, String field) {
		Element element = (Element) doc.getElementsByTagName(field).item(0);
		NodeList textLNList = element.getChildNodes();
		return ((Node) textLNList.item(0)).getNodeValue().trim();
	}
	
	/**
	 * Get value from an attribute of a field
	 * @param doc
	 * @param field
	 * @param attrName
	 * @return
	 */
	public static String getAttributeFromElement(Document doc, 
			String field, String attrName) {
		Element element = (Element) doc.getElementsByTagName(field).item(0);
		return element.getAttribute(attrName);
	}
}
