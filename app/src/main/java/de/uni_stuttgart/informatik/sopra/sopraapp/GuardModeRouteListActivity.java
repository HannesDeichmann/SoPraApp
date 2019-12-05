package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GuardModeRouteListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_mode_route_list);
        ListView listView = findViewById(R.id.guardRouteList);

        Guard loggedInGuard = (Guard) getIntent().getExtras().get("loggedInGuard");
        ArrayList<GuardRoute> guardRouteList = loggedInGuard.getRoutes();

        ArrayList<String> routeStringList = new ArrayList<>();


        for(GuardRoute guardRoute : guardRouteList){
                routeStringList.add(guardRoute.getRoute().getRouteName());

        }
        /*GuardRoute guardRoute = guardRouteList.get(0);*/
        /*routeStringList.add(guardRoute.getRoute().getRouteName());*/


        routeStringList.add(loggedInGuard.getForename());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                routeStringList);

        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(view.getContext(), PatrolActivity.class);
                GuardRoute selectedRoute= guardRouteList.get(position);
                intent.putExtra("selectedRoute", selectedRoute);
                startActivity(intent);

            }
        });
    }
}
