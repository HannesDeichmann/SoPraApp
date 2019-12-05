package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.time.Duration;
import java.util.ArrayList;
import java.util.stream.Stream;

public class WaypointListActivity extends AppCompatActivity implements DurratoinDialog.DurationDialogListener {
    private Intent intent;
    private Duration duration;
    private Waypoint waypoint;
    private Route route;
    Button btnCancelWaypoint;
    ListView listView;
    DatabaseWaypoint databaseWaypoint;
    ArrayList<String> waypointStringList;
    int waypointId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waypoint_list);

        databaseWaypoint = new DatabaseWaypoint(this);
        btnCancelWaypoint = findViewById(R.id.btnCancelWaypoint);
        listView = findViewById(R.id.waypointList);
        waypointStringList = new ArrayList<>();

        btnCancelWaypoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WaypointActivity.newWaypoint = true;
                Intent intent = new Intent(view.getContext(), WaypointActivity.class);
                startActivity(intent);
            }
        });

        for (Waypoint waypoint : databaseWaypoint.getAllWaypoints()) {
            waypointStringList.add(waypoint.toString());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                waypointStringList);

        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            WaypointActivity.newWaypoint = false;
            //EIGENTLICH: Item.at(position) oder so ...
            waypoint = databaseWaypoint.getAllWaypoints().get(position);
            waypointId = Integer.parseInt(waypoint.getWaypointId());
            if (getIntent().getStringExtra("root").equals("WaypointActivity")) {
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
        });
    }

    public void openDialog() {
        DurratoinDialog duration = new DurratoinDialog();
        duration.show(getSupportFragmentManager(), "duration dialog");
    }

    @Override
    public void applyText(String inputDuration) {
        duration = Duration.ofMinutes(Integer.parseInt(inputDuration));
        route.addWaypoint(new RouteWaypoint(waypoint, duration));
        intent.putExtra("route", route);
        startActivity(intent);
        finish();

    }
}

