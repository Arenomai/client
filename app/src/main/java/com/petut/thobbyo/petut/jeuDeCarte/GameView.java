package com.petut.thobbyo.petut.jeuDeCarte;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
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
    List<Monstre> monstres = new ArrayList<>();
    List<Defense> defenses = new ArrayList<>();
    List<Sort> sorts = new ArrayList<>();

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
                // android.util.Log.d(TAG, "Dessin monstre (" + a.getPosX() + ", " + a.getPosY() + ")");
                a.dessiner(canvas);
                canvas.restore();

                if (a.getPosY() < 0){
                    suppr.add(a);
                }
            }
        }

        for(Defense a : defenses){
            a.dessiner(canvas);
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

        switch (event.getAction()) {
            // code exécuté lorsque le doigt touche l'écran.
            case MotionEvent.ACTION_DOWN:
                // Déplace tous les monstres
                synchronized (monstres) {
                    for (Monstre a : monstres) {
                        a.moov();
                    }
                }
                if (x >= 0 && x < largeurPlateau && y >= 0 && y < hauteurPlateau) {
                    // Crée un monstre
                    Monstre atta = new Monstre(1, 1, x, y, 1, 0, 2, "Monstre pas bô",
                            new Image(this.getContext(), R.mipmap.monstre_sourire));

                    // On ajoute le monstre à la liste
                    synchronized (monstres) {
                        monstres.add(atta);
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
        int cote = w > h ? h : w;
        matricePlateau.reset();
        matricePlateau.postScale(cote, cote);

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