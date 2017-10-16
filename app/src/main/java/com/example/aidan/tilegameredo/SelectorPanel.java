package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.aidan.tilegameredo.levelEditor.LevelEditor;

public class SelectorPanel extends SurfaceView implements Runnable{
    private  Boolean running;
    private Thread gameThread = null;

    private static SurfaceHolder surfaceHolder;
    private static android.graphics.Canvas canvas;
    private static Paint paint;

    public SelectorPanel(Context context){
        super(context);

        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        surfaceHolder = getHolder();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        LevelSelector.load(context);
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            LevelSelector.draw(canvas,paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {
        while (running) {
                LevelSelector.update();
        }
    }

    public void resume() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause() {
        running = false;
        try {
            gameThread.join();
        } catch (Exception e) {}
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                LevelSelector.touch(-1,-1);
                break;
            case MotionEvent.ACTION_DOWN:
                LevelSelector.touch((int)motionEvent.getRawX(),(int)motionEvent.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                LevelSelector.touch((int)motionEvent.getRawX(),(int)motionEvent.getRawY());
        }
        return true;
    }
}