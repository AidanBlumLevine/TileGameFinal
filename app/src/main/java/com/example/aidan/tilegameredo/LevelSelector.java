package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import java.util.ArrayList;

public class LevelSelector {
    private  String tab = "default";
    //default custom
    private  ArrayList<Level> levels;
    private ArrayList<Bitmap> previews = new ArrayList<>();
    private  Level selectedLevel;
    private  int scrollPosition=0,screenHeight,screenWidth,edgeBuffer,touchStartY,maxLevel,oldX,oldY,startX,startY;
    private  Rect listArea;
    private  Button backButton,tabDefault,tabCustom;
    private  Context context;
    private SelectorMenu popup = null;

    private int levelHeight = 250;
    private  final int levelBuffer = 20;

    public LevelSelector(Context context) {
        selectedLevel=null;
        this.context = context;
        levels = new ArrayList<>();
        levels = Loader.getDefaultLevels();

        edgeBuffer = 20;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        int tabHeight = screenHeight / 7;
        listArea = new Rect(edgeBuffer,tabHeight+(int)(edgeBuffer*1.5),screenWidth-edgeBuffer,screenHeight-edgeBuffer);

        backButton = new Button(edgeBuffer*3,tabHeight/2+edgeBuffer-tabHeight/3,Bitmap.createScaledBitmap(Loader.getButtonBack(context),2*tabHeight/3,2*tabHeight/3,false));
        tabDefault = new Button(5*tabHeight/6+edgeBuffer*6,edgeBuffer+tabHeight/9,7*tabHeight/4,tabHeight/3,Color.rgb(255,100,72),"DEFAULT LEVELS",48,Loader.getFont(context));
        tabCustom = new Button(5*tabHeight/6+edgeBuffer*6,edgeBuffer+5*tabHeight/9,7*tabHeight/4,tabHeight/3,Color.rgb(65,99,135),"CUSTOM LEVELS",48,Loader.getFont(context));

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        maxLevel = settings.getInt("maxLevel",1);

        previews = Loader.getDefaultPreviews();
        levelHeight = 6*previews.get(0).getHeight()/4;
    }

