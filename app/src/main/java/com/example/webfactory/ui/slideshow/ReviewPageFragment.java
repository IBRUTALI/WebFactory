package com.example.webfactory.ui.slideshow;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.webfactory.Databases.DBHelperReview;
import com.example.webfactory.R;
import com.example.webfactory.databinding.FragmentPollsBinding;
import com.example.webfactory.databinding.FragmentReviewPageBinding;

public class ReviewPageFragment extends Fragment {
    private FragmentReviewPageBinding binding;
    private String id, title, description;

    public ReviewPageFragment() {
        super(R.layout.fragment_review_page);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReviewPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getAndSetIntentData();

        binding.updateReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelperReview dBHelperReview = new DBHelperReview(getContext());
                title= binding.reviewPageTitle.getText().toString().trim();
                description= binding.reviewPageDescription.getText().toString().trim();
                dBHelperReview.updateData(id, title, description);
            }
        });

        binding.deleteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteReview();
            }
        });


        return root;
    }

    void getAndSetIntentData(){
        if(getArguments() != null){
            id = getArguments().getString("reviewId");
            title = getArguments().getString("reviewTitle");
            description = getArguments().getString("reviewDescription");

            binding.reviewPageTitle.setText(title);
            binding.reviewPageDescription.setText(description);
        }else{
            Toast.makeText(getContext(), "Нет данных", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteReview() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


