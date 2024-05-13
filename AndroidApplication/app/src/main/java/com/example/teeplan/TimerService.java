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
    public static final String INTERVAL_CHANGE_ACTION = "com.example.teeplan.intervalChange";
    public static final String EXTRA_TIME_VALUE = "extra_time_value";
    private boolean isRunning = true;
    private boolean isWorkInterval = true;
    private static final String LOG_TAG = "TimerService";
    private CountDownTimer countDownTimer;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTimer();


        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startTimer() {
        long minutes = isWorkInterval ? 0 : 0;
        long seconds = isWorkInterval ? 5 : 10;

        long timeMilis = minutes * 60 * 1000 + seconds * 1000 + 1000;
        countDownTimer = new CountDownTimer(timeMilis, 20) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutesString = ((millisUntilFinished / 1000) % 3600) / 60;
                long secondsString = (millisUntilFinished / 1000) % 60;
                String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutesString, secondsString);

                Intent broadcastIntent = new Intent(TIMER_TICK_ACTION);
                broadcastIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                broadcastIntent.putExtra(EXTRA_TIME_VALUE, timeFormatted);
                sendBroadcast(broadcastIntent);
            }

            @Override
            public void onFinish() {
                Log.e(LOG_TAG, "Finished");
                Intent broadcastIntent = new Intent(INTERVAL_CHANGE_ACTION);
                broadcastIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(broadcastIntent);

                isWorkInterval = !isWorkInterval;

                startTimer();
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "TimerService onDestroy");
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }


}