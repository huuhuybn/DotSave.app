package com.poly.dotsave.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.poly.dotsave.R;
import com.poly.dotsave.gson.Image;
import com.poly.dotsave.gson.Video;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageHolder> {

    private Context context;
    private List<Image> videos;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String title, description;



    public ImageAdapter(Context context, List<Image> videos) {
        this.context = context;
        this.videos = videos;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video, parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        Glide.with(context).load(videos.get(position).getUrl()).centerCrop().into(holder.imageView);
        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(videos.get(holder.getAdapterPosition()).getUrl());
                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle(title);
                request.setDescription(description);
                downloadManager.enqueue(request);
            }
        });
        holder.textView.setText("Image/" + videos.get(position).getWidth() + ":" + videos.get(position).getHeight());
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }
}
