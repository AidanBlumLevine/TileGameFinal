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

    private Button buttonTrash;
    private Button buttonTopBack;
    private Button buttonBack;
    private Button buttonForward;
    private Button buttonMiddle;
    private Rect starArea;
    private int boxSize;
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

        buttonMiddle = new Button((width - boxSize) / 2, playingField.bottom + topBottomBuffer, Bitmap.createScaledBitmap(ImageLoader.getButtonReset(context),boxSize,boxSize,false));
        buttonForward = new Button(width - leftRightBuffer - boxSize, playingField.bottom + topBottomBuffer,Bitmap.createScaledBitmap(ImageLoader.getButtonRight(context),boxSize,boxSize,false));
        buttonBack = new Button(leftRightBuffer, playingField.bottom + topBottomBuffer,Bitmap.createScaledBitmap(ImageLoader.getButtonLeft(context),boxSize,boxSize,false));
        buttonTopBack = new Button(leftRightBuffer, topBottomBuffer/4,Bitmap.createScaledBitmap(ImageLoader.getButtonBack(context),boxSize,boxSize,false));
        buttonTrash = new Button(width-leftRightBuffer-boxSize, topBottomBuffer/4,Bitmap.createScaledBitmap(ImageLoader.getButtonTrash(context),boxSize,boxSize,false));

        starArea = new Rect(width/2-boxSize,topBottomBuffer*2,width/2+boxSize,topBottomBuffer*2 + boxSize/2);
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
        buttonBack.draw(canvas,paint);
        buttonForward.draw(canvas,paint);
        buttonMiddle.draw(canvas,paint);
        buttonTopBack.draw(canvas,paint);
        buttonTrash.draw(canvas,paint);

        update();

        if(Game.getStars()>2){
            canvas.drawBitmap(ImageLoader.getGoldCrate(context),starArea.centerX()-starArea.height()/2,starArea.top,paint);
        } else if(Game.getSwipes()<=Game.getStarLevels()[0]){
            paint.setAlpha(100);
            canvas.drawBitmap(ImageLoader.getGoldCrate(context),starArea.centerX()-starArea.height()/2,starArea.top,paint);
        } else {
            canvas.drawBitmap(ImageLoader.getEmptyStarCrate(context),starArea.centerX()-starArea.height()/2,starArea.top,paint);
        }
        if(Game.getStars()>1){
            canvas.drawBitmap(ImageLoader.getSilverCrate(context),starArea.centerX()-starArea.height()/2,starArea.top,paint);
        }
        if(Game.getStars()>0){
            canvas.drawBitmap(ImageLoader.getBronzeCrate(context),starArea.right-starArea.height(),starArea.top,paint);
        }

        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        canvas.drawText(Game.getSwipes()+"",100,300,paint);
    }

    public void update() {
        int tX=Game.getTouchX();
        int tY=Game.getTouchY();
        buttonBack.touch(tX,tY);
        buttonForward.touch(tX,tY);
        buttonMiddle.touch(tX,tY);
        buttonTopBack.touch(tX,tY);
        buttonTrash.touch(tX,tY);
    }

    public void released() {
        if(Game.isPlaying()) {
            int nextId = Game.getNextLevelId();
            if (buttonMiddle.getHover()) {
                fadeParticle f = new fadeParticle();
            }
            if (buttonBack.hover && Game.getLevel()!=1) {
                Game.levelChange(-1);
                fadeParticle f = new fadeParticle();
            }
            if (buttonForward.hover && !(Game.getLevelPack().equals("default") && Game.getLevel()==Game.getMaxLevel()) && !(Game.getLevelPack().equals("custom") && Game.getLevel()>=nextId-1)) {
                Game.levelChange(1);
                fadeParticle f = new fadeParticle();
            }
            if (buttonTopBack.hover) {
                Intent i = new Intent(context,HomeScreen.class);
                context.startActivity(i);
                ((AppCompatActivity)context).overridePendingTransition(R.anim.up_to_mid,R.anim.mid_to_down);
            }
            if (buttonTrash.hover && !Game.getLevelPack().equals("default")  && nextId>1) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                deleteLevel();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete this level?").setPositiveButton("Yes", dialogClickListener)
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
}
