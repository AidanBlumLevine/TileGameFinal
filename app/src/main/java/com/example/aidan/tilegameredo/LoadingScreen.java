package com.example.aidan.tilegameredo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class LoadingScreen extends AppCompatActivity {
    private LoadingPanel panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        panel = new LoadingPanel(this);
        setContentView(panel);
    }

    @Override
    protected void onResume(){
        super.onResume();
        panel.resume();
    }
    @Override
    public void onBackPressed() {
    }
    @Override
    protected void onPause(){
        super.onPause();
        panel.pause();
    }
}
