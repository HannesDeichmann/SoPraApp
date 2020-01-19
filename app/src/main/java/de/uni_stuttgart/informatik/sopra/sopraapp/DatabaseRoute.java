package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.bluetooth.BluetoothClass;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseRoute extends SQLiteOpenHelper {
    DatabaseWaypoint databaseWaypoint;
    public static final int DATABASE_VERSION = 1;
    public static final String DEVIDESTRING = "/";
    public static final String DATABASE_NAME = "RouteData.db";
    public static final String SQL_CREATE_ROUTE_ENTRIES = "CREATE TABLE " +
            DbContract.TABLE_NAME_ROUTE + "(" +
            DbContract.COLUMN_NAME_ROUTEID + " INTEGER PRIMARY KEY," +
            DbContract.COLUMN_NAME_ROUTENAME + " TEXT," +
            DbContract.COLUMN_NAME_ROUTEWAYPOINTIDLIST + " TEXT," +
            DbContract.COLUMN_NAME_ROUTETIMELIST + " TEXT" + " )";


    private static final String SQL_DELETE_ROUTE_ENTRIES = "DROP TABLE IF EXISTS " + DbContract.TABLE_NAME_ROUTE;

    public DatabaseRoute(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ROUTE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ROUTE_ENTRIES);
        onCreate(db);
    }

    private String getWaypointIdString(Route route) {
        String waypointIdList = "";
        if (route.getWaypointStrings() != null) {
            if (route.getWaypointStrings().size() <= route.getWaypoints().size()) {
                for (RouteWaypoint w : route.getWaypoints()) {
                    waypointIdList += DEVIDESTRING + w.getWaypoint().getWaypointId();
                }
            } else {
                for (RouteWaypointStrings rwps : route.getWaypointStrings()) {
                    waypointIdList += DEVIDESTRING + rwps.getUserId();
                }
            }
        } else {
            for (RouteWaypoint w : route.getWaypoints()) {
                waypointIdList += DEVIDESTRING + w.getWaypoint().getWaypointId();
            }
        }
        if (!waypointIdList.equals("")) {
            waypointIdList.substring(1);
        }
        return waypointIdList;
    }

    //gleich wie getWapoinIdString anpassen
    private String getTimeString(Route route) {
        String timeString = "";
        if (route.getWaypointStrings() != null) {
            if (route.getWaypointStrings().size() <= route.getWaypoints().size()) {
                for (RouteWaypoint w : route.getWaypoints()) {
                    timeString += DEVIDESTRING + String.valueOf(w.getDuration().toMinutes());
                }
            } else {
                for (RouteWaypointStrings rwps : route.getWaypointStrings()) {
                    timeString += DEVIDESTRING + rwps.getTime();
                }
            }
        } else {
            for (RouteWaypoint w : route.getWaypoints()) {
                timeString += DEVIDESTRING + String.valueOf(w.getDuration().toMinutes());
            }
        }

        return timeString;
    }


    public void addRoute(Route route) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.COLUMN_NAME_ROUTEID, route.getRouteId());
        values.put(DbContract.COLUMN_NAME_ROUTENAME, route.getRouteName());
        values.put(DbContract.COLUMN_NAME_ROUTEWAYPOINTIDLIST, getWaypointIdString(route));
        values.put(DbContract.COLUMN_NAME_ROUTETIMELIST, getTimeString(route));
        db.insert(DbContract.TABLE_NAME_ROUTE, null, values);
        db.close();
    }

    public Route getRouteFromPosition(int position) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + DbContract.TABLE_NAME_ROUTE + " order by "
                + DbContract.COLUMN_NAME_ROUTEID + " asc limit 1 offset " + position, null);
        c.moveToFirst();
        Route route = new Route();
        addRouteInfos(route, c);
        c.close();
        return route;
    }

    private void addRouteInfos(Route route, Cursor c) {
        route.setRouteId(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_ROUTEID)));
        String waypointid = c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_ROUTEWAYPOINTIDLIST));
        String time = c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_ROUTETIMELIST));
        route.setWaypointStrings(createRouteWaypoints(waypointid, time));
        route.setRouteName(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_ROUTENAME)));
    }

    private ArrayList<RouteWaypointStrings> createRouteWaypoints(String points, String time) {
        ArrayList<String> pointsList = new ArrayList<>();
        ArrayList<String> timeList = new ArrayList<>();
        ArrayList<RouteWaypointStrings> routeWaypoints = new ArrayList<>();
        pointsList = DbContract.stringIntoArrayList(points);
        timeList = DbContract.stringIntoArrayList(time);
        if (pointsList.size() == timeList.size())
            for (int i = 0; i < timeList.size(); i++) {
                if (!pointsList.get(i).isEmpty() && !timeList.get(i).isEmpty()) {
                    routeWaypoints.add(new RouteWaypointStrings(pointsList.get(i), timeList.get(i)));

                }
            }
        return routeWaypoints;
    }

    public Route getRouteById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + DbContract.TABLE_NAME_ROUTE + " where "
                + DbContract.COLUMN_NAME_ROUTEID + "=" + id, null);
        c.moveToFirst();
        Route route = new Route();
        addRouteInfos(route, c);
        c.close();
        return route;
    }

    public ArrayList<Route> getAllRoutes() {
        ArrayList<Route> routeList = new ArrayList<Route>();
        for (int i = 0; i <= this.getRouteCount() - 1; i++) {
            routeList.add(getRouteFromPosition(i));
        }
        return routeList;
    }

    public void deleteRoute(Route Route) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] RouteId = new String[]{String.valueOf(Route.getRouteId())};
        db.delete(DbContract.TABLE_NAME_ROUTE, DbContract.COLUMN_NAME_ROUTEID + "=?", RouteId);
        db.close();
    }

    public int getRouteCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT  * FROM " + DbContract.TABLE_NAME_ROUTE, null);
        int count = c.getCount();
        c.close();
        return count;
    }
}

