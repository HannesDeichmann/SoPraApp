package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

import static de.uni_stuttgart.informatik.sopra.sopraapp.DbContract.DIVIDESTRING;

public class ScheduleActivity extends AppCompatActivity {
    Button btnSelectGuard;
    Button btnSelectRoute;
    static EditText etSetTime;
    Button btnSave;
    static Guard selectedGuard;
    static Route selectedRoute;
    //ListView listView;
    static TextView tvSelectedGuard;
    static TextView tvSelectedRoute;
    static ArrayList<String> routeStringList;
    static DatabaseGuard databaseGuard;
    static DatabaseRoute databaseRoute;
    //ArrayAdapter<String> dataAdapter;

    RecyclerView listView;
    RecyclerView.Adapter dataAdapter;
    RecyclerView.LayoutManager rvManagerRef;
    /*
    <ListView
        android:id="@+id/routeList"
        android:layout_width="300dp"
        android:layout_height="200dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/tvSelectedGuard" />
     */

    static void addRoutesFromDbToEmptyGuard(Guard guard){
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
        //listView = findViewById(R.id.routeList);
        tvSelectedRoute = findViewById(R.id.tvSelectedRoute);
        databaseGuard = new DatabaseGuard(this);
        databaseRoute = new DatabaseRoute(this);
        ScheduleAdapter.databaseGuard = databaseGuard;
        ScheduleAdapter.databaseRoute = databaseRoute;
        routeStringList=new ArrayList<>();
        //dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,routeStringList);
        //listView.setAdapter(dataAdapter);
        // dataAdapter.notifyDataSetChanged();

        listView = findViewById(R.id.routeList);
        rvManagerRef = new LinearLayoutManager(this);
        listView.setLayoutManager(rvManagerRef);
        dataAdapter = new ScheduleAdapter(routeStringList);
        listView.setAdapter(dataAdapter);


        btnSelectGuard.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), GuardListActivity.class);
            intent.putExtra("Schedule","");
            startActivity(intent);
            finish();
        });

        btnSelectRoute.setOnClickListener(view -> {
            if (selectedGuard != null && !tvSelectedGuard.getText().equals("")) {
                Intent intent = new Intent(view.getContext(), RouteActivity.class);
                intent.putExtra("schedule", selectedRoute);
                intent.putExtra("guard", selectedGuard);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(),"First select a guard",Toast.LENGTH_SHORT).show();
            }
        });

        btnSave.setOnClickListener(v -> {
            if (selectedGuard != null && selectedRoute != null && !etSetTime.getText().toString().equals("")) {
                if(checkForDublicate()){
                    Toast.makeText(getApplicationContext(),"It exists allready",Toast.LENGTH_SHORT).show();
                }else{
                    selectedGuard.setGuardRouteList(new ArrayList<GuardRoute>());
                    addRoutesFromDbToEmptyGuard(selectedGuard);
                    selectedGuard.addRoute(new GuardRoute(selectedRoute, etSetTime.getText().toString()));
                    etSetTime.setText("");
                    databaseGuard.addGuardRoute(selectedGuard);
                    onResume();
                }
            } else {
                Toast.makeText(getApplicationContext(),"Information is missing",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        addAllGuardRoutesToList();
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
            tvSelectedRoute.setText(selectedRoute.getRouteName());
        }
        if (selectedGuard != null) {
            Guard guard = databaseGuard.getGuardWithRoutes(selectedGuard);
            ArrayList<String> idList = guard.getRouteTimeString();
            ArrayList<String> timeList = guard.getRouteIdString();
            ArrayList<GuardRoute> guardRouteList = new ArrayList<>();
            if (idList.size() == timeList.size()) {
                for (int i = 0; i < idList.size(); i++) {
                    Route route = databaseRoute.getRouteById(Integer.parseInt(idList.get(i)));
                    GuardRoute guardRoute = new GuardRoute(route, timeList.get(i));
                    guardRouteList.add(guardRoute);
                }
            }
            selectedGuard.setGuardRouteList(guardRouteList);
        }
        dataAdapter.notifyDataSetChanged();
    }
    private void addAllGuardRoutesToList(){
        routeStringList.clear();
        for(Guard g: databaseGuard.getAllGuards()) {
            Guard guard = databaseGuard.getGuardWithRoutes(g);
            ArrayList<String> idList = guard.getRouteTimeString();
            ArrayList<String> timeList = guard.getRouteIdString();
            if (idList.size() == timeList.size()) {
                for (int i = 0; i < idList.size(); i++) {
                    Route route = databaseRoute.getRouteById(Integer.parseInt(idList.get(i)));
                    routeStringList.add(guard.toString() + ": " + (new GuardRoute(route, timeList.get(i))).toString());
                }
            }
        }
        dataAdapter.notifyDataSetChanged();
    }
    private boolean checkForDublicate(){
        for(String string:routeStringList) {
            String[] splitted = string.split(":");
            if(selectedRoute.getRouteId().equals(splitted[3].trim())
                && selectedGuard.getUserId().equals(splitted[0].trim())
                && etSetTime.getText().toString().equals(splitted[4].split(DIVIDESTRING)[1])) {
              return true;
            }
        }
        return false;
    }
}
