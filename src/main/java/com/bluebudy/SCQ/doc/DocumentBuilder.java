package com.bluebudy.SCQ.doc;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public interface DocumentBuilder {

	String buildDocument()  throws SAXException, IOException, ParserConfigurationException, TransformerException, Exception ;
	
}
