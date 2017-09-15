package com.example.aidan.tilegameredo.levelEditor.dumbTiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.aidan.tilegameredo.Tile;
import com.example.aidan.tilegameredo.levelEditor.LevelEditor;

public class DumbDoubleCrate extends Tile {
    private double oldX,oldY;
    private Bitmap scaledTexture;

    private int position;
    //1 is sideways
    //2 is upright

    public DumbDoubleCrate(int xPos, int yPos,int position,Bitmap img) {
        super(xPos, yPos, img);
        oldX = xPos;
        oldY = yPos;
        this.position = position;
        if (position == 1) {
            scaledTexture = Bitmap.createScaledBitmap(super.getTexture(), (int) (LevelEditor.getPlayingField().width() / LevelEditor.getLevelWidth() * (2+(1-LevelEditor.getSizeMultiplier())) * LevelEditor.getSizeMultiplier()), (int) (LevelEditor.getPlayingField().width() / LevelEditor.getLevelWidth() * LevelEditor.getSizeMultiplier()), false);
        } else {
            scaledTexture = Bitmap.createScaledBitmap(super.getTexture(), (int) (LevelEditor.getPlayingField().width() / LevelEditor.getLevelWidth() * LevelEditor.getSizeMultiplier()), (int) (LevelEditor.getPlayingField().width() / LevelEditor.getLevelWidth() * (2+(1-LevelEditor.getSizeMultiplier())) * LevelEditor.getSizeMultiplier()), false);

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
            scaledTexture = Bitmap.createScaledBitmap(super.getTexture(), (int) (LevelEditor.getPlayingField().width() / LevelEditor.getLevelWidth() * (2+(1-LevelEditor.getSizeMultiplier())) * LevelEditor.getSizeMultiplier()), (int) (LevelEditor.getPlayingField().width() / LevelEditor.getLevelWidth() * LevelEditor.getSizeMultiplier()), false);
        } else {
            scaledTexture = Bitmap.createScaledBitmap(super.getTexture(), (int) (LevelEditor.getPlayingField().width() / LevelEditor.getLevelWidth() * LevelEditor.getSizeMultiplier()), (int) (LevelEditor.getPlayingField().width() / LevelEditor.getLevelWidth() * (2+(1-LevelEditor.getSizeMultiplier())) * LevelEditor.getSizeMultiplier()), false);

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
        canvas.drawBitmap(scaledTexture,(int)oldX * LevelEditor.getPlayingField().height() / LevelEditor.getLevelWidth() / 30 + LevelEditor.getPlayingField().left, (int)oldY * LevelEditor.getPlayingField().height() / LevelEditor.getLevelWidth() / 30 + LevelEditor.getPlayingField().top, paint);
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
