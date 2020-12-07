package com.example.spaceinvaders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button play,score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        play=findViewById(R.id.play);
        score=findViewById(R.id.score);

    }

    public void onButton(View view)
    {
        Intent intent;
        if (view==play){
            intent=new Intent(this,Game.class);
        }
        else {
            intent=new Intent(this,Score.class);
        }
        startActivity(intent);
    }
}