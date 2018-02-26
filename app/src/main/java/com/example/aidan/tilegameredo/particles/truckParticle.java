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

import com.example.aidan.tilegameredo.EndScreen;
import com.example.aidan.tilegameredo.Game;
import com.example.aidan.tilegameredo.HomeScreen;
import com.example.aidan.tilegameredo.ParticleManager;
import com.example.aidan.tilegameredo.R;
import com.example.aidan.tilegameredo.SelectorScreen;

public class truckParticle extends Particle{

    private int targetSize,mode,height,width,x=-30,targetY,targetX;
    private Context context;
    private Game parent;

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
    }
    public void paint(Canvas canvas, Paint paint){
        super.setTime(super.getTime()-3000/parent.getFps());
        if(mode==1){
            paint.setColor(Color.YELLOW);
            canvas.drawRect(x,targetY-20,x+100,targetY+40,paint);
            x+=700/parent.getFps();
            if(Math.abs(x+50 - targetX) < 7){
                mode=2;
                super.setTime(300);
            }
        }

        if (super.getTime() <= 0 && mode==2) {
            mode=3;
            parent.hideGoal();
        }

        if(mode==3){
            paint.setColor(Color.YELLOW);
            canvas.drawRect(x,targetY-20,x+100,targetY+40,paint);
            x+=700/parent.getFps();
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

    @Override
    public boolean isDone() {
        return mode==4;
    }
}
