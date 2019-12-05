package de.uni_stuttgart.informatik.sopra.sopraapp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Route {

    private String routeName;
    private ArrayList<Waypoint> waypoints;
    private String routeId;

    public String getRouteId() { return routeId; }

    public void setRouteId(String id) { this.routeId = id; }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String name) {
        this.routeName = name;
    }

    public ArrayList getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(ArrayList waypoints) {
        this.waypoints = waypoints;
    }

    public void addWaypoint(Waypoint waypoint) { this.waypoints.add(waypoint); }
}
