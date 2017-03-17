package com.petut.thobbyo.arenomai;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

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

    /* private ButtonData getButtonByActivity(@NonNull Class<?> activite) {
        for (ButtonData btn : boutons) {
            if (btn.activite.equals(activite)) {
                return btn;
            }
        }
        return null;
    } */

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

        final ButtonData[] boutons = new ButtonData[] {
                new ButtonData(R.id.plan, PlanActivity.class),
                new ButtonData(R.id.profil, ProfilActivity.class),
                new ButtonData(R.id.defense, ArmesActivity.class),
                new ButtonData(R.id.cartes, CartesActivity.class),
                new ButtonData(R.id.tcp, AboutActivity.class),
        };

        ArrayList<Integer> resBoutonsSelection = new ArrayList<>();
        resBoutonsSelection.add(R.drawable.menu_plan_sel);
        resBoutonsSelection.add(R.drawable.menu_moi_sel);
        resBoutonsSelection.add(R.drawable.menu_armes_sel);
        resBoutonsSelection.add(R.drawable.menu_cartes_sel);
        resBoutonsSelection.add(R.drawable.menu_about_sel);

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
                ///bouton.btn.setColorFilter(new LightingColorFilter(Color.rgb(220, 220, 220), 0));
                bouton.btn.setImageResource(resBoutonsSelection.get(i));
            }
        }
    }
}
