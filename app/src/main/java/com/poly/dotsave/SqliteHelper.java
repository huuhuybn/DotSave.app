package com.poly.dotsave;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.poly.dotsave.gson.Video;
import com.poly.dotsave.model.History;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {

    public SqliteHelper(@Nullable Context context) {
        super(context, "dq.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String table = "Create table abc(id INTEGER primary key autoincrement,link text,title text, thumb text ,time text)";
        sqLiteDatabase.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert(History history) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("link", history.link);
        contentValues.put("time", history.time);
        contentValues.put("title", history.title);
        contentValues.put("thumb", history.thumb);
        getReadableDatabase().insert("abc", null, contentValues);
    }

    public void delete(int id) {
        getReadableDatabase().delete("abc", "id=?", new String[]{String.valueOf(id)});
    }

    public List<History> getAllHistories() {
        List<History> histories = new ArrayList<>();
        String query = "Select * from abc order by time desc";
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String link = cursor.getString(1);
                String title = cursor.getString(2);
                String thumb = cursor.getString(3);
                String time = cursor.getString(4);
                History history = new History(id, link, title, thumb, time);
                histories.add(history);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return histories;
    }
}
