package com.petut.thobbyo.petut;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

import java.util.ArrayList;

public class CartesActivity extends AppCompatActivity {
    ImageButton plan;
    ImageButton tcp;
    ImageButton profil;
    ImageButton defense;
    ImageButton cartes;

    GridView grille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartes);

        tcp = (ImageButton) findViewById(R.id.tcp);
        plan = (ImageButton) findViewById(R.id.plan);
        profil = (ImageButton) findViewById(R.id.profil);
        defense = (ImageButton) findViewById(R.id.defense);
        cartes = (ImageButton) findViewById(R.id.cartes);

        grille = (GridView) findViewById(R.id.grille);

        tcp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartesActivity.this, TCPActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartesActivity.this, PlanActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartesActivity.this, ProfilActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        defense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartesActivity.this, ArmesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        cartes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        class CarteAdapter extends BaseAdapter {
            ArrayList<CarteView> listeObjets;

            public CarteAdapter(ArrayList<CarteView> listeObjets) {
                this.listeObjets = listeObjets;
            }

            @Override
            public long getItemId(int i) {
                return listeObjets.get(i).getId();
            }

            @Override
            public int getCount() {
                return listeObjets.size();
            }

            @Override
            public Object getItem(int i) {
                return listeObjets.get(i);
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                return listeObjets.get(i);
            }
        }

        ArrayList<CarteView> listeCartes = new ArrayList<CarteView>();
        listeCartes.add(new CarteView(CartesActivity.this,1));
        listeCartes.add(new CarteView(CartesActivity.this,2));
        listeCartes.add(new CarteView(CartesActivity.this,3));
        listeCartes.add(new CarteView(CartesActivity.this,2));

        grille.setAdapter(new CarteAdapter(listeCartes));
    }
}
