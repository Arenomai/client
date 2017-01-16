package com.petut.thobbyo.petut.jeuDeCarte;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.petut.thobbyo.petut.GameActivity;
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

    // Matrices des cartes pour optenir leur position
    private Matrix SAdef = new Matrix();
    private Matrix SAatta = new Matrix();
    // private Matrix SAsort = new Matrix();

    // Liste des carte qui sont sur le plateau
    final List<Monstre> monstres = new ArrayList<>();
    final List<Defense> defenses = new ArrayList<>();
    final List<Sort> sorts = new ArrayList<>();

    //les 2 dieux qui place leur larbin sur le terrun
    Joueurs ami = new Joueurs(6, 1, 1);
    Joueurs ennemi = new Joueurs(6, 1, 0);

    // Type de la carte qui est placer
    private enum TypeCarte {
        Defense,
        Attaque
    }
    private TypeCarte typeCarteSelectionnee;
    private Carte cartePosee = null;

    // Cartes pour la selection de la carte active
    private Carte def = new Carte(50, 50, 0, 0, new Image(this.getContext(), R.mipmap.bleu_mur_icone_128 ), "", -1);
    private Carte atta = new Carte(50, 50, 0, 0, new Image(this.getContext(), R.mipmap.monstre_sourire), "", -1);

    private final Paint fondDefense, fondAttaque;
    private final Carte carteFond;

    // Création de la surface de dessin
    public GameView(Context context, AttributeSet attri) {
        super(context, attri);
        getHolder().addCallback(this);
        gameLoopThread = new GameLoopThread(this);

        carteFond = new Carte(getContext());
        final int baseColor = ContextCompat.getColor(getContext(), R.color.arenomai_fond_plateau);
        carteFond.setBorderColors(baseColor - 0x070707, baseColor - 0x0E0E0E, baseColor - 0x151515);
        carteFond.setBorder(0.05f);
        fondDefense = new Paint();
        fondDefense.setColor(Color.argb(32, 0, 127, 255));
        fondAttaque = new Paint();
        fondAttaque.setColor(Color.argb(32, 255, 127, 0));

        peintureGrille = new Paint();
        peintureGrille.setStyle(Paint.Style.STROKE);
        peintureGrille.setStrokeWidth(0.01f);
        peintureGrille.setColor(Color.WHITE);
        setTaillePlateau(5, 9, peintureGrille.getStrokeWidth());

        setTypeCarteSelectionnee(TypeCarte.Defense);
    }

    private void setTaillePlateau(int largeur, int hauteur, float epaisseur) {
        epaisseur /= 2;
        largeurPlateau = largeur;
        hauteurPlateau = hauteur;

        pointsGrille = new float[(largeurPlateau+hauteurPlateau+2)*4];
        final float fx = 1.f / largeurPlateau;
        for (int x = 0; x < largeurPlateau + 1; ++x) {
            pointsGrille[x*4] = pointsGrille[x*4 + 2] = x * fx;
            pointsGrille[x*4 + 1] = 0 - epaisseur;
            pointsGrille[x*4 + 3] = 1 + epaisseur;
        }
        final int off = (largeurPlateau + 1) * 4;
        final float fy = 1.f / hauteurPlateau;
        for (int y = 0; y < hauteurPlateau + 1; ++y) {
            pointsGrille[off + y*4 + 1] = pointsGrille[off + y*4 + 3] = y * fy;
            pointsGrille[off + y*4] = 0 - epaisseur;
            pointsGrille[off + y*4 + 2] = 1 + epaisseur;
        }
    }

    private void dessinerGrille(Canvas canvas) {
        // canvas.drawLines(pointsGrille, peintureGrille);
        Matrix m = new Matrix();
        for (int x = 0; x < largeurPlateau; ++x) {
            for (int y = 0; y < hauteurPlateau; ++y) {
                m.setScale(1.f / largeurPlateau, 1.f / hauteurPlateau);
                m.postTranslate((float) x / largeurPlateau,
                        (float) y / hauteurPlateau);
                canvas.save();
                canvas.concat(m);
                if (peutPlacerCarte(TypeCarte.Attaque, x, y)) {
                    carteFond.setCouleurFond(fondAttaque);
                } else if (peutPlacerCarte(TypeCarte.Defense, x, y)) {
                    carteFond.setCouleurFond(fondDefense);
                } else {
                    carteFond.setCouleurFond(null);
                }
                carteFond.dessiner(canvas);
                canvas.restore();
            }
        }
    }

    // Fonction qui "dessine" un écran de jeu
    public void doDraw(Canvas canvas) {
        if (canvas == null) { return; }
        CanvasMatrixProxy canMtx = new CanvasMatrixProxy(canvas);

        // On efface l'écran
        canvas.drawColor(Color.WHITE);
        if (fondCouleur != null)
            canvas.drawBitmap(fondCouleur, 0, 0, null);

        // Dessine la grille
        canMtx.save();
        canMtx.concat(matricePlateau);

        dessinerGrille(canvas);

        Matrix Adef = new Matrix();
        Adef.postScale(1.f / largeurPlateau, 1.f / hauteurPlateau);
        Adef.postTranslate(0.1f, 1.05f);
        SAdef = Adef;
        canMtx.save();
        canMtx.concat(Adef);
        def.dessiner(canvas);
        canMtx.restore();

        Matrix Aatta = new Matrix();
        Aatta.postScale(1.f / largeurPlateau, 1.f / hauteurPlateau);
        Aatta.postTranslate(0.5f, 1.05f);
        SAatta = Aatta;
        canMtx.save();
        canMtx.concat(Aatta);
        atta.dessiner(canvas);
        canMtx.restore();

        ArrayList<Carte> suppr = new ArrayList<>();
        // Dessine les carte et les fait se déplacer.
        synchronized (monstres) {
            for (Monstre a : monstres) {
                Matrix matriceMonstre = new Matrix();
                matriceMonstre.postScale(1.f / largeurPlateau, 1.f / hauteurPlateau);
                matriceMonstre.postTranslate((float) a.getPosX() / largeurPlateau,
                        (float) a.getPosY() / hauteurPlateau);
                canMtx.save();
                canMtx.concat(matriceMonstre);
                a.dessiner(canvas);
                canMtx.restore();

                if (a.getDef() <= 0){
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
                canMtx.save();
                canMtx.concat(matricedefenses);
                a.dessiner(canvas);
                canMtx.restore();

                if (a.getDef() <= 0){
                    suppr.add(a);
                }
            }
        }

        for(Sort a : sorts){
            a.dessiner(canvas);
        }

        for(Carte a : suppr){

            synchronized (monstres){
                if(a instanceof Monstre ){
                    monstres.remove(a);
                }
            }

            synchronized (defenses){
                if(a instanceof Defense){
                    defenses.remove(a);
                }
            }
        }

        canMtx.restore();
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée immédiatement après la création de l'objet SurfaceView
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // création du processus GameLoopThread si cela n'est pas fait
        if (gameLoopThread.getState() == Thread.State.TERMINATED) {
            gameLoopThread = new GameLoopThread(this);
        }
        gameLoopThread.setRunning(true);
        gameLoopThread.start();
    }



    public void mouvement(){

        // Déplace tous les monstres sauf si il y a quelque chose à leurs emplacements d'arrivée
        synchronized (monstres) {

            for (Monstre m1 : monstres) {
                boolean avancer = true;
                for (Monstre m2 : monstres) {
                    if (m2.getPosY() == m1.posAfterMoov() && m2.getPosX() == m1.getPosX() && m2 != m1) {
                        avancer = false;

                        if(m1.getAppartenance() != m2.getAppartenance()){
                            m1.pertDef(m2.getDamage());
                        }
                    }
                }

                synchronized (defenses) {

                    for (Defense d : defenses) {
                        if (d.getPosY() == m1.posAfterMoov() && d.getPosX() == m1.getPosX()) {
                            avancer = false;

                            if(m1.getAppartenance() != d.getAppartenance()){
                                m1.pertDef(d.getDamage());
                                d.pertDef(m1.getDamage());
                            }
                        }
                    }

                }

                if(m1.posAfterMoov() < 0){
                    avancer = false;
                    ennemi.pertePv(m1.getDamage());
                    m1.pertDef(ennemi.getDamage());
                }

                if(m1.posAfterMoov() > 8){
                    avancer = false;
                    ami.pertePv(m1.getDamage());
                    m1.pertDef(ami.getDamage());
                }

                if(avancer){
                    m1.moov();
                }
            }

        }

        synchronized (monstres){
            synchronized (defenses){
                ennemi.IA(monstres, defenses, this.getContext());
            }
        }
        cartePosee = null;
    }

    private void setTypeCarteSelectionnee(TypeCarte tc) {
        typeCarteSelectionnee = tc;
        final Carte actif = tc == TypeCarte.Attaque ? atta : def;
        final Carte inactif = tc == TypeCarte.Attaque ? def : atta;
        actif.setBorderColors(0xFFE0E000, 0xFFC0C000, 0xFFA0A000);
        inactif.setBorderColors(0xFF404040, 0xFF303030, 0xFF202020);
    }

    public boolean peutPlacerCarte(TypeCarte tc, int x, int y) {
        if (x < 0 || x >= largeurPlateau) {
            return false;
        }
        switch (tc) {
            case Defense:
                return y >= hauteurPlateau * 5 / 9 && y < hauteurPlateau * 7 / 9;
            case Attaque:
                return y >= hauteurPlateau * 7 / 9 && y < hauteurPlateau;
        }
        return false;
    }

    // Gère les touchés sur l'écran
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Position du doigt sur l'écran
        final int currentX = (int) event.getX();
        final int currentY = (int) event.getY();

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

                //On test le choix de la carte du joueurs
                Matrix plateauSAdef = new Matrix(SAdef);
                plateauSAdef.postConcat(matricePlateau);
                Matrix inv = new Matrix();
                if (!plateauSAdef.invert(inv)) {
                    throw new RuntimeException("matrice n'est pas inversible");
                }
                points = new float[] { currentX, currentY };
                inv.mapPoints(points);
                if(points[0] >= 0 && points[0] <= 1 && points[1] >= 0 && points[1] <= 1){
                    setTypeCarteSelectionnee(TypeCarte.Defense);
                }


                Matrix plateauSAatta = new Matrix(SAatta);
                plateauSAatta.postConcat(matricePlateau);
                inv = new Matrix();
                if (!plateauSAatta.invert(inv)) {
                    throw new RuntimeException("matrice n'est pas inversible");
                }
                points = new float[] { currentX, currentY };
                inv.mapPoints(points);
                if(points[0] >= 0 && points[0] <= 1 && points[1] >= 0 && points[1] <= 1){
                    setTypeCarteSelectionnee(TypeCarte.Attaque);
                }


                /*if(points[0] >= 0 && points[0] <= 1 && points[1] >= 0 && points[1] <= 1){
                    typeCarteSelectionnee = 3;
                }*/

                //fin du test

                Monstre atta = null;
                Defense def = null;

                //Cas ou on n'a pas encore poser de carte
                if (cartePosee == null) {

                    //On verifie qu'il n'y a pas déjà une carte a cette endroi
                    boolean pasOcuper = true;

                    synchronized (monstres) {

                        for (Monstre m : monstres) {
                            if (m.getPosY() == y && m.getPosX() == x) {
                                pasOcuper = false;
                            }
                        }
                    }

                    synchronized (defenses) {
                        for (Defense d : defenses) {
                            if (d.getPosY() == y && d.getPosX() == x) {
                                pasOcuper = false;
                            }
                        }
                    }


                    //on place la carte a l'endroit ou le joueures a appuiller
                    if (x >= 0 && x < largeurPlateau && y >= 0 && y < hauteurPlateau && pasOcuper) {
                        //Le joueurs pose une carte

                        // Crée une carte
                        switch (typeCarteSelectionnee) {
                            case Defense:
                                if (peutPlacerCarte(TypeCarte.Defense, x, y)) {
                                    def = new Defense(1, 1, x, y, 4, 0, "MÛRE", new Image(this.getContext(), R.mipmap.bleu_mur_icone_128), 1);
                                    cartePosee = def;
                                }
                                break;
                            case Attaque:
                                if (peutPlacerCarte(TypeCarte.Attaque, x, y)) {
                                    atta = new Monstre(1, 1, x, y, 1, 2, 2, "Monstre pas bô", new Image(this.getContext(), R.mipmap.monstre_sourire), 1);
                                    cartePosee = atta;
                                }
                                break;
                        }

                        if (cartePosee != null) {
                            cartePosee.setBorderColors(0xFF0000E0, 0xFF0000C0, 0xFF0000A0);
                        }

                        /*if (x >= 0 && x < largeurPlateau && y >= 0 && y < hauteurPlateau) {
                            if (typeCarteSelectionnee == 3) {
                                atta = new Monstre(1, 1, x, y, 1, 0, 2, "Monstre pas bô", new Image(this.getContext(), R.mipmap.monstre_sourire));
                            }
                        }*/

                        // On ajoute le monstre à la liste
                        synchronized (monstres) {
                            if (atta != null) {
                                monstres.add(atta);
                            }
                        }

                        // On ajoute la defence à la liste
                        synchronized (defenses) {
                            if (def != null) {
                                defenses.add(def);
                            }
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
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée à la CREATION et MODIFICATION et ONRESUME de l'écran
    // nous obtenons ici la largeur/hauteur de l'écran en pixels
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
        float ph = h * 0.85f;
        final float ratioEcran = w / ph,
                ratioPlateau = ((float)largeurPlateau) / hauteurPlateau;
        if (ratioEcran > ratioPlateau) {
            final float largeur = ph / hauteurPlateau * largeurPlateau;
            matricePlateau.setScale(largeur, ph);
            matricePlateau.postTranslate((w - largeur) / 2, 0);
        } else {
            final float hauteur = ((float)w) / largeurPlateau * hauteurPlateau;
            matricePlateau.setScale(w, hauteur);
            matricePlateau.postTranslate(0, (ph - hauteur) / 2);
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

    public void stop() {
        if (ami.getPv() < 0 || ennemi.getPv() < 0) {
            gameLoopThread.setRunning(false);
            final GameActivity context = (GameActivity) this.getContext();

            // On change le message en fonction de si le joueur a gagné ou perdu.
            String msg = "";
            if (ami.getPv() < 0) {
                msg = "Vous n'êtes vraiment pas fort...\nVous avez perdu contre une IA qui joue random.";
            }
            if (ennemi.getPv() < 0) {
                msg = "Vous êtes vraiment une exception...\nVous avez gagné contre une IA qui joue random.";
            }
            final String MSG = msg;

            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setMessage(MSG);
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id){
                            dialog.cancel();
                            context.finish();
                        }
                    });
                    final AlertDialog alert = alertDialogBuilder.create();
                    alert.show();
                }
            });
        }
    }

} // class GameView
