package com.example.spaceinvaders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DBSCORES.db";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE scores " + "(id INTEGER PRIMARY KEY, date TEXT, score INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS scores");
        onCreate(db);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean insertItem(int score)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("score",score);
        LocalDateTime dateTime=LocalDateTime.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatedDate=dateTime.format(formatter);
        contentValues.put("date",formatedDate);
        long insertedId=db.insert("scores",null,contentValues);
        if(insertedId==-1)return false;
        return true;
    }

    public ArrayList<String> getItemList()
    {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from scores", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            String date = res.getString(res.getColumnIndex("date"));
            int score=res.getInt(res.getColumnIndex("score"));
            arrayList.add("date: "+date + "  score: "+score);
            res.moveToNext();
        }

        return arrayList;
    }

    public int removeAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int nRecordDeleted = 0;
        nRecordDeleted=db.delete("scores",null,null);
        return nRecordDeleted;
    }
}
