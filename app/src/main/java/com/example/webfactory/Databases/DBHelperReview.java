package com.example.webfactory.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelperReview extends SQLiteOpenHelper {

    private Context context;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "reviewDb";
    private static final String TABLE_REVIEW = "reviews";
    private static final String TABLE_POLLS = "polls";
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_VAR1 = "var1";
    private static final String COLUMN_VAR2 = "var2";
    private static final String COLUMN_VAR3 = "var3";

    public DBHelperReview(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_REVIEW + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_TITLE + " text, " + KEY_DESCRIPTION + " text" + ");");

        db.execSQL("create table " + TABLE_POLLS + " (" + COLUMN_ID
                + " integer primary key autoincrement, " + COLUMN_TITLE + " text, "
                + COLUMN_VAR1 + " text, " + COLUMN_VAR2 + " text, " + COLUMN_VAR3 + " text" + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_REVIEW);
        db.execSQL("drop table if exists " + TABLE_POLLS);

        onCreate(db);

    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_REVIEW;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readAllDataP(){
        String query = "SELECT * FROM " + TABLE_POLLS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor1 = null;
        if(db != null){
            cursor1 = db.rawQuery(query, null);
        }
        return cursor1;
    }

    public void updateData(String row_id, String title, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE, title);
        cv.put(KEY_DESCRIPTION, description);

        long result = db.update(TABLE_REVIEW, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Не удалось обновить отзыв!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Отзыв обновлен!", Toast.LENGTH_SHORT).show();
        }

    }

    public void addReview(String title, String description){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_TITLE, title);
        cv.put(KEY_DESCRIPTION, description);
        long result = db.insert(DBHelperReview.TABLE_REVIEW, null, cv);
        if(result == -1){
            Toast.makeText(context, "Не получилось!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Получилось!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addPolls(String title, String var1, String var2, String var3){

        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues cv1 = new ContentValues();

        cv1.put(COLUMN_TITLE, title);
        cv1.put(COLUMN_VAR1, var1);
        cv1.put(COLUMN_VAR2, var2);
        cv1.put(COLUMN_VAR3, var3);
        long result1 = db1.insert(DBHelperReview.TABLE_POLLS, null, cv1);
        if(result1 == -1){
            Toast.makeText(context, "Не получилось!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Получилось!", Toast.LENGTH_SHORT).show();
        }
}
}

