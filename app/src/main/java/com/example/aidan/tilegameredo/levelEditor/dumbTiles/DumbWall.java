package com.example.aidan.tilegameredo.levelEditor.dumbTiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.aidan.tilegameredo.Tile;
import com.example.aidan.tilegameredo.levelEditor.LevelEditor;

public class DumbWall extends Tile {
    private Bitmap scaledTexture;

    public DumbWall(int xPos, int yPos,Bitmap img) {
        super(xPos, yPos,img);
        scaledTexture = Bitmap.createScaledBitmap(super.getTexture(),(int)(LevelEditor.getPlayingField().height()/LevelEditor.getLevelWidth()*LevelEditor.getSizeMultiplier()),(int)(LevelEditor.getPlayingField().height()/LevelEditor.getLevelWidth()*LevelEditor.getSizeMultiplier()),false);

    }
    public void paint(Canvas canvas, Paint paint){
        canvas.drawBitmap(scaledTexture,super.getX()*LevelEditor.getPlayingField().height()/LevelEditor.getLevelWidth()/30+LevelEditor.getPlayingField().left,super.getY()*LevelEditor.getPlayingField().height()/LevelEditor.getLevelWidth()/30+LevelEditor.getPlayingField().top,paint);
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
        scaledTexture = Bitmap.createScaledBitmap(super.getTexture(),(int)(LevelEditor.getPlayingField().height()/LevelEditor.getLevelWidth()*LevelEditor.getSizeMultiplier()),(int)(LevelEditor.getPlayingField().height()/LevelEditor.getLevelWidth()*LevelEditor.getSizeMultiplier()),false);

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
}



}
