package com.petut.thobbyo.petut.jeuDeCarte;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.petut.thobbyo.petut.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// SurfaceView est une surface de dessin.
// référence : http://developer.android.com/reference/android/view/SurfaceView.html
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "GameView";

    // déclaration de l'objet définissant la boucle principale de déplacement et de rendu
    private GameLoopThread gameLoopThread;
    private Bitmap fondCouleur;
    private Paint peintureGrille;
    private float[] pointsGrille;
    private final Matrix matricePlateau = new Matrix();
    private int largeurPlateau, hauteurPlateau;
    private int choix = 0;

    // Matrices des cartes pour optenir leur position
    private Matrix SAdef = new Matrix();
    private Matrix SAatta = new Matrix();
    private Matrix SAsort = new Matrix();

    // Liste des carte qui sont sur le plateau
    List<Monstre> monstres = new ArrayList<>();
    List<Defense> defenses = new ArrayList<>();
    List<Sort> sorts = new ArrayList<>();

    // Type de la carte qui est placer
    private int typeC = 1;

    // Création de la surface de dessin
    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setTaillePlateau(5, 9);
        gameLoopThread = new GameLoopThread(this);

        peintureGrille = new Paint();
        peintureGrille.setStyle(Paint.Style.STROKE);
        peintureGrille.setStrokeWidth(0.01f);
        peintureGrille.setColor(Color.WHITE);
    }

    private void setTaillePlateau(int largeur, int hauteur) {
        largeurPlateau = largeur;
        hauteurPlateau = hauteur;

        pointsGrille = new float[(largeurPlateau+hauteurPlateau+2)*4];
        final float fx = 1.f / largeurPlateau;
        for (int x = 0; x < largeurPlateau + 1; ++x) {
            pointsGrille[x*4] = pointsGrille[x*4 + 2] = x * fx;
            pointsGrille[x*4 + 1] = 0;
            pointsGrille[x*4 + 3] = 1;
        }
        final int off = (largeurPlateau + 1) * 4;
        final float fy = 1.f / hauteurPlateau;
        for (int y = 0; y < hauteurPlateau + 1; ++y) {
            pointsGrille[off + y*4 + 1] = pointsGrille[off + y*4 + 3] = y * fy;
            pointsGrille[off + y*4] = 0;
            pointsGrille[off + y*4 + 2] = 1;
        }
    }

    private void dessinerGrille(Canvas canvas) {
        canvas.drawLines(pointsGrille, peintureGrille);
    }

    // Fonction qui "dessine" un écran de jeu
    public void doDraw(Canvas canvas) {
        if (canvas == null) { return; }

        // On efface l'écran
        canvas.drawColor(Color.WHITE);
        if (fondCouleur != null)
            canvas.drawBitmap(fondCouleur, 0, 0, null);

        // Dessine la grille
        canvas.save();
        canvas.concat(matricePlateau);

        dessinerGrille(canvas);

        Carte def = new Carte(50, 50, 0, 0, new Image(this.getContext(), R.mipmap.bleu_mur_icone_128 ), "");
        Carte atta = new Carte(50, 50, 0, 0, new Image(this.getContext(), R.mipmap.monstre_sourire), "");

        Matrix Adef = new Matrix();
        Adef.postScale(1.f / largeurPlateau, 1.f / hauteurPlateau);
        Adef.postTranslate(0.1f, 1.05f);
        SAdef = Adef;
        canvas.save();
        canvas.concat(Adef);
        def.dessiner(canvas);

        canvas.restore();

        Matrix Aatta = new Matrix();
        Aatta.postScale(1.f / largeurPlateau, 1.f / hauteurPlateau);
        Aatta.postTranslate(0.5f, 1.05f);
        canvas.save();
        canvas.concat(Aatta);
        atta.dessiner(canvas);
        canvas.restore();


        ArrayList<Carte> suppr = new ArrayList<>();
        // Dessine les carte et les fait se déplacer.
        synchronized (monstres) {
            for (Monstre a : monstres) {
                Matrix matriceMonstre = new Matrix();
                matriceMonstre.postScale(1.f / largeurPlateau, 1.f / hauteurPlateau);
                matriceMonstre.postTranslate((float) a.getPosX() / largeurPlateau,
                        (float) a.getPosY() / hauteurPlateau);
                canvas.save();
                canvas.concat(matriceMonstre);
                a.dessiner(canvas);
                canvas.restore();

                if (a.getPosY() < 0){
                    suppr.add(a);
                }
            }
        }

        synchronized (defenses) {
            for(Defense a : defenses){
                Matrix matricedefenses = new Matrix();
                matricedefenses.postScale(1.f / largeurPlateau, 1.f / hauteurPlateau);
                matricedefenses.postTranslate((float) a.getPosX() / largeurPlateau,
                        (float) a.getPosY() / hauteurPlateau);
                canvas.save();
                canvas.concat(matricedefenses);
                a.dessiner(canvas);
                canvas.restore();

                /*if (a.getPosY() < 0){
                    suppr.add(a);
                }*/
            }
        }

        for(Sort a : sorts){
            a.dessiner(canvas);
        }

        for(Carte a : suppr){
            monstres.remove(a);
        }
        canvas.restore();
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
        // Position du doigt sur l'écrant
        int currentX = (int) event.getX();
        int currentY = (int) event.getY();

        // Position que prendra la carte
        final Matrix invMatricePlateau = new Matrix();
        if (!matricePlateau.invert(invMatricePlateau)) {
            throw new RuntimeException("matricePlateau n'est pas inversible");
        }
        float[] points = new float[] { currentX, currentY };
        invMatricePlateau.mapPoints(points);
        // On trouve cette position en fonction de la taille du plateau
        final int x = (int) Math.floor(points[0] * largeurPlateau),
                  y = (int) Math.floor(points[1] * hauteurPlateau);

        Log.d(" x ", ""+points[0]+" ; " + x);
        Log.d(" y ", ""+points[1]+" ; " + y);

        switch (event.getAction()) {
            // code exécuté lorsque le doigt touche l'écran.
            case MotionEvent.ACTION_DOWN:
                // Déplace tous les monstres

                synchronized (monstres) {
                    for (Monstre a : monstres) {
                        a.moov();
                    }
                }

////////    pointsC ne contient pas des valeurs comprise entre 0 et 1 !!!
                currentX = (int) event.getX();
                currentY = (int) event.getY();

                Matrix inv = new Matrix();
                if (!SAdef.invert(inv)) {
                    throw new RuntimeException("matrice n'est pas inversible");
                }
                points = new float[] { currentX, currentY };
                inv.mapPoints(points);
                if(points[0] >= 0 && points[0] <= 1 && points[1] >= 0 && points[1] <= 1){
                    typeC = 1;
                }

                inv = new Matrix();
                if (!SAatta.invert(inv)) {
                    throw new RuntimeException("matrice n'est pas inversible");
                }
                points = new float[] { currentX, currentY };
                inv.mapPoints(points);
                if(points[0] >= 0 && points[0] <= 1 && points[1] >= 0 && points[1] <= 1){
                    typeC = 2;
                }

                inv = new Matrix();
                if (!SAsort.invert(inv)) {
                    throw new RuntimeException("matrice n'est pas inversible");
                }
                points = new float[] { currentX, currentY };
                inv.mapPoints(points);
                if(points[0] >= 0 && points[0] <= 1 && points[1] >= 0 && points[1] <= 1){
                    typeC = 3;
                }
////////
                Monstre atta = null;
                Defense def = null;

                Log.d(" pointsC : ", points[0]+"");

                if (x >= 0 && x < largeurPlateau && y >= 0 && y < hauteurPlateau) {
                    // Crée une carte
                    if(typeC == 1){
                        def = new Defense(50, 50, x, y, 4, 1, "MÛRE", new Image(this.getContext(), R.mipmap.bleu_mur_icone_128 ));
                    }

                    if(typeC == 2){
                        atta = new Monstre(1, 1, x, y, 1, 0, 2, "Monstre pas bô",
                                new Image(this.getContext(), R.mipmap.monstre_sourire));
                    }

                    if(typeC == 3){
                        atta = new Monstre(1, 1, x, y, 1, 0, 2, "Monstre pas bô",
                                new Image(this.getContext(), R.mipmap.monstre_sourire));
                    }

                    // On ajoute le monstre à la liste
                    synchronized (monstres) {
                        if (atta != null){
                            monstres.add(atta);
                        }
                    }
                    synchronized (defenses){
                        if(def != null){
                            defenses.add(def);
                        }
                    }
                }
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
        matricePlateau.reset();
        final float ratioEcran = ((float)w) / h,
                ratioPlateau = ((float)largeurPlateau) / hauteurPlateau;
        if (ratioEcran > ratioPlateau) {
            final float largeur = ((float)h) / hauteurPlateau * largeurPlateau;
            matricePlateau.postScale(largeur*0.85f, h*0.85f);
            matricePlateau.postTranslate((w - largeur) / 2, 0);
        } else {
            final float hauteur = ((float)w) / largeurPlateau * hauteurPlateau;
            matricePlateau.postScale(w, hauteur);
            matricePlateau.postTranslate(0, (h - hauteur) / 2);
        }

        int[] pixels = new int[w * h];
        Random random = new Random();
        final int baseColor = ContextCompat.getColor(getContext(), R.color.arenomai_fond_plateau);
        final int baseColorR = Color.red(baseColor), baseColorG = Color.green(baseColor),
                baseColorB = Color.blue(baseColor);
        for (int idx = 0; idx < w * h; ++idx) {
            int add = random.nextInt(32);
            pixels[idx] = Color.argb(255, baseColorR + add, baseColorG + add, baseColorB + add);
        }
        fondCouleur = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        fondCouleur.setPixels(pixels, 0, w, 0, 0, w, h);
    }
} // class GameView