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

    private  final int levelHeight = 200;
    private  final int levelBuffer = 20;

    public LevelSelector(Context context) {
        selectedLevel=null;
        this.context = context;
        levels = new ArrayList<>();
        levels = LevelGenerator.getAllLevels(tab,context);

        edgeBuffer = 20;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        int tabHeight = screenHeight /12;
        listArea = new Rect(edgeBuffer,tabHeight+(int)(edgeBuffer*1.5),screenWidth-edgeBuffer,screenHeight-edgeBuffer);

        int boxSize = tabHeight-10;
        backButton = new Button(edgeBuffer,edgeBuffer,Bitmap.createScaledBitmap(ImageLoader.getButtonBack(context),boxSize,boxSize,false));
        tabDefault = new Button(screenWidth-boxSize*4-edgeBuffer*3,edgeBuffer,Bitmap.createScaledBitmap(ImageLoader.getButtonWideBlank(context),boxSize*5/3,boxSize,false));
        tabCustom = new Button(screenWidth-boxSize*2-edgeBuffer*2,edgeBuffer,Bitmap.createScaledBitmap(ImageLoader.getButtonWideBlank(context),boxSize*5/3,boxSize,false));

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        maxLevel = settings.getInt("maxLevel",1);

        int imageSize = Math.min((listArea.width()-4*edgeBuffer)/3-levelHeight/3,7*levelHeight/8);
        for(int i=0;i<levels.size();i++){
            previews.add(Bitmap.createScaledBitmap(preview(levels.get(i),false),imageSize,imageSize,false));
        }
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
        //canvas.clipRect(listArea.left,listArea.top+edgeBuffer/2,listArea.right,listArea.bottom-edgeBuffer/2);
        for(int i=0;i<levels.size();i++){
            int yPosition = (i-i%3)/3*(levelHeight+levelBuffer)+levelBuffer+listArea.top-scrollPosition;
            if(yPosition<screenHeight) {
                Rect thisLevel;

                int levelWidth = (listArea.width()-4*edgeBuffer)/3;
                if(i%3==0){
                    thisLevel = new Rect(listArea.left + edgeBuffer, yPosition, listArea.left + levelWidth+edgeBuffer, yPosition + levelHeight);
                } else if(i%3==1){
                    thisLevel = new Rect(listArea.left + levelWidth+2*edgeBuffer, yPosition, listArea.left + 2*levelWidth+2*edgeBuffer, yPosition + levelHeight);
                } else {
                    thisLevel = new Rect(listArea.left + 2*levelWidth+3*edgeBuffer, yPosition, listArea.right-edgeBuffer, yPosition + levelHeight);
                }
                String levelName = levels.get(i).getName();
                Rect nameBounds = new Rect();
                paint.getTextBounds(levelName,0,levelName.length(),nameBounds);
                if(nameBounds.width()+50>thisLevel.width()){
                    while(nameBounds.width()+50>thisLevel.width()){
                        levelName = levelName.substring(0,levelName.length()-1);
                        paint.getTextBounds(levelName+"...",0,levelName.length()+1,nameBounds);
                    }
                    levelName = levelName+"...";
                }

                if(tab.equals("default") && Integer.valueOf(levels.get(i).getName())>maxLevel){
                    paint.setColor(Color.LTGRAY);
                    canvas.drawRect(thisLevel,paint);
                } else {
                    paint.setColor(Color.WHITE);
                    //canvas.drawRect(thisLevel.left,thisLevel.top+thisLevel.height()/10,thisLevel.right,thisLevel.bottom-thisLevel.height()/10,paint);
                    //canvas.drawRect(thisLevel.left+thisLevel.height()/10,thisLevel.top,thisLevel.right-thisLevel.height()/10,thisLevel.bottom,paint);
                    canvas.drawRect(thisLevel,paint);
                    try {canvas.drawBitmap(previews.get(i), thisLevel.centerX() - previews.get(i).getWidth()/2, thisLevel.centerY() - previews.get(i).getHeight()/2, paint);
                    }catch (Exception e){}
                }

                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(48);
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                int xPos = (thisLevel.left+thisLevel.width() / 2);
                int yPos = (int) (thisLevel.centerY() - ((paint.descent() + paint.ascent()) / 2)) ;

                paint.setColor(Color.argb(100,200,200,200));
                canvas.drawRect(thisLevel.left,(int) (thisLevel.centerY() + ((paint.descent() + paint.ascent()) / 2))-20,thisLevel.right,(int) (thisLevel.centerY() - ((paint.descent() + paint.ascent()) / 2))+20,paint);

                paint.setColor(Color.argb(200,0,0,0));
                canvas.drawText(levelName, xPos, yPos, paint);
            }
        }
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
            if(backButton.getHover()){
                Intent i = new Intent(context,HomeScreen.class);
                context.startActivity(i);
                ((AppCompatActivity)context).overridePendingTransition(R.anim.up_to_mid,R.anim.mid_to_down);
            }
            if(tabDefault.getHover() && !tab.equals("default")){
                levels = LevelGenerator.getAllLevels("default",context);
                tab="default";
                selectedLevel=null;
                scrollPosition=0;
                previews.clear();
                for(int i=0;i<levels.size();i++){
                    previews.add(Bitmap.createScaledBitmap(preview(levels.get(i),false),imageSize,imageSize,false));
                }
            }
            if(tabCustom.getHover() && !tab.equals("custom")){
                levels = LevelGenerator.getAllLevels("custom",context);
                tab="custom";
                selectedLevel=null;
                scrollPosition=0;
                previews.clear();
                for(int i=0;i<levels.size();i++){
                    previews.add(Bitmap.createScaledBitmap(preview(levels.get(i),false),imageSize,imageSize,false));
                }
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
                    if (thisLevel.contains(oldX, oldY) && !(tab.equals("default") && Integer.valueOf(levels.get(i).getName())>maxLevel)) {
                        selectedLevel = levels.get(i);
                        popup = new SelectorMenu(selectedLevel,preview(levels.get(i),true),this,context);
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

    private Bitmap preview(Level level,Boolean background){
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap preview = Bitmap.createBitmap(level.getWidth()*30, level.getWidth()*30, conf);
        Canvas canvas = new Canvas(preview);
        String tiles = level.toString().split("\\|")[4];
        Paint p = new Paint();
        if(background) {
            canvas.drawColor(Color.argb(100, 220, 220, 220));
        }
        int tileSize = 29;
        for(int i=0;i<tiles.split(":").length;i++){
            Log.e("TEST",tiles.split(":").length+"");
                if (tiles.split(":")[i].split(",")[0].equals("box"))
                    canvas.drawBitmap(Bitmap.createScaledBitmap(ImageLoader.getBoxImage(context),tileSize,tileSize,false),Integer.valueOf(tiles.split(":")[i].split(",")[1]), Integer.valueOf(tiles.split(":")[i].split(",")[2]), p);
                if (tiles.split(":")[i].split(",")[0].equals("crate"))
                    canvas.drawBitmap(Bitmap.createScaledBitmap(ImageLoader.getCrateImage(context),tileSize,tileSize,false),Integer.valueOf(tiles.split(":")[i].split(",")[1]), Integer.valueOf(tiles.split(":")[i].split(",")[2]), p);
                if (tiles.split(":")[i].split(",")[0].equals("emptyCrate"))
                    canvas.drawBitmap(Bitmap.createScaledBitmap(ImageLoader.getEmptyCrateImage(context),tileSize,tileSize,false),Integer.valueOf(tiles.split(":")[i].split(",")[1]), Integer.valueOf(tiles.split(":")[i].split(",")[2]), p);
                if (tiles.split(":")[i].split(",")[0].equals("wall"))
                    canvas.drawBitmap(Bitmap.createScaledBitmap(ImageLoader.getWallImage(context),tileSize,tileSize,false),Integer.valueOf(tiles.split(":")[i].split(",")[1]), Integer.valueOf(tiles.split(":")[i].split(",")[2]), p);
                if (tiles.split(":")[i].split(",")[0].equals("doubleCrate") && Integer.valueOf(tiles.split(":")[i].split(",")[3]) == 1)
                    canvas.drawBitmap(Bitmap.createScaledBitmap(ImageLoader.getDoubleCrateImage(context),tileSize*2,tileSize,false),Integer.valueOf(tiles.split(":")[i].split(",")[1]), Integer.valueOf(tiles.split(":")[i].split(",")[2]), p);
                if (tiles.split(":")[i].split(",")[0].equals("doubleCrate") && Integer.valueOf(tiles.split(":")[i].split(",")[3]) == 2)
                    canvas.drawBitmap(Bitmap.createScaledBitmap(ImageLoader.getDoubleCrate2Image(context),tileSize,tileSize*2,false),Integer.valueOf(tiles.split(":")[i].split(",")[1]), Integer.valueOf(tiles.split(":")[i].split(",")[2]), p);
                if (tiles.split(":")[i].split(",")[0].equals("spike")) {
                    canvas.save();
                    canvas.rotate(Integer.valueOf(tiles.split(":")[i].split(",")[3]) * 90, Integer.valueOf(tiles.split(":")[i].split(",")[1]) + 15, Integer.valueOf(tiles.split(":")[i].split(",")[2]) + 15);
                    canvas.drawBitmap(Bitmap.createScaledBitmap(ImageLoader.getSpikeImage(context), tileSize, tileSize, false), Integer.valueOf(tiles.split(":")[i].split(",")[1]), Integer.valueOf(tiles.split(":")[i].split(",")[2]), p);
                    canvas.restore();
                }
        }
        return preview;
    }

    public void play() {
        Intent i = new Intent(context,GameScreen.class);
        i.putExtra("level",selectedLevel.toString());
        i.putExtra("pack",tab);
        context.startActivity(i);
        ((AppCompatActivity)context).overridePendingTransition(R.anim.up_to_mid,R.anim.mid_to_down);
    }
}
