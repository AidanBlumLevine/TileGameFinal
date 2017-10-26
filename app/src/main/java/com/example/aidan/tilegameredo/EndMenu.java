package com.example.aidan.tilegameredo;

import android.content.Context;
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
    public EndMenu(int stars, Context context) {
        this.stars = stars;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        starArea = new Rect(width/4,height/2-125,width/4*3,height/2-25);
        buttonArea = new  Rect(width/4,height/2+25,width/4*3,height/2+125);
        menu = new Button(buttonArea.centerX()-buttonArea.height()*2,buttonArea.top, Bitmap.createScaledBitmap(ImageLoader.getButtonMenu(context),buttonArea.height(),buttonArea.height(),false));
        replay = new Button(buttonArea.centerX()-buttonArea.height()/2,buttonArea.top, Bitmap.createScaledBitmap(ImageLoader.getButtonReset(context),buttonArea.height(),buttonArea.height(),false));
        next = new Button(buttonArea.centerX()+buttonArea.height(),buttonArea.top, Bitmap.createScaledBitmap(ImageLoader.getButtonRight(context),buttonArea.height(),buttonArea.height(),false));
        bronzeCrate = ImageLoader.getBronzeCrate(context);
        silverCrate = ImageLoader.getSilverCrate(context);
        goldCrate = ImageLoader.getGoldCrate(context);
    }

    public void touch(int x, int y) {
        if(x==-1 && y==-1){
            if(menu.getHover()){

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
            canvas.drawBitmap(goldCrate, starArea.centerX() + starArea.height(), starArea.top, paint);
        } else {
            paint.setAlpha(100);
            canvas.drawBitmap(goldCrate, starArea.centerX() + starArea.height(), starArea.top, paint);
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
            canvas.drawBitmap(bronzeCrate, starArea.centerX() - starArea.height()*2, starArea.top, paint);
        } else {
            paint.setAlpha(100);
            canvas.drawBitmap(bronzeCrate, starArea.centerX() - starArea.height()*2, starArea.top, paint);
            paint.reset();
        }
    }
}
