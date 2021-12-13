package com.example.webfactory.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webfactory.PollsPage;
import com.example.webfactory.R;
import com.example.webfactory.model.Polls;

import java.util.List;

public class PollsAdapter extends RecyclerView.Adapter<PollsAdapter.PollsViewHolder> {

    Context context;
    List<Polls> pollsArray;

    public PollsAdapter(Context context, List<Polls> pollsArray) {
        this.context = context;
        this.pollsArray = pollsArray;
    }

    @NonNull
    @Override
    public PollsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View pollsItems = LayoutInflater.from(context).inflate(R.layout.polls_item, parent, false);
        return new PollsViewHolder(pollsItems);
    }

    @Override
    public void onBindViewHolder(@NonNull PollsViewHolder holder, int position) {

        holder.pollsTitle.setText(pollsArray.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PollsPage.class);
                intent.putExtra("pollsTitle", pollsArray.get(position).getTitle());
                intent.putExtra("pollsVar1", pollsArray.get(position).getVar1());
                intent.putExtra("pollsVar2", pollsArray.get(position).getVar2());
                intent.putExtra("pollsVar3", pollsArray.get(position).getVar3());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pollsArray.size();
    }

    public static final class PollsViewHolder extends RecyclerView.ViewHolder {

        TextView pollsTitle;

        public PollsViewHolder(@NonNull View itemView) {
            super(itemView);

            pollsTitle = itemView.findViewById(R.id.pollsTitle);

        }
    }
}
