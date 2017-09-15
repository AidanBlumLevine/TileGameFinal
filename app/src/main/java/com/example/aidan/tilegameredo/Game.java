package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;

public class Game {

    private static Rect playingField;
    private static final int fps=30;
    private static int touchX,touchY;

    public static void load(Context context){

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        defaultLevel = settings.getInt("defaultLevel", 1);
        customLevel = settings.getInt("customLevel", 1);
        maxLevel = settings.getInt("maxLevel", 1);
        firstPlay = settings.getBoolean("firstPlay",true);

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        playingField = new Rect(40, (height - width + 2 * 40) / 2, width - 40, (height + width - 2 * 40) / 2);

        levelGen = new LevelGenerator();
        sideBar = new Menu(playingField,width,height,context);

        status = "playing";
        //CHECK IF THIS IS RIGHT THIBNG TO DO
        playAgain();
    }

    public static void draw(Canvas canvas, Paint paint){

    }

    public static void update(){
        for (Tile t : tiles) {
            t.update();
        }
    }

    public static void swipe(){

    }

    public static boolean isSolidTile(int x, int y) {
        for (Tile t : tiles) {
            if (!(t instanceof Spike) && t.getX() == x && t.getY() == y || (t instanceof DoubleCrate && ((DoubleCrate) t).getPosition() == 1 && t.getX() + 30 == x && t.getY() == y) || (t instanceof DoubleCrate && ((DoubleCrate) t).getPosition() == 2 && t.getX() == x && t.getY() + 30 == y)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTile(int x, int y, Class tileType) {
        for (Tile t : tiles) {
            if (!(t instanceof Spike) && tileType.isInstance(t) && (t.getX() == x && t.getY() == y || (t instanceof DoubleCrate && ((DoubleCrate) t).getPosition() == 1 && t.getX() + 30 == x && t.getY() == y) || (t instanceof DoubleCrate && ((DoubleCrate) t).getPosition() == 2 && t.getX() == x && t.getY() + 30 == y))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTileBesides(int x, int y, Class tileType) {
        for (Tile t : tiles) {
            if (!(t instanceof Spike) && !tileType.isInstance(t) && (t.getX() == x && t.getY() == y || (t instanceof DoubleCrate && ((DoubleCrate) t).getPosition() == 1 && t.getX() + 30 == x && t.getY() == y) || (t instanceof DoubleCrate && ((DoubleCrate) t).getPosition() == 2 && t.getX() == x && t.getY() + 30 == y))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSpike(int x, int y) {
        for (Tile t : tiles) {
            if (t instanceof Spike && t.getX() == x && t.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public static boolean tilesMoving() {
        for (Tile t : tiles) {
            if (t.isMoving()) {
                return true;
            }
        }
        return false;
    }

    public static void levelComplete(int x, int y, int size) {
        if (status.equals("playing")) {
            status = "over";
            if(levelPack.equals("default")){
                defaultLevel++;
            } else {
                customLevel++;
            }
            Overlay.setTarget(x, y,size);
        }

    }

    public static void playAgain() {
        tiles.clear();
        if(levelPack.equals("default")) {
            tiles.addAll(levelGen.getLevel(defaultLevel, context));
        } else {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            String levelString = settings.getString("customlevel"+customLevel, "");
            if(!levelString.equals("")){
                tiles.addAll(LevelGenerator.decodeLevel(levelString));
            } else {
                customLevel=Math.max(customLevel-1,0);
                if(customLevel>0){
                    playAgain();
                }
            }
        }

    }

    private static void tileSort(String sort) {
        if (sort.equals("Right")) {
            for (int i = 1; i < tiles.size(); i++) {
                for (int k = 0; k < i; k++) {
                    if (tiles.get(i).getX() > tiles.get(k).getX()) {
                        Tile saleShifted = tiles.get(i);
                        for (int j = i; j > k; j--) {
                            tiles.set(j, tiles.get(j - 1));
                        }
                        tiles.set(k, saleShifted);
                    }
                }
            }
        } else if (sort.equals("Left")) {
            for (int i = 1; i < tiles.size(); i++) {
                for (int k = 0; k < i; k++) {
                    if (tiles.get(i).getX() < tiles.get(k).getX()) {
                        Tile saleShifted = tiles.get(i);
                        for (int j = i; j > k; j--) {
                            tiles.set(j, tiles.get(j - 1));
                        }
                        tiles.set(k, saleShifted);
                    }
                }
            }
        } else if (sort.equals("Up")) {
            for (int i = 1; i < tiles.size(); i++) {
                for (int k = 0; k < i; k++) {
                    if (tiles.get(i).getY() < tiles.get(k).getY()) {
                        Tile saleShifted = tiles.get(i);
                        for (int j = i; j > k; j--) {
                            tiles.set(j, tiles.get(j - 1));
                        }
                        tiles.set(k, saleShifted);
                    }
                }
            }
        } else if (sort.equals("Down")) {
            for (int i = 1; i < tiles.size(); i++) {
                for (int k = 0; k < i; k++) {
                    if (tiles.get(i).getY() > tiles.get(k).getY()) {
                        Tile saleShifted = tiles.get(i);
                        for (int j = i; j > k; j--) {
                            tiles.set(j, tiles.get(j - 1));
                        }
                        tiles.set(k, saleShifted);
                    }
                }
            }
        } else {
            System.out.println("Unknown Sort");
        }
    }

    public static String getStatus() {
        return status;
    }

    public static void setLevelWidth(int levelSize) {
        levelWidth = levelSize;
    }

    public static void setStatus(String string) {
        status = string;
    }

    public static double getSizeMultiplier() {
        return sizeMultiplier;
    }

    public static Rect getPlayingField() {
        return playingField;
    }

    public static int getTilesInLevel() {
        return numberOfTilesInLevel;
    }

    public static int getTouchX() {
        return touchX;
    }

    public static int getTouchY() {
        return touchY;
    }

    public static void levelChange(int i) {
        if(levelPack.equals("default")) {
            if (defaultLevel + i <= maxLevel) {
                defaultLevel += i;
            }
        } else {
            customLevel += i;
        }
    }

    public static int getMaxLevel(){
        return maxLevel;
    }

    public static int getFPS() {
        return fps;
    }

    public static boolean firstPlay() {
        return firstPlay;
    }

}
