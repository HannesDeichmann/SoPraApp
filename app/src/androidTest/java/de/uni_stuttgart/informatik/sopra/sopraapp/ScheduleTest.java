package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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

import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.startsWith;


@RunWith(AndroidJUnit4.class)
public class ScheduleTest {

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
    public void testSchedule() {
        /**
         * Login as admin
         */
        onView(withId(R.id.etUsername)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());

        /**
         * setup test data
         */
        createTestGuard();
        createTestWaypoint();
        createTestRoute();

        /**
         * test the schedule Activity by assigning the guard with id 1 the route with id 1
         */
        onView(withId(R.id.btnSchedule)).perform(click());
        onView(withId(R.id.selectGuard)).perform(click());
        onData(hasToString(startsWith("1:"))).perform(click());
        onView(withId(R.id.selectRoute)).perform(click());
        onData(hasToString(startsWith("1"))).perform(click());
        onView(withId(R.id.saveScheduleItem)).perform(click());
        Espresso.pressBack();
        Espresso.pressBack();
        onView(withId(R.id.btnLogin)).check(matches(withText("Login")));
        logIn("1", "1234");
        testAssignedRoute();
        testChangingPassword("newPassword1234");
        logIn("1", "newPassword1234");
        testChangingPassword("1234");


    }

    public void logIn(String id, String password){
        onView(withId(R.id.etUsername)).perform(clearText(),typeText(id), closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(clearText(),typeText(password), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
    }

    /**
     * Change the password in the profile fragment.
     * Confirm first that the guard is already logged in.
     */
    public void testChangingPassword(String newPassword){

        onView(withId(R.id.tvRoutes)).check(matches(withText("Choose a route to start")));
        onView((withId(R.id.nav_profile))).perform(click());
        onView(withId(R.id.tvUsername)).check(matches(withText("Otto Mullerich")));
        onView(withId(R.id.etChangePassword)).perform(clearText(), typeText(newPassword), closeSoftKeyboard());
        onView(withId(R.id.etConfirmPassword)).perform(clearText(), typeText(newPassword), closeSoftKeyboard());
        onView(withId(R.id.btnChangePassword)).perform(click());
        onView(withId(R.id.btnLogOut)).perform(click());
        /**
         * Log out and re-log in with the newly assigned password.
         */
        logIn("1", newPassword);
        onView(withId(R.id.tvRoutes)).check(matches(withText("Choose a route to start")));
        onView((withId(R.id.nav_profile))).perform(click());
        onView(withId(R.id.tvUsername)).check(matches(withText("Otto Mullerich")));
        onView(withId(R.id.btnLogOut)).perform(click());
    }

    /**
     * Check if the assigned route is displayed.
     * Confirm first that the guard is already logged in.
     */
    public void testAssignedRoute(){
        onView(withId(R.id.tvRoutes)).check(matches(withText("Choose a route to start")));
        onData(hasToString(startsWith(""))).inAdapterView(withId(R.id.lvRoutes)).atPosition(0).check(matches(withText("TestRoute")));
        onData(hasToString(startsWith(""))).inAdapterView(withId(R.id.lvRoutes)).atPosition(0).perform(click());
        onView(withId(R.id.tvNextWaypointName)).check(matches(withText("TestWaypoint")));
        onView(withId(R.id.btnCancelActiveRoute)).perform(click());

    }
    public void createTestGuard(){

        onView(withId(R.id.tvAdminModus)).check(matches(withText("Admin Mode")));
        onView(withId(R.id.btnGuard)).perform(click());
        onView(withId(R.id.etForname)).perform(clearText(), typeText("Otto"), closeSoftKeyboard());
        onView(withId(R.id.etSurname)).perform(clearText(), typeText("Mullerich"),closeSoftKeyboard());
        onView(withId(R.id.pwUserPassword)).perform(clearText(), typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.btnAcceptGuard)).perform(click());
        Espresso.pressBack();

    }
    public void createTestWaypoint(){

        onView(withId(R.id.tvAdminModus)).check(matches(withText("Admin Mode")));
        onView(withId(R.id.btnWaypoints)).perform(click());
        onView(withId(R.id.etWaypointName)).perform(typeText("TestWaypoint"), closeSoftKeyboard());
        onView(withId(R.id.etWaypointNote)).perform(typeText("TestNote"), closeSoftKeyboard());
        onView(withId(R.id.btnAcceptWaypoint)).perform(click());
        Espresso.pressBack();
    }

    public void createTestRoute(){

        onView(withId(R.id.tvAdminModus)).check(matches(withText("Admin Mode")));
        onView(withId(R.id.btnRoutes)).perform(click());
        onView(withId(R.id.newRoute)).perform(click());
        onView(withId(R.id.etRouteName)).perform(clearText(), typeText("TestRoute"), closeSoftKeyboard());
        onView(withId(R.id.btnAddWaypoint)).perform(click());
        onData(hasToString(startsWith("1:"))).perform(click());
        onView(withId(R.id.durationInput)).perform(typeText("1"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.saveRoute)).perform(click());
        Espresso.pressBack();
    }
}
