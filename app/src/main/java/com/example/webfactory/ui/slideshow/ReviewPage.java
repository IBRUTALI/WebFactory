package com.example.webfactory.ui.slideshow;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.webfactory.Databases.DBHelperReview;
import com.example.webfactory.R;

public class ReviewPage extends AppCompatActivity {

    EditText reviewTitle, reviewDescription;
    Button btnDeleteReview, btnUpdateReview;
    String id, title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);

        reviewTitle = findViewById(R.id.reviewPageTitle);
        reviewDescription = findViewById(R.id.reviewPageDescription);

        getAndSetIntentData();


        btnUpdateReview = findViewById(R.id.updateReview);

        btnUpdateReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelperReview dBHelperReview = new DBHelperReview(ReviewPage.this);
                title=reviewTitle.getText().toString().trim();
                description=reviewDescription.getText().toString().trim();
                dBHelperReview.updateData(id, title, description);
            }
        });

        btnDeleteReview = findViewById(R.id.deleteReview);

        btnDeleteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteReview();
            }
        });



    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("reviewId") && getIntent().hasExtra("reviewTitle") && getIntent().hasExtra("reviewDescription")){

            //Получение интента
            id = getIntent().getStringExtra("reviewId");
            title = getIntent().getStringExtra("reviewTitle");
            description = getIntent().getStringExtra("reviewDescription");

            //Установка интента
            reviewTitle.setText(title);
            reviewDescription.setText(description);
        }else{
            Toast.makeText(this, "Нет данных", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteReview() {



    }
}


