package com.example.hw3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /* Declare our various views */
    Spinner background, overlap1, overlap2, overlap3;
    Button start, playAndPause, restart;
    ImageView soundPic;
    TextView songName, bgroundText, overlap1Text, overlap2Text, overlap3Text;
    SeekBar overlap1Seek, overlap2Seek, overlap3Seek;

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
        overlap1Seek = findViewById(R.id.o1_seek);
        overlap2Seek = findViewById(R.id.o2_seek);
        overlap3Seek = findViewById(R.id.o3_seek);

        /* Set up the spinners */
        createSpinner(R.array.songs, background);
        createSpinner(R.array.sounds, overlap1);
        createSpinner(R.array.sounds, overlap2);
        createSpinner(R.array.sounds, overlap3);
    }

    public void updateName(String musicName) {
        songName.setText(musicName);
    }

    private void createSpinner(int items, Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}