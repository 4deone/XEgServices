package cm.deone.corp.egservices.models;

public class Employer {
    private String emId;
    private String emNom;
    private String emTelephone;
    private String emAvatar;
    private String emEmail;
    private String emCni;
    private String emPoste;
    private String emQuartier;
    private String emUid;
    private String emDate;

    public Employer() {
    }

    public Employer(String emId, String emNom) {
        this.emId = emId;
        this.emNom = emNom;
    }

    public Employer(String emId,
                    String emNom,
                    String emTelephone,
                    String emAvatar,
                    String emEmail,
                    String emCni,
                    String emPoste,
                    String emQuartier,
                    String emUid,
                    String emDate) {
        this.emId = emId;
        this.emNom = emNom;
        this.emTelephone = emTelephone;
        this.emAvatar = emAvatar;
        this.emEmail = emEmail;
        this.emCni = emCni;
        this.emPoste = emPoste;
        this.emQuartier = emQuartier;
        this.emUid = emUid;
        this.emDate = emDate;
    }

    public String getEmId() {
        return emId;
    }

    public void setEmId(String emId) {
        this.emId = emId;
    }

    public String getEmNom() {
        return emNom;
    }

    public void setEmNom(String emNom) {
        this.emNom = emNom;
    }

    public String getEmTelephone() {
        return emTelephone;
    }

    public void setEmTelephone(String emTelephone) {
        this.emTelephone = emTelephone;
    }

    public String getEmAvatar() {
        return emAvatar;
    }

    public void setEmAvatar(String emAvatar) {
        this.emAvatar = emAvatar;
    }

    public String getEmEmail() {
        return emEmail;
    }

    public void setEmEmail(String emEmail) {
        this.emEmail = emEmail;
    }

    public String getEmCni() {
        return emCni;
    }

    public void setEmCni(String emCni) {
        this.emCni = emCni;
    }

    public String getEmPoste() {
        return emPoste;
    }

    public void setEmPoste(String emPoste) {
        this.emPoste = emPoste;
    }

    public String getEmQuartier() {
        return emQuartier;
    }

    public void setEmQuartier(String emQuartier) {
        this.emQuartier = emQuartier;
    }

    public String getEmUid() {
        return emUid;
    }

    public void setEmUid(String emUid) {
        this.emUid = emUid;
    }

    public String getEmDate() {
        return emDate;
    }

    public void setEmDate(String emDate) {
        this.emDate = emDate;
    }
}
