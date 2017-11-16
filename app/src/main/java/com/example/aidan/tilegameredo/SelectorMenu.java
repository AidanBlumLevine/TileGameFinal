package com.example.aidan.tilegameredo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

class SelectorMenu {
    private Rect popupArea;
    private Bitmap preview;
    private Level level;
    private LevelSelector parent;
    private Button share,play;
    private int textSize;
    public SelectorMenu(Level level, Bitmap preview, LevelSelector parent, Context context) {
        this.level=level;
        this.parent=parent;

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        int border = width/12;
        popupArea = new Rect(border,border,width-border,height-border);

        int buttonWidth =  (popupArea.width()-150)/2;
        share = new Button(popupArea.left+50,popupArea.bottom-50-buttonWidth/2,Bitmap.createScaledBitmap(ImageLoader.getButtonPlay(context),buttonWidth,buttonWidth/2,false));
        play = new Button(popupArea.left+100+buttonWidth,popupArea.bottom-50-buttonWidth/2,Bitmap.createScaledBitmap(ImageLoader.getButtonShare(context),buttonWidth,buttonWidth/2,false));

        int imageWidth = Math.min(popupArea.height()-300-buttonWidth/2,popupArea.width()-100);
        this.preview = Bitmap.createScaledBitmap(preview,imageWidth,imageWidth,false);

        Rect textArea = new Rect(popupArea.left+20,popupArea.top+20,popupArea.right-20,popupArea.top+180);
        Rect testRect = new Rect();
        int size = 100;
        Paint p = new Paint();
        p.setTextSize(size);
        p.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        p.getTextBounds(level.getName(),0,level.getName().length(),testRect);
        while(!(testRect.width()<textArea.width() && testRect.height()<textArea.height())){
            size--;
            p.setTextSize(size);
            p.getTextBounds(level.getName(),0,level.getName().length(),testRect);
        }
        textSize=size;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(230,255,255,255));
        canvas.drawRect(popupArea,paint);

        share.draw(canvas,paint);
        play.draw(canvas,paint);

        int imageY = (popupArea.height()-300-(popupArea.width()-150)/2/2-preview.getHeight())/2+popupArea.top+200;
        canvas.drawBitmap(preview,(popupArea.width()-preview.getWidth())/2+popupArea.left,imageY,paint);

        paint.setColor(Color.argb(200,0,0,0));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        int xPos = (popupArea.centerX());
        Rect textRect = new Rect();
        paint.getTextBounds(level.getName(),0,level.getName().length(),textRect);
        canvas.drawText(level.getName(), xPos, (imageY-popupArea.top)/2+popupArea.top+textRect.height()/2, paint);
        paint.reset();
    }

    public boolean touch(int x, int y, int type) {
        if(type == -1){
            if(share.getHover()){
                String shareUrl = "http://myonlinegrades.com/block/addLevel.php?name="+level.getName()+"&level="+level.getTilesString();
                Log.e("shareUrl",shareUrl);
                Share s = new Share();
                s.execute(shareUrl);
            }
            if(play.getHover()){
                parent.play();
            }
        }
        share.touch(x,y);
        play.touch(x,y);
        if(!popupArea.contains(x,y) && type == 1){
            return true;
        }
        return false;
    }

    private void shareResult(String s){
        Log.e("RESULT",s);
    }
    private class Share extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("START","ASYNC");
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.e("Response: ", "> " + line);

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            shareResult(result);
        }
    }
}
