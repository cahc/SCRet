package se.cc.scopus;

/**
 * Created by crco0001 on 1/10/2018.
 */
public class StandardizedAffiliation {


    String afid;
    String affiliation;
    String affiliationCity;
    String affiliationCountry;


    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getAfid() {
        return afid;
    }

    public void setAfid(String afid) {
        this.afid = afid;
    }

    public String getAffiliationCity() {
        return affiliationCity;
    }

    public void setAffiliationCity(String affiliationCity) {
        this.affiliationCity = affiliationCity;
    }

    public String getAffiliationCountry() {
        return affiliationCountry;
    }

    public void setAffiliationCountry(String affiliationCountry) {
        this.affiliationCountry = affiliationCountry;
    }


    @Override
    public String toString() {
        return "StandardizedAffiliation{" +
                "afid='" + afid + '\'' +
                ", affiliation='" + affiliation + '\'' +
                ", affiliationCity='" + affiliationCity + '\'' +
                ", affiliationCountry='" + affiliationCountry + '\'' +
                '}';
    }
}
