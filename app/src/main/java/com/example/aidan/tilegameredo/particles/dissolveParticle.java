package com.example.aidan.tilegameredo.particles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Pair;

import com.example.aidan.tilegameredo.Game;
import com.example.aidan.tilegameredo.Tile;

import java.util.ArrayList;

public class dissolveParticle  extends Particle {
    private ArrayList<Pair<Bitmap,Point>> chunks = new ArrayList<>();
    public dissolveParticle(int x, int y,Tile type) {
        super(x, y, 100);
        Bitmap texture=type.getScaledTexture();
//        for(int i=0;i<texture.getWidth();i+=texture.getWidth()/4){
//            for(int r=0;r<texture.getHeight();r+=texture.getHeight()/4){
//                chunks.add(new Pair(Bitmap.createBitmap(texture,i,r,Math.min(texture.getWidth()/4,texture.getWidth()-i), Math.min(texture.getHeight()/4,texture.getHeight()-r)),new Point(i,r)));
//            }
//        }
        chunks.add(new Pair(Bitmap.createBitmap(texture,0,0,texture.getWidth()/4, texture.getHeight()/4),new Point(0,0)));
        chunks.add(new Pair(Bitmap.createBitmap(texture,texture.getWidth()/4,0,texture.getWidth()/4, texture.getHeight()/4),new Point(texture.getWidth()/4,0)));
        chunks.add(new Pair(Bitmap.createBitmap(texture,texture.getWidth()/4*2,0,texture.getWidth()/4, texture.getHeight()/4),new Point(texture.getWidth()/4*2,0)));
        chunks.add(new Pair(Bitmap.createBitmap(texture,texture.getWidth()/4*3,0,texture.getWidth()/4, texture.getHeight()/4),new Point(texture.getWidth()/4*3,0)));
        chunks.add(new Pair(Bitmap.createBitmap(texture,texture.getWidth()/4*3,texture.getHeight()/4,texture.getWidth()/4, texture.getHeight()/4),new Point(texture.getWidth()/4*3,texture.getHeight()/4)));
        chunks.add(new Pair(Bitmap.createBitmap(texture,texture.getWidth()/4*3,texture.getHeight()/4*2,texture.getWidth()/4, texture.getHeight()/4),new Point(texture.getWidth()/4*3,texture.getHeight()/4*2)));
        chunks.add(new Pair(Bitmap.createBitmap(texture,texture.getWidth()/4*3,texture.getHeight()/4*3,texture.getWidth()/4, texture.getHeight()/4),new Point(texture.getWidth()/4*3,texture.getHeight()/4*3)));
        chunks.add(new Pair(Bitmap.createBitmap(texture,texture.getWidth()/4*2,texture.getHeight()/4*3,texture.getWidth()/4, texture.getHeight()/4),new Point(texture.getWidth()/4*2,texture.getHeight()/4*3)));
        chunks.add(new Pair(Bitmap.createBitmap(texture,texture.getWidth()/4,texture.getHeight()/4*3,texture.getWidth()/4, texture.getHeight()/4),new Point(texture.getWidth()/4,texture.getHeight()/4*3)));
        chunks.add(new Pair(Bitmap.createBitmap(texture,0,texture.getHeight()/4*3,texture.getWidth()/4, texture.getHeight()/4),new Point(0,texture.getHeight()/4*3)));
        chunks.add(new Pair(Bitmap.createBitmap(texture,0,texture.getHeight()/4*2,texture.getWidth()/4, texture.getHeight()/4),new Point(0,texture.getHeight()/4*2)));
        chunks.add(new Pair(Bitmap.createBitmap(texture,0,texture.getHeight()/4,texture.getWidth()/4, texture.getHeight()/4),new Point(0,texture.getHeight()/4)));
        chunks.add(new Pair(Bitmap.createBitmap(texture,texture.getWidth()/4,texture.getHeight()/4,texture.getWidth()/4, texture.getHeight()/4),new Point(texture.getWidth()/4,texture.getHeight()/4)));
        chunks.add(new Pair(Bitmap.createBitmap(texture,texture.getWidth()/4*2,texture.getHeight()/4,texture.getWidth()/4, texture.getHeight()/4),new Point(texture.getWidth()/4*2,texture.getHeight()/4)));
        chunks.add(new Pair(Bitmap.createBitmap(texture,texture.getWidth()/4*2,texture.getHeight()/4*2,texture.getWidth()/4, texture.getHeight()/4),new Point(texture.getWidth()/4*2,texture.getHeight()/4*2)));
        chunks.add(new Pair(Bitmap.createBitmap(texture,texture.getWidth()/4,texture.getHeight()/4*2,texture.getWidth()/4, texture.getHeight()/4),new Point(texture.getWidth()/4,texture.getHeight()/4*2)));

    }

    public void update(){
        if(!chunks.isEmpty()) {
            super.setTime(super.getTime() - 1000 / Game.getFps());
            if (super.getTime() < chunks.size() * 6.25) {
                //chunks.remove((int)(Math.random()*chunks.size()));
                chunks.remove(0);
            }
        }
    }

    @Override
    public void paint(Canvas canvas, Paint paint) {
        for(Pair p:chunks){
            canvas.drawBitmap((Bitmap)p.first,((Point)p.second).x+super.getX(),((Point)p.second).y+super.getY(),paint);
        }
        update();
    }

    @Override
    public boolean isDone() {
        return chunks.isEmpty();
    }
}
