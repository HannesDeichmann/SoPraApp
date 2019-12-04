package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.time.Duration;
import java.util.ArrayList;

public class RouteCreationActivity extends AppCompatActivity {
    private ArrayList<String> list = new ArrayList<>();
    private ListView selectedWaypointList;
    private ArrayAdapter<String> myArrayAdapter;
    private Button addWaypointRef;
    private Button btnSaveRoute;
    private Button btnDeleteRoute;
    private TextView tvRouteName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_creation);
        Route route = new Route();
        selectedWaypointList = findViewById(R.id.selectedWaypointList);
        addWaypointRef = findViewById(R.id.addWaypoint);
        myArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, list);
        btnSaveRoute = findViewById(R.id.saveRoute);
        btnDeleteRoute = findViewById(R.id.deleteRoute);
        tvRouteName = findViewById(R.id.routeName);
        btnSaveRoute.setOnClickListener(v -> {
            route.setName(tvRouteName.getText().toString());
            //TODO add Route Object to Database
            list.clear();
         finish();
        });
        btnDeleteRoute.setOnClickListener(v -> {
            list.clear();
            finish();
        });
        addWaypointRef.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), WaypointListActivity.class);
            intent.putExtra("root","RouteCreationActivity");
            intent.putExtra("routeObj", route);
            startActivity(intent);
            finish();
        });

        //if Activity ist started from an other Intent with Information in Extra this if else need to be specified
        if(getIntent().getExtras()!=null) {
            RouteWaypoint waypoint = (RouteWaypoint) getIntent().getExtras().get("selectedWaypoint");
            list.add(waypoint.getWaypoint().getWaypointName()+" - Time: "+ waypoint.getDuration().toMinutes()+"min");
            Duration duration = (Duration) getIntent().getExtras().get("timeToWaypoint");
            route.addWaypoint(waypoint);
        }
       selectedWaypointList.setAdapter(myArrayAdapter);
    }
}
