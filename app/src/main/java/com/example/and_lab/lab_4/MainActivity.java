package com.example.and_lab.lab_4;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextToSpeech textToSpeechObj;
    private String textToSpeechString;
    private MediaController controller;
    private VideoView videoView;

//    private MediaController.MediaPlayerControl mediaPlayer;
    private boolean mDragging;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread ad_thread = new Thread(new AdvertisementThread(getApplicationContext()));
//        Thread ad_thread = new Thread(new AdvertisementThread("An ad message can be entered here", getApplicationContext()));
//        Thread ad_thread = new Thread(new AdvertisementThread("Also ad message duration can be entered as a second argument", 7, getApplicationContext()));
        ad_thread.run();

        textToSpeechString = "Playback has completed";
        initializeTextToSpeech();

        initializeMediaPlayer();
        initializeMediaController();
        mProgress = findViewById(R.id.seekBar);


        //Customize Progress Bar Drawable
//        final int topContainerId1 = getResources().getIdentifier("mediacontroller_progress", "id", "android");
//        Log.v("VIEW", "onCreate: topContainerID1 = " + topContainerId1);
//        final SeekBar seekbar = controller.findViewById(topContainerId1);
//        seekbar.setProgressDrawable(getResources().getDrawable(R.drawable.gradient));


        videoView.setMediaController(controller);
        videoView.start();

//        final TextView text=(TextView)findViewById(R.id.textView);
        setSeekbarListener();
    }

    public void setSeekbarListener() {
        final SeekBar seek=(SeekBar) mProgress;

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int p=0;
            public void onStartTrackingTouch(SeekBar bar) {
                controller.show(3600000);
                mDragging = true;
                // By removing these pending progress messages we make sure
                // that a) we won't update the progress while the user adjusts
                // the seekbar and b) once the user is done dragging the thumb
                // we will post one of these messages to the queue again and
                // this ensures that there will be exactly one message queued up.
                //removeCallbacks(mShowProgress);

//                seek.setProgress(videoView.getBufferPercentage()*100);
            }
            @Override
            public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
//                if (!fromuser) {
//                    // We're not interested in programmatically generated changes to
//                    // the progress bar's position.
//                    return;
//                }
//                long duration = videoView.getDuration();
//                long newposition = (duration * progress) / 1000L;
//                videoView.seekTo( (int) newposition);
//              //  if (mCurrentTime != null)
//                //    mCurrentTime.setText(stringForTime( (int) newposition));

                if (videoView == null) {
                    return;
                }

                if (!fromuser) {
                    return;
                }

                long duration = videoView.getDuration();
                long newposition = (duration * progress) / 1000L;
                videoView.seekTo( (int) newposition);
//                if (mCurrentTime != null)
//                    mCurrentTime.setText(stringForTime( (int) newposition));
            }
            @Override
            public void onStopTrackingTouch(SeekBar bar) {
                //mDragging = false;
//                seek.setProgress(videoView.getBufferPercentage()*100);
                //updatePausePlay();
                //show(sDefaultTimeout);
                // Ensure that progress is properly updated in the future,
                // the call to show() does not guarantee this because it is a
                // no-op if we are already showing.
                //post(mShowProgress);
                mDragging = false;
                setProgress();
                controller.show(3000);
            }

        });
    }

    private int setProgress() {
        if (videoView == null || mDragging) {
            return 0;
        }

        int position = videoView.getCurrentPosition();
        int duration = videoView.getDuration();
        if (videoView != null) {
            if (duration > 0) {
                // use long to avoid overflow
                long pos = 1000L * position / duration;
                mProgress.setProgress( (int) pos);
            }
            int percent = videoView.getBufferPercentage();
            mProgress.setSecondaryProgress(percent * 10);
        }

//        if (mEndTime != null)
//            mEndTime.setText(stringForTime(duration));
//        if (mCurrentTime != null)
//            mCurrentTime.setText(stringForTime(position));

        return position;
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    protected void releasePlayer(){
        videoView.stopPlayback();
    }

    protected void initializeMediaPlayer(){
        videoView = findViewById(R.id.videoView);
        String pathString = "android.resource://" + getPackageName() + "/raw/x_hunter";
                Log.v("FILE_PATHS", "pathString = " + pathString);
        videoView.setVideoURI(Uri.parse(pathString));
        videoView.requestFocus();
    }

    protected void initializeMediaController(){
        controller = new MediaController(this);
//        controller.setMediaPlayer(mediaPlayer);
        controller.setAnchorView(videoView);
    }

    protected void initializeTextToSpeech(){
        textToSpeechObj = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                textToSpeechObj.setLanguage(Locale.US);
            }

        });
    }

    protected void StartTextToSpeech(){
        textToSpeechObj.speak(textToSpeechString, TextToSpeech.QUEUE_FLUSH, null);
    }


}
