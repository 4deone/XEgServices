package cm.deone.corp.egservices.models;

public class Client {
    private String cId;
    private String cNom;
    private String cTelephone;
    private String cQuartier;
    private String cCommentaire;
    private String cDate;
    private String cNdevis;
    private String cNrecu;
    private String cNtache;

    public Client() {
    }

    public Client(String cId,
                  String cNom,
                  String cTelephone,
                  String cQuartier,
                  String cCommentaire,
                  String cDate,
                  String cNdevis,
                  String cNrecu,
                  String cNtache) {
        this.cId = cId;
        this.cNom = cNom;
        this.cTelephone = cTelephone;
        this.cQuartier = cQuartier;
        this.cCommentaire = cCommentaire;
        this.cDate = cDate;
        this.cNdevis = cNdevis;
        this.cNrecu = cNrecu;
        this.cNtache = cNtache;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getcNom() {
        return cNom;
    }

    public void setcNom(String cNom) {
        this.cNom = cNom;
    }

    public String getcTelephone() {
        return cTelephone;
    }

    public void setcTelephone(String cTelephone) {
        this.cTelephone = cTelephone;
    }

    public String getcQuartier() {
        return cQuartier;
    }

    public void setcQuartier(String cQuartier) {
        this.cQuartier = cQuartier;
    }

    public String getcCommentaire() {
        return cCommentaire;
    }

    public void setcCommentaire(String cCommentaire) {
        this.cCommentaire = cCommentaire;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }

    public String getcNdevis() {
        return cNdevis;
    }

    public void setcNdevis(String cNdevis) {
        this.cNdevis = cNdevis;
    }

    public String getcNrecu() {
        return cNrecu;
    }

    public void setcNrecu(String cNrecu) {
        this.cNrecu = cNrecu;
    }

    public String getcNtache() {
        return cNtache;
    }

    public void setcNtache(String cNtache) {
        this.cNtache = cNtache;
    }
}
