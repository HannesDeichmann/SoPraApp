package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class WaypointListActivity extends AppCompatActivity {
    Button btnCancelWaypoint;
    ListView listView;
    DatabaseWaypoint databaseWaypoint;
    ArrayList<String> waypointStringList;

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

        for(Waypoint waypoint : databaseWaypoint.getAllWaypoints()){
            waypointStringList.add(waypoint.toString());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                waypointStringList);

        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(view.getContext(), WaypointActivity.class);
                WaypointActivity.newWaypoint = false;
                //EIGENTLICH: Item.at(position) oder so ...
                int waypointId = Integer.parseInt(databaseWaypoint.getAllWaypoints().get(position).getWaypointId());
                intent.putExtra("editedWaypointId", waypointId);
                startActivity(intent);
            }
        });

    }
}

