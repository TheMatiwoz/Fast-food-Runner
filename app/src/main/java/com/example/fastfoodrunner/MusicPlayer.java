package com.example.fastfoodrunner;

import android.app.Activity;
import android.media.MediaPlayer;

class MusicPlayer {

    private MediaPlayer mediaPlayer;
    private GameActivity activity;

    public MusicPlayer(GameActivity activity, int level){
        this.activity = activity;
        int[] musicList = new int[2];
        musicList[0] = R.raw.kfc_music;
        musicList[1] = R.raw.main_music;

        mediaPlayer = MediaPlayer.create(activity, musicList[level]);
        mediaPlayer.setLooping(true);
    }

    public void play(){
        mediaPlayer.start();
    }

    public void stop(){
        mediaPlayer.stop();
    }

    public void pointSound(){
        MediaPlayer sound = MediaPlayer.create(activity, R.raw.coinhit);
        sound.start();
    }

    public void collisionSound(){
        MediaPlayer sound = MediaPlayer.create(activity, R.raw.collision2);
        sound.start();
    }
}
