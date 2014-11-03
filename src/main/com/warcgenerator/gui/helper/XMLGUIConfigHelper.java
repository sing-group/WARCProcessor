package com.warcgenerator.gui.helper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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

import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.config.GUIConfig;
import com.warcgenerator.gui.exception.config.ConfigException;
import com.warcgenerator.gui.exception.config.ValidateXMLSchemaException;

/**
 * Read the app configuration from xml config
 * 
 * @author Miguel Callon
 * 
 */
public class XMLGUIConfigHelper {
	private static Logger logger = Logger.getLogger(XMLGUIConfigHelper.class);

	private static void validateSchema(Document document, String schemaFilePath)
			throws ValidateXMLSchemaException {
		// create a SchemaFactory capable of understanding WXS schemas
		SchemaFactory factory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		// load a WXS schema, represented by a Schema instance
		InputStream is = factory.getClass().getResourceAsStream(schemaFilePath);
		Source schemaFile = new StreamSource(is);
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

	public static void getGUIConfigFromXml(InputStream is, GUIConfig config) {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(is);

			validateSchema(doc, Constants.CONFIG_SCHEMA_FILE_PATH);

			// normalize text representation
			doc.getDocumentElement().normalize();

			NodeList recentConfigFiles = doc.getElementsByTagName(
					"recentConfigFiles").item(0).getChildNodes();

			for (int s = 0; s < recentConfigFiles.getLength(); s++) {
				Node nodeAux = recentConfigFiles.item(s);
				if (nodeAux.getNodeType() == Node.ELEMENT_NODE) {
					if (nodeAux.getNodeName().equals("configFile")) {
						config.addRecentConfigFile(nodeAux
								.getTextContent().trim());
					} 
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
		
	}// end of main

	
	/**
	 * Return a XML with config
	 * 
	 * @param config
	 * @return
	 */
	public static void saveXMLFromGUIConfig(String path, 
			GUIConfig config) {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder;
		Document doc = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();

			// root elements
			doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("gui-configuration");
			doc.appendChild(rootElement);

			Element recentConfigFiles = doc.createElement("recentConfigFiles");
			rootElement.appendChild(recentConfigFiles);

			for (String configFilePath:config.getRecentConfigFiles()) {
				Element configFile = doc.createElement("configFile");
				configFile.setTextContent(configFilePath);
				recentConfigFiles.appendChild(configFile);
			}
				
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			
			// At the moment we are not to validate Scheme
			//validateSchema(doc, Constants.configSchemaFilePath);

			// normalize text representation
			doc.getDocumentElement().normalize();
			
			StreamResult result = new StreamResult(new File(path));

			transformer.transform(source, result);

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	 * 
	 * @param doc
	 * @param field
	 * @param attrName
	 * @return
	 */
	public static String getAttributeFromElement(Document doc, String field,
			String attrName) {
		Element element = (Element) doc.getElementsByTagName(field).item(0);
		return element.getAttribute(attrName);
	}
}