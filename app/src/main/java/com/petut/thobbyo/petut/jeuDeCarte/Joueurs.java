package com.petut.thobbyo.petut.jeuDeCarte;

/**
 * Created by Thobbyo on 14/01/2017.
 */

public class Joueurs {

    private int pv;
    private int damage;
    private int position;

    public Joueurs(int pv, int damage, int position){
        this.pv = pv;
        this.damage = damage;
        this.position = position;
    }

    public int getPosition(){
        return position;
    }

    public int getPv(){
        return pv;
    }

    public int getDamage(){
        return damage;
    }

    public void pertePv(int dega){
        pv -= dega;
    }

}
