package cm.deone.corp.egservices.models;

public class Article {
    private String arId;
    private String arDesignation;
    private String arPrix;
    private String arUnite;
    private String arQuantite;
    private String arCommentaire;
    private String arDate;

    public Article() {
    }

    public Article(String arId,
                   String arDesignation,
                   String arPrix,
                   String arUnite,
                   String arQuantite,
                   String arCommentaire,
                   String arDate) {
        this.arId = arId;
        this.arDesignation = arDesignation;
        this.arPrix = arPrix;
        this.arUnite = arUnite;
        this.arQuantite = arQuantite;
        this.arCommentaire = arCommentaire;
        this.arDate = arDate;
    }

    public String getArId() {
        return arId;
    }

    public void setArId(String arId) {
        this.arId = arId;
    }

    public String getArDesignation() {
        return arDesignation;
    }

    public void setArDesignation(String arDesignation) {
        this.arDesignation = arDesignation;
    }

    public String getArPrix() {
        return arPrix;
    }

    public void setArPrix(String arPrix) {
        this.arPrix = arPrix;
    }

    public String getArUnite() {
        return arUnite;
    }

    public void setArUnite(String arUnite) {
        this.arUnite = arUnite;
    }

    public String getArQuantite() {
        return arQuantite;
    }

    public void setArQuantite(String arQuantite) {
        this.arQuantite = arQuantite;
    }

    public String getArCommentaire() {
        return arCommentaire;
    }

    public void setArCommentaire(String arCommentaire) {
        this.arCommentaire = arCommentaire;
    }

    public String getArDate() {
        return arDate;
    }

    public void setArDate(String arDate) {
        this.arDate = arDate;
    }
}
