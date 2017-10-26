package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class EndMenu {
    private Button menu,replay,next;
    private int stars;
    private Rect starArea,buttonArea;
    private Bitmap goldCrate,silverCrate,bronzeCrate;
    private Context context;
    public EndMenu(int stars, Context context) {
        this.stars = stars;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        starArea = new Rect(width/5,height/2-200,width/5*4,height/2-25);
        buttonArea = new  Rect(width/5,height/2+25,width/5*4,height/2+200);

        menu = new Button(buttonArea.centerX()-buttonArea.height()*5/3,buttonArea.top, Bitmap.createScaledBitmap(ImageLoader.getButtonMenu(context),buttonArea.height(),buttonArea.height(),false));
        replay = new Button(buttonArea.centerX()-buttonArea.height()/2,buttonArea.top, Bitmap.createScaledBitmap(ImageLoader.getButtonReset(context),buttonArea.height(),buttonArea.height(),false));
        next = new Button(buttonArea.centerX()+buttonArea.height()*2/3,buttonArea.top, Bitmap.createScaledBitmap(ImageLoader.getButtonRight(context),buttonArea.height(),buttonArea.height(),false));
        bronzeCrate = Bitmap.createScaledBitmap(ImageLoader.getBronzeCrate(context),starArea.height(),starArea.height(),false);
        silverCrate = Bitmap.createScaledBitmap(ImageLoader.getSilverCrate(context),starArea.height(),starArea.height(),false);
        goldCrate = Bitmap.createScaledBitmap(ImageLoader.getGoldCrate(context),starArea.height(),starArea.height(),false);
        this.context=context;
    }

    public void touch(int x, int y) {
        if(x==-1 && y==-1){
            if(menu.getHover()){
                Intent i = new Intent(context,SelectorScreen.class);
                context.startActivity(i);
            }
            if(replay.getHover()){

            }
            if(next.getHover()){

            }
        }
        menu.touch(x,y);
        replay.touch(x,y);
        next.touch(x,y);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawColor(Color.BLACK);
        menu.draw(canvas,paint);
        replay.draw(canvas,paint);
        next.draw(canvas,paint);
        if (stars > 2) {
            canvas.drawBitmap(goldCrate, starArea.centerX() + starArea.height()*2/3, starArea.top, paint);
        } else {
            paint.setAlpha(100);
            canvas.drawBitmap(goldCrate, starArea.centerX() + starArea.height()*2/3, starArea.top, paint);
            paint.reset();
        }

        if (stars > 1) {
            canvas.drawBitmap(silverCrate, starArea.centerX() - starArea.height() / 2, starArea.top, paint);
        } else {
            paint.setAlpha(100);
            canvas.drawBitmap(silverCrate, starArea.centerX() - starArea.height() / 2, starArea.top, paint);
            paint.reset();
        }

        if (stars > 0) {
            canvas.drawBitmap(bronzeCrate, starArea.centerX() - starArea.height()*5/3, starArea.top, paint);
        } else {
            paint.setAlpha(100);
            canvas.drawBitmap(bronzeCrate, starArea.centerX() - starArea.height()*5/3, starArea.top, paint);
            paint.reset();
        }
    }
}
