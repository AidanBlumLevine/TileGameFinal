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

import java.util.ArrayList;

public class GamePanel extends SurfaceView implements Runnable{
    private ArrayList<int[]> touches = new ArrayList<int[]>();
    private ArrayList<Integer> swipes = new ArrayList<>();
    private volatile Boolean playing;
    private Thread gameThread = null;
    private long lastTime;
    private SurfaceHolder surfaceHolder;
    private android.graphics.Canvas canvas;
    private Paint paint;
    private Game game;

    public GamePanel(Context context,Level level,String pack){
        super(context);
        game = new Game(level,context,pack);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        surfaceHolder = getHolder();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            game.draw(canvas,paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {
        while (playing) {
            if(System.nanoTime()-lastTime>=1000000000/game.getFPS()) {
                try {
                    if (touches != null) {
                        for (int i = 0; i < touches.size(); i++) {
                            game.touch(touches.get(i)[0], touches.get(i)[1]);
                            //ATTEMP TO READ FROM A NULL ARRAY ERROR HERE
                        }
                        touches.clear();
                    }
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
                for(int i=0;i<swipes.size();i++){
                    game.swipe(swipes.get(i));
                }
                swipes.clear();
                draw();
                game.update();
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
        game.paused();
        playing = false;
        try {
            gameThread.join();
        } catch (Exception e) {}
        ParticleManager.clear();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                queueTouch(-1,-1);
                break;
            case MotionEvent.ACTION_DOWN:
                queueTouch((int)motionEvent.getRawX(),(int)motionEvent.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                queueTouch((int)motionEvent.getRawX(),(int)motionEvent.getRawY());
        }
        return true;
    }
    private void queueTouch(int x, int y){
        touches.add(new int[]{x,y});
    }
    public Game getGame() {
        return game;
    }

    public void swipe(int i) {
        swipes.add(i);
    }
}
