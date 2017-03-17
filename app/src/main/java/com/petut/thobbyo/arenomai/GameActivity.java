package com.petut.thobbyo.arenomai;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.petut.thobbyo.arenomai.jeuDeCarte.GameView;

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
                GameView GV = (GameView) findViewById(R.id.ViewGame);
                GV.mouvement();
            }
        });
    }
}