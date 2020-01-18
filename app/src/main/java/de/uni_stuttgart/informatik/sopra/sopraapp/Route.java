package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.provider.ContactsContract;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class Route implements Serializable {

    private String routeName;
    private ArrayList<RouteWaypoint> waypoints;
    private String routeId;
    private ArrayList<RouteWaypointStrings> waypointStrings;

    public Route(){
        this.routeName = "default";
        this.waypoints = new ArrayList<RouteWaypoint>();
    }

    @Override
    public String toString(){
        String string = "";
        string += routeId + ": ";
        string += routeName;
        return string;
    }

    public void setRouteId(String id) { this.routeId = id; }

    public String getRouteName() { return routeName; }

    public String getRouteId() { return routeId; }

    public void setRouteName(String name) { this.routeName = name; }

    public ArrayList<RouteWaypoint> getWaypoints() { return waypoints; }

    public void setWaypoints(ArrayList waypoints) { this.waypoints = waypoints; }

    public ArrayList<Waypoint> getOnlyWaypoints(){
        ArrayList<Waypoint> wpList = new ArrayList<>();
        for(RouteWaypoint rwp: this.waypoints){
            wpList.add(rwp.getWaypoint());
        }
        return wpList;
    }

    public void addWaypoint(RouteWaypoint waypoint){
        this.waypoints.add(waypoint);
    }

    public void replaceWaypointAt(RouteWaypoint waypoint, int position){
        this.waypoints.remove(position);
        this.waypoints.add(position, waypoint);}

    public void deleteWaypoint(RouteWaypoint waypoint){ this.waypoints.remove(waypoint); }

    public ArrayList<RouteWaypointStrings> getWaypointStrings() {
        return waypointStrings;
    }

    public void setWaypointStrings(ArrayList<RouteWaypointStrings> waypointStrings) {
        this.waypointStrings = waypointStrings;

    }
}
