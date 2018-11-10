package com.example.and_lab.lab_4;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextToSpeech textToSpeechObj;
    private EditText write;
    private String textToSpeechString;
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
