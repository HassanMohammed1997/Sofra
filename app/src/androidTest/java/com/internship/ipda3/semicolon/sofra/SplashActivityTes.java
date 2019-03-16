package com.internship.ipda3.semicolon.sofra;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.internship.ipda3.semicolon.sofra.ui.activity.SplashActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class SplashActivityTes {


    @Rule
    public ActivityTestRule<SplashActivity> splashActivityActivityTestRule
            = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void clickOrderFoodButton_openHomeActivity() throws Exception{
        onData(withId(R.id.order_food_button))
                .perform(click())
                .check(matches(isChecked()));

    }


}
