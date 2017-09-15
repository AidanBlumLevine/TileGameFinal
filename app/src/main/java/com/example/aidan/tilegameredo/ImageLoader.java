package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class ImageLoader {
    public static Bitmap glowCenter,background,defaultEnd,customEnd;
    public static ArrayList<Bitmap> clouds;

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
}
