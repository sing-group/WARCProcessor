package com.warcgenerator.core.helper;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.DataSourceConfig;

public class XMLConfigHelper {
	public static void configure(String path, AppConfig config) {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			System.out.println("Parse xml: " + path);
			Document doc = docBuilder.parse(new File(path));

			// normalize text representation
			doc.getDocumentElement().normalize();
			System.out.println("Root element of the doc is "
					+ doc.getDocumentElement().getNodeName());

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
			System.out.println("Total no of datasouces : " + totalDS);

			for (int s = 0; s < listOfDS.getLength(); s++) {
				Node dataSourceNode = listOfDS.item(s);
				DataSourceConfig ds = new DataSourceConfig();
				
				if (dataSourceNode.getNodeType() == Node.ELEMENT_NODE) {
					Element dataSourceElement = (Element) dataSourceNode;
					ds.setDsClassName(dataSourceElement.getAttribute("class"));
			
					// Check if isSpam parameter exists
					ds.setSpamOrHam(false);
					if (dataSourceElement.getAttribute("isSpam") != null) {
						System.out.println("isSpam no es nulo");
						String isSpam = dataSourceElement.getAttribute("isSpam");
						
						System.out.println("isSpam es " + isSpam);
						if (isSpam.toLowerCase().equals("true")) {
							ds.setSpamOrHam(true);
							
							System.out.println("isSpam1!!!" + ds.isSpam());
						} 
					} 
					
					
					
					//NodeList nodeList = dataSourceElement.getChildNodes();
					NodeList dataSourceInfoNode = dataSourceNode.getChildNodes();
					ds.setFilePath(((Node)dataSourceInfoNode.item(1)).getTextContent().trim());
					ds.setHandlerClassName(((Node)dataSourceInfoNode.item(3)).getTextContent().trim());
					
					System.out.println("FilePath is " + ds.getFilePath());
					System.out.println("Handler is " + ds.getHandlerClassName());
					
					config.getDataSourceConfigs().add(ds);
				}
			}
		} catch (SAXParseException err) {
			System.out.println("** Parsing error" + ", line "
					+ err.getLineNumber() + ", uri " + err.getSystemId());
			System.out.println(" " + err.getMessage());

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



