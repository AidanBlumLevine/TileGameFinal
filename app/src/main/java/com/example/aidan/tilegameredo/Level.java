package com.example.aidan.tilegameredo;

import java.util.ArrayList;

public class Level {
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private int width,stars;
    private int[] starLevels;
    public String toString(){
        String levelString;
        levelString = stars+"|"+starLevels[0]+","+starLevels[1]+","+starLevels[2]+"|"+size+"|";
        for(Tile t:tiles) {
            if(t instanceof DumbBox)
                levelString += "box,"+t.getX()+","+t.getY()+":";
            if(t instanceof DumbCrate)
                levelString += "crate,"+t.getX()+","+t.getY()+":";
            if(t instanceof DumbEmptyCrate)
                levelString += "emptyCrate,"+t.getX()+","+t.getY()+":";
            if(t instanceof DumbWall)
                levelString += "wall,"+t.getX()+","+t.getY()+":";
            if(t instanceof DumbDoubleCrate)
                levelString += "doubleCrate,"+t.getX()+","+t.getY()+","+((DumbDoubleCrate) t).getPosition()+":";
            if(t instanceof DumbSpike)
                levelString += "spike,"+t.getX()+","+t.getY()+","+((DumbSpike) t).getPosition()+":";
        }
        return levelString;
    }
}
