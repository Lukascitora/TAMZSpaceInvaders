package com.example.spaceinvaders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button playClassic,playEndless,score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        playClassic=findViewById(R.id.playClassic);
        playEndless=findViewById(R.id.playEndless);
        score=findViewById(R.id.score);

    }

    public void onButton(View view)
    {
        Intent intent;
        if (view==playClassic){
            intent=new Intent(this,Game.class);
            boolean endless=false;
            intent.putExtra("Endless",endless);
        }
        else if(view==playEndless)
        {
            intent=new Intent(this,Game.class);
            boolean endless=true;
            intent.putExtra("Endless",endless);
        }
        else {
            intent=new Intent(this,Score.class);
        }
        startActivity(intent);
    }
}