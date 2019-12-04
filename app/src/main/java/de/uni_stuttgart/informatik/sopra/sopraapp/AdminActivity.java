package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    TextView tvAdminModusRef;
    Button btnRoutenRef;
    Button btnWaypointsRef;
    Button btnGuardsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        tvAdminModusRef= findViewById(R.id.tvAdminModus);
        btnRoutenRef = findViewById(R.id.btnRoutes);
        btnWaypointsRef = findViewById(R.id.btnWaypoints);
        btnGuardsRef = findViewById(R.id.btnGuard);

        btnWaypointsRef.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), WaypointActivity.class);
            startActivity(intent);
        });
        btnRoutenRef.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), RouteActivity.class);
            startActivity(intent);
        });
        btnGuardsRef.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), GuardActivity.class);
            startActivity(intent);
        });
    }
}
