package com.example.hw3;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlayFragment extends Fragment {
    private TextView songName;
    Button playOrPause, restart;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.play_fragment, container, false);
        songName = v.findViewById(R.id.song_name);
        playOrPause = v.findViewById(R.id.play_or_pause);
        restart = v.findViewById(R.id.restart);
        playOrPause.setText(R.string.pause);
        imageView = v.findViewById(R.id.song_image);
        setRetainInstance(true);

        View.OnClickListener ClickListener = r -> {
            if (r.getId() == playOrPause.getId()) {
                switch (MainActivity.musicService.getPlayingStatus()) {
                    case 0:
                        MainActivity.musicService.startMusic();
                        playOrPause.setText(R.string.pause);
                        break;
                    case 1:
                        MainActivity.musicService.pauseMusic();
                        playOrPause.setText(R.string.play);
                        break;
                    case 2:
                        MainActivity.musicService.resumeMusic();
                        playOrPause.setText(R.string.pause);
                        break;
                }
            }
            else if (r.getId() == restart.getId()) {
                MainActivity.musicService.restart();
                playOrPause.setText(R.string.play);
                imageView.setImageResource(R.drawable.default_img);
            }
        };
        playOrPause.setOnClickListener(ClickListener);
        restart.setOnClickListener(ClickListener);
        return v;
    }

    public void setImageView(int id) {
        if (MainActivity.portrait) {
            switch (id) {
                case 1:
                    imageView.setImageResource(R.drawable.cheering);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.clapping);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.letsgohokes);
                    break;
                case 4:
                    imageView.setImageResource(R.drawable.default_img);
                    break;
            }
        }
    }

    public void setSongName(String name) {
        songName.setText(name);
    }
}