    public  void draw(Canvas canvas, Paint paint) {
        Loader.drawBackground(canvas, paint);

        //paint.setARGB(50,0,0,0);
        //canvas.drawRect(edgeBuffer,edgeBuffer*2+(screenHeight/12-10),screenWidth-edgeBuffer,screenHeight-edgeBuffer,paint);
        //paint.setARGB(100,0,0,0);
        //canvas.drawRect(edgeBuffer/2,edgeBuffer/2,screenWidth-edgeBuffer/2,edgeBuffer*2+(screenHeight/12-10)-edgeBuffer/2,paint);
        paint.setColor(Color.rgb(230,230,230));
        canvas.drawRect(listArea.left,listArea.top+edgeBuffer/2+3,listArea.right,listArea.top+edgeBuffer/2+3,paint);
        canvas.save();
        canvas.clipRect(listArea.left,listArea.top+edgeBuffer/2,listArea.right,listArea.bottom-edgeBuffer/2);
        for(int i=0;i<levels.size();i++){
            int yPosition = (i-i%3)/3*(levelHeight+levelBuffer)+levelBuffer+listArea.top-scrollPosition;
            if(yPosition<screenHeight) {
                Rect thisLevel;
                paint.setAntiAlias(true);

                int levelWidth = (listArea.width()-4*edgeBuffer)/3;
                if(i%3==0){
                    thisLevel = new Rect(listArea.left + edgeBuffer, yPosition, listArea.left + levelWidth+edgeBuffer, yPosition + levelHeight);
                } else if(i%3==1){
                    thisLevel = new Rect(listArea.left + levelWidth+2*edgeBuffer, yPosition, listArea.left + 2*levelWidth+2*edgeBuffer, yPosition + levelHeight);
                } else {
                    thisLevel = new Rect(listArea.left + 2*levelWidth+3*edgeBuffer, yPosition, listArea.right-edgeBuffer, yPosition + levelHeight);
                }
                if(levels.get(i).getStars()==3){
                    paint.setColor(Color.rgb(255,182,72));
                } else {
                    paint.setColor(Color.rgb(65,99,135));
                }
                paint.setMaskFilter(new BlurMaskFilter(1, BlurMaskFilter.Blur.NORMAL));
                canvas.drawRect(thisLevel,paint);

                if(levels.get(i).getStars()==3) {
                    paint.setColor(Color.rgb(75, 75, 75));
                } else {
                    paint.setColor(Color.rgb(50, 74, 99));
                }
                int iWidth = previews.get(0).getWidth();
                canvas.drawRect(thisLevel.centerX()-iWidth/2-5,thisLevel.top + iWidth/8-5,thisLevel.centerX()+iWidth/2+5,thisLevel.top + 9*iWidth/8+5,paint);
                paint.reset();
                paint.setAntiAlias(true);

                if(Loader.getFont(context)!=null)
                    paint.setTypeface(Loader.getFont(context));

                String levelName = levels.get(i).getName();
                if(tab.equals("custom")){
                    levelName = levelName.substring(0,levelName.length()-6);
                }
                Rect nameBounds = new Rect();
                paint.getTextBounds(levelName,0,levelName.length(),nameBounds);
                if(nameBounds.width()+100>thisLevel.width()){
                    while(nameBounds.width()+100>thisLevel.width()){
                        levelName = levelName.substring(0,levelName.length()-1);
                        paint.getTextBounds(levelName+"...",0,levelName.length()+1,nameBounds);
                    }
                    levelName = levelName+"...";
                }

                paint.reset();
                paint.setAntiAlias(true);
                canvas.drawBitmap(previews.get(i), thisLevel.centerX() - previews.get(i).getWidth()/2, thisLevel.top + previews.get(i).getHeight()/8, paint);

                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTextSize(48);
                //paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                int xPos = (thisLevel.left+thisLevel.width()/8);
                int yPos = (int) (thisLevel.top+9*iWidth/8+(thisLevel.height()-9*iWidth/8)/2 - ((paint.descent() + paint.ascent()) / 2) + 2) ;
                // THe +2 is from the thin border

                if(levels.get(i).getStars()==3) {
                    paint.setColor(Color.rgb(75, 75, 75));
                } else {
                    paint.setColor(Color.WHITE);
                }
                canvas.drawText(levelName, xPos, yPos, paint);

                if(tab.equals("default")){
                    Rect starArea = new Rect(thisLevel.left+thisLevel.width()/3,thisLevel.top+9*iWidth/8,thisLevel.right,thisLevel.bottom);
                    for(int s=0;s<3;s++){
                        int x = (s+1)*(int)(starArea.width()/4.5)+ starArea.left;
                        if(levels.get(i).getStars()==3) {
                            paint.setColor(Color.rgb(75,75,75));
                        } else if(levels.get(i).getStars()>s) {
                            paint.setColor(Color.rgb(255,182,72));
                        } else {
                            paint.setColor(Color.rgb(50,74,99));
                        }
                        canvas.drawCircle(x, starArea.centerY(), starArea.height()/5, paint);
                    }
                }
                paint.setAntiAlias(false);

                if(tab.equals("default") && Integer.valueOf(levels.get(i).getName())>maxLevel){
                    paint.setColor(Color.argb(200,0,0,0));
                    canvas.drawRect(thisLevel,paint);
                    canvas.drawBitmap(Loader.getLock(context),thisLevel.centerX()-25,thisLevel.top+thisLevel.width()/2-30,paint);
                }
            }
        }
        paint.setColor(Color.rgb(230,230,230));
        canvas.drawRect(listArea.left,listArea.top+edgeBuffer/2,listArea.right,listArea.top+edgeBuffer/2+3,paint);
        canvas.restore();
        paint.reset();

        backButton.draw(canvas,paint);
        tabCustom.draw(canvas,paint);
        tabDefault.draw(canvas,paint);

        if(popup!=null){
            popup.draw(canvas,paint);
        }

    }

