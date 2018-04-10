package com.example.aidan.tilegameredo;

import android.app.Activity;
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
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.aidan.tilegameredo.levelEditor.LevelEditorScreen;

import java.util.ArrayList;

class SelectorMenu {
    private ArrayList<int[]> touches = new ArrayList<int[]>();
    private Rect popupArea,starArea,textArea,previewArea;
    private Bitmap preview;
    private Level level;
    private LevelSelector parent;
    private Button play,edit,delete,back;
    private int textSize;
    private Context context;
    private Bitmap starEmpty,starFull;
    public SelectorMenu(Level level, Bitmap preview, LevelSelector parent, Context context) {
        this.level=level;
        this.parent=parent;
        this.context = context;

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        int border = width/12;
        popupArea = new Rect(border/2,border/2,width-border/2,height-border/2);

        previewArea = new Rect(popupArea.left,popupArea.centerY()-popupArea.height()/4,popupArea.right,popupArea.centerY()+popupArea.height()/4);
        int imageWidth = Math.min(previewArea.height()-100,popupArea.width()-100);
        this.preview = Bitmap.createScaledBitmap(preview,imageWidth,imageWidth,false);

        if(parent.getTab() == "custom") {
            textArea = new Rect(popupArea.left + 20 + popupArea.width() / 4, popupArea.top + border, popupArea.right - 20, previewArea.top - border);
        } else {
            textArea = new Rect(popupArea.right-border/2-(previewArea.top-popupArea.top-2*border), popupArea.top + border, popupArea.right - border/2, previewArea.top - border);
        }
        back = new Button(popupArea.left+border/2,textArea.top+border/2,Bitmap.createScaledBitmap(Loader.getButtonBack(context),textArea.height()-border,textArea.height()-border,false));

        play = new Button(popupArea.left+border,previewArea.bottom+border, (popupArea.centerX()-border)-(popupArea.left+border),(popupArea.bottom-border)-(previewArea.bottom+border),Color.rgb(65,99,135),"PLAY",48,Loader.getFont(context));
        if(parent.getTab() == "custom") {
            edit = new Button(popupArea.centerX() + popupArea.width() / 6 - (popupArea.height()/10)/2, previewArea.bottom + border, Bitmap.createScaledBitmap(Loader.getButtonEdit(context), popupArea.height()/10, popupArea.height()/10, false));
            delete = new Button(popupArea.centerX() + 2 * popupArea.width() / 6 - (popupArea.height()/10)/2, previewArea.bottom + border, Bitmap.createScaledBitmap(Loader.getButtonTrash(context), popupArea.height()/10, popupArea.height()/10, false));
        } else {
            edit = new Button(popupArea.centerX() + popupArea.width() / 4 - (popupArea.height()/8)/2, previewArea.bottom + border, Bitmap.createScaledBitmap(Loader.getButtonEdit(context), popupArea.height()/8, popupArea.height()/8, false));
        }


        Rect testRect = new Rect();
        int size = 300;
        String levelName = level.getName();
        if(parent.getTab().equals("custom")){
            levelName = levelName.substring(0,levelName.length()-6);
        }
        Paint p = new Paint();
        p.setTextSize(size);
        p.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        p.getTextBounds(levelName,0,levelName.length(),testRect);
        while(!(testRect.width()<textArea.width() && testRect.height()<textArea.height()-border)){
            size--;
            p.setTextSize(size);
            p.getTextBounds(levelName,0,levelName.length(),testRect);
        }
        textSize=size;

        starArea = new Rect(popupArea.left+textArea.height(),textArea.top,textArea.left,textArea.bottom);

        starEmpty = Bitmap.createScaledBitmap(Loader.getStarBlueBorder(context),starArea.width()/5,starArea.width()/5,false);
        starFull = Bitmap.createScaledBitmap(Loader.getStarBorder(context),starArea.width()/5,starArea.width()/5,false);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.save();
        canvas.clipRect(popupArea);
        paint.setAlpha(240);
        Loader.drawShiftBackground(canvas,paint);
        canvas.restore();
        paint.setStrokeWidth(10);
        paint.setColor(Color.DKGRAY);
        canvas.drawLine(popupArea.left-5,popupArea.top,popupArea.right+5,popupArea.top,paint);
        canvas.drawLine(popupArea.left-5,popupArea.bottom,popupArea.right+5,popupArea.bottom,paint);
        canvas.drawLine(popupArea.left,popupArea.top-5,popupArea.left,popupArea.bottom+5,paint);
        canvas.drawLine(popupArea.right,popupArea.top-5,popupArea.right,popupArea.bottom+5,paint);

        play.draw(canvas,paint);
        edit.draw(canvas,paint);
        back.draw(canvas,paint);
        if(delete != null) {
            delete.draw(canvas, paint);
        }

        int imageY = previewArea.centerY()-preview.getHeight()/2;
        canvas.drawBitmap(preview,(popupArea.width()-preview.getWidth())/2+popupArea.left,imageY,paint);

        paint.setColor(Color.argb(200,0,0,0));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        int xPos = (textArea.centerX());
        String levelName = level.getName();
        if(parent.getTab().equals("custom")){
            //STRING OUT OF BOUNDS HERE ++++++++++++!+!+@+@+!@+!++#+!@+#+@#++$#+#!+$!+#+$#+$+!@#+$!+#$+%+$++!$#+$+!#+$+!#$+#+$+!@#$+!@#+!@+#+!@#+!@+#+!@#+@!+$+#@
            levelName = levelName.substring(0,levelName.length()-6);
        }
        Rect textRect = new Rect();
        paint.getTextBounds(levelName,0,levelName.length(),textRect);
        canvas.drawText(levelName, xPos, textArea.centerY()+textRect.height()/2, paint);
        paint.reset();

        if(parent.getTab() == "default") {
            if (level.getStars() > 0) {
                canvas.drawBitmap(starFull, starArea.left+starArea.width()/10, starArea.centerY()-starArea.width()/10, paint);
            } else {
                canvas.drawBitmap(starEmpty, starArea.left+starArea.width()/10, starArea.centerY()-starArea.width()/10, paint);
            }

            if (level.getStars() > 1) {
                canvas.drawBitmap(starFull, starArea.centerX() - starArea.width() / 10, starArea.centerY()-starArea.width()/10, paint);
            } else {
                canvas.drawBitmap(starEmpty, starArea.centerX() - starArea.width() / 10, starArea.centerY()-starArea.width()/10, paint);
            }

            if (level.getStars() > 2) {
                canvas.drawBitmap(starFull, starArea.right-3*starArea.width()/10, starArea.centerY()-starArea.width()/10, paint);
            } else {
                canvas.drawBitmap(starEmpty,   starArea.right-3*starArea.width()/10, starArea.centerY()-starArea.width()/10, paint);
            }
        }
    }

