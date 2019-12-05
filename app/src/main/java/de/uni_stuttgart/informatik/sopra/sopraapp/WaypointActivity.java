package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WaypointActivity extends AppCompatActivity {

    Button btnEditWaypointRef;
    Button btnAcceptWaypointRef;
    Button btnDeleteWaypointRef;
    EditText etWaypointNameRef;
    EditText etWaypointIdRef;
    EditText etWaypointNfcTagRef;
    Waypoint editedWaypoint;
    DatabaseWaypoint databaseWaypoint;
    public static boolean newWaypoint = true;

    private void clearTextFields(){
        etWaypointNameRef.setText("");
        etWaypointIdRef.setText("");
        etWaypointNfcTagRef.setText("");
    }

    private Waypoint getEditedWaypoint() {
        return databaseWaypoint.getWaypointById(getIntent().getIntExtra("editedWaypointId", 0));
    }

    private void checkEditNewWaypoint() {
        if (newWaypoint == true) {
            btnDeleteWaypointRef.setVisibility(View.INVISIBLE);
        } else {
            btnDeleteWaypointRef.setVisibility(View.VISIBLE);
            etWaypointNameRef.setText(getEditedWaypoint().getWaypointName());
            etWaypointIdRef.setText(getEditedWaypoint().getWaypointId());
            etWaypointNfcTagRef.setText(getEditedWaypoint().getWaypointNfcTag());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waypoint);

        btnEditWaypointRef = findViewById(R.id.btnEditWaypoint);
        btnAcceptWaypointRef = findViewById(R.id.btnAcceptWaypoint);
        btnDeleteWaypointRef = findViewById(R.id.btnDeleteWaypoint);
        etWaypointNameRef = findViewById(R.id.etWaypointName);
        etWaypointIdRef = findViewById(R.id.etWaypointId);
        etWaypointNfcTagRef = findViewById(R.id.etWaypointNfcTag);

        databaseWaypoint = new DatabaseWaypoint(this);

        btnAcceptWaypointRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etWaypointIdRef.getText().length() == 6) {
                    Waypoint createdWaypoint = new Waypoint();
                    createdWaypoint.setWaypointName(etWaypointNameRef.getText().toString());
                    createdWaypoint.setWaypointId(etWaypointIdRef.getText().toString());
                    createdWaypoint.setWaypointNfcTag(etWaypointNfcTagRef.getText().toString());
                    databaseWaypoint.addWaypoint(createdWaypoint);
                    newWaypoint = true;
                    Intent intent = new Intent(view.getContext(), WaypointActivity.class);
                    startActivity(intent);
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
                databaseWaypoint.deleteWaypoint(getEditedWaypoint());
                newWaypoint = true;
                Intent intent = new Intent(view.getContext(), WaypointActivity.class);
                startActivity(intent);
                clearTextFields();
            }
        });

        btnEditWaypointRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WaypointListActivity.class);
                intent.putExtra("root", "WaypointActivity");
                startActivity(intent);
            }
        });

        btnEditWaypointRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WaypointListActivity.class);
                startActivity(intent);
            }
        });
    }
}
