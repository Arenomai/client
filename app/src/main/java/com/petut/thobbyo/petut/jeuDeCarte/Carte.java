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
public class Carte {
    protected int tailleW;
    protected int tailleH;
    protected int posX;
    protected int posY;
    protected Image img;
    private Bitmap bmpBord, bmpCoin;
    private float border;
    private int bw, cw;
    private final Paint bordsPaint;
    private final BitmapShader bordBS, coinBS;
    public Carte(int tailleH, int tailleW, int posX, int posY, Image img, String nom){
        this.tailleH = tailleH;
        this.tailleW = tailleW;
        this.posX = posX;
        this.posY = posY;
        this.img = img;
        this.img.load();
        final Context c = img.getContext();
        Drawable dr = ContextCompat.getDrawable(c, R.drawable.bordure_carte_bord);
        bmpBord = ((BitmapDrawable) dr).getBitmap();
        dr = ContextCompat.getDrawable(c, R.drawable.bordure_carte_coin);
        bmpCoin = ((BitmapDrawable) dr).getBitmap();
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
    public  int getPosY(){
        return posY;
    }
}