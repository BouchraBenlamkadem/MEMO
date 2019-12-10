package com.dev.bbl.memo;

import android.widget.ImageView;

public class categorie {

    private int id;
    private String nom;
    private ImageView icon;
    private static MainActivity activity;

    public categorie(int id, String nom, ImageView icon) {
        this.id = id;
        this.nom = nom;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public static int getIcon(String category) {
     //   imageView.setDrawableResource(getIcon
        if(category.equals("etudes"))
            return android.R.drawable.ic_menu_edit;
        else if(category.equals("divers"))
            return android.R.drawable.ic_menu_view;
        else if(category.equals("taches"))
            return android.R.drawable.ic_input_get;
        else if(category.equals("secret1")||category.equals("secret2"))
            return android.R.drawable.ic_lock_lock;
        else if(category.equals("nocat"))
            return android.R.drawable.ic_menu_sort_by_size;
        else
            return R.drawable.ic_menu_send;

    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }

    public void setActivity(MainActivity mainActivity)
    {
        activity=mainActivity;
    }
}