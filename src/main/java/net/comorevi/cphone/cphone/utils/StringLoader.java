package net.comorevi.cphone.cphone.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class StringLoader {
    public static Map<String, String> loadString(InputStream in) {

        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
            Element element = document.getDocumentElement();
            Map<String, String> languages = new HashMap<>();

            NodeList strings = element.getChildNodes();
            for(int i = 0; i < strings.getLength(); i++) {
                Node stringNode = strings.item(i);
                if(stringNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element stringElement = (Element) stringNode;
                    if(stringElement.getNodeName().equals("string")) {
                        languages.put(stringElement.getAttribute("name"), stringElement.getTextContent());
                    }
                }
            }

            return languages;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
