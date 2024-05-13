package com.example.teeplan;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimerFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    SharedPreferences sharedPreferences;
    private static final String TIMER_RUNNING_KEY = "com.example.teeplan.buttonPref";
    private static final String IS_BREAK_KEY = "com.example.teeplan.isBreak";
    private boolean isTimerRunning = false;
    private boolean isBreak = false;


    public TimerFragment() {
    }

    public static TimerFragment newInstance(String param1, String param2) {
        TimerFragment fragment = new TimerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        isTimerRunning = sharedPreferences.getBoolean(TIMER_RUNNING_KEY, false);
        isBreak = sharedPreferences.getBoolean(IS_BREAK_KEY, false);
    }


    private Button startTimerButton;
    private TextView timerView;
    private TextView isBreakView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timer, container, false);
        timerView = rootView.findViewById(R.id.timerText);
        isBreakView = rootView.findViewById(R.id.isBreak);

        startTimerButton = rootView.findViewById(R.id.startTimerButton);
        startTimerButton.setText("25:00");

        handleIsBreak();
        updateButtonState();


        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTimerRunning) {
                    startTimer();
                } else {
                    resetTimer();
                }
            }
        });


        return rootView;
    }

    private void startTimerService() {
        Intent serviceIntent = new Intent(getActivity(), TimerService.class);
        getActivity().startService(serviceIntent);
    }

    private void stopTimerService() {
        Intent serviceIntent = new Intent(getActivity(), TimerService.class);
        getActivity().stopService(serviceIntent);
    }

    private BroadcastReceiver timerBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals(TimerService.TIMER_TICK_ACTION)) {
                String timeValue = intent.getStringExtra(TimerService.EXTRA_TIME_VALUE);
                timerView.setText(String.valueOf(timeValue));

            }
        }

    };
    private BroadcastReceiver intervalChangeBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals(TimerService.INTERVAL_CHANGE_ACTION)) {
                Log.e("tag", "AAAA");
                isBreak = sharedPreferences.getBoolean(IS_BREAK_KEY, false);
                sharedPreferences.edit().putBoolean(IS_BREAK_KEY, !isBreak).apply();
                handleIsBreak();

            }
        }

    };

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getActivity().registerReceiver(timerBroadcastReceiver, new IntentFilter(TimerService.TIMER_TICK_ACTION), Context.RECEIVER_EXPORTED);
            getActivity().registerReceiver(intervalChangeBroadcastReceiver, new IntentFilter(TimerService.INTERVAL_CHANGE_ACTION), Context.RECEIVER_EXPORTED);
        }
        Log.e("TimerService", "receiver registered");
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(timerBroadcastReceiver);
        getActivity().unregisterReceiver(intervalChangeBroadcastReceiver);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateButtonState();
    }

    private void updateButtonState() {
        if (isTimerRunning) {
            startTimerButton.setText("Reset");
        } else {
            startTimerButton.setText("Start");
        }
    }

    private void startTimer() {
        startTimerService();
        isTimerRunning = true;
        sharedPreferences.edit().putBoolean(TIMER_RUNNING_KEY, true).apply();
        sharedPreferences.edit().putBoolean(IS_BREAK_KEY, false).apply();
        handleIsBreak();
        updateButtonState();
    }

    private void resetTimer() {
        isTimerRunning = false;
        sharedPreferences.edit().putBoolean(TIMER_RUNNING_KEY, false).apply();
        handleIsBreak();
        timerView.setText("25:00");
        stopTimerService();
        updateButtonState();
    }

    private void handleIsBreak() {
        isTimerRunning = sharedPreferences.getBoolean(TIMER_RUNNING_KEY, false);
        if (isTimerRunning) {
            isBreak = sharedPreferences.getBoolean(IS_BREAK_KEY, false);
            if (isBreak) {
                isBreakView.setText("BREAK TIME");
            } else {
                isBreakView.setText("FOCUS TIME");
            }
        } else {
            isBreakView.setText("");
        }
    }

}