package de.uni_stuttgart.informatik.sopra.sopraapp;


import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class WaypointRouteTest {

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
    public void useAppContext() {
        //login
        onView(withId(R.id.etUsername)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());

        //Waypoint creation
        onView(withId(R.id.btnWaypoints)).perform(click());
        String[] wpList = {"Eingang", "Hier geht es los", "Schatzkammer", "Nichts mitgehen lassen!", "WC", "Auch das muss bewacht werden", "Ausgang", "Jetzt aber raus hier"};
        for (int i = 0; i < wpList.length / 2; i++) {
            onView(withId(R.id.etWaypointName)).perform(typeText(wpList[i * 2]), closeSoftKeyboard());
            onView(withId(R.id.etWaypointNote)).perform(typeText(wpList[i * 2 + 1]), closeSoftKeyboard());
            onView(withId(R.id.btnAddLocation)).perform(click());
            onView(withId(R.id.canvas)).perform(click());
            onView(withId(R.id.btnSaveMap)).perform(click());
            onView(withId(R.id.etWaypointName)).check(matches(withText(wpList[i * 2])));
            onView(withId(R.id.etWaypointNote)).check(matches(withText(wpList[i * 2 + 1])));
            onView(withId(R.id.btnAcceptWaypoint)).perform(click());
        }

        onView(withId(R.id.btnEditWaypoint)).perform(click());
        onData(hasToString(startsWith(""))).inAdapterView(withId(R.id.recyclerListWp)).atPosition(0).check(matches(withText("1: Eingang Pos 539.5;886.5, NoteHier geht es los, ")));
        onData(hasToString(startsWith(""))).inAdapterView(withId(R.id.recyclerListWp)).atPosition(1).check(matches(withText("2: Schatzkammer Pos 539.5;886.5, NoteNichts mitgehen lassen!, ")));
        onData(hasToString(startsWith(""))).inAdapterView(withId(R.id.recyclerListWp)).atPosition(2).check(matches(withText("3: WC Pos 539.5;886.5, NoteAuch das muss bewacht werden, ")));
        onData(hasToString(startsWith(""))).inAdapterView(withId(R.id.recyclerListWp)).atPosition(3).check(matches(withText("4: Ausgang Pos 539.5;886.5, NoteJetzt aber raus hier, ")));
        onData(hasToString(startsWith("3"))).perform(click());
        onView(withId(R.id.etWaypointName)).perform(clearText(), typeText("Geheimtuer"), closeSoftKeyboard());
        onView(withId(R.id.etWaypointNote)).perform(clearText(), typeText("Vorsicht!"), closeSoftKeyboard());
        onView(withId(R.id.btnAddLocation)).perform(click());
        onView(withId(R.id.canvas)).perform(click());
        onView(withId(R.id.btnSaveMap)).perform(click());
        onView(withId(R.id.btnAcceptWaypoint)).perform(click());

        Espresso.pressBack();

        //Route creation
        onView(withId(R.id.btnRoutes)).perform(click());
        // Route 1
        onView(withId(R.id.newRoute)).perform(click());
        onView(withId(R.id.etRouteName)).perform(typeText("Marathon"), closeSoftKeyboard());
        //add wp1
        onView(withId(R.id.btnAddWaypoint)).perform(click());
        onData(hasToString(startsWith(""))).inAdapterView(withId(R.id.recyclerListWp)).atPosition(0).check(matches(withText("1: Eingang Pos 539.5;886.5, NoteHier geht es los, ")));
        onData(hasToString(startsWith(""))).inAdapterView(withId(R.id.recyclerListWp)).atPosition(1).check(matches(withText("2: Schatzkammer Pos 539.5;886.5, NoteNichts mitgehen lassen!, ")));
        onData(hasToString(startsWith(""))).inAdapterView(withId(R.id.recyclerListWp)).atPosition(2).check(matches(withText("3: Geheimtuer Pos 539.5;886.5, NoteVorsicht!, ")));
        onData(hasToString(startsWith(""))).inAdapterView(withId(R.id.recyclerListWp)).atPosition(3).check(matches(withText("4: Ausgang Pos 539.5;886.5, NoteJetzt aber raus hier, ")));
        onData(hasToString(startsWith("1"))).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("2"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        //add wp2
        onView(withId(R.id.btnAddWaypoint)).perform(click());
        onData(hasToString(startsWith("2"))).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("9"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        //add wp3
        onView(withId(R.id.btnAddWaypoint)).perform(click());
        onData(hasToString(startsWith("4"))).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("9"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        // add wp4 with change while selection
        onView(withId(R.id.btnAddWaypoint)).perform(click());
        onData(hasToString(startsWith("1"))).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("7"), closeSoftKeyboard());
        onView(withText("CANCEL")).perform(click());
        onData(hasToString(startsWith("4"))).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("3"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        //replace wp
        onData(hasToString(startsWith("Eingang"))).perform(click());
        onData(hasToString(startsWith("2"))).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("99"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        onData(hasToString(startsWith(""))).inAdapterView(withId(R.id.selectedWaypointList)).atPosition(0).check(matches(withText("Schatzkammer - 99min.")));
        onData(hasToString(startsWith(""))).inAdapterView(withId(R.id.selectedWaypointList)).atPosition(1).check(matches(withText("Schatzkammer - 9min.")));
        onData(hasToString(startsWith(""))).inAdapterView(withId(R.id.selectedWaypointList)).atPosition(3).check(matches(withText("Ausgang - 3min.")));
        onView(withId(R.id.saveRoute)).perform(click());

        // Route 2
        onData(hasToString(startsWith("1"))).inAdapterView(withId(R.id.routeList)).atPosition(0).perform(click());
        onView(withId(R.id.btnAddOldWaypoints)).perform(click());
        //add wp
        onView(withId(R.id.btnAddWaypoint)).perform(click());
        onData(hasToString(startsWith("1"))).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("2"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());

        // open Route and get old WPs
        onData(hasToString(startsWith(""))).inAdapterView(withId(R.id.selectedWaypointList)).atPosition(0).perform(click());
        onData(hasToString(startsWith("3"))).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("6"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.saveRoute)).perform(click());
        pressBack();
    }

    private void myText(int id, String text) {
        onView(withId(id)).perform(typeText(text));
    }
}
