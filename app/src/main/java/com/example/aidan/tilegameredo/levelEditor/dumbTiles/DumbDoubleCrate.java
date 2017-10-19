package com.example.aidan.tilegameredo.levelEditor.dumbTiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.aidan.tilegameredo.Tile;
import com.example.aidan.tilegameredo.levelEditor.LevelEditor;

public class DumbDoubleCrate extends Tile {
    private double oldX,oldY;
    private Bitmap scaledTexture;
    private LevelEditor parent;
    private int position;
    //1 is sideways
    //2 is upright

    public DumbDoubleCrate(int xPos, int yPos,int position,Bitmap img,LevelEditor parent) {
        super(xPos, yPos, img);
        this.parent=parent;
        oldX = xPos;
        oldY = yPos;
        this.position = position;
        if (position == 1) {
            scaledTexture = Bitmap.createScaledBitmap(super.getTexture(), (int) (parent.getPlayingField().width() / parent.getLevelWidth() * (2+(1-parent.getSizeMultiplier())) * parent.getSizeMultiplier()), (int) (parent.getPlayingField().width() / parent.getLevelWidth() * parent.getSizeMultiplier()), false);
        } else {
            scaledTexture = Bitmap.createScaledBitmap(super.getTexture(), (int) (parent.getPlayingField().width() / parent.getLevelWidth() * parent.getSizeMultiplier()), (int) (parent.getPlayingField().width() / parent.getLevelWidth() * (2+(1-parent.getSizeMultiplier())) * parent.getSizeMultiplier()), false);

        }
    }

    public int getPosition() {
        return position;
    }

    public boolean isMoving(){
        return false;
    }

    @Override
    public void updateSize() {
        if (position == 1) {
            scaledTexture = Bitmap.createScaledBitmap(super.getTexture(), (int) (parent.getPlayingField().width() / parent.getLevelWidth() * (2+(1-parent.getSizeMultiplier())) * parent.getSizeMultiplier()), (int) (parent.getPlayingField().width() / parent.getLevelWidth() * parent.getSizeMultiplier()), false);
        } else {
            scaledTexture = Bitmap.createScaledBitmap(super.getTexture(), (int) (parent.getPlayingField().width() / parent.getLevelWidth() * parent.getSizeMultiplier()), (int) (parent.getPlayingField().width() / parent.getLevelWidth() * (2+(1-parent.getSizeMultiplier())) * parent.getSizeMultiplier()), false);

        }
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
    public void paint(Canvas canvas, Paint paint) {
        canvas.drawBitmap(scaledTexture,(int)oldX * parent.getPlayingField().height() / parent.getLevelWidth() / 30 + parent.getPlayingField().left, (int)oldY * parent.getPlayingField().height() / parent.getLevelWidth() / 30 + parent.getPlayingField().top, paint);
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
