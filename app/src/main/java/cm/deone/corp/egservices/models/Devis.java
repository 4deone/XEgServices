package cm.deone.corp.egservices.models;

import java.util.List;

public class Devis {
    private String deId;
    private Client deClient;
    private List<Article> deArticleList;
    private String deValidation;
    private String deIntitule;
    private String deMaindoeuvre;
    private String dePrixHT;
    private String deTva;
    private String dePrixTTC;
    private String deDate;
    private String deUid;
    private String deUnom;
    private String deUavatar;

    public Devis() {
    }

    public Devis(String deId,
                 Client deClient,
                 List<Article> deArticleList,
                 String deValidation,
                 String deIntitule,
                 String deMaindoeuvre,
                 String dePrixHT,
                 String deTva,
                 String dePrixTTC,
                 String deDate,
                 String deUid,
                 String deUnom,
                 String deUavatar) {
        this.deId = deId;
        this.deClient = deClient;
        this.deArticleList = deArticleList;
        this.deValidation = deValidation;
        this.deIntitule = deIntitule;
        this.deMaindoeuvre = deMaindoeuvre;
        this.dePrixHT = dePrixHT;
        this.deTva = deTva;
        this.dePrixTTC = dePrixTTC;
        this.deDate = deDate;
        this.deUid = deUid;
        this.deUnom = deUnom;
        this.deUavatar = deUavatar;
    }

    public String getDeId() {
        return deId;
    }

    public void setDeId(String deId) {
        this.deId = deId;
    }

    public Client getDeClient() {
        return deClient;
    }

    public void setDeClient(Client deClient) {
        this.deClient = deClient;
    }

    public List<Article> getDeArticleList() {
        return deArticleList;
    }

    public void setDeArticleList(List<Article> deArticleList) {
        this.deArticleList = deArticleList;
    }

    public String getDeValidation() {
        return deValidation;
    }

    public void setDeValidation(String deValidation) {
        this.deValidation = deValidation;
    }

    public String getDeIntitule() {
        return deIntitule;
    }

    public void setDeIntitule(String deIntitule) {
        this.deIntitule = deIntitule;
    }

    public String getDeMaindoeuvre() {
        return deMaindoeuvre;
    }

    public void setDeMaindoeuvre(String deMaindoeuvre) {
        this.deMaindoeuvre = deMaindoeuvre;
    }

    public String getDePrixHT() {
        return dePrixHT;
    }

    public void setDePrixHT(String dePrixHT) {
        this.dePrixHT = dePrixHT;
    }

    public String getDeTva() {
        return deTva;
    }

    public void setDeTva(String deTva) {
        this.deTva = deTva;
    }

    public String getDePrixTTC() {
        return dePrixTTC;
    }

    public void setDePrixTTC(String dePrixTTC) {
        this.dePrixTTC = dePrixTTC;
    }

    public String getDeDate() {
        return deDate;
    }

    public void setDeDate(String deDate) {
        this.deDate = deDate;
    }

    public String getDeUid() {
        return deUid;
    }

    public void setDeUid(String deUid) {
        this.deUid = deUid;
    }

    public String getDeUnom() {
        return deUnom;
    }

    public void setDeUnom(String deUnom) {
        this.deUnom = deUnom;
    }

    public String getDeUavatar() {
        return deUavatar;
    }

    public void setDeUavatar(String deUavatar) {
        this.deUavatar = deUavatar;
    }
}
