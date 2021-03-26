package com.example.hw3;

import android.os.Bundle;
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
    private Button playOrPause, restart;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.play_fragment, container, false);
        songName = v.findViewById(R.id.song_name);
        playOrPause = v.findViewById(R.id.play_or_pause);
        restart = v.findViewById(R.id.restart);
        setRetainInstance(true);

        View.OnClickListener ClickListener = r -> {
            if (r.getId() == playOrPause.getId()) {

            }
            else if (r.getId() == restart.getId()) {

            }
        };
        return v;
    }

    public void updateTextView(CharSequence text) {
        songName.setText(text);
    }
}
