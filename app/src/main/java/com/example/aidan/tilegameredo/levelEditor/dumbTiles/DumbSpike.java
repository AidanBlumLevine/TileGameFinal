package com.example.aidan.tilegameredo.levelEditor.dumbTiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.aidan.tilegameredo.Tile;
import com.example.aidan.tilegameredo.levelEditor.LevelEditor;

public class DumbSpike extends Tile {
    private int direction;
    //1: ->
    //2: |
    //3 <-
    //4  ^
    private Bitmap scaledTexture;
    private LevelEditor parent;
    public DumbSpike(int xPos, int yPos,int direction,Bitmap img,LevelEditor parent) {
        super(xPos, yPos,img);
        this.parent=parent;
        this.direction = direction;
        scaledTexture = Bitmap.createScaledBitmap(super.getTexture(),(int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()),(int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()),false);
    }
    public void paint(Canvas canvas, Paint paint){
        canvas.save();
        canvas.rotate((direction-1)*90,super.getX()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().left+((int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()))/2
                ,super.getY()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().top+((int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()))/2);
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

    }

    @Override
    public Bitmap getScaledTexture() {
        return null;
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void update() {
        scaledTexture = Bitmap.createScaledBitmap(super.getTexture(),(int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()),(int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()),false);
    }


    public int getPosition() {
        return direction;
    }
}
