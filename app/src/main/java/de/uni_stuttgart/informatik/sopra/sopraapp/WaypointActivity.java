package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WaypointActivity extends AppCompatActivity {

    Button btnEditWaypointRef;
    Button btnAcceptWaypointRef;
    Button btnDeleteWaypointRef;
    Button btnAddLocationRef;

    EditText etWaypointNameRef;
    EditText etWaypointIdRef;
    TextView tvWaypointPositionRef;
    EditText etWaypointNoteRef;

    DatabaseWaypoint databaseWaypoint;
    DatabaseRoute databaseRoute;
    public static boolean newWaypoint = true;
    public static String waypointLocation = "";

    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().hasExtra("wpLocation")){
            setTextFields((Waypoint) getIntent().getExtras().get("wpLocation"));

        }
    }

    private void clearTextFields(){
        etWaypointNameRef.setText("");
        etWaypointIdRef.setText("");
        tvWaypointPositionRef.setText("");
        etWaypointNoteRef.setText("");
    }

    private Waypoint getEditedWaypoint() {
        return databaseWaypoint.getWaypointById(getIntent().getIntExtra("editedWaypointId", 0));
    }

    private void checkEditNewWaypoint() {
        if (newWaypoint) {
            btnDeleteWaypointRef.setVisibility(View.INVISIBLE);
        } else {
            btnDeleteWaypointRef.setVisibility(View.VISIBLE);
            setTextFields(getEditedWaypoint());
        }
    }

    private void setTextFields(Waypoint waypoint){
        etWaypointNameRef.setText(waypoint.getWaypointName());
        etWaypointIdRef.setText(waypoint.getWaypointId());
        tvWaypointPositionRef.setText(waypoint.getWaypointPosition());
        etWaypointNoteRef.setText(waypoint.getWaypointNote());
    }

    private Waypoint addWaypointInfos(){
        Waypoint createdWaypoint = new Waypoint();
        createdWaypoint.setWaypointName(etWaypointNameRef.getText().toString());
        createdWaypoint.setWaypointId(etWaypointIdRef.getText().toString());
        createdWaypoint.setWaypointPosition(tvWaypointPositionRef.getText().toString());
        createdWaypoint.setWaypointNote(etWaypointNoteRef.getText().toString());
        return createdWaypoint;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waypoint);

        btnEditWaypointRef = findViewById(R.id.btnEditWaypoint);
        btnAcceptWaypointRef = findViewById(R.id.btnAcceptWaypoint);
        btnDeleteWaypointRef = findViewById(R.id.btnDeleteWaypoint);
        btnAddLocationRef = findViewById(R.id.btnAddLocation);

        etWaypointNameRef = findViewById(R.id.etWaypointName);
        etWaypointIdRef = findViewById(R.id.etWaypointId);
        tvWaypointPositionRef = findViewById(R.id.tvLocationDisplay);
        etWaypointNoteRef = findViewById(R.id.etWaypointNote);

        databaseWaypoint = new DatabaseWaypoint(this);
        databaseRoute = new DatabaseRoute(this);

        if(!getIntent().hasExtra("wpLocation")){
            checkEditNewWaypoint();
        }

        btnAcceptWaypointRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etWaypointIdRef.getText().length() == Waypoint.waypointIdLength) {
                    Waypoint createdWaypoint = addWaypointInfos();
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

        btnAddLocationRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((etWaypointIdRef.getText().length() == Waypoint.waypointIdLength )/*&&
                        (DrawingView.getBitmap() != null)*/){
                    Intent intent = new Intent(v.getContext(), DrawActivity.class);
                    Waypoint waypoint = addWaypointInfos();
                    intent.putExtra("waypoint",waypoint);
                    startActivity(intent);
                    finish();
                    onResume();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),"Id must have 6 chars", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
