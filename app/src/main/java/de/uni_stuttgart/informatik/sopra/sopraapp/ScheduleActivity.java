package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.PublicKey;

public class ScheduleActivity extends AppCompatActivity {
    Button btnSelectGuard;
    Button btnSelectRoute;
    TextView tvSetTime;
    Button btnSave;
    Guard selectedGuard;
    Route selectedRoute;
    private static int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        btnSelectGuard = findViewById(R.id.selectGuard);
        btnSelectRoute = findViewById(R.id.selectRoute);
        tvSetTime = findViewById(R.id.setStartTime);
        btnSave = findViewById(R.id.saveScheduleItem);
        btnSelectGuard.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ScheduleGuardListActivity.class);
            startActivity(intent);
            finish();
        });
        /*btnSelectRoute.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), RouteListActivity.class);
            intent.putExtra("route", selectedRoute);
            startActivity(intent);
        });*/
        btnSave.setOnClickListener(v -> {
            //TODO add alert if no guard oder no route or no time is selected
            selectedGuard.addRoute(new GuardRoute(selectedRoute, tvSetTime.getText().toString()));
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (i > 0&&getIntent().getExtras()!=null) {
            if (getIntent().getExtras().get("selectedGuard") != null) {
                selectedGuard = (Guard) getIntent().getExtras().get("selectedGuard");
            }
            if (getIntent().getExtras().get("selectedRoute") != null) {
                selectedRoute = (Route) getIntent().getExtras().get("selectedRoute");
            }
        } else {
            i++;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        i = 0;
    }
}
