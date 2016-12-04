package com.petut.thobbyo.petut.Armes;

/**
 * Created by hyakosm on 04/12/16.
 */

public abstract class Arme {
    String titre;
    int taux;
    boolean attaque;
    int res_image;

    public Arme(String titre, int taux, boolean attaque, int res_image) {
        this.attaque = attaque;
        this.titre = titre;
        this.taux = taux;
        this.res_image = res_image;
    }

    public boolean getAttaque() {
        return attaque;
    }

    public String getTitre() {
        return titre;
    }

    public int getTaux() {
        return taux;
    }

    public int getRes_image() {
        return res_image;
    }
}
