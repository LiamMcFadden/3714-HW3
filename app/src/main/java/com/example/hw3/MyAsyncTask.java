package com.example.hw3;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

@SuppressLint("StaticFieldLeak")
public class MyAsyncTask extends AsyncTask<String, String, String> {
    MusicPlayer player;
    boolean paused = false;

    public MyAsyncTask(MusicPlayer player) {
        this.player = player;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            Thread.sleep(player.time * 1000);
            player.playMusic();
            if (player.musicIndex > 2 && MainActivity.portrait)
                player.updateFragmentImage(player.musicIndex-2);
            else if (player.musicIndex > 2)
                MainActivity.updateSongImage(player.musicIndex-2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgressUpdate(String... text) {
        super.onProgressUpdate(text);
    }
}

