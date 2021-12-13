package com.example.webfactory;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReviewPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);

        TextView reviewTitle = findViewById(R.id.reviewPageTitle);
        TextView reviewDescription = findViewById(R.id.reviewPageDescription);

        reviewTitle.setText(getIntent().getStringExtra("reviewTitle"));
        reviewDescription.setText(getIntent().getStringExtra("reviewDescription"));

        Button btnDeleteReview = findViewById(R.id.deleteReview);
        btnDeleteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteReview();
            }
        });

    }

    private void deleteReview() {

    }
}


