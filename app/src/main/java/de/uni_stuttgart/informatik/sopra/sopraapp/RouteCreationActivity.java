package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.ArrayList;

public class RouteCreationActivity extends AppCompatActivity {
    private ArrayList<String> list = new ArrayList<>();
    private ListView selectedWaypointList;
    private ArrayAdapter<String> myArrayAdapter;
    private Button btnAddWaypointRef;
    private Button btnSaveRoute;
    private Button btnAddOldWaypoints;
    private Button btnDeleteRoute;
    private EditText etRouteName;
    private Route route;
    DatabaseRoute databaseRoute;
    DatabaseWaypoint databaseWaypoint;
    Route editRoute;

    private RouteWaypoint createRouteWaypointByPos(int pos){
        RouteWaypoint routeWaypoint = new RouteWaypoint();

        routeWaypoint.setWaypoint(databaseWaypoint.getWaypointById(Integer.parseInt
                (route.getWaypointStrings().get(pos).getUserId())));

        routeWaypoint.setDuration(Duration.ofMinutes(Integer.parseInt
                (route.getWaypointStrings().get(pos).getTime())));
        return routeWaypoint;
    }

    private void insertIntoList(int position, Route route){
        list.add(route.getWaypoints().get(position).getWaypoint().getWaypointName()
                + " - " + route.getWaypoints().get(position).getDuration().toMinutes() + "min.");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_creation);
        myArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list);
        route = new Route();
        databaseRoute = new DatabaseRoute(this);
        databaseWaypoint = new DatabaseWaypoint(this);

        selectedWaypointList = findViewById(R.id.selectedWaypointList);
        btnAddWaypointRef = findViewById(R.id.addWaypoint);
        btnSaveRoute = findViewById(R.id.saveRoute);
        btnDeleteRoute = findViewById(R.id.deleteRoute);
        etRouteName = findViewById(R.id.etRouteName);
        selectedWaypointList.setAdapter(myArrayAdapter);
        btnAddOldWaypoints = findViewById(R.id.btnAddOldWaypoints);

        if (getIntent().hasExtra("editRoute")) {
            route = (Route) getIntent().getExtras().get("editRoute");
            etRouteName.setText(route.getRouteName());
            btnAddOldWaypoints.setVisibility(View.VISIBLE);
        } else {
            btnAddOldWaypoints.setVisibility(View.INVISIBLE);
        }

        btnAddOldWaypoints.setOnClickListener(v -> {
            route= ((Route) getIntent().getExtras().get("editRoute"));
            for (int i = 0; i < route.getWaypointStrings().size(); i++) {
                route.addWaypoint(createRouteWaypointByPos(i));
            }
            for (int j = 0; j < route.getWaypoints().size(); j++) {
                insertIntoList(j,route);
            }
            myArrayAdapter.notifyDataSetChanged();
            btnAddOldWaypoints.setVisibility(View.INVISIBLE);
        });

        btnSaveRoute.setOnClickListener(v -> {
            //wegpunkte wurden schon in der Waypointlistaktivity hinzugefügt
            for(Route r:databaseRoute.getAllRoutes()){
                if(r.getRouteId().equals(route.getRouteId())){
                    databaseRoute.deleteRoute(route);
                }
            }
            databaseRoute.addRoute(route);
            finish();
        });

        btnDeleteRoute.setOnClickListener(v -> {
            if (getIntent().hasExtra("editRoute")) {
                databaseRoute.deleteRoute((Route) getIntent().getExtras().get("editRoute"));
            }
            finish();
        });

        btnAddWaypointRef.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), WaypointListActivity.class);
            intent.putExtra("root", "RouteCreationActivity");
            route.setRouteName(etRouteName.getText().toString());
            intent.putExtra("route", route);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().hasExtra("route")) {
            route = (Route) getIntent().getExtras().get("route");
            etRouteName.setText(route.getRouteName());
            for (int j = 0; j < route.getWaypoints().size(); j++) {
                insertIntoList(j,route);
            }
            myArrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void updateLists(RouteWaypoint waypoint) {
        route.getWaypoints().add(waypoint);
        list.add(waypoint.getWaypoint().getWaypointName() + " - " + waypoint.getDuration().toMinutes() + "min.");
    }
}
