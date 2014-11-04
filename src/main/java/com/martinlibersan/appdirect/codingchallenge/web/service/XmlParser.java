package com.martinlibersan.appdirect.codingchallenge.web.service;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Component
public class XmlParser {

	private static final Logger logger = Logger.getLogger(XmlParser.class); 

	public String format(String unformattedXml) {
		String xmlString = "";
		try {
			Document document = parseXmlFromString(unformattedXml);
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			// initialize StreamResult with File object to save to file
			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(document);
			transformer.transform(source, result);
			xmlString = result.getWriter().toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return xmlString;
	}

	public Document parseXmlFromInputSource(InputSource is) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(is);
	}

	public Document parseXmlFromString(String xml) throws ParserConfigurationException, SAXException, IOException {
		InputSource is = new InputSource(new StringReader(xml));
		return parseXmlFromInputSource(is);
	}

}
