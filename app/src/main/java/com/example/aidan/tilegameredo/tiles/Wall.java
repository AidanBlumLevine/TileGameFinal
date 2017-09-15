package com.example.aidan.tilegameredo.tiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.aidan.tilegameredo.GamePanel;
import com.example.aidan.tilegameredo.Tile;


public class Wall extends Tile {
    private Bitmap scaledTexture;

    public Wall(int xPos, int yPos,Bitmap img) {
        super(xPos, yPos,img);
        scaledTexture = Bitmap.createScaledBitmap(super.getTexture(),(int)(GamePanel.getPlayingField().height()/GamePanel.getTilesInLevel()*GamePanel.getSizeMultiplier()),(int)(GamePanel.getPlayingField().height()/GamePanel.getTilesInLevel()*GamePanel.getSizeMultiplier()),false);

    }
    public void paint(Canvas canvas, Paint paint){
        canvas.drawBitmap(scaledTexture,super.getX()*GamePanel.getPlayingField().height()/GamePanel.getTilesInLevel()/30+GamePanel.getPlayingField().left,super.getY()*GamePanel.getPlayingField().height()/GamePanel.getTilesInLevel()/30+GamePanel.getPlayingField().top,paint);
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
        scaledTexture = Bitmap.createScaledBitmap(super.getTexture(),(int)(GamePanel.getPlayingField().height()/GamePanel.getTilesInLevel()*GamePanel.getSizeMultiplier()),(int)(GamePanel.getPlayingField().height()/GamePanel.getTilesInLevel()*GamePanel.getSizeMultiplier()),false);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }
    @Override
    public Bitmap getScaledTexture() {
        return scaledTexture;
    }

}
