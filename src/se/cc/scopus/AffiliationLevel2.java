package se.cc.scopus;

import java.util.List;

/**
 * Created by crco0001 on 1/11/2018.
 */
public class AffiliationLevel2 {

    String afid;
    String dptid;
    List<String> organisations;
    String city;
    String country;


    public String getAfid() {
        return afid;
    }

    public void setAfid(String afid) {
        this.afid = afid;
    }

    public String getDptid() {
        return dptid;
    }

    public void setDptid(String dptid) {
        this.dptid = dptid;
    }

    public List<String> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(List<String> organisations) {
        this.organisations = organisations;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    @Override
    public String toString() {
        return "AffiliationLevel2{" +
                "afid='" + afid + '\'' +
                ", dptid='" + dptid + '\'' +
                ", organisations=" + organisations +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
