package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import static de.uni_stuttgart.informatik.sopra.sopraapp.ProtocolActivity.list;

public class PatrolActivity extends AppCompatActivity {
    private TextView tvCountdownRef;
    private TextView tvTimeRef;
    private TextView tvNextWaypointNameRef;
    private TextView tvRouteNameRef;
    private TextView tvScanFeedbackRef;
    private Button btnStartCountdownRef;
    DatabaseGuard databaseGuard;
    private Date date;
    private String protocolString;
    private Guard guard;
    private String protocolStringTimes = "";
    private Button btnScanWaypointRef;
    public int nextWaypointCounter;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 0;
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol);

        nextWaypointCounter = 0;
        tvTimeRef = findViewById(R.id.tvTime);
        tvCountdownRef = findViewById(R.id.tvCountdown);
        tvRouteNameRef = findViewById(R.id.tvRouteName);
        tvNextWaypointNameRef = findViewById(R.id.tvNextWaypointName);
        tvScanFeedbackRef = findViewById(R.id.tvScanFeedback);
        btnStartCountdownRef = findViewById(R.id.btnStartCountdown);
        btnScanWaypointRef = findViewById(R.id.btnScanWaypoint);


        btnStartCountdownRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStop();
            }

        });
        setupInformation();

        btnScanWaypointRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                GuardRoute selectedRoute = (GuardRoute) getIntent().getExtras().get("selectedRoute");
                Route route = selectedRoute.getRoute();

                //check if timer is running or the scanned waypoint was the last one
                if (timerRunning || nextWaypointCounter == route.getWaypoints().size()) {

                    //check if the scanned waypoint was the last one
                    if (nextWaypointCounter == route.getWaypoints().size()) {
                        date = new Date();
                        protocolString += sdf.format(date) + "; " + "false" + protocolStringTimes;
                        list.add(protocolString);
                        Guard loggedInGuard = (Guard) getIntent().getExtras().get("loggedInGuard");
                        Intent intent = new Intent(view.getContext(), GuardModeActivity.class);
                        intent.putExtra("loggedInGuard", loggedInGuard);
                        startActivity(intent);
                        finish();
                    } else {
                        date = new Date();
                        protocolStringTimes += " ;" + sdf.format(date);
                        nextWaypointCounter += 1;
                        stopTimer();
                        setupInformation();
                    }
                } else {
                    tvScanFeedbackRef.setText("Please start the timer before scanning");
                }
            }

        });

    }

    /**
     * Sets up the information gui for the guard:
     * If the last waypoint is reached, no new timer is set and no next waypoint is shown
     */
    public void setupInformation() {

        GuardRoute selectedRoute = (GuardRoute) getIntent().getExtras().get("selectedRoute");
        Route route = selectedRoute.getRoute();


        if (nextWaypointCounter == route.getWaypoints().size()) {
            tvNextWaypointNameRef.setText("Please finish route");
            btnScanWaypointRef.setText("Finish route");
        } else {
            RouteWaypoint nextWaypoint = route.getWaypoints().get(nextWaypointCounter);
            long timeLeft = nextWaypoint.getDuration().toMinutes();
            timeLeftInMilliseconds = 60000 * timeLeft; // 60000 millisec = 1 min
            String startTime = selectedRoute.getTime();
            tvTimeRef.setText(startTime);
            tvRouteNameRef.setText(route.getRouteName());
            tvNextWaypointNameRef.setText(nextWaypoint.getWaypoint().getWaypointName());
            updateTimer();
            startTimer();
        }
        guard = (Guard) getIntent().getExtras().get("loggedInGuard");
        protocolString = route.getRouteName() + route.getRouteId() + "; " + guard.getForename() + " " + guard.getSurname() + "; " + selectedRoute.getTime() + "; ";
    }

    public void startStop() {
        if (timerRunning) {
            stopTimer();
        } else {
            startTimer();
        }
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();

        btnStartCountdownRef.setText("PAUSE");
        timerRunning = true;
    }

    public void stopTimer() {
        countDownTimer.cancel();
        btnStartCountdownRef.setText("START");
        timerRunning = false;
        //TODO log Entry
    }

    public void updateTimer() {
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;

        String timeLeftText;
        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        tvCountdownRef.setText(timeLeftText);
    }
}
