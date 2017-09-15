package com.example.aidan.tilegameredo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Tile {
    private int x,y;
    private Bitmap texture;
    public Tile(int xPos, int yPos, Bitmap tex){
        x=xPos;
        y=yPos;
        this.texture=tex;
    }

    public abstract void paint(Canvas canvas, Paint paint);
    public abstract void pushLeft();
    public abstract void pushRight();
    public abstract void pushUp();
    public abstract void pushDown();
    public abstract void update();
    public abstract boolean isMoving();
    public abstract void updateSize();

    public Bitmap getTexture() {
        return texture;
    }

    public abstract Bitmap getScaledTexture();

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setX(int x){
        this.x=x;
    }
    public void setY(int y){
        this.y=y;
    }

    public abstract boolean isDead();
}
