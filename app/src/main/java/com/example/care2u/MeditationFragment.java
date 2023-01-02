package com.example.care2u;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class MeditationFragment extends Fragment {
    // count down function
    private static final long START_TIME_IN_MILLIS = 600000; // 10 minutes

    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    // music play function
    private MediaPlayer sound;
    private Button playpause,previous,next;

    private int[] songs = {R.raw.music1, R.raw.music2};
    private int currentSong = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meditation, container, false);

        // count down function
        mTextViewCountDown = rootView.findViewById(R.id.text_view_countdown);
        mButtonStartPause = rootView.findViewById(R.id.button_start_pause);
        mButtonReset = rootView.findViewById(R.id.button_reset);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        // music play function
        playpause = rootView.findViewById(R.id.Btn_play_music);
        previous = rootView.findViewById(R.id.Btn_previous);
        next = rootView.findViewById(R.id.Btn_next);

        sound = MediaPlayer.create(getActivity(),songs[currentSong]);
        playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sound.isPlaying()) {
                    startMusic();
                } else {
                    sound.pause();
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // stop music and release it
                sound.stop();
                sound.release();

                // decrease the index to the previous music
                if(currentSong != 0){
                    currentSong = (currentSong - 1) % songs.length;
                } else {
                    currentSong = (songs.length - 1) % songs.length;
                }
                sound = MediaPlayer.create(getActivity(),songs[currentSong]);
                startMusic();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // stop music and release it
                sound.stop();
                sound.release();

                // increase the index to the next music
                currentSong = (currentSong + 1) % songs.length;
                sound = MediaPlayer.create(getActivity(),songs[currentSong]);
                startMusic();
            }
        });


        // output
        updateCountDownText();

        return rootView;
    }

    // count down function
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String updateCountDownText = "" + minutes;
        updateCountDownText += ":";
        if(seconds < 10) updateCountDownText += "0";
        updateCountDownText += "" + seconds;

        mTextViewCountDown.setText(updateCountDownText);
    }

    // music play function
    public void startMusic(){
        sound.setLooping(true);
        sound.start();
    }

}