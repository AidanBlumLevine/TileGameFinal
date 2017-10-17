package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class LevelSelector {
    private static String tab = "default";
    private static ArrayList<String> levels;
    private static Level selectedLevel;
    private static int scrollPosition;
    private Rect listArea;
    public static void load(Context context) {
        levels = LevelGenerator.getAllLevels(tab);
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static void draw(Canvas canvas, Paint paint) {
    }

    public static void update() {
    }

    public static void touch(int x, int y) {
    }

    public static String getTab() {
        return tab;
    }

    public static Level getLevel() {
        return selectedLevel;
    }
}
