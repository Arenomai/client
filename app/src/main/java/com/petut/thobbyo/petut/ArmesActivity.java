package com.petut.thobbyo.petut;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.petut.thobbyo.petut.Armes.Arme;
import com.petut.thobbyo.petut.Armes.Attaque.BouleMagique;
import com.petut.thobbyo.petut.Armes.Attaque.EpeeBois;
import com.petut.thobbyo.petut.Armes.Attaque.EpeeDiamant;
import com.petut.thobbyo.petut.Armes.Attaque.EpeeFer;
import com.petut.thobbyo.petut.Armes.Attaque.EpeeOr;
import com.petut.thobbyo.petut.Armes.Attaque.HacheFer;
import com.petut.thobbyo.petut.Armes.Attaque.HacheOr;
import com.petut.thobbyo.petut.Armes.Defense.BouclierBois;
import com.petut.thobbyo.petut.Armes.Defense.BouclierBoisFer;
import com.petut.thobbyo.petut.Armes.Defense.BouclierBoisOr;
import com.petut.thobbyo.petut.Armes.Defense.BouclierOrDiamant;
import com.petut.thobbyo.petut.Armes.Defense.CalendrierPTT;

import java.util.ArrayList;
import java.util.Iterator;

public class ArmesActivity extends AppCompatActivity {
    GridView grille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armes);

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

        grille = (GridView) findViewById(R.id.grille);

        final ArrayList<Arme>  listeArmes = new ArrayList<Arme>();

        listeArmes.add(new HacheFer());
        listeArmes.add(new HacheOr());
        listeArmes.add(new EpeeBois());
        listeArmes.add(new EpeeFer());
        listeArmes.add(new EpeeOr());
        listeArmes.add(new EpeeDiamant());
        listeArmes.add(new BouleMagique());
        listeArmes.add(new CalendrierPTT());
        listeArmes.add(new BouclierBois());
        listeArmes.add(new BouclierBoisFer());
        listeArmes.add(new BouclierBoisOr());
        listeArmes.add(new BouclierOrDiamant());

        final Arme[] selectionArmes = new Arme[2]; // Attaque, défense

        selectionArmes[0] = listeArmes.get(1);
        selectionArmes[1] = listeArmes.get(10);

        final ArrayList<ArmeView> listeArmeView = new ArrayList<ArmeView>();

        for(int i=0 ;i<listeArmes.size() ; i++) {
            final Arme arme = listeArmes.get(i);

            final ArmeView vue = new ArmeView(ArmesActivity.this, arme.getTitre(), arme.getTaux(), arme.getAttaque(), arme.getRes_image());

            ImageButton imagevue = (ImageButton) vue.findViewById(R.id.image_arme);
            vue.setId(i);
            imagevue.setId(i);

            imagevue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectionArmes[(arme.getAttaque()) ? 1 : 0] = arme; // Attaque, défense
                    for (int i = 0; i < listeArmeView.size(); i++) {
                        if (listeArmeView.get(i).getAttaque() == arme.getAttaque()) {
                            listeArmeView.get(i).removeSelection();
                        }
                    }
                    vue.setSelection();
                }
            });

            /*imagevue.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Arme monArme = listeArmes.get(view.getId());

                   *//* Iterator ite = listeArmes.iterator();

                    while(!(ite.next().equals(monArme)));
                    *//*

                    // Si attaque cliquée-long
                    if(monArme.getAttaque()) {
                        int nbattaque = 0;

                        for(int i=0 ; i<listeArmes.size() ; i++) {
                            if(listeArmes.get(i).getAttaque()) {
                                nbattaque++;
                            }
                        }

                        Toast.makeText(getApplicationContext(), "attaque", Toast.LENGTH_SHORT).show();
                        if(nbattaque >= 1) {
                            Toast.makeText(getApplicationContext(), "Objet déposé : "+monArme.getTitre(), Toast.LENGTH_SHORT).show();

                            *//*if(selectionArmes[0].equals(monArme)) {
                                if(ite.hasNext()) {
                                    Arme prochaineArme = (Arme) ite.next();
                                    selectionArmes[0] = prochaineArme;
                                    listeArmeView.get(listeArmes.indexOf(prochaineArme)).setSelection();
                                }
                            }*//*
                            listeArmeView.remove(view.getId());
                            listeArmes.remove(view.getId());
                        }
                    }


                    ArmeAdapter adapter = (ArmeAdapter) grille.getAdapter();
                    adapter.notifyDataSetChanged();

                    return true;
                }
            });*/

            listeArmeView.add(vue);
        }

        ArmeAdapter grilleAdapter = new ArmeAdapter(listeArmeView);
        grille.setAdapter(grilleAdapter);

    }
}
