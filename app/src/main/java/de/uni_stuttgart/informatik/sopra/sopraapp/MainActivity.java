package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView tvLoginRef;
    Button btnLoginRef;
    DatabaseGuard databaseGuard;
    DatabaseWaypoint databaseWaypoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        databaseGuard = new DatabaseGuard(this);
        databaseWaypoint = new DatabaseWaypoint(this);

        //TODO BUG: Database darf nicht leer sein
        if(databaseGuard.getGuardCount() == 0) {
            databaseGuard.addGuard(new Guard("Damit", "Database", "!= empty"));
        }
        if(databaseWaypoint.getWaypointCount() == 0) {
            databaseWaypoint.addWaypoint(new Waypoint("FirstWaypoint", "123456", "Tag", "Note"));
        }

        tvLoginRef = (TextView) findViewById(R.id.tvLogin);
        btnLoginRef = (Button) findViewById(R.id.btnLogin);

        btnLoginRef.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AdminActivity.class);
                startActivity(intent);
            }
        });
    }
}
