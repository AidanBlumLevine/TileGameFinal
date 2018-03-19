package com.example.aidan.tilegameredo.tiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.aidan.tilegameredo.Game;
import com.example.aidan.tilegameredo.Tile;

public class Spike extends Tile {
    private int direction;
    //1: ->
    //2: |
    //3 <-
    //4  ^
    private Bitmap scaledTexture;
    private Game parent;
    private int trueX,trueY,width;
    public Spike(int xPos, int yPos,int direction,Bitmap img,Game parent) {
        super(xPos, yPos,img);
        this.parent=parent;
        this.direction = direction;
        width = (int)Math.round((double)parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier());
        trueX = (int)Math.round((double)super.getX()*parent.getPlayingField().height()/parent.getLevelWidth()+parent.getPlayingField().left);
        trueY = (int)Math.round((double)super.getY()*parent.getPlayingField().height()/parent.getLevelWidth()+parent.getPlayingField().top);
        scaledTexture = Bitmap.createScaledBitmap(super.getTexture(),width,width,false);
    }
    public void paint(Canvas canvas, Paint paint){

        canvas.save();
        canvas.rotate((direction-1)*90,trueX+width/2,
                trueY+width/2);
        canvas.drawBitmap(scaledTexture,trueX,trueY,paint);
        canvas.restore();
    }

    @Override
    public void pushLeft() {
    }

    @Override
    public void pushRight() {
    }

    @Override
    public void pushUp() {
    }

    @Override
    public void pushDown() {
    }

    @Override
    public boolean isMoving() {
        return false;
    }

    @Override
    public void updateSize() {
        scaledTexture = Bitmap.createScaledBitmap(super.getTexture(),(int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()),(int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()),false);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }
    @Override
    public Bitmap getScaledTexture() {
        return scaledTexture;
    }

    @Override
    public boolean isDead() {
        return false;
    }
}
