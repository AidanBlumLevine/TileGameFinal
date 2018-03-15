package com.example.aidan.tilegameredo.levelEditor.dumbTiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.aidan.tilegameredo.Tile;
import com.example.aidan.tilegameredo.levelEditor.LevelEditor;


public class DumbEmptyCrate extends Tile {
    private double oldX,oldY;
    private Bitmap scaledTexture;
    private LevelEditor parent;
    public DumbEmptyCrate(int xPos, int yPos, Bitmap img,LevelEditor parent) {
        super(xPos, yPos,img);
        this.parent=parent;
        oldX=xPos;
        oldY=yPos;
        scaledTexture = Bitmap.createScaledBitmap(super.getTexture(),(int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()),(int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()),false);
    }
    public boolean isMoving(){
        return false;
    }

    @Override
    public void updateSize() {
        scaledTexture = Bitmap.createScaledBitmap(super.getTexture(),(int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()),(int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()),false);
    }

    @Override
    public Bitmap getScaledTexture() {
        return null;
    }

    @Override
    public boolean isDead() {
        return false;
    }

    public void paint(Canvas canvas, Paint paint){
        canvas.drawBitmap(scaledTexture,(int)oldX*parent.getPlayingField().height()/parent.getLevelWidth()+parent.getPlayingField().left,(int)oldY*parent.getPlayingField().height()/parent.getLevelWidth()+parent.getPlayingField().top,paint);
    }
    public void update(){
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

}
