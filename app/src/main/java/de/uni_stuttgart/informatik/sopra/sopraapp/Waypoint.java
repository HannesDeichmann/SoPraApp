package de.uni_stuttgart.informatik.sopra.sopraapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Waypoint implements Serializable {
    private String waypointName;
    private String waypointPosition;
    private String waypointId;
    private String waypointNote;
    public static final int waypointIdLength = 6;

    public Waypoint(String waypointName, String waypointId, String waypointPosition, String waypointNote) {
        this.setWaypointName(waypointName);
        this.setWaypointId(waypointId);
        this.setWaypointPosition(waypointPosition);
        this.setWaypointNote(waypointNote);
    }

    public Waypoint() {
        this.setWaypointName("");
        this.setWaypointId("");
        this.setWaypointPosition("");
        this.setWaypointNote("");
    }

    @Override
    public String toString() {
        String waypointString = this.waypointId + ": ";
        waypointString += this.waypointName + " Pos ";
        waypointString += this.waypointPosition + ", Note";
        waypointString += this.waypointNote + ", ";
        return waypointString;
    }

    public Float getXKoordinate(){
        String[] splitted = this.waypointPosition.split(";");
        return Float.valueOf(splitted[0]);
    }
    public Float getYKoordinate(){
        String[] splitted = this.waypointPosition.split(";");
        return Float.valueOf(splitted[1]);
    }

    public void setWaypointName(String waypointName) {
        this.waypointName = waypointName;
    }

    public String getWaypointName() {
        return this.waypointName;
    }

    public void setWaypointId(String waypointId) {
        this.waypointId = waypointId;
    }

    public String getWaypointId() {
        return this.waypointId;
    }

    public String getWaypointPosition() {
        return this.waypointPosition;
    }

    public String getWaypointNote() {
        return this.waypointNote;
    }

    public void setWaypointPosition(String waypointPosition) {
        this.waypointPosition = waypointPosition;
    }

    public void setWaypointNote(String waypointNote) {
        this.waypointNote = waypointNote;
    }
}
