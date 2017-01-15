package com.petut.thobbyo.petut;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.petut.thobbyo.petut.jeuDeCarte.GameLoopThread;
import com.petut.thobbyo.petut.jeuDeCarte.GameView;

import java.util.ArrayList;

/**
 * Created by Thobbyo on 12/01/2017.
 */

public class GameActivity extends AppCompatActivity {

    //Boutton Tour Suivant
    private Button button_TS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //GameView game = new GameView(GameActivity.this);
        setContentView(R.layout.activity_game);
        button_TS = (Button) findViewById(R.id.buttonTourSuivant);
        button_TS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}