package com.petut.thobbyo.arenomai.jeuDeCarte;

import android.content.Context;

import com.petut.thobbyo.arenomai.R;

import java.util.List;
import java.util.Random;

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

    private static int randomInteger(int aStart, int aEnd, Random aRandom){
        long range = (long)aEnd - (long)aStart + 1;
        long fraction = (long)(range * aRandom.nextDouble());
        int randomNumber =  (int)(fraction + aStart);
        return randomNumber;
    }

    public void IA(List<Monstre> monstre, List<Defense> defense, Context contexte){
        if (position == 0) {
            Random ran = new Random();
            int randomInt = ran.nextInt(2);
            Carte cartePlacee = null;

            if (randomInt == 0) {
                int randY = randomInteger(0, 1, ran);
                int randX = randomInteger(0, 4, ran);

                boolean pasOcuper = true;

                synchronized (monstre) {
                    for (Monstre m : monstre) {
                        if (m.getPosY() == randY && m.getPosX() == randX) {
                            pasOcuper = false;
                        }
                    }
                }
                if(pasOcuper){
                    Monstre mm = new Monstre(1, 1, randX, randY, 1, 2, 2, "Monstre pas bô", new Image(contexte, R.mipmap.monstre_sourire), 0);
                    cartePlacee = mm;
                    monstre.add(mm);
                }
            }

            if (randomInt == 1){
                int randY = randomInteger(2, 3, ran);
                int randX = randomInteger(0, 4, ran);

                boolean pasOcuper = true;

                synchronized (defense) {
                    for (Defense d : defense) {
                        if (d.getPosY() == randY && d.getPosX() == randX) {
                            pasOcuper = false;
                        }
                    }
                }

                if(pasOcuper){
                    Defense dd = new Defense(1, 1, randX, randY, 4, 0, "MÛRE", new Image(contexte, R.mipmap.bleu_mur_icone_128), 0);
                    cartePlacee = dd;
                    defense.add(dd);
                }


            }


            if (cartePlacee != null) {
                cartePlacee.setBorderColors(0xFFE00000, 0xFFC00000, 0xFFA00000);
            }

        }
    }
}
