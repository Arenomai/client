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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

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

        final TreeMap<Integer, Arme> listeArmes = new TreeMap<>();
        final ArrayList<ArmeView> listeArmeView = new ArrayList<>();
        final Integer[] selectionArmes = new Integer[2]; // Attaque, défense

        // POUR LE SERVEUR : récupérer l'inventaire des armes
        listeArmes.put(10, new HacheFer());
        listeArmes.put(11, new HacheOr());
        listeArmes.put(12, new EpeeBois());
        listeArmes.put(13, new EpeeFer());
        listeArmes.put(14, new EpeeOr());
        listeArmes.put(15, new EpeeDiamant());
        listeArmes.put(16, new BouleMagique());
        listeArmes.put(117, new CalendrierPTT());
        listeArmes.put(118, new BouclierBois());
        listeArmes.put(119, new BouclierBoisFer());
        listeArmes.put(1110, new BouclierBoisOr());
        listeArmes.put(1111, new BouclierOrDiamant());

        // POUR LE SERVEUR, dire quelles sont les deux armes sélectionnées.
        selectionArmes[0] = 11;
        selectionArmes[1] = 1110;

        for (int i : listeArmes.keySet()) {
            final Arme arme = listeArmes.get(i);
            final ArmeView vue = new ArmeView(ArmesActivity.this, arme.getTitre(), arme.getTaux(), arme.getAttaque(), arme.getRes_image());
            ImageButton imagevue = (ImageButton) vue.findViewById(R.id.image_arme);

            vue.setId(i);
            imagevue.setId(i);

            if(selectionArmes[0] == i || selectionArmes[1] == i) {
                vue.setSelection();
            }

            imagevue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectionArmes[(arme.getAttaque()) ? 1 : 0] = view.getId(); // Attaque, défense
                    for (int i = 0; i < listeArmeView.size(); i++) {
                        if (listeArmeView.get(i).getAttaque() == arme.getAttaque()) {
                            listeArmeView.get(i).removeSelection();
                        }
                    }
                    vue.setSelection();
                }
            });

            imagevue.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Arme monArme = listeArmes.get(view.getId());

                    //if(selectionArmes[0] == (int) view.getId() || selectionArmes[1] == (int) view.getId()) {
                    //    Toast.makeText(getApplicationContext(), "Dépose interdite, objet en cours d'usage", Toast.LENGTH_SHORT).show();
                    //}
                    //else {
                        Toast.makeText(getApplicationContext(), "Objet déposé : " + monArme.getTitre(), Toast.LENGTH_SHORT).show();

                        // POUR LE SERVEUR : dire quel objet a été déposé sur la carte.

                        ArmeView vue = null;
                        for (ArmeView i : listeArmeView)
                            if (i.getId() == view.getId())
                                vue = i;

                        listeArmeView.remove(vue);
                        listeArmes.remove(view.getId());
                    //}

                    ArmeAdapter adapter = (ArmeAdapter) grille.getAdapter();
                    adapter.notifyDataSetChanged();

                    return true;
                }
            });

            listeArmeView.add(vue);
        }

        ArmeAdapter grilleAdapter = new ArmeAdapter(listeArmeView);
        grille.setAdapter(grilleAdapter);

    }
}
