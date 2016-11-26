package com.petut.thobbyo.petut.jeuDeCarte;

/**
 * Created by Thobbyo on 10/10/2016.
 */

public class Cartes {

    public Cartes(int taille, int posX, int posY){
        this.taille = taille;
        this.posX = posX;
        this.posY = posY;
    }

    public void setTaille(int Ntaille){
        this.taille = Ntaille;
    }

    public void setPos(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public int getTaille(){
        return taille;
    }

    public int getPosX(){
        return posX;
    }

    public  int getPosY(){
        return posY;
    }

    private int taille;
    private int posX;
    private int posY;

}
