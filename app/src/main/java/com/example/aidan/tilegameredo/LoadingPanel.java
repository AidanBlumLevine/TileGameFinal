package com.example.aidan.tilegameredo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.SurfaceView;

public class LoadingPanel  extends SurfaceView implements Runnable{
    private Context context;
    private Thread UIThread;
    public LoadingPanel(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    public void run() {
        Log.e("STartLoading","StartLoading");
        new Thread(new Runnable() {
            @Override
            public void run() {
                load();
                Log.e("DoneLoading","DoneLoading");
                Intent i = new Intent(context,HomeScreen.class);
                context.startActivity(i);
                ((Activity)context).overridePendingTransition(R.anim.down_to_mid,R.anim.mid_to_up);
            }
        }).start();
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

        Loader.loadDefaultLevels(context);
        Loader.loadCustomLevels(context);

    }

    public void resume() {
        UIThread = new Thread(this);
        UIThread.start();
    }

    public void pause() {
        try {
            UIThread.join();
        } catch (Exception e) {}
    }
}
