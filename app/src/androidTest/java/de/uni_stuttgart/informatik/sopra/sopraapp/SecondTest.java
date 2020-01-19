package de.uni_stuttgart.informatik.sopra.sopraapp;


import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import android.widget.Button;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SecondTest {

    //Instrumentation.ActivityMonitor monitor = getInstrumetation().addMonitor(GuardActivity.class)

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class)
    {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent intent = new Intent(targetContext, MainActivity.class);
            intent.putExtra("Message", "Hello");
            return intent;
        }
    };


    @Test
    public void useAppContext() throws Exception {

        MainActivity activity = rule.getActivity();

        onView(withId(R.id.etUsername)).perform(typeText("1"));
        onView(withId(R.id.etPassword)).perform(typeText("1234"));
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.tvUsername)).check(matches(withText("otto müllerich")));
    }
}
