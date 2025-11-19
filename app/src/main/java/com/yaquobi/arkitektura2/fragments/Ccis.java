package com.yaquobi.arkitektura2.fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.yaquobi.arkitektura2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yaquobi.arkitektura2.activity.ARActivity;

public class Ccis extends Fragment {

    private MediaPlayer mediaPlayer = null;
    private ImageView currentPlayingButton = null; // track which button is active

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ccis, container, false);

        ImageView backArrow = view.findViewById(R.id.arrow);
        backArrow.setOnClickListener(v -> {
            stopAudio();
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Home())
                    .commit();
        });

        View previewBtn = view.findViewById(R.id.previewBtn);
        previewBtn.setOnClickListener(v -> {
            stopAudio();
            Intent intent = new Intent(requireContext(), ARActivity.class);
            intent.putExtra("MODEL_KEY", "CCIS");
            startActivity(intent);
        });

        View fab = view.findViewById(R.id.feedback);
        fab.setOnClickListener(v -> {
            stopAudio();
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Feedback())
                    .addToBackStack(null)
                    .commit();
        });

        View facultyBtn = view.findViewById(R.id.facultyBtn);
        facultyBtn.setOnClickListener(v -> {
            stopAudio();
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Faculty())
                    .addToBackStack(null)
                    .commit();
        });

        ImageView speakerHistory = view.findViewById(R.id.speakerHistory);
        ImageView speakerInfo = view.findViewById(R.id.speakerInfo);
        ImageView speakerDepartment = view.findViewById(R.id.speakerDept);

        // ✅ Audio click listener
        View.OnClickListener speakerClickListener = view1 -> {
            ImageView btn = (ImageView) view1;

            // If same button tapped while playing → stop audio
            if (currentPlayingButton == btn && mediaPlayer != null && mediaPlayer.isPlaying()) {
                stopAudio();
                return;
            }

            stopAudio(); // Stop any running audio first
            currentPlayingButton = btn;

            // Change button tint to Active color
            btn.setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.lighter),
                    PorterDuff.Mode.SRC_IN
            );

            // Play the correct file
            int audioRes = 0;
            if (btn == speakerHistory) audioRes = R.raw.ccis_history_audio;
            else if (btn == speakerInfo) audioRes = R.raw.ccis_info_audio;
            else if (btn == speakerDepartment) audioRes = R.raw.ccis_dept_audio;

            mediaPlayer = MediaPlayer.create(requireContext(), audioRes);
            mediaPlayer.start();

            // When finished, reset button
            mediaPlayer.setOnCompletionListener(mp -> stopAudio());
        };

        speakerHistory.setOnClickListener(speakerClickListener);
        speakerInfo.setOnClickListener(speakerClickListener);
        speakerDepartment.setOnClickListener(speakerClickListener);

        return view;
    }

    private void stopAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        // Reset button color if one was active
        if (currentPlayingButton != null) {
            currentPlayingButton.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN);
            currentPlayingButton = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAudio(); // ✅ Stop when leaving fragment
    }

    @Override
    public void onResume() {
        super.onResume();
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_navigation);
        bottomNav.setItemIconTintList(ColorStateList.valueOf(Color.BLACK));
        bottomNav.setItemTextColor(ColorStateList.valueOf(Color.BLACK));
    }
}
