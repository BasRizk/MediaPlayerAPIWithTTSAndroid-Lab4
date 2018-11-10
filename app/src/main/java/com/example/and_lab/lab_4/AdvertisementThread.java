package com.example.and_lab.lab_4;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;
import android.os.Handler;

public class AdvertisementThread implements Runnable {

    CharSequence advertisement = "Enjoying our music? subscribe for more";
    int duration = Toast.LENGTH_LONG;
    Context context;
    private final Handler handler = new Handler();

    AdvertisementThread(CharSequence advertisement, int duration, Context context) {
        this.advertisement = advertisement;
        this.duration = duration;
        this.context = context;
    }

    AdvertisementThread(CharSequence advertisement, Context context) {
        this.advertisement = advertisement;
        this.context = context;
    }

    AdvertisementThread(Context context) {
        this.context = context;
    }

    public void run() {
        handler.postDelayed(this, 10000);
        Toast.makeText(context, advertisement, duration).show();
    }

}
