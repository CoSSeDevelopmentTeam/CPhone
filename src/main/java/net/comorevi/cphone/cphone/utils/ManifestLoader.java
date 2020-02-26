package net.comorevi.cphone.cphone.utils;

import net.comorevi.cphone.cphone.application.ApplicationPermission;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class ManifestLoader {

    public static ApplicationManifest loadManifest(InputStream in) {
        try {
            ApplicationManifest manifest = new ApplicationManifest();
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
            NodeList element;

            element = document.getElementsByTagName("application");
            if (element != null) {
                for (int i = 0; i < element.getLength(); i++) {
                    NodeList list = element.item(i).getChildNodes();

                    for (int j = 0; j < list.getLength(); j++) {
                        String text = list.item(j).getTextContent();

                        switch (list.item(j).getNodeName()) {
                            case "title":
                                manifest.setTitle(text);
                                break;

                            case "description":
                                manifest.setDescription(text);
                                break;

                            case "author":
                                manifest.setAuthor(text);
                                break;

                            case "version":
                                manifest.setVersion(text);
                                break;

                            case "mcbeversion":
                                manifest.setMcbeVersion(text);
                                break;

                            case "icon":
                                manifest.setIcon(text);
                                break;

                            case "iconType":
                                manifest.setIconType(text);
                                break;

                            case "initialize":
                                manifest.setInitialize(text);
                                break;

                            case "price":
                                manifest.setPrice(Integer.parseInt(text));
                                break;

                            case "visible":
                                manifest.setVisible(Boolean.parseBoolean(text));
                                break;
                        }
                    }
                }
            }

            element = document.getElementsByTagName("permission");
            if (element != null & element.getLength() > 0 && element.item(0).getAttributes().getLength() > 0) {
                if (element.item(0).getAttributes().item(0).getNodeName().equals("type")) {
                    manifest.setPermission(ApplicationPermission.fromName(element.item(0).getAttributes().item(0).getTextContent()));
                }
            }

            element = document.getElementsByTagName("activity");
            for (int i = 0; i < element.getLength(); i++) {
                NodeList list = element.item(i).getChildNodes();

                for (int j = 0; j < list.getLength(); j++) {
                    String text = list.item(j) != null ? list.item(j).getTextContent() : null;

                    switch (list.item(j).getNodeName()) {
                        case "main":
                            manifest.setMain(text);
                            break;
                    }
                }
            }

            return manifest;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
