package com.example.teeplan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.teeplan.coupon.CouponFragment;
import com.example.teeplan.databinding.ActivityMainBinding;
import com.example.teeplan.event.EventFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static final String TIMER_RUNNING_KEY = "com.example.teeplan.buttonPref";
    private static final String CURRENT_FRAGMENT_KEY = "CURRENT_FRAGMENT_KEY";
    private String currentFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        resetTimerToOriginalState();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            replaceFragment(new ToDoFragment(), ToDoFragment.class.getSimpleName());
        } else {
            currentFragmentTag = savedInstanceState.getString(CURRENT_FRAGMENT_KEY);
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(currentFragmentTag);
            if (currentFragment != null) {
                replaceFragment(currentFragment, currentFragmentTag);
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.todo) {
                replaceFragment(new ToDoFragment(), ToDoFragment.class.getSimpleName());
            } else if (id == R.id.events) {
                replaceFragment(new EventFragment(), EventFragment.class.getSimpleName());
            } else if (id == R.id.timer) {
                replaceFragment(new TimerFragment(), TimerFragment.class.getSimpleName());
            } else if (id == R.id.coupon) {
                replaceFragment(new CouponFragment(), CouponFragment.class.getSimpleName());
            } else if (id == R.id.settings) {
                replaceFragment(new SettingsFragment(), SettingsFragment.class.getSimpleName());
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, tag);
        fragmentTransaction.commit();
        currentFragmentTag = tag;
    }

    private void resetTimerToOriginalState() {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(TIMER_RUNNING_KEY, false).apply();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_FRAGMENT_KEY, currentFragmentTag);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentFragmentTag = savedInstanceState.getString(CURRENT_FRAGMENT_KEY);
    }
}
