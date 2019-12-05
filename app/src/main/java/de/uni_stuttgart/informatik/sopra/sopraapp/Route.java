package de.uni_stuttgart.informatik.sopra.sopraapp;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class Route implements Serializable {

    private String routeName;
    private ArrayList<RouteWaypoint> waypoints;
    private String routeId;

    public Route(){
        this.routeName = "default";
        this.waypoints = new ArrayList<RouteWaypoint>();
    }
    public void setRouteId(String id) { this.routeId = id; }

    public String getRouteName() {
        return routeName;
    }
    public String getRouteId() { return routeId; }

    public void setRouteName(String name) {
        this.routeName = name;
    }

    public ArrayList<RouteWaypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(ArrayList waypoints) {
        this.waypoints = waypoints;
    }

    public void addWaypoint(RouteWaypoint waypoint){
        this.waypoints.add(waypoint);
    }

    public void deleteWaypoint(RouteWaypoint waypoint){ this.waypoints.remove(waypoint); }
}
