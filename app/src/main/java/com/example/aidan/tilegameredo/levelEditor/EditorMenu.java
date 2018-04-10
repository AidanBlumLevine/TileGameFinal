package com.example.aidan.tilegameredo.levelEditor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import com.example.aidan.tilegameredo.Button;
import com.example.aidan.tilegameredo.HomeScreen;
import com.example.aidan.tilegameredo.Loader;
import com.example.aidan.tilegameredo.R;
import com.example.aidan.tilegameredo.Tile;
import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbBox;
import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbCrate;
import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbDoubleCrate;
import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbEmptyCrate;
import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbSpike;
import com.example.aidan.tilegameredo.levelEditor.dumbTiles.DumbWall;


public class EditorMenu {
    private String selectedItem = "null";
    private Button buttonSave,buttonTopBack,buttonSizeUp,buttonSizeDown;
    private Rect box,crate,doubleCrate,emptyCrate,spike,wall,spike2,spike3,spike4,doubleCrate2;

    private Bitmap boxImg,crateImg,doubleCrate1Img,doubleCrate2Img,emptyCrateImg,spikeImg,wallImg,
            boxImgRaw,crateImgRaw,doubleCrate1ImgRaw,doubleCrate2ImgRaw,emptyCrateImgRaw,spikeImgRaw,wallImgRaw;
    private Rect buttonArea, levelArea, iconArea;
    private Context context;
    private LevelEditor parent;
    AlertDialog.Builder builder;
    public EditorMenu(Context context, LevelEditor parent){
        this.context = context;
        this.parent = parent;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        int edgeBuffer = screenWidth/20;
        levelArea = parent.getPlayingField();
        buttonArea = new Rect(edgeBuffer,edgeBuffer,screenWidth-edgeBuffer,levelArea.top-edgeBuffer);
        int bottomHeight = screenHeight-levelArea.bottom-edgeBuffer*2;
        iconArea = new Rect(screenWidth/2  -  bottomHeight*3/2,levelArea.bottom+edgeBuffer,screenWidth/2  +  bottomHeight*3/2,screenHeight-edgeBuffer);

        buttonTopBack = new Button(buttonArea.left+edgeBuffer, buttonArea.left + edgeBuffer*3,Bitmap.createScaledBitmap(Loader.getButtonBack(context),edgeBuffer*3,edgeBuffer*3,false));
        buttonSave = new Button(buttonArea.centerX()-edgeBuffer*2,buttonArea.centerY()-edgeBuffer,edgeBuffer*4,edgeBuffer*2,Color.BLUE,"SAVE",48,Loader.getFont(context));
        buttonSizeUp = new Button(buttonArea.right-3*edgeBuffer,buttonArea.top+edgeBuffer,Bitmap.createScaledBitmap(Loader.getButtonSizeUp(context),edgeBuffer*2,edgeBuffer*2,false));
        buttonSizeDown = new Button(buttonArea.right-3*edgeBuffer,buttonArea.bottom-3*edgeBuffer,Bitmap.createScaledBitmap(Loader.getButtonSizeDown(context),edgeBuffer*2,edgeBuffer*2,false));


        int bufferX = iconArea.width()/30;
        int iconWidth = (iconArea.width()-7*bufferX)/6;
        int bufferY = (iconArea.height()-iconWidth*2)/3;
        int left = iconArea.left;
        int top = iconArea.top;
        int buffer = Math.min(bufferX,bufferY);
        box = new Rect(buffer+left,buffer+top,iconWidth+buffer+left,iconWidth+buffer+top);
        crate = new Rect(2*buffer+iconWidth+left,buffer+top,2*buffer+2*iconWidth+left,buffer+iconWidth+top);
        doubleCrate2 = new Rect(buffer+left,2*buffer+iconWidth+top,2*iconWidth+2*buffer+left,2*iconWidth+2*buffer+top);
        doubleCrate = new Rect(3*buffer+2*iconWidth+left,buffer+top,3*iconWidth+3*buffer+left,2*iconWidth+2*buffer+top);

        emptyCrate = new Rect(4*buffer+3*iconWidth+left,buffer+top,4*(iconWidth+buffer)+left,iconWidth+buffer+top);
        wall = new Rect(4*buffer+3*iconWidth+left,2*buffer+iconWidth+top,4*(iconWidth+buffer)+left,2*(iconWidth+buffer)+top);

        spike = new Rect(5*buffer+4*iconWidth+left,3*buffer/2+top+iconWidth/2,5*(iconWidth+buffer)+left,3*buffer/2+top+3*iconWidth/2);
        spike2 = new Rect(11*buffer/2+9*iconWidth/2+left,buffer+top,11*buffer/2+11*iconWidth/2+left,buffer+iconWidth+top);

        spike3 = new Rect(6*buffer+5*iconWidth+left,3*buffer/2+top+iconWidth/2,6*iconWidth+6*buffer+left,3*buffer/2+top+3*iconWidth/2);
        spike4 = new Rect(11*buffer/2+9*iconWidth/2+left,2*buffer+iconWidth+top,11*iconWidth/2+11*buffer/2+left,2*(iconWidth+buffer)+top);

        boxImgRaw = Loader.getBoxImage(context);
        crateImgRaw = Loader.getCrateImage(context);
        doubleCrate1ImgRaw = Loader.getDoubleCrate2Image(context);
        doubleCrate2ImgRaw = Loader.getDoubleCrateImage(context);
        emptyCrateImgRaw = Loader.getEmptyCrateImage(context);
        spikeImgRaw = Loader.getSpikeImage(context);
        wallImgRaw = Loader.getWallImage(context);
        boxImg = Bitmap.createScaledBitmap(boxImgRaw,box.width(),box.height(),false);
        crateImg = Bitmap.createScaledBitmap(crateImgRaw,crate.width(),crate.height(),false);
        doubleCrate1Img = Bitmap.createScaledBitmap(doubleCrate1ImgRaw,doubleCrate.width(),doubleCrate.height(),false);
        doubleCrate2Img = Bitmap.createScaledBitmap(doubleCrate2ImgRaw,doubleCrate2.width(),doubleCrate2.height(),false);
        emptyCrateImg = Bitmap.createScaledBitmap(emptyCrateImgRaw,emptyCrate.width(),emptyCrate.height(),false);
        spikeImg = Bitmap.createScaledBitmap(spikeImgRaw,spike.width(),spike.height(),false);
        wallImg = Bitmap.createScaledBitmap(wallImgRaw,wall.width(),wall.height(),false);
    }

