package de.uni_stuttgart.informatik.sopra.sopraapp;


import android.content.Context;
import android.content.Intent;
import android.widget.ListView;

import androidx.test.espresso.Espresso;

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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class GuardTest {

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
        public void testGuards() {
        //login
        onView(withId(R.id.etUsername)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());

        //declare guards
        Guard max = new Guard("Max", "Mustermann", "1", "");
        Guard arne = new Guard("Arne", "Bartenbach", "1", "");
        Guard kai = new Guard("Kai", "Braun", "2", "");
        Guard phil = new Guard("Phil", "Smponi", "3", "");
        onView(withId(R.id.btnGuard)).perform(click());

        //create 3 Guards
        String[] guardList = {"Arne", "Bartenbach", "000000", "Kai", "Braun", "015775", "Phil", "Smponi", "01814381514781"};
        for (int i = 0; i < guardList.length / 3; i++) {
            onView(withId(R.id.etForname)).perform(clearText(), typeText(guardList[i * 3]), closeSoftKeyboard());
            onView(withId(R.id.etSurname)).perform(clearText(), typeText(guardList[3 * i + 1]), closeSoftKeyboard());
            onView(withId(R.id.pwUserPassword)).perform(clearText(), typeText(guardList[3 * i + 2]), closeSoftKeyboard());
            onView(withId(R.id.btnAcceptGuard)).perform(click());
        }
        Espresso.pressBack();
        onView(withId(R.id.tvAdminModus)).check(matches(withText("Admin Mode")));
        onView(withId(R.id.btnGuard)).perform(click());
        onView(withId(R.id.btnAcceptGuard)).perform(click());
        onView(withId(R.id.tvGuardIDNumber)).check(matches(withText("4")));
        onView(withId(R.id.btnEditGuard)).perform(click());
        onView(withId(R.id.btnCancel)).perform(click());
        onView(withId(R.id.tvGuardIDNumber)).check(matches(withText("4")));
        onView(withId(R.id.btnEditGuard)).perform(click());
        onData(hasToString(startsWith("1:"))).perform(click());
        onView(withId(R.id.btnNewId)).perform(click());
        onView(withId(R.id.btnEditGuard)).perform(click());
        onData(hasToString(startsWith("1"))).check(matches(withText(arne.toString() + "000000")));
        onView(withId(R.id.etSearchGuard)).perform(clearText(), typeText("Arne"), closeSoftKeyboard());
        onData(hasToString(startsWith("1"))).check(matches(withText(arne.toString() + "000000")));


        testEditGuard(arne);
        testListItems(max, kai, phil);
        testDeleteGuard(max);
        Espresso.pressBack();
        loginWithGuards();
        deleteFirstGuards();
    }

    private void testEditGuard(Guard arne) {
        onView(withId(R.id.btnEditGuard)).perform(click());
        onData(hasToString(startsWith("1"))).check(matches(withText(arne.toString() + "000000")));
        onData(hasToString(startsWith("1:"))).perform(click());

        //Checks if the textfields are correct filled
        onView(withId(R.id.etForname)).check(matches(withText("Arne")));
        onView(withId(R.id.tvGuardIDNumber)).check(matches(withText("1")));
        onView(withId(R.id.etSurname)).check(matches(withText("Bartenbach")));
        onView(withId(R.id.pwUserPassword)).check(matches(withText("000000")));

        //Overwrite the old data
        onView(withId(R.id.etForname)).perform(clearText(), typeText("Max"), closeSoftKeyboard());
        onView(withId(R.id.etSurname)).perform(clearText(), typeText("Mustermann"), closeSoftKeyboard());
        onView(withId(R.id.pwUserPassword)).perform(clearText(), typeText("sicheresPW123"), closeSoftKeyboard());
        onView(withId(R.id.btnAcceptGuard)).perform(click());
    }

    private void testListItems(Guard max, Guard kai, Guard phil) {
        onView(withId(R.id.btnEditGuard)).perform(click());
        onData(hasToString(startsWith("1"))).check(matches(withText(max.toString() + "sicheresPW123")));
        onData(hasToString(startsWith("2"))).check(matches(withText(kai.toString() + "015775")));
        onData(hasToString(startsWith("3"))).check(matches(withText(phil.toString() + "01814381514781")));
    }

    private void testDeleteGuard(Guard max) {
        onData(hasToString(startsWith("1:"))).perform(click());
        onView(withId(R.id.btnDeleteGuard)).perform(click());
        onView(withId(R.id.btnEditGuard)).perform(click());
        onView(withId(R.id.guardList)).check(matches(not(withText(containsString("1")))));
        Espresso.pressBack();
    }

    private void deleteFirstGuards() {
        //login as admin
        onView(withId(R.id.etUsername)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.btnGuard)).perform(click());

        //delete the 2 guards, that are created earlier
        for(int i = 2; i < 4; i++) {
            onView(withId(R.id.btnEditGuard)).perform(click());
            onData(hasToString(startsWith(Integer.valueOf(i).toString()))).perform(click());
            onView(withId(R.id.btnDeleteGuard)).perform(click());
        }

        //back to MainActivity
        Espresso.pressBack();
        Espresso.pressBack();
    }

    private void loginWithGuards(){
        //Login as Kai
        onView(withId(R.id.etUsername)).perform(clearText(),typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(clearText(),typeText("015775"), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.tvRoutes)).check(matches(withText("Choose a route to start")));
        Espresso.pressBack();

        //login as Phil
        onView(withId(R.id.etUsername)).perform(clearText(),typeText("3"), closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(clearText(),typeText("01814381514781"), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.tvRoutes)).check(matches(withText("Choose a route to start")));
        Espresso.pressBack();
    }
}