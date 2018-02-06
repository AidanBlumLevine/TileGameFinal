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

    private Button buttonTrash;
    private Button buttonTopBack;
    private Button buttonMiddle;
    private Button buttonEdit;
    private Rect starArea;
    private Bitmap goldCrate,silverCrate,bronzeCrate,emptyCrate;
    private Context context;
    private Game parent;
    private int liveTime=0,oldSwipes=-1;

    public Menu(Rect playingField,int width, int height, Context context, Game parent){
        this.context=context;
        this.parent=parent;

        int topHeight = playingField.top;
        int boxBuffer = 40;
        int boxSize = Math.min((width - boxBuffer*7)/4,topHeight/3);

        buttonTopBack = new Button(width/2-3*boxBuffer/2-boxSize*2, boxBuffer, Bitmap.createScaledBitmap(ImageLoader.getButtonBack(context),boxSize,boxSize,false));
        buttonMiddle = new Button(width/2-boxBuffer/2-boxSize, boxBuffer,Bitmap.createScaledBitmap(ImageLoader.getButtonReset(context),boxSize,boxSize,false));
        buttonTrash = new Button(width/2+boxBuffer/2, boxBuffer,Bitmap.createScaledBitmap(ImageLoader.getButtonTrash(context),boxSize,boxSize,false));
        buttonEdit = new Button(width/2+3*boxBuffer/2+boxSize, boxBuffer,Bitmap.createScaledBitmap(ImageLoader.getButtonEdit(context),boxSize,boxSize,false));

        int starCenterY = (topHeight-boxBuffer-boxSize)/2+boxBuffer+boxSize;
        starArea = new Rect(width/2-4*boxSize/3,starCenterY-boxSize/3,width/2+4*boxSize/3,starCenterY+boxSize/3);

        goldCrate = Bitmap.createScaledBitmap(ImageLoader.getGoldCrate(context),starArea.height(),starArea.height(),false);
        silverCrate = Bitmap.createScaledBitmap(ImageLoader.getSilverCrate(context),starArea.height(),starArea.height(),false);
        bronzeCrate = Bitmap.createScaledBitmap(ImageLoader.getBronzeCrate(context),starArea.height(),starArea.height(),false);
        emptyCrate = Bitmap.createScaledBitmap(ImageLoader.getEmptyStarCrate(context),starArea.height(),starArea.height(),false);

    }

    public void paint(Canvas canvas, Paint paint) {
        buttonMiddle.draw(canvas,paint);
        buttonTopBack.draw(canvas,paint);
        buttonTrash.draw(canvas,paint);
        buttonEdit.draw(canvas,paint);
        update();
        if(parent.getPack().equals("default")) {
            if (parent.getStars() > 2) {
                canvas.drawBitmap(goldCrate, starArea.right - starArea.height(), starArea.top, paint);
            } else if (parent.getSwipes() <= parent.getStarLevels()[0]) {
                paint.setAlpha(120);
                canvas.drawBitmap(goldCrate, starArea.right - starArea.height(), starArea.top, paint);
                paint.reset();
            } else {
                canvas.drawBitmap(emptyCrate, starArea.right - starArea.height(), starArea.top, paint);
            }

            if (parent.getStars() > 1) {
                canvas.drawBitmap(silverCrate, starArea.centerX() - starArea.height() / 2, starArea.top, paint);
            } else if (parent.getSwipes() <= parent.getStarLevels()[1]) {
                paint.setAlpha(120);
                canvas.drawBitmap(silverCrate, starArea.centerX() - starArea.height() / 2, starArea.top, paint);
                paint.reset();
            } else {
                canvas.drawBitmap(emptyCrate, starArea.centerX() - starArea.height() / 2, starArea.top, paint);
            }

            if (parent.getStars() > 0) {
                canvas.drawBitmap(bronzeCrate, starArea.left, starArea.top, paint);
            } else if (parent.getSwipes() <= parent.getStarLevels()[2]) {
                paint.setAlpha(120);
                canvas.drawBitmap(bronzeCrate, starArea.left, starArea.top, paint);
                paint.reset();
            } else {
                canvas.drawBitmap(emptyCrate, starArea.left, starArea.top, paint);
            }
        }
    }

    public void update() {
        int tX=parent.getTouchX();
        int tY=parent.getTouchY();
        buttonMiddle.touch(tX,tY);
        buttonTopBack.touch(tX,tY);
        buttonTrash.touch(tX,tY);
        buttonEdit.touch(tX,tY);
    }


    public boolean live(){
        if(buttonMiddle.getHover() || buttonEdit.getHover() || buttonTopBack.getHover() || buttonTrash.getHover() || oldSwipes != parent.getSwipes()){
            liveTime=5;
        }
        oldSwipes = parent.getSwipes();
        if(liveTime>0){
            liveTime--;
            return true;
        }

        return false;
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
            if (buttonEdit.getHover()){
                Intent i = new Intent(context,LevelEditorScreen.class);
                i.putExtra("level",parent.getLevel().toString());
                context.startActivity(i);
            }
            if (buttonTrash.getHover() && !parent.getPack().equals("default")) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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
                });
            }
        }
    }

    private void deleteLevel(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        Level deleteLevel = parent.getLevel();
        editor.remove(deleteLevel.getName());
        String newNamesList = settings.getString(parent.getPack()+"LevelNames","");
        editor.putString(parent.getPack()+"LevelNames",newNamesList.replace(deleteLevel.getName()+",",""));
        editor.remove(deleteLevel.getName()+parent.getPack());
        editor.commit();
        Intent i = new Intent(context,SelectorScreen.class);
        context.startActivity(i);
        ((AppCompatActivity)context).overridePendingTransition(R.anim.up_to_mid,R.anim.mid_to_down);
    }
}
