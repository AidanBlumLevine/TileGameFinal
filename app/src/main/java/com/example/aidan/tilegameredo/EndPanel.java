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
import com.example.aidan.tilegameredo.particles.Particle;

public class EndPanel extends SurfaceView implements Runnable{
    private volatile Boolean playing;
    private Thread gameThread = null;
    private SurfaceHolder surfaceHolder;
    private android.graphics.Canvas canvas;
    private Paint paint;
    private EndMenu menu;

    public EndPanel(Context context,int stars,String level,String pack){
        super(context);
        menu = new EndMenu(stars,context,level,pack);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        surfaceHolder = getHolder();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            menu.draw(canvas,paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {
        while (playing) {
            draw();
        }
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
        ParticleManager.clear();
        Loader.loadDefaultLevels(getContext());
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                menu.touch(-1,-1);
                break;
            case MotionEvent.ACTION_DOWN:
                menu.touch((int)motionEvent.getRawX(),(int)motionEvent.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                menu.touch((int)motionEvent.getRawX(),(int)motionEvent.getRawY());
        }
        return true;
    }
}
