package com.example.aidan.tilegameredo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Button {
    private int x,y;
    private Bitmap image,smallImage;
    private boolean hover=false;
    public Button(int x, int y, Bitmap image){
        this.x=x;
        this.y=y;
        this.image=image;
        smallImage = Bitmap.createScaledBitmap(image,image.getWidth()-10,image.getHeight()-10,false);

    }
    public void draw(Canvas canvas,Paint paint){
        if(hover) {
            canvas.drawBitmap(smallImage, x+5, y+5, paint);
        } else {
            canvas.drawBitmap(image, x, y, paint);
        }
    }
    public void touch(int xTouch,int yTouch){
        Rect area = new Rect(x,y,image.getWidth()+x,image.getHeight()+y);
        hover = area.contains(xTouch,yTouch);
    }
    public boolean getHover(){
        return hover;
    }
}
