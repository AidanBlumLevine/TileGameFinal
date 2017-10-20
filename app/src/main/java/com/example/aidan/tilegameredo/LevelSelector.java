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
    private  int scrollPosition,screenHeight,screenWidth,edgeBuffer;
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

        for(int i=0;i<levels.size();i++){
            int yPosition = i*(levelHeight+levelBuffer)+levelBuffer+listArea.top;
            if(yPosition<screenHeight) {
                Rect thisLevel = new Rect(listArea.left + 20, yPosition, listArea.right - 20, yPosition + levelHeight);
                if(selectedLevel==levels.get(i)) {
                    paint.setColor(Color.GREEN);
                } else {
                    paint.setColor(Color.GRAY);
                }
                canvas.drawRect(thisLevel,paint);
                paint.setColor(Color.BLACK);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(50);
                int xPos = (thisLevel.left+thisLevel.width() / 2);
                int yPos = (int) (thisLevel.top+((thisLevel.height() / 2) - ((paint.descent() + paint.ascent()) / 2))) ;
                canvas.drawText(levels.get(i).getName(), xPos, yPos, paint);
            }
        }

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
                i.putExtra("level",selectedLevel.getName()+tab);
                i.putExtra("pack",tab);
                context.startActivity(i);
                ((AppCompatActivity)context).overridePendingTransition(R.anim.up_to_mid,R.anim.mid_to_down);
            }
        }
        backButton.touch(x,y);
        playButton.touch(x,y);
        tabCustom.touch(x,y);
        tabDownloaded.touch(x,y);
        tabDefault.touch(x,y);

        if(type==1){
            if(listArea.contains(x,y)){
                for(int i=0;i<levels.size();i++){
                    int yPosition = i*(levelHeight+levelBuffer)+levelBuffer+listArea.top;
                    if(yPosition<screenHeight) {
                        Rect thisLevel = new Rect(listArea.left + 20, yPosition, listArea.right - 20, yPosition + levelHeight);
                        if (thisLevel.contains(x, y)) {
                            selectedLevel = levels.get(i);
                        }
                    }
                }
            }
        }
    }
}
