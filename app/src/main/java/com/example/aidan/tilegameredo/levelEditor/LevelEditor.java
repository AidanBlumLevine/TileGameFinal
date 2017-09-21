package com.example.aidan.tilegameredo.levelEditor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;

import com.example.aidan.tilegameredo.ImageLoader;
import com.example.aidan.tilegameredo.LevelGenerator;
import com.example.aidan.tilegameredo.Tile;
import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbDoubleCrate;

import java.util.ArrayList;

public class LevelEditor {
    private static final int fps=90;
    private final static double sizeMultiplier = 0.97;

    private static int levelWidth = 12;
    private static int touchX,touchY;

    private static ArrayList<Tile> tiles = new ArrayList<>();
    private static EditorMenu editorMenu;
    private static Rect playingField;
    private static Context context;


    public static void load(Context context){
        LevelEditor.context=context;

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int buffer = 40;

        playingField = new Rect(buffer, (height - width + 2 * buffer) / 2, width - buffer, (height + width - 2 * buffer) / 2);

        editorMenu = new EditorMenu(context);
        if(tiles.isEmpty()){
            editorMenu.generateBorder();
        }

    }

    public static int getFps() {
        return fps;
    }

    public static boolean isTile(int x, int y) {
        for (Tile t : tiles) {
            if (t.getX() == x && t.getY() == y || (t instanceof DumbDoubleCrate && ((DumbDoubleCrate) t).getPosition() == 1 && t.getX() + 30 == x && t.getY() == y) || (t instanceof DumbDoubleCrate && ((DumbDoubleCrate) t).getPosition() == 2 && t.getX() == x && t.getY() + 30 == y)) {
                return true;
            }
        }
        return false;
    }

    public static double getSizeMultiplier() {
        return sizeMultiplier;
    }

    public static Rect getPlayingField() {
        return playingField;
    }

    public static int getTilesInLevel() {
        return levelWidth;
    }

    public static int getTouchX() {
        return touchX;
    }

    public static int getTouchY() {
        return touchY;
    }

    public static int getLevelWidth() {
        return levelWidth;
    }

    public static void addTile(Tile t) {
        tiles.add(t);
    }

    public static Tile getTileAt(int x, int y) {
        for(Tile t:tiles){
            if(t.getX() ==x && t.getY()==y){
                return t;
            }
        }
        return null;
    }

    public static void removeTile(int x, int y) {
        for(int t=0;t<tiles.size();t++){
            if(tiles.get(t).getX()==x && tiles.get(t).getY()==y){
                tiles.remove(t);
                t--;
            }
        }
    }

    public static void save(){
        String levelString = LevelGenerator.encodeLevel(tiles,editorMenu.getSize());
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        int levelNumber = getNextLevelId();
        editor.putString("customlevel"+levelNumber, levelString);
        editor.putInt("customLevel",levelNumber);
        editor.commit();
    }

    public static int getNextLevelId() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        int i=1;
        while(!settings.getString("customlevel"+i, "").equals("")){
            i++;
        }
        return i;
    }

    public static void changeSize(int i) {
        levelWidth=Math.max(3,levelWidth+i);
    }

    public static void touch(int x, int y, int type) {
        if(type==-1){
            editorMenu.released();
        }
        touchX=x;
        touchY=y;
        if(type==1){
            editorMenu.pressed();
        }
    }

    public static void update() {
        try {
            for (Tile t : tiles) {
                t.update();
            }
        } catch (Exception e){}
    }

    public static void draw(Canvas canvas, Paint paint,Context context) {
        canvas.drawColor(Color.WHITE);
        paint.setAlpha(80);
        canvas.drawBitmap(ImageLoader.getBackground(context),-50,-30,paint);
        paint.setARGB(180,255,255,255);
        canvas.drawRect(playingField.left-10,playingField.top-10,playingField.right+10,playingField.bottom+10,paint);
        paint.reset();
        editorMenu.paint(canvas,paint);
        paint.reset();
        canvas.save();
        canvas.translate((float)(playingField.width()/editorMenu.getSize()*(1-sizeMultiplier))/2,(float)(playingField.width()/editorMenu.getSize()*(1-sizeMultiplier))/2);
        try {
            for (Tile t : tiles) {
                t.paint(canvas, paint);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        canvas.restore();
    }
}
