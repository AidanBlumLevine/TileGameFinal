package com.example.aidan.tilegameredo.tiles;

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

    private int position;
    //1 is sideways
    //2 is upright

    public DoubleCrate(int xPos, int yPos,int position,Bitmap img) {
        super(xPos, yPos, img);
        oldX = xPos;
        oldY = yPos;
        this.position = position;
        if (position == 1) {
            scaledTexture = Bitmap.createScaledBitmap(super.getTexture(), (int) (Game.getPlayingField().width() / Game.getLevelWidth() * (2+(1-Game.getSizeMultiplier())) * Game.getSizeMultiplier()), (int) (Game.getPlayingField().width() / Game.getLevelWidth() * Game.getSizeMultiplier()), false);
        } else {
            scaledTexture = Bitmap.createScaledBitmap(super.getTexture(), (int) (Game.getPlayingField().width() / Game.getLevelWidth() * Game.getSizeMultiplier()), (int) (Game.getPlayingField().width() / Game.getLevelWidth() * (2+(1-Game.getSizeMultiplier())) * Game.getSizeMultiplier()), false);

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
            scaledTexture = Bitmap.createScaledBitmap(super.getTexture(), (int) (Game.getPlayingField().width() / Game.getLevelWidth() * (2+(1-Game.getSizeMultiplier())) * Game.getSizeMultiplier()), (int) (Game.getPlayingField().width() / Game.getLevelWidth() * Game.getSizeMultiplier()), false);
        } else {
            scaledTexture = Bitmap.createScaledBitmap(super.getTexture(), (int) (Game.getPlayingField().width() / Game.getLevelWidth() * Game.getSizeMultiplier()), (int) (Game.getPlayingField().width() / Game.getLevelWidth() * (2+(1-Game.getSizeMultiplier())) * Game.getSizeMultiplier()), false);

        }
    }

    @Override
    public void paint(Canvas canvas, Paint paint) {
        canvas.drawBitmap(scaledTexture,(int)oldX * Game.getPlayingField().height() / Game.getLevelWidth() / 30 + Game.getPlayingField().left, (int)oldY * Game.getPlayingField().height() / Game.getLevelWidth() / 30 + Game.getPlayingField().top, paint);
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
        if(position==1 && ((Game.isSpike((int)oldX, (int)oldY)) || Game.isSpike((int)oldX+30, (int)oldY))){
            if(!dead){
                dissolveParticle p = new dissolveParticle((int)oldX*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().left, (int)oldY*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().top,this);
                dissolveParticle p2 = new dissolveParticle((int)oldX*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().left, (int)oldY*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().top,this);
            }
            dead=true;
        }
        if(position==2 && ((Game.isSpike((int)oldX, (int)oldY)) || Game.isSpike((int)oldX, (int)oldY+30))){
            if(!dead){
                Log.e("s",(int)oldX+","+ (int)oldY);
                dissolveParticle p = new dissolveParticle((int)oldX*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().left, (int)oldY*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().top,this);
                dissolveParticle p2 = new dissolveParticle((int)oldX*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().left, (int)oldY*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().top,this);
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
        if(Math.abs(oldY-super.getY())<=moveSpeed*1001.0/Game.getFps() && oldY!=super.getY() && inMotion ){
            if(position==2){
                if(Game.isTile(super.getX(), super.getY()+60, Wall.class)){
                    hitParticle p = new hitParticle(super.getX()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().left, (super.getY()+30)*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().top,2);
                }
                if(Game.isTile(super.getX(), super.getY()-30, Wall.class)){
                    hitParticle p = new hitParticle(super.getX()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().left, super.getY()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().top,4);
                }
            }
            if(position==1){
                if(Game.isTile(super.getX(), super.getY()+30, Wall.class)){
                    hitParticle p = new hitParticle(super.getX()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().left, super.getY()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().top,2);
                }
                if(Game.isTile(super.getX()+30, super.getY()+30, Wall.class)){
                    hitParticle p = new hitParticle((super.getX()+30)*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().left, super.getY()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().top,2);
                }

                if(Game.isTile(super.getX(), super.getY()-30, Wall.class)){
                    hitParticle p = new hitParticle(super.getX()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().left, super.getY()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().top,4);
                }
                if(Game.isTile(super.getX()+30, super.getY()-30, Wall.class)){
                    hitParticle p = new hitParticle((super.getX()+30)*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().left, super.getY()*Game.getPlayingField().height()/Game.getLevelWidth()/30+Game.getPlayingField().top,4);
                }
            }
        }
        if(Math.abs(oldX-super.getX())<=moveSpeed*1001.0/Game.getFps() && oldX!=super.getX() && inMotion ){
            if(position==1) {
                if (Game.isTile(super.getX() + 60, super.getY(), Wall.class)) {
                    hitParticle p = new hitParticle((super.getX()+30) * Game.getPlayingField().height() / Game.getLevelWidth() / 30 + Game.getPlayingField().left, super.getY() * Game.getPlayingField().height() / Game.getLevelWidth() / 30 + Game.getPlayingField().top, 1);
                }
                if (Game.isTile(super.getX() - 30, super.getY(), Wall.class)) {
                    hitParticle p = new hitParticle(super.getX() * Game.getPlayingField().height() / Game.getLevelWidth() / 30 + Game.getPlayingField().left, super.getY() * Game.getPlayingField().height() / Game.getLevelWidth() / 30 + Game.getPlayingField().top, 3);
                }
            }
            if(position==2) {
                if (Game.isTile(super.getX() + 30, super.getY(), Wall.class)) {
                    hitParticle p = new hitParticle(super.getX() * Game.getPlayingField().height() / Game.getLevelWidth() / 30 + Game.getPlayingField().left, super.getY() * Game.getPlayingField().height() / Game.getLevelWidth() / 30 + Game.getPlayingField().top, 1);
                }
                if (Game.isTile(super.getX() + 30, super.getY()+30, Wall.class)) {
                    hitParticle p = new hitParticle(super.getX() * Game.getPlayingField().height() / Game.getLevelWidth() / 30 + Game.getPlayingField().left, (super.getY()+30) * Game.getPlayingField().height() / Game.getLevelWidth() / 30 + Game.getPlayingField().top, 1);
                }

                if (Game.isTile(super.getX() - 30, super.getY(), Wall.class)) {
                    hitParticle p = new hitParticle(super.getX() * Game.getPlayingField().height() / Game.getLevelWidth() / 30 + Game.getPlayingField().left, super.getY() * Game.getPlayingField().height() / Game.getLevelWidth() / 30 + Game.getPlayingField().top, 3);
                }
                if (Game.isTile(super.getX() - 30, super.getY()+30, Wall.class)) {
                    hitParticle p = new hitParticle(super.getX() * Game.getPlayingField().height() / Game.getLevelWidth() / 30 + Game.getPlayingField().left, (super.getY()+30) * Game.getPlayingField().height() / Game.getLevelWidth() / 30 + Game.getPlayingField().top, 3);
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
            while(!Game.isSolidTile(super.getX()-i*30,super.getY())){
                i++;
            }
        } else {
            while(!Game.isSolidTile(super.getX()-i*30,super.getY()) && !Game.isSolidTile(super.getX()-i*30,super.getY()+30)){
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
            while(!Game.isSolidTile(super.getX()+i*30+30,super.getY())){
                i++;
            }
        } else {
            while(!Game.isSolidTile(super.getX()+i*30,super.getY()) && !Game.isSolidTile(super.getX()+i*30,super.getY()+30)){
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
            while(!Game.isSolidTile(super.getX(),super.getY()-i*30) && !Game.isSolidTile(super.getX()+30,super.getY()-i*30)){
                i++;
            }
        } else {
            while(!Game.isSolidTile(super.getX(),super.getY()-i*30)){
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
            while(!Game.isSolidTile(super.getX(),super.getY()+i*30) && !Game.isSolidTile(super.getX()+30,super.getY()+i*30)){
                i++;
            }
        } else {
            while(!Game.isSolidTile(super.getX(),super.getY()+i*30+30)){
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
