package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity {
    Button btnSelectGuard;
    Button btnSelectRoute;
    EditText etSetTime;
    Button btnSave;
    Guard selectedGuard;
    Route selectedRoute;
    ListView listView;
    TextView tvSelectedGuard;
    TextView tvSelectedRoute;
    ArrayList<String> routeStringList;
    DatabaseGuard databaseGuard;
    DatabaseRoute databaseRoute;


    private void addRoutesFromDbToEmptyGuard(Guard guard){
        ArrayList<String> timeList = databaseGuard.getGuardWithRoutes(guard).getRouteIdString();
        ArrayList<String> idList = databaseGuard.getGuardWithRoutes(guard).getRouteTimeString();
        if (idList.size() == timeList.size()) {
            for (int i = 0; i < idList.size(); i++) {
                Route route = databaseRoute.getRouteById(Integer.parseInt(idList.get(i)));
                GuardRoute guardRoute = new GuardRoute(route, timeList.get(i));
                guard.addRoute(guardRoute);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        btnSelectGuard = findViewById(R.id.selectGuard);
        btnSelectRoute = findViewById(R.id.selectRoute);
        etSetTime = findViewById(R.id.etSetStartTime);
        btnSave = findViewById(R.id.saveScheduleItem);
        tvSelectedGuard = findViewById(R.id.tvSelectedGuard);
        listView = findViewById(R.id.routeList);
        tvSelectedRoute = findViewById(R.id.tvSelectedRoute);
        databaseGuard = new DatabaseGuard(this);
        databaseRoute = new DatabaseRoute(this);

        btnSelectGuard.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ScheduleGuardListActivity.class);
            startActivity(intent);
            finish();
        });
        btnSelectRoute.setOnClickListener(view -> {
            if (selectedGuard != null) {
                Intent intent = new Intent(view.getContext(), RouteActivity.class);
                intent.putExtra("schedule", selectedRoute);
                if (!tvSelectedGuard.getText().equals("")) {
                    if (selectedGuard != null) {
                        intent.putExtra("guard", selectedGuard);
                    } else {
                        //Exception werfen
                    }
                }
                startActivity(intent);
                finish();
            } else {
                //Alertfenster
                tvSelectedGuard.setText("Select Guard First");
            }
        });
        btnSave.setOnClickListener(v -> {
            if (selectedGuard != null && selectedRoute != null && !etSetTime.getText().toString().equals("")) {
                //System.out.println(selectedGuard.getGuardRouteList().size());
                selectedGuard.setGuardRouteList(new ArrayList<GuardRoute>());
                addRoutesFromDbToEmptyGuard(selectedGuard);
                selectedGuard.addRoute(new GuardRoute(selectedRoute, etSetTime.getText().toString()));

                etSetTime.setText("");
                routeStringList = new ArrayList<>();
                databaseGuard.addGuardRoute(selectedGuard);
                Guard guard = databaseGuard.getGuardWithRoutes(selectedGuard);
                ArrayList<String> idList = guard.getRouteTimeString();
                ArrayList<String> timeList = guard.getRouteIdString();

                if (idList.size() == timeList.size()) {
                    for (int i = 0; i < idList.size(); i++) {
                        Route route = databaseRoute.getRouteById(Integer.parseInt(idList.get(i)));
                        routeStringList.add((new GuardRoute(route, timeList.get(i))).toString());
                    }
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        routeStringList);
                listView.setAdapter(dataAdapter);

            } else {
                //TODO alert window
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().hasExtra("selectedGuard")) {
            selectedGuard = (Guard) getIntent().getExtras().get("selectedGuard");
            tvSelectedGuard.setText(selectedGuard.getUserId() + ": " + selectedGuard.getForename());
        }
        if (getIntent().hasExtra("routeSelectedGuard")) {
            selectedGuard = (Guard) getIntent().getExtras().get("routeSelectedGuard");
            tvSelectedGuard.setText(selectedGuard.getUserId() + ": " + selectedGuard.getForename());
        }
        if (getIntent().hasExtra("scheduleRoute")) {
            selectedRoute = (Route) getIntent().getExtras().get("scheduleRoute");
            tvSelectedRoute.setText(selectedRoute.toString());
        }
        if (selectedGuard != null) {
            Guard guard = databaseGuard.getGuardWithRoutes(selectedGuard);
            ArrayList<String> idList = guard.getRouteTimeString();
            ArrayList<String> timeList = guard.getRouteIdString();
            routeStringList = new ArrayList<>();
            routeStringList.clear();
            if (idList.size() == timeList.size()) {
                for (int i = 0; i < idList.size(); i++) {
                    Route route = databaseRoute.getRouteById(Integer.parseInt(idList.get(i)));
                    routeStringList.add((new GuardRoute(route, timeList.get(i))).toString());
                }
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    routeStringList);
            listView.setAdapter(dataAdapter);
        }
    }
}
