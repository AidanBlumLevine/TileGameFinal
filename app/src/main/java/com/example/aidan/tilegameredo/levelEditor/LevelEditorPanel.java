package com.example.aidan.tilegameredo.levelEditor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.aidan.tilegameredo.ImageLoader;
import com.example.aidan.tilegameredo.LevelGenerator;
import com.example.aidan.tilegameredo.LevelSelector;
import com.example.aidan.tilegameredo.Tile;
import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbDoubleCrate;

import java.util.ArrayList;

public class LevelEditorPanel extends SurfaceView implements Runnable {
    private ArrayList<int[]> touches = new ArrayList<int[]>();
    volatile Boolean playing;
    private Thread gameThread = null;
    private static long lastTime;
    private android.graphics.Canvas canvas;
    private Paint paint;
    private SurfaceHolder surfaceHolder;
    private LevelEditor levelEditor;
    public LevelEditorPanel(Context context) {
        super(context);

        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        surfaceHolder = getHolder();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        levelEditor = new LevelEditor(context);
    }
    public LevelEditorPanel(Context context,String level) {
        super(context);

        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        surfaceHolder = getHolder();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        levelEditor = new LevelEditor(context,level);
    }

    @Override
    public void run() {
        while (playing) {
            if(System.nanoTime()-lastTime>=1000000000/levelEditor.getFps()) {
                for(int i=0;i<touches.size();i++){
                    levelEditor.touch(touches.get(i)[0],touches.get(i)[1],touches.get(i)[2]);
                }
                touches.clear();
                draw();
                levelEditor.update();
                lastTime = System.nanoTime();
            }
        }
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            levelEditor.draw(canvas,paint,super.getContext());
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (Exception e) {

        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                queueTouch(-1,-1,-1);
                break;
            case MotionEvent.ACTION_DOWN:
                queueTouch((int)motionEvent.getRawX(),(int)motionEvent.getRawY(),1);
                break;
            case MotionEvent.ACTION_MOVE:
                queueTouch((int)motionEvent.getRawX(),(int)motionEvent.getRawY(),0);
        }
        return true;
    }
    private void queueTouch(int x, int y, int type){
        touches.add(new int[]{x,y,type});
    }
}