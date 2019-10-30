package com.example.bakingapp;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.bakingapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsAnything.anything;

@RunWith(AndroidJUnit4.class)
public class MenuScreenTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void recipeItemDisplayed(){
        onView(withText("Brownies")).check(matches(isDisplayed()));
    }

    @Test
    public void clickGridViewItem_OpensOrderActivity(){

        //Nutella Pie is clicked
        onData(anything()).inAdapterView(withId(R.id.master_list_fragment)).atPosition(0).perform(click());

        onView(withText("1.5 TSP salt")).check(matches(isDisplayed()));
    }



}
