package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.aidan.tilegameredo.particles.Particle;
import com.example.aidan.tilegameredo.particles.endParticle;
import com.example.aidan.tilegameredo.particles.fadeParticle;
import com.example.aidan.tilegameredo.particles.starsParticle;
import com.example.aidan.tilegameredo.tiles.Box;
import com.example.aidan.tilegameredo.tiles.Crate;
import com.example.aidan.tilegameredo.tiles.DoubleCrate;
import com.example.aidan.tilegameredo.tiles.EmptyCrate;
import com.example.aidan.tilegameredo.tiles.Spike;
import com.example.aidan.tilegameredo.tiles.Wall;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class Game {

    private static final int fps=100;
    private final static double sizeMultiplier = 0.97;

    private static int touchX,touchY,defaultLevel,customLevel,maxLevel,levelWidth=1,swipes,leastSwipes,stars;
    private static boolean firstPlay,playing;

    private static String levelPack = "default";
    private static Rect playingField;
    private static LevelGenerator levelGen;
    private static Menu menu;
    private static ArrayList<Tile> tiles = new ArrayList<>();
    private static Context context;
    private static int[] starLevels= new int[3];

    public static void load(Context context){
        Game.context = context;

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        defaultLevel = settings.getInt("defaultLevel", 1);
        customLevel = settings.getInt("customLevel", 1);
        maxLevel = settings.getInt("maxLevel", 1);
        firstPlay = settings.getBoolean("firstPlay",true);

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        playingField = new Rect(40, (height - width + 2 * 40) / 2, width - 40, (height + width - 2 * 40) / 2);

        levelGen = new LevelGenerator(context);
        menu = new Menu(playingField,width,height,context);

        playAgain();
    }

    public static void draw(Canvas canvas, Paint paint){
        canvas.drawColor(Color.WHITE);
        paint.setAlpha(80);
        canvas.drawBitmap(ImageLoader.getBackground(context),-30,-50,paint);

        paint.setARGB(180,255,255,255);
        canvas.drawRect(playingField.left-10,playingField.top-10,playingField.right+10,playingField.bottom+10,paint);
        paint.reset();

        menu.paint(canvas, paint);
        paint.reset();

        canvas.save();
        canvas.translate((float)(playingField.width()/levelWidth*(1-sizeMultiplier))/2,(float)(playingField.width()/levelWidth*(1-sizeMultiplier))/2);
        for (int i = 0; i < tiles.size(); i++) {
            if (!(tiles.get(i) instanceof EmptyCrate) && !(tiles.get(i) instanceof Wall)) {
                tiles.get(i).paint(canvas, paint);
            }
            if (!tilesMoving() && tiles.get(i).isDead()) {
                tiles.remove(i);
                i--;
            }
        }
        for (Tile t : tiles) {
            if (t instanceof EmptyCrate || t instanceof Wall) {
                t.paint(canvas, paint);
            }
        }
        paint.reset();
        ParticleManager.paint(canvas, paint);
        canvas.restore();
        paint.reset();

        if(tiles.isEmpty()){
            if(levelPack.equals("default")){
                canvas.drawBitmap(Bitmap.createScaledBitmap(ImageLoader.getDefaultEnd(context),playingField.width(),playingField.height(),false),playingField.left,playingField.top,paint);
            } else {
                canvas.drawBitmap(Bitmap.createScaledBitmap(ImageLoader.getCustomEnd(context),playingField.width(),playingField.height(),false),playingField.left,playingField.top,paint);
            }
        }

    }

    public static void update(){
        for (Tile t : tiles) {
            t.update();
        }
    }

    public static void swipe(int direction){
        //1 ^ 2> 3\  4<
        if(playing) {
            swipes++;
            if (direction == 1) {
                if (!tilesMoving()) {
                    tileSort("Up");
                    for (Tile t : tiles) {
                        t.pushUp();
                    }
                }
            }
            if (direction == 4) {
                if (!tilesMoving()) {
                    tileSort("Left");
                    for (Tile t : tiles) {
                        t.pushLeft();
                    }
                }
            }
            if (direction == 2) {
                if (!tilesMoving()) {
                    if (firstPlay) {
                        firstPlay = false;
                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("firstPlay", false);
                        editor.commit();
                    }
                    tileSort("Right");
                    for (Tile t : tiles) {
                        t.pushRight();
                    }
                }
            }

            if (direction == 3) {
                if (!tilesMoving()) {
                    tileSort("Down");
                    for (Tile t : tiles) {
                        t.pushDown();
                    }
                }
            }
        }
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


        playing = false;
        if(levelPack.equals("default")){
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = settings.edit();

            if(swipes<leastSwipes) {
                editor.putInt("leastSwipes" + defaultLevel, swipes);
                leastSwipes=swipes;
            }
            defaultLevel++;
            if(maxLevel<defaultLevel){
                maxLevel=defaultLevel;
                editor.putInt("maxLevel", maxLevel);
            }
            editor.commit();
        } else {
            customLevel++;
        }
        endParticle f = new endParticle(x,y,size);
    }

    public static void playAgain() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        playing = true;
        tiles.clear();
        if(levelPack.equals("default")) {
            tiles.addAll(levelGen.getLevel(defaultLevel, context));
        } else {
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
        if(levelPack.equals("default")) {
            leastSwipes = settings.getInt("leastSwipes"+defaultLevel, 1000);
        }
        updateStars();
        swipes = 0;
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

    public static void setLevelWidth(int levelSize) {
        levelWidth = levelSize;
    }

    public static double getSizeMultiplier() {
        return sizeMultiplier;
    }

    public static Rect getPlayingField() {
        return playingField;
    }

    public static int getLevelWidth() {
        return levelWidth;
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

    public static void touch(int x, int y) {
        if(x==-1 && y==-1){
            menu.released();
        }
        touchX =x;
        touchY =y;
    }

    public static int getFps() {
        return fps;
    }

    public static void setLevelPack(String levelPack) {
        Game.levelPack = levelPack;
    }

    public static int getDefaultLevel() {
        return defaultLevel;
    }

    public static int getCustomLevel() {
        return customLevel;
    }

    public static Context getContext() {
        return context;
    }

    public static boolean isPlaying() {
        return playing;
    }

    public static String getLevelPack() {
        return levelPack;
    }

    public static int getLevel() {
        if(levelPack.equals("default")){
            return defaultLevel;
        }
        return customLevel;
    }

    public static int getNextLevelId() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        int i=1;
        while(!settings.getString("customlevel"+i, "").equals("")){
            i++;
        }
        return i;
    }

    public static void setPlaying(boolean playing) {
        Game.playing = playing;
    }

    public static void setStarLevels(int[] starLevels) {
        Game.starLevels = starLevels;
    }

    public static int getStars() {
        return stars;
    }

    public static void updateStars(){
        if(leastSwipes<=starLevels[0]){
            stars=3;
        } else if(leastSwipes<=starLevels[1]){
            stars=2;
        } else if(leastSwipes<=starLevels[2]){
            stars=1;
        } else {
            stars=0;
        }
    }
}
//make stars part of end{article while solid black

//make end particle only be as big as needed

//add sounds

//old=================================

//name tipping crates ++

//check all grey buttons +

//new font for everything ++

//new arrow +++

