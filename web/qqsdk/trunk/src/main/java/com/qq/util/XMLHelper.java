package com.qq.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class XMLHelper {

    private static XMLHelper xmlhelper = new XMLHelper();

    private XMLHelper() {
    }

    public static XMLHelper getInstance() {
        return xmlhelper;
    }

    public HashMap<String, String> getInfo(String xml) throws ParserConfigurationException, SAXException, IOException {

        HashMap<String, String> info = new HashMap<String, String>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documnet = builder.parse(readAsInputStream(xml));
        Element root = documnet.getDocumentElement();
        NodeList nl = root.getChildNodes();
        int length = nl.getLength();
        Node node = null;
        for (int i = 0; i < length; i++) {
            node = nl.item(i);
            if (node.getNodeType() == Document.ELEMENT_NODE) {
                info.put(node.getNodeName(), node.getTextContent());
            }
        }

        return info;
    }

    public InputStream readAsInputStream(String str) {
        ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
        return stream;
    }
}
