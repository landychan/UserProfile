package com.landychan.userprofile;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.doubleClick;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    private String errorEmptyUsernameField = "Username cannot be empty";
    private String errorEmptyPasswordField = "Password cannot be empty";
    private String errorIncorrectPassword = "Incorrect password";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = getTargetContext();

        assertEquals("com.landychan.userprofile", appContext.getPackageName());
    }

    @Test
    public void checkViewLoadedSuccessfully() {
        onView(withId(R.id.button_login)).check(matches(notNullValue()));
        onView(withId(R.id.button_register)).check(matches(notNullValue()));
        onView(withId(R.id.edit_username)).check(matches(notNullValue()));
        onView(withId(R.id.edit_password)).check(matches(notNullValue()));
    }

    @Test
    public void ensureUsernameAcceptsInput() {
        onView(withId(R.id.edit_username))
                .perform(typeText("ESPRESSOTEST"), closeSoftKeyboard());
        onView(withId(R.id.edit_username)).check(matches(withText("ESPRESSOTEST")));
    }

    @Test
    public void ensurePasswordAcceptsInput() {
        onView(withId(R.id.edit_password))
                .perform(typeText("PASSWORDTEST"), closeSoftKeyboard());
        onView(withId(R.id.edit_password)).check(matches(withText("PASSWORDTEST")));
    }

//    @Before
//    public void getString() {
//        Resources resources = InstrumentationRegistry.getContext().getResources();
//        errorEmptyUsernameField = resources.getString(R.string.empty_username);
//        errorEmptyPasswordField = resources.getString(R.string.empty_password);
//        errorIncorrectPassword = resources.getString(R.string.error_incorrect_password);
//    }


    @Test
    public void testUsernameValidation() {

        // Empty username and password
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.edit_username)).check(matches(hasErrorText(errorEmptyUsernameField)));
        onView(withId(R.id.edit_password)).check(matches(hasErrorText(errorEmptyPasswordField)));

        // Empty password field
        onView(withId(R.id.edit_username))
                .perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.edit_password)).check(matches(hasErrorText(errorEmptyPasswordField)));

        // Empty username field
        onView(withId(R.id.edit_username))
                .perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.edit_password))
                .perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.edit_username)).check(matches(hasErrorText(errorEmptyUsernameField)));

    }

    @Test
    public void testPasswordValidation() {

        Intents.init();
        // Empty password field
        onView(withId(R.id.edit_username))
                .perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.edit_password)).check(matches(hasErrorText(errorEmptyPasswordField)));

        //Incorrect password

        onView(withId(R.id.edit_password))
                .perform(typeText("1234567890"), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.edit_password)).check(matches(hasErrorText(errorIncorrectPassword)));
        onView(withId(R.id.edit_password)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.edit_password))
                .perform(typeText("ABCDEFGHIJKLMNOQRSTUVWXYZ"), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.edit_password)).check(matches(hasErrorText(errorIncorrectPassword)));
        onView(withId(R.id.edit_password)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.edit_password))
                .perform(typeText("A**(!@#&@!(&$)(!@&#)(2138123891"), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.edit_password)).check(matches(hasErrorText(errorIncorrectPassword)));

        //Correct password

        onView(withId(R.id.edit_password)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.edit_password))
                .perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());
        intended(allOf(hasComponent(hasShortClassName(".UserInfoActivity"))));

        Intents.release();

    }

    @Test
    public void testRegisterButton() {
        Intents.init();

        // Double click
        onView(withId(R.id.button_register)).perform(doubleClick());
        intended(allOf(hasComponent(hasShortClassName(".RegisterActivity"))));

        // Long click
        onView(withId(R.id.button_register)).perform(longClick());
        intended(allOf(hasComponent(hasShortClassName(".RegisterActivity"))));

        // Single click
        onView(withId(R.id.button_register)).perform(click());
        intended(allOf(hasComponent(hasShortClassName(".RegisterActivity"))));


        Intents.release();
    }


}