    public void paint(Canvas canvas, Paint paint){
        canvas.drawBitmap(boxImg,box.left,box.top,paint);
        canvas.drawBitmap(crateImg,crate.left,crate.top,paint);
        canvas.drawBitmap(doubleCrate1Img,doubleCrate.left,doubleCrate.top,paint);
        canvas.drawBitmap(doubleCrate2Img,doubleCrate2.left,doubleCrate2.top,paint);
        canvas.drawBitmap(emptyCrateImg,emptyCrate.left,emptyCrate.top,paint);
        canvas.drawBitmap(spikeImg,spike.left,spike.top,paint);
        canvas.drawBitmap(wallImg,wall.left,wall.top,paint);
        canvas.save();
        canvas.rotate(90,spike2.exactCenterX(),spike2.exactCenterY());
        canvas.drawBitmap(spikeImg,spike2.left,spike2.top,paint);
        canvas.restore();
        canvas.save();
        canvas.rotate(180,spike3.exactCenterX(),spike3.exactCenterY());
        canvas.drawBitmap(spikeImg,spike3.left,spike3.top,paint);
        canvas.restore();
        canvas.save();
        canvas.rotate(270,spike4.exactCenterX(),spike4.exactCenterY());
        canvas.drawBitmap(spikeImg,spike4.left,spike4.top,paint);
        canvas.restore();

        if(selectedItem.equals("box")){
            canvas.drawBitmap(boxImg,parent.getTouchX()-box.width()/2,parent.getTouchY()-box.width()/2,paint);
        }
        if(selectedItem.equals("crate")){
            canvas.drawBitmap(crateImg,parent.getTouchX()-box.width()/2,parent.getTouchY()-box.width()/2,paint);
        }
        if(selectedItem.equals("doubleCrate")){
            canvas.drawBitmap(doubleCrate1Img,parent.getTouchX()-box.width()/2,parent.getTouchY()-box.width()/2,paint);
        }
        if(selectedItem.equals("doubleCrate2")){
            canvas.drawBitmap(doubleCrate2Img,parent.getTouchX()-box.width()/2,parent.getTouchY()-box.width()/2,paint);
        }
        if(selectedItem.equals("emptyCrate")){
            canvas.drawBitmap(emptyCrateImg,parent.getTouchX()-box.width()/2,parent.getTouchY()-box.width()/2,paint);
        }
        if(selectedItem.equals("wall")){
            canvas.drawBitmap(wallImg,parent.getTouchX()-box.width()/2,parent.getTouchY()-box.width()/2,paint);
        }
        if(selectedItem.equals("spike")){
            canvas.drawBitmap(spikeImg,parent.getTouchX()-box.width()/2,parent.getTouchY()-box.width()/2,paint);
        }
        if(selectedItem.equals("spike2")){
            canvas.save();
            canvas.rotate(90,parent.getTouchX()-box.width()/2+spike2.width()/2,parent.getTouchY()-box.width()/2+spike2.height()/2);
            canvas.drawBitmap(spikeImg,parent.getTouchX()-box.width()/2,parent.getTouchY()-box.width()/2,paint);
            canvas.restore();
        }
        if(selectedItem.equals("spike3")){
            canvas.save();
            canvas.rotate(180,parent.getTouchX()-box.width()/2+spike3.width()/2,parent.getTouchY()-box.width()/2+spike3.height()/2);
            canvas.drawBitmap(spikeImg,parent.getTouchX()-box.width()/2,parent.getTouchY()-box.width()/2,paint);
            canvas.restore();
        }
        if(selectedItem.equals("spike4")){
            canvas.save();
            canvas.rotate(270,parent.getTouchX()-box.width()/2+spike4.width()/2,parent.getTouchY()-box.width()/2+spike4.height()/2);
            canvas.drawBitmap(spikeImg,parent.getTouchX()-box.width()/2,parent.getTouchY()-box.width()/2,paint);
            canvas.restore();
        }

        buttonSave.draw(canvas,paint);
        buttonSizeDown.draw(canvas,paint);
        buttonSizeUp.draw(canvas,paint);
        buttonTopBack.draw(canvas,paint);

        int tX=parent.getTouchX();
        int tY=parent.getTouchY();
        buttonSave.touch(tX,tY);
        buttonSizeDown.touch(tX,tY);
        buttonSizeUp.touch(tX,tY);
        buttonTopBack.touch(tX,tY);
    }

