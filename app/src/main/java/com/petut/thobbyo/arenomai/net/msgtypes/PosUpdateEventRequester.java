package com.petut.thobbyo.arenomai.net.msgtypes;

import android.content.Context;
import android.os.AsyncTask;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.petut.thobbyo.arenomai.net.Connection;
import com.petut.thobbyo.arenomai.net.InMessage;
import com.petut.thobbyo.arenomai.net.MessageType;
import com.petut.thobbyo.arenomai.net.OutMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krevar on 15/01/2017.
 */

public class PosUpdateEventRequester extends AsyncTask<Double, Double, List<LatLng>> {
    public PosUpdateEventRequester(Context ctx, Connection co) {
        context = ctx;
        this.co = co;
    }
    private Connection co;
    private Context context;


    @Override
    protected List<LatLng> doInBackground(Double... params)
    {
        List<LatLng> listLocations = new ArrayList<>();
        OutMessage omsg = new OutMessage(MessageType.PosUpdate, PosUpdate.Subtype.EventGet);
        omsg.writeDouble(params[0]);
        omsg.writeDouble(params[1]);
        co.write(omsg);
        InMessage inmsg = co.read();
        int j = inmsg.readI32();
        for(int i=0; i < j;i+=2)
        {
            double lat = inmsg.readDouble();
            double lng = inmsg.readDouble();
            LatLng pos = new LatLng(lat,lng);
            listLocations.add(pos);
            System.out.println(pos.getLatitude());
            System.out.println(pos.getLongitude());
        }
        return listLocations;
    }

}
