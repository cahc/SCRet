package se.cc.scopus;

/**
 * Created by crco0001 on 1/10/2018.
 */
public class Author {

   private  long auid;
   private  int seq;
   private  String initials;
   private  String surnNme;
   private  String giveNname;
   private  long mainAffiliationID;


    public long getAuid() {
        return auid;
    }

    public void setAuid(long auid) {
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

    public long getMainAffiliationID() {
        return mainAffiliationID;
    }

    public void setMainAffiliationID(long mainAffiliationID) {
        this.mainAffiliationID = mainAffiliationID;
    }
}
