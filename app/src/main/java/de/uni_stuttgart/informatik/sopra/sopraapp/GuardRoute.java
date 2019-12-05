package de.uni_stuttgart.informatik.sopra.sopraapp;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

public class GuardRoute implements Serializable {
    private Route route;
    private String time;

    public GuardRoute(Route route, String time){
        this.route = route;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
