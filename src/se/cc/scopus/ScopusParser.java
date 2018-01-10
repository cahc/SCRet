package se.cc.scopus;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;

/**
 * Created by crco0001 on 1/5/2018.
 */
public class ScopusParser {


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


    private final XPath xpath;
    private XPathExpression bibliographyExp;
    private XPathExpression bibliographySourceTitleExp;
    private XPathExpression coreDataExp;
    private XPathExpression languageExp;
    private final Document doc;

    public ScopusParser(Document doc) throws XPathExpressionException {

        this.xpath = XPathFactory.newInstance().newXPath();
        this.doc = doc;


        this.bibliographyExp = this.xpath.compile("/abstracts-retrieval-response/item/bibrecord/tail/bibliography");
              this.bibliographySourceTitleExp = this.xpath.compile(".//ref-sourcetitle");


        this.coreDataExp = this.xpath.compile("/abstracts-retrieval-response/coredata/*");
        this.languageExp = this.xpath.compile("/abstracts-retrieval-response/language");
    }

    public void getCitedReferences() throws XPathExpressionException {


        Node node = (Node)bibliographyExp.evaluate(this.doc,XPathConstants.NODE);

        System.out.println("# of references: " + ((Element)node).getAttribute("refcount") );

        NodeList refs = node.getChildNodes();

       // System.out.println("# of references : " + refs.getLength());

        for(int i=0; i<refs.getLength(); i++) {

            Node ref = refs.item(i);
            Node child = (Node) bibliographySourceTitleExp.evaluate(ref, XPathConstants.NODE);
            System.out.println( ((Element)child).getTextContent() );


        }

    }


    public Record getCoreData() throws XPathExpressionException {

        NodeList nodeList = (NodeList)coreDataExp.evaluate(this.doc,XPathConstants.NODESET);

        System.out.println("# children: " + nodeList.getLength());
        Record record = new Record();

        for(int i=0; i< nodeList.getLength(); i++) {

            Node node = nodeList.item(i);

            if( node.getNodeType() == Node.ELEMENT_NODE ) {


                Element element = (Element)node;
                //System.out.println(element.getTagName());

                if(  "prism:url".equals( element.getTagName() ) )               record.setURL(element.getTextContent() );

                if(  "eid".equals( element.getTagName() ) )                     record.setEid( element.getTextContent() );
                if(  "prism:doi".equals( element.getTagName() ) )               record.setDoi( element.getTextContent() );
                if(  "dc:title".equals( element.getTagName() ) )                record.setTitle( element.getTextContent() );
                if(  "srctype".equals( element.getTagName() ) )                 record.setSrctype( element.getTextContent() );

                if(  "dc:description".equals( element.getTagName() ) )  {

                   Object summary = xpath.evaluate("abstract/para",node,XPathConstants.STRING);

                   if(summary != null)                                        record.setSummaryText((String)summary);
                }


                if(  "prism:publicationName".equals( element.getTagName() ) ) record.setPublicationName( element.getTextContent() );

                if(  "prism:coverDate".equals( element.getTagName() ) )       record.setPublicationYear( Integer.valueOf( element.getTextContent().substring(0,4) ) );

                if(  "source-id".equals( element.getTagName() ) )       record.setSourceId( Long.valueOf(element.getTextContent() ) );
                if(  "citedby-count".equals( element.getTagName() ) )       record.setCitedBy( Integer.valueOf(element.getTextContent() ) );
            }

        }


        //finally add lang that is not part of coredata
        Node lang  = (Node)languageExp.evaluate(this.doc,XPathConstants.NODE);
        if(lang != null) record.setLanguage( ((Element)lang).getAttribute("xml:lang") );


        return record;
    }


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
        Document doc = db.parse( "C:\\Users\\crco0001\\Desktop\\PARSE_SCOPUS\\EXAMPLE3.xml" );


        ScopusParser scopusParser = new ScopusParser(doc);

        Record record = scopusParser.getCoreData();

        System.out.println(record);




        /*
        For debugging:

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

        */



    }


}
