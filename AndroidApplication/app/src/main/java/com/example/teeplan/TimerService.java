package com.example.teeplan;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import java.util.Locale;

public class TimerService extends Service {
    public static final String TIMER_TICK_ACTION = "com.example.teeplan.timerTick";
    public static final String INTERVAL_CHANGE_ACTION = "com.example.teeplan.intervalChange";
    public static final String EXTRA_TIME_VALUE = "extra_time_value";
    public static final String EXTRA_STATUS_VALUE = "extra_status_value";
    private boolean isRunning = true;
    private boolean isWorkInterval = true;
    private static final String LOG_TAG = "TimerService";
    private CountDownTimer countDownTimer;
    String timeFormatted;
    long minutesWork, minutesBreak, secondsWork, secondsBreak;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        minutesWork = intent.getIntExtra("minutesWork", 25);
        secondsWork = intent.getIntExtra("secondsWork", 0);
        minutesBreak = intent.getIntExtra("minutesBreak", 5);
        secondsBreak = intent.getIntExtra("secondsBreak", 0);
        startTimer();

        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }


    private void startTimer() {
        long minutes = isWorkInterval ? minutesWork : minutesBreak;
        long seconds = isWorkInterval ? secondsWork : secondsBreak;

        long timeMilis = minutes * 60 * 1000 + seconds * 1000 + 999;
        countDownTimer = new CountDownTimer(timeMilis, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutesString = ((millisUntilFinished / 1000) % 3600) / 60;
                long secondsString = (millisUntilFinished / 1000) % 60;
                timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutesString, secondsString);

                Intent broadcastIntentTime = new Intent(TIMER_TICK_ACTION);
                broadcastIntentTime.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                broadcastIntentTime.putExtra(EXTRA_TIME_VALUE, timeFormatted);
                sendBroadcast(broadcastIntentTime);

            }

            @Override
            public void onFinish() {
                Log.e(LOG_TAG, "Finished");

                isWorkInterval = !isWorkInterval;

                Intent broadcastIntentStatus = new Intent(INTERVAL_CHANGE_ACTION);
                broadcastIntentStatus.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(broadcastIntentStatus);
                startTimer();
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "TimerService onDestroy");
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        TimerService getService() {
            return TimerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public boolean isWorkInterval() {
        return isWorkInterval;
    }

    public String getTime() {
        return timeFormatted;
    }

}