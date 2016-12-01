package com.petut.thobbyo.petut.jeuDeCarte;

/**
 * Created by Thobbyo on 11/11/2016.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.petut.thobbyo.petut.R;

import java.util.ArrayList;
import java.util.List;

// SurfaceView est une surface de dessin.
// référence : http://developer.android.com/reference/android/view/SurfaceView.html
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    // déclaration de l'objet définissant la boucle principale de déplacement et de rendu
    private GameLoopThread gameLoopThread;
    private Image fond;
    List<Monstre> monstres = new ArrayList<Monstre>();
    List<Defense> defenses = new ArrayList<Defense>();
    List<Sort> sorts = new ArrayList<Sort>();

    // création de la surface de dessin
    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameLoopThread = new GameLoopThread(this);

        fond = new Image(this.getContext(), R.mipmap.cadrillage);
    }

    // Fonction qui "dessine" un écran de jeu
    public void doDraw(Canvas canvas) {
        if(canvas==null) {return;}

        // Replace le fond de l'écrant
        fond.draw(canvas, 0, 0);

        // Dessine les carte et les fait ce déplacer.
        for(Monstre a : monstres){
            a.dessiner(canvas);
        }
        for(Defense a : defenses){
            a.dessiner(canvas);
        }
        for(Sort a : sorts){
            a.dessiner(canvas);
        }


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
        //Position du doig sur l'écrant
        int currentX = (int)event.getX() ;
        int currentY = (int)event.getY() ;

        //position que prendra la carte
        int x, y;
        //On trouve cette position en fonction de la taille du fond d'écrant
        x = (currentX/(fond.getW()/5))*fond.getW()/5 ;
        y = (currentY/(fond.getH()/9))*fond.getH()/9 ;

        switch (event.getAction()) {

            // code exécuté lorsque le doigt touche l'écran.
            case MotionEvent.ACTION_DOWN:

                for(Monstre a : monstres){
                    a.moov();
                }

                Monstre atta = new Monstre(fond.getH()/9, fond.getW()/5, x, y, 1, 0, 2, "Monstre pas bo", new Image(this.getContext(), R.mipmap.monstre_sourire));

                monstres.add(atta);
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