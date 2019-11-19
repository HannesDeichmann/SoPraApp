package de.uni_stuttgart.informatik.sopra.sopraapp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Route {

    private String name;
    private ArrayList<Waypoint> waypoints;
    private int startTime;
    private int finishTime;

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

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }


    public void addWaypoint(Waypoint waypoint){
        this.waypoints.add(waypoint);
    }
}
