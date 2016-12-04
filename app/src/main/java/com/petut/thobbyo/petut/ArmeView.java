package com.petut.thobbyo.petut;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.petut.thobbyo.petut.jeuDeCarte.GameLoopThread;
import com.petut.thobbyo.petut.jeuDeCarte.GameView;

/**
 * TODO: document your custom view class.
 */
public class ArmeView extends LinearLayout {
    LinearLayout layout;
    TextView titre;
    TextView desc;
    ImageButton image;
    boolean attaque;

    public ArmeView(Context context, String strTitre, int taux, boolean attaque, int res_image) {
        super(context);
        init();
        layout = (LinearLayout) findViewById(R.id.layout_arme);
        titre = (TextView) findViewById(R.id.titre);
        desc = (TextView) findViewById(R.id.desc);
        image = (ImageButton) findViewById(R.id.image_arme);
        this.attaque = attaque;

        titre.setText(strTitre);
        if(attaque) {
            desc.setText("Dégat : " + Integer.toString(taux));
        }
        else {
            desc.setText("Protection : " + Integer.toString(taux));
        }
        image.setImageResource(res_image);
    }

    public void setSelection() {
        layout.setBackgroundResource(R.color.arenomai_parchemin);
    }

    public void removeSelection() {
        layout.setBackgroundResource(0);
    }

    public boolean getAttaque() {
        return attaque;
    }

    public ArmeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArmeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.sample_arme_view, this);
    }
}
