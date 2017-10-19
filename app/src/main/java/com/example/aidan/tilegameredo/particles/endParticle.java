package com.example.aidan.tilegameredo.particles;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.aidan.tilegameredo.Game;
import com.example.aidan.tilegameredo.HomeScreen;
import com.example.aidan.tilegameredo.ParticleManager;
import com.example.aidan.tilegameredo.R;
import com.example.aidan.tilegameredo.SelectorScreen;

public class endParticle extends Particle{

    private int targetSize,mode,height,width;
    private Context context;

    public endParticle(int tX, int tY, int tS, Context context){
        super(tX,tY,1500);
        this.context=context;
        targetSize=tS;
        mode=1;
        height = Resources.getSystem().getDisplayMetrics().heightPixels;
        width = Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public void paint(Canvas canvas, Paint paint){
        super.setTime(super.getTime()-1000/Game.getFps());
        if (super.getTime() <= targetSize && mode==1) {
            mode=2;
            super.setTime(255);
        }
        if (super.getTime() <= 0 && mode==2) {
            mode=3;
            Game.updateStars();
            Intent i = new Intent(context,SelectorScreen.class);
            context.startActivity(i);
            super.setTime(255);
        }
        if (super.getTime() <= 0 && mode==3) {
            mode=4;
        }
        if(mode==1 || mode==2) {
            paint.setColor(Color.BLACK);
            Path path = new Path();
            path.lineTo(-50, -50);
            path.lineTo(-50, height);
            path.lineTo(width, height);
            path.lineTo(width, -120);
            if(mode==2){
                path.addCircle((super.getX()), (super.getY()), targetSize, Path.Direction.CW);
            } else {
                path.addCircle((super.getX()), (super.getY()), Math.max((float) super.getTime(), targetSize), Path.Direction.CW);
            }
            canvas.drawPath(path, paint);
        }
        if(mode==2){
            paint.setColor(Color.BLACK);
            paint.setAlpha((int)super.getTime()*-1+255);
            canvas.drawRect(-10,-10,canvas.getWidth(),canvas.getHeight(),paint);
        }
        if(mode==3){
            paint.setColor(Color.BLACK);
            canvas.drawRect(-10,-10,canvas.getWidth(),canvas.getHeight(),paint);
        }
    }

    @Override
    public boolean isDone() {
        return mode==4;
    }
}
