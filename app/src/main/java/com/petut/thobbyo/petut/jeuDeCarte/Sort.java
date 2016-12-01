package com.petut.thobbyo.petut.jeuDeCarte;

/**
 * Created by Thobbyo on 01/12/2016.
 */

public class Sort extends Cartes{

    private int damage;

    public Sort(int tailleH, int tailleW, int posX, int posY, int damage, String nom, Image img){
        super(tailleH, tailleW, posX, posY, img, nom);
        this.damage = damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

}
