
package com.example.aidan.tilegameredo;

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.content.res.Resources;
        import android.content.res.XmlResourceParser;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.preference.PreferenceManager;
        import android.util.Log;

        import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbBox;
        import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbCrate;
        import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbDoubleCrate;
        import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbEmptyCrate;
        import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbSpike;
        import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbWall;
        import com.example.aidan.tilegameredo.tiles.Box;
        import com.example.aidan.tilegameredo.tiles.Crate;
        import com.example.aidan.tilegameredo.tiles.DoubleCrate;
        import com.example.aidan.tilegameredo.tiles.EmptyCrate;
        import com.example.aidan.tilegameredo.tiles.Spike;
        import com.example.aidan.tilegameredo.tiles.Wall;

        import org.xmlpull.v1.XmlPullParser;
        import org.xmlpull.v1.XmlPullParserException;

        import java.io.IOException;
        import java.util.ArrayList;


public class LevelGenerator {

    private static Context context;

    public LevelGenerator(Context context){
        LevelGenerator.context = context;
    }

    public static ArrayList<String> getAllLevels(String pack) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String levelNameList = settings.getString(pack+"LevelNames","");
        ArrayList<String> levels = new ArrayList<String>();
        for(String s:levelNameList.split(",")){
            levels.add(settings.getString(s+pack,""));
        }
        return levels;
    }
    public static String encodeLevel(ArrayList<Tile> tiles, int size,int stars,int[] starLevels,String name) {
        String levelString;
        levelString = name+"|"+stars+"|"+starLevels[0]+","+starLevels[1]+","+starLevels[2]+"|"+size+"|";
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

    public ArrayList<Tile> getLevel(int waveId) {
        ArrayList<Tile> level = new ArrayList<Tile>();

        Resources res = context.getResources();
        XmlResourceParser parser = res.getXml(R.xml.levels);

        int eventType = 0;
        try {
            eventType = parser.getEventType();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        boolean correctLevel = false;
        String text=null,type=null,posX=null,posY=null,position=null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagname = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tagname.equalsIgnoreCase("Level"+waveId)) {
                        correctLevel = true;
                    }
                    break;

                case XmlPullParser.TEXT:
                    text = parser.getText();
                    break;

                case XmlPullParser.END_TAG:
                    if (tagname.equalsIgnoreCase("Level"+waveId)) {
                        return level;
                    } else if(tagname.equalsIgnoreCase("type")){
                        type = text.trim();
                    } else if(tagname.equalsIgnoreCase("posX")){
                        posX = text.trim();
                    } else if(tagname.equalsIgnoreCase("posY")){
                        posY = text.trim();
                    } else if(tagname.equalsIgnoreCase("position")){
                        position = text;
                    } else if(tagname.equalsIgnoreCase("size")){
                        Game.setLevelWidth(Integer.valueOf(text.trim()));
                    } else if(tagname.equalsIgnoreCase("stars")){
                        Game.setStarLevels(new int[]{Integer.valueOf(text.trim().split(",")[0]),
                                Integer.valueOf(text.trim().split(",")[1]),
                                Integer.valueOf(text.trim().split(",")[2])});
                    }else if(tagname.equalsIgnoreCase("border") && correctLevel){
                        int x = Integer.valueOf(text.split(",")[0].trim());
                        int y = Integer.valueOf(text.split(",")[1].trim());
                        Bitmap img = BitmapFactory.decodeResource(context.getResources(),R.drawable.wallpixelated);
                        for(int i=0;i<x;i++){
                            level.add(new Wall(30*i,0,img));
                        }
                        for(int i=0;i<x;i++){
                            level.add(new Wall(30*i,30*(y-1),img));
                        }
                        for(int i=1;i<y-1;i++){
                            level.add(new Wall(0,30*i,img));
                        }
                        for(int i=1;i<y-1;i++){
                            level.add(new Wall(30*(x-1),30*i,img));
                        }

                    } else if(tagname.equalsIgnoreCase("tile") && correctLevel){
                        if(type.equals("Wall")) {
                            Bitmap img = ImageLoader.getWallImage(context);
                            level.add(new Wall(Integer.valueOf(posX)*30,Integer.valueOf(posY)*30,img));
                        } else if(type.equals("Crate")){
                            Bitmap img = ImageLoader.getCrateImage(context);
                            level.add(new Crate(Integer.valueOf(posX)*30,Integer.valueOf(posY)*30,img));
                        } else if(type.equals("EmptyCrate")){
                            Bitmap img = ImageLoader.getEmptyCrateImage(context);
                            level.add(new EmptyCrate(Integer.valueOf(posX)*30,Integer.valueOf(posY)*30,img));
                        } else if(type.equals("Box")){
                            Bitmap img = ImageLoader.getBoxImage(context);
                            level.add(new Box(Integer.valueOf(posX)*30,Integer.valueOf(posY)*30,img));

                        } else if(type.equals("DoubleCrate")){
                            Bitmap img;
                            if(Integer.valueOf(position.trim())==1) {
                                img =ImageLoader.getDoubleCrateImage(context);
                            } else {
                                img = ImageLoader.getDoubleCrate2Image(context);
                            }
                            level.add(new DoubleCrate(Integer.valueOf(posX)*30,Integer.valueOf(posY)*30,Integer.valueOf(position.trim()),img));
                        } else if(type.equals("Spike")){
                            Bitmap img = ImageLoader.getSpikeImage(context);
                            level.add(new Spike(Integer.valueOf(posX)*30,Integer.valueOf(posY)*30,Integer.valueOf(position.trim()),img));
                        }
                    }
                    break;

                default:
                    break;
            }
            try {
                eventType = parser.next();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.e("Test","Cannot find level");
        return level;
    }
}

