package com.example.aidan.tilegameredo;

public class MarketLevel extends Level {
    private int plays;
    public MarketLevel(String level,int plays) {
        super(level);
        this.plays=plays;
    }
    public int getPlays(){
        return plays;
    }
}
