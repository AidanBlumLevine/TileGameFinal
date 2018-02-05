package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

class SelectorMenu {
    private Rect popupArea;
    private Bitmap preview;
    private Level level;
    private LevelSelector parent;
    private Button play;
    private int textSize;
    private Context context;
    public SelectorMenu(Level level, Bitmap preview, LevelSelector parent, Context context) {
        this.level=level;
        this.parent=parent;
        this.context = context;

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        int border = width/12;
        popupArea = new Rect(border,border,width-border,height-border);

        int buttonWidth =  (popupArea.width()-150)/2;
        play = new Button(popupArea.left+100+buttonWidth,popupArea.bottom-50-buttonWidth/2,Bitmap.createScaledBitmap(ImageLoader.getButtonShare(context),buttonWidth,buttonWidth/2,false));

        int imageWidth = Math.min(popupArea.height()-300-buttonWidth/2,popupArea.width()-100);
        this.preview = Bitmap.createScaledBitmap(preview,imageWidth,imageWidth,false);

        Rect textArea = new Rect(popupArea.left+20,popupArea.top+20,popupArea.right-20,popupArea.top+180);
        Rect testRect = new Rect();
        int size = 100;
        Paint p = new Paint();
        p.setTextSize(size);
        p.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        p.getTextBounds(level.getName(),0,level.getName().length(),testRect);
        while(!(testRect.width()<textArea.width() && testRect.height()<textArea.height())){
            size--;
            p.setTextSize(size);
            p.getTextBounds(level.getName(),0,level.getName().length(),testRect);
        }
        textSize=size;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(230,255,255,255));
        canvas.drawRect(popupArea,paint);

        play.draw(canvas,paint);

        int imageY = (popupArea.height()-300-(popupArea.width()-150)/2/2-preview.getHeight())/2+popupArea.top+200;
        canvas.drawBitmap(preview,(popupArea.width()-preview.getWidth())/2+popupArea.left,imageY,paint);

        paint.setColor(Color.argb(200,0,0,0));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        int xPos = (popupArea.centerX());
        Rect textRect = new Rect();
        paint.getTextBounds(level.getName(),0,level.getName().length(),textRect);
        canvas.drawText(level.getName(), xPos, (imageY-popupArea.top)/2+popupArea.top+textRect.height()/2, paint);
        paint.reset();
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
