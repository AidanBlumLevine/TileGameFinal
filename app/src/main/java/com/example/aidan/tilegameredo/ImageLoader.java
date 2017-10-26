package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;

public class ImageLoader {
    private static Bitmap glowCenter,background,defaultEnd,customEnd,
            boxImg,crateImg,wallImg,spikeImg,emptyCrateImg,
            doubleCrateImg,doubleCrateImg2,buttonReset,buttonRight,
            buttonLeft,buttonTopBack,buttonTrash,buttonSave,buttonSizeUp,buttonSizeDown,
            goldCrate,silverCrate,bronzeCrate,emptyStarCrate,buttonPlay,buttonWideBlank,
            buttonShare,buttonMenu;
    private static ArrayList<Bitmap> clouds;

    public static Bitmap getGlowCenter(Context context) {
        if(glowCenter == null){
            glowCenter = BitmapFactory.decodeResource(context.getResources(), R.drawable.glowcenter);
        }
        return glowCenter;
    }

    public static Bitmap getCloud(int i,Context context) {
        if (clouds == null) {
            clouds = new ArrayList<>();
            for (int n = 1; n < 9; n++) {
                int resId = context.getResources().getIdentifier("cloud" + n, "drawable", context.getPackageName());
                clouds.add(BitmapFactory.decodeResource(context.getResources(), resId));
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

    public static Bitmap getButtonReset(Context context) {
        if (buttonReset == null) {
            buttonReset = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonblank);
        }
        return buttonReset;
    }

    public static Bitmap getButtonRight(Context context) {
        if (buttonRight == null) {
            buttonRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonright);
        }
        return buttonRight;
    }

    public static Bitmap getButtonLeft(Context context) {
        if (buttonLeft == null) {
            buttonLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonleft);
        }
        return buttonLeft;
    }

    public static Bitmap getButtonBack(Context context) {
        if (buttonTopBack == null) {
            buttonTopBack = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonup);
        }
        return buttonTopBack;
    }

    public static Bitmap getButtonTrash(Context context) {
        if (buttonTrash == null) {
            buttonTrash = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonblank);
        }
        return buttonTrash;
    }

    public static Bitmap getButtonSave(Context context) {
        if (buttonSave == null) {
            buttonSave = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonblank);
        }
        return buttonSave;
    }

    public static Bitmap getButtonSizeUp(Context context) {
        if (buttonSizeUp == null) {
            buttonSizeUp = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonblank);
        }
        return buttonSizeUp;
    }

    public static Bitmap getButtonSizeDown(Context context) {
        if (buttonSizeDown == null) {
            buttonSizeDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonblank);
        }
        return buttonSizeDown;
    }

    public static Bitmap getGoldCrate(Context context) {
        if (goldCrate == null) {
            goldCrate = BitmapFactory.decodeResource(context.getResources(), R.drawable.goldcrate);
        }
        return goldCrate;
    }

    public static Bitmap getSilverCrate(Context context) {
        if (silverCrate == null) {
            silverCrate = BitmapFactory.decodeResource(context.getResources(), R.drawable.silvercrate);
        }
        return silverCrate;
    }

    public static Bitmap getBronzeCrate(Context context) {
        if (bronzeCrate == null) {
            bronzeCrate = BitmapFactory.decodeResource(context.getResources(), R.drawable.bronzecrate);
        }
        return bronzeCrate;
    }

    public static Bitmap getEmptyStarCrate(Context context) {
        if (emptyStarCrate == null) {
            emptyStarCrate = BitmapFactory.decodeResource(context.getResources(), R.drawable.emptystarcrate);
        }
        return emptyStarCrate;
    }

    public static Bitmap getButtonPlay(Context context) {
        if (buttonPlay == null) {
            buttonPlay = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonblank);
        }
        return buttonPlay;
    }

    public static Bitmap getButtonWideBlank(Context context) {
        if (buttonWideBlank == null) {
            buttonWideBlank = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonblank);
        }
        return buttonWideBlank;
    }

    public static Bitmap getButtonShare(Context context) {
        if (buttonShare == null) {
            buttonShare = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonblank);
        }
        return buttonShare;

    }

    public static Bitmap getButtonMenu(Context context) {
        if (buttonMenu == null) {
            buttonMenu = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonblank);
        }
        return buttonMenu;

    }
}
