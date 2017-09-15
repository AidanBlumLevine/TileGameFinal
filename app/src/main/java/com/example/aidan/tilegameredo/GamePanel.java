package com.example.aidan.tilegameredo;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GamePanel extends SurfaceView implements Runnable{
    private volatile Boolean playing;
    private Thread gameThread = null;
    private static final int fps=30;
    private static long lastTime;

    @Override
    public void run() {
        while (playing) {
            if(System.nanoTime()-lastTime>=1000000000/fps) {
                draw();
                for (Tile t : tiles) {
                    t.update();

                }
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
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("defaultLevel", defaultLevel);
        editor.putInt("customLevel", customLevel);
        playing = false;
        try {
            gameThread.join();
        } catch (Exception e) {}
    }

    static class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float vX, float vY) {

            Double angle = Math.toDegrees(Math.atan2(e1.getY() - e2.getY(), e2.getX() - e1.getX()));

            if (angle > 45 && angle <= 135) {
                if (!tilesMoving()) {
                    tileSort("Up");
                    for (Tile t : tiles) {
                        t.pushUp();
                    }
                }
            } else if (angle >= 135 && angle < 180 || angle < -135 && angle > -180) {
                tileSort("Left");
                if (!tilesMoving()) {
                    for (Tile t : tiles) {
                        t.pushLeft();
                    }
                }
            } else if (angle < -45 && angle >= -135) {
                if (!tilesMoving()) {
                    tileSort("Down");
                    for (Tile t : tiles) {
                        t.pushDown();
                    }
                }
            } else if (angle > -45 && angle <= 45) {
                if (!tilesMoving()) {
                    if(firstPlay){
                        firstPlay=false;
                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("firstPlay", false);
                        editor.commit();

                    }
                    tileSort("Right");
                    for (Tile t : tiles) {
                        t.pushRight();
                    }
                }
            }
            return false;
        }
    }
}
