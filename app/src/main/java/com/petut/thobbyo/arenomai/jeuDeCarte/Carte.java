package com.petut.thobbyo.arenomai.jeuDeCarte;
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
import com.petut.thobbyo.arenomai.R;

import static android.graphics.Color.*;

public class Carte {

    protected int tailleW;
    protected int tailleH;
    protected int posX;
    protected int posY;
    protected Context ctx;
    protected Image img;

    private final int borderColors[] = new int[3];
    private Bitmap bmpBord, bmpCoin;
    private float border;
    private int bw, cw;
    private Paint bordsPaint;
    private BitmapShader bordBS, coinBS;

    private Paint couleurFond;

    protected int appartenance;

    public Carte(Context ctx) {
        this.ctx = ctx;
        setBorderColors(RED, GREEN, BLUE);
    }

    public Carte(Image img, String nom, int appartenance) {
        this(img.getContext());
        this.img = img;
        this.img.load();
        this.appartenance = appartenance;
    }

    public Carte(int tailleH, int tailleW, int posX, int posY, Image img, String nom, int appartenance) {
        this(img, nom, appartenance);
        this.tailleH = tailleH;
        this.tailleW = tailleW;
        this.posX = posX;
        this.posY = posY;
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
        final Context c = ctx;
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

    public float getBorder() {
        return border;
    }

    private void dessinerCadre(Canvas canvas) {
        final Matrix m = new Matrix();
        /* Bords */
        {
            bordsPaint.setShader(bordBS);
            final float borderL = 0,
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
            // border / bw * 5*3 == sx * border * (cw/bw) / cw * 5*3
            //       border / bw == sx * border * (cw/bw) / cw
            //       border / bw == sx * border * 1/bw * cw / cw
            //       border / bw == sx * border / bw
            final float sx = border / cw, sy = sx;
            // Bas Gauche
            m.setScale(sx, sy);
            m.postTranslate(0, 1-sy);
            canvas.save();
            canvas.concat(m);
            canvas.drawRect(new RectF(0, 0, 1, 1), bordsPaint);
            canvas.restore();
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
        if (img != null) {
            canvas.drawBitmap(img.getBitmap(), null, new Rect(0, 0, 1, 1), null);
        }
        if (couleurFond != null) {
            canvas.drawRect(0, 0, 1, 1, couleurFond);
        }
        dessinerCadre(canvas);
    }

    public void setCouleurFond(Paint couleurFond) {
        this.couleurFond = couleurFond;
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
