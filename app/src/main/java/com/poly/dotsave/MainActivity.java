package com.poly.dotsave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabs;
    private ViewPager2 pagers;
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = findViewById(R.id.tabs);
        pagers = findViewById(R.id.pagers);
        myPagerAdapter = new MyPagerAdapter(this);
        pagers.setAdapter(myPagerAdapter);
        new TabLayoutMediator(tabs, pagers, (tab, position) ->
        {
            switch (position) {
                case 0:
                    tab.setText("Home");
                    break;
                case 1:
                    tab.setText("History");
                    break;
            }
        }
        ).attach();

    }


}