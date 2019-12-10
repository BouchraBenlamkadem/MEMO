package com.dev.bbl.memo;

public class memo {

    private int id;
    private String titre;
    private String date;//et heure
    private int favorit;
    private String protection;
    private String categorie;
    private String contenu;

    public memo(int id, String titre, String date, int favorit, String protection, String  categorie, String contenu) {
        this.id=id;
        this.titre = titre;
        this.date = date;
        this.favorit = favorit;
        this.protection = protection;
        this.categorie = categorie;
        this.contenu = contenu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int isFavorit() {
        return favorit;
    }

    public void setFavorit(int favorit) {
        this.favorit = favorit;
    }

    public String  getProtection() {
        return protection;
    }

    public void setProtection(String  protection) {
        this.protection = protection;
    }

    public String  getCategorie() {
        return categorie;
    }

    public void setCategorie(String  categorie) {
        this.categorie = categorie;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
}
