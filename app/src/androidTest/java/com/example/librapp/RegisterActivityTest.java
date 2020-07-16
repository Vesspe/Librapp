package com.example.librapp;

import android.view.View;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterActivityTest {

    @Rule
    public ActivityTestRule<RegisterActivity> activityTestRule =
            new ActivityTestRule<>(RegisterActivity.class);

    //simple display activity test
    @Test
    public void test_register_display() {
        Espresso.onView(ViewMatchers.withId(R.id.registerLayout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }

    @Test
    public void test_register_editTexts(){
        String email="mockup@gmail.com";
        String password = "mockup1234";
        String name = "adam";

        onView(withId(R.id.nameRegister))
                .perform(ViewActions.typeText(name));
        onView(withId(R.id.emailRegister))
                .perform(ViewActions.typeText(email));
        onView(withId(R.id.passwordRegister))
                .perform(ViewActions.typeText(password));
    }

    //TODO test return button and check if login activity will display
    @Test
    public void test_register_returnButton(){
        //performing click to return to login activity
        Espresso.onView(ViewMatchers.withId(R.id.buttonRegisterReturn))
                .perform(ViewActions.click());
        //test to see if textView is displayed so login activity is displayed
        /*Espresso.onView(ViewMatchers.withId(R.id.welcome))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.registerLayout))
                .perform(NavigationViewActions.navigateTo(R.id.loginLayout));

        String check = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation()
                .getContext()
                .getString(R.string.)

        String expectedNoStatisticsText = InstrumentationRegistry.getTargetContext()
                .getString(R.string.no_item_available);
        onView(withId(R.id.no_statistics)).check(matches(withText(expectedNoStatisticsText)));*/


    }




}


//cheatsheet
/*Espresso.onView(ViewMatchers.withId(R.id.registerLayout))
        //.perform(ViewActions.click())
        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));*/
