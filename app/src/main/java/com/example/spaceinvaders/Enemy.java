package com.example.spaceinvaders;

import android.graphics.Bitmap;
import android.util.Log;

public class Enemy extends GameObject {
    //id 0-3
    public Enemy(int hp, double posX, double posY, Bitmap image) {
        super(hp, posX,posY,image);
        speed[0]=50;
        speed[1]=0;
    }

    public void update(long elapsed,int phase)
    {
        pos[0]=pos[0]+((speed[0]*elapsed)/1000)*phase;
        pos[1]=pos[1]+((speed[1]*elapsed)/1000)*phase;
        //Log.d("elapsed:", String.valueOf(elapsed));
    }
}
