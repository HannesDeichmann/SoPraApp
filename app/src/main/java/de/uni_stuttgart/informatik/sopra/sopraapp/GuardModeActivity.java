package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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


     public void closeActivity(){
         finish();
     }
    private void addRoutesFromDatabase() {
        Guard guard = databaseGuard.getGuardWithRoutes(loggedInGuard);

        ArrayList<String> timeList = guard.getRouteIdString();
        ArrayList<String> idList = guard.getRouteTimeString();
        if (idList.size() == timeList.size()) {
            for (int i = 0; i < idList.size(); i++) {
                Route route = databaseRoute.getRouteById(Integer.parseInt(idList.get(i)));
                GuardRoute guardRoute = new GuardRoute(route, timeList.get(i));
                if (!loggedInGuard.getGuardRouteList().contains(guardRoute)) {
                    addWaypointsToRoute(route);
                    loggedInGuard.addRoute(guardRoute);
                }
            }
        }
    }

    private void addWaypointsToRoute(Route route) {
        Route routeFromData = databaseRoute.getRouteById(Integer.parseInt(route.getRouteId()));
        for (RouteWaypointStrings s : routeFromData.getWaypointStrings()) {
            RouteWaypoint routeWaypoint = new RouteWaypoint();
            routeWaypoint.setWaypoint(databaseWaypoint.getWaypointById(s.getUserId()));
            routeWaypoint.setDuration(Duration.ofMinutes(Integer.parseInt(s.getTime())));
            route.addWaypoint(routeWaypoint);
        }
    }
    public void setLoggedInGuard(Guard guard) { this.loggedInGuard = guard; }

    public Guard getLoggedInGuard() { return this.loggedInGuard; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_mode);
        databaseGuard = new DatabaseGuard(this);
        databaseRoute = new DatabaseRoute(this);
        databaseWaypoint = new DatabaseWaypoint(this);
        loggedInGuard = (Guard) getIntent().getExtras().get("loggedInGuard");

        addRoutesFromDatabase();

        BottomNavigationView bottomNav= findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        Fragment fragment = RoutesFragment.newInstance(loggedInGuard);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_routes:
                            selectedFragment = RoutesFragment.newInstance(loggedInGuard);
                            break;
                        case R.id.nav_profile:
                            selectedFragment = ProfileFragment.newInstance(loggedInGuard);
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
    }

