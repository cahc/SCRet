package se.cc.scopus;

import java.util.List;

/**
 * Created by crco0001 on 1/10/2018.
 */
public class Author {

   private  String auid;
   private  int seq;
   private  String initials;
   private  String surnNme;
   private  String giveNname;

   private List<AffiliationLevel2> affiliationsLevel2;


    public String getAuid() {
        return auid;
    }

    public void setAuid(String auid) {
        this.auid = auid;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getSurnNme() {
        return surnNme;
    }

    public void setSurnNme(String surnNme) {
        this.surnNme = surnNme;
    }

    public String getGiveNname() {
        return giveNname;
    }

    public void setGiveNname(String giveNname) {
        this.giveNname = giveNname;
    }

    public List<AffiliationLevel2> getAffiliationsLevel2() {
        return affiliationsLevel2;
    }

    public void setAffiliationsLevel2(List<AffiliationLevel2> affiliationsLevel2) {
        this.affiliationsLevel2 = affiliationsLevel2;
    }


    @Override
    public String toString() {
        return "Author{" +
                "auid=" + auid +
                ", seq=" + seq +
                ", initials='" + initials + '\'' +
                ", surnNme='" + surnNme + '\'' +
                ", giveNname='" + giveNname + '\'' +
                ", affiliationsLevel2=" + affiliationsLevel2 +
                '}';
    }
}
