package com.petut.thobbyo.arenomai;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class CarteView extends LinearLayout {
    public CarteView(Context context) {
        super(context);
        init();
    }

    public CarteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CarteView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.sample_carte_view, this);
    }
}
