package com.example.aidan.tilegameredo.levelEditor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;

import com.example.aidan.tilegameredo.Loader;
import com.example.aidan.tilegameredo.Level;
import com.example.aidan.tilegameredo.LevelGenerator;
import com.example.aidan.tilegameredo.Tile;
import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbDoubleCrate;

import java.util.ArrayList;

public class LevelEditor {
    private final int fps=100;
    private final double sizeMultiplier = 0.97;

    private int levelWidth = 12;
    private int touchX,touchY;

    private ArrayList<Tile> tiles = new ArrayList<>();
    private EditorMenu editorMenu;
    private Rect playingField;
    private Context context;


    public LevelEditor(Context context){
        this.context=context;

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int buffer = 40;

        playingField = new Rect(buffer, (height - width + 2 * buffer) / 2, width - buffer, (height + width - 2 * buffer) / 2);

        editorMenu = new EditorMenu(context,this);
        if(tiles.isEmpty()){
            editorMenu.generateBorder();
        }

    }

    public LevelEditor(Context context, String level) {
        this.context=context;

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int buffer = 40;

        playingField = new Rect(buffer, (height - width + 2 * buffer) / 2, width - buffer, (height + width - 2 * buffer) / 2);

        levelWidth = new Level(level).getWidth();
        tiles = new Level(level).getDumbTiles(context,this);
        editorMenu = new EditorMenu(context,this);
    }

    public int getFps() {
        return fps;
    }

    public boolean isTile(int x, int y) {
        for (Tile t : tiles) {
            if (t.getX() == x && t.getY() == y || (t instanceof DumbDoubleCrate && ((DumbDoubleCrate) t).getPosition() == 1 && t.getX() + 30 == x && t.getY() == y) || (t instanceof DumbDoubleCrate && ((DumbDoubleCrate) t).getPosition() == 2 && t.getX() == x && t.getY() + 30 == y)) {
                return true;
            }
        }
        return false;
    }

    public double getSizeMultiplier() {
        return sizeMultiplier;
    }

    public Rect getPlayingField() {
        return playingField;
    }

    public int getTilesInLevel() {
        return levelWidth;
    }

    public int getTouchX() {
        return touchX;
    }

    public int getTouchY() {
        return touchY;
    }

    public int getLevelWidth() {
        return levelWidth;
    }

    public void addTile(Tile t) {
        tiles.add(t);
    }

    public Tile getTileAt(int x, int y) {
        for(Tile t:tiles){
            if(t.getX() ==x && t.getY()==y){
                return t;
            }
        }
        return null;
    }

    public void removeTile(int x, int y) {
        for(int t=0;t<tiles.size();t++){
            if(tiles.get(t).getX()==x && tiles.get(t).getY()==y){
                tiles.remove(t);
                t--;
            }
        }
    }

    public void save(String name){
        String levelString = LevelGenerator.encodeLevel(tiles,editorMenu.getSize(),0,new int[]{0,0,0},name);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("customLevelNames",settings.getString("customLevelNames","")+name+",");
        editor.putString(name+"custom",levelString);
        editor.commit();
        Loader.loadCustomLevels(context);
    }

    public void changeSize(int i) {
        levelWidth=Math.min(25,Math.max(3,levelWidth+i));
    }

    public void touch(int x, int y, int type) {
        if(type==-1){
            editorMenu.released();
        }
        touchX=x;
        touchY=y;
        if(type==1){
            editorMenu.pressed();
        }
    }

    public void update() {
            for (Tile t : tiles) {
                t.update();
            }
    }

    public void draw(Canvas canvas, Paint paint,Context context) {
        canvas.drawColor(Color.WHITE);
        paint.setAlpha(80);
        canvas.drawBitmap(Loader.getBackground(context),-50,-30,paint);
        paint.setARGB(180,255,255,255);
        canvas.drawRect(playingField.left-10,playingField.top-10,playingField.right+10,playingField.bottom+10,paint);
        paint.reset();
        editorMenu.paint(canvas,paint);
        paint.reset();
        canvas.save();
        canvas.translate((float)(playingField.width()/editorMenu.getSize()*(1-sizeMultiplier))/2,(float)(playingField.width()/editorMenu.getSize()*(1-sizeMultiplier))/2);
        //try {
            for (Tile t : tiles) {
                t.paint(canvas, paint);
            }
        //}catch(Exception e){
        //    e.printStackTrace();
        //}
        canvas.restore();
    }
}
