package com.petut.thobbyo.petut.jeuDeCarte;

/**
 * Created by Thobbyo on 01/12/2016.
 */

public class Sort extends Carte {

    private int damage;

    public Sort(int tailleH, int tailleW, int posX, int posY, int damage, String nom, Image img, int appartenance){
        super(tailleH, tailleW, posX, posY, img, nom, appartenance);
        this.damage = damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

}
