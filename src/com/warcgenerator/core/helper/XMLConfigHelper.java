package com.warcgenerator.core.helper;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.WarcDS;

public class XMLConfigHelper {
	private static Logger logger = Logger.getLogger(XMLConfigHelper.class);
	
	public static void configure(String path, AppConfig config) {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(path));

			// normalize text representation
			doc.getDocumentElement().normalize();
			
			config.setCorpusDirPath(getValueFromElement(doc, "corpusDirPath"));
			config.setSpamDirName(getValueFromElement(doc, "spamDirName"));
			config.setHamDirName(getValueFromElement(doc, "hamDirName"));
			config.setDomainsLabeledFileName(
					getValueFromElement(doc, "domainsLabeledFileName"));
			config.setDomainsNotFoundFileName(
					getValueFromElement(doc, "domainsNotFoundFileName"));
			config.setMaxDepthOfCrawling(
					getValueFromElement(doc, "maxDepthOfCrawling"));
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
					
					config.getDataSourceConfigs().add(ds);
				}
			}
		} catch (SAXParseException err) {
			logger.error("** Parsing error" + ", line "
					+ err.getLineNumber() + ", uri " + err.getSystemId());
			logger.error(" " + err.getMessage());

		} catch (SAXException e) {
			Exception x = e.getException();
			((x == null) ? e : x).printStackTrace();

		} catch (Throwable t) {
			t.printStackTrace();
		}
		// System.exit (0);

	}// end of main
	
	
	public static String getValueFromElement(Document doc, String field) {	
		Element element = (Element) doc.getElementsByTagName(field).item(0);
		NodeList textLNList = element.getChildNodes();
		return ((Node)textLNList.item(0)).getNodeValue().trim();
	}
}



