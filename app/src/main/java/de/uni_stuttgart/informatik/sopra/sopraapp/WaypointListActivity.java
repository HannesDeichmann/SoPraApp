package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class WaypointListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waypoint_list);
        ListView listView = findViewById(R.id.waypointList);

        ArrayList<String> waypointStringList = new ArrayList<>();
        //delete/////////////////////////////////////////////////////////////////
        new Waypoint("1","1","1");
        new Waypoint("2","2","2");
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
            Intent intent = null;
            System.out.println(getIntent().getStringExtra("root"));

            if(getIntent().getStringExtra("root").equals("WaypointActivity")) {
                intent = new Intent(view.getContext(), WaypointActivity.class);
            }else if(getIntent().getStringExtra("root").equals("RouteActivity")){
                intent = new Intent(view.getContext(), RouteActivity.class);
            }else{
                //intent extra should be one of the checked above
            }
            Waypoint waypoint =  Waypoint.getWaypointList().get(position);
            String string = parent.getItemAtPosition(position).toString();
            intent.putExtra("deleteWaypoint", string);
            intent.putExtra("selectedWaypoint", waypoint);
            startActivity(intent);
        });

    }
}

