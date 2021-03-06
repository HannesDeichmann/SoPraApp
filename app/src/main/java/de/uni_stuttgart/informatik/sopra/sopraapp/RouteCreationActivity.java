package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.Duration;
import java.util.ArrayList;


/**
 * Activity to create a Route.
 * Add Waypoints with duration
 *
 * @author Arne Bartenbach 3392087
 * @version 22.01.2020
 */
public class RouteCreationActivity extends AppCompatActivity {
    private ArrayList<String> list = new ArrayList<>();
    private ListView selectedWaypointList;
    private ArrayAdapter<String> myArrayAdapter;
    private Button btnAddWaypointRef;
    private Button btnSaveRoute;
    private Button btnAddOldWaypoints;
    private Button btnDeleteRoute;
    private Button btnShowMapRef;
    private EditText etRouteName;
    private Route route;
    private boolean oldWPcklick;
    DatabaseRoute databaseRoute;
    DatabaseWaypoint databaseWaypoint;

    private RouteWaypoint createRouteWaypointByPos(int pos){
        RouteWaypoint routeWaypoint = new RouteWaypoint();

        routeWaypoint.setWaypoint(databaseWaypoint.getWaypointById(
                route.getWaypointStrings().get(pos).getUserId()));

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
        btnAddWaypointRef = findViewById(R.id.btnAddWaypoint);
        btnSaveRoute = findViewById(R.id.saveRoute);
        btnShowMapRef = findViewById(R.id.btnShowMapInRouteActivity);
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
        selectedWaypointList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(view.getContext(), WaypointListActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("root", "RouteCreationActivity");
            intent.putExtra("route", route);
            list.remove(position);
            startActivity(intent);
            finish();
        });

        btnAddOldWaypoints.setOnClickListener(v -> {
            oldWPcklick = true;
            route = (Route) getIntent().getExtras().get("editRoute");
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
            if (etRouteName.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Route name is missing!", Toast.LENGTH_SHORT).show();
            } else if (route.getWaypoints().size() == 0 && route.getWaypointStrings() == null) {
                Toast.makeText(getApplicationContext(), "Route has no Waypoints!", Toast.LENGTH_SHORT).show();
            } else if(route.getWaypointStrings()!=null &&((route.getWaypointStrings().size()==0 && route.getWaypoints().size()==0))){

                    Toast.makeText(getApplicationContext(), "Route has no Waypoints!", Toast.LENGTH_SHORT).show();
            }else{
                //wegpunkte wurden schon in der Waypointlistaktivity hinzugef??gt
                route.setRouteName(etRouteName.getText().toString());
                for (Route r : databaseRoute.getAllRoutes()) {
                    if (r.getRouteId().equals(route.getRouteId())) {
                        databaseRoute.deleteRoute(route);
                    }
                }
                databaseRoute.addRoute(route);
                finish();
            }
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

        btnShowMapRef.setOnClickListener(view -> {
            if(list.size()>0 ){
                Intent intent = new Intent(view.getContext(), MapActivity.class);
                intent.putExtra("RouteActivity", route);
                DrawingView.drawRouteInRouteActivity = true;
                startActivity(intent);
                if(getIntent().hasExtra("route")){
                    myArrayAdapter.clear();
                }
            }else {
                Toast.makeText(getApplicationContext(),"There are 0 Waypoints in the List",Toast.LENGTH_SHORT).show();
            }
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

    public void updateLists(RouteWaypoint waypoint) {
        route.getWaypoints().add(waypoint);
        list.add(waypoint.getWaypoint().getWaypointName() + " - " + waypoint.getDuration().toMinutes() + "min.");
    }
}
