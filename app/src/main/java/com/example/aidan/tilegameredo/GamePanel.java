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

public class GamePanel extends SurfaceView implements Runnable{
    private volatile Boolean playing;
    private Thread gameThread = null;
    private static long lastTime;
    private static SurfaceHolder surfaceHolder;
    private static android.graphics.Canvas canvas;
    private static Paint paint;

    public GamePanel(Context context, String levelPack){
        super(context);

        Game.setLevelPack(levelPack);

        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        surfaceHolder = getHolder();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        Game.load(context);
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            Game.draw(canvas,paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {
        while (playing) {
            if(System.nanoTime()-lastTime>=1000000000/Game.getFPS()) {
                draw();
                Game.update();
                lastTime = System.nanoTime();
            }
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(super.getContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("defaultLevel", Game.getDefaultLevel());
        editor.putInt("customLevel", Game.getCustomLevel());
        editor.commit();
        playing = false;
        try {
            gameThread.join();
        } catch (Exception e) {}
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                Game.touch(-1,-1);
                break;
            case MotionEvent.ACTION_DOWN:
                Game.touch((int)motionEvent.getRawX(),(int)motionEvent.getRawY());
                break;
        }
        return true;
    }
}
