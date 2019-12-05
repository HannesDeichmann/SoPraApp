package de.uni_stuttgart.informatik.sopra.sopraapp;

public class RouteWaypointStrings {
    private String time;
    private String userId;

    public RouteWaypointStrings( String time, String userId){
        this.time = time;
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
