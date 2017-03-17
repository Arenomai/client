package com.petut.thobbyo.arenomai.jeuDeCarte;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Thobbyo on 21/11/2016.
 */

public class Defense extends Carte {

    public Defense(int tailleH, int tailleW,  int posX, int posY, int def, int damage, String nom, Image img, int appartenance) {
        super(tailleH, tailleW, posX, posY, img, nom, appartenance);
        this.def = def;
        this.initDef = def;
        this.damage = damage;
        this.nom = nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = 0;
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

    public void pertDef(int v){
        def -= v;
    }

    private int vitesse;
    private int def, initDef;
    private int damage;
    private String nom;

    @Override
    public void dessiner(Canvas canvas) {
        super.dessiner(canvas);
        final Paint p = new Paint();
        p.setColor(0xFF00CC00);
        canvas.drawRect(getBorder() * 2, 1 - getBorder()*3, getBorder() * 2 + (1 - getBorder()*4)*((float)def/initDef), 1 - getBorder()*2, p);
    }
}
