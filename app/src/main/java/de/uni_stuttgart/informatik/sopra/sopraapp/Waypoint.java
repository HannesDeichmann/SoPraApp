package de.uni_stuttgart.informatik.sopra.sopraapp;

import java.util.ArrayList;

public class Waypoint{
    private String waypointName;
    private String waypointNfcTag;
    private String waypointId;
    public static final int waypointIdLength = 6;

    public Waypoint(String waypointName, String waypointId, String waypointNfcTag){
        this.setWaypointName(waypointName);
        this.setWaypointId(waypointId);
        this.setWaypointNfcTag(waypointNfcTag);
    }

    public Waypoint(){
        this.setWaypointName("");
        this.setWaypointId("");
        this.setWaypointNfcTag("");
    }

    @Override
    public String toString(){
        String waypointString = this.waypointId + ": ";
        waypointString += this.waypointName + "NFC: ";
        waypointString += this.waypointNfcTag;
        return waypointString;
    }

    public void setWaypointName(String waypointName){
        this.waypointName=waypointName;
    }

    public String getWaypointName(){
        return this.waypointName;
    }

    public void setWaypointId(String waypointId){
        this.waypointId=waypointId;
    }

    public String getWaypointId(){
        return this.waypointId;
    }

    public void setWaypointNfcTag(String waypointNfcTag){
        this.waypointNfcTag = waypointNfcTag;
    }

    public String getWaypointNfcTag(){
        return this.waypointNfcTag;
    }


}
