package com.petut.thobbyo.petut.Armes;

/**
 * Created by hyakosm on 05/01/17.
 */

public class ObjetPlan {
    Arme objet;
    boolean se_merite;
    int latitude;
    int longitude;

    public ObjetPlan(Arme objet, int latitude, int longitude, boolean se_merite) {
        this.objet = objet;
        this.se_merite = se_merite;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Arme getObjet() {
        return this.objet;
    }

    public boolean getSeMerite() {
        return this.se_merite;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getLongitude() {
        return longitude;
    }
}
