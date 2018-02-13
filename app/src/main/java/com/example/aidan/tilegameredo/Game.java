package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.media.SoundPool;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.aidan.tilegameredo.particles.endParticle;
import com.example.aidan.tilegameredo.tiles.DoubleCrate;
import com.example.aidan.tilegameredo.tiles.EmptyCrate;
import com.example.aidan.tilegameredo.tiles.Spike;
import com.example.aidan.tilegameredo.tiles.Wall;

import java.util.ArrayList;

public class Game {

    private final int fps=100 ;
    private final  double sizeMultiplier = 0.97;
    private SoundPlayer soundPlayer;
    private String pack;
    private int touchX,touchY,levelWidth,swipes,stars,firstTime=50;
    private boolean playing;
    private Level level;
    private Rect playingField;
    private Menu menu;
    private ArrayList<Tile> tiles = new ArrayList<>();
    private Context context;
    private int[] starLevels= new int[3];
    private ArrayList<String> queuedSounds = new ArrayList<>();

    public Game(Level level,Context context,String pack){
        this.pack = pack;
        this.context=context;
        this.level=level;

        soundPlayer = new SoundPlayer(context);

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        playingField = new Rect(40, (height - width + 2 * 40) / 2+80, width - 40, (height + width - 2 * 40) / 2+80);
        levelWidth = level.getWidth();
        starLevels = level.getStarLevels();
        tiles = level.getTiles(context,this);
        stars = 0;

        menu = new Menu(playingField,width,height,context,this);
        playing = true;
        swipes = 0;
    }

    public void draw(Canvas canvas, Paint paint){
//        if(firstTime>0 || !playing){
//            firstTime--;
//        } else if(menu.live()){
//            canvas.clipRect(playingField.left,0,playingField.right,playingField.bottom);
//        } else {
//            canvas.clipRect(playingField);
//        }
        canvas.drawColor(Color.argb(200,255,255,255));
        paint.setAlpha(80);
        canvas.drawBitmap(ImageLoader.getBackground(context),-30,-50,paint);

        paint.setARGB(180,255,255,255);
        canvas.drawRect(playingField.left-10,playingField.top-10,playingField.right+10,playingField.bottom+10,paint);
        paint.reset();
        menu.paint(canvas, paint);
        paint.reset();



        //This part take 10 miliseconds
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
    }

    public  void update(){
        boolean wasMoving = tilesMoving();
        for (Tile t : tiles) {
            t.update();
        }
        if(wasMoving && !tilesMoving()){
            soundPlayer.stopSlideSound();
        }
        for(String s:queuedSounds){
            if(s.equals("hit")){
                soundPlayer.playHitSound();
            }
        }
        queuedSounds.clear();
    }

    public  void swipe(int direction){
        //1 ^ 2> 3\  4<
        if(playing) {
            boolean couldSwipe = !tilesMoving();

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
            if(tilesMoving() && couldSwipe){
                swipes++;
                soundPlayer.playSlideSound();
            }
        }
    }

    public  boolean isSolidTile(int x, int y) {
        for (Tile t : tiles) {
            if (!(t instanceof Spike) && t.getX() == x && t.getY() == y || (t instanceof DoubleCrate && ((DoubleCrate) t).getPosition() == 1 && t.getX() + 30 == x && t.getY() == y) || (t instanceof DoubleCrate && ((DoubleCrate) t).getPosition() == 2 && t.getX() == x && t.getY() + 30 == y)) {
                return true;
            }
        }
        return false;
    }

    public  boolean isTile(int x, int y, Class tileType) {
        for (Tile t : tiles) {
            if (!(t instanceof Spike) && tileType.isInstance(t) && (t.getX() == x && t.getY() == y || (t instanceof DoubleCrate && ((DoubleCrate) t).getPosition() == 1 && t.getX() + 30 == x && t.getY() == y) || (t instanceof DoubleCrate && ((DoubleCrate) t).getPosition() == 2 && t.getX() == x && t.getY() + 30 == y))) {
                return true;
            }
        }
        return false;
    }

