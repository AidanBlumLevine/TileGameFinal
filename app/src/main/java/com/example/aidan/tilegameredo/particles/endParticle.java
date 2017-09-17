package com.example.aidan.tilegameredo.particles;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.aidan.tilegameredo.Game;

public class endParticle extends Particle{

    private int targetSize,mode;

    public endParticle(int tX,int tY,int tS){
        super(tX,tY,1000);
        targetSize=tS;
        mode=1;
    }
    public void paint(Canvas canvas, Paint paint){
        super.setTime(super.getTime()-1000/Game.getFps());
        if (super.getTime() <= targetSize && mode==1) {
            mode=2;
            super.setTime(255);
        }
        if (super.getTime() <= 0 && mode==2) {
            mode=3;
            super.setTime(255);
            Game.playAgain();
        }
        if (super.getTime() <= 0 && mode==3) {
            mode=4;
        }
        if(mode==1 || mode==2) {
            int height = Resources.getSystem().getDisplayMetrics().heightPixels;
            int width = Resources.getSystem().getDisplayMetrics().widthPixels;
            paint.setColor(Color.BLACK);
            Path path = new Path();
            path.lineTo(0, 0);
            path.lineTo(0, height);
            path.lineTo(width, height);
            path.lineTo(width, 0);
            if(mode==2){
                path.addCircle((super.getX()), (super.getY()), targetSize, Path.Direction.CW);
            } else {
                path.addCircle((super.getX()), (super.getY()), Math.max((float) super.getTime(), targetSize), Path.Direction.CW);
            }
            canvas.drawPath(path, paint);
        }
        if(mode==2){
            paint.setColor(Color.BLACK);
            paint.setAlpha((int)super.getTime()*-1+255);
            canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),paint);
        }
        if(mode==3){
            paint.setColor(Color.BLACK);
            paint.setAlpha((int)super.getTime());
            canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),paint);
        }
    }

    @Override
    public boolean isDone() {
        return mode==4;
    }
}
