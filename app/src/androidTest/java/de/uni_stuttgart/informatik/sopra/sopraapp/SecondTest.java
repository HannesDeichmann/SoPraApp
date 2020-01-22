package de.uni_stuttgart.informatik.sopra.sopraapp;


import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import android.widget.Button;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
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
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class) {
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
        onView(withId(R.id.etUsername)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());

        //guard creation
        onView(withId(R.id.btnGuard)).perform(click());
        String[] guardList = {"Georgios", "Solakis", "133709", "Arne", "Bartenbach", "000000", "Kai", "Braun", "015775", "Phil", "Smponi", "01814381514781"};
        for (int i = 0; i < guardList.length / 3; i++) {
            onView(withId(R.id.etForname)).perform(typeText(guardList[i * 3]), closeSoftKeyboard());
            onView(withId(R.id.etSurname)).perform(typeText(guardList[3 * i + 1]), closeSoftKeyboard());
            onView(withId(R.id.pwUserPassword)).perform(typeText(guardList[3 * i + 2]), closeSoftKeyboard());
            onView(withId(R.id.btnAcceptGuard)).perform(click());
        }
        onView(withId(R.id.btnEditGuard)).perform(click());
        onView(withId(R.id.guardList)).perform(click());
        onView(withId(R.id.etForname)).perform(clearText(), typeText("Max"), closeSoftKeyboard());
        onView(withId(R.id.etSurname)).perform(clearText(), typeText("Mustermann"), closeSoftKeyboard());
        onView(withId(R.id.pwUserPassword)).perform(clearText(), typeText("sicheresPW123"), closeSoftKeyboard());
        onView(withId(R.id.btnAcceptGuard)).perform(click());
        Espresso.pressBack();
        //Waypoint creation
        onView(withId(R.id.btnWaypoints)).perform(click());
        String[] wpList = {"Eingang", "000001", "Hier geht es los", "Schatzkammer", "000002", "Nichts mitgehen lassen!", "WC", "000003", "Auch das muss bewacht werden", "Ausgang", "000004", "Jetzt aber raus hier"};
        for (int i = 0; i < wpList.length / 3; i++) {
            onView(withId(R.id.etWaypointName)).perform(typeText(wpList[i * 3]), closeSoftKeyboard());
            onView(withId(R.id.etWaypointId)).perform(typeText(wpList[i * 3 + 1]), closeSoftKeyboard());
            onView(withId(R.id.etWaypointNote)).perform(typeText(wpList[i * 3 + 2]), closeSoftKeyboard());
            //onView(withId(R.id.btnAddLocation)).perform(click());
            onView(withId(R.id.btnAcceptWaypoint)).perform(click());
        }
        onView(withId(R.id.btnEditWaypoint)).perform(click());
        onData(hasToString(startsWith("000004"))).perform(click());
        onView(withId(R.id.etWaypointName)).perform(typeText("Geheimtuer"), closeSoftKeyboard());
        onView(withId(R.id.etWaypointId)).perform(typeText("000005"), closeSoftKeyboard());
        onView(withId(R.id.etWaypointNote)).perform(typeText("Vorsicht!"), closeSoftKeyboard());
        //onView(withId(R.id.btnAddLocation)).perform(click());
        onView(withId(R.id.btnAcceptWaypoint)).perform(click());
        Espresso.pressBack();

        //Route creation
        onView(withId(R.id.btnRoutes)).perform(click());
        // Route 1
        onView(withId(R.id.newRoute)).perform(click());
        onView(withId(R.id.etRouteName)).perform(typeText("Marathon"),closeSoftKeyboard());
        //add wp1
        onView(withId(R.id.btnAddWaypoint)).perform(click());
        onData(hasToString(startsWith("000001"))).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("2"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        //add wp2
        onView(withId(R.id.btnAddWaypoint)).perform(click());
        onData(hasToString(startsWith("000002"))).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("9"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        //add wp3
        onView(withId(R.id.btnAddWaypoint)).perform(click());
        onData(hasToString(startsWith("000002"))).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("9"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        // add wp4
        onView(withId(R.id.btnAddWaypoint)).perform(click());
        onData(hasToString(startsWith("000001"))).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("7"), closeSoftKeyboard());
        onView(withText("CANCEL")).perform(click());
        onView(withId(R.id.waypointList)).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("9"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        //replace wp
        onData(hasToString(startsWith("Eingang"))).perform(click());
        onView(withId(R.id.waypointList)).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("99"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.saveRoute)).perform(click());
        // Route 2
        onView(withId(R.id.newRoute)).perform(click());
        onView(withId(R.id.etWaypointName)).perform(typeText("Tour de France"), closeSoftKeyboard());
        //add wp1
        onView(withId(R.id.btnAddWaypoint)).perform(click());
        onView(withId(R.id.waypointList)).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("4"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        //add wp2
        onView(withId(R.id.btnAddWaypoint)).perform(click());
        onView(withId(R.id.waypointList)).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("2"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        //del. wp
        onView(withId(R.id.selectedWaypointList)).perform(click());
        onView(withId(R.id.btnCancelWPSelect)).perform(click());
        onView(withId(R.id.saveRoute)).perform(click());
        // open Route and get old WPs
        onView(withId(R.id.routeList)).perform(click());
        onView(withId(R.id.btnAddOldWaypoints)).perform(click());
        onView(withId(R.id.btnAddWaypoint)).perform(click());
        onView(withId(R.id.waypointList)).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("6"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
    }


    private void myText(int id, String text) {
        onView(withId(id)).perform(typeText(text));
    }
}
