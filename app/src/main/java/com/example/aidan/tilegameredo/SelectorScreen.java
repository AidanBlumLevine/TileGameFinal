package com.example.aidan.tilegameredo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SelectorScreen extends AppCompatActivity {
    private SelectorPanel panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        panel = new SelectorPanel(this);
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
