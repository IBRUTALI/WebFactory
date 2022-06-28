package com.example.webfactory.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.webfactory.R;
import com.example.webfactory.model.News;
import com.example.webfactory.presentation.home.WebView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    Context context;
    ArrayList<News> newsArrayList;

    public NewsAdapter(Context context, ArrayList<News> newsArrayList) {
        this.context = context;
        this.newsArrayList = newsArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebView.class);
                intent.putExtra("url", newsArrayList.get(position).getUrl());
                context.startActivity(intent);
            }
        });

        holder.mTime.setText("Опубликовано в:-" + newsArrayList.get(position).getPublishedAt());
        holder.mAuthor.setText(newsArrayList.get(position).getAuthor());
        holder.mHeading.setText(newsArrayList.get(position).getTitle());
        holder.mContent.setText(newsArrayList.get(position).getDescription());
        Glide.with(context).load(newsArrayList.get(position).getUrlToImage()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mHeading, mContent, mAuthor, mTime;
        CardView cardView;
        ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mHeading = itemView.findViewById(R.id.main_heading);
            mContent = itemView.findViewById(R.id.content);
            mAuthor = itemView.findViewById(R.id.newsAuthor);
            mTime = itemView.findViewById(R.id.newsTime);
            cardView = itemView.findViewById(R.id.newsCV);
            imageView = itemView.findViewById(R.id.newsIV);
        }
    }
}
