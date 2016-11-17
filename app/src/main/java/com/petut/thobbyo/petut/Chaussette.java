package com.petut.thobbyo.petut;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by hyakosm on 17/11/16.
 */

public class Chaussette extends AsyncTask {
    Socket chaussette;
    PrintWriter tamponSortie;
    BufferedReader tamponEntree;

    public void Chaussette() {
        try {
            chaussette = new Socket("thgros.fr", 4242);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            tamponSortie = new PrintWriter(new BufferedWriter(new OutputStreamWriter(chaussette.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            tamponEntree = new BufferedReader(new InputStreamReader(chaussette.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String Send(String message) {
        tamponSortie.print(message);
        try {
            return tamponEntree.readLine();
        } catch (IOException e) {
            return "ERROR";
        }
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }
}