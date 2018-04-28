package com.example.aidan.tilegameredo;


import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundPlayer {
    private AudioAttributes audioAttributes;
    private SoundPool soundPool;
    private static int slideSound;
    private static int hitSound;

    public SoundPlayer(Context context){
        if(Build.VERSION.SDK_INT >= 21){
            audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .build();

            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes)
                                                .setMaxStreams(3)
                                                .build();

        } else {
            soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        }

        slideSound = soundPool.load(context,R.raw.slidesound2,1);
        hitSound = soundPool.load(context,R.raw.hitsound1,1);
    }

    public void playSlideSound(){
        soundPool.play(slideSound,0.2f,0.2f,1,0,0.5f);
    }
    public void stopSlideSound(){
        soundPool.stop(slideSound);
    }

    public void playHitSound() {
        soundPool.play(hitSound,1.0f,1.0f,1,0,1.0f);
    }
}
