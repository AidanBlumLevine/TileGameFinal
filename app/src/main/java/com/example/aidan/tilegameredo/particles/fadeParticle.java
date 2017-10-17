package com.example.aidan.tilegameredo.particles;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.aidan.tilegameredo.Game;
import com.example.aidan.tilegameredo.ParticleManager;

public class fadeParticle extends Particle{

    private int mode;

    public fadeParticle(){
        super(0,0,255);
        Game.setPlaying(false);
        mode=1;
    }
    public void paint(Canvas canvas, Paint paint){
        super.setTime(super.getTime()-1000/ Game.getFps());
        if (super.getTime() <= 0 && mode==1) {
            mode=2;
            super.setTime(255);
            levelSelecotr.playagain
        }
        if (super.getTime() <= 0 && mode==2) {
            mode=3;
        }

        if(mode==1){
            paint.setColor(Color.BLACK);
            paint.setAlpha((int)super.getTime()*-1+255);
            canvas.drawRect(-10,-10,canvas.getWidth(),canvas.getHeight(),paint);
        }
        if(mode==2){
            paint.setColor(Color.BLACK);
            paint.setAlpha((int)super.getTime());
            canvas.drawRect(-10,-10,canvas.getWidth(),canvas.getHeight(),paint);
        }
        paint.reset();
    }

    @Override
    public boolean isDone() {
        return mode==3;
    }
}

