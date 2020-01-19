package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WaypointActivity extends AppCompatActivity {

    Button btnEditWaypointRef;
    Button btnAcceptWaypointRef;
    Button btnDeleteWaypointRef;

    EditText etWaypointNameRef;
    EditText etWaypointIdRef;
    EditText etWaypointPositionRef;
    EditText etWaypointNoteRef;

    DatabaseWaypoint databaseWaypoint;
    DatabaseRoute databaseRoute;
    public static boolean newWaypoint = true;

    private void clearTextFields(){
        etWaypointNameRef.setText("");
        etWaypointIdRef.setText("");
        etWaypointPositionRef.setText("");
        etWaypointNoteRef.setText("");
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
            etWaypointPositionRef.setText(getEditedWaypoint().getWaypointPosition());
            etWaypointNoteRef.setText(getEditedWaypoint().getWaypointNote());
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
        etWaypointPositionRef = findViewById(R.id.etWaypointPosition);
        etWaypointNoteRef = findViewById(R.id.etWaypointNote);

        databaseWaypoint = new DatabaseWaypoint(this);
        databaseRoute = new DatabaseRoute(this);

        checkEditNewWaypoint();

        btnAcceptWaypointRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etWaypointIdRef.getText().length() == Waypoint.waypointIdLength) {
                    Waypoint createdWaypoint = new Waypoint();
                    createdWaypoint.setWaypointName(etWaypointNameRef.getText().toString());
                    createdWaypoint.setWaypointId(etWaypointIdRef.getText().toString());
                    createdWaypoint.setWaypointPosition(etWaypointPositionRef.getText().toString());
                    createdWaypoint.setWaypointNote(etWaypointNoteRef.getText().toString());
                    if(newWaypoint)databaseWaypoint.addWaypoint(createdWaypoint);
                    else{
                        databaseWaypoint.deleteWaypoint(createdWaypoint);
                        databaseWaypoint.addWaypoint(createdWaypoint);
                    }
                    newWaypoint = true;

                    Intent intent = new Intent(view.getContext(), WaypointActivity.class);
                    startActivity(intent);
                    finish();
                   // clearTextFields();
                } else {
                    //TODO Alertfenster
                    //etWaypointIdRef.setText("WaypointId needs " + Waypoint.waypointIdLength +" chars");
                    Toast toast = Toast.makeText(getApplicationContext(), "ID needs 6 chars", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        //wp needs to be deleted out of the routes that use the wp
        btnDeleteWaypointRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Route route : databaseRoute.getAllRoutes()){
                    for (int i = 0; i < route.getWaypointStrings().size(); i++){
                        int wpId = Integer.parseInt(route.getWaypointStrings().get(i).getUserId());
                        if(Integer.parseInt(getEditedWaypoint().getWaypointId()) == wpId){
                            route.getWaypointStrings().remove(i);

                            databaseRoute.deleteRoute(route);
                            databaseRoute.addRoute(route);
                        }
                    }
                }
                databaseWaypoint.deleteWaypoint(getEditedWaypoint());
                newWaypoint = true;
                Intent intent = new Intent(view.getContext(), WaypointActivity.class);
                startActivity(intent);
                finish();
                }
        });

        btnEditWaypointRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WaypointListActivity.class);
                intent.putExtra("root", "WaypointActivity");
                startActivity(intent);
                finish();
            }
        });
    }
}
