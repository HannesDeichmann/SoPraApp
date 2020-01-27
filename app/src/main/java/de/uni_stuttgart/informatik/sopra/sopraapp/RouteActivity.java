package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Activity to show the Route-List. There you can select a Route or create a new one.
 *
 * @author Arne Bartenbach 3392087
 * @version 22.01.2020
 */
public class RouteActivity extends AppCompatActivity {
    Button btnNewRoute;
    EditText etSearchRouteRef;
    DatabaseRoute databaseRoute;
    ListView listView;
    ArrayList<String> routeStringList;
    ArrayAdapter<String> dataAdapter;
    ArrayList<Route> allRoutes = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        allRoutes = databaseRoute.getAllRoutes();
        if(getIntent().hasExtra("schedule")){
            btnNewRoute.setVisibility(View.INVISIBLE);
        }else {
            btnNewRoute.setVisibility(View.VISIBLE);
        }

        routeStringList = new ArrayList<>();

        for (Route route : allRoutes) {
            routeStringList.add(route.toString());
        }

        dataAdapter = new ArrayAdapter<>(
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
        etSearchRouteRef = findViewById(R.id.etSearchRoute);
        allRoutes = databaseRoute.getAllRoutes();

        btnNewRoute.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RouteCreationActivity.class);
            intent.putExtra("newRoute", true);
            startActivity(intent);
        });

        etSearchRouteRef.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = etSearchRouteRef.getText().toString();
                routeStringList.clear();
                for(Route route: allRoutes){
                    if(route.toString().contains(searchText)){routeStringList.add(route.toString()); }
                }
                dataAdapter.notifyDataSetChanged();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) { }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if(getIntent().hasExtra("schedule")){
                Intent intent = new Intent(view.getContext(), ScheduleActivity.class);
                intent.putExtra("scheduleRoute", getRouteFromPosition(position));
                intent.putExtra("routeSelectedGuard", (Guard) getIntent().getExtras().get("guard"));
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(view.getContext(), RouteCreationActivity.class);
                intent.putExtra("editRoute", getRouteFromPosition(position));
                startActivity(intent);
            }
        });
    }
    private Route getRouteFromPosition(int position){
        String routeIdString = routeStringList.get(position).split(":")[0];
        return databaseRoute.getRouteById(Integer.valueOf(routeIdString));
    }
}
