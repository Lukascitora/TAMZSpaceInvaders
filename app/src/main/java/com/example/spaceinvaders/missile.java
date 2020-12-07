package com.example.spaceinvaders;

import android.graphics.Bitmap;

public class missile extends GameObject {
    public missile( double posX, double posY, Bitmap image) {
        super(1, posX, posY, image);
        speed[1]=-750;
    }

    public void update(long elapsed) {

        pos[1]=pos[1]+((speed[1]*elapsed)/1000);
    }
}
