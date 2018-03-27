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
import com.example.aidan.tilegameredo.Loader;
import com.example.aidan.tilegameredo.ParticleManager;
import com.example.aidan.tilegameredo.R;
import com.example.aidan.tilegameredo.SelectorScreen;

public class truckParticle extends Particle{

    private int targetSize,mode,height,width,x=-1000,targetY,targetX;
    private Context context;
    private Game parent;
    private Bitmap truck,truckFull;
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
        Bitmap tempTruck = Loader.getTruck(context);
        Bitmap tempTruckFull = Loader.getTruckFull(context);
        truck = Bitmap.createScaledBitmap(tempTruck,(int)((double)tempTruck.getWidth()/tempTruck.getHeight()*(2*targetSize)),2*targetSize,false);
        truckFull = Bitmap.createScaledBitmap(tempTruckFull,(int)((double)tempTruckFull.getWidth()/tempTruckFull.getHeight()*(2*targetSize)),2*targetSize,false);

    }
    public void paint(Canvas canvas, Paint paint){
        super.setTime(super.getTime()-3000/parent.getFps());
        if(mode==1){
            paint.reset();
            canvas.drawBitmap(truck,x,targetY-targetSize,paint);
            x+=1400/parent.getFps();
            if(Math.abs(x+truck.getWidth()/2 - targetX) <= 7){
                mode=2;
                super.setTime(300);
            }
        }

       if(mode ==2) {
            paint.reset();
           canvas.drawBitmap(truck,x,targetY-targetSize,paint);
           if (super.getTime() <= 0) {
               mode = 3;
               super.setTime(300);
               parent.hideGoal();
               ParticleManager.addParticle(new explosionParticle(targetX+15-(int)(truck.getWidth()*.2),targetY+15-truck.getHeight()/4));
           }
       }
        if(mode == 3) {
            paint.reset();
            canvas.drawBitmap(truckFull,x,targetY-targetSize,paint);
            if (super.getTime() <= 0) {
                mode = 4;
            }
        }

        if(mode==4){
            paint.reset();
            canvas.drawBitmap(truckFull,x,targetY-targetSize,paint);
            x+=1400/parent.getFps();
            paint.setColor(Color.BLACK);
            paint.setAlpha(Math.max(0,(int)map(targetX,width+500,0,255,x+truck.getWidth()/2)));
            canvas.drawRect(-10,-10,width,height,paint);
            paint.reset();
            if(x>width){
                mode = 5;
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
        return mode==5;
    }
}
