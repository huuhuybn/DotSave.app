package com.poly.dotsave.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.poly.dotsave.R;

public class HistoryHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public Button btnPlay, btnDel;
    public TextView textView;

    public HistoryHolder(View view) {
        super(view);
        imageView = view.findViewById(R.id.imageView);
        btnPlay = view.findViewById(R.id.btnPlay);
        btnDel = view.findViewById(R.id.btnDelete);
        textView = view.findViewById(R.id.textView);
    }
}
