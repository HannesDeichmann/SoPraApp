package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.time.Duration;
import java.util.ArrayList;

public class WaypointListActivity extends AppCompatActivity implements DurationDialog.DurationDialogListener {
    private Intent intent;
    private Duration duration;
    private Waypoint waypoint;
    private Route route;
    ListView listView;
    DatabaseWaypoint databaseWaypoint;
    ArrayList<String> waypointStringList;
    int waypointId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waypoint_list);

        databaseWaypoint = new DatabaseWaypoint(this);
        listView = findViewById(R.id.waypointList);
        waypointStringList = new ArrayList<>();

        for (Waypoint waypoint : databaseWaypoint.getAllWaypoints()) {
            waypointStringList.add(waypoint.toString());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                waypointStringList);

        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            //EIGENTLICH: Item.at(position) oder so ...
            waypoint = databaseWaypoint.getAllWaypoints().get(position);
            waypointId = Integer.parseInt(waypoint.getWaypointId());
            if (getIntent().hasExtra("position")){
                route = (Route) getIntent().getExtras().get("route");
                openDialog();
                intent = new Intent(view.getContext(), RouteCreationActivity.class);
                intent.putExtra("route", route);
            }else {
                if (getIntent().getStringExtra("root").equals("WaypointActivity")) {
                    WaypointActivity.newWaypoint = false;
                    intent = new Intent(view.getContext(), WaypointActivity.class);
                    intent.putExtra("editedWaypointId", waypointId);

                    startActivity(intent);
                    finish();
                } else if (getIntent().getStringExtra("root").equals("RouteCreationActivity")) {
                    //  intent = new Intent(view.getContext(), RouteCreationActivity.class);
                    route = (Route) getIntent().getExtras().get("route");
                    openDialog();
                    intent = new Intent(view.getContext(), RouteCreationActivity.class);

                } else {
                    //intent extra should be one of the checked above
                }
            }
        });
    }

    public void openDialog() {
        DurationDialog duration = new DurationDialog();
        duration.show(getSupportFragmentManager(), "duration dialog");
    }

    @Override
    public void applyText(String inputDuration) {
        duration = Duration.ofMinutes(Integer.parseInt(inputDuration));
        if(getIntent().hasExtra("position")){
            route.replaceWaypointAt(new RouteWaypoint(waypoint, duration), (int) getIntent().getExtras().get("position"));
        }else {
            route.addWaypoint(new RouteWaypoint(waypoint, duration));
        }
        intent.putExtra("route", route);
        startActivity(intent);
        finish();

    }
}

