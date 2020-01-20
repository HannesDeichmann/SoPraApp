package de.uni_stuttgart.informatik.sopra.sopraapp;

import java.util.ArrayList;

public class DbContract {
    public static final String TABLE_NAME_GUARD = "guardtable";
    public static final String COLUMN_NAME_GUARDID = "guardid";
    public static final String COLUMN_NAME_GUARDPASSWORD = "guardpassword";
    public static final String COLUMN_NAME_GUARDFORNAME = "guardforname";
    public static final String COLUMN_NAME_GUARDSURNAME = "guardsurname";
    public static final String COLUMN_NAME_GUARDROUTEIDLIST = "guardrouteidlist";
    public static final String COLUMN_NAME_GUARDSTARTTIMELIST = "guardstarttimelist";

    public static final String TABLE_NAME_WAYPOINT = "waypointtable";
    public static final String COLUMN_NAME_WAYPOINTID = "waypointid";
    public static final String COLUMN_NAME_WAYPOINTNAME = "waypointname";
    public static final String COLUMN_NAME_WAYPOINTPOSITION = "waypointposition";
    public static final String COLUMN_NAME_WAYPOINTNOTE = "waypointnote";

    public static final String TABLE_NAME_ROUTE = "routetable";
    public static final String COLUMN_NAME_ROUTEID = "routeid";
    public static final String COLUMN_NAME_ROUTENAME = "routename";
    public static final String COLUMN_NAME_ROUTETIMELIST = "routetimes";
    public static final String COLUMN_NAME_ROUTEWAYPOINTIDLIST = "routewaypointid";

    public static final String DIVIDESTRING = "/";

    public static ArrayList<String> stringIntoArrayList(String string){
        ArrayList<String> arrayList = new ArrayList<>();
        String[] split = string.split(DIVIDESTRING);
        for(String text:split) {
            if(!text.equals(""))
            arrayList.add(text);
        }
        return arrayList;
    }
}
