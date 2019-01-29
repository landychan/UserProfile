package com.landychan.userprofile;

import android.app.Activity;
import android.support.test.espresso.contrib.PickerActions;
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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.ActivityResultMatchers.hasResultCode;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class RegisterActivityEspressoTest {

    private int number;

    private String errorDuplicateUser = "Username already exists";
    private String errorEmptyUsername = "Username cannot be empty";
    private String errorEmptyPassword = "Password cannot be empty";
    private String errorInvalidEmail = "Email address is invalid";
    private String errorEmptyFirstName = "First name cannot be empty";
    private String errorEmptyLastName = "Last name cannot be empty";
    private String errorEmptyAddressOne = "Address Line One cannot be empty";
    private String errorEmptyCity = "City cannot be empty";
    private String errorEmptyState = "State cannot be empty";
    private String errorInvalidZipcode = "Zip code is invalid";
    private String errorInvalidBirthdate = "Birth date is invalid";

    @Rule
    public ActivityTestRule<RegisterActivity> mActivityRule =
            new ActivityTestRule<>(RegisterActivity.class);

    @Before
    public void initRandom() {

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        number = random.nextInt(100000);
    }

    @Test
    public void checkViewLoadedSuccessfully() {

        onView(withId(R.id.edit_username)).check(matches(notNullValue()));
        onView(withId(R.id.edit_password)).check(matches(notNullValue()));
        onView(withId(R.id.edit_email_address)).check(matches(notNullValue()));
        onView(withId(R.id.edit_first_name)).check(matches(notNullValue()));
        onView(withId(R.id.edit_last_name)).check(matches(notNullValue()));
        onView(withId(R.id.edit_address)).check(matches(notNullValue()));
        onView(withId(R.id.edit_address_line_two)).check(matches(notNullValue()));
        onView(withId(R.id.edit_city)).check(matches(notNullValue()));
        onView(withId(R.id.edit_state)).check(matches(notNullValue()));
        onView(withId(R.id.edit_zip_code)).check(matches(notNullValue()));
        onView(withId(R.id.edit_birthdate)).check(matches(notNullValue()));
        onView(withId(R.id.button_register)).check(matches(notNullValue()));
    }

    @Test
    public void registerUser() {

        onView(withId(R.id.edit_username)).perform(typeText("testuser" + number), closeSoftKeyboard());
        onView(withId(R.id.edit_password)).perform(typeText("testuser" + number), closeSoftKeyboard());
        onView(withId(R.id.edit_email_address)).perform(typeText("testuser" + number + "@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.edit_first_name)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.edit_last_name)).perform(typeText("User"), closeSoftKeyboard());
        onView(withId(R.id.edit_address)).perform(typeText("7535 Torrey Santa Fe Rd"), closeSoftKeyboard());
        onView(withId(R.id.edit_address_line_two)).perform(typeText("Building " + number), closeSoftKeyboard());
        onView(withId(R.id.edit_city)).perform(typeText("San Diego"), closeSoftKeyboard());
        onView(withId(R.id.edit_state)).perform(typeText("California"), closeSoftKeyboard());
        onView(withId(R.id.edit_zip_code)).perform(typeText("92129"), closeSoftKeyboard());

        setDate(2019,1,30);
        onView(withId(R.id.button_register)).perform(click());
        assertThat(mActivityRule.getActivityResult(), hasResultCode(Activity.RESULT_OK));

    }

    @Test
    public void registerDuplicateuser() {

        onView(withId(R.id.edit_username)).perform(typeText("testuser"), closeSoftKeyboard());
        onView(withId(R.id.edit_password)).perform(typeText("testuser"), closeSoftKeyboard());
        onView(withId(R.id.edit_email_address)).perform(typeText("testuser" + number + "@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.edit_first_name)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.edit_last_name)).perform(typeText("User"), closeSoftKeyboard());
        onView(withId(R.id.edit_address)).perform(typeText("7535 Torrey Santa Fe Rd"), closeSoftKeyboard());
        onView(withId(R.id.edit_address_line_two)).perform(typeText("Building " + number), closeSoftKeyboard());
        onView(withId(R.id.edit_city)).perform(typeText("San Diego"), closeSoftKeyboard());
        onView(withId(R.id.edit_state)).perform(typeText("California"), closeSoftKeyboard());
        onView(withId(R.id.edit_zip_code)).perform(typeText("92129"), closeSoftKeyboard());

        setDate(2019,1,30);
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.edit_username)).check(matches(hasErrorText(errorDuplicateUser)));

    }

    @Test
    public void testFieldValidation() {
        onView(withId(R.id.button_register)).perform(click());

        // Test empty fields
        onView(withId(R.id.edit_username)).check(matches(hasErrorText(errorEmptyUsername)));
        onView(withId(R.id.edit_password)).check(matches(hasErrorText(errorEmptyPassword)));
        onView(withId(R.id.edit_email_address)).check(matches(hasErrorText(errorInvalidEmail)));
        onView(withId(R.id.edit_first_name)).check(matches(hasErrorText(errorEmptyFirstName)));
        onView(withId(R.id.edit_last_name)).check(matches(hasErrorText(errorEmptyLastName)));
        onView(withId(R.id.edit_address)).check(matches(hasErrorText(errorEmptyAddressOne)));
        onView(withId(R.id.edit_city)).check(matches(hasErrorText(errorEmptyCity)));
        onView(withId(R.id.edit_state)).check(matches(hasErrorText(errorEmptyState)));
        onView(withId(R.id.edit_zip_code)).check(matches(hasErrorText(errorInvalidZipcode)));
        onView(withId(R.id.edit_birthdate)).check(matches(hasErrorText(errorInvalidBirthdate)));

        // Test invalid format
        onView(withId(R.id.edit_email_address)).perform(typeText("testuser" + number + "atgmailcom"), closeSoftKeyboard());
        onView(withId(R.id.edit_password)).perform(typeText("tst"), closeSoftKeyboard());
        onView(withId(R.id.edit_zip_code)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.button_register)).perform(click());

        onView(withId(R.id.edit_password)).check(matches(hasErrorText(errorEmptyPassword)));
        onView(withId(R.id.edit_email_address)).check(matches(hasErrorText(errorInvalidEmail)));
        onView(withId(R.id.edit_zip_code)).check(matches(hasErrorText(errorInvalidZipcode)));

    }

    // Helper function to set date in date picker
    public void setDate(int year, int monthOfYear, int dayOfMonth) {
        onView(withId(R.id.edit_birthdate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, monthOfYear, dayOfMonth));
        onView(withId(android.R.id.button1)).perform(click());
    }


}
