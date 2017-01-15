package com.petut.thobbyo.petut.net.msgtypes;

import android.location.Location;

import com.petut.thobbyo.petut.net.InMessage;
import com.petut.thobbyo.petut.net.OutMessage;
import com.petut.thobbyo.petut.net.Serializable;

public class PosUpdate {

    public static abstract class Subtype {
        public static final byte
                EventGet = 0,
                EventGetResponse = 1;
    }

    public static class Notify implements Serializable {
        public Location loc;

        @Override
        public void writeToMsg(OutMessage msg) {
            msg.writeDouble(loc.getLatitude());
            msg.writeDouble(loc.getLongitude());
        }

        @Override
        public void readFromMsg(InMessage msg) {
            loc = new Location("GameServer");
            loc.setLongitude(msg.readDouble());
            loc.setLatitude(msg.readDouble());
        }
    }
}
