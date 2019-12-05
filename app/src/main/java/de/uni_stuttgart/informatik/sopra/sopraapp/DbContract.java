package de.uni_stuttgart.informatik.sopra.sopraapp;

import java.util.ArrayList;

public class DbContract {
    public static final String TABLE_NAME_GUARD = "guardtable";
    public static final String COLUMN_NAME_GUARDID = "guardid";
    public static final String COLUMN_NAME_GUARDPASSWORD = "guardpassword";
    public static final String COLUMN_NAME_GUARDFORNAME = "guardforname";
    public static final String COLUMN_NAME_GUARDSURNAME = "guardsurname";

    public static final String TABLE_NAME_WAYPOINT = "waypointtable";
    public static final String COLUMN_NAME_WAYPOINTID = "waypointid";
    public static final String COLUMN_NAME_WAYPOINTNAME = "waypointname";
    public static final String COLUMN_NAME_WAYPOINTPOSITION = "waypointposition";
    public static final String COLUMN_NAME_WAYPOINTNOTE = "waypointnote";


    public static final String TABLE_NAME_ROUTE = "routetable";
    public static final String COLUMN_NAME_ROUTEID = "routeid";
    public static final String COLUMN_NAME_ROUTENAME = "routename";
    //public static final ArrayList<Waypoint> COLUMN_NAME_ROUTEPOINTLIST = new ArrayList<Waypoint>();
}
