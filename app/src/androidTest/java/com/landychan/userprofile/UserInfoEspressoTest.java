package com.landychan.userprofile;

import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.DatePicker;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class UserInfoEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void logInToTestUser() {

        onView(withId(R.id.edit_username))
                .perform(typeText("testuser"), closeSoftKeyboard());
        onView(withId(R.id.edit_password))
                .perform(typeText("testuser"), closeSoftKeyboard());
        onView(withId(R.id.button_login))
                .perform(click());
    }

    @Test
    public void checkViewLoadedSuccessfully() {

        onView(withId(R.id.tv_username)).check(matches(notNullValue()));
        onView(withId(R.id.tv_email_address)).check(matches(notNullValue()));
        onView(withId(R.id.tv_first_name)).check(matches(notNullValue()));
        onView(withId(R.id.tv_last_name)).check(matches(notNullValue()));
        onView(withId(R.id.tv_address)).check(matches(notNullValue()));
        onView(withId(R.id.tv_address_line_two)).check(matches(notNullValue()));
        onView(withId(R.id.tv_city)).check(matches(notNullValue()));
        onView(withId(R.id.tv_state)).check(matches(notNullValue()));
        onView(withId(R.id.tv_zip_code)).check(matches(notNullValue()));
        onView(withId(R.id.tv_birthdate)).check(matches(notNullValue()));
        onView(withId(R.id.button_edit)).check(matches(notNullValue()));
    }

    @Test
    public void openEditMode() {
        Intents.init();
        onView(withId(R.id.button_edit)).perform(click());
        intended(allOf(hasComponent(hasShortClassName(".RegisterActivity"))));
        Intents.release();
    }

    @Test
    public void editUser() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int number = random.nextInt(100000);

        Intents.init();
        onView(withId(R.id.button_edit)).perform(click());
        intended(allOf(hasComponent(hasShortClassName(".RegisterActivity"))));

        // Clear edit text fields
        onView(withId(R.id.edit_password)).perform(clearText());
        onView(withId(R.id.edit_email_address)).perform(clearText());
        onView(withId(R.id.edit_first_name)).perform(clearText());
        onView(withId(R.id.edit_last_name)).perform(clearText());
        onView(withId(R.id.edit_address)).perform(clearText());
        onView(withId(R.id.edit_address_line_two)).perform(clearText());
        onView(withId(R.id.edit_city)).perform(clearText());
        onView(withId(R.id.edit_state)).perform(clearText());
        onView(withId(R.id.edit_zip_code)).perform(clearText());


        // Edit user details
        onView(withId(R.id.edit_password)).perform(typeText("testuser"), closeSoftKeyboard());
        onView(withId(R.id.edit_email_address)).perform(typeText("testuser" + number + "@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.edit_first_name)).perform(typeText("Test" + number), closeSoftKeyboard());
        onView(withId(R.id.edit_last_name)).perform(typeText("User" + number), closeSoftKeyboard());
        onView(withId(R.id.edit_address)).perform(typeText(number + " Torrey Santa Fe Rd"), closeSoftKeyboard());
        onView(withId(R.id.edit_address_line_two)).perform(typeText("Building " + number), closeSoftKeyboard());
        onView(withId(R.id.edit_city)).perform(typeText("San Diego"), closeSoftKeyboard());
        onView(withId(R.id.edit_state)).perform(typeText("California"), closeSoftKeyboard());

        String newZip = String.format("%05d", number);
        onView(withId(R.id.edit_zip_code)).perform(typeText(newZip), closeSoftKeyboard());


        int month = random.nextInt(12) + 1;
        int day = random.nextInt(30) + 1;
        int year = random.nextInt(118) + 1900;

        setDate(year, month,day);

        onView(withId(R.id.button_register)).perform(click());
        intended(allOf(hasExtraWithKey("userdetails")));

        Intents.release();
    }


    // Helper function to set date in date picker
    public void setDate(int year, int monthOfYear, int dayOfMonth) {
        onView(withId(R.id.edit_birthdate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, monthOfYear, dayOfMonth));
        onView(withId(android.R.id.button1)).perform(click());
    }
}
