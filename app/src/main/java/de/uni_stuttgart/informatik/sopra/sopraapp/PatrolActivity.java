package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PatrolActivity extends AppCompatActivity {
    private TextView tvCountdownRef;
    private TextView tvTimeRef;
    private TextView tvNextWaypointNameRef;
    private TextView tvRouteNameRef;
    private TextView tvScanFeedbackRef;
    private TextView tvNoteRef;
    private Date date;
    private Guard guard;
    private Button btnStartCountdownRef;
    private Button btnScanWaypointRef;
    private Button btnCancelActiveRouteRef;
    private Button btnShowMapRef;
    public int nextWaypointCounter;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 0;
    private boolean timerRunning;
    private ListView lvCompleteRouteRef;
    DatabasePatrol databasePatrol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol);

        nextWaypointCounter = 0;
        tvTimeRef = findViewById(R.id.tvTime);
        tvCountdownRef = findViewById(R.id.tvCountdown);
        tvRouteNameRef = findViewById(R.id.tvRouteName);
        tvNoteRef = findViewById(R.id.tvNote);
        tvNextWaypointNameRef = findViewById(R.id.tvNextWaypointName);
        tvScanFeedbackRef = findViewById(R.id.tvScanFeedback);
        btnStartCountdownRef = findViewById(R.id.btnStartCountdown);
        btnScanWaypointRef = findViewById(R.id.btnScanWaypoint);
        btnCancelActiveRouteRef = findViewById(R.id.btnCancelActiveRoute);
        btnShowMapRef = findViewById(R.id.btnShowMap);
        lvCompleteRouteRef = findViewById(R.id.lvCompleteRoute);
        databasePatrol = new DatabasePatrol(this);
        GuardRoute selectedRoute = (GuardRoute) getIntent().getExtras().get("selectedRoute");
        Route route = selectedRoute.getRoute();
        ArrayList<RouteWaypoint> waypointList = route.getWaypoints();
        ArrayList<String> waypointsStringList = new ArrayList<>();


        for(RouteWaypoint routeWaypoint  : waypointList){
            int durationInt = (int) routeWaypoint.getDuration().toMinutes();
            String duration = Integer.toString(durationInt);
            waypointsStringList.add(routeWaypoint.getWaypoint().getWaypointName()
                    + " " + duration + "min");

        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                waypointsStringList);

        lvCompleteRouteRef.setAdapter(dataAdapter);
        lvCompleteRouteRef.setDividerHeight(5);
        setupInformation();
        btnShowMapRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MapActivity.class);
                intent.putExtra("route", route);
                startActivity(intent);
            }
        });
        btnStartCountdownRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStop();
            }
        });
        btnCancelActiveRouteRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databasePatrol.updatePatrolString("(interrupted)");
                finish();
            }
        });
        btnScanWaypointRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

                //check if timer is running or the scanned waypoint was the last one
                if (timerRunning || nextWaypointCounter == route.getWaypoints().size()) {

                    //check if the scanned waypoint was the last one
                    if (nextWaypointCounter == route.getWaypoints().size()) {
                        date = new Date();
                        databasePatrol.updatePatrolString("finished at:" + sdf.format(date));
                        Guard loggedInGuard = (Guard) getIntent().getExtras().get("loggedInGuard");
                        Intent intent = new Intent(view.getContext(), GuardModeActivity.class);
                        finish();
                    } else {
                        date = new Date();
                        String actuallWaypointName = route.getWaypoints().get(nextWaypointCounter).getWaypoint().getWaypointName();
                        DrawingView.addDoneWaypoint(route.getWaypoints().get(nextWaypointCounter).getWaypoint());
                        databasePatrol.updatePatrolString(actuallWaypointName + ": " + sdf.format(date) + ";");
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
            tvNextWaypointNameRef.setText("");
            btnScanWaypointRef.setText("Finish route");
            tvNoteRef.setText("Please finish route");
        } else {
            RouteWaypoint nextWaypoint = route.getWaypoints().get(nextWaypointCounter);
            long timeLeft = nextWaypoint.getDuration().toMinutes();
            timeLeftInMilliseconds = 60000 * timeLeft; // 60000 millisec = 1 min
            String formattedStartTime = formatStartTime(selectedRoute.getTime());
            tvTimeRef.setText(formattedStartTime);
            tvRouteNameRef.setText(route.getRouteName());
            tvNoteRef.setText(nextWaypoint.getWaypoint().getWaypointNote());
            tvNextWaypointNameRef.setText(nextWaypoint.getWaypoint().getWaypointName());
            updateTimer();
            startTimer();
        }
        guard = (Guard) getIntent().getExtras().get("loggedInGuard");
    }

    public String formatStartTime(String time){
        //String formattedTime = time.substring(0,1) + ":" + time.substring(2,3);
        String formattedTime = new StringBuilder(time).insert(2, ":").toString();
        return formattedTime;
    }

    public void startStop() {
        if (timerRunning) {
            stopTimer();
            databasePatrol.updatePatrolString("(break)");
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
        if(timeLeftText.equals("0:00")){
            Toast.makeText(PatrolActivity.this, "Silent alarm would now be sent",
                    Toast.LENGTH_SHORT).show();
           databasePatrol.updatePatrolString("(alarm)");
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(timerRunning) {
            stopTimer();
        }
    }
}
