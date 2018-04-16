package com.example.aidan.tilegameredo;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;

public class Button {
    private int x,y,width,height,color=-666,textSize;
    private Bitmap image,smallImage;
    private boolean hover=false;
    private String text;
    private Typeface typeface;
    public Button(int x, int y, Bitmap image){
        this.x=x;
        this.y=y;
        this.image=image;
        smallImage = Bitmap.createScaledBitmap(image,image.getWidth()-10,image.getHeight()-10,false);

    }

    public Button(int x, int y, int width, int height, int color, String text, int textSize, Typeface typeface) {
        this.x=x;
        this.y=y;
        this.width = width;
        this.height=height;
        this.color=color;
        this.text=text;
        this.textSize=textSize;
        this.typeface = typeface;

        Rect testRect = new Rect();
        Paint p = new Paint();
        p.setTextSize(textSize);
        p.setTypeface(typeface);
        p.getTextBounds(text,0,text.length(),testRect);
        while(!(testRect.width()<width*0.9 && testRect.height()<height*0.9)){
            width++;
        }

        this.width = width;
    }

    public Button(int x, int y, int width, int height, int color, String text, Typeface typeface) {
        this.x=x;
        this.y=y;
        this.width = width;
        this.height=height;
        this.color=color;
        this.text=text;
        this.typeface = typeface;

        Rect testRect = new Rect();
        int size = 300;
        Paint p = new Paint();
        p.setTextSize(size);
        p.setTypeface(typeface);
        p.getTextBounds(text,0,text.length(),testRect);
        while(!(testRect.width()<width*0.9 && testRect.height()<height*0.9)){
            size--;
            p.setTextSize(size);
            p.getTextBounds(text,0,text.length(),testRect);
        }
        textSize=size;
    }

    public void draw(Canvas canvas,Paint paint){
        if(color == -666) {
            if (hover) {
                canvas.drawBitmap(smallImage, x + 5, y + 5, paint);
            } else {
                canvas.drawBitmap(image, x, y, paint);
            }
        } else {
            paint.setMaskFilter(new BlurMaskFilter(1, BlurMaskFilter.Blur.NORMAL));
            paint.setColor(color);
            canvas.drawRect(x,y,width+x,height+y,paint);
            paint.reset();
            paint.setAntiAlias(true);
            paint.setTypeface(typeface);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(textSize);
            int xPos = (x+width/2);
            int yPos = (int) (y+height/2 - ((paint.descent() + paint.ascent()) / 2) + 1) ;
            paint.setColor(Color.WHITE);
            canvas.drawText(text,xPos,yPos,paint);
        }
    }
    public void touch(int xTouch,int yTouch){
        Rect area = null;
        if(color == -666) {
            area = new Rect(x, y, image.getWidth() + x, image.getHeight() + y);
        } else {
            area = new Rect(x,y,x+width,y+height);
        }
        hover = area.contains(xTouch,yTouch);

    }
    public void setColor(int color){
        this.color=color;
    }
    public boolean getHover(){
        return hover;
    }
}
