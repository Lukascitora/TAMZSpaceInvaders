package com.example.spaceinvaders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Player extends GameObject {

    int score=0;
    public Player(int hp, double posX,double posY,Bitmap image) {
        super( hp,  posX, posY,image);

    }



    public void update()
    {

    }
}