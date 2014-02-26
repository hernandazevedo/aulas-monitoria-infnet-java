

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Parser DOM
 * 
 * @author acavalcanti
 *
 */
public class DOMManager {

    private DocumentBuilder builder;
    
    private Document document;

    /**
     * Construtor da classe
     * 
     * @throws Exception excessao levantada no caso de ocorrer algum erro na inicializacao da classe
     */
    public DOMManager() throws Exception {
        try {
            DocumentBuilderFactory b = DocumentBuilderFactory.newInstance();
            b.setValidating(true);
            builder = b.newDocumentBuilder();
//            DOMErrorHandler errorHandler = new DOMErrorHandler();
//            builder.setErrorHandler(errorHandler);
        } catch (ParserConfigurationException e) {
            throw new Exception(e);
        }
    }
    
    /**
     * Executa o parser do arquivo recebido como parametro e mantem um atributo com uma referencia a ele
     * 
     * @param file arquivo a ser parseado
     * @throws Exception excessao levantada caso ocorra um problema no parser ou o arquivo nao seja encontrado
     */
    public void parser(InputStream  file) throws Exception {
        try {
        document = builder.parse(file);
        } catch(SAXException e) {
            throw new Exception(e);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    /**
     * Executa o parser do arquivo recebido como parametro e mantem um atributo com uma referencia a ele
     * 
     * @param file arquivo a ser parseado
     * @throws Exception excessao levantada caso ocorra um problema no parser ou o arquivo nao seja encontrado
     */
    public void parserFile(File  file) throws Exception {
        try {
        document = builder.parse(file);
        } catch(SAXException e) {
            throw new Exception(e);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }
    
    /**
     * Retorna o documento xml parseado
     * 
     * @return xml parseado
     */
    public Document getDocument() {
        return document;
    }
    
    /**
     * Altera o valor de um atributo de um elemento do documento xml parseado
     * 
     * @param nodeName nome do elemento que contem o atributo
     * @param attribute atributo que tra seu valor alterado
     * @param oldValue valor a ser substituido
     * @param newValue novo valor
     */
    public void updateAttributeValue(String nodeName, String attribute,
            String oldValue, String newValue) {
        NodeList list = document.getElementsByTagName(nodeName);
        Node node = null;
        Node oldAttribute = null;
        Node newAttribute = null;

        for (int i = 0; i < list.getLength(); i++) {
            node = list.item(i);
            oldAttribute = node.getAttributes().getNamedItem(attribute);
            if (oldAttribute != null
                    && oldAttribute.getNodeValue() == oldValue) {
                newAttribute = oldAttribute;
                newAttribute.setNodeValue(newValue);
                replaceNode(oldAttribute, newAttribute);
            }
        }
    }
    
    /**
     * Altera o valor de um elemento do documento xml parseado
     * 
     * @param nodeName nome do elemento a ser alterado
     * @param oldValue valor a ser substuido
     * @param newValue novo valor
     */
    public void updateElementValue(String nodeName, String oldValue,
            String newValue) {
        NodeList list = document.getElementsByTagName(nodeName);
        Node oldNode = null;
        Node newNode = null;

        for (int i = 0; i < list.getLength(); i++) {
            oldNode = list.item(i);
            if(oldNode.getNodeValue() == oldValue) {
                newNode = oldNode;
                newNode.setNodeValue(newValue);
                replaceNode(oldNode, newNode);
            }
        }
    }
    
    private void replaceNode(Node oldNode, Node newNode) {
        Node oldParent = oldNode.getParentNode();
        Node newParent = oldNode.getParentNode().replaceChild(oldNode, newNode);
        oldParent.getOwnerDocument().replaceChild(oldParent, newParent);

    }
}
