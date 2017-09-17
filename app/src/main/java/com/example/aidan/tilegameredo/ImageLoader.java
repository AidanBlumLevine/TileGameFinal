package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class ImageLoader {
    private static Bitmap glowCenter,background,defaultEnd,customEnd,boxImg,crateImg,wallImg,spikeImg,emptyCrateImg,doubleCrateImg,doubleCrateImg2;
    private static ArrayList<Bitmap> clouds;

    public static Bitmap getGlowCenter(Context context) {
        if(glowCenter == null){
            glowCenter = BitmapFactory.decodeResource(context.getResources(), R.drawable.glowcenter);
        }
        return glowCenter;
    }

    public static Bitmap getCloud(int i) {
        if (clouds == null) {
            clouds = new ArrayList<>();
            for (int n = 1; n < 9; n++) {
                int resId = Game.getContext().getResources().getIdentifier("cloud" + n, "drawable", Game.getContext().getPackageName());
                clouds.add(BitmapFactory.decodeResource(Game.getContext().getResources(), resId));
            }
        }
        return clouds.get(i - 1);
    }

    public static Bitmap getBackground(Context context) {
        if(background == null) {
            background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.backround5), Math.max(Resources.getSystem().getDisplayMetrics().heightPixels,Resources.getSystem().getDisplayMetrics().widthPixels) + 300, Math.max(Resources.getSystem().getDisplayMetrics().heightPixels,Resources.getSystem().getDisplayMetrics().widthPixels) + 300, false);
        }
        return background;
    }

    public static Bitmap getCustomEnd(Context context) {
        if (customEnd == null) {
            customEnd = BitmapFactory.decodeResource(context.getResources(), R.drawable.customend);
        }
        return customEnd;
    }

    public static Bitmap getDefaultEnd(Context context) {
        if (defaultEnd == null) {
            defaultEnd = BitmapFactory.decodeResource(context.getResources(), R.drawable.defaultend);
        }
        return defaultEnd;
    }

    public static Bitmap getBoxImage(Context context) {
        if (boxImg == null) {
            boxImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.boxpixelated);
        }
        return boxImg;
    }

    public static Bitmap getCrateImage(Context context) {
        if (crateImg == null) {
            crateImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.cratepixelated);
        }
        return crateImg;
    }

    public static Bitmap getDoubleCrateImage(Context context) {
        if (doubleCrateImg == null) {
            doubleCrateImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.doublecrate1pixelated);
        }
        return doubleCrateImg;
    }

    public static Bitmap getDoubleCrate2Image(Context context) {
        if (doubleCrateImg2 == null) {
            doubleCrateImg2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.doublecrate2pixelated);
        }
        return doubleCrateImg2;
    }

    public static Bitmap getSpikeImage(Context context) {
        if (spikeImg == null) {
            spikeImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.spikespixelated);
        }
        return spikeImg;
    }

    public static Bitmap getWallImage(Context context) {
        if (wallImg == null) {
            wallImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.wallpixelated);
        }
        return wallImg;
    }

    public static Bitmap getEmptyCrateImage(Context context) {
        if (emptyCrateImg == null) {
            emptyCrateImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.emptycratepixelated);
        }
        return emptyCrateImg;
    }
}
