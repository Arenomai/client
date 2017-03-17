package com.petut.thobbyo.arenomai;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

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
            desc.setText(getResources().getString(R.string.damages)+Integer.toString(taux));
        }
        else {
            desc.setText(getResources().getString(R.string.protection)+Integer.toString(taux));
        }
        image.setImageResource(res_image);
    }

    public void setSelection() {
        layout.setBackgroundResource(R.color.arenomai_parchemin);
    }

    public void setSelectionLongClic() { layout.setBackgroundResource(R.color.mapbox_blue); }

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
