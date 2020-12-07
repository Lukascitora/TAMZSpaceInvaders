package com.example.spaceinvaders;

import android.graphics.Canvas;
import android.os.Build;
import android.view.SurfaceHolder;

import androidx.annotation.RequiresApi;


public class MainThread extends Thread{
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    public static Canvas canvas;


    public MainThread(SurfaceHolder surfaceHolder,GameView gameView)
    {
        super();
        this.surfaceHolder=surfaceHolder;
        this.gameView=gameView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run()
    {
        long lastTime,current,elapsed;
        lastTime=System.currentTimeMillis();
        while(running){
            current=System.currentTimeMillis();
            elapsed=current-lastTime;
            lastTime=current;
            canvas=null;
            try{
                canvas=this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gameView.update(elapsed);
                    this.gameView.draw(canvas);
                }

            }catch(Exception e){}finally{
                if(canvas!=null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setRunning(boolean isRunning){
        running=isRunning;
    }
}
