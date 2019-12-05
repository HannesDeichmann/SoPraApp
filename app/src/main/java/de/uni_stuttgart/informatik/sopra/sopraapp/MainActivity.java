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

        //TODO BUG: Database darf nicht leer sein
        databaseGuard = new DatabaseGuard(this);
        databaseWaypoint = new DatabaseWaypoint(this);

        databaseGuard.addGuard(new Guard("Erster","Wächter","Password"));
        databaseWaypoint.addWaypoint(new Waypoint("FirstWaypoint","1111111", "Tag"));


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
