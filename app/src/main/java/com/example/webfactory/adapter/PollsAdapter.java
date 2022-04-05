package com.example.webfactory.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webfactory.ui.polls.PollsPageFragment;
import com.example.webfactory.R;

import java.util.ArrayList;

public class PollsAdapter extends RecyclerView.Adapter<PollsAdapter.PollsViewHolder> {

    private Context context;
    private ArrayList polls_id, polls_title, polls_var1, polls_var2, polls_var3;
    private NavController navController;

    public PollsAdapter(Context context, ArrayList polls_id, ArrayList polls_title,
                        ArrayList polls_var1, ArrayList polls_var2, ArrayList polls_var3) {
        this.context = context;
        this.polls_id = polls_id;
        this.polls_title = polls_title;
        this.polls_var1 = polls_var1;
        this.polls_var2 = polls_var2;
        this.polls_var3 = polls_var3;
    }

    @NonNull
    @Override
    public PollsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View pollsItems = LayoutInflater.from(context).inflate(R.layout.polls_item, parent, false);
        return new PollsViewHolder(pollsItems);
    }

    @Override
    public void onBindViewHolder(@NonNull PollsViewHolder holder, int position) {
        position = holder.getAdapterPosition();
        holder.pollsTitle.setText(String.valueOf(polls_title.get(position)));
        holder.pollsId.setText(String.valueOf(polls_id.get(position)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("pollsId", String.valueOf(polls_id.get(holder.getAdapterPosition())));
                bundle.putString("pollsTitle", String.valueOf(polls_title.get(holder.getAdapterPosition())));
                bundle.putString("pollsVar1", String.valueOf(polls_var1.get(holder.getAdapterPosition())));
                bundle.putString("pollsVar2", String.valueOf(polls_var2.get(holder.getAdapterPosition())));
                bundle.putString("pollsVar3", String.valueOf(polls_var3.get(holder.getAdapterPosition())));
                navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_nav_polls_to_pollsPage2, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return polls_id.size();
    }

    public static final class PollsViewHolder extends RecyclerView.ViewHolder {

        TextView pollsTitle;
        TextView pollsId;
        ConstraintLayout pollFragment;

        public PollsViewHolder(@NonNull View itemView) {
            super(itemView);
            pollsTitle = itemView.findViewById(R.id.pollsTitle);
            pollsId = itemView.findViewById(R.id.pollsId);
            pollFragment = itemView.findViewById(R.id.pollFragment);

        }
    }
}
