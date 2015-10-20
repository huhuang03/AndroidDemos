package com.tonghu.adapterviewtest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @功能描述：
 * @作者： york
 * @创建时间： 10/13/15
 */
@RunWith(JUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void test_onData() throws InterruptedException {
        Espresso.onData(Matchers.allOf(Matchers.instanceOf(String.class), Matchers.equalTo("Item: 6"))).perform(ViewActions.click());
        Thread.sleep(1000);
    }

}
