package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import java.util.ArrayList;

class SelectorMenu {
    private ArrayList<int[]> touches = new ArrayList<int[]>();
    private Rect popupArea,starArea,textArea,previewArea;
    private Bitmap preview;
    private Level level;
    private LevelSelector parent;
    private Button play,edit,delete,back;
    private int textSize;
    private Context context;
    private Bitmap goldCrate,silverCrate,bronzeCrate;
    public SelectorMenu(Level level, Bitmap preview, LevelSelector parent, Context context) {
        this.level=level;
        this.parent=parent;
        this.context = context;

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        int border = width/12;
        popupArea = new Rect(border,border,width-border,height-border);

        previewArea = new Rect(popupArea.left,popupArea.centerY()-popupArea.height()/4,popupArea.right,popupArea.centerY()+popupArea.height()/4);
        int imageWidth = Math.min(previewArea.height()-100,popupArea.width()-100);
        this.preview = Bitmap.createScaledBitmap(preview,imageWidth,imageWidth,false);


        textArea = new Rect(popupArea.left+20+popupArea.width()/4,popupArea.top+border*2,popupArea.right-20,previewArea.top-2*border);

        back = new Button(popupArea.left+border,textArea.top,Bitmap.createScaledBitmap(Loader.getButtonBack(context),textArea.height(),textArea.left-popupArea.left,false));

        play = new Button(popupArea.left+border,previewArea.bottom+border, popupArea.centerX()-border,popupArea.bottom-border,Color.YELLOW,"PLAY",48,Loader.getFont(context));
        if(parent.getTab() =)
        edit  = new Button(popupArea.centerX()+popupArea.width()/2+popupArea.width()/3-height-previewArea.bottom/4,previewArea.bottom+border,Bitmap.createScaledBitmap(Loader.getButtonEdit(context),height-previewArea.bottom/2,height-previewArea.bottom/2,false));



        Rect testRect = new Rect();
        int size = 100;
        String levelName = level.getName();
        if(parent.getTab().equals("custom")){
            levelName = levelName.substring(0,levelName.length()-6);
        }
        Paint p = new Paint();
        p.setTextSize(size);
        p.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        p.getTextBounds(levelName,0,levelName.length(),testRect);
        while(!(testRect.width()<textArea.width() && testRect.height()<textArea.height())){
            size--;
            p.setTextSize(size);
            p.getTextBounds(levelName,0,levelName.length(),testRect);
        }
        textSize=size;

        starArea = new Rect(popupArea.left,textArea.bottom,popupArea.right,popupArea.top);

        goldCrate = Bitmap.createScaledBitmap(Loader.getGoldCrate(context),starArea.height(),starArea.height(),false);
        silverCrate = Bitmap.createScaledBitmap(Loader.getSilverCrate(context),starArea.height(),starArea.height(),false);
        bronzeCrate = Bitmap.createScaledBitmap(Loader.getBronzeCrate(context),starArea.height(),starArea.height(),false);
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(230,255,255,255));
        canvas.drawRect(popupArea,paint);

        play.draw(canvas,paint);

        int imageY = previewArea.centerY()-preview.getHeight()/2;
        canvas.drawBitmap(preview,(popupArea.width()-preview.getWidth())/2+popupArea.left,imageY,paint);

        paint.setColor(Color.argb(200,0,0,0));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        int xPos = (popupArea.centerX());
        String levelName = level.getName();
        if(parent.getTab().equals("custom")){
            //STRING OUT OF BOUNDS HERE ++++++++++++!+!+@+@+!@+!++#+!@+#+@#++$#+#!+$!+#+$#+$+!@#+$!+#$+%+$++!$#+$+!#+$+!#$+#+$+!@#$+!@#+!@+#+!@#+!@+#+!@#+@!+$+#@
            levelName = levelName.substring(0,levelName.length()-6);
        }
        Rect textRect = new Rect();
        paint.getTextBounds(levelName,0,levelName.length(),textRect);
        canvas.drawText(levelName, xPos, textArea.centerY()+textRect.height()/2, paint);
        paint.reset();

        //if(level.getStarLevels()[0] != 0) {
            if (level.getStars() > 2) {
                canvas.drawBitmap(goldCrate, starArea.right - starArea.height(), starArea.top, paint);
            } else {
                paint.setAlpha(120);
                canvas.drawBitmap(goldCrate, starArea.right - starArea.height(), starArea.top, paint);
                paint.reset();
            }

            if (level.getStars() > 1) {
                canvas.drawBitmap(silverCrate, starArea.centerX() - starArea.height() / 2, starArea.top, paint);
            } else {
                paint.setAlpha(120);
                canvas.drawBitmap(silverCrate, starArea.centerX() - starArea.height() / 2, starArea.top, paint);
                paint.reset();
            }

            if (level.getStars() > 0) {
                canvas.drawBitmap(bronzeCrate, starArea.left, starArea.top, paint);
            } else {
                paint.setAlpha(120);
                canvas.drawBitmap(bronzeCrate,  starArea.left, starArea.top, paint);
                paint.reset();
            }
        //}
    }

    public boolean touch(int x, int y, int type) {
        if(type == -1){
            if(play.getHover()){
                parent.play();
            }
        }
        play.touch(x,y);
        if(!popupArea.contains(x,y) && type == 1){
            return true;
        }
        return false;
    }
}
