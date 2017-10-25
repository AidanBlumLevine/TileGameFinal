package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

class SelectorMenu {
    private Rect popupArea;
    private Bitmap preview;
    private Level level;
    private LevelSelector parent;
    private Button share,play;
    public SelectorMenu(Level level, Bitmap preview, LevelSelector parent, Context context) {
        this.level=level;
        this.parent=parent;

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        int border = width/12;
        popupArea = new Rect(border,border,width-border,height-border);

        int buttonWidth =  (popupArea.width()-150)/2;
        share = new Button(popupArea.left+50,popupArea.bottom-50-buttonWidth/2,Bitmap.createScaledBitmap(ImageLoader.getButtonPlay(context),buttonWidth,buttonWidth/2,false));
        play = new Button(popupArea.left+100+buttonWidth,popupArea.bottom-50-buttonWidth/2,Bitmap.createScaledBitmap(ImageLoader.getButtonShare(context),buttonWidth,buttonWidth/2,false));

        int imageWidth = Math.min(popupArea.height()-300-buttonWidth/2,popupArea.width()-100);
        this.preview = Bitmap.createScaledBitmap(preview,imageWidth,imageWidth,false);
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(230,255,255,255));
        canvas.drawRect(popupArea,paint);

        paint.setColor(Color.argb(200,0,0,0));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(48);
        int xPos = (popupArea.centerX());
        canvas.drawText(level.getName(), xPos, popupArea.top+100, paint);
        paint.reset();

        share.draw(canvas,paint);
        play.draw(canvas,paint);

        int imageY = (popupArea.height()-300-(popupArea.width()-150)/2/2-preview.getHeight())/2+popupArea.top+150;
        canvas.drawBitmap(preview,(popupArea.width()-preview.getWidth())/2+popupArea.left,imageY,paint);
    }

    public boolean touch(int x, int y, int type) {
        if(type == -1){
            if(share.getHover()){

            }
            if(play.getHover()){
                parent.play();
            }
        }
        share.touch(x,y);
        play.touch(x,y);
        if(!popupArea.contains(x,y) && type == 1){
            return true;
        }
        return false;
    }
}
