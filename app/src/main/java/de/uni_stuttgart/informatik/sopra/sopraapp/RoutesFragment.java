package de.uni_stuttgart.informatik.sopra.sopraapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

public class RoutesFragment extends Fragment {

    private ListView lvRoutesRef;
    DatabasePatrol databasePatrol;

    public static RoutesFragment newInstance(Guard guard){
        RoutesFragment fragment = new RoutesFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("guard", guard);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routes, container,false);
        lvRoutesRef = view.findViewById(R.id.lvRoutes);


        Guard loggedInGuard = (Guard) getArguments().getSerializable("guard");
        ArrayList<GuardRoute> guardRouteList = loggedInGuard.getGuardRouteList();
        databasePatrol = new DatabasePatrol(getActivity());
        ArrayList<String> routeStringList = new ArrayList<>();


        for(GuardRoute guardRoute : guardRouteList){
            routeStringList.add(guardRoute.getRoute().getRouteName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                routeStringList);

        lvRoutesRef.setAdapter(dataAdapter);

        lvRoutesRef.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), PatrolActivity.class);
                GuardRoute selectedRoute= guardRouteList.get(position);
                intent.putExtra("selectedRoute", selectedRoute);
                intent.putExtra("loggedInGuard", loggedInGuard);
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                DrawingView.setDoneWaypoints(new ArrayList<Waypoint>());
                databasePatrol.addNewPatrol(";" + selectedRoute.getRoute().getRouteName()+ ";" +
                        loggedInGuard.getForename() + ";" + selectedRoute.getTime() +";" + sdf.format(date) + ";" );
                startActivity(intent);
            }
        });
        return view;

    }
}
