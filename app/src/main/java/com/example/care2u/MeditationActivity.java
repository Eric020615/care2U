package com.example.care2u;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MeditationActivity extends AppCompatActivity {

    // count down function
    private static final long START_TIME_IN_MILLIS = 600000; // 10 minutes
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    private TextView mTextViewCountDown;
    private Button mButtonStartPause,mButtonReset;
    private ImageView backButton;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    // music play function
    private MediaPlayer sound;
    private ImageView playpause,previous,next;

    private int[] songs = {R.raw.music1, R.raw.music2};
    private int currentSong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);
        // count down function
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);
        backButton = findViewById(R.id.meditation_back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                finish();
            }
        });

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    v.startAnimation(buttonClick);
                    pauseTimer();
                } else {
                    v.startAnimation(buttonClick);
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                resetTimer();
            }
        });

        playpause = findViewById(R.id.Btn_play_music);
        previous = findViewById(R.id.Btn_previous);
        next = findViewById(R.id.Btn_next);

        sound = MediaPlayer.create(getApplicationContext(),songs[currentSong]);
        playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sound.isPlaying()) {
                    view.startAnimation(buttonClick);
                    startMusic();
                } else {
                    view.startAnimation(buttonClick);
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
                    view.startAnimation(buttonClick);
                    currentSong = (currentSong - 1) % songs.length;
                } else {
                    view.startAnimation(buttonClick);
                    currentSong = (songs.length - 1) % songs.length;
                }
                sound = MediaPlayer.create(getApplicationContext(),songs[currentSong]);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // stop music and release it
                view.startAnimation(buttonClick);
                sound.stop();
                sound.release();

                // increase the index to the next music
                currentSong = (currentSong + 1) % songs.length;
                sound = MediaPlayer.create(getApplicationContext(),songs[currentSong]);
            }
        });


        // output
        updateCountDownText();
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
