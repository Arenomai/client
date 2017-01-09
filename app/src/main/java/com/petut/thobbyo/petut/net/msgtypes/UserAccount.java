package com.petut.thobbyo.petut.net.msgtypes;

/**
 * Created by Krevar on 09/01/2017.
 */

public abstract class UserAccount {
    public static abstract class Subtype {
        public static final byte
                PassModify = 1,
                BioModify = 2,
                NickModify = 3,
                InfoRequest = 4,
                InfoResponse = 5;
    }

}
