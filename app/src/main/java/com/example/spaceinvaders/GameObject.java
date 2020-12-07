package com.example.spaceinvaders;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class GameObject {

    Bitmap image;
    public int hp;
    double[] pos=new double[2];
    double[] speed=new double[2];

    public GameObject(int health,double posX,double posY,Bitmap image){
        this.image=image;
        hp=health;
        pos[0]=posX;
        pos[1]=posY;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image,(int)(pos[0]-(image.getWidth()/2)),(int)(pos[1]-(image.getHeight()/2)),null);
    }

}
