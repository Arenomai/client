package com.petut.thobbyo.petut.jeuDeCarte;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import com.petut.thobbyo.petut.R;

import static android.graphics.Color.*;

public class Carte {

    protected int tailleW;
    protected int tailleH;
    protected int posX;
    protected int posY;
    protected Image img;

    private final int borderColors[] = new int[3];
    private Bitmap bmpBord, bmpCoin;
    private float border;
    private int bw, cw;
    private Paint bordsPaint;
    private BitmapShader bordBS, coinBS;
    protected int appartenance;

    public Carte(int tailleH, int tailleW, int posX, int posY, Image img, String nom, int appartenance){
        this.tailleH = tailleH;
        this.tailleW = tailleW;
        this.posX = posX;
        this.posY = posY;
        this.img = img;
        this.img.load();
        this.appartenance = appartenance;
        setBorderColors(RED, GREEN, BLUE);
    }

    private Bitmap replaceColors(final Bitmap src, int c1, int c2, int c3) {
        int pixels[] = new int[src.getWidth() * src.getHeight()];
        src.getPixels(pixels, 0, src.getWidth(), 0, 0, src.getWidth(), src.getHeight());
        final int c1r = red(c1), c1g = green(c1), c1b = blue(c1), c1a = alpha(c1),
                c2r = red(c2), c2g = green(c2), c2b = blue(c2), c2a = alpha(c2),
                c3r = red(c3), c3g = green(c3), c3b = blue(c3), c3a = alpha(c3);
        for (int i = 0; i < src.getWidth() * src.getHeight(); ++i) {
            final int r = red(pixels[i]),
                    g = green(pixels[i]),
                    b = blue(pixels[i]),
                    a = alpha(pixels[i]);
            pixels[i] = argb(
                    a,
                    (r * c1r)/255 + (g * c2r)/255 + (b * c3r)/255,
                    (r * c1g)/255 + (g * c2g)/255 + (b * c3g)/255,
                    (r * c1b)/255 + (g * c2b)/255 + (b * c3b)/255);
        }
        return Bitmap.createBitmap(pixels, src.getWidth(), src.getHeight(), src.getConfig());
    }

    public void setBorderColors(int c1, int c2, int c3) {
        final Context c = img.getContext();
        Drawable dr = ContextCompat.getDrawable(c, R.drawable.bordure_carte_bord);
        bmpBord = replaceColors(((BitmapDrawable) dr).getBitmap(), c1, c2, c3);
        dr = ContextCompat.getDrawable(c, R.drawable.bordure_carte_coin);
        bmpCoin = replaceColors(((BitmapDrawable) dr).getBitmap(), c1, c2, c3);
        bw = bmpBord.getWidth();
        cw = bmpCoin.getWidth();
        setBorder(0.1f);
        bordsPaint = new Paint();
        // bordsPaint.setAntiAlias(true);
        bordsPaint.setFilterBitmap(true);
        // bordsPaint.setDither(true);
        bordBS = new BitmapShader(bmpBord, Shader.TileMode.CLAMP, Shader.TileMode.REPEAT);
        coinBS = new BitmapShader(bmpCoin, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    }

    public void setTaille(int Ntaille){
        img.setTailleImage(Ntaille, Ntaille);
    }
    public void setTaille(int Htaille, int Wtaille){
        img.setTailleImage(Htaille, Wtaille);
    }
    public void setPos(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public void setImg(Image img){this.img = img ;}

    public final void setBorder(float b) {
        border = b;
    }

    private void dessinerCadre(Canvas canvas) {
        final Matrix m = new Matrix();
            /* Bords */
        {
            bordsPaint.setShader(bordBS);
            final float z = 11 * 3;
            final float borderL = border, // * z/bmpCoin.getWidth(),
                    sx = border / bw, sy = sx * 2;
            // Gauche
            m.setScale(sx, sy);
            bordBS.setLocalMatrix(m);
            canvas.drawRect(new RectF(0, borderL, border, 1 - borderL), bordsPaint);
            // Droite
            m.setScale(-sx, sy);
            m.postTranslate(1, 0);
            bordBS.setLocalMatrix(m);
            canvas.drawRect(new RectF(1 - border, borderL, 1, 1 - borderL), bordsPaint);
            // Haut
            m.setRotate(90);
            m.postScale(sy, sx);
            bordBS.setLocalMatrix(m);
            canvas.drawRect(new RectF(borderL, 0, 1 - borderL, border), bordsPaint);
            // Bas
            m.setRotate(270);
            m.postScale(sy, sx);
            m.postTranslate(0, 1);
            bordBS.setLocalMatrix(m);
            canvas.drawRect(new RectF(borderL, 1 - border, 1 - borderL, 1), bordsPaint);
        }
            /* Coins */
        if (false) {
            bordsPaint.setShader(coinBS);
            final int z = 5 * 3;
            final float sx = border / bw, sy = sx;
            // Bas Gauche
            m.setScale(sx, sy);
            m.postTranslate(0, 1-sy);
            coinBS.setLocalMatrix(m);
            canvas.drawRect(new RectF(0, 1-sy, sx, 1), bordsPaint);
            // Haut Gauche
            m.setScale(sx/cw, -sy/cw);
            m.postTranslate(0, sy);
            coinBS.setLocalMatrix(m);
            canvas.drawRect(new RectF(0, 0, sx, sy), bordsPaint);
            // Haut Droit
            m.setScale(-sx/cw, -sy/cw);
            m.postTranslate(1, sy);
            coinBS.setLocalMatrix(m);
            canvas.drawRect(new RectF(1-sx, 0, 1, sy), bordsPaint);
            // Bas Droit
            m.setScale(-sx/cw, sy/cw);
            m.postTranslate(1, 1-sy);
            coinBS.setLocalMatrix(m);
            canvas.drawRect(new RectF(1-sx, 1-sy, 1, 1), bordsPaint);
        }
    }

    public void dessiner(Canvas canvas) {
        canvas.drawBitmap(img.getBitmap(), null, new Rect(0, 0, 1, 1), null);
        dessinerCadre(canvas);
    }

    public Image getImg(){return img ;}
    
    public int getPosX(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }

    public int getAppartenance(){
        return appartenance;
    }

}
