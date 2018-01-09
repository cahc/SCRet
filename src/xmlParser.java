import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;

/**
 * Created by crco0001 on 1/5/2018.
 */
public class xmlParser {


    /*

    public static final short ELEMENT_NODE              = 1;
    public static final short ATTRIBUTE_NODE            = 2
    public static final short TEXT_NODE                 = 3;
    public static final short CDATA_SECTION_NODE        = 4;
    public static final short ENTITY_REFERENCE_NODE     = 5;
    public static final short ENTITY_NODE               = 6;
    public static final short PROCESSING_INSTRUCTION_NODE = 7;
    public static final short COMMENT_NODE              = 8;
    public static final short DOCUMENT_NODE             = 9;
    public static final short DOCUMENT_TYPE_NODE        = 10;
    public static final short DOCUMENT_FRAGMENT_NODE    = 11;
    public static final short NOTATION_NODE             = 12;

     */


    static void dump(Element e) {

        System.out.printf("Element: %s, %s, %s, %s%n", e.getNodeName(), e.getLocalName(), e.getPrefix(), e.getNamespaceURI());

        NamedNodeMap nnm = e.getAttributes();
        if (nnm != null)
            for (int i = 0; i < nnm.getLength(); i++) {
                Node node = nnm.item(i);
                Attr attr = e.getAttributeNode(node.getNodeName());
                System.out.printf("  Attribute %s = %s%n", attr.getName(), attr.getValue());
            }
        NodeList nl = e.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element)
                dump((Element) node);
        }
    }




    public static void main(String[] arg) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {


        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
       // documentBuilderFactory.setNamespaceAware(true); //TODO hmm...
        DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();
        Document doc = db.parse( "C:\\Users\\crco0001\\Desktop\\PARSE_SCOPUS\\EXAMPLE1.xml" );


        String expression = "/abstracts-retrieval-response/item/bibrecord/tail/bibliography";
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList)xPath.evaluate(expression,doc,XPathConstants.NODESET);       // NodeList nodeList2 = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

        Node citedRefnode = nodes.item(0);

        System.out.println("# of references: " + ((Element)citedRefnode).getAttribute("refcount") );

        dump( (Element) citedRefnode );

        System.exit(0);

        System.out.println("Endcoding: " + doc.getXmlEncoding() );

        if(doc.hasChildNodes() ) {

            NodeList nodeList = doc.getChildNodes();
            System.out.println("#Child nodes  :" + nodeList.getLength() ) ;
            Node node = nodeList.item(0);
            System.out.println(node.getNodeName() + " : type: " + node.getNodeType() );

            if(node.getNodeType() == Node.ELEMENT_NODE) {

                dump( (Element)node );

            }


        }



        NodeList list = doc.getElementsByTagNameNS("http://www.elsevier.com/xml/ani/common","para");

        System.out.println("What now: " + list.getLength());
        System.out.println( ((Element)list.item(0)).getTextContent() );
        System.out.println( ((Element)list.item(1)).getTextContent() );
        System.out.println("");





    }


}
