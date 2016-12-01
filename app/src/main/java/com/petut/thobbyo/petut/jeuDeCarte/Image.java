package com.petut.thobbyo.petut.jeuDeCarte;

/**
 * Created by Thobbyo on 11/11/2016.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Image
{
    private BitmapDrawable img = null; // image
    private int R_mipmap; // image contenue
    private int IW, IH; // largeur et hauteur de l'image
    private int wEcran, hEcran; // largeur et hauteur de l'écran en pixels

    // contexte de l'application Android
    // il servira à accéder aux ressources
    private final Context mContext;

    // Constructeur d'une image
    public Image(final Context c, int mipmap) {
        mContext=c; // sauvegarde du contexte
        R_mipmap = mipmap;
    }

    // on attribue à l'objet "Image" l'image passée en paramètre
    // w et h sont sa largeur et hauteur définis en pixels
    public BitmapDrawable setImage(final Context c, final int ressource, final int w, final int h) {
        Drawable dr = c.getResources().getDrawable(ressource);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(bitmap, w, h, true));
    }

    // redimensionnement de l'image selon la largeur/hauteur de l'écran passés en paramètre
    public void resize(int wScreen, int hScreen) {
        IW = wScreen;
        IH = hScreen;
        img = setImage(mContext, R_mipmap, IW, IH);
    }

    public void setTailleImage(int W, int H) {
        IW = W;
        IH = H;
    }

    // retourne la largeur de l'image en pixel
    public int getW() {
        return IW;
    }

    // retourne la hauteur de l'image en pixel
    public int getH() {
        return IH;
    }

    // on dessine l'image, en x et y
    public void draw(Canvas canvas, int x, int y)
    {
        if(img==null) {return;}
        canvas.drawBitmap(img.getBitmap(), x, y, null);
    }

}