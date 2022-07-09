package com.example.data.databases

import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.widget.Toast
import com.example.data.storage.reviews.Review

class DBHelperReview(private val context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE_REVIEW =
            "create table $TABLE_REVIEW ($KEY_ID integer primary key autoincrement, $KEY_TITLE text, $KEY_DESCRIPTION text, $KEY_CATEGORY text);"
        db.execSQL(CREATE_TABLE_REVIEW)

        db.execSQL(
            "create table " + TABLE_POLLS + " (" + COLUMN_ID
                    + " integer primary key autoincrement, " + COLUMN_TITLE + " text, "
                    + COLUMN_VAR1 + " text, " + COLUMN_VAR2 + " text, " + COLUMN_VAR3 + " text" + ");"
        )
        db.execSQL(
            "create table " + TABLE_POLLS_ANSWERS + " (" + ANSWERS_ID
                    + " integer primary key autoincrement, " + ANSWERS_TITLE + " text, "
                    + ANSWERS_VAR1 + " text, " + ANSWERS_ANS1 + " text, " + ANSWERS_VAR2 + " text, "
                    + ANSWERS_ANS2 + " text, " + ANSWERS_VAR3 + " text, " + ANSWERS_ANS3 + " text" + ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists $TABLE_REVIEW")
        db.execSQL("drop table if exists $TABLE_POLLS")
        db.execSQL("drop table if exists $TABLE_POLLS_ANSWERS")
        onCreate(db)
    }

    fun readAllDataReview(): Cursor? {
        val query = "SELECT * FROM $TABLE_REVIEW"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    fun readAllDataPolls(): Cursor? {
        val query = "SELECT * FROM $TABLE_POLLS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    fun readAllDataAnswers(): Cursor? {
        val query = "SELECT * FROM $TABLE_POLLS_ANSWERS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    fun updateData(row_id: String, title: String?, description: String?) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(KEY_TITLE, title)
        cv.put(KEY_DESCRIPTION, description)
        val result = db.update(TABLE_REVIEW, cv, "_id=?", arrayOf(row_id)).toLong()
        if (result == -1L) {
            Toast.makeText(context, "Не удалось обновить отзыв!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Отзыв обновлен!", Toast.LENGTH_SHORT).show()
        }
    }

    fun addReviewDB(review: Review) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(KEY_TITLE, review.title)
        cv.put(KEY_DESCRIPTION, review.description)
        cv.put(KEY_CATEGORY, review.category)
        val result = db.insert(TABLE_REVIEW, null, cv)
        if (result == -1L) {
            Toast.makeText(context, "Не получилось!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Получилось!", Toast.LENGTH_SHORT).show()
        }
    }

    fun addPollsDB(title: String?, var1: String?, var2: String?, var3: String?) {
        val db1 = this.writableDatabase
        val cv1 = ContentValues()
        cv1.put(COLUMN_TITLE, title)
        cv1.put(COLUMN_VAR1, var1)
        cv1.put(COLUMN_VAR2, var2)
        cv1.put(COLUMN_VAR3, var3)
        val result1 = db1.insert(TABLE_POLLS, null, cv1)
        if (result1 == -1L) {
            Toast.makeText(context, "Не получилось!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Получилось!", Toast.LENGTH_SHORT).show()
        }
    }

    fun addPollsAnswerDB(title: String?, var1: String?, ans1: String?, var2: String?, ans2: String?, var3: String?, ans3: String?) {
        val db2 = this.writableDatabase
        val cv2 = ContentValues()
        cv2.put(ANSWERS_TITLE, title)
        cv2.put(ANSWERS_VAR1, var1)
        cv2.put(ANSWERS_ANS1, ans1)
        cv2.put(ANSWERS_VAR2, var2)
        cv2.put(ANSWERS_ANS2, ans2)
        cv2.put(ANSWERS_VAR3, var3)
        cv2.put(ANSWERS_ANS3, ans3)
        val result2 = db2.insert(TABLE_POLLS_ANSWERS, null, cv2)
        if (result2 == -1L) {
            Toast.makeText(context, "Не получилось!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Анкета отправлена!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val DATABASE_VERSION = 6
        private const val DATABASE_NAME = "reviewDb"
        private const val TABLE_REVIEW = "reviews"
        private const val TABLE_POLLS = "polls"
        private const val TABLE_POLLS_ANSWERS = "answers"
        private const val KEY_ID = "_id"
        private const val KEY_TITLE = "title"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_CATEGORY = "category"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_VAR1 = "var1"
        private const val COLUMN_VAR2 = "var2"
        private const val COLUMN_VAR3 = "var3"
        private const val ANSWERS_ID = "_id"
        private const val ANSWERS_TITLE = "title"
        private const val ANSWERS_VAR1 = "var1"
        private const val ANSWERS_ANS1 = "ans1"
        private const val ANSWERS_VAR2 = "var2"
        private const val ANSWERS_ANS2 = "ans2"
        private const val ANSWERS_VAR3 = "var3"
        private const val ANSWERS_ANS3 = "ans3"
    }
}