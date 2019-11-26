package de.uni_stuttgart.informatik.sopra.sopraapp;

import java.util.ArrayList;

public class Waypoint{
    private String waypointName;
    private String waypointNfcTag;
    private String waypointId;
    private static ArrayList<Waypoint> waypointList = new ArrayList<Waypoint>();
    public static final int waypointIdLength = 6;
    public static final String waypointIdIndicator = "Login-Daten: ";

    public Waypoint(String waypointName, String waypointId, String waypointNfcTag){
        this.setWaypointName(waypointName);
        this.setWaypointId(waypointId);
        this.setWaypointNfcTag(waypointNfcTag);
        this.waypointList.add(this);
    }

    public Waypoint(){

        this.setWaypointName("");
        this.setWaypointId("");
        this.setWaypointNfcTag("");
        this.waypointList.add(this);
    }

    @Override
    public String toString(){
        String waypointString = "";
        waypointString += "Name: " + this.waypointName + " ";
        waypointString += "Login-Daten: " + this.waypointId + " ";
        waypointString += this.waypointNfcTag + "; ";
        return waypointString;
    }
    public static ArrayList<Waypoint> getWaypointList() {
        return waypointList;
    }

    public static void setWaypointList(ArrayList<Waypoint> waypointList) { Waypoint.waypointList = waypointList;}

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
