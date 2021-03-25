package com.example.hw3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /* Declare our various views */
    Spinner background, overlap1, overlap2, overlap3;
    Button start, playAndPause, restart;
    ImageView soundPic;
    TextView songName, bgroundText, overlap1Text, overlap2Text, overlap3Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Initialize our views */
        background = findViewById(R.id.background_spinner);
        overlap1 = findViewById(R.id.overlap1_spinner);
        overlap2 = findViewById(R.id.overlap2_spinner);
        overlap3 = findViewById(R.id.overlap3_spinner);
        start = findViewById(R.id.start);
        playAndPause = findViewById(R.id.play_or_pause);
        restart = findViewById(R.id.restart);
        soundPic = findViewById(R.id.song_image);
        songName = findViewById(R.id.song_name);
        bgroundText = findViewById(R.id.background_text);
        overlap1Text = findViewById(R.id.overlap1);
        overlap2Text = findViewById(R.id.overlap2);
        overlap3Text = findViewById(R.id.overlap3);
    }
}