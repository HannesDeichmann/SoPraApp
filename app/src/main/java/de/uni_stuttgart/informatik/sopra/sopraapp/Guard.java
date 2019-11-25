package de.uni_stuttgart.informatik.sopra.sopraapp;

import java.util.ArrayList;

public class Guard {
    private String forename;
    private String surname;
    private String userId;
    private String userPassword;
    private static ArrayList<Guard> guardList = new ArrayList<Guard>();
    public static final int userIdLength = 6;
    public static final String userIdIndicator = "Login-Daten: ";

    public Guard(String forname, String surname, String userId, String userPassword) {
        this.forename = forname;
        this.surname = surname;
        this.userId = userId;
        this.userPassword = userPassword;
        this.guardList.add(this);
    }

    public Guard() {
        this.forename = "";
        this.surname = "";
        this.userId = "";
        this.userPassword = "";
        this.guardList.add(this);
    }

    @Override
    public String toString(){
        String guardString = "";
        guardString += "Name: " + this.forename + " ";
        guardString += this.surname + "; ";
        guardString += "Login-Daten: " + this.userId + " ";
        guardString += this.userPassword;
        return guardString;
    }

    public static ArrayList<Guard> getGuardList() {
        return guardList;
    }

    public static void setGuardList(ArrayList<Guard> guardList) {
        Guard.guardList = guardList;
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
}
