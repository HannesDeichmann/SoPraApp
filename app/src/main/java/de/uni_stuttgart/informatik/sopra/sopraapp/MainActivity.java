package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.Duration;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView tvLoginRef;
    Button btnAdminLoginRef;
    DatabaseGuard databaseGuard;
    DatabaseWaypoint databaseWaypoint;
    EditText etUsernameRef;
    EditText etPasswordRef;
    Button btnLoginRef;
    String guardUsername;
    String guardPassword;
    TextView tvLoginFeedbackRef;
    private Duration duration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        databaseGuard = new DatabaseGuard(this);
        databaseWaypoint = new DatabaseWaypoint(this);

        //TODO BUG: Database darf nicht leer sein
        if(databaseGuard.getGuardCount() == 0) {
            databaseGuard.addGuard(new Guard("Damit", "Database", "123"));
            databaseGuard.addGuard(new Guard("Damit", "Database", "!= empty"));
            Route route = new Route();
            Waypoint dieEckeHinterDemDönerladen= new Waypoint("DieEckeHinterDemDönerladen", "223456", "Tag1", "Hitler");
            Duration duration = Duration.ofMinutes(600);
            RouteWaypoint routeWaypoint= new RouteWaypoint(dieEckeHinterDemDönerladen, duration);
            route.addWaypoint(routeWaypoint);
        }
        if(databaseWaypoint.getWaypointCount() == 0) {
            databaseWaypoint.addWaypoint(new Waypoint("FirstWaypoint", "123456", "Tag", "Note"));
        }

        /*Test Data Set Hardcoded//////////////////////////////////////////
        Route route = new Route();
        *Waypoint DieEckeHinterDemDönerladen= new Waypoint("DieEckeHinterDemDönerladen", "223456", "Tag1", "Hitler");
        String minutes = "600";
        duration = Duration.ofMinutes(Integer.parseInt(minutes));
        RouteWaypoint routeWaypoint= new RouteWaypoint(DieEckeHinterDemDönerladen, duration);
        route.addWaypoint(routeWaypoint);
        GuardRoute guardRoute1 = new GuardRoute(route, "1401");
        GuardRoute guardRoute2 = new GuardRoute(route, "1401");
        GuardRoute guardRoute3 = new GuardRoute(route, "1401");

        Guard otto = new Guard("otto", "müllerich", "1234");
        otto.addRoute(guardRoute1);
        otto.addRoute(guardRoute2);
        otto.addRoute(guardRoute3);
        databaseGuard.addGuard(otto);
        */

        tvLoginRef = (TextView) findViewById(R.id.tvLogin);
        btnAdminLoginRef = (Button) findViewById(R.id.btnAdminLogin);
        etUsernameRef = findViewById(R.id.etUsername);
        etPasswordRef = findViewById(R.id.etPassword);
        btnLoginRef = findViewById(R.id.btnLogin);
        tvLoginFeedbackRef = findViewById(R.id.tvLoginFeedback);
        tvLoginFeedbackRef.setText("");

        btnAdminLoginRef.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AdminActivity.class);
                startActivity(intent);
            }
        });

        btnLoginRef.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                guardUsername=etUsernameRef.getText().toString();
                guardPassword=etPasswordRef.getText().toString();

                //the admin mode is accessable with the username "admin and password "admin"
                if(guardUsername.equals("admin") && guardPassword.equals("admin")){
                    Intent intent = new Intent(view.getContext(), AdminActivity.class);
                    startActivity(intent);
                }else if(guardUsername.equals("") || guardPassword.equals("")) {
                    tvLoginFeedbackRef.setText("Please enter a username and a password");
                }else{
                    //check every guard if the given username is found and if yes, check if the
                    //passwords match
                    for (Guard guard : databaseGuard.getAllGuards()) {
                        if (guard.getUserId().equals(guardUsername)) {
                            if (guard.getUserPassword().equals(guardPassword)) {
                                Intent intent = new Intent(view.getContext(), GuardModeActivity.class);
                                intent.putExtra("guard", guard);
                                startActivity(intent);
                                tvLoginFeedbackRef.setText("");
                                etUsernameRef.setText("");
                                etPasswordRef.setText("");
                            } else {
                                tvLoginFeedbackRef.setText("Wrong password");
                                etPasswordRef.setText("");
                            }
                        }
                    }
                    if (!tvLoginFeedbackRef.getText().toString().equals("Wrong password")) {
                        tvLoginFeedbackRef.setText("No such username");
                    }
                }
            }
        });
    }
}
