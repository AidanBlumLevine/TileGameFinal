package com.example.aidan.tilegameredo.levelEditor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LevelEditorScreen extends AppCompatActivity {

    private LevelEditorPanel levelEditorPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            levelEditorPanel = new LevelEditorPanel(this,b.getString("level"));
        } else {
            levelEditorPanel = new LevelEditorPanel(this);
        }
        setContentView(levelEditorPanel);


    }

    @Override
    protected void onResume(){
        super.onResume();
        levelEditorPanel.resume();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onPause(){
        super.onPause();
        levelEditorPanel.pause();
    }
}
