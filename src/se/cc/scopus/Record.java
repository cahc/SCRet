package se.cc.scopus;

import java.util.List;

/**
 * Created by crco0001 on 1/10/2018.
 */
public class Record {


    //from <coredata>
    String eid;
    String doi;
    String title;
    String summaryText; //abstract..
    String publicationName;
    String srctype;
    String URL;
    String language;

    String sourceId;
    int citedBy;
    int publicationYear;



    List<Author> authorList;
    List<CitedReference> citedReferences;
    List<AffiliationLevel1> affiliationLevel1List;



    public int getNrRefs() {

        return this.citedReferences.size();
    }

    public int getNrStandardizedAffils() {

        return this.affiliationLevel1List.size();
    }


    public List<AffiliationLevel1> getAffiliationLevel1List() {
        return affiliationLevel1List;
    }

    public void setAffiliationLevel1List(List<AffiliationLevel1> affiliationLevel1List) {
        this.affiliationLevel1List = affiliationLevel1List;
    }

    public List<CitedReference> getCitedReferences() {
        return citedReferences;
    }

    public void setCitedReferences(List<CitedReference> citedReferences) {
        this.citedReferences = citedReferences;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummaryText() {
        return summaryText;
    }

    public void setSummaryText(String summaryText) {
        this.summaryText = summaryText;
    }

    public String getPublicationName() {
        return publicationName;
    }

    public void setPublicationName(String publicationName) {
        this.publicationName = publicationName;
    }

    public String getSrctype() {
        return srctype;
    }

    public void setSrctype(String srctype) {
        this.srctype = srctype;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public int getCitedBy() {
        return citedBy;
    }

    public void setCitedBy(int citedBy) {
        this.citedBy = citedBy;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }


    @Override
    public String toString() {
        return "Record{" +
                "eid='" + eid + '\'' +
                ", doi='" + doi + '\'' +
                ", title='" + title + '\'' +
                ", summaryText='" + summaryText + '\'' +
                ", publicationName='" + publicationName + '\'' +
                ", srctype='" + srctype + '\'' +
                ", URL='" + URL + '\'' +
                ", language='" + language + '\'' +
                ", sourceId=" + sourceId +
                ", citedBy=" + citedBy +
                ", publicationYear=" + publicationYear +
                '}';
    }
}
