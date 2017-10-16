package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class LevelSelector {
    private String tab = "default";
    private static ArrayList<String> levels;
    public static void load(Context context) {
        //each level should be in order of names, with stars and star levels included
        levels = LevelGenerator.getAllCustomLevels();
    }

    public static void draw(Canvas canvas, Paint paint) {
    }

    public static void update() {
    }

    public static void touch(int x, int y) {
    }
}
