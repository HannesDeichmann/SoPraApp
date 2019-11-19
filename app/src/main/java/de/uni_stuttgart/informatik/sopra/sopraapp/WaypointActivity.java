package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WaypointActivity extends AppCompatActivity {

    Button btnCreateRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waypoint);

        btnCreateRef = findViewById(R.id.btnCreate);

        btnCreateRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaypointFragment waypointFragment = new WaypointFragment();
                waypointFragment.show(getSupportFragmentManager(), WaypointFragment.class.getSimpleName());
            }
        });
    }
}
