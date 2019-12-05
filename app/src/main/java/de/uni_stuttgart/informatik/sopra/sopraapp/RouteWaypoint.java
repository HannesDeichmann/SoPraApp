package de.uni_stuttgart.informatik.sopra.sopraapp;

import java.io.Serializable;
import java.time.Duration;

public class RouteWaypoint implements Serializable {
    private Waypoint waypoint;
    private Duration duration;
    private String waypointId;
    private String timeString;

    public RouteWaypoint(Waypoint waypoint, Duration duration){
        this.waypoint = waypoint;
        this.duration = duration;
    }
    public RouteWaypoint(String waypointId, String timeString){
        this.waypoint = waypoint;
        this.duration = duration;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Waypoint getWaypoint() {
        return this.waypoint;
    }

    public void setWaypoint(Waypoint waypoint) {
        this.waypoint = waypoint;
    }
}
