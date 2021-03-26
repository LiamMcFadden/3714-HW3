package com.example.hw3;

import android.content.res.Configuration;
import android.media.MediaPlayer;

public class MusicPlayer implements MediaPlayer.OnCompletionListener {

    MediaPlayer player;
    long time;
    int currentPosition = 0;
    int musicIndex = 0;
    private int musicStatus = 0;//0: before playing, 1 playing, 2 paused
    private MusicService musicService;
    private PlayFragment playFragment;

    static final int[] MUSICPATH = new int[]{
            R.raw.gotechgo,
            R.raw.enter_sandman,
            R.raw.tech_triumph,
            R.raw.cheering,
            R.raw.clapping,
            R.raw.lestgohokies
    };

    static final String[] MUSICNAME = new String[]{
            "Go Tech Go!",
            "Enter Sandman",
            "Tech Triumph",
            "Cheering",
            "Clapping",
            "Go Hokies!"
    };

    public MusicPlayer(MusicService service) {

        this.musicService = service;
    }

    public void setSoundAndTime(int sound, long time) {
        musicIndex = sound;
        this.time = time;
    }

    public int getMusicStatus() {

        return musicStatus;
    }

    public void setPlayFragment(PlayFragment playFragment) {
        this.playFragment = playFragment;
    }

    public void updateFragmentImage(int id) {
        if (playFragment != null && playFragment.isVisible()) {
            playFragment.setImageView(id);
        }
        else {
            MainActivity.updateSongImage(id);
        }
    }

    public String getMusicName() {

        return MUSICNAME[musicIndex];
    }

    public void playMusic() {
        if (player != null)
            player.release();
        player= MediaPlayer.create(this.musicService, MUSICPATH[musicIndex]);
        player.start();
        player.setOnCompletionListener(this);
        musicService.onUpdateMusicName(getMusicName());
        musicStatus = 1;
    }

    public void restartMusic() {
        if (player != null)
            player.release();
        musicStatus = 0;

    }

    public void pauseMusic() {
        if(player!= null && player.isPlaying()){
            player.pause();
            currentPosition= player.getCurrentPosition();
            musicStatus= 2;
        }
    }

    public void resumeMusic() {
        if(player!= null){
            player.seekTo(currentPosition);
            player.start();
            musicStatus=1;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        player.release();
        if (MainActivity.portrait && playFragment != null && playFragment.isVisible())
            playFragment.setImageView(4);
        else
            MainActivity.updateSongImage(4);
        player= null;
    }
}