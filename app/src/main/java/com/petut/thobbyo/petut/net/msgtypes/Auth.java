package com.petut.thobbyo.petut.net.msgtypes;

import com.petut.thobbyo.petut.net.InMessage;
import com.petut.thobbyo.petut.net.OutMessage;
import com.petut.thobbyo.petut.net.Serializable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class Auth {

    public static abstract class Subtype {
        public static final byte
                Request = 0,
                ResponseSuccess = 1,
                ResponsFailure = 2;
    }

    public static class Request implements Serializable {
        public String username;
        public byte passwordSha512[];

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
