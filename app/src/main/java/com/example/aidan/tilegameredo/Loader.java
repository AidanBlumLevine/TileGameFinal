package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

public class Loader {
    private static Bitmap glowCenter,background,
            boxImg,crateImg,wallImg,spikeImg,emptyCrateImg,
            doubleCrateImg,doubleCrateImg2,buttonReset,buttonRight,
            buttonTopBack,buttonTrash,buttonSave,buttonSizeUp,buttonSizeDown,
            goldCrate,silverCrate,bronzeCrate,emptyStarCrate,buttonPlay,buttonWideBlank,
            buttonShare,buttonMenu,buttonEdit,buttonTopRated,buttonSearch,buttonMostPlayed,
            buttonDownload,buttonNew,truck,truckFull;
    private static ArrayList<Bitmap> clouds;
    private static ArrayList<Level> defaultLevels;
    private static ArrayList<Bitmap> defaultPreviews = new ArrayList<>();
    private static ArrayList<Level> customLevels;
    private static ArrayList<Bitmap> customPreviews = new ArrayList<>();


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
            buttonPlay = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonwideblank);
        }
        return buttonPlay;
    }

    public static Bitmap getButtonWideBlank(Context context) {
        if (buttonWideBlank == null) {
            buttonWideBlank = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonwideblank);
        }
        return buttonWideBlank;
    }

    public static Bitmap getButtonShare(Context context) {
        if (buttonShare == null) {
            buttonShare = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonwideblank);
        }
        return buttonShare;
    }

    public static Bitmap getButtonMenu(Context context) {
        if (buttonMenu == null) {
            buttonMenu = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonblank);
        }
        return buttonMenu;
    }

    public static Bitmap getButtonEdit(Context context) {
        if (buttonEdit == null) {
            buttonEdit = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonblank);
        }
        return buttonEdit;
    }

    public static Bitmap getButtonTopPlayed(Context context) {
        if (buttonTopRated == null) {
            buttonTopRated = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonwideblank);
        }
        return buttonTopRated;
    }

    public static Bitmap getButtonSearch(Context context) {
        if (buttonSearch == null) {
            buttonSearch = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonwideblank);
        }
        return buttonSearch;
    }

    public static Bitmap getButtonMostPlayed(Context context) {
        if (buttonMostPlayed == null) {
            buttonMostPlayed = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonwideblank);
        }
        return buttonMostPlayed;
    }

    public static Bitmap getButtonNew(Context context) {
        if (buttonNew == null) {
            buttonNew = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonwideblank);
        }
        return buttonNew;
    }

    public static Bitmap getButtonDownload(Context context) {
        if (buttonDownload == null) {
            buttonDownload = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttonblank);
        }
        return buttonDownload;
    }

    public static Bitmap getTruck(Context context) {
        if (truck == null) {
            truck = BitmapFactory.decodeResource(context.getResources(), R.drawable.smalltruckempty);
        }
        return truck;
    }
    public static Bitmap getTruckFull(Context context) {
        if (truckFull == null) {
            truckFull = BitmapFactory.decodeResource(context.getResources(), R.drawable.smalltruck);
        }
        return truckFull;
    }

    public static void loadDefaultLevels(Context context) {
        defaultLevels = LevelGenerator.getAllLevels("default",context);
        defaultPreviews.clear();
        //listArea = new Rect(edgeBuffer,tabHeight+(int)(edgeBuffer*1.5),screenWidth-edgeBuffer,screenHeight-edgeBuffer); ===== THIS IS WHERE SCREENWIDTH-2*edgebuffer comes from
        //int imageSize = Math.min((listArea.width()-4*edgeBuffer)/3-levelHeight/3,7*levelHeight/8);
        int edgeBuffer = 20;
        int levelHeight = 200;
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int imageSize = (screenWidth-6*edgeBuffer)/3-levelHeight/4;
        for(int i=0;i<defaultLevels.size();i++){
            defaultPreviews.add(Bitmap.createScaledBitmap(preview(defaultLevels.get(i),true,context),imageSize,imageSize,false));
        }
    }

    public static void loadCustomLevels(Context context) {
        customLevels = LevelGenerator.getAllLevels("custom",context);
        customPreviews.clear();
        int edgeBuffer = 20;
        int levelHeight = 200;
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        //int imageSize = Math.min((screenWidth-6*edgeBuffer)/3-levelHeight/7,7*levelHeight/8);
        int imageSize = (screenWidth-6*edgeBuffer)/3-levelHeight/4;
        for(int i=0;i<customLevels.size();i++){
            customPreviews.add(Bitmap.createScaledBitmap(preview(customLevels.get(i),true,context),imageSize,imageSize,false));
        }
    }

    public static Bitmap preview(Level level,Boolean small,Context context){
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap preview = Bitmap.createBitmap(level.getWidth()*30, level.getWidth()*30, conf);
        Canvas canvas = new Canvas(preview);
        String tiles = level.toString().split("\\|")[4];
        Paint p = new Paint();
        canvas.drawColor(Color.WHITE);
        int tileSize = 29;
        if(small){
            tileSize++;
        }
        for(int i=0;i<tiles.split(":").length;i++){
            if (tiles.split(":")[i].split(",")[0].equals("box")){
                if(small){
                    p.setColor(Color.rgb(255,182,72));
                    canvas.drawRect(Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30, Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30,Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30+ tileSize,Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30+tileSize,p);
                } else {
                    canvas.drawBitmap(Bitmap.createScaledBitmap(Loader.getBoxImage(context), tileSize, tileSize, false), Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30, Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30, p);
                }
            }
            if (tiles.split(":")[i].split(",")[0].equals("crate")) {
                if (small) {
                    p.setColor(Color.rgb(65, 99, 135));
                    canvas.drawRect(Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30, Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30,Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30+ tileSize,Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30+tileSize,p);
                } else {
                    canvas.drawBitmap(Bitmap.createScaledBitmap(Loader.getCrateImage(context), tileSize, tileSize, false), Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30, Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30, p);
                }
            }
            if (tiles.split(":")[i].split(",")[0].equals("emptyCrate")) {
                if (small) {
                    p.setColor(Color.rgb(255, 100, 72));
                    canvas.drawRect(Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30, Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30,Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30+ tileSize,Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30+tileSize,p);
                    p.reset();
                } else {
                    canvas.drawBitmap(Bitmap.createScaledBitmap(Loader.getEmptyCrateImage(context), tileSize, tileSize, false), Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30, Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30, p);
                }
            }
            if (tiles.split(":")[i].split(",")[0].equals("wall")) {
                if(small){
                    p.setColor(Color.rgb(186,186,186));
                    canvas.drawRect(Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30, Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30,Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30+ tileSize,Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30+tileSize,p);
                } else {
                    canvas.drawBitmap(Bitmap.createScaledBitmap(Loader.getWallImage(context), tileSize, tileSize, false), Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30, Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30, p);
                }
            }
            if (tiles.split(":")[i].split(",")[0].equals("doubleCrate") && Integer.valueOf(tiles.split(":")[i].split(",")[3]) == 1) {
                if(small){
                    p.setColor(Color.rgb(65, 99, 135));
                    canvas.drawRect(Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30, Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30,Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30+ tileSize*2,Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30+tileSize,p);
                } else {
                    canvas.drawBitmap(Bitmap.createScaledBitmap(Loader.getDoubleCrateImage(context), tileSize * 2, tileSize, false), Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30, Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30, p);
                }
            }
            if (tiles.split(":")[i].split(",")[0].equals("doubleCrate") && Integer.valueOf(tiles.split(":")[i].split(",")[3]) == 2) {
                if(small){
                    p.setColor(Color.rgb(65, 99, 135));
                    canvas.drawRect(Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30, Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30,Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30+ tileSize,Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30+tileSize*2,p);
                } else {
                    canvas.drawBitmap(Bitmap.createScaledBitmap(Loader.getDoubleCrate2Image(context), tileSize, tileSize * 2, false), Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30, Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30, p);
                }
            }
            if (tiles.split(":")[i].split(",")[0].equals("spike")) {
                if(small){
                    p.setColor(Color.LTGRAY);
                    canvas.drawRect(Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30, Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30,Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30+ tileSize,Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30+tileSize,p);
                } else {
                    canvas.save();
                    canvas.rotate((Integer.valueOf(tiles.split(":")[i].split(",")[3]) - 1) * 90, Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30 + 15, Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30 + 15);
                    canvas.drawBitmap(Bitmap.createScaledBitmap(Loader.getSpikeImage(context), tileSize, tileSize, false), Integer.valueOf(tiles.split(":")[i].split(",")[1]) * 30, Integer.valueOf(tiles.split(":")[i].split(",")[2]) * 30, p);
                    canvas.restore();
                }
            }
            p.setColor(Color.argb(50,134,134,134));
            for(int x=0;x<level.getWidth();x++){
                canvas.drawLine(x*30,0,x*30,level.getWidth()*30,p);
            }
            for(int y=0;y<level.getWidth();y++){
                canvas.drawLine(0,y*30,level.getWidth()*30,y*30,p);
            }
        }
        return preview;
    }

    public static ArrayList<Bitmap> getDefaultPreviews() {
        return defaultPreviews;
    }
    public static ArrayList<Bitmap> getCustomPreviews() {
        return customPreviews;
    }

    public static ArrayList<Level> getDefaultLevels() {
        return defaultLevels;
    }
    public static ArrayList<Level> getCustomLevels() {
        return customLevels;
    }
}
