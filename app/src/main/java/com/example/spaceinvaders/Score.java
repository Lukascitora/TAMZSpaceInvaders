package com.example.spaceinvaders;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Score extends AppCompatActivity {

    DBHelper scoreDB;
    ListView scoreListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        scoreDB=new DBHelper(this);
        ArrayList<String> scoreList=new ArrayList<String>();
        scoreList=scoreDB.getItemList();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,scoreList);
        scoreListView=findViewById(R.id.listViewScores);
        scoreListView.setAdapter(arrayAdapter);
    }
}