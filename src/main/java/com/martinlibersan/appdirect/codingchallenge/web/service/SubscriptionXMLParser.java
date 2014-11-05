package com.martinlibersan.appdirect.codingchallenge.web.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.martinlibersan.appdirect.codingchallenge.web.model.Person;

/**
 * Class responsible to parse subscription response from AppDirect
 * and extract user information. 
 *
 */
@Component
public class SubscriptionXMLParser {

	private static final Logger logger = Logger.getLogger(SubscriptionXMLParser.class);

	@Autowired
	private XmlParser xmlParser;

	public Person parse(String xml) throws ParserConfigurationException, SAXException, IOException {
		logger.info("SubscriptionXMLParser parse");
		Document document = xmlParser.parseXmlFromString(xml);

		Map<String, String> marketPlaceMap = parserMarketPlace(document);
		Map<String, String> creatorMap = parserCreator(document);
		Map<String, String> compagnyMap = parserCompagny(document);
		Map<String, String> orderMap = parserOrder(document);

		Person person = new Person();
		person.setOpenId(creatorMap.get("openId"));
		person.setFirstName(creatorMap.get("firstName"));
		person.setLastName(creatorMap.get("lastName"));
		person.setCompagny(compagnyMap.get("name"));
		person.setXml(xml.getBytes("UTF-8"));
		return person;
	}

	protected Map<String, String> parserMarketPlace(Document document) {
		Map<String, String> map = new HashMap<String, String>();
		NodeList nList = document.getElementsByTagName("marketplace");
		Node nNode = nList.item(0);

		if (nNode != null) {

			logger.info("   \n" + nNode.getNodeName());

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				parseAttribute(map, eElement, "baseUrl");
				parseAttribute(map, eElement, "partner");

			}
		}
		return map;
	}

	protected Map<String, String> parserCreator(Document document) {
		Map<String, String> map = new HashMap<String, String>();
		NodeList nList = document.getElementsByTagName("creator");
		Node nNode = nList.item(0);

		if (nNode != null) {
			logger.info("   \n" + nNode.getNodeName());

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				parseAttribute(map, eElement, "firstName");
				parseAttribute(map, eElement, "lastName");
				parseAttribute(map, eElement, "email");
				parseAttribute(map, eElement, "openId");
				parseAttribute(map, eElement, "uuid");
			}
		}
		return map;
	}


	protected Map<String, String> parserCompagny(Document document) {
		Map<String, String> map = new HashMap<String, String>();
		NodeList nList = document.getElementsByTagName("company");
		Node nNode = nList.item(0);

		if (nNode != null) {
			logger.info("   \n" + nNode.getNodeName());

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				parseAttribute(map, eElement, "name");
				parseAttribute(map, eElement, "website");
				parseAttribute(map, eElement, "email");
				parseAttribute(map, eElement, "phoneNumber");
				parseAttribute(map, eElement, "uuid");
			}
		}
		return map;
	}

	protected Map<String, String> parserOrder(Document document) {
		Map<String, String> map = new HashMap<String, String>();
		NodeList nList = document.getElementsByTagName("order");
		Node nNode = nList.item(0);

		if (nNode != null) {
			logger.info("   \n" + nNode.getNodeName());

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				parseAttribute(map, eElement, "editionCode");
			}
		}
		return map;
	}

	protected void parseAttribute(Map<String, String> map, Element eElement, String attributeName) {
		NodeList nElements = eElement.getElementsByTagName(attributeName);
		if (nElements != null) {
			Node nItem = nElements.item(0);
			if (nItem != null) {
				String attributeValue = nItem.getTextContent();
				map.put(attributeName, attributeValue);
				logger.info("      " + attributeName + " : " + attributeValue);
			}
		}
	}

}
