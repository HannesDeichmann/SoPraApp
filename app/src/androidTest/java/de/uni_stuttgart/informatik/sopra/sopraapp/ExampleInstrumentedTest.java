package de.uni_stuttgart.informatik.sopra.sopraapp;


import android.widget.Button;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

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
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class);


    @Test
    public void useAppContext() throws Exception {

        MainActivity activity = rule.getActivity();

        TextView tvHello = (TextView) activity.findViewById(R.id.tvLogin);
        assertThat(tvHello,notNullValue());
        assertThat(tvHello, instanceOf(TextView.class));
        assertEquals("TextView text compare", "SoPra App", tvHello.getText().toString());

        Button btnButton =(Button) activity.findViewById(R.id.btnLogin);
        assertThat(btnButton,notNullValue());
        assertEquals("Test button text", "Click me!", btnButton.getText().toString());

    }
}
