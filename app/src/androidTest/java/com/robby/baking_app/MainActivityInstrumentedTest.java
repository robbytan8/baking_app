package com.robby.baking_app;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Robby on 9/14/2017.
 *
 * @author Robby Tan
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<RecipeMainActivity> activityRule = new ActivityTestRule<>(RecipeMainActivity.class);

    @Test
    public void ensureMainRecyclerShow() throws Exception {
        onView(withId(R.id.rv_main)).check(matches(isDisplayed()));
    }

    @Test
    public void ensureMainRecyclerScroll() throws Exception {
        onView(withId(R.id.rv_main)).perform(RecyclerViewActions.scrollToPosition(2));
    }

    @Test
    public void ensureItemTextInMainRecyclerView() throws Exception {
        onView(withId(R.id.rv_main)).perform(RecyclerViewActions.scrollToPosition(2))
                .check(matches(hasDescendant(withText("Yellow Cake"))));
    }

    @Test
    public void ensureChangeActivityToSecond() throws Exception {
        onView(withId(R.id.rv_main)).check(matches(hasDescendant(withId(R.id.cv_recipe_item)))).perform(click());
    }
}
