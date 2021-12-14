package com.example.webfactory.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webfactory.R;
import com.example.webfactory.ReviewPage;
import com.example.webfactory.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    ArrayList review_id, review_title, review_description;
    Activity activity;

    public CategoryAdapter(Activity activity, Context context, ArrayList review_id, ArrayList review_title, ArrayList review_description) {
        this.activity = activity;
        this.context = context;
        this.review_id = review_id;
        this.review_title = review_title;
        this.review_description = review_description;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View categoryItems = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(categoryItems);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        holder.categoryTitle.setText(String.valueOf(review_title.get(position)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReviewPage.class);
                intent.putExtra("reviewId", String.valueOf(review_id.get(position)));
                intent.putExtra("reviewTitle", String.valueOf(review_title.get(position)));
                intent.putExtra("reviewDescription", String.valueOf(review_description.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return review_id.size();
    }

    public static final class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView categoryTitle;
        ConstraintLayout reviewFragment;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTitle = itemView.findViewById(R.id.categoryTitle);
            reviewFragment = itemView.findViewById(R.id.reviewFragment);

        }
    }

}
