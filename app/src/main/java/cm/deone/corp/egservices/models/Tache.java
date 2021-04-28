package cm.deone.corp.egservices.models;

import java.util.HashMap;
import java.util.Map;

public class Tache {
    private String taId;
    private String taTitre;
    private String taDate;
    private String taDuree;
    private String taDateDebut;
    private String taDateFin;
    private String taDescrption;
    private String taStatus;
    private HashMap<String, Object> taMapEmployer;

    public Tache() {
    }

    public Tache(String taId,
                 String taTitre,
                 String taDate,
                 String taDuree,
                 String taDescrption,
                 String taStatus,
                 HashMap<String, Object> taMapEmployer) {
        this.taId = taId;
        this.taTitre = taTitre;
        this.taDate = taDate;
        this.taDuree = taDuree;
        this.taDescrption = taDescrption;
        this.taStatus = taStatus;
        this.taMapEmployer = taMapEmployer;
    }

    public Tache(String taId,
                 String taTitre,
                 String taDate,
                 String taDuree,
                 String taDateDebut,
                 String taDateFin,
                 String taDescrption,
                 String taStatus,
                 HashMap<String, Object> taMapEmployer) {
        this.taId = taId;
        this.taTitre = taTitre;
        this.taDate = taDate;
        this.taDuree = taDuree;
        this.taDateDebut = taDateDebut;
        this.taDateFin = taDateFin;
        this.taDescrption = taDescrption;
        this.taStatus = taStatus;
        this.taMapEmployer = taMapEmployer;
    }

    public String getTaId() {
        return taId;
    }

    public void setTaId(String taId) {
        this.taId = taId;
    }

    public String getTaTitre() {
        return taTitre;
    }

    public void setTaTitre(String taTitre) {
        this.taTitre = taTitre;
    }

    public String getTaDate() {
        return taDate;
    }

    public void setTaDate(String taDate) {
        this.taDate = taDate;
    }

    public String getTaDuree() {
        return taDuree;
    }

    public void setTaDuree(String taDuree) {
        this.taDuree = taDuree;
    }

    public String getTaDateDebut() {
        return taDateDebut;
    }

    public void setTaDateDebut(String taDateDebut) {
        this.taDateDebut = taDateDebut;
    }

    public String getTaDateFin() {
        return taDateFin;
    }

    public void setTaDateFin(String taDateFin) {
        this.taDateFin = taDateFin;
    }

    public String getTaDescrption() {
        return taDescrption;
    }

    public void setTaDescrption(String taDescrption) {
        this.taDescrption = taDescrption;
    }

    public String getTaStatus() {
        return taStatus;
    }

    public void setTaStatus(String taStatus) {
        this.taStatus = taStatus;
    }

    public HashMap<String, Object> getTaMapEmployer() {
        return taMapEmployer;
    }

    public void setTaMapEmployer(HashMap<String, Object> taMapEmployer) {
        this.taMapEmployer = taMapEmployer;
    }
}
