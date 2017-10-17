package com.example.aidan.tilegameredo.particles;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import com.example.aidan.tilegameredo.Game;
import com.example.aidan.tilegameredo.ParticleManager;

public class endParticle extends Particle{

    private int targetSize,mode,height,width;

    public endParticle(int tX,int tY,int tS){
        super(tX,tY,1500);
        targetSize=tS;
        mode=1;
        height = Resources.getSystem().getDisplayMetrics().heightPixels;
        width = Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public void paint(Canvas canvas, Paint paint){
        super.setTime(super.getTime()-1000/Game.getFps());
        if (super.getTime() <= targetSize && mode==1) {
            mode=2;
            super.setTime(255);
        }
        if (super.getTime() <= 0 && mode==2) {
            mode=3;
            Game.updateStars();
            go to end screen
        }
        if(mode==1 || mode==2) {
            paint.setColor(Color.BLACK);
            Path path = new Path();
            path.lineTo(-50, -50);
            path.lineTo(-50, height);
            path.lineTo(width, height);
            path.lineTo(width, -120);
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
            canvas.drawRect(-10,-10,canvas.getWidth(),canvas.getHeight(),paint);
        }
    }

    @Override
    public boolean isDone() {
        return mode==3;
    }
}
