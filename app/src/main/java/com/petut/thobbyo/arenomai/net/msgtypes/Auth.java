package com.petut.thobbyo.arenomai.net.msgtypes;

import com.petut.thobbyo.arenomai.net.InMessage;
import com.petut.thobbyo.arenomai.net.OutMessage;
import com.petut.thobbyo.arenomai.net.Serializable;

public abstract class Auth {

    public static abstract class Subtype {
        public static final byte
                Denied = 0,
                Request = 1,
                Response = 2;
    }

    public static class Request implements Serializable {
        private String username;
        private String password;
        private byte passwordSha512[];

        public void setPassword(final String pw) {
            /*MessageDigest md;
            try {
                md = MessageDigest.getInstance("SHA-512");
                md.update(pw.getBytes());
                passwordSha512 = md.digest();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }*/
            this.password = pw;
        }

        public void setUsername(final String un)
        {
            this.username = un;
        }
        @Override
        public void writeToMsg(OutMessage msg) {
            msg.writeString(username);
            msg.writeString(password);
            //msg.writeData(passwordSha512);
        }

        @Override
        public void readFromMsg(InMessage msg) {
            throw new UnsupportedOperationException();
        }
    }
}
