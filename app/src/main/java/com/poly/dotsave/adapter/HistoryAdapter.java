package com.poly.dotsave.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.poly.dotsave.R;
import com.poly.dotsave.SqliteHelper;
import com.poly.dotsave.model.History;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryHolder> {
    private List<History> histories;
    private Context context;

    public HistoryAdapter(Context context, List<History> histories) {
        this.histories = histories;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        Glide.with(context).load(histories.get(position).thumb).centerCrop().into(holder.imageView);
        holder.textView.setText(histories.get(position).title);
        holder.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SqliteHelper sqliteHelper = new SqliteHelper(context);
                sqliteHelper.delete(histories.get(holder.getAdapterPosition()).id);
                histories.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }
}
