package de.uni_stuttgart.informatik.sopra.sopraapp;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TimePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.startsWith;

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
    public void useAppContext() {
        //login
        onView(withId(R.id.etUsername)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());


       //guard creation
        onView(withId(R.id.btnGuard)).perform(click());
        String[] guardList = {"Arne", "Bartenbach", "000000", "Kai", "Braun", "015775", "Phil", "Smponi", "01814381514781"};
        for (int i = 0; i < guardList.length / 3; i++) {
            onView(withId(R.id.etForname)).perform(typeText(guardList[i * 3]), closeSoftKeyboard());
            onView(withId(R.id.etSurname)).perform(typeText(guardList[3 * i + 1]), closeSoftKeyboard());
            onView(withId(R.id.pwUserPassword)).perform(typeText(guardList[3 * i + 2]), closeSoftKeyboard());
            onView(withId(R.id.btnAcceptGuard)).perform(click());
        }
        onView(withId(R.id.btnEditGuard)).perform(click());
        onData(hasToString(startsWith("1:"))).perform(click());
        onView(withId(R.id.etForname)).perform(clearText(), typeText("Max"), closeSoftKeyboard());
        onView(withId(R.id.etSurname)).perform(clearText(), typeText("Mustermann"), closeSoftKeyboard());
        onView(withId(R.id.pwUserPassword)).perform(clearText(), typeText("sicheresPW123"), closeSoftKeyboard());
        onView(withId(R.id.btnAcceptGuard)).perform(click());
        Espresso.pressBack();
        //Waypoint creation
        onView(withId(R.id.btnWaypoints)).perform(click());
        String[] wpList = {"Eingang", "Hier geht es los", "Schatzkammer", "Nichts mitgehen lassen!", "WC", "Auch das muss bewacht werden", "Ausgang", "Jetzt aber raus hier"};
        for (int i = 0; i < wpList.length / 3; i++) {
            onView(withId(R.id.etWaypointName)).perform(typeText(wpList[i * 2]), closeSoftKeyboard());
            onView(withId(R.id.etWaypointNote)).perform(typeText(wpList[i * 2 + 1]), closeSoftKeyboard());
            //picture should be selected
            onView(withId(R.id.btnAddLocation)).perform(click());
            onView(withId(R.id.canvas)).perform(click());
            onView(withId(R.id.btnSaveMap)).perform(click());
            onView(withId(R.id.btnAcceptWaypoint)).perform(click());
        }

        onView(withId(R.id.btnEditWaypoint)).perform(click());
        onData(hasToString(startsWith("40000"))).perform(click());
        onView(withId(R.id.etWaypointName)).perform(typeText("Geheimtuer"), closeSoftKeyboard());
        onView(withId(R.id.showWaypointId)).perform(typeText("500000"), closeSoftKeyboard());
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
        onData(hasToString(startsWith("3"))).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("9"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        // add wp4
        onView(withId(R.id.btnAddWaypoint)).perform(click());
        onData(hasToString(startsWith("1"))).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("7"), closeSoftKeyboard());
        onView(withText("CANCEL")).perform(click());
        onView(withId(R.id.recyclerListWp)).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("9"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        //replace wp
        onData(hasToString(startsWith("Eingang"))).perform(click());
        onData(hasToString(startsWith("5"))).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("99"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.saveRoute)).perform(click());
        // Route 2
        onView(withId(R.id.newRoute)).perform(click());
        onView(withId(R.id.etRouteName)).perform(typeText("Marathon"),closeSoftKeyboard());
        //add wp1
        onView(withId(R.id.btnAddWaypoint)).perform(click());
        onData(hasToString(startsWith("1"))).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("2"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        //add wp2
        onView(withId(R.id.btnAddWaypoint)).perform(click());
        onData(hasToString(startsWith("5"))).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("9"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        //add wp3
        onView(withId(R.id.btnAddWaypoint)).perform(click());
        onData(hasToString(startsWith("3"))).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("9"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        // open Route and get old WPs
        onData(hasToString(startsWith("1"))).perform(click());
        onView(withId(R.id.btnAddOldWaypoints)).perform(click());
        onView(withId(R.id.btnAddWaypoint)).perform(click());
        onData(hasToString(startsWith("3"))).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("6"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        onData(hasToString(startsWith("1"))).perform(click());
        onView(withId(R.id.btnCancelWPSelect)).perform(click());
        onView(withId(R.id.saveRoute)).perform(click());
        pressBack();

        //schedule
        onView(withId(R.id.btnSchedule)).perform(click());
        onView(withId(R.id.selectGuard)).perform(click());
        onData(hasToString(startsWith("1:"))).perform(click());
        onView(withId(R.id.selectRoute)).perform(click());
        onData(hasToString(startsWith("1"))).perform(click());
        onView(withId(R.id.btnSelectStartTime)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(TimePicker.class);
            }

            @Override
            public String getDescription() {
                return "Set the value of a TimePicker";            }

            @Override
            public void perform(UiController uiController, View view) {
                ((TimePicker)view).setHour(13);
                ((TimePicker)view).setMinute(37);
            }
        });
        onView(withId(R.id.saveScheduleItem)).perform(click());
        pressBack();
        pressBack();
        onView(withId(R.id.etUsername)).perform(typeText("1"), closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("sicheresPW123"), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.btnSelectARouteToStart)).perform(click());
        onData(hasToString(startsWith("Marathon"))).perform(click());
        onView(withId(R.id.btnShowMap)).perform(click());
        onView(withId(R.id.btnBackToPatrol)).perform(click());
        onView(withId(R.id.btnFinishRoute)).perform(click());
        onView(withId(R.id.btnLogOut)).perform(click());
        onView(withId(R.id.etUsername)).perform(typeText("admin"),closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("admin"),closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.btnProtocol)).perform(click());
        onView(withId(R.id.btnExport)).perform(click());
        pressBack();
        pressBack();
    }
    private void myText(int id, String text) {
        onView(withId(id)).perform(typeText(text));
    }
}
