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

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
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
        //login
        onView(withId(R.id.etUsername)).perform(typeText("admin"),closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("admin"),closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        //guard creation
        onView(withId(R.id.btnGuard)).perform(click());
        String[] guardList = {"Georgios","Solakis","133709","Arne","Bartenbach","000000","Kai","Braun","015775","Phil","Smponi","01814381514781"};
        for(int i = 0; i<guardList.length/3;i++){
            onView(withId(R.id.etForname)).perform(typeText(guardList[i*3]),closeSoftKeyboard());
            onView(withId(R.id.etSurname)).perform(typeText(guardList[3*i+1]),closeSoftKeyboard());
            onView(withId(R.id.pwUserPassword)).perform(typeText(guardList[3*i+2]),closeSoftKeyboard());
            onView(withId(R.id.btnAcceptGuard)).perform(click());
        }
        onView(withId(R.id.btnEditGuard)).perform(click());
        onView(withId(R.id.guardList)).perform(click());
        onView(withId(R.id.etForname)).perform(typeText("Max"),closeSoftKeyboard());
        onView(withId(R.id.etSurname)).perform(typeText("Mustermann"),closeSoftKeyboard());
        onView(withId(R.id.pwUserPassword)).perform(typeText("sicheresPW123"),closeSoftKeyboard());
        onView(withId(R.id.btnAcceptGuard)).perform(click());
        pressBack();
        //Waypoint creation
        onView(withId(R.id.btnWaypoints)).perform(click());
        String[] wpList = {"Eingang","000001","Hier geht es los","Schatzkammer","000002","Nichts mitgehen lassen!","WC","000003","Auch das muss bewacht werden","Ausgang","000004","Jetzt aber raus hier"};
        for (int i = 0; i < wpList.length/3; i++){
            onView(withId(R.id.etWaypointName));
        }
    }

    private void myText(int id,String text){
        onView(withId(id)).perform(typeText(text));
    }
}
