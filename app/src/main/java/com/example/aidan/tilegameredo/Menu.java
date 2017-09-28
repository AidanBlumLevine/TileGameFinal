package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.aidan.tilegameredo.particles.fadeParticle;


public class Menu {
    private float hoverForward = 1;
    private float hoverBack= 1;
    private float hoverMiddle= 1;
    private float hoverTopBack= 1;
    private float hoverTrash= 1;
    private Rect buttonTrash;
    private Rect buttonTopBack;
    private Rect buttonBack;
    private Rect buttonForward;
    private Rect buttonMiddle;
    private Rect starArea;
    private int boxSize,stars;
    private Bitmap arrow;
    private int swipeDisplayValue=0;
    private Context context;

    public Menu(Rect playingField,int width, int height, Context context){
        this.context=context;
        if(Game.firstPlay()){
            int imageHeight = Game.getPlayingField().height()/5;
            arrow  = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.arrow),imageHeight,(int)(imageHeight*.7),false);
        }
        int bottomSpaceHeight = height - playingField.bottom;
        int topBottomBuffer = (height - playingField.bottom) / 8;
        int leftRightBuffer = width / 18;
        boxSize = bottomSpaceHeight - topBottomBuffer * 2;

        buttonMiddle = new Rect((width - boxSize) / 2, playingField.bottom + topBottomBuffer, (width - boxSize) / 2 + boxSize, playingField.bottom + topBottomBuffer + boxSize);
        buttonForward = new Rect(width - leftRightBuffer - boxSize, playingField.bottom + topBottomBuffer, width - leftRightBuffer, playingField.bottom + topBottomBuffer + boxSize);
        buttonBack = new Rect(leftRightBuffer, playingField.bottom + topBottomBuffer, leftRightBuffer + boxSize, playingField.bottom + topBottomBuffer + boxSize);
        buttonTopBack = new Rect(leftRightBuffer, topBottomBuffer, leftRightBuffer + boxSize, topBottomBuffer + boxSize);
        buttonTrash = new Rect(width-leftRightBuffer-boxSize, topBottomBuffer, width-leftRightBuffer, topBottomBuffer + boxSize);
        starArea = new Rect(width/2-boxSize,topBottomBuffer,width/2+boxSize,topBottomBuffer + boxSize/2);
    }

    public void paint(Canvas canvas, Paint paint) {
        if(Game.firstPlay() && Game.getLevelPack().equals("default")){
            swipeDisplayValue+=300/Game.getFps();
            paint.setAlpha((int)(Math.cos(Math.toRadians(swipeDisplayValue))*100)+105);
            canvas.drawBitmap(arrow,Game.getPlayingField().centerX()-Game.getPlayingField().height()/10-90+swipeDisplayValue,Game.getPlayingField().centerY()-arrow.getHeight()/2,paint);
            paint.reset();
            if(swipeDisplayValue>180){
                swipeDisplayValue=0;
            }
        }

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(boxSize/4.66f);
//        paint.setStrokeCap(Paint.Cap.ROUND);
//        paint.setStrokeJoin(Paint.Join.ROUND);
        canvas.save();

        //triangle1
        canvas.scale(hoverForward/1.5f, hoverForward/1.5f, buttonForward.exactCenterX(), buttonForward.exactCenterY());
        paint.setColor(Color.argb(255,40,25,55));
        canvas.drawPath(triangle(buttonForward.centerX(),buttonForward.centerY(),boxSize/2.5f,false),paint);
        canvas.rotate((float)Math.toDegrees(hoverForward*11+1.5f), buttonForward.exactCenterX(), buttonForward.exactCenterY());
        if((Game.getLevel()!=Game.getMaxLevel() && Game.getLevelPack().equals("default")) || ((Game.getLevel()<Game.getNextLevelId()-1) && Game.getLevelPack().equals("custom"))) {
            paint.setColor(Color.argb(200, 255, 204, 0));
        } else {
            paint.setColor(Color.argb(200, 192, 192, 192));
        }
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(buttonForward,paint);
        canvas.restore();
        canvas.save();


        //triangle2
        canvas.scale(hoverBack/1.5f, hoverBack/1.5f, buttonBack.exactCenterX(), buttonBack.exactCenterY());
        paint.setColor(Color.argb(255,40,25,55));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(triangle(buttonBack.centerX(),buttonBack.centerY(),boxSize/2.5f,true),paint);
        canvas.rotate((float)Math.toDegrees(hoverBack*-11-1.5f), buttonBack.exactCenterX(), buttonBack.exactCenterY());
        if(Game.getLevel()>1) {
            paint.setColor(Color.argb(200, 255, 204, 0));
        } else {
            paint.setColor(Color.argb(200, 192, 192, 192));
        }
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(buttonBack,paint);
        canvas.restore();
        canvas.save();

        //home

        canvas.scale(hoverTopBack/1.5f, hoverTopBack/1.5f, buttonTopBack.exactCenterX(), buttonTopBack.exactCenterY());
        paint.setColor(Color.argb(255,40,25,55));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.save();
        canvas.rotate(90,buttonTopBack.centerX(),buttonTopBack.centerY());
        canvas.drawPath(triangle(buttonTopBack.centerX(),buttonTopBack.centerY(),boxSize/2.5f,true),paint);
        canvas.restore();
        paint.setColor(Color.argb(200, 255, 204, 0));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(buttonTopBack,paint);
        canvas.restore();
        canvas.save();

        //trash
        canvas.save();
        canvas.scale(hoverTrash/1.5f, hoverTrash/1.5f, buttonTrash.exactCenterX(), buttonTrash.exactCenterY());
        paint.setColor(Color.argb(255,40,25,55));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(trashPath(buttonTrash.centerX(),buttonTrash.centerY(),boxSize/3.5f),paint);
        if(Game.getLevelPack().equals("custom") && Game.getNextLevelId()>1) {
            paint.setColor(Color.argb(200, 255, 204, 0));
        } else {
            paint.setColor(Color.argb(200, 192, 192, 192));
        }
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(buttonTrash,paint);
        canvas.restore();


        //reset
        canvas.scale(hoverMiddle/1.5f, hoverMiddle/1.5f, buttonMiddle.exactCenterX(), buttonMiddle.exactCenterY());
        paint.setColor(Color.argb(255,40,25,55));
        paint.setStrokeWidth(boxSize/6.4f);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(resetPath(buttonMiddle.centerX(),buttonMiddle.centerY(),boxSize/3.5f),paint);
        paint.setStrokeWidth(boxSize/8f);
        canvas.drawPath(triangle(buttonMiddle.centerX(),buttonMiddle.centerY()-boxSize/3.5f,boxSize/5,true),paint);
        paint.setColor(Color.argb(200,255,204,0));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(buttonMiddle,paint);
        canvas.restore();
        update();

        if(stars>2){
            canvas.drawRect(starArea.left,starArea.centerY()-starArea.height()/2,starArea.left+starArea.height(),starArea.centerY()+starArea.height()/2,paint);
        }
        if(stars>1){
            canvas.drawRect(starArea.centerX()-starArea.height()/2,starArea.centerY()-starArea.height()/2,starArea.centerX()+starArea.height()/2,starArea.centerY()+starArea.height()/2,paint);
        }
        if(stars>0){
            canvas.drawRect(starArea.right-starArea.height(),starArea.centerY()-starArea.height()/2,starArea.right-starArea.height(),starArea.centerY()+starArea.height()/2,paint);

        }
    }

    public void update() {
        if(buttonForward.contains(Game.getTouchX(), Game.getTouchY())){
            if(hoverForward<1.3){
                hoverForward+=(1.3-hoverForward)/100*1000/Game.getFps()+.0001;
            }
        }
        else if(hoverForward>1){
            hoverForward-=.001*1000/Game.getFps();
        }
        if(hoverForward<1){
            hoverForward=1;
        }
        if(hoverForward>1.3){
            hoverForward=1.3f;
        }
        if(buttonBack.contains(Game.getTouchX(), Game.getTouchY())){
            if(hoverBack<1.3){
                hoverBack+=(1.3-hoverBack)/100*1000/Game.getFps()+.0001;
            }
        }
        else if(hoverBack>1){
            hoverBack-=.001*1000/Game.getFps();
        }
        if(hoverBack<1){
            hoverBack=1;
        }
        if(hoverBack>1.3){
            hoverBack=1.3f;
        }
        if(buttonTopBack.contains(Game.getTouchX(), Game.getTouchY())){
            if(hoverTopBack<1.3){
                hoverTopBack+=(1.3-hoverTopBack)/100*1000/Game.getFps()+.0001;
            }
        } else if(hoverTopBack>1){
            hoverTopBack-=.001*1000/Game.getFps();
        }
        if(hoverTopBack<1){
            hoverTopBack=1;
        }
        if(hoverTopBack>1.3){
            hoverTopBack=1.3f;
        }
        if(buttonTrash.contains(Game.getTouchX(), Game.getTouchY())){
            if(hoverTrash<1.3){
                hoverTrash+=(1.3-hoverTrash)/100*1000/Game.getFps()+.0001;
            }
        } else if(hoverTrash>1){
            hoverTrash-=.001*1000/Game.getFps();
        }
        if(hoverTrash<1){
            hoverTrash=1;
        }
        if(hoverTrash>1.3){
            hoverTrash=1.3f;
        }
        if(buttonMiddle.contains(Game.getTouchX(), Game.getTouchY())){
            if(hoverMiddle<1.3){
                hoverMiddle+=(1.3-hoverMiddle)/100*1000/Game.getFps()+.0001;
            }
        }
        else if(hoverMiddle>1){
            hoverMiddle-=.001*1000/Game.getFps();
        }
        if(hoverMiddle<1){
            hoverMiddle=1;
        }
        if(hoverMiddle>1.3){
            hoverMiddle=1.3f;
        }
    }

    public void released() {
        if(Game.isPlaying()) {
            int nextId = Game.getNextLevelId();
            if (buttonMiddle.top<Game.getTouchY() && buttonMiddle.bottom>Game.getTouchY() && buttonMiddle.left<Game.getTouchX() && buttonMiddle.right>Game.getTouchX()) {
                fadeParticle f = new fadeParticle();
            }
            if (buttonBack.contains(Game.getTouchX(), Game.getTouchY()) && Game.getLevel()!=1) {
                Game.levelChange(-1);
                fadeParticle f = new fadeParticle();
            }
            if (buttonForward.contains(Game.getTouchX(), Game.getTouchY()) && !(Game.getLevelPack().equals("default") && Game.getLevel()==Game.getMaxLevel()) && !(Game.getLevelPack().equals("custom") && Game.getLevel()>=nextId-1)) {
                Game.levelChange(1);
                fadeParticle f = new fadeParticle();
            }
            if (buttonTopBack.contains(Game.getTouchX(), Game.getTouchY())) {
                Intent i = new Intent(context,HomeScreen.class);
                context.startActivity(i);
                ((AppCompatActivity)context).overridePendingTransition(R.anim.up_to_mid,R.anim.mid_to_down);
            }
            if (buttonTrash.contains(Game.getTouchX(), Game.getTouchY()) && !Game.getLevelPack().equals("default")  && nextId>1) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                deleteLevel();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        }
    }
    private static void deleteLevel(){
        int nextId = Game.getNextLevelId();
        int lastLevel = nextId-1;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(Game.getContext());
        SharedPreferences.Editor editor = settings.edit();
        for(int i=Game.getLevel();i<lastLevel;i++){
            editor.putString("customlevel"+i,settings.getString("customlevel"+(i+1),""));
        }
        editor.remove("customlevel"+lastLevel);

        editor.commit();
        fadeParticle f = new fadeParticle();
    }

    public static Path triangle(float x, float y, float length, boolean dir){
        Path triangle = new Path();
        if(dir){
            triangle.moveTo(x-length/2f, y);
            triangle.lineTo(x+length/1.8f, y+length/1.8f);
            triangle.lineTo(x+length/1.8f, y-length/1.8f);
            triangle.close();
        } else {
            triangle.moveTo(x+length/2f, y);
            triangle.lineTo(x-length/1.8f, y+length/1.8f);
            triangle.lineTo(x-length/1.8f, y-length/1.8f);
            triangle.close();
        }

        return triangle;
    }

    public Path resetPath(float x, float y,float length){
        Path curve = new Path();
        curve.moveTo(x-length, y-length);
        curve.lineTo(x-length, y+length);
        curve.lineTo(x+length, y+length);
        curve.lineTo(x+length, y-length);
        curve.lineTo(x+length/2, y-length);
        return curve;
    }

    public Path trashPath(float x, float y,float length){
        Path curve = new Path();
        curve.moveTo(x-length, y-length);
        curve.lineTo(x+length, y+length);
        curve.moveTo(x-length, y+length);
        curve.lineTo(x+length, y-length);
        return curve;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
