package com.poly.dotsave;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.poly.dotsave.gson.Video;
import com.poly.dotsave.model.History;

public class SqliteHelper extends SQLiteOpenHelper {

    public SqliteHelper(@Nullable Context context) {
        super(context, "dq.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String table = "Create table abc(id interger primary key autoincrement,link text, time text)";
        sqLiteDatabase.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insert(History history){

    }
}
