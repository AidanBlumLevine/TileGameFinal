package com.example.aidan.tilegameredo.particles;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.aidan.tilegameredo.ParticleManager;

public abstract class Particle {
    private int x,y;
    private double time;

    public Particle(int x,int y,int time){
        this.x=x;
        this.y=y;
        this.time=time;
        ParticleManager.addParticle(this);
    }

    public abstract void paint(Canvas canvas, Paint paint);

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public double getTime(){
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public abstract boolean isDone();
}
