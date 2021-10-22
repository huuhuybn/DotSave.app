package com.poly.dotsave;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.poly.dotsave.adapter.ImageAdapter;
import com.poly.dotsave.adapter.VideoAdapter;
import com.poly.dotsave.gson.Image;
import com.poly.dotsave.gson.Response;
import com.poly.dotsave.gson.Video;
import com.poly.dotsave.model.History;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText edtText;
    private Button btnLoad;

    private VideoAdapter videoAdapter;
    private List<Video> videos;
    private TextView tvTitle, tvDescription;

    private ImageAdapter imageAdapter;
    private List<Image> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtText = findViewById(R.id.edtText);
        btnLoad = findViewById(R.id.btnLoad);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);


        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String test = edtText.getText().toString();
                try {
                    URL url = new URL(test);
                    requestLink(url.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    edtText.setError(getString(R.string.notify_empty));
                    Toast.makeText(MainActivity.this, getString(R.string.notify_empty), Toast.LENGTH_SHORT).show();
                    return;
                }
                btnLoad.setEnabled(false);
                btnLoad.setText(getString(R.string.loading));
            }
        });

        RecyclerView lvListVideos = findViewById(R.id.lvListVideos);
        RecyclerView lvListImage = findViewById(R.id.lvListImage);

        videos = new ArrayList<>();
        videoAdapter = new VideoAdapter(MainActivity.this, videos);
        lvListVideos.setAdapter(videoAdapter);
        lvListVideos.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        images = new ArrayList<>();
        imageAdapter = new ImageAdapter(MainActivity.this, images);
        lvListImage.setAdapter(imageAdapter);
        lvListImage.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    public void requestLink(String url) {
        AndroidNetworking.get("https://dotsave.app/")
                .addQueryParameter("url", url)
                .addQueryParameter("mobile", "3")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("stta", response);
                        btnLoad.setEnabled(true);
                        btnLoad.setText(getString(R.string.btn_load));
                        Gson gson = new Gson();
                        Response res = gson.fromJson(response, Response.class);
                        Log.e("abc", res.getBody().getTitle());
                        if (videos.size() > 0) {
                            videos = new ArrayList<>();
                            videoAdapter = new VideoAdapter(MainActivity.this, videos);
                        }


                        videoAdapter.setTitle(res.getBody().getTitle());
                        videoAdapter.setDescription(res.getBody().getDescription());
                        videos.addAll(res.getBody().getVideos());
                        videoAdapter.notifyDataSetChanged();

                        SqliteHelper sqliteHelper = new SqliteHelper(MainActivity.this);
                        sqliteHelper.insert(new History(res.getBody().getVideos().get(0).getUrl(), String.valueOf(System.currentTimeMillis()),
                                res.getBody().getTitle(), res.getBody().getVideos().get(0).getThumbnail()));


//                        imageAdapter.setTitle(res.getBody().getTitle());
//                        imageAdapter.setDescription(res.getBody().getDescription());
//                        images.addAll(res.getBody().getImages());
//                        imageAdapter.notifyDataSetChanged();

                        tvTitle.setText(res.getBody().getTitle());
                        tvDescription.setText(res.getBody().getDescription());

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.history:
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                break;
            case R.id.home:
                String url = "https://dotsave.app";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;

            case R.id.about:
                String home = "https://www.facebook.com/dotsave";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(home));
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}