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
import android.util.Log;

import com.example.aidan.tilegameredo.particles.fadeParticle;

import static android.content.DialogInterface.BUTTON_POSITIVE;


public class Menu {

    private Button buttonTrash;
    private Button buttonTopBack;
    private Button buttonMiddle;
    private Rect starArea;
    private int boxSize;
    private Bitmap goldCrate,silverCrate,bronzeCrate,emptyCrate;
    private Context context;

    public Menu(Rect playingField,int width, int height, Context context){
        this.context=context;

        int bottomSpaceHeight = height - playingField.bottom;
        int topBottomBuffer = (height - playingField.bottom) / 8;
        int leftRightBuffer = width / 18;
        boxSize = bottomSpaceHeight - topBottomBuffer * 3;

        buttonMiddle = new Button((width - boxSize) / 2, playingField.bottom + topBottomBuffer, Bitmap.createScaledBitmap(ImageLoader.getButtonReset(context),boxSize,boxSize,false));
        buttonTopBack = new Button(leftRightBuffer, topBottomBuffer/4,Bitmap.createScaledBitmap(ImageLoader.getButtonBack(context),boxSize,boxSize,false));
        buttonTrash = new Button(width-leftRightBuffer-boxSize, topBottomBuffer/4,Bitmap.createScaledBitmap(ImageLoader.getButtonTrash(context),boxSize,boxSize,false));

        starArea = new Rect(width/2-boxSize,topBottomBuffer*2,width/2+boxSize,topBottomBuffer*2 + boxSize/2);

        goldCrate = Bitmap.createScaledBitmap(ImageLoader.getGoldCrate(context),starArea.height(),starArea.height(),false);
        silverCrate = Bitmap.createScaledBitmap(ImageLoader.getSilverCrate(context),starArea.height(),starArea.height(),false);
        bronzeCrate = Bitmap.createScaledBitmap(ImageLoader.getBronzeCrate(context),starArea.height(),starArea.height(),false);
        emptyCrate = Bitmap.createScaledBitmap(ImageLoader.getEmptyStarCrate(context),starArea.height(),starArea.height(),false);

    }

    public void paint(Canvas canvas, Paint paint) {
        buttonMiddle.draw(canvas,paint);
        buttonTopBack.draw(canvas,paint);
        buttonTrash.draw(canvas,paint);

        update();

        if(Game.getStars()>2){
            canvas.drawBitmap(goldCrate,starArea.right-starArea.height(),starArea.top,paint);
        } else if(Game.getSwipes()<=Game.getStarLevels()[0]){
            paint.setAlpha(120);
            canvas.drawBitmap(goldCrate,starArea.right-starArea.height(),starArea.top,paint);
            paint.reset();
        } else {
            canvas.drawBitmap(emptyCrate,starArea.right-starArea.height(),starArea.top,paint);
        }

        if(Game.getStars()>1){
            canvas.drawBitmap(silverCrate,starArea.centerX()-starArea.height()/2,starArea.top,paint);
        } else if(Game.getSwipes()<=Game.getStarLevels()[1]){
            paint.setAlpha(120);
            canvas.drawBitmap(silverCrate,starArea.centerX()-starArea.height()/2,starArea.top,paint);
            paint.reset();
        } else {
            canvas.drawBitmap(emptyCrate,starArea.centerX()-starArea.height()/2,starArea.top,paint);
        }

        if(Game.getStars()>0){
            canvas.drawBitmap(bronzeCrate,starArea.left,starArea.top,paint);
        } else if(Game.getSwipes()<=Game.getStarLevels()[2]){
            paint.setAlpha(120);
            canvas.drawBitmap(bronzeCrate,starArea.left,starArea.top,paint);
            paint.reset();
        } else {
            canvas.drawBitmap(emptyCrate,starArea.left,starArea.top,paint);
        }

        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        canvas.drawText(Game.getSwipes()+"",100,300,paint);
    }

    public void update() {
        int tX=Game.getTouchX();
        int tY=Game.getTouchY();
        buttonMiddle.touch(tX,tY);
        buttonTopBack.touch(tX,tY);
        buttonTrash.touch(tX,tY);
    }

    public void released() {
        if(Game.isPlaying()) {
            if (buttonMiddle.getHover()) {
                fadeParticle f = new fadeParticle();
            }
            if (buttonTopBack.getHover()) {
                Intent i = new Intent(context,SelectorScreen.class);
                context.startActivity(i);
                ((AppCompatActivity)context).overridePendingTransition(R.anim.up_to_mid,R.anim.mid_to_down);
            }
            if (buttonTrash.getHover()) {
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

    private void deleteLevel(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        Level deleteLevel = Game.getLevel();
        editor.remove(deleteLevel.getName());
        String newNamesList = settings.getString(LevelSelector.getTab()+"LevelNames","");
        editor.putString(LevelSelector.getTab()+"LevelNames",newNamesList.replace(deleteLevel.getName()+",",""));
        editor.remove(deleteLevel.getName()+LevelSelector.getTab());
        editor.commit();
        Intent i = new Intent(context,SelectorScreen.class);
        context.startActivity(i);
        ((AppCompatActivity)context).overridePendingTransition(R.anim.up_to_mid,R.anim.mid_to_down);
    }
}
