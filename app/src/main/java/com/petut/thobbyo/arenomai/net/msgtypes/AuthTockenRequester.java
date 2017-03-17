package com.petut.thobbyo.arenomai.net.msgtypes;

import android.os.AsyncTask;

import com.petut.thobbyo.arenomai.net.Connection;
import com.petut.thobbyo.arenomai.net.InMessage;
import com.petut.thobbyo.arenomai.net.MessageType;
import com.petut.thobbyo.arenomai.net.OutMessage;


public class AuthTockenRequester extends AsyncTask<String, Void, Integer> {

    private Connection co;

    public AuthTockenRequester(Connection co) {
        this.co = co;
    }

    @Override
    protected Integer doInBackground(String... params) {

        OutMessage omsg = new OutMessage(MessageType.Auth, Auth.Subtype.Request);
        omsg.writeString(params[0]);
        omsg.writeString(params[1]);
        co.write(omsg);
        InMessage inmsg = co.read();

        int token;
        if (inmsg.getSubtype() == Auth.Subtype.Denied) {
            token = -2;
        } else {
            token = inmsg.readI32();
        }
        return token;
    }
}

