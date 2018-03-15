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
    private double oldX,oldY,acceleration;
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
        this.context=context;
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
        canvas.drawBitmap(scaledTexture,(int)(oldX*parent.getPlayingField().height()/parent.getLevelWidth()+parent.getPlayingField().left),(int)(oldY*parent.getPlayingField().height()/parent.getLevelWidth()+parent.getPlayingField().top),paint);
    }
    public void update() {
        if (oldX < super.getX()) {
            oldX += 33.3 / parent.getFps() * acceleration;
        }
        if (oldX > super.getX()) {
            oldX -= 33.3 / parent.getFps() * acceleration;
        }
        if (oldY < super.getY()) {
            oldY += 33.3 / parent.getFps() * acceleration;
        }
        if (oldY > super.getY()) {
            oldY -= 33.3 / parent.getFps() * acceleration;
        }
        if (inMotion) {
            acceleration += .2;
        }
        if (parent.isSpike((int) oldX, (int) oldY)) {
            if (!dead) {
                dissolveParticle p = new dissolveParticle((int) (oldX * parent.getPlayingField().height() / parent.getLevelWidth()  + parent.getPlayingField().left), (int) (oldY * parent.getPlayingField().height() / parent.getLevelWidth()  + parent.getPlayingField().top), this, parent);
            }
            fadeParticle f = new fadeParticle(parent);
            dead = true;
        }
        if (Math.abs(oldY - super.getY()) <= 33.3 / parent.getFps() * acceleration) {
            if (parent.isTile(super.getX(), super.getY() + 1, Wall.class) && oldY!=super.getY() && inMotion) {
                hitParticle p = new hitParticle(super.getX() * parent.getPlayingField().height() / parent.getLevelWidth()  + parent.getPlayingField().left, super.getY() * parent.getPlayingField().height() / parent.getLevelWidth()  + parent.getPlayingField().top, 2, parent, context);
                parent.addSound("hit");
            }else if(parent.isSolidTile(super.getX(), super.getY()+1) && oldY!=super.getY() && inMotion){
                parent.addSound("hit");
            }
            if (parent.isTile(super.getX(), super.getY() - 1, Wall.class) && oldY!=super.getY() && inMotion) {
                hitParticle p = new hitParticle(super.getX() * parent.getPlayingField().height() / parent.getLevelWidth()  + parent.getPlayingField().left, super.getY() * parent.getPlayingField().height() / parent.getLevelWidth()  + parent.getPlayingField().top, 4, parent, context);
                parent.addSound("hit");
            }else if(parent.isSolidTile(super.getX(), super.getY()-1) && oldY!=super.getY() && inMotion){
                parent.addSound("hit");
            }
            if (oldY != super.getY() && inMotion && !dead) {
                inMotion = false;
            }
            oldY = super.getY();
        }
        if (Math.abs(oldX - super.getX()) <= 33.3 / parent.getFps() * acceleration) {
            if (parent.isTile(super.getX() + 1, super.getY(), Wall.class) && oldX!=super.getX() && inMotion) {
                hitParticle p = new hitParticle(super.getX() * parent.getPlayingField().height() / parent.getLevelWidth()  + parent.getPlayingField().left, super.getY() * parent.getPlayingField().height() / parent.getLevelWidth()  + parent.getPlayingField().top, 1, parent, context);
                parent.addSound("hit");
            }else if(parent.isSolidTile(super.getX()+1, super.getY()) && oldX!=super.getX() && inMotion){
                parent.addSound("hit");
            }
            if (parent.isTile(super.getX() - 1, super.getY(), Wall.class) && oldX!=super.getX() && inMotion) {
                hitParticle p = new hitParticle(super.getX() * parent.getPlayingField().height() / parent.getLevelWidth()  + parent.getPlayingField().left, super.getY() * parent.getPlayingField().height() / parent.getLevelWidth()  + parent.getPlayingField().top, 3, parent, context);
                parent.addSound("hit");
            }else if(parent.isSolidTile(super.getX()-1, super.getY()) && oldX!=super.getX() && inMotion){
                parent.addSound("hit");
            }
            if (oldX != super.getX() && inMotion && !dead) {
                inMotion = false;
            }
            oldX = super.getX();
        }
    }

    @Override
    public void pushLeft() {
        oldX=super.getX();
        oldY=super.getY();
        int i=1;
        while(!parent.isTileBesides(super.getX()-i,super.getY(), Box.class)){
            i++;
        }
        super.setX(super.getX()-(i-1));
        inMotion=true;
        acceleration = 1;
    }

    @Override
    public void pushRight() {
        oldX=super.getX();
        oldY=super.getY();
        int i=1;
        while(!parent.isTileBesides(super.getX()+i,super.getY(), Box.class)){
            i++;
        }
        super.setX(super.getX()+(i-1));
        inMotion=true;
        acceleration = 1;
    }
    @Override
    public void pushUp() {
        oldX=super.getX();
        oldY=super.getY();
        int i=1;
        while(!parent.isTileBesides(super.getX(),super.getY()-i, Box.class)){
            i++;
        }
        super.setY(super.getY()-(i-1));
        inMotion=true;
        acceleration = 1;
    }

    @Override
    public void pushDown() {
        oldX=super.getX();
        oldY=super.getY();
        int i=1;
        while(!parent.isTileBesides(super.getX(),super.getY()+i, Box.class)){
            i++;
        }
        super.setY(super.getY()+(i-1));
        inMotion=true;
        acceleration = 1;
    }

    public boolean isDead() {
        return dead;
    }

    @Override
    public Bitmap getScaledTexture() {
        return scaledTexture;
    }
}
