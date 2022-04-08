package com.example.webfactory.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webfactory.R;
import com.example.webfactory.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Category> categoryList;
    private ArrayList<Category> categoryListFull;
    private NavController navController;

    public CategoryAdapter(Context context, ArrayList categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        categoryListFull = new ArrayList<>(categoryList);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View categoryItems = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(categoryItems);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        position = holder.getAdapterPosition();
        holder.categoryTitle.setText(String.valueOf(categoryList.get(position).getTitle()));
        holder.categoryId.setText(String.valueOf(categoryList.get(position).getId()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("reviewId", String.valueOf(categoryList.get(holder.getAdapterPosition()).getId()));
                bundle.putString("reviewTitle", String.valueOf(categoryList.get(holder.getAdapterPosition()).getTitle()));
                bundle.putString("reviewDescription", String.valueOf(categoryList.get(holder.getAdapterPosition()).getDescription()));
                navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_nav_forum_to_reviewPageFragment, bundle);
            }
        });
    }

    @Override
    public Filter getFilter() {
        return categoryFilter;
    }

    private Filter categoryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Category> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(categoryListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Category item: categoryListFull){
                    if(item.getCategory().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            categoryList.clear();
            categoryList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static final class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView categoryTitle;
        TextView categoryId;
        ConstraintLayout reviewFragment;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTitle = itemView.findViewById(R.id.categoryTitle);
            categoryId = itemView.findViewById(R.id.categoryId);
            reviewFragment = itemView.findViewById(R.id.reviewFragment);

        }
    }

}
