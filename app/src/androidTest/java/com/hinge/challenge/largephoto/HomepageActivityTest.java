package com.hinge.challenge.largephoto;

import android.content.ComponentName;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.hinge.challenge.largephoto.ui.activity.GalleryActivity;
import com.hinge.challenge.largephoto.ui.activity.HomepageActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class HomepageActivityTest
{
    @Rule
    public ActivityTestRule<HomepageActivity> homepageActivityTestRule =
            new ActivityTestRule<>(HomepageActivity.class, true, true);

    @Before
    public void registerIdlingResource() {
        Espresso.registerIdlingResources(
                homepageActivityTestRule.getActivity().getCountingIdlingResource());
    }

    @Test
    public void testContainsHomepageFragment()
    {
        Espresso.registerIdlingResources(homepageActivityTestRule.getActivity().getCountingIdlingResource());

        Intents.init();

        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(new ComponentName(getTargetContext(), GalleryActivity.class)));

        Intents.release();
    }
}
