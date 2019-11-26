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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        ListView selectedWaypointList = findViewById(R.id.selectedWaypointList);
        ArrayList<String> list = new ArrayList<String>();
        Button addWaypointRef = findViewById(R.id.addWaypoint);
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list);

        addWaypointRef.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), RouteWaypointListActivity.class);
                startActivity(intent);
                list.add(intent.getStringExtra("WaypointName"));
                selectedWaypointList.setAdapter(myArrayAdapter);

            }

        });
        selectedWaypointList.setAdapter(myArrayAdapter);
    }
}
