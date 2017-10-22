package com.example.aidan.tilegameredo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class EndScreen extends AppCompatActivity {
    private EndPanel panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        int stars = b.getInt("stars");
        panel = new EndPanel(this,stars);

        setContentView(panel);
    }

    @Override
    protected void onResume(){
        super.onResume();
        panel.resume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        panel.pause();
    }

}
