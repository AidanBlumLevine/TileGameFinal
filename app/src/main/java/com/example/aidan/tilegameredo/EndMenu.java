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
    private Bitmap starFull,starEmpty;
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
            menu = new Button(buttonArea.centerX() - buttonArea.height() * 5 / 3, buttonArea.top, Bitmap.createScaledBitmap(Loader.getButtonBack(context), buttonArea.height(), buttonArea.height(), false));
            replay = new Button(buttonArea.centerX() - buttonArea.height() / 2, buttonArea.top, Bitmap.createScaledBitmap(Loader.getButtonReset(context), buttonArea.height(), buttonArea.height(), false));
            next = new Button(buttonArea.centerX() + buttonArea.height() * 2 / 3, buttonArea.top, Bitmap.createScaledBitmap(Loader.getButtonNext(context), buttonArea.height(), buttonArea.height(), false));
        } else {
            menu = new Button(buttonArea.centerX() - buttonArea.height() * 13 / 12, buttonArea.top, Bitmap.createScaledBitmap(Loader.getButtonBack(context), buttonArea.height(), buttonArea.height(), false));
            replay = new Button(buttonArea.centerX() + buttonArea.height() / 12, buttonArea.top, Bitmap.createScaledBitmap(Loader.getButtonReset(context), buttonArea.height(), buttonArea.height(), false));
        }
        this.context=context;
        starEmpty = Bitmap.createScaledBitmap(Loader.getStarBlueBorder(context),starArea.height(),starArea.height(),false);
        starFull = Bitmap.createScaledBitmap(Loader.getStarBorder(context),starArea.height(),starArea.height(),false);
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
        Loader.drawBackground(canvas,paint);
        menu.draw(canvas,paint);
        replay.draw(canvas,paint);
        if(next!=null){
            next.draw(canvas,paint);
        }
        if(pack.equals("default")) {
            if (stars > 2) {
                canvas.drawBitmap(starFull, starArea.centerX() + starArea.height() * 2 / 3, starArea.top, paint);
            } else {
                canvas.drawBitmap(starEmpty, starArea.centerX() + starArea.height() * 2 / 3, starArea.top, paint);
            }

            if (stars > 1) {
                canvas.drawBitmap(starFull, starArea.centerX() - starArea.height() / 2, starArea.top, paint);
            } else {
                canvas.drawBitmap(starEmpty, starArea.centerX() - starArea.height() / 2, starArea.top, paint);
            }

            if (stars > 0) {
                canvas.drawBitmap(starFull, starArea.centerX() - starArea.height() * 5 / 3, starArea.top, paint);
            } else {
                canvas.drawBitmap(starEmpty, starArea.centerX() - starArea.height() * 5 / 3, starArea.top, paint);
            }
        }
    }
}
