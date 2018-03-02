package com.example.aidan.tilegameredo.particles;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.aidan.tilegameredo.EndScreen;
import com.example.aidan.tilegameredo.Game;
import com.example.aidan.tilegameredo.HomeScreen;
import com.example.aidan.tilegameredo.ImageLoader;
import com.example.aidan.tilegameredo.ParticleManager;
import com.example.aidan.tilegameredo.R;
import com.example.aidan.tilegameredo.SelectorScreen;

public class truckParticle extends Particle{

    private int targetSize,mode,height,width,x=-200,targetY,targetX;
    private Context context;
    private Game parent;
    private Bitmap truck;
    public truckParticle(int tX, int tY, int tS, Context context,Game parent){
        super(tX,tY,1500);
        this.parent = parent;
        this.context=context;
        targetSize=tS;
        targetX = tX;
        targetY = tY;
        mode=1;
        height = Resources.getSystem().getDisplayMetrics().heightPixels;
        width = Resources.getSystem().getDisplayMetrics().widthPixels;
        truck = Bitmap.createScaledBitmap(ImageLoader.getTruck(context),100+targetSize,40+targetSize,false);
    }
    public void paint(Canvas canvas, Paint paint){
        super.setTime(super.getTime()-3000/parent.getFps());
        if(mode==1){
            paint.setColor(Color.YELLOW);
            canvas.drawBitmap(truck,x,targetY-20-targetSize/2,paint);
            x+=700/parent.getFps();
            if(Math.abs(x+(100+targetSize)/2 - targetX) <= 7){
                mode=2;
                super.setTime(300);
                x=targetX-(100+targetSize)/2;
            }
        }

       if(mode ==2) {
           paint.setColor(Color.YELLOW);
           canvas.drawBitmap(truck,x,targetY-20-targetSize/2,paint);
           if (super.getTime() <= 0) {
               mode = 3;
               parent.hideGoal();
           }
       }

        if(mode==3){
            paint.setColor(Color.YELLOW);
            canvas.drawBitmap(truck,x,targetY-20-targetSize/2,paint);
            x+=700/parent.getFps();
            paint.setColor(Color.BLACK);
            paint.setAlpha((int)map(targetX,width+300,0,255,x+(100+targetSize)/2));
            canvas.drawRect(-10,-10,width,height,paint);
            paint.reset();
            if(x>width){
                mode = 4;
                parent.saveStars();
                Intent i = new Intent(context,EndScreen.class);
                i.putExtra("stars",parent.getStars());
                i.putExtra("pack",parent.getPack());
                i.putExtra("level",parent.getLevel().toString());
                context.startActivity(i);
            }
        }
    }

    private double map(double start1,double stop1,double start2,double stop2,double value){
        return ((value - start1) / (stop1 - start1)) * (stop2 - start2) + start2;
    }
    @Override
    public boolean isDone() {
        return mode==4;
    }
}
