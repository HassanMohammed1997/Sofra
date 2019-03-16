package com.internship.ipda3.semicolon.sofra.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.ui.fragment.userCycle.SplashFragment;

import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.replace;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //replace splash fragment
        replace(R.id.frame, new SplashFragment(), getSupportFragmentManager());



    }
}
