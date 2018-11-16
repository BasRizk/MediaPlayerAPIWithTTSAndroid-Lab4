package com.example.and_lab.lab_4;

import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextToSpeech textToSpeechObj;
    private String textToSpeechString;
    private MediaController controller;
    private VideoView videoView;

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


        //Customize Progress Bar Drawable
        final int topContainerId1 = getResources().getIdentifier("mediacontroller_progress", "id", "android");
        Log.v("VIEW", "onCreate: topContainerID1 = " + topContainerId1);
        final SeekBar seekbar = controller.findViewById(topContainerId1);
        seekbar.setProgressDrawable(getResources().getDrawable(R.drawable.gradient));


        videoView.setMediaController(controller);
        videoView.start();

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
