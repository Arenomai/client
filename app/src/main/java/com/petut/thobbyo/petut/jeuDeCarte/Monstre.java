package com.petut.thobbyo.petut.jeuDeCarte;

import android.util.Log;

/**
 * Created by Thobbyo on 10/10/2016.
 */

public class Monstre extends Carte {


    public Monstre(int tailleH, int tailleW, int posX, int posY, int vitesse, int def, int damage, String nom, Image img, int appartenance) {
        super(tailleH, tailleW, posX, posY, img, nom, appartenance);
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
        if(appartenance == 0){
            posY += tailleH;
        }
        if(appartenance == 1){
            posY -= tailleH;
        }
    }

    public int posAfterMoov(){
        int a = -1;
        if(appartenance == 0){
            a = posY + tailleH;
        }
        if(appartenance == 1){
            a = posY - tailleH;
        }
        return a;
    }

    public void pertDef(int v){
        def -= v;
    }

    private int vitesse;
    private int def;
    private int damage;
    private String nom;

}
