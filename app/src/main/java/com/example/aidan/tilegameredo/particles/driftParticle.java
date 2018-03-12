package com.example.aidan.tilegameredo.particles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class driftParticle {
    private int x,y,size,speed;
    private double angle;
    private int color;
    public driftParticle(int x,int y,int size,int speed,double angle){
        this.x=x;
        this.y=y;
        this.size=size;
        this.speed=speed;
        this.angle=angle;
        color = Color.BLUE;
    }

    public void draw(Canvas canvas, Paint paint){
        paint.setColor(color);
        canvas.drawCircle(x,y,size,paint);
        y+=Math.sin(angle)*speed;
        x+=Math.cos(angle)*speed;
        speed--;
        size--;
    }

    public int getSize() {
        return size;
    }
}
