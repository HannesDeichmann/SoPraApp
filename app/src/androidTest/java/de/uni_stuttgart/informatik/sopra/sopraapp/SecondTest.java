package de.uni_stuttgart.informatik.sopra.sopraapp;


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

    @Rule
    public ActivityTestRule<DisplayActivity> rule = new ActivityTestRule<DisplayActivity>(DisplayActivity.class)
    {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent intent = new Intent(targetContext, DisplayActivity.class);
            intent.putExtra("Message", "Hello");
            return intent;
        }
    };


    @Test
    public void useAppContext() throws Exception {

        DisplayActivity activity = rule.getActivity();


        TextView tvMessage = (TextView) activity.findViewById(R.id.tvMessage);
        assertThat(tvMessage,notNullValue());
        assertEquals("TextView text compare", "Hello", tvMessage.getText().toString());



    }
}
