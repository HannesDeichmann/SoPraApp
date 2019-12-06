package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.Duration;
import java.util.ArrayList;

public class GuardModeRouteListActivity extends AppCompatActivity {


    private Duration duration;
    DatabaseGuard databaseGuard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_mode_route_list);
        ListView listView = findViewById(R.id.guardRouteList);

        ///////////////////////////////////////////////////
        Route route = new Route();
        route.setRouteName("Route um die Ecke The One");
        route.setRouteId("123123");
        Route route2 = new Route();
        route2.setRouteName("Route um die Ecke2/3");
        Waypoint DieEckeHinterDemDönerladen= new Waypoint("DieEckeHinterDemDoenerladen", "123456", "Tag1", "Hitler");
        Waypoint DieEckeVorDemDönerladen= new Waypoint("DieEckeVorDemDoenerladen", "223456", "Tag2", "Hitler");
        Waypoint DieEckeHinterDemDönerladenTheRevenge= new Waypoint("DieEckeHinterDemDoenerladenTheRevenge", "323456", "Tag3", "Hitler");
        String minutes = "2";
        duration = Duration.ofMinutes(Integer.parseInt(minutes));
        RouteWaypoint routeWaypoint1= new RouteWaypoint(DieEckeHinterDemDönerladen, duration);
        RouteWaypoint routeWaypoint2= new RouteWaypoint(DieEckeVorDemDönerladen, duration);
        RouteWaypoint routeWaypoint3= new RouteWaypoint(DieEckeHinterDemDönerladenTheRevenge, duration);
        route.addWaypoint(routeWaypoint1);
        route.addWaypoint(routeWaypoint2);
        route.addWaypoint(routeWaypoint3);
        GuardRoute guardRoute1 = new GuardRoute(route, "1401");
        GuardRoute guardRoute2 = new GuardRoute(route2, "1401");
        GuardRoute guardRoute3 = new GuardRoute(route2, "1401");
        Guard otto = new Guard("otto", "muellerich", "2", "1234");
        databaseGuard = new DatabaseGuard(this);
        databaseGuard.addGuard(otto);
        otto.addRoute(guardRoute1);
        otto.addRoute(guardRoute2);
        otto.addRoute(guardRoute3);
        Guard loggedInGuard = otto;
        ///////////////////////////////////////////////////////////

        //Guard loggedInGuard = (Guard) getIntent().getExtras().get("loggedInGuard");
        ArrayList<GuardRoute> guardRouteList = loggedInGuard.getGuardRouteList();

        ArrayList<String> routeStringList = new ArrayList<>();


        for(GuardRoute guardRoute : guardRouteList){
                routeStringList.add(guardRoute.getRoute().getRouteName());

        }

        routeStringList.add(loggedInGuard.getForename());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                routeStringList);

        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(view.getContext(), PatrolActivity.class);
                GuardRoute selectedRoute= guardRouteList.get(position);
                intent.putExtra("selectedRoute", selectedRoute);
                intent.putExtra("loggedInGuard", loggedInGuard);
                startActivity(intent);
                finish();
            }
        });
    }
}
