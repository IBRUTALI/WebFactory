package com.example.webfactory;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


//public class ReviewPageFragment extends Fragment {
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View root = inflater.inflate(R.layout.activity_review_page, container, false);
//
////        ConstraintLayout reviewPage = root.findViewById(R.id.reviewPageFragment);
//        TextView reviewTitle = root.findViewById(R.id.reviewPageTitle);
//        TextView reviewDescription = root.findViewById(R.id.reviewPageDescription);
//
//
//        reviewTitle.setText(getActivity().getIntent().getStringExtra("reviewTitle"));
//        reviewDescription.setText(getActivity().getIntent().getStringExtra("reviewDescription"));
//
//        Button btnDeleteReview = root.findViewById(R.id.deleteReview);
//        btnDeleteReview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                deleteReview();
//            }
//        });
//        return root;
//    }
//    private void deleteReview () {
//
//        }
//    }
public class ReviewPage extends AppCompatActivity {

            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);

        ConstraintLayout reviewPage = findViewById(R.id.reviewPage);
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
    private void deleteReview(){

    }
    }


