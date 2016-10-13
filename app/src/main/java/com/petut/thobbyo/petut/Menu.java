package com.petut.thobbyo.petut;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.petut.thobbyo.petut.jeuDeCarte.Cartes;

/**
 * Created by Thobbyo on 06/10/2016.
 */

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        TextView tx = (TextView) findViewById(R.id.textInutile);

        Cartes n = new Cartes(1, 3, 5);

        tx.setText(Integer.toString(n.getPosX()));

    }
}
