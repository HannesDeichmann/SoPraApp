package de.uni_stuttgart.informatik.sopra.sopraapp;

import java.lang.reflect.Array;

public class Waypoint{

    private String name;
    private int nfcTag;
    private Array connections;

    public Waypoint(String name, int nfcTag){
        Waypoint waypoint = new Waypoint();
        waypoint.setName(name);
        waypoint.setNfcTag(nfcTag);
    }

    public Waypoint(){
        Waypoint waypoint = new Waypoint();
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }

    public void setNfcTag(int nfcTag){
        this.nfcTag = nfcTag;
    }

    public int getNfcTag(){
        return this.nfcTag;
    }

    public void setConnections(Waypoint waypoint, int duration){
        //TODO
    }

    public Array getConnections(){
        return this.connections;
    }
    public void deleteWaypoint(){
        //TODO
    }


}
