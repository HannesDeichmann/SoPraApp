package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RouteActivity extends AppCompatActivity {
    Button btnNewRoute;
    DatabaseRoute databaseRoute;
    ListView listView;
    ArrayList<String> routeStringList;

    @Override
    protected void onResume() {
        super.onResume();

        if(getIntent().hasExtra("schedule")){
            btnNewRoute.setVisibility(View.INVISIBLE);
        }else {
            btnNewRoute.setVisibility(View.VISIBLE);
        }

        routeStringList = new ArrayList<>();

        for (Route route : databaseRoute.getAllRoutes()) {
            routeStringList.add(route.toString());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                routeStringList);

        listView.setAdapter(dataAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        btnNewRoute = findViewById(R.id.newRoute);
        databaseRoute = new DatabaseRoute(this);
        listView = findViewById(R.id.routeList);

        btnNewRoute.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RouteCreationActivity.class);
            intent.putExtra("newRoute", true);
            startActivity(intent);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if(getIntent().hasExtra("schedule")){
                Intent intent = new Intent(view.getContext(), ScheduleActivity.class);
                intent.putExtra("scheduleRoute", databaseRoute.getAllRoutes().get(position));
                intent.putExtra("routeSelectedGuard", (Guard) getIntent().getExtras().get("guard"));
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(view.getContext(), RouteCreationActivity.class);
                intent.putExtra("editRoute", databaseRoute.getAllRoutes().get(position));
                startActivity(intent);
            }
        });
    }
}
