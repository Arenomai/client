package com.petut.thobbyo.petut.jeuDeCarte;

/**
 * Created by Thobbyo on 11/11/2016.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;

import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.petut.thobbyo.petut.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

// SurfaceView est une surface de dessin.
// référence : http://developer.android.com/reference/android/view/SurfaceView.html
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    // déclaration de l'objet définissant la boucle principale de déplacement et de rendu
    private GameLoopThread gameLoopThread;
    private Image fond;
    List<Cartes> monstre = new ArrayList<Cartes>();

    // création de la surface de dessin
    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameLoopThread = new GameLoopThread(this);

        fond = new Image(this.getContext(), R.mipmap.chat_mignon_02);
    }

    // Fonction qui "dessine" un écran de jeu
    public void doDraw(Canvas canvas) {
        if(canvas==null) {return;}

        // on efface l'écran, en blanc
        fond.draw(canvas, 0, 0);

        for(Cartes a : monstre){
            a.dessiner(canvas);
            a.moov(0, -50);
        }


    }

    public void DrawCartre(Cartes car, int deplacementX, int deplacementY){
        car.moov(deplacementX, deplacementY);
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée immédiatement après la création de l'objet SurfaceView
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // création du processus GameLoopThread si cela n'est pas fait
        if(gameLoopThread.getState() == Thread.State.TERMINATED) {
            gameLoopThread = new GameLoopThread(this);
        }
        gameLoopThread.setRunning(true);
        gameLoopThread.start();
    }

    // Gère les touchés sur l'écran
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int currentX = (int)event.getX();
        int currentY = (int)event.getY();

        switch (event.getAction()) {

            // code exécuté lorsque le doigt touche l'écran.
            case MotionEvent.ACTION_DOWN:
                Monstre atta = new Monstre(100, currentX, currentY, 1, 0, 2, "Monstre pas bo", new Image(this.getContext(), R.mipmap.monstre_sourire));

                monstre.add(atta);
                break;
        }
        return false;
    }


    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée juste avant que l'objet ne soit détruit.
    // on tente ici de stopper le processus de gameLoopThread
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        gameLoopThread.setRunning(false);
        while (retry) {
            try {
                gameLoopThread.join();
                retry = false;
            }
            catch (InterruptedException e) {}
        }
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée à la CREATION et MODIFICATION et ONRESUME de l'écran
    // nous obtenons ici la largeur/hauteur de l'écran en pixels
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
        fond.resize(w,h); // on définit la taille de l'image selon la taille de l'écran
    }
} // class GameView