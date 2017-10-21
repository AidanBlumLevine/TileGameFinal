package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class LevelSelector {
    private  String tab = "custom";
    //defualt custom downloaded
    private  ArrayList<Level> levels;
    private  Level selectedLevel;
    private  int scrollPosition=0,screenHeight,screenWidth,edgeBuffer,touchStartY;
    private  Rect listArea;
    private  Button backButton,playButton,tabDefault,tabDownloaded,tabCustom;
    private  Context context;

    private  final int levelHeight = 100;
    private  final int levelBuffer = 20;

    public LevelSelector(Context context) {
        selectedLevel=null;
        this.context = context;
        levels = LevelGenerator.getAllLevels(tab,context);

        edgeBuffer = 20;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        int tabHeight = screenHeight /12;
        listArea = new Rect(edgeBuffer,tabHeight+(int)(edgeBuffer*1.5),screenWidth-edgeBuffer,screenHeight-edgeBuffer);

        int boxSize = tabHeight-10;
        backButton = new Button(edgeBuffer,edgeBuffer,Bitmap.createScaledBitmap(ImageLoader.getButtonBack(context),boxSize,boxSize,false));
        playButton = new Button(boxSize+edgeBuffer*2,edgeBuffer,Bitmap.createScaledBitmap(ImageLoader.getButtonPlay(context),boxSize,boxSize,false));
        tabDefault = new Button(screenWidth-boxSize*15/3-edgeBuffer*3,edgeBuffer,Bitmap.createScaledBitmap(ImageLoader.getButtonWideBlank(context),boxSize*5/3,boxSize,false));
        tabCustom = new Button(screenWidth-boxSize*10/3-edgeBuffer*2,edgeBuffer,Bitmap.createScaledBitmap(ImageLoader.getButtonWideBlank(context),boxSize*5/3,boxSize,false));
        tabDownloaded = new Button(screenWidth-boxSize*5/3-edgeBuffer,edgeBuffer,Bitmap.createScaledBitmap(ImageLoader.getButtonWideBlank(context),boxSize*5/3,boxSize,false));
    }

    public  void draw(Canvas canvas, Paint paint) {
        canvas.drawColor(Color.WHITE);
        paint.setAlpha(80);
        canvas.drawBitmap(ImageLoader.getBackground(context),-30,-50,paint);

        paint.setARGB(50,0,0,0);
        canvas.drawRect(edgeBuffer,edgeBuffer*2+(screenHeight/12-10),screenWidth-edgeBuffer,screenHeight-edgeBuffer,paint);
        paint.setARGB(100,0,0,0);
        canvas.drawRect(edgeBuffer/2,edgeBuffer/2,screenWidth-edgeBuffer/2,edgeBuffer*2+(screenHeight/12-10)-edgeBuffer/2,paint);
        paint.reset();

        canvas.save();
        canvas.clipRect(listArea.left,listArea.top+edgeBuffer/2,listArea.right,listArea.bottom-edgeBuffer/2);
        for(int i=0;i<levels.size();i++){
            int yPosition = i*(levelHeight+levelBuffer)+levelBuffer+listArea.top-scrollPosition;
            if(yPosition<screenHeight) {
                Rect thisLevel = new Rect(listArea.left + 20, yPosition, listArea.right - 20, yPosition + levelHeight);
                if(selectedLevel==levels.get(i)) {
                    paint.setColor(Color.LTGRAY);
                    canvas.drawRect(thisLevel.left,thisLevel.top+thisLevel.height()/10,thisLevel.right,thisLevel.bottom-thisLevel.height()/10,paint);
                    canvas.drawRect(thisLevel.left+thisLevel.height()/10,thisLevel.top,thisLevel.right-thisLevel.height()/10,thisLevel.bottom,paint);
                } else {
                    paint.setColor(Color.WHITE);
                    canvas.drawRect(thisLevel.left,thisLevel.top+thisLevel.height()/10,thisLevel.right,thisLevel.bottom-thisLevel.height()/10,paint);
                    canvas.drawRect(thisLevel.left+thisLevel.height()/10,thisLevel.top,thisLevel.right-thisLevel.height()/10,thisLevel.bottom,paint);

                }
                paint.setColor(Color.BLACK);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(50);
                int xPos = (thisLevel.left+thisLevel.width() / 2);
                int yPos = (int) (thisLevel.top+((thisLevel.height() / 2) - ((paint.descent() + paint.ascent()) / 2))) ;
                canvas.drawText(levels.get(i).getName(), xPos, yPos, paint);
            }
        }
        canvas.restore();

        backButton.draw(canvas,paint);
        playButton.draw(canvas,paint);
        tabCustom.draw(canvas,paint);
        tabDefault.draw(canvas,paint);
        tabDownloaded.draw(canvas,paint);

    }

    public  void touch(int x, int y,int type) {
        if(type==-1){
            if(backButton.getHover()){
                Intent i = new Intent(context,HomeScreen.class);
                context.startActivity(i);
                ((AppCompatActivity)context).overridePendingTransition(R.anim.up_to_mid,R.anim.mid_to_down);
            }
            if(playButton.getHover() && selectedLevel!=null){
                Intent i = new Intent(context,GameScreen.class);
                i.putExtra("level",selectedLevel.toString());
                i.putExtra("pack",tab);
                context.startActivity(i);
                ((AppCompatActivity)context).overridePendingTransition(R.anim.up_to_mid,R.anim.mid_to_down);
            }
            if(tabDefault.getHover() && !tab.equals("default")){
                tab="default";
                levels = LevelGenerator.getAllLevels(tab,context);
                selectedLevel=null;
                scrollPosition=0;
            }
            if(tabCustom.getHover() && !tab.equals("custom")){
                tab="custom";
                levels = LevelGenerator.getAllLevels(tab,context);
                selectedLevel=null;
                scrollPosition=0;
            }
            if(tabDownloaded.getHover() && !tab.equals("downloaded")){
                tab="downloaded";
                levels = LevelGenerator.getAllLevels(tab,context);
                selectedLevel=null;
                scrollPosition=0;
            }
        }
        backButton.touch(x,y);
        playButton.touch(x,y);
        tabCustom.touch(x,y);
        tabDownloaded.touch(x,y);
        tabDefault.touch(x,y);
        if(type==0) {
            scrollPosition += touchStartY - y;
            touchStartY = y;
            scrollPosition = Math.max(0, scrollPosition);
            int height = listArea.height()-edgeBuffer;
            int levelsHeight = levels.size()*(levelHeight+levelBuffer);
            scrollPosition = Math.min(scrollPosition,Math.abs(levelsHeight-height));
        }
        if(type==1){
            if(listArea.contains(x,y)){
                touchStartY=y;
                for(int i=0;i<levels.size();i++){
                    int yPosition = i*(levelHeight+levelBuffer)+levelBuffer+listArea.top;
                    Rect thisLevel = new Rect(listArea.left + 20, yPosition, listArea.right - 20, yPosition + levelHeight);
                    if (thisLevel.contains(x, y+scrollPosition)) {
                        selectedLevel = levels.get(i);
                    }
                }
            }
        }
    }
}
