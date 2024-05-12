package com.example.teeplan;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Locale;

public class TimerService extends Service {
    public static final String TIMER_TICK_ACTION = "com.example.teeplan.timerTick";
    public static final String EXTRA_TIME_VALUE = "extra_time_value";
    private boolean isRunning = true;
    private static final String LOG_TAG = "TimerService";
    private CountDownTimer countDownTimer;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTimer();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startTimer(){
        long minutes = 0;
        long seconds = 100;

        long timeMilis = minutes * 60 * 1000 + seconds * 1000;
        countDownTimer = new CountDownTimer(timeMilis,50) {
            @Override
            public void onTick(long millisUntilFinished) {
                long hours = (millisUntilFinished/1000) /3600;
                long minutes = ((millisUntilFinished/1000) %3600)/60;
                long seconds = (millisUntilFinished/1000) %60;
                String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d",hours,minutes,seconds);
                Log.e(LOG_TAG,Long.toString((millisUntilFinished/1000) %60));

                Intent broadcastIntent = new Intent(TIMER_TICK_ACTION);
                broadcastIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                broadcastIntent.putExtra(EXTRA_TIME_VALUE, timeFormatted);
                sendBroadcast(broadcastIntent);
            }

            @Override
            public void onFinish() {
                Log.e(LOG_TAG, "Finished");
            }
        };
        countDownTimer.start();
    }

}