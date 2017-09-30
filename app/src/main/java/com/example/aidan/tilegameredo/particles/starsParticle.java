package com.example.aidan.tilegameredo.particles;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.aidan.tilegameredo.Game;

public class starsParticle extends Particle {
    private int height,width,mode;
    public starsParticle() {
        super(0, 0, Resources.getSystem().getDisplayMetrics().heightPixels/2);
        height = Resources.getSystem().getDisplayMetrics().heightPixels;
        width = Resources.getSystem().getDisplayMetrics().widthPixels;
        mode=1;
    }

    public void update(){
        super.setTime(super.getTime() - 5000 / Game.getFps());
    }

    @Override
    public void paint(Canvas canvas, Paint paint) {
        int drawY=0;
        if(mode==1){
            drawY = height/2-(int)super.getTime()-1500;
            if(super.getTime()<-1800){
                mode=2;
                super.setTime(1000);
            }
        }
        if(mode==2){
            drawY = height/2+300;
            if(super.getTime()<0){
                mode=3;
                super.setTime(height/2);
            }
        }
        if(mode==3){
            drawY = height-(int)super.getTime()+300;
            if(super.getTime()<300){
                mode=4;
            }
        }
        Rect starArea = new Rect(width/5,drawY,4*width/5,drawY+width/7);
        paint.setColor(Color.YELLOW);
        if(Game.getStars()>2){
            canvas.drawRect(starArea.left,starArea.top,starArea.left+starArea.height(),starArea.bottom,paint);
        }
        if(Game.getStars()>1){
            canvas.drawRect(starArea.centerX()-starArea.height()/2,starArea.top,starArea.centerX()+starArea.height()/2,starArea.bottom,paint);
        }
        if(Game.getStars()>0){
            canvas.drawRect(starArea.right-starArea.height(),starArea.top,starArea.right,starArea.bottom,paint);
        }
        update();
    }

    @Override
    public boolean isDone() {
        return mode==4;
    }
}
