package com.example.aidan.tilegameredo.levelEditor;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.aidan.tilegameredo.Button;
import com.example.aidan.tilegameredo.HomeScreen;
import com.example.aidan.tilegameredo.ImageLoader;
import com.example.aidan.tilegameredo.Menu;
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
    private int boxSize;
    private Button buttonSave,buttonTopBack,buttonSizeUp,buttonSizeDown;
    private Rect box,crate,doubleCrate,emptyCrate,spike,wall,spike2,spike3,spike4,doubleCrate2;

    private Bitmap boxImg,crateImg,doubleCrate1Img,doubleCrate2Img,emptyCrateImg,spikeImg,wallImg,
            boxImgRaw,crateImgRaw,doubleCrate1ImgRaw,doubleCrate2ImgRaw,emptyCrateImgRaw,spikeImgRaw,wallImgRaw;
    private Context context;

    public EditorMenu(Context context){
        this.context = context;

        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        int buffer = 20;
        int height = LevelEditor.getPlayingField().top;
        int tileSize = Math.min((height-3*buffer)/2,(screenWidth-7*buffer)/6);
        int centeringBuffer = (screenWidth-buffer*7-tileSize*6)/2;

        int bottomSpaceHeight = screenHeight - LevelEditor.getPlayingField().bottom;
        int topBottomBuffer = bottomSpaceHeight / 8;
        int leftRightBuffer = screenWidth / 18;

        boxSize = bottomSpaceHeight - topBottomBuffer * 3;
        buttonTopBack = new Button(leftRightBuffer, LevelEditor.getPlayingField().bottom + topBottomBuffer,Bitmap.createScaledBitmap(ImageLoader.getButtonBack(context),boxSize,boxSize,false));
        buttonSave = new Button(screenWidth-leftRightBuffer-boxSize, LevelEditor.getPlayingField().bottom + topBottomBuffer,Bitmap.createScaledBitmap(ImageLoader.getButtonSave(context),boxSize,boxSize,false));
        int smallBoxSize = boxSize/2;
        buttonSizeUp = new Button(LevelEditor.getPlayingField().centerX()-(smallBoxSize)/2,LevelEditor.getPlayingField().bottom + leftRightBuffer,Bitmap.createScaledBitmap(ImageLoader.getButtonSizeUp(context),smallBoxSize,smallBoxSize,false));
        buttonSizeDown = new Button(LevelEditor.getPlayingField().centerX()-(smallBoxSize)/2, LevelEditor.getPlayingField().bottom + leftRightBuffer+smallBoxSize,Bitmap.createScaledBitmap(ImageLoader.getButtonSizeDown(context),smallBoxSize,smallBoxSize,false));

        box = new Rect(buffer+centeringBuffer,buffer,tileSize+buffer+centeringBuffer,tileSize+buffer);
        crate = new Rect(2*buffer+tileSize+centeringBuffer,buffer,2*buffer+2*tileSize+centeringBuffer,buffer+tileSize);
        doubleCrate2 = new Rect((int)(1.5*buffer)+centeringBuffer,2*buffer+tileSize,2*(tileSize+(int)(.7*buffer))+centeringBuffer,2*(tileSize+buffer));
        doubleCrate = new Rect(3*buffer+2*tileSize+centeringBuffer,(int)(1.5*buffer),3*tileSize+3*buffer+centeringBuffer,2*tileSize+2*(int)(.7*buffer));

        emptyCrate = new Rect(4*buffer+3*tileSize+centeringBuffer,buffer,4*(tileSize+buffer)+centeringBuffer,tileSize+buffer);
        wall = new Rect(4*buffer+3*tileSize+centeringBuffer,2*buffer+tileSize,4*(tileSize+buffer)+centeringBuffer,2*(tileSize+buffer));

        spike = new Rect(5*buffer+4*tileSize+centeringBuffer,buffer,5*(tileSize+buffer)+centeringBuffer,tileSize+buffer);
        spike2 = new Rect(5*buffer+4*tileSize+centeringBuffer,2*buffer+tileSize,5*(tileSize+buffer)+centeringBuffer,2*(tileSize+buffer));

        spike3 = new Rect(6*buffer+5*tileSize+centeringBuffer,buffer,6*(tileSize+buffer)+centeringBuffer,tileSize+buffer);
        spike4 = new Rect(6*buffer+5*tileSize+centeringBuffer,2*buffer+tileSize,6*(tileSize+buffer)+centeringBuffer,2*(tileSize+buffer));

        boxImgRaw = ImageLoader.getBoxImage(context);
        crateImgRaw = ImageLoader.getCrateImage(context);
        doubleCrate1ImgRaw = ImageLoader.getDoubleCrate2Image(context);
        doubleCrate2ImgRaw = ImageLoader.getDoubleCrateImage(context);
        emptyCrateImgRaw = ImageLoader.getEmptyCrateImage(context);
        spikeImgRaw = ImageLoader.getSpikeImage(context);
        wallImgRaw = ImageLoader.getWallImage(context);
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
            canvas.drawBitmap(boxImg,LevelEditor.getTouchX()-box.width()/2,LevelEditor.getTouchY()-box.width()/2,paint);
        }
        if(selectedItem.equals("crate")){
            canvas.drawBitmap(crateImg,LevelEditor.getTouchX()-box.width()/2,LevelEditor.getTouchY()-box.width()/2,paint);
        }
        if(selectedItem.equals("doubleCrate")){
            canvas.drawBitmap(doubleCrate1Img,LevelEditor.getTouchX()-box.width()/2,LevelEditor.getTouchY()-box.width()/2,paint);
        }
        if(selectedItem.equals("doubleCrate2")){
            canvas.drawBitmap(doubleCrate2Img,LevelEditor.getTouchX()-box.width()/2,LevelEditor.getTouchY()-box.width()/2,paint);
        }
        if(selectedItem.equals("emptyCrate")){
            canvas.drawBitmap(emptyCrateImg,LevelEditor.getTouchX()-box.width()/2,LevelEditor.getTouchY()-box.width()/2,paint);
        }
        if(selectedItem.equals("wall")){
            canvas.drawBitmap(wallImg,LevelEditor.getTouchX()-box.width()/2,LevelEditor.getTouchY()-box.width()/2,paint);
        }
        if(selectedItem.equals("spike")){
            canvas.drawBitmap(spikeImg,LevelEditor.getTouchX()-box.width()/2,LevelEditor.getTouchY()-box.width()/2,paint);
        }
        if(selectedItem.equals("spike2")){
            canvas.save();
            canvas.rotate(90,LevelEditor.getTouchX()-box.width()/2+spike2.width()/2,LevelEditor.getTouchY()-box.width()/2+spike2.height()/2);
            canvas.drawBitmap(spikeImg,LevelEditor.getTouchX()-box.width()/2,LevelEditor.getTouchY()-box.width()/2,paint);
            canvas.restore();
        }
        if(selectedItem.equals("spike3")){
            canvas.save();
            canvas.rotate(180,LevelEditor.getTouchX()-box.width()/2+spike3.width()/2,LevelEditor.getTouchY()-box.width()/2+spike3.height()/2);
            canvas.drawBitmap(spikeImg,LevelEditor.getTouchX()-box.width()/2,LevelEditor.getTouchY()-box.width()/2,paint);
            canvas.restore();
        }
        if(selectedItem.equals("spike4")){
            canvas.save();
            canvas.rotate(270,LevelEditor.getTouchX()-box.width()/2+spike4.width()/2,LevelEditor.getTouchY()-box.width()/2+spike4.height()/2);
            canvas.drawBitmap(spikeImg,LevelEditor.getTouchX()-box.width()/2,LevelEditor.getTouchY()-box.width()/2,paint);
            canvas.restore();
        }

        buttonSave.draw(canvas,paint);
        buttonSizeDown.draw(canvas,paint);
        buttonSizeUp.draw(canvas,paint);
        buttonTopBack.draw(canvas,paint);

        int tX=LevelEditor.getTouchX();
        int tY=LevelEditor.getTouchY();
        buttonSave.touch(tX,tY);
        buttonSizeDown.touch(tX,tY);
        buttonSizeUp.touch(tX,tY);
        buttonTopBack.touch(tX,tY);
    }

    private Path savePath(int x, int y, float size) {
        Path p = new Path();
        p.moveTo(x-size,y-size);
        p.lineTo(x-size,y+size);
        p.lineTo(x+size,y+size);
        p.lineTo(x+size,y-size/2);
        p.lineTo(x+size/2,y-size);
        p.close();
        p.moveTo(x-size,y);
        p.lineTo(x+size,y);
        p.moveTo(x+size/3f,y-size/4);
        p.lineTo(x+size/3f,y-size/1.5f);

        return p;
    }

    public void released() {
        if(LevelEditor.getPlayingField().contains(LevelEditor.getTouchX(),LevelEditor.getTouchY())){
            int boxX = (int)((LevelEditor.getTouchX()-LevelEditor.getPlayingField().left)/(LevelEditor.getPlayingField().width()/(double)LevelEditor.getTilesInLevel()));
            int boxY = (int)((LevelEditor.getTouchY()-LevelEditor.getPlayingField().top)/(LevelEditor.getPlayingField().height()/(double)LevelEditor.getTilesInLevel()));
            if(!LevelEditor.isTile(boxX*30,boxY*30)){
                switch(selectedItem) {
                    case "box":
                        LevelEditor.addTile(new DumbBox(boxX*30,boxY*30,boxImgRaw));
                        break;
                    case "crate":
                        LevelEditor.addTile(new DumbCrate(boxX*30,boxY*30,crateImgRaw));
                        break;
                    case "doubleCrate":
                        if(!LevelEditor.isTile(boxX*30,boxY*30+30))
                            LevelEditor.addTile(new DumbDoubleCrate(boxX*30,boxY*30,2,doubleCrate1ImgRaw));
                        break;
                    case "doubleCrate2":
                        if(!LevelEditor.isTile(boxX*30+30,boxY*30))
                            LevelEditor.addTile(new DumbDoubleCrate(boxX*30,boxY*30,1,doubleCrate2ImgRaw));
                        break;
                    case "wall":
                        LevelEditor.addTile(new DumbWall(boxX*30,boxY*30,wallImgRaw));
                        break;
                    case "emptyCrate":
                        LevelEditor.addTile(new DumbEmptyCrate(boxX*30,boxY*30,emptyCrateImgRaw));
                        break;
                    case "spike":
                        LevelEditor.addTile(new DumbSpike(boxX*30,boxY*30,1,spikeImgRaw));
                        break;
                    case "spike2":
                        LevelEditor.addTile(new DumbSpike(boxX*30,boxY*30,2,spikeImgRaw));
                        break;
                    case "spike3":
                        LevelEditor.addTile(new DumbSpike(boxX*30,boxY*30,3,spikeImgRaw));
                        break;
                    case "spike4":
                        LevelEditor.addTile(new DumbSpike(boxX*30,boxY*30,4,spikeImgRaw));
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
            LevelEditor.save();
        }
        if (buttonSizeDown.getHover()) {
            sizeChange(false);
        }if (buttonSizeUp.getHover()) {
            sizeChange(true);
        }
    }

    public void pressed() {
        if(LevelEditor.getPlayingField().contains(LevelEditor.getTouchX(),LevelEditor.getTouchY())) {
            int boxX = (int) ((LevelEditor.getTouchX() - LevelEditor.getPlayingField().left) / (LevelEditor.getPlayingField().width() / (double) LevelEditor.getTilesInLevel()));
            int boxY = (int) ((LevelEditor.getTouchY() - LevelEditor.getPlayingField().top) / (LevelEditor.getPlayingField().height() / (double) LevelEditor.getTilesInLevel()));
            if(boxX!=0 && boxX != LevelEditor.getTilesInLevel()-1 && boxY != 0 && boxY != LevelEditor.getTilesInLevel()-1) {
                if (LevelEditor.getTileAt(boxX * 30, boxY * 30) instanceof DumbBox) {
                    selectedItem = "box";
                }
                if (LevelEditor.getTileAt(boxX * 30, boxY * 30) instanceof DumbCrate) {
                    selectedItem = "crate";
                }
                if((LevelEditor.getTileAt(boxX * 30, boxY * 30) instanceof DumbDoubleCrate && ((DumbDoubleCrate)LevelEditor.getTileAt(boxX * 30, boxY * 30)).getPosition()==1) || (LevelEditor.getTileAt(boxX * 30-30, boxY * 30) instanceof DumbDoubleCrate && ((DumbDoubleCrate)LevelEditor.getTileAt(boxX * 30-30, boxY * 30)).getPosition()==1)){
                    selectedItem = "doubleCrate2";
                    if((LevelEditor.getTileAt(boxX * 30-30, boxY * 30) instanceof DumbDoubleCrate)) {
                        LevelEditor.removeTile(boxX * 30 - 30, boxY * 30);
                    }
                }
                if((LevelEditor.getTileAt(boxX * 30, boxY * 30) instanceof DumbDoubleCrate && ((DumbDoubleCrate)LevelEditor.getTileAt(boxX * 30, boxY * 30)).getPosition()==2) || (LevelEditor.getTileAt(boxX * 30, boxY * 30-30) instanceof DumbDoubleCrate && ((DumbDoubleCrate)LevelEditor.getTileAt(boxX * 30, boxY * 30-30)).getPosition()==2)){
                    selectedItem = "doubleCrate";
                    if((LevelEditor.getTileAt(boxX * 30, boxY * 30-30) instanceof DumbDoubleCrate)) {
                        LevelEditor.removeTile(boxX * 30, boxY * 30 - 30);
                    }
                }
                if (LevelEditor.getTileAt(boxX * 30, boxY * 30) instanceof DumbEmptyCrate) {
                    selectedItem = "emptyCrate";
                }
                if (LevelEditor.getTileAt(boxX * 30, boxY * 30) instanceof DumbSpike) {
                    if (((DumbSpike) LevelEditor.getTileAt(boxX * 30, boxY * 30)).getPosition() == 1) {
                        selectedItem = "spike";
                    } else if (((DumbSpike) LevelEditor.getTileAt(boxX * 30, boxY * 30)).getPosition() == 2) {
                        selectedItem = "spike2";
                    } else if (((DumbSpike) LevelEditor.getTileAt(boxX * 30, boxY * 30)).getPosition() == 3) {
                        selectedItem = "spike3";
                    } else {
                        selectedItem = "spike4";
                    }
                }
                if (LevelEditor.getTileAt(boxX * 30, boxY * 30) instanceof DumbWall) {
                    selectedItem = "wall";
                }

                LevelEditor.removeTile(boxX * 30, boxY * 30);
            }
        }
        if(box.contains(LevelEditor.getTouchX(),LevelEditor.getTouchY())){
            selectedItem = "box";
        }
        if(crate.contains(LevelEditor.getTouchX(),LevelEditor.getTouchY())){
            selectedItem = "crate";
        }
        if(doubleCrate.contains(LevelEditor.getTouchX(),LevelEditor.getTouchY())){
            selectedItem = "doubleCrate";
        }
        if(doubleCrate2.contains(LevelEditor.getTouchX(),LevelEditor.getTouchY())){
            selectedItem = "doubleCrate2";
        }
        if(wall.contains(LevelEditor.getTouchX(),LevelEditor.getTouchY())){
            selectedItem = "wall";
        }
        if(emptyCrate.contains(LevelEditor.getTouchX(),LevelEditor.getTouchY())){
            selectedItem = "emptyCrate";
        }
        if(spike.contains(LevelEditor.getTouchX(),LevelEditor.getTouchY())){
            selectedItem = "spike";
        }
        if(spike2.contains(LevelEditor.getTouchX(),LevelEditor.getTouchY())){
            selectedItem = "spike2";
        }
        if(spike3.contains(LevelEditor.getTouchX(),LevelEditor.getTouchY())){
            selectedItem = "spike3";
        }
        if(spike4.contains(LevelEditor.getTouchX(),LevelEditor.getTouchY())){
            selectedItem = "spike4";
        }
    }

    public int getSize() {
        return LevelEditor.getTilesInLevel();
    }

    public void generateBorder(){
        for(int i=0;i<LevelEditor.getTilesInLevel();i++){
            LevelEditor.addTile(new DumbWall(30*i,0,wallImgRaw));
        }
        for(int i=0;i<LevelEditor.getTilesInLevel();i++){
            LevelEditor.addTile(new DumbWall(30*i,30*(LevelEditor.getTilesInLevel()-1),wallImgRaw));
        }
        for(int i=1;i<LevelEditor.getTilesInLevel()-1;i++){
            LevelEditor.addTile(new DumbWall(0,30*i,wallImgRaw));
        }
        for(int i=1;i<LevelEditor.getTilesInLevel()-1;i++){
            LevelEditor.addTile(new DumbWall(30*(LevelEditor.getTilesInLevel()-1),30*i,wallImgRaw));
        }
    }

    public void sizeChange(boolean big){
        int oldTiles = LevelEditor.getTilesInLevel();
        if(big) {
            LevelEditor.changeSize(1);
        } else {
            LevelEditor.changeSize(-1);
        }
        LevelEditor.removeTile(30*(oldTiles-1),30*(oldTiles-1));
        for(int i=0;i<LevelEditor.getTilesInLevel()+1;i++)
            LevelEditor.removeTile(30*i,0);
        for(int i=0;i<LevelEditor.getTilesInLevel()+1;i++)
            LevelEditor.removeTile(0,30*i);
        for(int i=1;i<LevelEditor.getTilesInLevel();i++)
            LevelEditor.removeTile(30*i,30*(oldTiles-1));
        for(int i=1;i<LevelEditor.getTilesInLevel();i++)
            LevelEditor.removeTile(30*(oldTiles-1),30*i);
        for(int i=1;i<LevelEditor.getTilesInLevel();i++){
            for(int r=1;r<LevelEditor.getTilesInLevel();r++){
                Tile t = LevelEditor.getTileAt(30*i,30*r);
                LevelEditor.removeTile(30*i,30*r);
                if(t instanceof DumbBox)
                    LevelEditor.addTile(new DumbBox(t.getX(),t.getY(),boxImgRaw));
                if(t instanceof DumbCrate && t.getX()<(LevelEditor.getTilesInLevel()-1)*30 && t.getY()<(LevelEditor.getTilesInLevel()-1)*30)
                    LevelEditor.addTile(new DumbCrate(t.getX(),t.getY(),crateImgRaw));
                if(t instanceof DumbWall && t.getX()<(LevelEditor.getTilesInLevel()-1)*30 && t.getY()<(LevelEditor.getTilesInLevel()-1)*30)
                    LevelEditor.addTile(new DumbWall(t.getX(),t.getY(),wallImgRaw));
                if(t instanceof DumbEmptyCrate && t.getX()<(LevelEditor.getTilesInLevel()-1)*30 && t.getY()<(LevelEditor.getTilesInLevel()-1)*30)
                    LevelEditor.addTile(new DumbEmptyCrate(t.getX(),t.getY(),emptyCrateImgRaw));
                if(t instanceof DumbSpike && t.getX()<(LevelEditor.getTilesInLevel()-1)*30 && t.getY()<(LevelEditor.getTilesInLevel()-1)*30)
                    LevelEditor.addTile(new DumbSpike(t.getX(),t.getY(),((DumbSpike) t).getPosition(),spikeImgRaw));
                if(t instanceof DumbDoubleCrate && ((DumbDoubleCrate) t).getPosition()==1 && t.getX()<(LevelEditor.getTilesInLevel()-2)*30 && t.getY()<(LevelEditor.getTilesInLevel()-1)*30)
                    LevelEditor.addTile(new DumbDoubleCrate(t.getX(), t.getY(), 1, doubleCrate2Img));
                if(t instanceof DumbDoubleCrate && ((DumbDoubleCrate) t).getPosition()==2 && t.getY()<(LevelEditor.getTilesInLevel()-2)*30 && t.getX()<(LevelEditor.getTilesInLevel()-1)*30){
                    LevelEditor.addTile(new DumbDoubleCrate(t.getX(),t.getY(),2,doubleCrate1Img));
                    Log.e("g",String.valueOf(t.getX())+","+String.valueOf((LevelEditor.getTilesInLevel()-1)*30));
                }

            }
        }
        generateBorder();
    }
}
