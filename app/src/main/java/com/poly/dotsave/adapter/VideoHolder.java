package com.poly.dotsave.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.dotsave.R;

public class VideoHolder extends RecyclerView.ViewHolder {

    final public ImageView imageView;
    final Button btnDownload;
    final TextView textView;

    public VideoHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        btnDownload = itemView.findViewById(R.id.btnDownload);
        textView = itemView.findViewById(R.id.textView);
    }
}
