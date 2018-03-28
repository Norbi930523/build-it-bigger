package com.udacity.norbi930523.builditbigger;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.norbi930523.builditbigger.activity.MainActivity;
import com.udacity.norbi930523.builditbigger.jokedisplayer.activity.JokeDisplayActivity;
import com.udacity.norbi930523.builditbigger.testutil.SimpleIdlingResource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by Norbert Boros on 2018. 03. 28..
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private SimpleIdlingResource idlingResource;

    @BeforeClass
    public static void setup(){
        Intents.init();
    }

    @Before
    public void registerIdlingResource(){
        idlingResource = activityTestRule.getActivity().getIdlingResource();

        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Test
    public void clickOnRecipe_startsDetailsActivity() throws Exception {
        onView(withId(R.id.tellJokeButton)).perform(click());

        intended(allOf(
                hasComponent(JokeDisplayActivity.class.getName()),
                hasExtra(equalTo("jokeText"), equalTo(idlingResource.getData()))
        ));
    }

    @After
    public void unregisterIdlingResource(){
        if(idlingResource != null){
            IdlingRegistry.getInstance().unregister(idlingResource);
            idlingResource = null;
        }
    }

    @AfterClass
    public static void tearDown(){
        Intents.release();
    }
}
