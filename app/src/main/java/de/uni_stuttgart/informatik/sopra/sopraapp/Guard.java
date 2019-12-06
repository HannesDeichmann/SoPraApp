package de.uni_stuttgart.informatik.sopra.sopraapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Guard implements Serializable {

    private ArrayList<GuardRoute> routes = new ArrayList<GuardRoute>();
    private String forename;
    private String surname;
    private String userId;
    private String userPassword;
    private ArrayList<String>  routeIdString;
    private ArrayList<GuardRoute> guardRouteList;
    private ArrayList<String> routeTimeString;

    public Guard(String forname, String surname, String userId, String userPassword) {
        this.forename = forname;
        this.surname = surname;
        this.userId = userId;
        this.userPassword = userPassword;
        this.routeIdString = new ArrayList<>();
        this.routeTimeString= new ArrayList<>();;
        this.guardRouteList= new ArrayList<>();
    }
    public Guard(String forname, String surname, String userPassword) {
        this.forename = forname;
        this.surname = surname;
        this.userId = "";
        this.userPassword = userPassword;
        this.routeIdString= new ArrayList<>();
        this.routeTimeString= new ArrayList<>();
        this.guardRouteList= new ArrayList<>();
    }

    public Guard() {
        this.forename = "";
        this.surname = "";
        this.userId = "";
        this.userPassword = "";
        this.routeIdString= new ArrayList<>();
        this.routeTimeString= new ArrayList<>();
        this.guardRouteList= new ArrayList<>();
    }

    @Override
    public String toString(){
        String guardString = "";
        guardString +=  this.userId + ": ";
        guardString += this.forename + " ";
        guardString += this.surname + ",   ";
        guardString += "Password: " + this.userPassword;
        return guardString;
    }

    public String getTimeListString(){
        String string ="";
        for(GuardRoute g: this.getGuardRouteList()){
            string += DbContract.DIVIDESTRING + g.getRoute().getRouteId();
        }
        return string;
    }
    public String getRouteIdListString(){
        String string ="";
        for(GuardRoute g: this.getGuardRouteList()){
            string += DbContract.DIVIDESTRING + g.getTime();
        }
        return string;
    }


    public void setForename(String forename) {
        this.forename = forename;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public ArrayList<GuardRoute> getRoutes() {
        return routes;
    }

    public void addRoute(GuardRoute route) {
        this.routes.add(route);
    }

    public ArrayList<String> getRouteIdString() {
        return routeIdString;
    }

    public void setRouteIdString(ArrayList<String> routeIdString) {
        this.routeIdString = routeIdString;
    }

    public ArrayList<GuardRoute> getGuardRouteList() {
        return guardRouteList;
    }

    public void setGuardRouteList(ArrayList<GuardRoute> guardRouteString) {
        this.guardRouteList = guardRouteString;
    }

    public ArrayList<String> getRouteTimeString() {
        return routeTimeString;
    }

    public void setRouteTimeString(ArrayList<String> routeTimeString) {
        this.routeTimeString = routeTimeString;
    }
}
