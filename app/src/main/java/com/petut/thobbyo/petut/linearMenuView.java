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

import com.petut.thobbyo.petut.jeuDeCarte.GameLoopThread;
import com.petut.thobbyo.petut.jeuDeCarte.GameView;

/**
 * TODO: document your custom view class.
 */
public class linearMenuView extends LinearLayout {
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
}
