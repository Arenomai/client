package com.petut.thobbyo.petut;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.petut.thobbyo.petut.jeuDeCarte.GameLoopThread;
import com.petut.thobbyo.petut.jeuDeCarte.GameView;

/**
 * TODO: document your custom view class.
 */
public class ArmeView extends LinearLayout {
    public ArmeView(Context context, int degat) {
        super(context);
        init();
        TextView texte = (TextView) findViewById(R.id.textView);
        texte.setText("Dégat : "+Integer.toString(degat));
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