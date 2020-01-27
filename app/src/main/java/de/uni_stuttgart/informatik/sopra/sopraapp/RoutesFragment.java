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
    private Duration duration1;
    private Duration duration2;
    private Duration duration3;

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
        ///////////////////////////////////////////////////
        Route route = new Route();
        route.setRouteName("Route um die Ecke The One (HartCode)");
        route.setRouteId("123123");
        Route route2 = new Route();
        route2.setRouteName("Route um die Ecke2/3 (HartCode)");
        Waypoint DieEckeHinterDemDönerladen= new Waypoint("Die Ecke Hinter Dem Doenerladen(HartCode)", "123456", "Tag1", "Bitte schnell laufen");
        Waypoint DieEckeVorDemDönerladen= new Waypoint("Die Ecke Vor Dem Doenerladen(HartCode)", "223456", "Tag2", "Noch schneller bitte");
        Waypoint DieEckeHinterDemDönerladenTheRevenge= new Waypoint("Die Ecke Hinter Dem Doenerladen The Revenge(HartCode)", "323456", "Tag3", "Und nicht stolpern");
        String minutes1 = "1";
        String minutes2 = "5";
        String minutes3 = "3";
        duration1 = Duration.ofMinutes(Integer.parseInt(minutes1));
        duration2 = Duration.ofMinutes(Integer.parseInt(minutes2));
        duration3 = Duration.ofMinutes(Integer.parseInt(minutes3));
        RouteWaypoint routeWaypoint1= new RouteWaypoint(DieEckeHinterDemDönerladen, duration1);
        RouteWaypoint routeWaypoint2= new RouteWaypoint(DieEckeVorDemDönerladen, duration2);
        RouteWaypoint routeWaypoint3= new RouteWaypoint(DieEckeHinterDemDönerladenTheRevenge, duration3);
        route.addWaypoint(routeWaypoint1);
        route.addWaypoint(routeWaypoint2);
        route.addWaypoint(routeWaypoint3);
        GuardRoute guardRoute1 = new GuardRoute(route, "1401");
        GuardRoute guardRoute2 = new GuardRoute(route2, "1401");
        GuardRoute guardRoute3 = new GuardRoute(route2, "1401");
        Guard otto = new Guard("Otto", "Muellerich", "2", "1234");

        //Guard loggedInGuard = otto;
        ///////////////////////////////////////////////////////////

        Guard loggedInGuard = (Guard) getArguments().getSerializable("guard");
        loggedInGuard.addRoute(guardRoute1);
        loggedInGuard.addRoute(guardRoute2);
        loggedInGuard.addRoute(guardRoute3);
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
