package com.petut.thobbyo.petut.jeuDeCarte;

/**
 * Created by Thobbyo on 10/10/2016.
 */

public class Monstre extends Cartes{


    public Monstre(int taille, int posX, int posY, int vitesse, int def, int damage, String nom, Image img) {
        super(taille, posX, posY, img, vitesse);
        this.def = def;
        this.damage = damage;
        this.nom = nom;
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

    private int vitesse;
    private int def;
    private int damage;
    private String nom;

}