    public boolean touch(int x, int y, int type) {
        if(type == -1){
            if(back.getHover()){
                parent.closePopup();
            }
            if(play.getHover()){
                parent.play();
            }
            if (edit.getHover()){
                Intent i = new Intent(context,LevelEditorScreen.class);
                i.putExtra("level",level.toString());
                context.startActivity(i);
            }
            if (delete != null && delete.getHover() && !parent.getTab().equals("default")) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        deleteLevel();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to delete this level?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();
                    }
                });
            }
        }
        play.touch(x,y);
        edit.touch(x,y);
        back.touch(x,y);
        if(delete != null){
            delete.touch(x,y);
        }
        if(!popupArea.contains(x,y) && type == 1){
            return true;
        }
        return false;
    }

    private void deleteLevel(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        Level deleteLevel = level;
        editor.remove(deleteLevel.getName());
        String newNamesList = settings.getString(parent.getTab()+"LevelNames","");
        editor.putString(parent.getTab()+"LevelNames",newNamesList.replace(deleteLevel.getName()+",",""));
        editor.remove(deleteLevel.getName()+parent.getTab());
        editor.commit();
        Intent i = new Intent(context,SelectorScreen.class);
        context.startActivity(i);
        ((AppCompatActivity)context).overridePendingTransition(R.anim.up_to_mid,R.anim.mid_to_down);
        Loader.loadCustomLevels(context);
    }
}
