package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Market {
    private Context context;
    private Rect listArea,sideBar,tabArea;
    private Button buttonTop,buttonNew,buttonMostPlayed,buttonDownload,buttonBack,buttonSearch;
    public Market(Context context){
        this.context=context;

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        
        int buffer = 30;
        sideBar = new Rect(buffer,buffer,width/8,height-buffer);
        tabArea = new Rect(sideBar.right+buffer,buffer,width-buffer,height/16);
        listArea = new Rect(sideBar.right+2*buffer,tabArea.bottom+2*buffer,width-2*buffer,height-2*buffer);

        int buttonWidth = sideBar.width()-2*buffer;
        buttonTop = new Button(sideBar.left+buffer,sideBar.top+buffer, Bitmap.createScaledBitmap(ImageLoader.getButtonTopPlayed(context),buttonWidth,buttonWidth,false));
    }
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawColor(Color.WHITE);
        paint.setAlpha(80);
        canvas.drawBitmap(ImageLoader.getBackground(context),-30,-50,paint);
        paint.reset();

        buttonTop.draw(canvas,paint);
        buttonNew.draw(canvas,paint);
        buttonMostPlayed.draw(canvas,paint);
        buttonDownload.draw(canvas,paint);
        buttonBack.draw(canvas,paint);
        buttonSearch.draw(canvas,paint);
    }

    public void touch(int x, int y, int type) {
        if(type == -1){
            if(buttonTop.getHover()){
                
            }
            if(buttonNew.getHover()){

            }
            if(buttonMostPlayed.getHover()){

            }
            if(buttonDownload.getHover()){

            }
            if(buttonBack.getHover()){

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
    }
}
