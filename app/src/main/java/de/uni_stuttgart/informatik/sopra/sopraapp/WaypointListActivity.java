package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.time.Duration;
import java.util.ArrayList;

public class WaypointListActivity extends AppCompatActivity implements DurratoinDialog.DurationDialogListener {
    private Intent intent;
    private Duration duration;
    private Waypoint waypoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waypoint_list);
        ListView listView = findViewById(R.id.waypointList);

        ArrayList<String> waypointStringList = new ArrayList<>();
        //delete/////////////////////////////////////////////////////////////////
        new Waypoint("Eingang","123456","111111");
        new Waypoint("Ausgang","000000","222222");
        //delete/////////////////////////////////////////////////////////////////
        for(Waypoint waypoint : Waypoint.getWaypointList()){
            waypointStringList.add(waypoint.toString());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                waypointStringList);

        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {

            System.out.println(getIntent().getStringExtra("root"));
            waypoint =  Waypoint.getWaypointList().get(position);
            String string = parent.getItemAtPosition(position).toString();
            if(getIntent().getStringExtra("root").equals("WaypointActivity")) {
                intent = new Intent(view.getContext(), WaypointActivity.class);
                intent.putExtra("deleteWaypoint", string);
                startActivity(intent);
                finish();

            }else if(getIntent().getStringExtra("root").equals("RouteCreationActivity")){
                Route route = (Route) getIntent().getExtras().get("routeObj");
                intent = new Intent(view.getContext(), RouteCreationActivity.class);
                openDialog();
            }else{
                //intent extra should be one of the checked above
            }
        });

    }

    public void openDialog(){
        DurratoinDialog duration = new DurratoinDialog();
        duration.show(getSupportFragmentManager(),"duration dialog");
    }

    @Override
    public void applyText(String inputDuration) {
        duration = Duration.ofMinutes(Integer.parseInt(inputDuration));
        RouteWaypoint routeWaypoint = new RouteWaypoint(waypoint, duration);
        intent.putExtra("selectedWaypoint", routeWaypoint);
        startActivity(intent);
    }
}

