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
    Button btnRefresh;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    DatabaseRoute databaseRoute;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        btnNewRoute = findViewById(R.id.newRoute);
        btnRefresh = findViewById(R.id.refresh);
        databaseRoute = new DatabaseRoute(this);
        listView = findViewById(R.id.routeList);
        /*recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);

        adapter = new RouteAdapter(databaseRoute);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        inknopf:
        adapter.notifyItemInserted(0);
        layoutManager.scrollToPosition(0);
        adapter.notifyDataSetChanged();
        */
        ArrayList<String> routeStringList = new ArrayList<>();
        for(Route route : databaseRoute.getAllRoutes()){
            routeStringList.add(route.toString());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                routeStringList);

        listView.setAdapter(dataAdapter);
        btnRefresh.setOnClickListener(v -> {
            routeStringList.clear();
            ArrayList<String> routeStringList2 = new ArrayList<>();
            for(Route route : databaseRoute.getAllRoutes()){
                routeStringList.add(route.toString());
            }

            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    routeStringList);

            listView.setAdapter(dataAdapter);
        });

        btnNewRoute.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RouteCreationActivity.class);
            intent.putExtra("newRoute",true);
            startActivity(intent);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(view.getContext(), RouteCreationActivity.class);
            intent.putExtra("editRoute",databaseRoute.getAllRoutes().get(position));
            startActivity(intent);
        });
    }
}
