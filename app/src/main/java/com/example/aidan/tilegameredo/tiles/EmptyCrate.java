package com.example.aidan.tilegameredo.tiles;

import android.content.Context;
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
    private Game parent;
    private Context context;

    public EmptyCrate(int xPos, int yPos,Bitmap img,Game parent,Context context) {
        super(xPos, yPos,img);
        this.parent=parent;
        oldX=xPos;
        oldY=yPos;
        scaledTexture = Bitmap.createScaledBitmap(super.getTexture(),(int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()),(int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()),false);
    }
    public boolean isMoving(){
        return !((int) oldX == super.getX() && (int) oldY == super.getY());
    }

    @Override
    public void updateSize() {
        scaledTexture = Bitmap.createScaledBitmap(super.getTexture(),(int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()),(int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()),false);
    }

    public void paint(Canvas canvas, Paint paint){
        canvas.drawBitmap(scaledTexture,(int)oldX*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().left,(int)oldY*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().top,paint);
    }
    public void update(){
        if(oldX<super.getX()){
            oldX+=moveSpeed*1000/parent.getFps();
        }
        if(oldX>super.getX()){
            oldX-=moveSpeed*1000/parent.getFps();
        }
        if(oldY<super.getY()){
            oldY+=moveSpeed*1000/parent.getFps();
        }
        if(oldY>super.getY()){
            oldY-=moveSpeed*1000/parent.getFps();
        }
        if(parent.isSpike((int)oldX,(int)oldY)){
            if(!dead){
                dissolveParticle p = new dissolveParticle((int)oldX*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().left, (int)oldY*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().top,this,parent);
            }
            fadeParticle f = new fadeParticle(parent);
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
        if(Math.abs(oldY-super.getY())<=moveSpeed*1001.0/parent.getFps() && oldY!=super.getY() && inMotion ){
            if(parent.isTile(super.getX(), super.getY()+30, Wall.class)){
                hitParticle p = new hitParticle(super.getX()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().left, super.getY()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().top,2,parent,context);
            }
            if(parent.isTile(super.getX(), super.getY()-30, Wall.class)){
                hitParticle p = new hitParticle(super.getX()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().left, super.getY()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().top,4,parent,context);
            }
        }
        if(Math.abs(oldX-super.getX())<=moveSpeed*1001.0/parent.getFps() && oldX!=super.getX() && inMotion ){
            if(parent.isTile(super.getX()+30, super.getY(), Wall.class)){
                hitParticle p = new hitParticle(super.getX()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().left, super.getY()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().top,1,parent,context);
            }
            if(parent.isTile(super.getX()-30, super.getY(), Wall.class)){
                hitParticle p = new hitParticle(super.getX()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().left, super.getY()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().top,3,parent,context);
            }
        }
    }

    @Override
    public void pushLeft() {
        oldX=super.getX();
        oldY=super.getY();
        int i=1;
        while(!parent.isTileBesides(super.getX()-i*30,super.getY(), Box.class)){
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
        while(!parent.isTileBesides(super.getX()+i*30,super.getY(), Box.class)){
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
        while(!parent.isTileBesides(super.getX(),super.getY()-i*30, Box.class)){
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
        while(!parent.isTileBesides(super.getX(),super.getY()+i*30, Box.class)){
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
