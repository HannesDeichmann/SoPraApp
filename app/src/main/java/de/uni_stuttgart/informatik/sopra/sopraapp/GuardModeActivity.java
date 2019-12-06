package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.Duration;
import java.util.ArrayList;

public class GuardModeActivity extends AppCompatActivity {

    Button btnSelectARouteToStartRef;
    Button btnLogOutRef;
    TextView tvUsernameRef;
    private Guard loggedInGuard;
    DatabaseGuard databaseGuard;
    DatabaseRoute databaseRoute;
    DatabaseWaypoint databaseWaypoint;

    private void addRoutesFromDatabase(){
        ArrayList<String> timeList = databaseGuard.getGuardWithRoutes(loggedInGuard).getRouteIdString();
        ArrayList<String> idList = databaseGuard.getGuardWithRoutes(loggedInGuard).getRouteTimeString();
        System.out.println("HIERTOll:" + timeList.size());
        if (idList.size() == timeList.size()) {
            for (int i = 0; i < idList.size(); i++) {
                Route route = databaseRoute.getRouteById(Integer.parseInt(idList.get(i)));
                GuardRoute guardRoute = new GuardRoute(route, timeList.get(i));
                if(!loggedInGuard.getGuardRouteList().contains(guardRoute)){
                    addWaypointsToRoute(route);
                    loggedInGuard.addRoute(guardRoute);
                }
            }
        }
    }
    private void addWaypointsToRoute(Route route){
        Route routeFromData = databaseRoute.getRouteById(Integer.parseInt(route.getRouteId()));
        for(RouteWaypointStrings s: routeFromData.getWaypointStrings()){
            RouteWaypoint routeWaypoint = new RouteWaypoint();
            routeWaypoint.setWaypoint(databaseWaypoint.getWaypointById(Integer.parseInt(s.getUserId())));
            routeWaypoint.setDuration(Duration.ofMinutes(Integer.parseInt(s.getTime())));
            route.addWaypoint(routeWaypoint);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_mode);
        databaseGuard = new DatabaseGuard(this);
        databaseRoute = new DatabaseRoute(this);
        databaseWaypoint = new DatabaseWaypoint(this);
        loggedInGuard = (Guard) getIntent().getExtras().get("loggedInGuard");

        btnSelectARouteToStartRef = findViewById(R.id.btnSelectARouteToStart);
        btnLogOutRef = findViewById(R.id.btnLogOut);
        tvUsernameRef = findViewById(R.id.tvUsername);
        tvUsernameRef.setText(loggedInGuard.getForename()+ " " + loggedInGuard.getSurname());
        addRoutesFromDatabase();

        btnSelectARouteToStartRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GuardModeRouteListActivity.class);
                intent.putExtra("loggedInGuard", loggedInGuard);
                startActivity(intent);
            }
        });

        btnLogOutRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                finish();
            }
        });
    }
}
