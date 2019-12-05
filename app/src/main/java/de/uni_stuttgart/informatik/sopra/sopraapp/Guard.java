package de.uni_stuttgart.informatik.sopra.sopraapp;

import java.util.ArrayList;

public class Guard {
    private String forename;
    private String surname;
    private String userId;
    private String userPassword;

    public Guard(String forname, String surname, String userId, String userPassword) {
        this.forename = forname;
        this.surname = surname;
        this.userId = userId;
        this.userPassword = userPassword;
    }
    public Guard(String forname, String surname, String userPassword) {
        this.forename = forname;
        this.surname = surname;
        this.userId = "";
        this.userPassword = userPassword;
    }

    public Guard() {
        this.forename = "";
        this.surname = "";
        this.userId = "";
        this.userPassword = "";
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
