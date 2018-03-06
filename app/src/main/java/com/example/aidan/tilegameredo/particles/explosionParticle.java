package com.example.aidan.tilegameredo.particles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class explosionParticle extends Particle {
    Random generator = new Random();
    private ArrayList<int[]> explosion = new ArrayList<>();
    public explosionParticle(int x,int y){
        super(x,y,0);
        for(int i=0;i<30;i++){
            explosion.add(new int[]{(int)(Math.random()*15),(int)(Math.random()*30+60)});
        }
    }
    @Override
    public void paint(Canvas canvas, Paint paint) {
        canvas.save();
        paint.setColor(Color.WHITE);
        for(int i=0;i<explosion.size();i++){
            if(explosion.get(i)[1]>0){
//                if(randomSeeded(i)) {
//                    paint.setColor(Color.WHITE);
//                } else {
//                    paint.setColor(Color.YELLOW);
//                }
                int x = (int)(Math.sin(i*12)*explosion.get(i)[0]);
                int y = (int)(Math.cos(i*12)*explosion.get(i)[0]);
                canvas.drawCircle(x + super.getX(), y + super.getY(), explosion.get(i)[1], paint);
                explosion.get(i)[0]+=Math.random()*20;
                explosion.get(i)[1]-=Math.random()*4;
            }
        }
        canvas.restore();
    }

    @Override
    public boolean isDone() {
        for(int[] i:explosion){
            if(i[1]>0){
                return false;
            }
        }
        return true;
    }

    boolean randomSeeded(long seed) {
        generator.setSeed(seed);
        boolean num = generator.nextBoolean();
        return num;
    }
}
