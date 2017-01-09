package com.petut.thobbyo.petut.net.msgtypes;

import com.petut.thobbyo.petut.net.InMessage;
import com.petut.thobbyo.petut.net.OutMessage;
import com.petut.thobbyo.petut.net.Serializable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class Auth {

    public static abstract class Subtype {
        public static final byte
                Denied = 0,
                Request = 1,
                Response = 2;
    }

    public static class Request implements Serializable {
        private String username;
        private byte passwordSha512[];

        public void setPassword(final String pw) {
            MessageDigest md;
            try {
                md = MessageDigest.getInstance("SHA-512");
                md.update(pw.getBytes());
                passwordSha512 = md.digest();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        public void setUsername(final String un)
        {
            this.username = un;
        }
        @Override
        public void writeToMsg(OutMessage msg) {
            msg.writeString(username);
            msg.writeData(passwordSha512);
        }

        @Override
        public void readFromMsg(InMessage msg) {
            throw new UnsupportedOperationException();
        }
    }
}
