package com.poly.dotsave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.poly.dotsave.adapter.HistoryAdapter;
import com.poly.dotsave.model.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView rvList;
    List<History> histories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        rvList = findViewById(R.id.lvList);
        histories = new SqliteHelper(this).getAllHistories();
        HistoryAdapter historyAdapter = new HistoryAdapter(this, histories);
        rvList.setAdapter(historyAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(this));


    }
}