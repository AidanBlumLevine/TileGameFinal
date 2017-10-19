package com.example.aidan.tilegameredo.tiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.aidan.tilegameredo.Game;
import com.example.aidan.tilegameredo.Tile;


public class Wall extends Tile {
    private Bitmap scaledTexture;

    public Wall(int xPos, int yPos,Bitmap img) {
        super(xPos, yPos,img);
        scaledTexture = Bitmap.createScaledBitmap(super.getTexture(),(int)(Game.getPlayingField().height()/Game.getLevelWidth()*Game.getSizeMultiplier()),(int)(Game.getPlayingField().height()/Game.getLevelWidth()*Game.getSizeMultiplier()),false);

    }
    public void paint(Canvas canvas, Paint paint){
        canvas.drawBitmap(scaledTexture,super.getX()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().left,super.getY()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().top,paint);
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
