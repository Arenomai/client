package com.petut.thobbyo.arenomai;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class CartesActivity extends AppCompatActivity {
    GridView grille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartes);

        grille = (GridView) findViewById(R.id.grille);

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
        listeCartes.add(new CarteView(CartesActivity.this));
        listeCartes.add(new CarteView(CartesActivity.this));
        listeCartes.add(new CarteView(CartesActivity.this));
        listeCartes.add(new CarteView(CartesActivity.this));

        grille.setAdapter(new CarteAdapter(listeCartes));
    }
}
