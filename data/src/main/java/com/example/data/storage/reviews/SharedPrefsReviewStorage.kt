package com.example.data.storage.reviews

import android.content.Context
import android.widget.Toast
import com.example.data.databases.DBHelperReview

class SharedPrefsReviewStorage(private val context: Context) : ReviewStorage {

    private var dbHelperReview: DBHelperReview = DBHelperReview(context)

    override fun addReview(review: Review) {
        dbHelperReview.addReviewDB(review)
    }

    override fun readReview(): ArrayList<Review>{
        val cursor = dbHelperReview.readAllDataReview()
        val reviewList = ArrayList<Review>()

        if (cursor != null) {
            if (cursor.count == 0) {
                Toast.makeText(context, "Нет данных ", Toast.LENGTH_SHORT).show()
            } else {
                while (cursor.moveToNext()) {
                    reviewList.add(
                        Review(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                        )
                    )
                }
            }
        }

        return reviewList

    }

}