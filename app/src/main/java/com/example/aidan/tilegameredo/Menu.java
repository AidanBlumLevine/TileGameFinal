package com.example.aidan.tilegameredo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.aidan.tilegameredo.levelEditor.LevelEditorScreen;
import com.example.aidan.tilegameredo.particles.fadeParticle;


public class Menu {

    private Button buttonTopBack;
    private Button buttonMiddle;
    private Rect starArea;
    private Bitmap starFull,starEmpty;
    private Context context;
    private Game parent;
    private int liveTime=0,oldSwipes=-1;

    public Menu(Rect playingField,int width, int height, Context context, Game parent){
        this.context=context;
        this.parent=parent;

        int topHeight = playingField.top;
        int boxBuffer = 40;
        int boxSize = Math.min((width - boxBuffer*7)/4,topHeight/3);

        buttonTopBack = new Button(width/2-3*boxBuffer/2-boxSize*2, boxBuffer, Bitmap.createScaledBitmap(Loader.getButtonBack(context),boxSize,boxSize,false));
        buttonMiddle = new Button(width/2-boxBuffer/2-boxSize, boxBuffer,Bitmap.createScaledBitmap(Loader.getButtonReset(context),boxSize,boxSize,false));

        int starCenterY = (topHeight-boxBuffer-boxSize)/2+boxBuffer+boxSize;
        starArea = new Rect(width/2-4*boxSize/3,starCenterY-boxSize/3,width/2+4*boxSize/3,starCenterY+boxSize/3);

        starEmpty = Bitmap.createScaledBitmap(Loader.getStarBlueBorder(context),starArea.height(),starArea.height(),false);
        starFull = Bitmap.createScaledBitmap(Loader.getStarBorder(context),starArea.height(),starArea.height(),false);

    }

    public void paint(Canvas canvas, Paint paint) {
        buttonMiddle.draw(canvas,paint);
        buttonTopBack.draw(canvas,paint);
        update();
        if(parent.getPack().equals("default")) {
            if (parent.getStars() > 2) {
                canvas.drawBitmap(starFull, starArea.right - starArea.height(), starArea.top, paint);
            } else if (parent.getSwipes() <= parent.getStarLevels()[0]) {
                canvas.drawBitmap(starEmpty, starArea.right - starArea.height(), starArea.top, paint);
            } else {
                //canvas.drawBitmap(emptyCrate, starArea.right - starArea.height(), starArea.top, paint);
            }

            if (parent.getStars() > 1) {
                canvas.drawBitmap(starFull, starArea.centerX() - starArea.height() / 2, starArea.top, paint);
            } else if (parent.getSwipes() <= parent.getStarLevels()[1]) {
                canvas.drawBitmap(starEmpty, starArea.centerX() - starArea.height() / 2, starArea.top, paint);
            } else {
                //canvas.drawBitmap(emptyCrate, starArea.centerX() - starArea.height() / 2, starArea.top, paint);
            }

            if (parent.getStars() > 0) {
                canvas.drawBitmap(starFull, starArea.left, starArea.top, paint);
            } else if (parent.getSwipes() <= parent.getStarLevels()[2]) {
                canvas.drawBitmap(starEmpty, starArea.left, starArea.top, paint);
            } else {
                //canvas.drawBitmap(emptyCrate, starArea.left, starArea.top, paint);
            }
        }
    }

    public void update() {
        int tX=parent.getTouchX();
        int tY=parent.getTouchY();
        buttonMiddle.touch(tX,tY);
        buttonTopBack.touch(tX,tY);

    }

    public void released() {
        if(parent.isPlaying()) {
            if (buttonMiddle.getHover()) {
                fadeParticle f = new fadeParticle(parent);
            }
            if (buttonTopBack.getHover()) {
                Intent i = new Intent(context,SelectorScreen.class);
                context.startActivity(i);
                ((AppCompatActivity)context).overridePendingTransition(R.anim.up_to_mid,R.anim.mid_to_down);
            }

        }
    }

}
