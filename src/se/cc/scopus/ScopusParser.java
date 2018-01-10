package se.cc.scopus;

import com.sun.org.apache.xpath.internal.NodeSet;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private XPathExpression standardizedAffiliationsEXp;
    private final Document doc;

    public ScopusParser(Document doc) throws XPathExpressionException {

        this.xpath = XPathFactory.newInstance().newXPath();
        this.doc = doc;


        this.bibliographyExp = this.xpath.compile("/abstracts-retrieval-response/item/bibrecord/tail/bibliography/*");
        this.bibliographySourceTitleExp = this.xpath.compile(".//ref-sourcetitle");


        this.coreDataExp = this.xpath.compile("/abstracts-retrieval-response/coredata/*");
        this.languageExp = this.xpath.compile("/abstracts-retrieval-response/language");
        this.standardizedAffiliationsEXp = this.xpath.compile("/abstracts-retrieval-response/affiliation");
    }

    public List<CitedReference> getCitedReferences() throws XPathExpressionException {


        NodeList nodeList = (NodeList)bibliographyExp.evaluate(this.doc,XPathConstants.NODESET);

        if(nodeList.getLength() == 0) return Collections.emptyList();

        List<CitedReference> citedReferenceList = new ArrayList<>();

        for(int i=0; i<nodeList.getLength(); i++) {



            Node node = nodeList.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE &&  "reference".equals( ((Element)node).getTagName()) )   {

                CitedReference citedReference = new CitedReference();

                Element element = (Element)node;

                String fulltext = (String)xpath.evaluate("ref-fulltext",element,XPathConstants.STRING);
                citedReference.setRefFulltext(fulltext);

                String title = (String)xpath.evaluate("ref-info/ref-title",element,XPathConstants.STRING);
                citedReference.setRefTitle(title);

                NodeList ids = (NodeList)xpath.evaluate("ref-info/refd-itemidlist/*",element,XPathConstants.NODESET);
                for(int j=0; j<ids.getLength(); j++) {

                    Node node1 = ids.item(j);
                    String idtype = ((Element)node1).getAttribute("idtype");

                    if("SGR".equals(idtype)) {
                        citedReference.setRefIdType(idtype);
                        citedReference.setRefID( node1.getTextContent() );
                        break;
                    }
                }


                String sourceTitle = (String)xpath.evaluate("ref-info/ref-sourcetitle",element,XPathConstants.STRING);
                citedReference.setRefSourceTitle(sourceTitle);


                Node refpubYear = (Node)xpath.evaluate("ref-info/ref-publicationyear",element,XPathConstants.NODE);
                citedReference.setPubYear(  ((Element)refpubYear).getAttribute("first") );

                Node firstRefAuthor = (Node)xpath.evaluate("ref-info/ref-authors/author[@seq=1][1]",element,XPathConstants.NODE);


                if(firstRefAuthor != null) {

                    Object indexedName = xpath.evaluate("indexed-name",firstRefAuthor,XPathConstants.STRING);

                    if(  indexedName != null ) citedReference.setFirstAuthor(indexedName.toString());


                } else {

                    //maybe a collaboration?

                    Node firstCollab = (Node)xpath.evaluate("ref-info/ref-authors/collaboration[@seq=1][1]",element,XPathConstants.NODE);

                    if(firstCollab != null) citedReference.setFirstAuthor(firstCollab.getFirstChild().getTextContent());

                }

                citedReferenceList.add(citedReference);
            }



        }


        return citedReferenceList;
    }


    public Record getCoreData() throws XPathExpressionException {

        NodeList nodeList = (NodeList)coreDataExp.evaluate(this.doc,XPathConstants.NODESET);

        System.out.println("Extracting core data");
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

                if(  "source-id".equals( element.getTagName() ) )       record.setSourceId( element.getTextContent()  );
                if(  "citedby-count".equals( element.getTagName() ) )       record.setCitedBy( Integer.valueOf(element.getTextContent() ) );
            }

        }


        //finally add lang that is not part of coredata

        System.out.println("Extracting cited refs");
        List<CitedReference> citedRefs = getCitedReferences();
        record.setCitedReferences( citedRefs );


        Node lang  = (Node)languageExp.evaluate(this.doc,XPathConstants.NODE);
        if(lang != null) record.setLanguage( ((Element)lang).getAttribute("xml:lang") );


        return record;
    }

    public List<StandardizedAffiliation> getStandardizedAffiliations() throws XPathExpressionException {

       NodeList nodeList =  (NodeList)this.standardizedAffiliationsEXp.evaluate(this.doc,XPathConstants.NODESET);
       List<StandardizedAffiliation> standardizedAffiliationList = new ArrayList<>();
       if(nodeList.getLength() == 0) return Collections.emptyList();

       for(int i=0; i<nodeList.getLength(); i++) {

           StandardizedAffiliation standardizedAffiliation = new StandardizedAffiliation();

           Element element =  (Element)nodeList.item(i);
           standardizedAffiliation.setAfid( element.getAttribute("id") );
           Object affiliation = ( xpath.evaluate("affilname",element,XPathConstants.STRING) );
           Object city =( xpath.evaluate("affiliation-city",element,XPathConstants.STRING) );
           Object country =( xpath.evaluate("affiliation-country",element,XPathConstants.STRING) );


           if(affiliation != null) standardizedAffiliation.setAffiliation( affiliation.toString());
           if(city != null) standardizedAffiliation.setAffiliationCity( city.toString());
           if(country != null) standardizedAffiliation.setAffiliationCountry( country.toString());

           standardizedAffiliationList.add(standardizedAffiliation);

       }



        return standardizedAffiliationList;
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

        List<StandardizedAffiliation> test = scopusParser.getStandardizedAffiliations();

        for(StandardizedAffiliation s : test) {

            System.out.println(s);

        }
        //Record record = scopusParser.getCoreData();

        //System.out.println(record.getNrRefs());



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
