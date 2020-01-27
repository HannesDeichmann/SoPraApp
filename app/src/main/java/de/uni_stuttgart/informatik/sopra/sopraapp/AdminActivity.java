package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


/**
 * Activity to navigate through the Admin Mode.
 *
 * @author Arne Bartenbach 3392087
 * @version 22.01.2020
 */
public class AdminActivity extends AppCompatActivity {
    TextView tvAdminModusRef;
    Button btnRoutenRef;
    Button btnWaypointsRef;
    Button btnGuardsRef;
    Button btnSchedule;
    Button btnProtocol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        tvAdminModusRef= findViewById(R.id.tvAdminModus);
        btnRoutenRef = findViewById(R.id.btnRoutes);
        btnWaypointsRef = findViewById(R.id.btnWaypoints);
        btnGuardsRef = findViewById(R.id.btnGuard);
        btnSchedule = findViewById(R.id.btnSchedule);
        btnProtocol = findViewById(R.id.btnProtocol);

        btnWaypointsRef.setOnClickListener(view -> {
            WaypointActivity.newWaypoint = true;
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
        btnSchedule.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ScheduleActivity.class);
            startActivity(intent);
        });
        btnProtocol.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ProtocolActivity.class);
            startActivity(intent);
        });
    }
}
