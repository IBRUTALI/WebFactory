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

    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";

    public DBHelperReview(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_REVIEW + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_TITLE + " text, " + KEY_DESCRIPTION + " text" + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_REVIEW);

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
}

