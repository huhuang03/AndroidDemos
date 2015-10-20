package com.tonghu.basicsample;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author york
 * @date 10/9/15
 * @since 1.0.0
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ChangeTextBehaviorTest {

    public static final String STRING_TO_BE_TYPED = "Espresso";

    @Rule
    public ActivityTestRule<MainActivity> mActivityResult = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void changeText_newActivity() {
        Espresso.onView(ViewMatchers.withId(R.id.editTextUserInput)).perform(ViewActions.typeText(STRING_TO_BE_TYPED), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.activityChangeTextBtn)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.show_text_view)).check(ViewAssertions.matches(ViewMatchers.withText(STRING_TO_BE_TYPED)));
    }
}
