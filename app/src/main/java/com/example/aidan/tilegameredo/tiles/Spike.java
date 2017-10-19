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

    public Spike(int xPos, int yPos,int direction,Bitmap img,Game parent) {
        super(xPos, yPos,img);
        this.parent=parent;
        this.direction = direction;
        scaledTexture = Bitmap.createScaledBitmap(super.getTexture(),(int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()),(int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()),false);
    }
    public void paint(Canvas canvas, Paint paint){
        canvas.save();
        canvas.rotate((direction-1)*90,super.getX()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().left+((int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()))/2,
                (super.getY()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().top+((int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()))/2));
        canvas.drawBitmap(scaledTexture,super.getX()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().left,super.getY()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().top,paint);
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
