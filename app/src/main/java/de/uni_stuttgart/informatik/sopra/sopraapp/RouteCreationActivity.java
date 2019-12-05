package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RouteCreationActivity extends AppCompatActivity {
    private ArrayList<String> list = new ArrayList<>();
    private ListView selectedWaypointList;
    private ArrayAdapter<String> myArrayAdapter;
    private Button addWaypointRef;
    private Button btnSaveRoute;
    private Button btnDeleteRoute;
    private TextView tvRouteName;
    private Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_creation);
        route = new Route();
        selectedWaypointList = findViewById(R.id.selectedWaypointList);
        addWaypointRef = findViewById(R.id.addWaypoint);
        myArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list);
        btnSaveRoute = findViewById(R.id.saveRoute);
        btnDeleteRoute = findViewById(R.id.deleteRoute);
        tvRouteName = findViewById(R.id.routeName);
        selectedWaypointList.setAdapter(myArrayAdapter);
        btnSaveRoute.setOnClickListener(v -> {
            route.setName(tvRouteName.getText().toString());
            //TODO add Route Object to Database
            i = 0;
            finish();
        });
        btnDeleteRoute.setOnClickListener(v -> {
            //TODO delete route from database
            i = 0;
            finish();
        });
        addWaypointRef.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), WaypointListActivity.class);
            intent.putExtra("root", "RouteCreationActivity");
            intent.putExtra("route", route);
            startActivity(intent);
            finish();
        });


    }

    private static int i = 0;

    @Override
    protected void onResume() {
        super.onResume();
        if (i > 0) {
            route = (Route) getIntent().getExtras().get("route");
            for (int j = 0; j < route.getWaypoints().size(); j++) {
                list.add(route.getWaypoints().get(j).getWaypoint().getWaypointName() + " - " + route.getWaypoints().get(j).getDuration().toMinutes() + "min.");
            }
            myArrayAdapter.notifyDataSetChanged();

        } else {
            i++;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        i=0;
    }

    //not used jet
    public void updateLists(RouteWaypoint waypoint) {
        route.getWaypoints().add(waypoint);
        list.add(waypoint.getWaypoint().getWaypointName() + " - " + waypoint.getDuration().toMinutes() + "min.");
    }


}
