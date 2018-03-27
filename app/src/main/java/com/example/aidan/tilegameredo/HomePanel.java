package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.aidan.tilegameredo.levelEditor.LevelEditor;
import com.example.aidan.tilegameredo.levelEditor.LevelEditorScreen;
import com.example.aidan.tilegameredo.particles.Particle;

public class HomePanel extends SurfaceView implements Runnable{
    private volatile Boolean playing;
    private Thread gameThread = null;
    private SurfaceHolder surfaceHolder;
    private android.graphics.Canvas canvas;
    private Paint paint;
    private Button play,edit;
    private Context context;
    public HomePanel(Context context){
        super(context);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        surfaceHolder = getHolder();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.context = context;

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        play = new Button(100,height*3/4,width/5,width/12,Color.rgb(65,99,135),"PLAY",48,Loader.getFont(context));
        edit = new Button(100,height*3/4+width/9,width/2,width/12,Color.rgb(65,99,135),"LEVEL EDITOR",48,Loader.getFont(context));

    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            Loader.drawBackground(canvas, paint);
            play.draw(canvas,paint);
            edit.draw(canvas,paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {
        while (playing) {
            draw();
        }
    }

    private void touch(int x,int y){
        if(x==-1 && y==-1){
            if(play.getHover()){
                Intent i = new Intent(context,SelectorScreen.class);
                context.startActivity(i);
            }
            if(edit.getHover()){
                Intent i = new Intent(context,LevelEditorScreen.class);
                context.startActivity(i);
            }
        }
        play.touch(x,y);
        edit.touch(x,y);
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (Exception e) {}
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                touch(-1,-1);
                break;
            case MotionEvent.ACTION_DOWN:
                touch((int)motionEvent.getRawX(),(int)motionEvent.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                touch((int)motionEvent.getRawX(),(int)motionEvent.getRawY());
        }
        return true;
    }
}