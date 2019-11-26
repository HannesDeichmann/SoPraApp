package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class WaypointActivity extends AppCompatActivity {

    Button btnShowWaypointRef;
    Button btnCreateWaypointRef;
    Button btnDeleteWaypointRef;
    EditText etWaypointNameRef;
    EditText etWaypointIdRef;
    EditText etWaypointNfcTagRef;
    Waypoint editedWaypoint;

    private void clearTextFields(){
        etWaypointNameRef.setText("");
        etWaypointIdRef.setText("");
        etWaypointNfcTagRef.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waypoint);

        btnShowWaypointRef = findViewById(R.id.btnShowWaypoint);
        btnCreateWaypointRef = findViewById(R.id.btnCreateWaypoint);
        btnDeleteWaypointRef = findViewById(R.id.btnDeleteWaypoint);
        etWaypointNameRef = findViewById(R.id.etWaypointName);
        etWaypointIdRef = findViewById(R.id.etWaypointId);
        etWaypointNfcTagRef = findViewById(R.id.etWaypointNfcTag);

        String deleteWaypointString = "";
        deleteWaypointString += getIntent().getStringExtra("deleteWaypoint");
        editedWaypoint = null;
        if (deleteWaypointString.contains(Waypoint.waypointIdIndicator)) {
            int indexOfId = deleteWaypointString.indexOf(Waypoint.waypointIdIndicator);
            deleteWaypointString = deleteWaypointString.substring(indexOfId, indexOfId + Waypoint.waypointIdIndicator.length()
                    + Waypoint.waypointIdLength);

            for (Waypoint waypoint : Waypoint.getWaypointList()) {
                if (waypoint.toString().contains(deleteWaypointString)) {
                    editedWaypoint = waypoint;
                    etWaypointNameRef.setText(waypoint.getWaypointName());
                    etWaypointIdRef.setText(waypoint.getWaypointId());
                    etWaypointNfcTagRef.setText(waypoint.getWaypointNfcTag());
                }
            }
        } else {
            clearTextFields();
        }

        btnCreateWaypointRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etWaypointIdRef.getText().length() == 6) {
                    Waypoint createdWaypoint;
                    if (editedWaypoint == null) {
                        createdWaypoint = new Waypoint();
                    } else {
                        createdWaypoint = editedWaypoint;
                    }
                    createdWaypoint.setWaypointName(etWaypointNameRef.getText().toString());
                    createdWaypoint.setWaypointId(etWaypointIdRef.getText().toString());
                    createdWaypoint.setWaypointNfcTag(etWaypointNfcTagRef.getText().toString());
                    clearTextFields();

                } else {
                    //TODO Alertfenster
                    etWaypointIdRef.setText("WaypointId needs 6 chars");
                }
            }
        });

        btnDeleteWaypointRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editedWaypoint != null && Waypoint.getWaypointList().contains(editedWaypoint)) {
                    Waypoint.getWaypointList().remove(editedWaypoint);
                    editedWaypoint = null;
                    clearTextFields();
                }else {
                    //TODO Alertfenster
                    clearTextFields();
                    etWaypointNameRef.setText("There is no Waypoint to delete");
                }
            }
        });

        btnShowWaypointRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WaypointListActivity.class);
                startActivity(intent);
            }
        });
    }
}
