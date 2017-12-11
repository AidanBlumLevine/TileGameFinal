package com.example.aidan.tilegameredo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.aidan.tilegameredo.levelEditor.LevelEditorScreen;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        int screenWidth = getResources().getSystem().getDisplayMetrics().widthPixels;

        final ImageButton button1 = (ImageButton) findViewById(R.id.imageButton2);
        ViewGroup.LayoutParams params = button1.getLayoutParams();
        params.width = screenWidth / 2;
        params.height = params.width / (198 / 70);

        button1.setLayoutParams(params);

        button1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ViewGroup.LayoutParams lp =  v.getLayoutParams();
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    lp.width=(int)(lp.width*1.1);
                    lp.height=(int)(lp.height*1.1);
                    button1.setLayoutParams(lp);
                }
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    lp.width=(int)(lp.width/1.1);
                    lp.height=(int)(lp.height/1.1);
                    button1.setLayoutParams(lp);
                }
                return false;
            }
        });

        final ImageButton button3 = (ImageButton) findViewById(R.id.imageButton3);

        params = button3.getLayoutParams();
        params.width = screenWidth / 2;
        params.height = params.width / (198 / 70);

        button3.setLayoutParams(params);

        button3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ViewGroup.LayoutParams lp =  v.getLayoutParams();
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    lp.width=(int)(lp.width*1.1);
                    lp.height=(int)(lp.height*1.1);
                    button3.setLayoutParams(lp);
                }
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    lp.width=(int)(lp.width/1.1);
                    lp.height=(int)(lp.height/1.1);
                    button3.setLayoutParams(lp);
                }
                return false;
            }
        });
    }

    public void playGame(View view) {
        Intent i = new Intent(this, SelectorScreen.class);
        startActivity(i);
        overridePendingTransition(R.anim.down_to_mid,R.anim.mid_to_up);
    }

    public void playEditor(View view) {
        Intent i = new Intent(this, LevelEditorScreen.class);
        startActivity(i);
        overridePendingTransition(R.anim.down_to_mid,R.anim.mid_to_up);
    }
}
