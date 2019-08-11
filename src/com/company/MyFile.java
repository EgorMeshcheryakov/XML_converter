package com.company;

import org.w3c.dom.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.logging.Logger;


public class MyFile {
    private static Logger log = Logger.getLogger(MyFile.class.getName());
    private final LinkedList<Field> fieldList = new LinkedList<>();
    private Document xmlDocument;

    public MyFile() {
    }

    public MyFile(Document xmlDocument) {
        this.xmlDocument = xmlDocument;
    }

    public void open(String pathname) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.xmlDocument = builder.parse(new File(pathname));
        }
        catch(Exception e) {
            log.info(e.getMessage());
        }
    }

    public MyFile toDst() {
        NodeList nodeList = this.xmlDocument.getDocumentElement().getElementsByTagName("Field");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            try {
                Field field = Field.toField(node);
                this.fieldList.add(field);
            }
            catch (MyException e) {
                log.info(e.getMessage());
            }
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document tmpDocument;

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            tmpDocument = builder.newDocument();
        }
        catch(Exception e) {
            log.info(e.getMessage());
            return null;
        }

        Element root = tmpDocument.createElement("data");
        for (Field field : this.fieldList) {
            Element element = tmpDocument.createElement(field.getType().toString().toLowerCase());

            element.setAttribute("name",field.getName());
            if(field.isDigitOnly()) {
                element.setAttribute("digitOnly", "true");
            }
            if(field.isReadOnly()) {
                element.setAttribute("readOnly", "true");
            }
            if(field.isRequired()) {
                element.setAttribute("required", "true");
            }

            if(field.getType().equals(Type.ADDRESS)) {
                String[] addrStrings = field.getValue().toString().split(",");
                element.setAttribute("street", addrStrings[0]);
                element.setAttribute("house", addrStrings[1]);
                element.setAttribute("flat", addrStrings[2]);
            } else if(field.getType().equals(Type.SUM)) {
                element.setAttribute("value", String.format ("%.2f", field.getValue()));
            } else {
                element.setAttribute("value", field.getValue().toString());
            }

            root.appendChild(element);
        }

        tmpDocument.appendChild(root);
        return new MyFile(tmpDocument);
    }

    public String save(String pathname) {
        String xmlString = "";
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            FileOutputStream outputStream = new FileOutputStream(pathname);
            transformer.transform(new DOMSource(this.xmlDocument), new StreamResult(outputStream));
            xmlString = outputStream.toString();
        }
        catch (Exception e) {
            log.info(e.getMessage());
        }

        return xmlString;
    }
}
