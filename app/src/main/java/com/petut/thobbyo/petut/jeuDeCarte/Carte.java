package com.petut.thobbyo.petut.jeuDeCarte;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
public class Carte {
    protected int tailleW;
    protected int tailleH;
    protected int posX;
    protected int posY;
    protected Image img;
    public Carte(int tailleH, int tailleW, int posX, int posY, Image img, String nom){
        this.tailleH = tailleH;
        this.tailleW = tailleW;
        this.posX = posX;
        this.posY = posY;
        this.img = img;
        this.img.load();
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
    public void dessiner(Canvas canvas) {
        canvas.drawBitmap(img.getBitmap(), null, new Rect(0, 0, 1, 1), null);
    }
    public Image getImg(){return img ;}
    public int getPosX(){
        return posX;
    }
    public  int getPosY(){
        return posY;
    }
}