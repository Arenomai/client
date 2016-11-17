package com.petut.thobbyo.petut;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * TODO: document your custom view class.
 */
public class linearMenuView extends LinearLayout {
    Button bouton_plan = (Button) findViewById(R.id.plan);
    Button bouton_cartes = (Button) findViewById(R.id.cartes);
    Button btn_defense = (Button) findViewById(R.id.defense);
    Button btn_profil = (Button) findViewById(R.id.profil);

    public linearMenuView(Context context) {
        super(context);
        init();
    }

    public linearMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public linearMenuView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.sample_linear_menu_view, this);
    }

    public void onClick(Context context, View view) {
        Intent myIntent = new Intent(context, MapsMenu.class);
        context.startActivity(myIntent);
    }
}
