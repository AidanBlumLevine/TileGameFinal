package com.example.aidan.tilegameredo.particles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.aidan.tilegameredo.Game;
import com.example.aidan.tilegameredo.Loader;
import com.example.aidan.tilegameredo.Tile;

public class winParticle  extends Particle {
    private Bitmap scaledTexture;
    private Game parent;
    public winParticle(int x, int y,Tile type,Game parent) {
        super(x, y, 360);
        this.parent = parent;
        scaledTexture = Bitmap.createScaledBitmap(Loader.getGlowCenter(parent.getContext()),type.getScaledTexture().getWidth(),type.getScaledTexture().getHeight(),false);
    }

    public void update(){
        super.setTime(super.getTime() - 500 / parent.getFps());
    }

    @Override
    public void paint(Canvas canvas, Paint paint) {
        paint.setAlpha((int)(120*(1-Math.cos(Math.toRadians(360-Math.max(super.getTime(),0))))));
        canvas.drawBitmap(scaledTexture,super.getX(),super.getY(),paint);
        update();
    }

    @Override
    public boolean isDone() {
        return super.getTime()<0;
    }
}
