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
    private static String tab = "custom";
    //defualt custom downloaded
    private static ArrayList<Level> levels;
    private static Level selectedLevel;
    private static int scrollPosition,screenHeight,screenWidth;
    private static Rect listArea;
    private static Button backButton,playButton;
    private static Context context;

    private static final int levelHeight = 100;
    private static final int levelBuffer = 20;

    public static void load(Context context) {
        selectedLevel=null;
        LevelSelector.context = context;
        levels = LevelGenerator.getAllLevels(tab,context);

        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        int sidebarWidth = screenWidth/10;
        int tabHeight = screenHeight /25;
        listArea = new Rect(sidebarWidth,tabHeight,screenWidth,screenHeight);

        int boxSize = screenWidth/12;
        backButton = new Button((sidebarWidth-boxSize)/2,(sidebarWidth-boxSize)/2,Bitmap.createScaledBitmap(ImageLoader.getButtonBack(context),boxSize,boxSize,false));
        playButton = new Button((sidebarWidth-boxSize)/2,(sidebarWidth-boxSize)+boxSize,Bitmap.createScaledBitmap(ImageLoader.getButtonPlay(context),boxSize,boxSize,false));
    }

    public static void draw(Canvas canvas, Paint paint) {
        canvas.drawColor(Color.WHITE);
        paint.setAlpha(80);
        canvas.drawBitmap(ImageLoader.getBackground(context),-30,-50,paint);

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
            }
        }

        backButton.draw(canvas,paint);
        playButton.draw(canvas,paint);
    }

    public static void touch(int x, int y,int type) {
        if(type==-1){
            if(backButton.getHover()){
                Intent i = new Intent(context,HomeScreen.class);
                context.startActivity(i);
                ((AppCompatActivity)context).overridePendingTransition(R.anim.up_to_mid,R.anim.mid_to_down);
            }
            if(playButton.getHover() && selectedLevel!=null){
                Intent i = new Intent(context,GameScreen.class);
                context.startActivity(i);
                ((AppCompatActivity)context).overridePendingTransition(R.anim.up_to_mid,R.anim.mid_to_down);
            }
        }
        backButton.touch(x,y);
        playButton.touch(x,y);
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

    public static void playAgain() {
        Game.load(selectedLevel);
    }

    public static String getTab() {
        return tab;
    }

    public static Level getLevel() {
        return selectedLevel;
    }

}
