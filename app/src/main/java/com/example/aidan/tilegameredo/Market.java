package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class Market {
    private Context context;
    private Rect listArea,sideBar,tabArea;
    private Button buttonTop,buttonNew,buttonMostPlayed,buttonDownload,buttonBack,buttonSearch;
    private String order="top";
    private ArrayList<Bitmap> previews = new ArrayList<>();
    private ArrayList<MarketLevel> levels = new ArrayList<>();
    private SelectorMenu popup = null;
    private  int scrollPosition=0,touchStartY,screenHeight;

    private final int levelHeight = 200;
    private final int levelBuffer = 20;
    private final int buffer = 30;
    public Market(Context context){
        this.context=context;

        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        sideBar = new Rect(buffer,buffer,width/5,screenHeight-buffer);
        tabArea = new Rect(sideBar.right+buffer,buffer,width-buffer,(width-sideBar.right)/3+buffer);
        listArea = new Rect(sideBar.right+2*buffer,tabArea.bottom+2*buffer,width-2*buffer,screenHeight-2*buffer);

        int buttonWidth = sideBar.width()-buffer;
        buttonBack = new Button(sideBar.left+buffer/2,sideBar.top+buffer/2, Bitmap.createScaledBitmap(ImageLoader.getButtonBack(context),buttonWidth,buttonWidth,false));
        buttonDownload = new Button(sideBar.left+buffer/2,sideBar.top+2*buffer/2+buttonWidth, Bitmap.createScaledBitmap(ImageLoader.getButtonDownload(context),buttonWidth,buttonWidth,false));

        buttonWidth = Math.min((tabArea.width()-2*buffer)/3,tabArea.height()-3*buffer/2);
        buttonNew = new Button(tabArea.left+buffer/2,tabArea.bottom-buttonWidth/2-buffer/2, Bitmap.createScaledBitmap(ImageLoader.getButtonNew(context),buttonWidth,buttonWidth/2,false));
        buttonMostPlayed = new Button(tabArea.centerX()-buttonWidth/2,tabArea.bottom-buttonWidth/2-buffer/2, Bitmap.createScaledBitmap(ImageLoader.getButtonMostPlayed(context),buttonWidth,buttonWidth/2,false));
        buttonTop = new Button(tabArea.right-buffer/2-buttonWidth,tabArea.bottom-buttonWidth/2-buffer/2, Bitmap.createScaledBitmap(ImageLoader.getButtonTopPlayed(context),buttonWidth,buttonWidth/2,false));

        buttonWidth = tabArea.height()-3*buffer/2-buttonWidth/2;
        buttonSearch = new Button(tabArea.centerX()-buttonWidth,tabArea.top+buffer/2, Bitmap.createScaledBitmap(ImageLoader.getButtonSearch(context),buttonWidth*2,buttonWidth,false));
        getResults();
    }
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawColor(Color.WHITE);
        paint.setAlpha(80);
        canvas.drawBitmap(ImageLoader.getBackground(context),-30,-50,paint);

        paint.setColor(Color.argb(200,200,200,200));
        canvas.drawRect(tabArea,paint);
        canvas.drawRect(sideBar,paint);
        canvas.drawRect(listArea,paint);
        paint.reset();

        buttonTop.draw(canvas,paint);
        buttonNew.draw(canvas,paint);
        buttonMostPlayed.draw(canvas,paint);
        buttonDownload.draw(canvas,paint);
        buttonBack.draw(canvas,paint);
        buttonSearch.draw(canvas,paint);

        canvas.save();
        canvas.clipRect(listArea.left,listArea.top+buffer/2,listArea.right,listArea.bottom-buffer/2);
        for(int i=0;i<levels.size();i++){
            int yPosition = i*(levelHeight+levelBuffer)+levelBuffer+listArea.top-scrollPosition;
            if(yPosition<screenHeight) {
                Rect thisLevel = new Rect(listArea.left+levelBuffer,yPosition,listArea.right-levelBuffer,yPosition+levelHeight);

                String levelName = levels.get(i).getName();
                Rect nameBounds = new Rect();
                paint.getTextBounds(levelName,0,levelName.length(),nameBounds);
                if(nameBounds.width()+50>thisLevel.width()/2){
                    while(nameBounds.width()+50>thisLevel.width()){
                        levelName = levelName.substring(0,levelName.length()-1);
                        paint.getTextBounds(levelName+"...",0,levelName.length()+1,nameBounds);
                    }
                    levelName = levelName+"...";
                }

                paint.setColor(Color.WHITE);
                canvas.drawRect(thisLevel,paint);
                try {canvas.drawBitmap(previews.get(i), thisLevel.centerX()+thisLevel.width()/4 - previews.get(i).getWidth()/2, thisLevel.centerY() - previews.get(i).getHeight()/2, paint);
                }catch (Exception e){}

                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(48);
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                int xPos = (thisLevel.left+thisLevel.width() / 4);
                int yPos = (int) (thisLevel.centerY() - ((paint.descent() + paint.ascent()) / 2)) ;

                paint.setColor(Color.argb(100,200,200,200));
                canvas.drawRect(thisLevel.left,(int) (thisLevel.centerY() + ((paint.descent() + paint.ascent()) / 2))-20,thisLevel.width()/2,(int) (thisLevel.centerY() - ((paint.descent() + paint.ascent()) / 2))+20,paint);

                paint.setColor(Color.argb(200,0,0,0));
                canvas.drawText(levelName, xPos, yPos, paint);
            }
        }
        canvas.restore();
        paint.reset();
    }

    public void touch(int x, int y, int type) {
        if(type == -1){
            if(buttonTop.getHover()){
                order="top";
                getResults();
            }
            if(buttonNew.getHover()){
                order="new";
                getResults();
            }
            if(buttonMostPlayed.getHover()){
                order="mostPlayed";
                getResults();
            }
            if(buttonDownload.getHover()){

            }
            if(buttonBack.getHover()){
                Intent i = new Intent(context,HomeScreen.class);
                context.startActivity(i);
                ((AppCompatActivity)context).overridePendingTransition(R.anim.up_to_mid,R.anim.mid_to_down);
            }
            if(buttonSearch.getHover()){

            }
        }
        buttonTop.touch(x,y);
        buttonNew.touch(x,y);
        buttonMostPlayed.touch(x,y);
        buttonDownload.touch(x,y);
        buttonBack.touch(x,y);
        buttonSearch.touch(x,y);
        if(type==0 && popup==null) {
            if(touchStartY!=-1) {
                scrollPosition += touchStartY - y;
            }
            touchStartY = y;
            scrollPosition = Math.max(0, scrollPosition);
            int height = listArea.height();
            int levelsHeight = (levels.size()-1)*(levelHeight+levelBuffer)+levelBuffer+listArea.top;
            scrollPosition = Math.min(scrollPosition,Math.max(0,levelsHeight-height));
        }
    }

    public void getResults(){
        previews.clear();
        int imageSize = Math.min(listArea.width()/2-levelBuffer-100,levelHeight-100);
        for(int i=0;i<levels.size();i++){
            previews.add(Bitmap.createScaledBitmap(preview(levels.get(i),false),imageSize,imageSize,false));
        }
    }

    public Bitmap preview(Level level,Boolean background){
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
            if(tiles.split(":")[2]!=null) {
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
                    canvas.rotate(Integer.valueOf(tiles.split(":")[i].split(",")[3]) * 90,Integer.valueOf(tiles.split(":")[i].split(",")[1])+15, Integer.valueOf(tiles.split(":")[i].split(",")[2])+15);
                    canvas.drawBitmap(Bitmap.createScaledBitmap(ImageLoader.getSpikeImage(context),tileSize,tileSize,false), Integer.valueOf(tiles.split(":")[i].split(",")[1]), Integer.valueOf(tiles.split(":")[i].split(",")[2]), p);
                    canvas.restore();
                }
            }
        }
        return preview;
    }
}
