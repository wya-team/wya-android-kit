package com.wya.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wya.kit.uikit.statusbar.StatusBarUtil;

public class ExampleActivity extends AppCompatActivity {
    
    StatusBarUtil mStatusBarUtil;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStatusBarUtil = StatusBarUtil.with(this);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        mStatusBarUtil.release();
    }
}
