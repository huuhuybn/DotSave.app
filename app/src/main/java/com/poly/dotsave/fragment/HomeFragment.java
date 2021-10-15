package com.poly.dotsave.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.poly.dotsave.R;
import com.poly.dotsave.gson.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    private TextInputEditText edtText;
    private Button btnLoad;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        edtText = view.findViewById(R.id.edtText);
        btnLoad = view.findViewById(R.id.btnLoad);

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String test = edtText.getText().toString();
                try {
                    URL url = new URL(test);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    edtText.setError(getString(R.string.notify_empty));
                    Toast.makeText(getActivity(), getString(R.string.notify_empty), Toast.LENGTH_SHORT).show();
                    return;
                }
                btnLoad.setEnabled(false);
                btnLoad.setText(getString(R.string.loading));
                requestLink(test);
            }
        });
    }

    public void requestLink(String url) {
        AndroidNetworking.get("https://dotsave.app/")
                .addQueryParameter("url", url)
                .addQueryParameter("mobile", "3")
                .setPriority(Priority.LOW)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        btnLoad.setEnabled(true);
                        btnLoad.setText(getString(R.string.btn_load));
                        Gson gson = new Gson();
                        Response res = gson.fromJson(response, Response.class);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

}
