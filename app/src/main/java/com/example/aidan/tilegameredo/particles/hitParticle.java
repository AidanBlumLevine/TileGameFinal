package com.example.aidan.tilegameredo.particles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.aidan.tilegameredo.Game;
import com.example.aidan.tilegameredo.ImageLoader;

import java.util.ArrayList;

public class hitParticle extends Particle {
    private ArrayList<Bitmap> clouds = new ArrayList<>();
    private int angle;
    public hitParticle(int x, int y,int angle) {
        super(x, y, 0);
        this.angle = angle;
        for(int i=1;i<9;i++) {
            clouds.add(Bitmap.createScaledBitmap(ImageLoader.getCloud(i),
                    (int)(Game.getPlayingField().height()/Game.getLevelWidth()*Game.getSizeMultiplier()),
                    (int)(Game.getPlayingField().width()/Game.getLevelWidth()*Game.getSizeMultiplier()),
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
        super.setTime(super.getTime() + 32.0 / Game.getFps());
    }

    @Override
    public boolean isDone() {
        return super.getTime()>8;
    }
}
