package com.petut.thobbyo.arenomai.jeuDeCarte;

import java.util.List;
import java.util.Random;

/**
 * Created by Thobbyo on 09/02/2017.
 */

public class Deck {

    List<Carte> mesCartes;

    public Deck(List<Carte> lesMienne){
        mesCartes = lesMienne;
    }

    private static int randomInteger(int aStart, int aEnd, Random aRandom){
        long range = (long)aEnd - (long)aStart + 1;
        long fraction = (long)(range * aRandom.nextDouble());
        int randomNumber =  (int)(fraction + aStart);
        return randomNumber;
    }

    public Carte RandCarte(){
        Random ran = new Random();
        int val = randomInteger(0, mesCartes.size(), ran);
        return mesCartes.get(val);
    }

}
