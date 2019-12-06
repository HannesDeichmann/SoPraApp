package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

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
    private  TextView tvRouteNameRef;
    private Button btnStartCountdownRef;
    DatabaseGuard databaseGuard;
    private Date date;
    private String protocolString;
    private Guard guard;
    private String protocolStringTimes;

    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 0;
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol);
        tvTimeRef = findViewById(R.id.tvTime);
        tvCountdownRef = findViewById(R.id.tvCountdown);
        tvRouteNameRef = findViewById(R.id.tvRouteName);
        tvNextWaypointNameRef = findViewById(R.id.tvNextWaypointName);
        btnStartCountdownRef = findViewById(R.id.btnStartCountdown);

        GuardRoute selectedRoute = (GuardRoute) getIntent().getExtras().get("selectedRoute");
        String startTime =  selectedRoute.getTime();
        Route route = selectedRoute.getRoute();
        RouteWaypoint nextWaypoint = route.getWaypoints().get(0);
        long timeLeft = nextWaypoint.getDuration().toMinutes();
        timeLeftInMilliseconds = 60000*timeLeft; // 60000 millisec = 1 min
        guard = (Guard) getIntent().getExtras().get("loggedInGuard");
        protocolString = route.getRouteName()+route.getRouteId()+"; "+guard.getForename()+" "+guard.getSurname()+"; "+selectedRoute.getTime()+"; ";
//TODO add to when scaned: date = new Date();
//                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//                protocolStringTimes += " ;"+sdf.format(date);

//TODO add to when finished (list empty vlt) bzw falls vorzeitig abgebrochen
//                          date = new Date();
//                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//                     protocolString += sdf.format(date)+"; "+booleanIfVorzeitigbeendet+protocolStringTimes;
//                  list.add(protocolString);

        tvTimeRef.setText(startTime);
        tvRouteNameRef.setText(route.getRouteName());
        tvNextWaypointNameRef.setText(route.getWaypoints().get(0).getWaypoint().getWaypointName());

        btnStartCountdownRef.setOnClickListener(view -> startStop());

        updateTimer();

    }

    public void startStop() {
        if (timerRunning){
            stopTimer();
        }else{
            startTimer();
        }
    }

    public void startTimer(){
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
        timerRunning=true;
    }

    public void stopTimer() {
        countDownTimer.cancel();
        btnStartCountdownRef.setText("START");
        timerRunning=false;
        //TODO log Entry
    }

    public void updateTimer(){
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
