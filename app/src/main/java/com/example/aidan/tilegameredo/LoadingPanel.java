package com.example.aidan.tilegameredo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.aidan.tilegameredo.particles.driftParticle;

import java.util.ArrayList;

public class LoadingPanel  extends SurfaceView implements Runnable{
    private Context context;
    private Thread UIThread;
    private Boolean loading;
    private SurfaceHolder surfaceHolder;
    private android.graphics.Canvas canvas;
    private Paint paint;
    private int width,height;
    private double angle=0;
    private Bitmap truck,crate;
    private ArrayList<driftParticle> particles = new ArrayList<>();
    public LoadingPanel(Context context) {
        super(context);
        this.context=context;
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        surfaceHolder = getHolder();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        height = Resources.getSystem().getDisplayMetrics().heightPixels;
        width = Resources.getSystem().getDisplayMetrics().widthPixels;
        Bitmap tempTruck = BitmapFactory.decodeResource(context.getResources(), R.drawable.trucktop);
        truck = Bitmap.createScaledBitmap(tempTruck,width/3,width*tempTruck.getHeight()/tempTruck.getWidth()/3, false);
        crate = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.cratepixelated),width/10,width/10,false);
    }

    @Override
    public void run() {
        Log.e("STartLoading","StartLoading");
        new Thread(new Runnable() {
            @Override
            public void run() {
                load();
                loading=false;
                Log.e("DoneLoading","DoneLoading");
                Intent i = new Intent(context,HomeScreen.class);
                context.startActivity(i);
                ((Activity)context).overridePendingTransition(R.anim.down_to_mid,R.anim.mid_to_up);

            }
        }).start();
        while(loading){
            draw();
        }
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            paint.setColor(Color.WHITE);
            canvas.drawRect(0,0,width,height,paint);
            canvas.save();
            canvas.rotate((float)angle,width/2,height/2);
            canvas.drawBitmap(truck,width/2-truck.getWidth(),height/2-truck.getHeight()/2-width/4,paint);
            angle+=3;
            canvas.restore();
            particles.add(new driftParticle(width/2-truck.getWidth(),height/2-truck.getHeight()/2-width/4,(int)(Math.random()*30),(int)(Math.random()*10),angle));
            Log.e(particles.size()+"",particles.size()+"");
            for(int i=0;i<particles.size();i++){
                if(particles.get(i).getSize()<0){
                    particles.remove(i);
                    i--;
                } else {
                    particles.get(i).draw(canvas,paint);
                }
            }
            canvas.drawBitmap(crate,(width-crate.getWidth())/2,(height-crate.getHeight())/2,paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void load() {
        Loader.getBackground(context);
        Loader.getBoxImage(context);
        Loader.getBronzeCrate(context);
        Loader.getButtonBack(context);
        Loader.getButtonDownload(context);
        Loader.getButtonEdit(context);
        Loader.getButtonMenu(context);
        Loader.getButtonMostPlayed(context);
        Loader.getButtonNew(context);
        Loader.getButtonPlay(context);
        Loader.getButtonReset(context);
        Loader.getButtonRight(context);
        Loader.getButtonSave(context);
        Loader.getButtonSearch(context);
        Loader.getButtonShare(context);
        Loader.getButtonSizeDown(context);
        Loader.getButtonSizeUp(context);
        Loader.getButtonTopPlayed(context);
        Loader.getButtonTrash(context);
        Loader.getButtonWideBlank(context);
        for(int i=1;i<9;i++) {
            Loader.getCloud(i,context);
        }
        Loader.getCrateImage(context);
        Loader.getDoubleCrate2Image(context);
        Loader.getDoubleCrateImage(context);
        Loader.getEmptyCrateImage(context);
        Loader.getEmptyStarCrate(context);
        Loader.getGlowCenter(context);
        Loader.getGoldCrate(context);
        Loader.getSilverCrate(context);
        Loader.getSpikeImage(context);
        Loader.getTruck(context);
        Loader.getTruckFull(context);
        Loader.getWallImage(context);

        Loader.getLock(context);

        Loader.loadDefaultLevels(context);
        Loader.loadCustomLevels(context);

    }

    public void resume() {
        UIThread = new Thread(this);
        UIThread.start();
        loading= true;
    }

    public void pause() {
        try {
            UIThread.join();
        } catch (Exception e) {}
        loading=false;
    }
}
