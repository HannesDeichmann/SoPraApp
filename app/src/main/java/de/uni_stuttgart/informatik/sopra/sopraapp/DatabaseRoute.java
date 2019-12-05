package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseRoute extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "RouteData.db";
    public static final String SQL_CREATE_ROUTE_ENTRIES = "CREATE TABLE " +
            DbContract.TABLE_NAME_ROUTE + "(" +
            DbContract.COLUMN_NAME_ROUTEID + " INTEGER PRIMARY KEY," +
            DbContract.COLUMN_NAME_ROUTENAME + " TEXT" + " )";
    //Waypointsliste adden

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

    public void addRoute(Route route) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.COLUMN_NAME_ROUTEID, route.getRouteId());
        values.put(DbContract.COLUMN_NAME_ROUTENAME, route.getRouteName());
        //values.put(DbContract.COLUMN_NAME_ROUTEPOINTLIST, route.getWaypoints());
        db.insert(DbContract.TABLE_NAME_ROUTE, null, values);
        db.close();
    }

    public Route getRoute(int position) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + DbContract.TABLE_NAME_ROUTE + " order by "
                + DbContract.COLUMN_NAME_ROUTEID + " asc limit 1 offset " + position, null);
        c.moveToFirst();
        Route Route = new Route();
        addRouteInfos(Route,c);
        c.close();
        return Route;
    }
    private void addRouteInfos(Route route, Cursor c){
        route.setRouteId(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_ROUTEID)));
        //route.setWaypoints(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_ROUTEPOINTLIST)));
        route.setRouteName(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_ROUTENAME)));
    }
    public Route getRouteById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + DbContract.TABLE_NAME_ROUTE + " where "
                + DbContract.COLUMN_NAME_ROUTEID + "=" + id, null);
        c.moveToFirst();
        Route route = new Route();
        addRouteInfos(route,c);
        c.close();
        return route;
    }

    public ArrayList<Route> getAllRoutes() {
        ArrayList<Route> routeList = new ArrayList<Route>();
        for (int i = 0; i <= this.getRouteCount() - 1; i++) {
            routeList.add(getRoute(i));
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

