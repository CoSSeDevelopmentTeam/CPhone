package net.comorevi.cphone.cphone.utils;

import net.comorevi.cphone.cphone.application.ApplicationPermission;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.exception.ManifestException;
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
                if (element.item(0).getAttributes().getNamedItem("type") != null) {
                    manifest.setPermission(ApplicationPermission.fromName(element.item(0).getAttributes().getNamedItem("type").getTextContent()));
                } else {
                    throw new ManifestException("[" + manifest.getTitle() + "] Attribute \"type\" in /permission must not be null.");
                }
            }

            if (manifest.getPermission() == null) {
                throw new ManifestException("[" + manifest.getTitle() + "] /permission must not be null.");
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

            if (manifest.getMain() == null) {
                throw new ManifestException("[" + manifest.getTitle() + "] /activity/main must not be null.");
            }

            // readable code...
            element = document.getElementsByTagName("external-lang");
            if (element != null) {
                for (int i = 0; i < element.getLength(); i++) {
                    NodeList list = element.item(i).getChildNodes();
                    for (int k = 0; k < list.getLength(); k++) {
                        if (list.item(k).getNodeName().equals("lang")) {
                            if (list.item(k).getAttributes().getNamedItem("region") == null) {
                                throw new ManifestException("[" + manifest.getTitle() + "] Attribute \"region\" in /external-lang/lang must not be null.");
                            }

                            NodeList list2 = list.item(k).getChildNodes();
                            String region = list.item(k).getAttributes().getNamedItem("region").getTextContent();

                            for (int j = 0; j < list2.getLength(); j++) {
                                String text = list2.item(j) != null ? list2.item(j).getTextContent() : null;

                                switch (list2.item(j).getNodeName()) {
                                    case "title":
                                        manifest.addTitle(region, text);
                                        break;

                                    case "description":
                                        manifest.addDescription(region, text);
                                        break;
                                }
                            }
                        }
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
