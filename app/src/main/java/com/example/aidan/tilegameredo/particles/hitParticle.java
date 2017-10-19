package com.example.aidan.tilegameredo.particles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.aidan.tilegameredo.Game;
import com.example.aidan.tilegameredo.ImageLoader;

import java.util.ArrayList;

public class hitParticle extends Particle {
    private ArrayList<Bitmap> clouds = new ArrayList<>();
    private int angle;
    private Game parent;

    public hitParticle(int x, int y, int angle, Game parent, Context context) {
        super(x, y, 0);
        this.parent = parent;
        this.angle = angle;
        for(int i=1;i<9;i++) {
            clouds.add(Bitmap.createScaledBitmap(ImageLoader.getCloud(i,context),
                    (int)(parent.getPlayingField().height()/parent.getLevelWidth()*parent.getSizeMultiplier()),
                    (int)(parent.getPlayingField().width()/parent.getLevelWidth()*parent.getSizeMultiplier()),
                    false));
        }
    }

    @Override
    public void paint(Canvas canvas, Paint paint) {
        if(super.getTime()>=1) {
            paint.reset();
            canvas.save();
            Bitmap cloud = clouds.get((int) super.getTime() - 1);
            canvas.rotate((angle - 1) * 90, cloud.getWidth() / 2 + super.getX(), cloud.getHeight() / 2 + super.getY());
            canvas.drawBitmap(cloud, super.getX()+cloud.getWidth()/10, super.getY(), paint);
            canvas.restore();
        }
        super.setTime(super.getTime() + 32.0 / parent.getFps());
    }

    @Override
    public boolean isDone() {
        return super.getTime()>8;
    }
}
