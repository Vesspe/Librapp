package com.example.librapp;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.TypeTextAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule =
            new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void test_login_activity_display() {
        String email="mockup@gmail.com";
        String password = "mockup1234";
        onView(withId(R.id.emailLogin))
                .perform(ViewActions.typeText(email));
        onView(withId(R.id.passwordLogin))
                .perform(ViewActions.typeText(password));
        onView(withId(R.id.buttonLogin))
                .perform(ViewActions.click());

        /*Espresso.onView(ViewMatchers.withId(R.id.loginLayout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));*/

    }
}