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

    public Spike(int xPos, int yPos,int direction,Bitmap img) {
        super(xPos, yPos,img);
        this.direction = direction;
        scaledTexture = Bitmap.createScaledBitmap(super.getTexture(),(int)(Game.getPlayingField().height()/Game.getLevelWidth()*Game.getSizeMultiplier()),(int)(Game.getPlayingField().height()/Game.getLevelWidth()*Game.getSizeMultiplier()),false);
    }
    public void paint(Canvas canvas, Paint paint){
        canvas.save();
        canvas.rotate((direction-1)*90,super.getX()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().left+((int)(Game.getPlayingField().height()/Game.getLevelWidth()*Game.getSizeMultiplier()))/2,
                (super.getY()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().top+((int)(Game.getPlayingField().height()/Game.getLevelWidth()*Game.getSizeMultiplier()))/2));
        canvas.drawBitmap(scaledTexture,super.getX()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().left,super.getY()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().top,paint);
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
        scaledTexture = Bitmap.createScaledBitmap(super.getTexture(),(int)(Game.getPlayingField().height()/Game.getLevelWidth()*Game.getSizeMultiplier()),(int)(Game.getPlayingField().height()/Game.getLevelWidth()*Game.getSizeMultiplier()),false);
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
