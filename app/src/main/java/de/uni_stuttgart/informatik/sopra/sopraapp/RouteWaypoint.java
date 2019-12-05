package de.uni_stuttgart.informatik.sopra.sopraapp;

import java.io.Serializable;
import java.time.Duration;

public class RouteWaypoint implements Serializable {
    private Waypoint waypoint;
    private Duration duration;
    public RouteWaypoint(Waypoint waypoint, Duration duration){
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
        return waypoint;
    }

    public void setWaypoint(Waypoint waypoint) {
        this.waypoint = waypoint;
    }
}
