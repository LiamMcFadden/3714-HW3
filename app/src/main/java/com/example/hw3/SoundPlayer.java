package com.example.hw3;

import android.media.MediaPlayer;

public class SoundPlayer implements MediaPlayer.OnCompletionListener {
    MediaPlayer player;
    int currentPosition = 0;
    int soundIndex = 0;
    private int musicStatus = 0;//0: before playing, 1 playing, 2 paused
    private MusicService musicService;

    static final int[] SOUNDPATH = new int[]{
            R.raw.cheering,
            R.raw.clapping,
            R.raw.lestgohokies
    };

    static final String[] SOUNDNAME = new String[]{
            "Cheering",
            "Clapping",
            "Go Hokies!"
    };

    public SoundPlayer(MusicService service) {
        this.musicService = service;
    }


    public int getMusicStatus() {
        return musicStatus;
    }

    public String getMusicName() {

        return SOUNDNAME[soundIndex];
    }

    public void playMusic() {
        player= MediaPlayer.create(this.musicService, SOUNDPATH[soundIndex]);
        player.start();
        player.setOnCompletionListener(this);
        musicService.onUpdateMusicName(getMusicName());
        musicStatus = 1;
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
        player= null;
    }
}

}
