package com.example.aidan.tilegameredo.levelEditor.dumbTiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.aidan.tilegameredo.Tile;
import com.example.aidan.tilegameredo.levelEditor.LevelEditor;
import com.example.aidan.tilegameredo.tiles.Wall;

public class DumbWall extends Tile {
    private int trueX,trueY,width;
    private LevelEditor parent;
    private boolean[][] surroundData = new boolean[3][3];
    public DumbWall(int xPos, int yPos,Bitmap img,LevelEditor parent) {
        super(xPos, yPos,img);
        this.parent=parent;
        trueX = (int)Math.round((double)super.getX()*parent.getPlayingField().height()/parent.getLevelWidth()+parent.getPlayingField().left);
        trueY = (int)Math.round((double)super.getY()*parent.getPlayingField().height()/parent.getLevelWidth()+parent.getPlayingField().top);
        width = (int)Math.round((double)parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier());
    }
    public void paint(Canvas canvas, Paint paint){
        //canvas.drawBitmap(scaledTexture,super.getX()*parent.getPlayingField().height()/parent.getLevelWidth()+parent.getPlayingField().left,super.getY()*parent.getPlayingField().height()/parent.getLevelWidth()+parent.getPlayingField().top,paint);
        paint.setColor(Color.rgb(147,147,147));
        canvas.drawRect(trueX,trueY,trueX+width,trueY+width,paint);
        paint.setColor(Color.rgb(186,186,186));
        if(surroundData[1][0]){
            canvas.drawRect(trueX+width/8,trueY,trueX+7*width/8,trueY+7*width/8,paint);
        }
        if(surroundData[0][1]){
            canvas.drawRect(trueX,trueY+width/8,trueX+7*width/8,trueY+7*width/8,paint);
        }
        if(surroundData[2][1]){
            canvas.drawRect(trueX+width/8,trueY+width/8,trueX+width,trueY+7*width/8,paint);
        }
        if(surroundData[1][2]){
            canvas.drawRect(trueX+width/8,trueY+width/8,trueX+7*width/8,trueY+width,paint);
        }

        if(surroundData[1][0] && surroundData[0][1]){
            if(surroundData[0][0]){
                canvas.drawRect(trueX,trueY,trueX+width/2,trueY+width/2,paint);
            } else {
                canvas.drawPath(triangle(trueX+width/8,trueY,trueX,trueY+width/8,trueX+width/2,trueY+width/2),paint);
            }
        }
        if(surroundData[1][0] && surroundData[2][1]){
            if(surroundData[2][0]){
                canvas.drawRect(trueX+width/2,trueY,trueX+width,trueY+width/2,paint);
            } else {
                canvas.drawPath(triangle(trueX+7*width/8,trueY,trueX+width,trueY+width/8,trueX+width/2,trueY+width/2),paint);
            }
        }
        if(surroundData[2][1] && surroundData[1][2]){
            if(surroundData[2][2]){
                canvas.drawRect(trueX+width/2,trueY+width/2,trueX+width,trueY+width,paint);
            } else {
                canvas.drawPath(triangle(trueX+7*width/8,trueY+width,trueX+width,trueY+7*width/8,trueX+width/2,trueY+width/2),paint);
            }
        }
        if(surroundData[1][2] && surroundData[0][1]){
            if(surroundData[0][2]){
                canvas.drawRect(trueX,trueY+width/2,trueX+width/2,trueY+width,paint);
            } else {
                canvas.drawPath(triangle(trueX,trueY+7*width/8,trueX+width/8,trueY+width,trueX+width/2,trueY+width/2),paint);
            }
        }

        if(!surroundData[1][0] && !surroundData[0][1] && !surroundData[2][1] && !surroundData[1][2]){
            canvas.drawRect(trueX+width/8,trueY+width/8,trueX+7*width/8,trueY+7*width/8,paint);
        }
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


    private Path triangle(int x1, int y1, int x2, int y2, int x3, int y3){
        Path p = new Path();
        p.moveTo(x1,y1);
        p.lineTo(x2,y2);
        p.lineTo(x3,y3);
        p.close();
        return p;
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
        for(int x=0;x<3;x++){
            for(int y=0;y<3;y++){
                surroundData[x][y] = (parent.isTile(x+super.getX()-1,y+super.getY()-1,DumbWall.class));
            }
        }
}



}