    public  void touch(int x, int y,int type) {
        int imageSize = Math.min((listArea.width()-4*edgeBuffer)/3-levelHeight/3,7*levelHeight/8);
        if(type==-1){
            if(backButton.getHover() && popup==null){
                Intent i = new Intent(context,HomeScreen.class);
                context.startActivity(i);
                ((AppCompatActivity)context).overridePendingTransition(R.anim.up_to_mid,R.anim.mid_to_down);
            }
            if(tabDefault.getHover() && !tab.equals("default") && popup==null){
                levels = Loader.getDefaultLevels();
                tab="default";
                tabDefault.setColor(Color.rgb(255,100,72));
                tabCustom.setColor(Color.rgb(65,99,135));
                selectedLevel=null;
                scrollPosition=0;
                previews = Loader.getDefaultPreviews();
            }
            if(tabCustom.getHover() && !tab.equals("custom") && popup==null){
                levels = Loader.getCustomLevels();
                tab="custom";
                tabCustom.setColor(Color.rgb(255,100,72));
                tabDefault.setColor(Color.rgb(65,99,135));
                selectedLevel=null;
                scrollPosition=0;
                previews = Loader.getCustomPreviews();
            }

            if(listArea.contains(oldX,oldY) && Math.sqrt((oldX-startX)*(oldX-startX)+(oldY-startY)*(oldY-startY))<50 && popup==null){
                for(int i=0;i<levels.size();i++){
                    int yPosition = (i-i%3)/3*(levelHeight+levelBuffer)+levelBuffer+listArea.top-scrollPosition;
                    Rect thisLevel;
                    int levelWidth = (listArea.width()-4*edgeBuffer)/3;
                    if(i%3==0){
                        thisLevel = new Rect(listArea.left + edgeBuffer, yPosition, listArea.left + levelWidth+edgeBuffer, yPosition + levelHeight);
                    } else if(i%3==1){
                        thisLevel = new Rect(listArea.left + levelWidth+2*edgeBuffer, yPosition, listArea.left + 2*levelWidth+2*edgeBuffer, yPosition + levelHeight);
                    } else {
                        thisLevel = new Rect(listArea.left + 2*levelWidth+3*edgeBuffer, yPosition, listArea.right-edgeBuffer, yPosition + levelHeight);
                    }
                    if (thisLevel.contains(oldX, oldY) && !(tab.equals("default") && Integer.valueOf(levels.get(i).getName())>maxLevel && popup==null)) {
                        selectedLevel = levels.get(i);
                        Log.e("POPUPCREATION","STARTED");
                        popup = new SelectorMenu(selectedLevel,Loader.preview(levels.get(i),false,context),this,context);
                        Log.e("POPUPCREATION","FISHIHED");
                    }
                }
            }
            touchStartY=-1;
        }
        backButton.touch(x,y);
        tabCustom.touch(x,y);
        tabDefault.touch(x,y);
        if(popup != null){
            if(popup.touch(x,y,type)){
                popup=null;
            }
        }

        if(type==0 && popup==null) {
            if(touchStartY!=-1) {
                scrollPosition += touchStartY - y;
            }
            touchStartY = y;
            scrollPosition = Math.max(0, scrollPosition);
            int height = listArea.height()-edgeBuffer;
            int levelsHeight = levels.size()/3*(levelHeight+levelBuffer)+(levelHeight+levelBuffer);
            scrollPosition = Math.min(scrollPosition,Math.max(0,levelsHeight-height));
        }
        if(type==1){
            startX=x;
            startY=y;
        }
        oldX = x;
        oldY=y;
    }



    public void play() {
        Intent i = new Intent(context, GameScreen.class);
        i.putExtra("level", selectedLevel.toString());
        i.putExtra("pack", tab);
        context.startActivity(i);
        ((AppCompatActivity) context).overridePendingTransition(R.anim.down_to_mid, R.anim.mid_to_up);

    }

    public String getTab() {
        return tab;
    }

    public void closePopup() {
        popup = null;
    }
}
