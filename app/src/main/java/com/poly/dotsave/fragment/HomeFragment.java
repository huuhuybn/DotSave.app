package com.poly.dotsave.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.poly.dotsave.R;
import com.poly.dotsave.SqliteHelper;
import com.poly.dotsave.adapter.ImageAdapter;
import com.poly.dotsave.adapter.VideoAdapter;
import com.poly.dotsave.gson.Image;
import com.poly.dotsave.gson.Response;
import com.poly.dotsave.gson.Video;
import com.poly.dotsave.model.History;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    private TextInputEditText edtText;
    private Button btnLoad;

    private VideoAdapter videoAdapter;
    private List<Video> videos;
    private TextView tvTitle, tvDescription;

    private ImageAdapter imageAdapter;
    private List<Image> images;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        edtText = view.findViewById(R.id.edtText);
        btnLoad = view.findViewById(R.id.btnLoad);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvDescription = view.findViewById(R.id.tvDescription);


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
                    Toast.makeText(getActivity(), getString(R.string.notify_empty), Toast.LENGTH_SHORT).show();
                    return;
                }
                btnLoad.setEnabled(false);
                btnLoad.setText(getString(R.string.loading));
            }
        });

        RecyclerView lvListVideos = view.findViewById(R.id.lvListVideos);
        RecyclerView lvListImage = view.findViewById(R.id.lvListImage);

        videos = new ArrayList<>();
        videoAdapter = new VideoAdapter(getActivity(), videos);
        lvListVideos.setAdapter(videoAdapter);
        lvListVideos.setLayoutManager(new LinearLayoutManager(getActivity()));

        images = new ArrayList<>();
        imageAdapter = new ImageAdapter(getActivity(), images);
        lvListImage.setAdapter(imageAdapter);
        lvListImage.setLayoutManager(new LinearLayoutManager(getActivity()));
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

                        videoAdapter.setTitle(res.getBody().getTitle());
                        videoAdapter.setDescription(res.getBody().getDescription());
                        videos.addAll(res.getBody().getVideos());
                        videoAdapter.notifyDataSetChanged();

                        SqliteHelper sqliteHelper = new SqliteHelper(getActivity());
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

}
