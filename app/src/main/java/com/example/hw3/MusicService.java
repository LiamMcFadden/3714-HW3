package com.example.hw3;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MusicService extends Service {

    MusicPlayer musicPlayer;
    MusicPlayer sound1Player;
    MusicPlayer sound2Player;
    MusicPlayer sound3Player;
    private final IBinder iBinder= new MyBinder();

    public static final String COMPLETE_INTENT = "complete intent";
    public static final String MUSICNAME = "music name";

    @Override
    public void onCreate() {
        super.onCreate();
        musicPlayer = new MusicPlayer(this);
        sound1Player = new MusicPlayer(this);
        sound2Player = new MusicPlayer(this);
        sound3Player = new MusicPlayer(this);
    }

    public void startMusic(){
        musicPlayer.playMusic();
        sound1Player.playMusic();
        sound2Player.playMusic();
        sound3Player.playMusic();
    }

    public void pauseMusic(){
        musicPlayer.pauseMusic();
        sound1Player.pauseMusic();
        sound2Player.pauseMusic();
        sound3Player.pauseMusic();
    }

    public void resumeMusic(){
        musicPlayer.resumeMusic();
        sound1Player.resumeMusic();
        sound2Player.resumeMusic();
        sound3Player.resumeMusic();
    }

    public int getPlayingStatus(){
        return musicPlayer.getMusicStatus();
    }

    public void setSoundAndTime(int song, int[] sounds, int[] times) {
        musicPlayer.setSoundAndTime(song, 0);
        sound1Player.setSoundAndTime(sounds[0], times[0]);
        sound2Player.setSoundAndTime(sounds[1], times[1]);
        sound3Player.setSoundAndTime(sounds[2], times[2]);
    }

    public void onUpdateMusicName(String musicname) {
        Intent intent = new Intent(COMPLETE_INTENT);
        intent.putExtra(MUSICNAME, musicname);
        sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return iBinder;
    }


    public class MyBinder extends Binder {

        MusicService getService(){
            return MusicService.this;
        }
    }
}