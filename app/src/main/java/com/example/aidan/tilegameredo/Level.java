package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.aidan.tilegameredo.tiles.Box;
import com.example.aidan.tilegameredo.tiles.Crate;
import com.example.aidan.tilegameredo.tiles.DoubleCrate;
import com.example.aidan.tilegameredo.tiles.EmptyCrate;
import com.example.aidan.tilegameredo.tiles.Spike;
import com.example.aidan.tilegameredo.tiles.Wall;

import java.util.ArrayList;

public class Level {
    private String level;
    private Context context;
    public Level(Context context,String name){
        this.context=context;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        level = settings.getString(name, "");
    }
    public String toString() {
        return level;
    }
    public ArrayList<Tile> getTiles() {
        String tiles = level.split("\\|")[4];
        ArrayList<Tile> levelTiles = new ArrayList<Tile>();
        for(int i=0;i<tiles.split(":").length;i++){
            if(tiles.split(":")[2]!=null) {
                if (tiles.split(":")[i].split(",")[0].equals("box"))
                    levelTiles.add(new Box(Integer.valueOf(tiles.split(":")[i].split(",")[1]), Integer.valueOf(tiles.split(":")[i].split(",")[2]), ImageLoader.getBoxImage(context)));
                if (tiles.split(":")[i].split(",")[0].equals("crate"))
                    levelTiles.add(new Crate(Integer.valueOf(tiles.split(":")[i].split(",")[1]), Integer.valueOf(tiles.split(":")[i].split(",")[2]), ImageLoader.getCrateImage(context)));
                if (tiles.split(":")[i].split(",")[0].equals("emptyCrate"))
                    levelTiles.add(new EmptyCrate(Integer.valueOf(tiles.split(":")[i].split(",")[1]), Integer.valueOf(tiles.split(":")[i].split(",")[2]), ImageLoader.getEmptyCrateImage(context)));
                if (tiles.split(":")[i].split(",")[0].equals("wall"))
                    levelTiles.add(new Wall(Integer.valueOf(tiles.split(":")[i].split(",")[1]), Integer.valueOf(tiles.split(":")[i].split(",")[2]), ImageLoader.getWallImage(context)));
                if (tiles.split(":")[i].split(",")[0].equals("doubleCrate") && Integer.valueOf(tiles.split(":")[i].split(",")[3]) == 1)
                    levelTiles.add(new DoubleCrate(Integer.valueOf(tiles.split(":")[i].split(",")[1]), Integer.valueOf(tiles.split(":")[i].split(",")[2]), Integer.valueOf(tiles.split(":")[i].split(",")[3]), ImageLoader.getDoubleCrateImage(context)));
                if (tiles.split(":")[i].split(",")[0].equals("doubleCrate") && Integer.valueOf(tiles.split(":")[i].split(",")[3]) == 2)
                    levelTiles.add(new DoubleCrate(Integer.valueOf(tiles.split(":")[i].split(",")[1]), Integer.valueOf(tiles.split(":")[i].split(",")[2]), Integer.valueOf(tiles.split(":")[i].split(",")[3]), ImageLoader.getDoubleCrate2Image(context)));
                if (tiles.split(":")[i].split(",")[0].equals("spike"))
                    levelTiles.add(new Spike(Integer.valueOf(tiles.split(":")[i].split(",")[1]), Integer.valueOf(tiles.split(":")[i].split(",")[2]), Integer.valueOf(tiles.split(":")[i].split(",")[3]), ImageLoader.getSpikeImage(context)));
            }
        }
        return levelTiles;
    }
    public int getWidth(){
        return Integer.valueOf(level.split("\\|")[3]);
    }
    public int getStars(){
        return Integer.valueOf(level.split("\\|")[1]);
    }
    public int[] getStarLevels(){
        return new int[]{Integer.valueOf(level.split("\\|")[2].split("\\,")[0]),
                        Integer.valueOf(level.split("\\|")[2].split("\\,")[1]),
                        Integer.valueOf(level.split("\\|")[2].split("\\,")[2])};
    }
    public String getName(){
        return level.split("\\|")[0];
    }
}
