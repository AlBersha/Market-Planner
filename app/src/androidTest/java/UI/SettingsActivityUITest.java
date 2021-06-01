package UI;

import androidx.test.core.app.ActivityScenario;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import marketplanner.stocks.R;
import marketplanner.stocks.SettingsActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SettingsActivityUITest {
    private String stringToBeTyped;
    private   ActivityScenario activityScenario;


    @Before
    public void launchActivity() {
      activityScenario = ActivityScenario.launch(SettingsActivity.class);
    }

    @Test
    public void accessibilityChecks() {
        try (ActivityScenario scenario = ActivityScenario.launch(SettingsActivity.class)) {
            onView(withId(R.id.settings)).perform(click());

        }
    }


    @Test
    public void changeText_sameActivity() {
        onView(withId(R.id.settings)).check(matches(isDisplayed()));


    }

}
