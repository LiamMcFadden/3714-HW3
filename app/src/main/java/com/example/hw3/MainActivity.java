package com.example.hw3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static final int[] SONG_LENGTHS = {49, 331, 111};
    /* Declare our various views */
    Spinner background, overlap1, overlap2, overlap3;
    Button start, playAndPause, restart;
    ImageView soundPic;
    TextView songName, bgroundText, overlap1Text, overlap2Text, overlap3Text;
    SeekBar overlap1Seek, overlap2Seek, overlap3Seek;
    static ImageView songImage;

    /* Sound fields */
    static MusicService musicService;
    MusicCompletionReceiver musicCompletionReceiver;
    Intent startMusicServiceIntent;
    int music;
    int[] sounds;
    long[] timings;

    private PlayFragment playFragment;

    /* Saved Instance fields */
    public static final String INITIALIZE_STATUS = "initialization status";
    public static final String MUSIC_PLAYING = "music playing";
    boolean isBound = false;
    boolean isInitialized = false;
    static boolean portrait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        portrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        if (!portrait) songImage = findViewById(R.id.song_image);
        music = 0;
        sounds = new int[3];
        timings = new long[3];
        /* Initialize our views */
        background = findViewById(R.id.background_spinner);
        overlap1 = findViewById(R.id.overlap1_spinner);
        overlap2 = findViewById(R.id.overlap2_spinner);
        overlap3 = findViewById(R.id.overlap3_spinner);
        start = findViewById(R.id.start);
        playAndPause = findViewById(R.id.play_or_pause);
        restart = findViewById(R.id.restart);
        soundPic = findViewById(R.id.song_image);
        bgroundText = findViewById(R.id.background_text);
        overlap1Text = findViewById(R.id.overlap1);
        overlap2Text = findViewById(R.id.overlap2);
        overlap3Text = findViewById(R.id.overlap3);
        overlap1Seek = findViewById(R.id.o1_seek);
        overlap2Seek = findViewById(R.id.o2_seek);
        overlap3Seek = findViewById(R.id.o3_seek);
        playFragment = new PlayFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, playFragment)
                .hide(playFragment)
                .commit();

        /* Set up the spinners */
        ArrayAdapter<CharSequence> backgroundAdapter = ArrayAdapter.createFromResource(this, R.array.songs, android.R.layout.simple_spinner_item);
        backgroundAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        background.setAdapter(backgroundAdapter);
        background.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> overlap1Adapter = ArrayAdapter.createFromResource(this, R.array.sounds, android.R.layout.simple_spinner_item);
        overlap1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        overlap1.setAdapter(overlap1Adapter);
        overlap1.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> overlap2Adapter = ArrayAdapter.createFromResource(this, R.array.sounds, android.R.layout.simple_spinner_item);
        overlap2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        overlap2.setAdapter(overlap2Adapter);
        overlap2.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> overlap3Adapter = ArrayAdapter.createFromResource(this, R.array.sounds, android.R.layout.simple_spinner_item);
        overlap3Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        overlap3.setAdapter(overlap3Adapter);
        overlap3.setOnItemSelectedListener(this);

        /* Set button click listeners */
        if (portrait)
            start.setOnClickListener(this);
        if (!portrait) {
            playAndPause.setOnClickListener(this);
            restart.setOnClickListener(this);
        }

        if (savedInstanceState != null) {
            isInitialized = savedInstanceState.getBoolean(INITIALIZE_STATUS);
            music = savedInstanceState.getInt(MUSIC_PLAYING);
            if (!portrait) {
                songName = findViewById(R.id.song_name);
                songName.setText(musicService.musicPlayer.getMusicName());
                switch (musicService.getPlayingStatus()) {
                    case 1:
                        playAndPause.setText(R.string.play);
                        break;
                    case 2:
                        playAndPause.setText(R.string.pause);
                    case 3:
                        playAndPause.setText(R.string.play);
                        break;
                }
            }
        }

        /* initialize sound fields */
        startMusicServiceIntent= new Intent(this, MusicService.class);

        if (!isInitialized) {
            startService(startMusicServiceIntent);
            isInitialized = true;
        }

        musicCompletionReceiver = new MusicCompletionReceiver(this);
    }

    @Override
    public void onClick(View view) {
        if (isBound) {
            setSoundAndTime();
            musicService.setSoundAndTime(music, sounds, timings);
            /* Play song and set button text to back */
            if (portrait && view.getId() == start.getId()) {
                /* get fields */
                if (playFragment != null)
                    playFragment.playOrPause.setText(R.string.pause);
                music = background.getSelectedItemPosition();
                sounds[0] = overlap1.getSelectedItemPosition() + 3;
                sounds[1] = overlap2.getSelectedItemPosition() + 3;
                sounds[2] = overlap3.getSelectedItemPosition() + 3;

                start.setText(R.string.back);
                if (!playFragment.isVisible()) {
                    getSupportFragmentManager().beginTransaction()
                            .show(playFragment)
                            .commit();
                    musicService.setPlayFragment(playFragment);
                    musicService.startMusic();
                    playFragment.setSongName(musicService.getSongName());
                }
                /* go back to edit screen */
                else {
                    start.setText(R.string.play);
                    getSupportFragmentManager().beginTransaction()
                            .hide(playFragment)
                            .commit();
                }
            }
            else if (!portrait) {
                if (view.getId() == playAndPause.getId()) {
                    switch (musicService.getPlayingStatus()) {
                        case 0:
                            musicService.startMusic();
                            playAndPause.setText(R.string.pause);
                            break;
                        case 1:
                            musicService.pauseMusic();
                            playAndPause.setText(R.string.play);
                            break;
                        case 2:
                            MainActivity.musicService.resumeMusic();
                            playAndPause.setText(R.string.pause);
                            break;
                    }
                }
                else if (view.getId() == restart.getId()) {
                    musicService.restart();
                    playAndPause.setText(R.string.play);
                    songImage.setImageResource(R.drawable.default_img);
                }
            }
        }
    }

    public static void updateSongImage(int id) {
        switch (id) {
            case 1:
                songImage.setImageResource(R.drawable.cheering);
                break;
            case 2:
                songImage.setImageResource(R.drawable.clapping);
                break;
            case 3:
                songImage.setImageResource(R.drawable.letsgohokes);
                break;
            case 4:
                songImage.setImageResource(R.drawable.default_img);
                break;
        }
    }

    private void setSoundAndTime() {
        music = background.getSelectedItemPosition();
        sounds[0] = overlap1.getSelectedItemPosition() + 3;
        sounds[1] = overlap2.getSelectedItemPosition() + 3;
        sounds[2] = overlap3.getSelectedItemPosition() + 3;
        timings[0] = overlap1Seek.getProgress() * SONG_LENGTHS[music] / 100;
        timings[1] = overlap2Seek.getProgress() * SONG_LENGTHS[music] / 100;
        timings[2] = overlap3Seek.getProgress() * SONG_LENGTHS[music] / 100;
    }

    public void updateName(String musicName) {
        songName.setText(musicName);
    }

    protected void onResume() {
        super.onResume();
        if(isInitialized && !isBound){
            bindService(startMusicServiceIntent, musicServiceConnection, Context.BIND_AUTO_CREATE);
        }

        registerReceiver(musicCompletionReceiver, new IntentFilter(MusicService.COMPLETE_INTENT));
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(isBound){
            unbindService(musicServiceConnection);
            isBound= false;
        }

        unregisterReceiver(musicCompletionReceiver);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(INITIALIZE_STATUS, isInitialized);
        outState.putInt(MUSIC_PLAYING, music);
        super.onSaveInstanceState(outState);
    }


    private ServiceConnection musicServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService.MyBinder musicBinder = (MusicService.MyBinder) iBinder;
            musicService = musicBinder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicService = null;
            isBound = false;
        }
    };
}