package com.petut.thobbyo.petut.jeuDeCarte;

/**
 * Created by Thobbyo on 10/10/2016.
 */

public class Monstre extends Cartes{


    public Monstre(int tailleH, int tailleW, int posX, int posY, int vitesse, int def, int damage, String nom, Image img) {
        super(tailleH, tailleW, posX, posY, img, nom);
        this.def = def;
        this.damage = damage;
        this.nom = nom;
        this.vitesse = vitesse;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public int getVitesse() {
        return vitesse;
    }

    public int getDef() {
        return def;
    }

    public String getNom() {
        return nom;
    }

    public void moov(){
        posY -= tailleH;
    }

    private int vitesse;
    private int def;
    private int damage;
    private String nom;

}
