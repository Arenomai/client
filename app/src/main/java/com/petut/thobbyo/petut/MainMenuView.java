package com.petut.thobbyo.petut;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainMenuView extends LinearLayout {
    private class ButtonData {
        int idRes;
        ImageButton btn;
        Class<?> activite;

        ButtonData(int idRes, Class<?> activite) {
            this.idRes = idRes;
            this.activite = activite;
        }
    }
    private ButtonData[] boutons;

    public MainMenuView(Context context) {
        super(context);
        init();
    }

    public MainMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MainMenuView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private ButtonData getButtonByActivity(@NonNull Class<?> activite) {
        for (ButtonData btn : boutons) {
            if (btn.activite.equals(activite)) {
                return btn;
            }
        }
        return null;
    }

    // http://stackoverflow.com/questions/8276634/android-get-hosting-activity-from-a-view
    private Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    private void init() {
        inflate(getContext(), R.layout.sample_linear_menu_view, this);

        boutons = new ButtonData[] {
                new ButtonData(R.id.tcp, TCPActivity.class),
                new ButtonData(R.id.plan, PlanActivity.class),
                new ButtonData(R.id.profil, ProfilActivity.class),
                new ButtonData(R.id.defense, ArmesActivity.class),
                new ButtonData(R.id.cartes, CartesActivity.class)
        };

        final Activity hostActivity = getActivity();
        Class<? extends Activity> hostActivityClass = null;
        if (hostActivity != null) {
            hostActivityClass = hostActivity.getClass();
        }
        for (int i = 0; i < boutons.length; ++i) {
            ButtonData bouton = boutons[i];
            final ButtonData finalBouton = boutons[i];
            bouton.btn = (ImageButton) findViewById(bouton.idRes);
            bouton.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), finalBouton.activite);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    getContext().startActivity(intent);
                }
            });
            if (hostActivityClass != null && hostActivityClass.equals(bouton.activite)) {
                bouton.btn.setColorFilter(new LightingColorFilter(Color.rgb(255, 128, 128), 0));
            }
        }
    }
}
