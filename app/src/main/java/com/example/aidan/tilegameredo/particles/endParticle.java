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
            super.setTime(255);
            Game.updateStars();
        }
        if(super.getTime()<=0 && mode==3){
            mode=4;
            Game.playAgain();
            super.setTime(255);
        }
        if (super.getTime() <= 0 && mode==4) {
            mode=5;
        }
        if(mode==1 || mode==2) {
            paint.setColor(Color.BLACK);
            Path path = new Path();
            path.lineTo(-20, -20);
            path.lineTo(-20, height);
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
        if(mode==3){
            paint.setColor(Color.BLACK);
            paint.setAlpha(255);
            canvas.drawRect(-10,-10,canvas.getWidth(),canvas.getHeight(),paint);
            int starAlpha = (int)(255*(Math.min(Math.sin(super.getTime()/255*Math.PI),.8)+.2));
            paint.setColor(Color.YELLOW);
            paint.setAlpha(starAlpha);
            if(Game.getStars()>1){
                canvas.drawRect(width/2-100,height/2-100,width/2+100,height/2+100,paint);
            }
            if(Game.getStars()>0){
                canvas.drawRect(width/2-350,height/2-100,width/2-150,height/2+100,paint);
            }
            if(Game.getStars()>2){
                canvas.drawRect(width/2+150,height/2-100,width/2+350,height/2+100,paint);
            }
        }
        if(mode==4){
            paint.setColor(Color.BLACK);
            paint.setAlpha((int)super.getTime());
            canvas.drawRect(-10,-10,canvas.getWidth(),canvas.getHeight(),paint);
        }
    }

    @Override
    public boolean isDone() {
        return mode==5;
    }
}
