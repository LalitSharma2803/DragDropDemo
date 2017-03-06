package com.dragdropdemo;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MusicPlayer extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener {

    TextView songNameTV, begning_time, total_time;
    SeekBar timerSeekBar;
    ImageView previousImageView, playImageView, stopImageView, nextImageView, repeatCurrentImageView, repeatAllImageView;
    MediaPlayer mediaPlayer;
    private Handler mHandler = new Handler();
    private Utilities utils;
    private ArrayList<HashMap<String, Object>> songsList;
    private int songListSize;
    private int currentSong = 0;

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();

            total_time.setText("" + utils.milliSecondsToTimer(totalDuration));
            begning_time.setText("" + utils.milliSecondsToTimer(currentDuration));

            int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));
            timerSeekBar.setProgress(progress);
            mHandler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        initializeViewComponent();
    }

    private void initializeViewComponent() {
        utils = new Utilities();
        songsList = songsList();
        songListSize = songsList.size();

        songNameTV = (TextView) findViewById(R.id.songNameTV);
        begning_time = (TextView) findViewById(R.id.begning_time);
        total_time = (TextView) findViewById(R.id.total_time);

        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);

        previousImageView = (ImageView) findViewById(R.id.previousImageView);
        playImageView = (ImageView) findViewById(R.id.playImageView);
        stopImageView = (ImageView) findViewById(R.id.stopImageView);
        nextImageView = (ImageView) findViewById(R.id.nextImageView);
        repeatCurrentImageView = (ImageView) findViewById(R.id.repeatCurrentImageView);
        repeatAllImageView = (ImageView) findViewById(R.id.repeatAllImageView);

        previousImageView.setOnClickListener(this);
        playImageView.setOnClickListener(this);
        stopImageView.setOnClickListener(this);
        nextImageView.setOnClickListener(this);
        repeatCurrentImageView.setOnClickListener(this);
        repeatAllImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previousImageView:
                stopPlaying();
                playImageView.setImageDrawable(getResources().getDrawable(R.drawable.pause_button));
                playPrevious();
                break;
            case R.id.playImageView:
                if (mediaPlayer != null) {
                    if (!mediaPlayer.isPlaying()) {
                        startPlaying();
                        playImageView.setImageDrawable(getResources().getDrawable(R.drawable.pause_button));
                    } else {
                        pausePlaying();
                        playImageView.setImageDrawable(getResources().getDrawable(R.drawable.play));
                    }
                } else {
                    startPlaying();
                    playImageView.setImageDrawable(getResources().getDrawable(R.drawable.pause_button));
                }
                break;
            case R.id.stopImageView:
                stopPlaying();
                break;
            case R.id.nextImageView:
                stopPlaying();
                playImageView.setImageDrawable(getResources().getDrawable(R.drawable.pause_button));
                playNext();
                break;
            case R.id.repeatCurrentImageView:
                break;
            case R.id.repeatAllImageView:
                break;
        }
    }

    private void startPlaying() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(MusicPlayer.this, (Integer) songsList.get(currentSong).get("rawfilename"));
            mediaPlayer.setOnCompletionListener(this);
        }

        mediaPlayer.start();
        songNameTV.setText(songsList.get(currentSong).get("SongName").toString());
        updateProgressBar();
    }

    private void pausePlaying() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    private void playNext() {
        if (currentSong < songListSize - 1) {
            currentSong++;
        } else {
            currentSong = 0;
        }
        mediaPlayer = MediaPlayer.create(MusicPlayer.this, (Integer) songsList.get(currentSong).get("rawfilename"));
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.start();
        songNameTV.setText(songsList.get(currentSong).get("SongName").toString());
        updateProgressBar();
    }

    private void playPrevious() {
        if (currentSong > 0) {
            currentSong = currentSong - 1;
        } else {
            currentSong = songListSize - 1;
        }
        mediaPlayer = MediaPlayer.create(MusicPlayer.this, (Integer) songsList.get(currentSong).get("rawfilename"));
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.start();
        songNameTV.setText(songsList.get(currentSong).get("SongName").toString());
        updateProgressBar();
    }

    private void stopPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            playImageView.setImageDrawable(getResources().getDrawable(R.drawable.play));
            mHandler.removeCallbacks(mUpdateTimeTask);
            begning_time.setText("0:00");
            timerSeekBar.setProgress(0);
        }
    }

    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playNext();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }


    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mediaPlayer.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

        mediaPlayer.seekTo(currentPosition);

        updateProgressBar();
    }

    private ArrayList<HashMap<String, Object>> songsList() {
        ArrayList<HashMap<String, Object>> songsList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("SongName", "Hello1");
        hashMap.put("rawfilename", R.raw.older_students_pay_attention);
        songsList.add(hashMap);

        hashMap = new HashMap<String, Object>();
        hashMap.put("SongName", "Hello2");
        hashMap.put("rawfilename", R.raw.poner_atencion);
        songsList.add(hashMap);

        hashMap = new HashMap<String, Object>();
        hashMap.put("SongName", "Hello3");
        hashMap.put("rawfilename", R.raw.younger_students_pay_attention);
        songsList.add(hashMap);

        return songsList;
    }
}
