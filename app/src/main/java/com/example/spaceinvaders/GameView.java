package com.example.spaceinvaders;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * TODO: document your custom view class.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private MainThread thread;
    private Player player;
    private Bitmap[] pictures;
    private Game gameActivity;
    private int enemiesInRow=4;
    private MediaPlayer shootSound,winSound,hitSound;
    private boolean endless;
    DBHelper dbhelper;

    int displayHeight=Resources.getSystem().getDisplayMetrics().heightPixels;
    int displayWight=Resources.getSystem().getDisplayMetrics().widthPixels;

    private Cooldown fire=new Cooldown(600),
            newPhase=new Cooldown(10000);
    private Cooldown spawnEnemy;
    private EnemyField enemyField;

    private ArrayList<missile> missiles=new ArrayList<missile>();

    private static int[] enemyFieldIds={
            1,1,1,1,
            2,2,2,2,
            3,3,3,3,
            4,4,4,4
    };


    int clickPosX;

    public GameView(Context context,boolean endless) {
        super(context);
        this.endless=endless;
        getHolder().addCallback(this);
        thread=new MainThread(getHolder(),this);
        setFocusable(true);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.endless=false;
        getHolder().addCallback(this);
        thread=new MainThread(getHolder(),this);
        setFocusable(true);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.endless=false;
        getHolder().addCallback(this);
        thread=new MainThread(getHolder(),this);
        setFocusable(true);
        init(context);
    }

    void init(Context context) {
        pictures=new Bitmap[6];
        int picWidth=Resources.getSystem().getDisplayMetrics().widthPixels/(enemiesInRow*3);
        pictures[0]= BitmapFactory.decodeResource(getResources(),R.drawable.enemy1);
        pictures[0]=Bitmap.createScaledBitmap(pictures[0], picWidth, picWidth, false);

        pictures[1]= BitmapFactory.decodeResource(getResources(),R.drawable.enemy2);
        pictures[1]=Bitmap.createScaledBitmap(pictures[1], picWidth, picWidth, false);

        pictures[2]= BitmapFactory.decodeResource(getResources(),R.drawable.enemy3);
        pictures[2]=Bitmap.createScaledBitmap(pictures[2], picWidth, picWidth, false);

        pictures[3]= BitmapFactory.decodeResource(getResources(),R.drawable.enemy4);
        pictures[3]=Bitmap.createScaledBitmap(pictures[3], picWidth, picWidth, false);

        pictures[4]= BitmapFactory.decodeResource(getResources(),R.drawable.plane);
        pictures[4]=Bitmap.createScaledBitmap(pictures[4], picWidth, picWidth, false);

        pictures[5]= BitmapFactory.decodeResource(getResources(),R.drawable.bullet);
        pictures[5]=Bitmap.createScaledBitmap(pictures[5],picWidth/4,picWidth/4,false);

        player=new Player(3,displayWight/2,
                displayHeight-pictures[4].getHeight()*2,pictures[4]);

        enemyField=new EnemyField(context,enemyFieldIds,enemiesInRow,new Bitmap[]{pictures[0],pictures[1],pictures[2],pictures[3]},endless);
        gameActivity=(Game)context;
        shootSound=MediaPlayer.create(getContext(),R.raw.shoot);
        hitSound=MediaPlayer.create(getContext(),R.raw.hit);
        winSound=MediaPlayer.create(getContext(),R.raw.win);
        if (!endless){
            newPhase.reset();}
        else{
            spawnEnemy=new Cooldown(3000);
        }
        fire.reset();
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas!=null) {

            player.draw(canvas);

            enemyField.draw(canvas);

            for(missile m:missiles)
            {
                m.draw(canvas);
            }
        }

    }

    private boolean checkOverlap(GameObject o1,GameObject o2)
    {
        if(o1.image==null||o2.image==null)
        {
            throw new IllegalArgumentException("bitmaps cannot be null");
        }
        int x1=(int)(o1.pos[0]-o1.image.getWidth()/2),
                y1=(int)(o1.pos[1]-o1.image.getHeight()/2),
                x2=(int)(o2.pos[0]-o2.image.getWidth()/2),
                y2=(int)(o2.pos[1]-o2.image.getHeight()/2);
        Rect bounds1 = new Rect(x1,y1, x1 + o1.image.getWidth(), y1 + o1.image.getHeight());
        Rect bounds2 = new Rect(x2,y2, x2 + o2.image.getWidth(), y2 + o2.image.getHeight());

        if (Rect.intersects(bounds1,bounds2)){
            Rect collisionBounds = getCollisionBounds(bounds1, bounds2);
            for (int i = collisionBounds.left; i < collisionBounds.right; i++) {
                for (int j = collisionBounds.top; j < collisionBounds.bottom; j++) {
                    int bitmap1Pixel = o1.image.getPixel(i - x1, j - y1);
                    int bitmap2Pixel = o2.image.getPixel(i - x2, j - y2);
                    if (isFilled(bitmap1Pixel) && isFilled(bitmap2Pixel)) {
                        return true;
                    }
                }}}

        return false;
    }

    private static Rect getCollisionBounds(Rect rect1, Rect rect2) {
        int left = Math.max(rect1.left, rect2.left);
        int top = Math.max(rect1.top, rect2.top);
        int right = Math.min(rect1.right, rect2.right);
        int bottom = Math.min(rect1.bottom, rect2.bottom);
        return new Rect(left, top, right, bottom);
    }

    private static boolean isFilled(int pixel) {
        return pixel != Color.TRANSPARENT;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void update(long elapsed) throws IOException {
        player.pos[0]=clickPosX;

        if(!endless){
            newPhase.update(elapsed);
            if(newPhase.readyReset())
            {
                 enemyField.phase=enemyField.phase*2;
            }
        }else{
            spawnEnemy.update(elapsed);
            if(spawnEnemy.readyReset())
            {
                Random r=new Random();
                int id =r.nextInt(5-1)+1;
                int enX=r.nextInt(displayWight-pictures[id-1].getWidth())+pictures[id-1].getWidth()/2;

                enemyField.addEnemy(id,enX,pictures[id-1]);
            }
        }

        enemyField.update(elapsed,displayHeight,displayWight,player);

        fire.update(elapsed);
        if(fire.readyReset())
        {
            shootSound.stop();
            shootSound.prepare();
            missiles.add(new missile(player.pos[0],player.pos[1],pictures[5]));
            shootSound.start();
        }
        for(missile m:missiles) {
            m.update(elapsed);
            if(m.pos[1]<=0||m.pos[1]>=displayHeight)
            {
                missiles.remove(m);
            }
            else
            {
                for(Enemy enemy:enemyField.enemies){
                    if(checkOverlap(m,enemy)){
                        hitSound.stop();
                        hitSound.prepare();
                        hitSound.start();
                        enemy.hp--;
                        missiles.remove(m);
                    }
                }
            }
        }
        for (Enemy en:enemyField.enemies)
        {
            if(checkOverlap(en,player)){
                enemyField.enemies.remove(en);
                player.hp--;
            }
        }


        if (player.hp<=0||(enemyField.enemies.isEmpty()&&enemyField.endlessMode==false))
        {
            if (player.hp>0)
            {
            winSound.start();}
            dbhelper=new DBHelper(getContext());
            dbhelper.insertItem(player.score);
            thread.setRunning(false);
            gameActivity.fin();
        }

    }



    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry=true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            retry=false;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,int format,int width, int height)
    {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction()){

            case MotionEvent.ACTION_MOVE:{
            }
            case MotionEvent.ACTION_DOWN:
            {
                clickPosX=(int)(event.getX());
                break;
            }

        }
        //return super.onTouchEvent(event);
        return true;
    }


}
