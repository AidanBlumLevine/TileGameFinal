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
    private String level,pack;
    public EndMenu(int stars, Context context,String level,String pack) {
        this.stars = stars;
        this.level=level;
        this.pack=pack;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        starArea = new Rect(width/5,height/2-200,width/5*4,height/2-25);
        buttonArea = new  Rect(width/5,height/2+25,width/5*4,height/2+200);

        if(pack.equals("default") && Integer.valueOf(new Level(level).getName())+1<=LevelGenerator.numberOfLevels()) {
            menu = new Button(buttonArea.centerX() - buttonArea.height() * 5 / 3, buttonArea.top, Bitmap.createScaledBitmap(ImageLoader.getButtonMenu(context), buttonArea.height(), buttonArea.height(), false));
            replay = new Button(buttonArea.centerX() - buttonArea.height() / 2, buttonArea.top, Bitmap.createScaledBitmap(ImageLoader.getButtonReset(context), buttonArea.height(), buttonArea.height(), false));
            next = new Button(buttonArea.centerX() + buttonArea.height() * 2 / 3, buttonArea.top, Bitmap.createScaledBitmap(ImageLoader.getButtonRight(context), buttonArea.height(), buttonArea.height(), false));
            bronzeCrate = Bitmap.createScaledBitmap(ImageLoader.getBronzeCrate(context),starArea.height(),starArea.height(),false);
            silverCrate = Bitmap.createScaledBitmap(ImageLoader.getSilverCrate(context),starArea.height(),starArea.height(),false);
            goldCrate = Bitmap.createScaledBitmap(ImageLoader.getGoldCrate(context),starArea.height(),starArea.height(),false);
        } else {
            menu = new Button(buttonArea.centerX() - buttonArea.height() * 13 / 12, height/2-buttonArea.height()/2, Bitmap.createScaledBitmap(ImageLoader.getButtonMenu(context), buttonArea.height(), buttonArea.height(), false));
            replay = new Button(buttonArea.centerX() + buttonArea.height() / 12, height/2-buttonArea.height()/2, Bitmap.createScaledBitmap(ImageLoader.getButtonReset(context), buttonArea.height(), buttonArea.height(), false));
        }
        this.context=context;
    }

    public void touch(int x, int y) {
        if(x==-1 && y==-1){
            if(menu.getHover()){
                Intent i = new Intent(context,SelectorScreen.class);
                context.startActivity(i);
            }
            if(replay.getHover()){
                Intent i = new Intent(context,GameScreen.class);
                i.putExtra("level",level);
                i.putExtra("pack",pack);
                context.startActivity(i);
            }
            if(next != null && next.getHover()){
                Intent i = new Intent(context,GameScreen.class);
                Level newLevel = LevelGenerator.getLevel(Integer.valueOf(new Level(level).getName())+1,context);
                i.putExtra("level",newLevel.toString());
                i.putExtra("pack",pack);
                context.startActivity(i);
            }
        }
        menu.touch(x,y);
        replay.touch(x,y);
        if(next!=null){next.touch(x,y);}
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawColor(Color.BLACK);
        menu.draw(canvas,paint);
        replay.draw(canvas,paint);
        if(next!=null){next.draw(canvas,paint);}
        if(goldCrate!=null) {
            if (stars > 2) {
                canvas.drawBitmap(goldCrate, starArea.centerX() + starArea.height() * 2 / 3, starArea.top, paint);
            } else {
                paint.setAlpha(100);
                canvas.drawBitmap(goldCrate, starArea.centerX() + starArea.height() * 2 / 3, starArea.top, paint);
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
                canvas.drawBitmap(bronzeCrate, starArea.centerX() - starArea.height() * 5 / 3, starArea.top, paint);
            } else {
                paint.setAlpha(100);
                canvas.drawBitmap(bronzeCrate, starArea.centerX() - starArea.height() * 5 / 3, starArea.top, paint);
                paint.reset();
            }
        }
    }
}
