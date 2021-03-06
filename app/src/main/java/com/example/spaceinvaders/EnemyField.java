package com.example.spaceinvaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class EnemyField {
    ArrayList<Enemy> enemies=new ArrayList<Enemy>();

    int phase=1;
    private int oldY;
    private int inRow;
    private int picWidth;
    private int[]speed=new int[]{50,0};
    private int direction=0;
    private double maxX=0,minX=0,minY=0,maxY=0;
    boolean endlessMode;
    MediaPlayer explosionSound;


    public EnemyField(Context context, int[] enemiesIds, int enemiesInRow, Bitmap[] pictures,boolean endless)
    {
        endlessMode=endless;
        int picWidth=pictures[0].getWidth();
        if (endlessMode==false){
            int enY=3*picWidth,enX=picWidth;
            for(int i=0;i< enemiesIds.length;i++)
            {
                if(i!=0&&i%enemiesInRow==0)
                {
                    enY=enY-picWidth*2;
                    enX=picWidth;
                }
                enemies.add(new Enemy(enemiesIds[i],enX,enY,pictures[enemiesIds[i]-1]));
                enX=enX+picWidth*2;
            }

            this.picWidth=picWidth;
            inRow=enemiesInRow;
        }
        explosionSound=MediaPlayer.create(context,R.raw.explosion2);

        Log.e("endless", String.valueOf(endless));
    }

    void addEnemy(int id,int posX, Bitmap picture)
    {
        Enemy e=new Enemy(id,posX,0-picture.getHeight()/2,picture);
        e.speed[0]=0;
        e.speed[1]=100;
        enemies.add(e);
    }

    void changeDirection(int displayWidth)
    {
        if(maxX>=displayWidth-picWidth&&direction==0){
            oldY= (int) minY;
            direction=1;
            this.speed[0]=0;
            this.speed[1]=50;
            setEnemiesSpeed();
        }

        else if((this.direction==1||direction==3)&&minY>oldY+picWidth)
        {
            if (direction==1){
                this.speed[0]=-50;
                direction=2;}
            else{
                this.speed[0]=50;
                direction=0;}

            this.speed[1]=0;
            setEnemiesSpeed();

        }

        else if(this.direction==2&&minX-picWidth<=0)
        {
            oldY=(int)minY;
            direction=3;
            this.speed[0]=0;
            this.speed[1]=50;
            setEnemiesSpeed();
        }

        if(!enemies.isEmpty()){
            maxX=minX=enemies.get(0).pos[0];
            maxY=minY=enemies.get(0).pos[1];}
    }

    void update(long elapsed,int displayHeight,int displayWidth,Player player) throws IOException {
        if(endlessMode==false){
            this.changeDirection(displayWidth);
        }

        for(Enemy enemy:enemies)
        {
            enemy.update(elapsed,phase);
            if(endlessMode==false){

                if(enemy.pos[0]>maxX)
                {
                    maxX=enemy.pos[0];
                }
                else if(enemy.pos[0]<minX)
                {
                    minX=enemy.pos[0];
                }
                 if(enemy.pos[1]>maxY)
                {
                    maxY=enemy.pos[1];
                }
                else if(enemy.pos[1]<minY)
                {
                    minY=enemy.pos[1];
                }
            }
            checkToDie(enemy,displayHeight,player);


        }
    }

    private void checkToDie(Enemy enemy,int displayHeight,Player player) throws IOException {
        if(enemy.pos[1]>=displayHeight||enemy.hp<=0)
        {
            if(enemy.hp<=0)
            {
                explosionSound.stop();
                explosionSound.prepare();
                player.score+=500;
                explosionSound.start();
            }
            else{player.hp--;}
            enemies.remove(enemy);
        }
    }

    private void setEnemiesSpeed()
    {
        for (Enemy enemy:enemies)
        {
            enemy.speed[0]=this.speed[0];
            enemy.speed[1]=this.speed[1];
        }
    }
    void draw(Canvas canvas)
    {
        for(Enemy enemy:enemies)
        {
            enemy.draw(canvas);
        }
    }

}
