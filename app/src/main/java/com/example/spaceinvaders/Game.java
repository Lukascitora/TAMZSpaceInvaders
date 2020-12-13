package com.example.spaceinvaders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Game extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras=getIntent().getExtras();
        boolean en=extras.getBoolean("Endless");
        setContentView(new GameView(this,en));
    }


    public void fin()
    {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}