package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RouteCreationActivity extends AppCompatActivity {
    private ArrayList<String> list = new ArrayList<>();
    private ListView selectedWaypointList;
    private ArrayAdapter<String> myArrayAdapter;
    private Button btnAddWaypointRef;
    private Button btnSaveRoute;
    private Button btnDeleteRoute;
    private EditText etRouteName;
    private Route route;
    DatabaseRoute databaseRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_creation);
        myArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list);
        route = new Route();
        databaseRoute = new DatabaseRoute(this);

        selectedWaypointList = findViewById(R.id.selectedWaypointList);
        btnAddWaypointRef = findViewById(R.id.addWaypoint);
        btnSaveRoute = findViewById(R.id.saveRoute);
        btnDeleteRoute = findViewById(R.id.deleteRoute);
        etRouteName = findViewById(R.id.etRouteName);
        selectedWaypointList.setAdapter(myArrayAdapter);

        if(getIntent().hasExtra("editGuard")){
            Route editRoute = (Route)getIntent().getExtras().get("editRoute");
            etRouteName.setText(editRoute.getRouteName());
        } else if(getIntent().hasExtra("route")) {
            etRouteName.setText(route.getRouteName());
        }

        btnSaveRoute.setOnClickListener(v -> {
            route.setRouteName(etRouteName.getText().toString());
            //wegpunkte wurden schon in der Waypointlistaktivity hinzugefügt
            databaseRoute.addRoute(route);
            i = 0;
            finish();
        });

        btnDeleteRoute.setOnClickListener(v -> {
            databaseRoute.deleteRoute(route);
            i = 0;
            finish();
        });

        btnAddWaypointRef.setOnClickListener(view -> {
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
                ArrayList<RouteWaypoint> r = route.getWaypoints();
                RouteWaypoint rw= r.get(j);
                Waypoint wp = rw.getWaypoint();
                String s = wp.getWaypointName();
                list.add(s + " - " + route.getWaypoints().get(j).getDuration().toMinutes() + "min.");
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
