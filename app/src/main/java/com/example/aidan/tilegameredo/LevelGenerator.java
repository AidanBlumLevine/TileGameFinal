
package com.example.aidan.tilegameredo;

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.content.res.Resources;
        import android.content.res.XmlResourceParser;
        import android.preference.PreferenceManager;
        import android.util.Log;

        import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbBox;
        import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbCrate;
        import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbDoubleCrate;
        import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbEmptyCrate;
        import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbSpike;
        import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbWall;


        import org.xmlpull.v1.XmlPullParser;
        import org.xmlpull.v1.XmlPullParserException;

        import java.io.IOException;
        import java.util.ArrayList;


public class LevelGenerator {
    private static final int numberOfDefaultLevels=13;
    public static ArrayList<Level> getAllLevels(String pack,Context context) {
        ArrayList<Level> levels = new ArrayList<Level>();
        if(pack.equals("default")) {
            for(int i=1;i<=numberOfDefaultLevels;i++){
                levels.add(getLevel(i,context));
            }
        } else {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            String levelNameList = settings.getString(pack + "LevelNames", "");
            if (levelNameList != "") {
                for (String s : levelNameList.split(",")) {
                    levels.add(new Level(context, s + pack));
                }
            }
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

    public static Level getLevel(int waveId,Context context) {
        String level = "";
        Level returnLevel;
        int width=0;
        int[] starLevels = new int[0];

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
                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                        String stars = settings.getString("stars"+waveId,"0");
                        String fullLevel = waveId+"|"+stars+"|"+starLevels[0]+","+starLevels[1]+","+starLevels[2]+"|"+width+"|"+level;
                        returnLevel = new Level(fullLevel);
                        return returnLevel;
                    } else if(tagname.equalsIgnoreCase("type")){
                        type = text.trim();
                    } else if(tagname.equalsIgnoreCase("posX")){
                        posX = text.trim();
                    } else if(tagname.equalsIgnoreCase("posY")){
                        posY = text.trim();
                    } else if(tagname.equalsIgnoreCase("position")){
                        position = text;
                    } else if(tagname.equalsIgnoreCase("size")){
                        width = Integer.valueOf(text.trim());
                    } else if(tagname.equalsIgnoreCase("stars")){
                        starLevels = new int[]{Integer.valueOf(text.trim().split(",")[0]),
                                Integer.valueOf(text.trim().split(",")[1]),
                                Integer.valueOf(text.trim().split(",")[2])};
                    }else if(tagname.equalsIgnoreCase("border") && correctLevel){
                        int x = Integer.valueOf(text.split(",")[0].trim());
                        int y = Integer.valueOf(text.split(",")[1].trim());
                        for(int i=0;i<x;i++){
                            level += "wall,"+30*i+","+0+":";
                        }
                        for(int i=0;i<x;i++){
                            level += "wall,"+30*i+","+30*(y-1)+":";
                        }
                        for(int i=1;i<y-1;i++){
                            level += "wall,"+0+","+30*i+":";
                        }
                        for(int i=1;i<y-1;i++) {
                            level += "wall," + 30 * (x - 1) + "," + 30 * i + ":";
                        }
                    } else if(tagname.equalsIgnoreCase("tile") && correctLevel){
                        if(type.equals("Wall")) {
                            level += "wall,"+Integer.valueOf(posX)*30+","+Integer.valueOf(posY)*30+":";
                        } else if(type.equals("Crate")){
                            level += "crate,"+Integer.valueOf(posX)*30+","+Integer.valueOf(posY)*30+":";
                        } else if(type.equals("EmptyCrate")){
                            level += "emptyCrate,"+Integer.valueOf(posX)*30+","+Integer.valueOf(posY)*30+":";
                        } else if(type.equals("Box")){
                            level += "box,"+Integer.valueOf(posX)*30+","+Integer.valueOf(posY)*30+":";

                        } else if(type.equals("DoubleCrate")){
                            level += "doubleCrate,"+Integer.valueOf(posX)*30+","+Integer.valueOf(posY)*30+","+position.trim()+":";

                        } else if(type.equals("Spike")){
                            level += "spike,"+Integer.valueOf(posX)*30+","+Integer.valueOf(posY)*30+","+position.trim()+":";

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
        return null;
    }

    public static int numberOfLevels() {
        return numberOfDefaultLevels;
    }
}

