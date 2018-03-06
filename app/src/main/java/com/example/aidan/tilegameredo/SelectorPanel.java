package com.example.aidan.tilegameredo;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class SelectorPanel extends SurfaceView implements Runnable{
    private  Boolean running;
    private Thread gameThread = null;
    private ArrayList<int[]> touches = new ArrayList<int[]>();

    private SurfaceHolder surfaceHolder;
    private android.graphics.Canvas canvas;
    private Paint paint;
    private LevelSelector levelSelector;

    public SelectorPanel(Context context){
        super(context);

        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        surfaceHolder = getHolder();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        levelSelector = new LevelSelector(context);
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            levelSelector.draw(canvas,paint);
//            paint.setColor(Color.GREEN);
//            canvas.drawRect(0,0,50,50,paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {
        while (running) {
            draw();
            try {
                if (touches != null) {
                    for (int i = 0; i < touches.size(); i++) {
                        levelSelector.touch(touches.get(i)[0], touches.get(i)[1], touches.get(i)[2]);
                        //ATTEMP TO READ FROM A NULL ARRAY ERROR HERE
                    }
                    touches.clear();
                }
            } catch (NullPointerException e){
                e.printStackTrace();
            }
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
    private void queueTouch(int x, int y, int z){
        touches.add(new int[]{x,y,z});
    }

}
