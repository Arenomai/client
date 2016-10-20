package com.petut.thobbyo.petut;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class Acceuil extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        View v = findViewById(R.id.activity_acceuil);

       v.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                changeView(findViewById(R.id.activity_menu));
            }

       });
    }

    public void changeView(View vi){
        Intent intent = new Intent(Acceuil.this, MapsMenu.class);
        startActivity(intent);
    }
}
