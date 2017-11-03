package com.example.aidan.tilegameredo;

import android.content.Context;

public class MarketLevel extends Level {
    private int plays;
    public MarketLevel(String level,int plays) {
        super(level);
    }
    public int getPlays(){
        return plays;
    }
}
