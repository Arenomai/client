package com.petut.thobbyo.petut;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

import java.util.ArrayList;

public class ArmesActivity extends AppCompatActivity {
    ImageButton plan;
    ImageButton tcp;
    ImageButton profil;
    ImageButton defense;
    ImageButton cartes;

    GridView grille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armes);

        tcp = (ImageButton) findViewById(R.id.tcp);
        plan = (ImageButton) findViewById(R.id.plan);
        profil = (ImageButton) findViewById(R.id.profil);
        defense = (ImageButton) findViewById(R.id.defense);
        cartes = (ImageButton) findViewById(R.id.cartes);

        grille = (GridView) findViewById(R.id.grille);

        tcp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArmesActivity.this, TCPActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArmesActivity.this, PlanActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArmesActivity.this, ProfilActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        defense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        cartes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArmesActivity.this, CartesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        class ArmeAdapter extends BaseAdapter {
            ArrayList<ArmeView> listeObjets;

            public ArmeAdapter(ArrayList<ArmeView> listeObjets) {
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

        ArrayList<ArmeView> listeArmes = new ArrayList<ArmeView>();
        listeArmes.add(new ArmeView(ArmesActivity.this,1));
        listeArmes.add(new ArmeView(ArmesActivity.this,2));
        listeArmes.add(new ArmeView(ArmesActivity.this,3));
        listeArmes.add(new ArmeView(ArmesActivity.this,2));
        listeArmes.add(new ArmeView(ArmesActivity.this,3));

        grille.setAdapter(new ArmeAdapter(listeArmes));
    }
}
