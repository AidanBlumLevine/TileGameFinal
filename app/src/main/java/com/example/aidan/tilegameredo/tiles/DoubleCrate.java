package com.example.aidan.tilegameredo.tiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.aidan.tilegameredo.Game;
import com.example.aidan.tilegameredo.Tile;
import com.example.aidan.tilegameredo.particles.dissolveParticle;
import com.example.aidan.tilegameredo.particles.hitParticle;

public class DoubleCrate extends Tile {
    private double oldX,oldY,moveSpeed;
    private boolean dead = false;
    private boolean inMotion=true;
    private Bitmap scaledTexture;
    private Game parent;
    private Context context;

    private int position;
    //1 is sideways
    //2 is upright

    public DoubleCrate(int xPos, int yPos,int position,Bitmap img,Game parent,Context context) {
        super(xPos, yPos, img);
        this.parent=parent;
        this.context=context;
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
        return !((int) oldX == super.getX() && (int) oldY == super.getY());
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
    public void paint(Canvas canvas, Paint paint) {
        canvas.drawBitmap(scaledTexture,(int)oldX * parent.getPlayingField().height() / parent.getLevelWidth() / 30 + parent.getPlayingField().left, (int)oldY * parent.getPlayingField().height() / parent.getLevelWidth() / 30 + parent.getPlayingField().top, paint);
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
        if(position==1 && ((parent.isSpike((int)oldX, (int)oldY)) || parent.isSpike((int)oldX+30, (int)oldY))){
            if(!dead){
                dissolveParticle p = new dissolveParticle((int)oldX*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().left, (int)oldY*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().top,this,parent);
                dissolveParticle p2 = new dissolveParticle((int)oldX*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().left, (int)oldY*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().top,this,parent);
            }
            dead=true;
        }
        if(position==2 && ((parent.isSpike((int)oldX, (int)oldY)) || parent.isSpike((int)oldX, (int)oldY+30))){
            if(!dead){
                Log.e("s",(int)oldX+","+ (int)oldY);
                dissolveParticle p = new dissolveParticle((int)oldX*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().left, (int)oldY*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().top,this,parent);
                dissolveParticle p2 = new dissolveParticle((int)oldX*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().left, (int)oldY*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().top,this,parent);
            }
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
            if(position==2){
                if(parent.isTile(super.getX(), super.getY()+60, Wall.class)){
                    hitParticle p = new hitParticle(super.getX()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().left, (super.getY()+30)*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().top,2,parent,context);
                }
                if(parent.isTile(super.getX(), super.getY()-30, Wall.class)){
                    hitParticle p = new hitParticle(super.getX()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().left, super.getY()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().top,4,parent,context);
                }
            }
            if(position==1){
                if(parent.isTile(super.getX(), super.getY()+30, Wall.class)){
                    hitParticle p = new hitParticle(super.getX()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().left, super.getY()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().top,2,parent,context);
                }
                if(parent.isTile(super.getX()+30, super.getY()+30, Wall.class)){
                    hitParticle p = new hitParticle((super.getX()+30)*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().left, super.getY()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().top,2,parent,context);
                }

                if(parent.isTile(super.getX(), super.getY()-30, Wall.class)){
                    hitParticle p = new hitParticle(super.getX()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().left, super.getY()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().top,4,parent,context);
                }
                if(parent.isTile(super.getX()+30, super.getY()-30, Wall.class)){
                    hitParticle p = new hitParticle((super.getX()+30)*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().left, super.getY()*parent.getPlayingField().height()/parent.getLevelWidth()/30+parent.getPlayingField().top,4,parent,context);
                }
            }
        }
        if(Math.abs(oldX-super.getX())<=moveSpeed*1001.0/parent.getFps() && oldX!=super.getX() && inMotion ){
            if(position==1) {
                if (parent.isTile(super.getX() + 60, super.getY(), Wall.class)) {
                    hitParticle p = new hitParticle((super.getX()+30) * parent.getPlayingField().height() / parent.getLevelWidth() / 30 + parent.getPlayingField().left, super.getY() * parent.getPlayingField().height() / parent.getLevelWidth() / 30 + parent.getPlayingField().top, 1,parent,context);
                }
                if (parent.isTile(super.getX() - 30, super.getY(), Wall.class)) {
                    hitParticle p = new hitParticle(super.getX() * parent.getPlayingField().height() / parent.getLevelWidth() / 30 + parent.getPlayingField().left, super.getY() * parent.getPlayingField().height() / parent.getLevelWidth() / 30 + parent.getPlayingField().top, 3,parent,context);
                }
            }
            if(position==2) {
                if (parent.isTile(super.getX() + 30, super.getY(), Wall.class)) {
                    hitParticle p = new hitParticle(super.getX() * parent.getPlayingField().height() / parent.getLevelWidth() / 30 + parent.getPlayingField().left, super.getY() * parent.getPlayingField().height() / parent.getLevelWidth() / 30 + parent.getPlayingField().top, 1,parent,context);
                }
                if (parent.isTile(super.getX() + 30, super.getY()+30, Wall.class)) {
                    hitParticle p = new hitParticle(super.getX() * parent.getPlayingField().height() / parent.getLevelWidth() / 30 + parent.getPlayingField().left, (super.getY()+30) * parent.getPlayingField().height() / parent.getLevelWidth() / 30 + parent.getPlayingField().top, 1,parent,context);
                }

                if (parent.isTile(super.getX() - 30, super.getY(), Wall.class)) {
                    hitParticle p = new hitParticle(super.getX() * parent.getPlayingField().height() / parent.getLevelWidth() / 30 + parent.getPlayingField().left, super.getY() * parent.getPlayingField().height() / parent.getLevelWidth() / 30 + parent.getPlayingField().top, 3,parent,context);
                }
                if (parent.isTile(super.getX() - 30, super.getY()+30, Wall.class)) {
                    hitParticle p = new hitParticle(super.getX() * parent.getPlayingField().height() / parent.getLevelWidth() / 30 + parent.getPlayingField().left, (super.getY()+30) * parent.getPlayingField().height() / parent.getLevelWidth() / 30 + parent.getPlayingField().top, 3,parent,context);
                }
            }
        }
    }

    @Override
    public void pushLeft() {
        oldX=super.getX();
        oldY=super.getY();
        int i=1;
        if(position == 1){
            while(!parent.isSolidTile(super.getX()-i*30,super.getY())){
                i++;
            }
        } else {
            while(!parent.isSolidTile(super.getX()-i*30,super.getY()) && !parent.isSolidTile(super.getX()-i*30,super.getY()+30)){
                i++;
            }
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
        if(position == 1){
            while(!parent.isSolidTile(super.getX()+i*30+30,super.getY())){
                i++;
            }
        } else {
            while(!parent.isSolidTile(super.getX()+i*30,super.getY()) && !parent.isSolidTile(super.getX()+i*30,super.getY()+30)){
                i++;
            }
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
        if(position == 1){
            while(!parent.isSolidTile(super.getX(),super.getY()-i*30) && !parent.isSolidTile(super.getX()+30,super.getY()-i*30)){
                i++;
            }
        } else {
            while(!parent.isSolidTile(super.getX(),super.getY()-i*30)){
                i++;
            }
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
        if(position == 1){
            while(!parent.isSolidTile(super.getX(),super.getY()+i*30) && !parent.isSolidTile(super.getX()+30,super.getY()+i*30)){
                i++;
            }
        } else {
            while(!parent.isSolidTile(super.getX(),super.getY()+i*30+30)){
                i++;
            }
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
