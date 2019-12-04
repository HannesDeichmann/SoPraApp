package de.uni_stuttgart.informatik.sopra.sopraapp;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class Route implements Serializable {

    private String name;
    private ArrayList<RouteWaypoint> waypoints;

    //default constructor
    public Route(){
        this.name = "dfault";
        this.waypoints = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getWaypoints() {
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
