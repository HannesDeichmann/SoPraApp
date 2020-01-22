package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class RouteFragment extends Fragment {


    TextView tvRouteNameRef;
    Activity rootActivity;
    private ListView lvCompleteRouteRef;
    private Route route;

    public static RouteFragment newInstance(Route route){
        RouteFragment fragment = new RouteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("route", route);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        route = (Route) getArguments().getSerializable("route");
        View view = inflater.inflate(R.layout.fragment_route, container,false);
        tvRouteNameRef = view.findViewById(R.id.tvRouteName);
        lvCompleteRouteRef = view.findViewById(R.id.lvCompleteRoute);
        tvRouteNameRef.setText(route.getRouteName());
        /**
         * Creating the listView for the whole route
         */
        ArrayList<RouteWaypoint> waypointList = route.getWaypoints();

        ArrayList<String> waypointsStringList = new ArrayList<>();


        for (RouteWaypoint routeWaypoint : waypointList) {
            int durationInt = (int) routeWaypoint.getDuration().toMinutes();
            String duration = Integer.toString(durationInt);
            waypointsStringList.add(routeWaypoint.getWaypoint().getWaypointName()
                    + " : Time to reach this waypoint: " + duration + "min");

        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,
                waypointsStringList);

        lvCompleteRouteRef.setAdapter(dataAdapter);
        lvCompleteRouteRef.setDividerHeight(5);
        return view;

    }
}
