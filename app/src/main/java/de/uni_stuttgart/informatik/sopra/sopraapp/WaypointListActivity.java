package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

public class WaypointListActivity extends AppCompatActivity implements DurationDialog.DurationDialogListener {
    private Intent intent;
    private Duration duration;
    private Waypoint waypoint;
    private Route route;
    private Button btnCancelWPSelect;
    ListView listView;
    DatabaseWaypoint databaseWaypoint;
    DatabaseRoute databaseRoute;
    ArrayList<String> waypointStringList;
    int waypointId;
    EditText etSearchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waypoint_list);

        btnCancelWPSelect = findViewById(R.id.btnCancelWPSelect);
        btnCancelWPSelect.setVisibility(View.GONE);
        if (getIntent().hasExtra("position")) {
            btnCancelWPSelect.setVisibility(View.VISIBLE);
        }
            databaseWaypoint = new DatabaseWaypoint(this);
        btnCancelWPSelect = findViewById(R.id.btnCancelWPSelect);
        listView = findViewById(R.id.waypointList);
        waypointStringList = new ArrayList<>();
        etSearchText = findViewById(R.id.etSearchWaypoint);
        ArrayList<Waypoint> allWaypoints = databaseWaypoint.getAllWaypoints();
        databaseRoute = new DatabaseRoute(this);
        for (Waypoint waypoint : allWaypoints) {
            waypointStringList.add(waypoint.toString());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                waypointStringList);

        listView.setAdapter(dataAdapter);

        etSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = etSearchText.getText().toString();
                waypointStringList.clear();
                for(Waypoint wp: allWaypoints){
                    if(wp.toString().contains(searchText)){waypointStringList.add(wp.toString()); }
                }
                dataAdapter.notifyDataSetChanged();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) { }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        btnCancelWPSelect.setOnClickListener(view -> {
            route = (Route) getIntent().getExtras().get("route");
            int position = getIntent().getIntExtra("position",0);
            route.deleteWaypoint(route.getWaypoints().get(position));
            route.getWaypointStrings().remove(position);
            databaseRoute.updateWaypointStrings(route);
            intent = new Intent(view.getContext(), RouteCreationActivity.class);
            intent.putExtra("route", route);
            startActivity(intent);
            finish();
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String clickedWaypoint = waypointStringList.get(position);
            for(Waypoint wp: allWaypoints){
                if(wp.getWaypointId().equals(clickedWaypoint.substring(0,Waypoint.waypointIdLength))){
                    waypoint = wp;
                }
            }
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

