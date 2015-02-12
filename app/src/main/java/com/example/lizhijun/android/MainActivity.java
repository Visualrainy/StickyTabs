package com.example.lizhijun.android;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;


public class MainActivity extends FragmentActivity {

    private TextView descriptionEt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    public void initViews() {
        descriptionEt = (TextView)findViewById(R.id.test);
        descriptionEt.setText("hello android studio");
    }
}
