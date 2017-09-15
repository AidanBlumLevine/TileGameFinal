package com.example.aidan.tilegameredo.tiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.aidan.tilegameredo.Game;
import com.example.aidan.tilegameredo.Tile;
import com.example.aidan.tilegameredo.particles.dissolveParticle;
import com.example.aidan.tilegameredo.particles.fadeParticle;
import com.example.aidan.tilegameredo.particles.hitParticle;


public class EmptyCrate extends Tile {
    private double oldX,oldY,moveSpeed;
    private boolean dead = false;
    private boolean inMotion=true;
    private Bitmap scaledTexture;
    public EmptyCrate(int xPos, int yPos,Bitmap img) {
        super(xPos, yPos,img);
        oldX=xPos;
        oldY=yPos;
        scaledTexture = Bitmap.createScaledBitmap(super.getTexture(),(int)(Game.getPlayingField().height()/Game.getLevelWidth()*Game.getSizeMultiplier()),(int)(Game.getPlayingField().height()/Game.getLevelWidth()*Game.getSizeMultiplier()),false);
    }
    public boolean isMoving(){
        return !((int) oldX == super.getX() && (int) oldY == super.getY());
    }

    @Override
    public void updateSize() {
        scaledTexture = Bitmap.createScaledBitmap(super.getTexture(),(int)(Game.getPlayingField().height()/Game.getLevelWidth()*Game.getSizeMultiplier()),(int)(Game.getPlayingField().height()/Game.getLevelWidth()*Game.getSizeMultiplier()),false);
    }

    public void paint(Canvas canvas, Paint paint){
        canvas.drawBitmap(scaledTexture,(int)oldX*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().left,(int)oldY*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().top,paint);
    }
    public void update(){
        if(oldX<super.getX()){
            oldX+=moveSpeed*1000/Game.getFps();
        }
        if(oldX>super.getX()){
            oldX-=moveSpeed*1000/Game.getFps();
        }
        if(oldY<super.getY()){
            oldY+=moveSpeed*1000/Game.getFps();
        }
        if(oldY>super.getY()){
            oldY-=moveSpeed*1000/Game.getFps();
        }
        if(Game.isSpike((int)oldX,(int)oldY)){
            if(!dead){
                dissolveParticle p = new dissolveParticle((int)oldX*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().left, (int)oldY*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().top,this);
            }
            fadeParticle f = new fadeParticle();
            dead=true;
        }
        if(Math.abs(oldY-super.getY())<=2){
            if(oldY!=super.getY() && inMotion && !dead){
                inMotion=false;
            }
            oldY=super.getY();
        }
        if(Math.abs(oldX-super.getX())<=2){
            if(oldX!=super.getX() && inMotion && !dead){
                inMotion=false;
            }
            oldX=super.getX();
        }
        if(Math.abs(oldY-super.getY())<=moveSpeed*1001.0/Game.getFps() && oldY!=super.getY() && inMotion ){
            if(Game.isTile(super.getX(), super.getY()+30, Wall.class)){
                hitParticle p = new hitParticle(super.getX()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().left, super.getY()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().top,2);
            }
            if(Game.isTile(super.getX(), super.getY()-30, Wall.class)){
                hitParticle p = new hitParticle(super.getX()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().left, super.getY()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().top,4);
            }
        }
        if(Math.abs(oldX-super.getX())<=moveSpeed*1001.0/Game.getFps() && oldX!=super.getX() && inMotion ){
            if(Game.isTile(super.getX()+30, super.getY(), Wall.class)){
                hitParticle p = new hitParticle(super.getX()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().left, super.getY()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().top,1);
            }
            if(Game.isTile(super.getX()-30, super.getY(), Wall.class)){
                hitParticle p = new hitParticle(super.getX()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().left, super.getY()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().top,3);
            }
        }
    }

    @Override
    public void pushLeft() {
        oldX=super.getX();
        oldY=super.getY();
        int i=1;
        while(!Game.isTileBesides(super.getX()-i*30,super.getY(), Box.class)){
            i++;
        }
        super.setX(super.getX()-(i-1)*30);
        moveSpeed = Math.abs((oldX-super.getX())/100.0);
        inMotion=true;
    }

    @Override
    public void pushRight() {
        oldX=super.getX();
        oldY=super.getY();
        int i=1;
        while(!Game.isTileBesides(super.getX()+i*30,super.getY(), Box.class)){
            i++;
        }
        super.setX(super.getX()+(i-1)*30);
        moveSpeed = Math.abs((oldX-super.getX())/100.0);
        inMotion=true;
    }
    @Override
    public void pushUp() {
        oldX=super.getX();
        oldY=super.getY();
        int i=1;
        while(!Game.isTileBesides(super.getX(),super.getY()-i*30, Box.class)){
            i++;
        }
        super.setY(super.getY()-(i-1)*30);
        moveSpeed = Math.abs((oldY-super.getY())/100.0);
        inMotion=true;
    }

    @Override
    public void pushDown() {
        oldX=super.getX();
        oldY=super.getY();
        int i=1;
        while(!Game.isTileBesides(super.getX(),super.getY()+i*30, Box.class)){
            i++;
        }
        super.setY(super.getY()+(i-1)*30);
        moveSpeed = Math.abs((oldY-super.getY())/100.0);
        inMotion=true;
    }

    public boolean isDead() {
        return dead;
    }

    @Override
    public Bitmap getScaledTexture() {
        return scaledTexture;
    }
}
