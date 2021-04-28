package cm.deone.corp.egservices.models;

public class Recu {
    private String reId;
    private String reDate;
    private String reMotif;
    private String reMontant;
    private String reAvance;
    private String reReste;
    private String reDid;
    private String reDmontant;
    private String reUid;
    private String reUnom;
    private String reUavatar;
    private Client reClient;

    public Recu() {
    }

    public Recu(String reId,
                String reDate,
                String reMotif,
                String reMontant,
                String reAvance,
                String reReste,
                String reDid,
                String reDmontant,
                String reUid,
                String reUnom,
                String reUavatar,
                Client reClient) {
        this.reId = reId;
        this.reDate = reDate;
        this.reMotif = reMotif;
        this.reMontant = reMontant;
        this.reAvance = reAvance;
        this.reReste = reReste;
        this.reDid = reDid;
        this.reDmontant = reDmontant;
        this.reUid = reUid;
        this.reUnom = reUnom;
        this.reUavatar = reUavatar;
        this.reClient = reClient;
    }

    public String getReId() {
        return reId;
    }

    public void setReId(String reId) {
        this.reId = reId;
    }

    public String getReDate() {
        return reDate;
    }

    public void setReDate(String reDate) {
        this.reDate = reDate;
    }

    public String getReMotif() {
        return reMotif;
    }

    public void setReMotif(String reMotif) {
        this.reMotif = reMotif;
    }

    public String getReMontant() {
        return reMontant;
    }

    public void setReMontant(String reMontant) {
        this.reMontant = reMontant;
    }

    public String getReAvance() {
        return reAvance;
    }

    public void setReAvance(String reAvance) {
        this.reAvance = reAvance;
    }

    public String getReReste() {
        return reReste;
    }

    public void setReReste(String reReste) {
        this.reReste = reReste;
    }

    public String getReDid() {
        return reDid;
    }

    public void setReDid(String reDid) {
        this.reDid = reDid;
    }

    public String getReDmontant() {
        return reDmontant;
    }

    public void setReDmontant(String reDmontant) {
        this.reDmontant = reDmontant;
    }

    public String getReUid() {
        return reUid;
    }

    public void setReUid(String reUid) {
        this.reUid = reUid;
    }

    public String getReUnom() {
        return reUnom;
    }

    public void setReUnom(String reUnom) {
        this.reUnom = reUnom;
    }

    public String getReUavatar() {
        return reUavatar;
    }

    public void setReUavatar(String reUavatar) {
        this.reUavatar = reUavatar;
    }

    public Client getReClient() {
        return reClient;
    }

    public void setReClient(Client reClient) {
        this.reClient = reClient;
    }
}
