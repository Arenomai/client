package com.petut.thobbyo.petut.jeuDeCarte;

import android.graphics.Canvas;

/**
 * Created by Thobbyo on 10/10/2016.
 */

public class Cartes {

    private int vitesse;
    private int taille;
    private int posX;
    private int posY;
    private Image img;

    public Cartes(int taille, int posX, int posY, Image img, int vitesse){
        this.taille = taille;
        this.posX = posX;
        this.posY = posY;
        this.img = img;
        this.img.resize(taille, taille);
        this.vitesse = vitesse;
    }

    public void setTaille(int Ntaille){
        img.setTailleImage(Ntaille, Ntaille);
    }

    public void setTaille(int Wtaille, int Htaille){
        img.setTailleImage(Wtaille, Htaille);
    }

    public void setPos(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public void setVitesse(int vitesse){this.vitesse = vitesse ;}

    public void setImg(Image img){this.img = img ;}

    public void moov(int mx, int my){
        posX += mx;
        posY += my;
    }

    public void dessiner(Canvas canvas){img.draw(canvas, posX, posY);}

    public Image getImg(){return img ;}

    public int getVitesse(){return vitesse ;}

    public int getPosX(){
        return posX;
    }

    public  int getPosY(){
        return posY;
    }

}