    public  boolean isTileBesides(int x, int y, Class tileType) {
        for (Tile t : tiles) {
            if (!(t instanceof Spike) && !tileType.isInstance(t) && (t.getX() == x && t.getY() == y || (t instanceof DoubleCrate && ((DoubleCrate) t).getPosition() == 1 && t.getX() + 30 == x && t.getY() == y) || (t instanceof DoubleCrate && ((DoubleCrate) t).getPosition() == 2 && t.getX() == x && t.getY() + 30 == y))) {
                return true;
            }
        }
        return false;
    }

    public  boolean isSpike(int x, int y) {
        for (Tile t : tiles) {
            if (t instanceof Spike && t.getX() == x && t.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public  boolean tilesMoving() {
        for (Tile t : tiles) {
            if (t.isMoving()) {
                return true;
            }
        }
        return false;
    }

    public  void levelComplete(int x, int y, int size) {
        playing = false;
        if(pack.equals("default")){
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            int maxLevel = settings.getInt("maxLevel",1);
            if(Integer.valueOf(level.getName()).equals(maxLevel)){
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("maxLevel",maxLevel+1);
                editor.commit();
            }
        }
        saveStars();
        endParticle f = new endParticle(x,y,size,context,this);
    }

    private  void tileSort(String sort) {
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
    public  double getSizeMultiplier() {
        return sizeMultiplier;
    }

    public  Rect getPlayingField() {
        return playingField;
    }

    public  int getLevelWidth() {
        return levelWidth;
    }

    public  int getTouchX() {
        return touchX;
    }

    public  int getTouchY() {
        return touchY;
    }

    public  int getFPS() {
        return fps;
    }

    public  void touch(int x, int y) {
        if(x==-1 && y==-1){
            menu.released();
        }
        touchX =x;
        touchY =y;
    }

    public void addSound(String s){
        for(String string: queuedSounds){
            if(string.equals(s)){
                return;
            }
        }
        queuedSounds.add(s);
    }

    public  int getFps() {
        return fps;
    }

    public  Context getContext() {
        return context;
    }

    public  boolean isPlaying() {
        return playing;
    }

    public  void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public  int getStars() {
        return stars;
    }

    public  void updateStars() {
        if (swipes <= starLevels[0]) {
            stars = 3;
        } else if (swipes <= starLevels[1]) {
            stars = 2;
        } else if (swipes <= starLevels[2]) {
            stars = 1;
        } else {
            stars = 0;
        }
    }

    public  int getSwipes() {
        return swipes;
    }
    public  Level getLevel() {
        return level;
    }

    public  int[] getStarLevels() {
        return starLevels;
    }

    public void reset() {
        levelWidth = level.getWidth();
        starLevels = level.getStarLevels();
        tiles = level.getTiles(context,this);
        stars = level.getStars();
        swipes=0;
    }

    public void play(){
        playing = true;
        firstTime = 5;
    }

    public String getPack() {
        return pack;
    }

    public void saveStars() {
        updateStars();
        if(stars>level.getStars()) {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("stars" + level.getName(), "" + stars);
            editor.commit();
        }
    }

    public SoundPlayer getSoundPlayer() {
        return soundPlayer;
    }
}
//MEMORY LEAK OR PHONE SUCKS- there are two instances of game and selector menu classes

//crates dont make noise on bottem lvl 6
//slide sound longer
//doublecrate bottem doesnt trigger + check all sides -maybe done still needs check
//make sounds request game to play so it doesnt double trigger. -done?
//detection of boxes hitting double crates is bad -check
//different hit sounds for walls and spikes and crates
//MOTION BLUR
//why is there ghosting on sliding tiles over large distances?

//edit button
//fix button looks
//fix game layout
//MAXESURE NAMES CANT BREAK SYSTEM

//old==============================================
//fix white dot in corner of end particel
//make buttons and grey buttons,-
//fix layout
//make end particle only be as big as needed
//fix transitions
//add sounds        overridePendingTransition(R.anim.down_to_mid,R.anim.mid_to_up);


//old=================================

//name tipping crates ++

//check all grey buttons +

//new font for everything ++

//new arrow +++

