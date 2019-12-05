package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseWaypoint extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WaypointData.db";
    public static final String SQL_CREATE_WAYPOINT_ENTRIES = "CREATE TABLE " +
            DbContract.TABLE_NAME_WAYPOINT + "(" +
            DbContract.COLUMN_NAME_WAYPOINTID + " TEXT," +
            DbContract.COLUMN_NAME_WAYPOINTNAME + " TEXT," +
            DbContract.COLUMN_NAME_WAYPOINTNFC + " TEXT" + " )";

    private static final String SQL_DELETE_WAYPOINT_ENTRIES = "DROP TABLE IF EXISTS " + DbContract.TABLE_NAME_WAYPOINT;

    public DatabaseWaypoint(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_WAYPOINT_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_WAYPOINT_ENTRIES);
        onCreate(db);
    }

    public void addWaypoint(Waypoint waypoint) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.COLUMN_NAME_WAYPOINTID, waypoint.getWaypointId());
        values.put(DbContract.COLUMN_NAME_WAYPOINTNAME, waypoint.getWaypointName());
        values.put(DbContract.COLUMN_NAME_WAYPOINTNFC, waypoint.getWaypointNfcTag());
        db.insert(DbContract.TABLE_NAME_WAYPOINT, null, values);
        db.close();
    }

    public Waypoint getWaypoint(int position) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + DbContract.TABLE_NAME_WAYPOINT + " order by "
                + DbContract.COLUMN_NAME_WAYPOINTID + " asc limit 1 offset " + position, null);
        c.moveToFirst();
        Waypoint waypoint = new Waypoint();
        addWaypointInfos(waypoint,c);
        c.close();
        return waypoint;
    }
    private void addWaypointInfos(Waypoint waypoint, Cursor c){
        waypoint.setWaypointId(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_WAYPOINTID)));
        waypoint.setWaypointName(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_WAYPOINTNAME)));
        waypoint.setWaypointNfcTag(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_WAYPOINTNFC)));
    }
    public Waypoint getWaypointById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + DbContract.TABLE_NAME_WAYPOINT+ " where "
                + DbContract.COLUMN_NAME_WAYPOINTID + "=" + id, null);
        c.moveToFirst();
        Waypoint waypoint = new Waypoint();
        addWaypointInfos(waypoint,c);
        c.close();
        return waypoint;
    }

    public ArrayList<Waypoint> getAllWaypoints() {
        ArrayList<Waypoint> waypointList = new ArrayList<>();
        for (int i = 0; i <= this.getWaypointCount() - 1; i++) {
            waypointList.add(getWaypoint(i));
        }
        return waypointList;
    }

    public void deleteWaypoint(Waypoint waypoint) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] WaypointId = new String[]{String.valueOf(waypoint.getWaypointId())};
        db.delete(DbContract.TABLE_NAME_WAYPOINT, DbContract.COLUMN_NAME_WAYPOINTID + "=?", WaypointId);
        db.close();
    }

    public int getWaypointCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT  * FROM " + DbContract.TABLE_NAME_WAYPOINT, null);
        int count = c.getCount();
        c.close();
        return count;
    }
}