    public void released() {
        Log.e("THREAD",Thread.currentThread().getName());
        if(parent.getPlayingField().contains(parent.getTouchX(),parent.getTouchY())){
            int boxX = (int)((parent.getTouchX()-parent.getPlayingField().left)/(parent.getPlayingField().width()/(double)parent.getTilesInLevel()));
            int boxY = (int)((parent.getTouchY()-parent.getPlayingField().top)/(parent.getPlayingField().height()/(double)parent.getTilesInLevel()));
            if(!parent.isTile(boxX,boxY)){
                switch(selectedItem) {
                    case "box":
                        parent.addTile(new DumbBox(boxX,boxY,boxImgRaw,parent));
                        break;
                    case "crate":
                        parent.addTile(new DumbCrate(boxX,boxY,crateImgRaw,parent));
                        break;
                    case "doubleCrate":
                        if(!parent.isTile(boxX,boxY+1))
                            parent.addTile(new DumbDoubleCrate(boxX,boxY,2,doubleCrate1ImgRaw,parent));
                        break;
                    case "doubleCrate2":
                        if(!parent.isTile(boxX+1,boxY))
                            parent.addTile(new DumbDoubleCrate(boxX,boxY,1,doubleCrate2ImgRaw,parent));
                        break;
                    case "wall":
                        parent.addTile(new DumbWall(boxX,boxY,wallImgRaw,parent));
                        break;
                    case "emptyCrate":
                        parent.addTile(new DumbEmptyCrate(boxX,boxY,emptyCrateImgRaw,parent));
                        break;
                    case "spike":
                        parent.addTile(new DumbSpike(boxX,boxY,1,spikeImgRaw,parent));
                        break;
                    case "spike2":
                        parent.addTile(new DumbSpike(boxX,boxY,2,spikeImgRaw,parent));
                        break;
                    case "spike3":
                        parent.addTile(new DumbSpike(boxX,boxY,3,spikeImgRaw,parent));
                        break;
                    case "spike4":
                        parent.addTile(new DumbSpike(boxX,boxY,4,spikeImgRaw,parent));
                        break;
                }
            }
        }
        selectedItem = "none";

        if (buttonTopBack.getHover()) {
            Intent i = new Intent(context,HomeScreen.class);
            context.startActivity(i);
            ((AppCompatActivity)context).overridePendingTransition(R.anim.up_to_mid,R.anim.mid_to_down);
        }
        if (buttonSave.getHover()) {
            builder = new AlertDialog.Builder(context);
            builder.setTitle("Choose a title for your level");

            InputFilter[] FilterArray = new InputFilter[1];
            FilterArray[0] = new InputFilter.LengthFilter(50);


            final EditText input = new EditText(context);
            input.setFilters(FilterArray);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                    if(input.getText().toString().contains(",") || input.getText().toString().contains(":") || input.getText().toString().contains("|")
                            || input.getText().toString().contains("{") || input.getText().toString().contains("}") || input.getText().toString().contains("\"")
                            || input.getText().toString().contains(";") || input.getText().toString().contains("]") || input.getText().toString().contains("[")){
                        dialog.cancel();
                        Toast.makeText(context, "Name cannot contain \",\" , \":\" , \"|\" , \"{\" , \"}\", \" \" \" , ; , ] , [", Toast.LENGTH_LONG).show();
                    } else if(!settings.getString(input.getText()+"custom","").equals("")){
                        dialog.cancel();
                        Toast.makeText(context, "That name is already taken", Toast.LENGTH_LONG).show();
                    } else if(input.getText().toString().equals("")){
                        dialog.cancel();
                        Toast.makeText(context, "Name cannot be empty", Toast.LENGTH_LONG).show();
                    } else {
                        parent.save(input.getText().toString());
                        Toast.makeText(context, "Level saved", Toast.LENGTH_LONG).show();
                        Log.e("LevelSaveName",input.getText().toString());
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    builder.show();
                }
            });

        }
        if (buttonSizeDown.getHover()) {
            sizeChange(false);
        }if (buttonSizeUp.getHover()) {
            sizeChange(true);
        }
    }

    public void pressed() {
        if(parent.getPlayingField().contains(parent.getTouchX(),parent.getTouchY())) {
            int boxX = (int) ((parent.getTouchX() - parent.getPlayingField().left) / (parent.getPlayingField().width() / (double) parent.getTilesInLevel()));
            int boxY = (int) ((parent.getTouchY() - parent.getPlayingField().top) / (parent.getPlayingField().height() / (double) parent.getTilesInLevel()));
            if(boxX!=0 && boxX != parent.getTilesInLevel()-1 && boxY != 0 && boxY != parent.getTilesInLevel()-1) {
                if (parent.getTileAt(boxX , boxY ) instanceof DumbBox) {
                    selectedItem = "box";
                }
                if (parent.getTileAt(boxX , boxY ) instanceof DumbCrate) {
                    selectedItem = "crate";
                }
                if((parent.getTileAt(boxX , boxY ) instanceof DumbDoubleCrate && ((DumbDoubleCrate)parent.getTileAt(boxX , boxY )).getPosition()==1) || (parent.getTileAt(boxX -1, boxY ) instanceof DumbDoubleCrate && ((DumbDoubleCrate)parent.getTileAt(boxX -1, boxY )).getPosition()==1)){
                    selectedItem = "doubleCrate2";
                    if((parent.getTileAt(boxX -1, boxY ) instanceof DumbDoubleCrate)) {
                        parent.removeTile(boxX  -1, boxY );
                    }
                }
                if((parent.getTileAt(boxX , boxY ) instanceof DumbDoubleCrate && ((DumbDoubleCrate)parent.getTileAt(boxX , boxY )).getPosition()==2) || (parent.getTileAt(boxX , boxY -1) instanceof DumbDoubleCrate && ((DumbDoubleCrate)parent.getTileAt(boxX , boxY -1)).getPosition()==2)){
                    selectedItem = "doubleCrate";
                    if((parent.getTileAt(boxX , boxY -1) instanceof DumbDoubleCrate)) {
                        parent.removeTile(boxX , boxY  -1);
                    }
                }
                if (parent.getTileAt(boxX , boxY ) instanceof DumbEmptyCrate) {
                    selectedItem = "emptyCrate";
                }
                if (parent.getTileAt(boxX , boxY ) instanceof DumbSpike) {
                    if (((DumbSpike) parent.getTileAt(boxX , boxY )).getPosition() == 1) {
                        selectedItem = "spike";
                    } else if (((DumbSpike) parent.getTileAt(boxX , boxY )).getPosition() == 2) {
                        selectedItem = "spike2";
                    } else if (((DumbSpike) parent.getTileAt(boxX , boxY )).getPosition() == 3) {
                        selectedItem = "spike3";
                    } else {
                        selectedItem = "spike4";
                    }
                }
                if (parent.getTileAt(boxX , boxY ) instanceof DumbWall) {
                    selectedItem = "wall";
                }
                parent.removeTile(boxX , boxY );
            }
        }
        if(box.contains(parent.getTouchX(),parent.getTouchY())){
            selectedItem = "box";
        }
        if(crate.contains(parent.getTouchX(),parent.getTouchY())){
            selectedItem = "crate";
        }
        if(doubleCrate.contains(parent.getTouchX(),parent.getTouchY())){
            selectedItem = "doubleCrate";
        }
        if(doubleCrate2.contains(parent.getTouchX(),parent.getTouchY())){
            selectedItem = "doubleCrate2";
        }
        if(wall.contains(parent.getTouchX(),parent.getTouchY())){
            selectedItem = "wall";
        }
        if(emptyCrate.contains(parent.getTouchX(),parent.getTouchY())){
            selectedItem = "emptyCrate";
        }
        if(spike.contains(parent.getTouchX(),parent.getTouchY())){
            selectedItem = "spike";
        }
        if(spike2.contains(parent.getTouchX(),parent.getTouchY())){
            selectedItem = "spike2";
        }
        if(spike3.contains(parent.getTouchX(),parent.getTouchY())){
            selectedItem = "spike3";
        }
        if(spike4.contains(parent.getTouchX(),parent.getTouchY())){
            selectedItem = "spike4";
        }
    }

    public int getSize() {
        return parent.getTilesInLevel();
    }

    public void generateBorder(){
        for(int i=0;i<parent.getTilesInLevel();i++){
            parent.addTile(new DumbWall(i,0,wallImgRaw,parent));
        }
        for(int i=0;i<parent.getTilesInLevel();i++){
            parent.addTile(new DumbWall(i,(parent.getTilesInLevel()-1),wallImgRaw,parent));
        }
        for(int i=1;i<parent.getTilesInLevel()-1;i++){
            parent.addTile(new DumbWall(0,i,wallImgRaw,parent));
        }
        for(int i=1;i<parent.getTilesInLevel()-1;i++){
            parent.addTile(new DumbWall((parent.getTilesInLevel()-1),i,wallImgRaw,parent));
        }
    }

    public void sizeChange(boolean big){
        int oldTiles = parent.getTilesInLevel();
        if(big) {
            parent.changeSize(1);
        } else {
            parent.changeSize(-1);
        }
        parent.removeTile((oldTiles-1),(oldTiles-1));
        for(int i=0;i<parent.getTilesInLevel()+1;i++)
            parent.removeTile(i,0);
        for(int i=0;i<parent.getTilesInLevel()+1;i++)
            parent.removeTile(0,i);
        for(int i=1;i<parent.getTilesInLevel();i++)
            parent.removeTile(i,(oldTiles-1));
        for(int i=1;i<parent.getTilesInLevel();i++)
            parent.removeTile((oldTiles-1),i);
        for(int i=1;i<parent.getTilesInLevel();i++){
            for(int r=1;r<parent.getTilesInLevel();r++){
                Tile t = parent.getTileAt(i,r);
                parent.removeTile(i,r);
                if(t instanceof DumbBox)
                    parent.addTile(new DumbBox(t.getX(),t.getY(),boxImgRaw,parent));
                if(t instanceof DumbCrate && t.getX()<(parent.getTilesInLevel()-1) && t.getY()<(parent.getTilesInLevel()-1))
                    parent.addTile(new DumbCrate(t.getX(),t.getY(),crateImgRaw,parent));
                if(t instanceof DumbWall && t.getX()<(parent.getTilesInLevel()-1) && t.getY()<(parent.getTilesInLevel()-1))
                    parent.addTile(new DumbWall(t.getX(),t.getY(),wallImgRaw,parent));
                if(t instanceof DumbEmptyCrate && t.getX()<(parent.getTilesInLevel()-1) && t.getY()<(parent.getTilesInLevel()-1))
                    parent.addTile(new DumbEmptyCrate(t.getX(),t.getY(),emptyCrateImgRaw,parent));
                if(t instanceof DumbSpike && t.getX()<(parent.getTilesInLevel()-1) && t.getY()<(parent.getTilesInLevel()-1))
                    parent.addTile(new DumbSpike(t.getX(),t.getY(),((DumbSpike) t).getPosition(),spikeImgRaw,parent));
                if(t instanceof DumbDoubleCrate && ((DumbDoubleCrate) t).getPosition()==1 && t.getX()<(parent.getTilesInLevel()-2) && t.getY()<(parent.getTilesInLevel()-1))
                    parent.addTile(new DumbDoubleCrate(t.getX(), t.getY(), 1, doubleCrate2Img,parent));
                if(t instanceof DumbDoubleCrate && ((DumbDoubleCrate) t).getPosition()==2 && t.getY()<(parent.getTilesInLevel()-2) && t.getX()<(parent.getTilesInLevel()-1)){
                    parent.addTile(new DumbDoubleCrate(t.getX(),t.getY(),2,doubleCrate1Img,parent));
                    Log.e("g",String.valueOf(t.getX())+","+String.valueOf((parent.getTilesInLevel()-1)));
                }

            }
        }
        generateBorder();
    }
}
