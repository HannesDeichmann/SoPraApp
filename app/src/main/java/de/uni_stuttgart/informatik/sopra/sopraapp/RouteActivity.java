package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class RouteActivity extends AppCompatActivity {
    Button btnNewRoute;
    ListView listView;
    ArrayList<String> routeList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        btnNewRoute = findViewById(R.id.newRoute);
        listView = findViewById(R.id.routeList);
        btnNewRoute.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RouteCreationActivity.class);
            startActivity(intent);
        });
    }
}
