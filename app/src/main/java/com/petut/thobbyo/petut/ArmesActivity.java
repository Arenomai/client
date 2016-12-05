package com.petut.thobbyo.petut;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

import com.petut.thobbyo.petut.Armes.Arme;
import com.petut.thobbyo.petut.Armes.Attaque.BouleMagique;
import com.petut.thobbyo.petut.Armes.Attaque.EpeeBois;
import com.petut.thobbyo.petut.Armes.Attaque.EpeeDiamant;
import com.petut.thobbyo.petut.Armes.Attaque.EpeeFer;
import com.petut.thobbyo.petut.Armes.Attaque.EpeeOr;
import com.petut.thobbyo.petut.Armes.Defense.BouclierBois;
import com.petut.thobbyo.petut.Armes.Defense.BouclierBoisFer;
import com.petut.thobbyo.petut.Armes.Defense.BouclierOrDiamant;
import com.petut.thobbyo.petut.Armes.Defense.CalendrierPTT;

import java.util.ArrayList;

public class ArmesActivity extends AppCompatActivity {
    GridView grille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armes);

        grille = (GridView) findViewById(R.id.grille);

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

        ArrayList<Arme>  listeArmes = new ArrayList<Arme>();

        listeArmes.add(new EpeeBois());
        listeArmes.add(new EpeeFer());
        listeArmes.add(new EpeeOr());
        listeArmes.add(new EpeeDiamant());
        listeArmes.add(new BouleMagique());
        listeArmes.add(new CalendrierPTT());
        listeArmes.add(new BouclierBois());
        listeArmes.add(new BouclierBoisFer());
        listeArmes.add(new BouclierOrDiamant());

        final Arme[] selectionArmes = new Arme[2]; // Attaque, défense

        final ArrayList<ArmeView> listeArmeView = new ArrayList<ArmeView>();

        for(int i=0 ;i<listeArmes.size() ; i++) {
            final Arme arme = listeArmes.get(i);

            final ArmeView vue = new ArmeView(ArmesActivity.this, arme.getTitre(), arme.getTaux(), arme.getAttaque(), arme.getRes_image());
            ImageButton imagevue = (ImageButton) vue.findViewById(R.id.image_arme);

            imagevue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectionArmes[(arme.getAttaque()) ? 1 : 0] = arme;
                    for (int i = 0; i < listeArmeView.size(); i++) {
                        if (listeArmeView.get(i).getAttaque() == arme.getAttaque()) {
                            listeArmeView.get(i).removeSelection();
                        }
                    }
                    vue.setSelection();
                }
            });

            listeArmeView.add(vue);
        }

        grille.setAdapter(new ArmeAdapter(listeArmeView));
    }
}
