package com.petut.thobbyo.arenomai.net.msgtypes;

/**
 * Created by Krevar on 09/01/2017.
 */

public abstract class UserAccount {
    public static abstract class Subtype {
        public static final byte
                PassModify = 1,
                AccountModify = 2,
                InfoRequest = 3;
    }

}
