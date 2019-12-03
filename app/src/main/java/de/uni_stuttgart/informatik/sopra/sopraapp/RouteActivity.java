package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;

public class RouteActivity extends AppCompatActivity {
    private static ArrayList<String> list = new ArrayList<>();
    private ListView selectedWaypointList;
    private ArrayAdapter<String> myArrayAdapter;
    private Button addWaypointRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        selectedWaypointList = findViewById(R.id.selectedWaypointList);
        addWaypointRef = findViewById(R.id.addWaypoint);
        myArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, list);

        addWaypointRef.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), WaypointListActivity.class);
            intent.putExtra("root","RouteActivity");
            startActivity(intent);
        });

        if(getIntent().getExtras()!=null) {
            Waypoint waypoint = (Waypoint) getIntent().getExtras().get("selectedWaypoint");
            list.add(waypoint.getWaypointName());
        }
       selectedWaypointList.setAdapter(myArrayAdapter);
    }
}
