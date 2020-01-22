package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * DatabaseGuard saves all guards in the database. It handles the update/add and delete methods.
 * The Database transforms a Guard in a String and saves each atribute in his own Column.
 *
 * @author Gabriel Bonnet 3410781
 * @version 22.01.2020
 */
public class DatabaseGuard extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GuardData.db";
    public static final String SQL_CREATE_GUARD_ENTRIES = "CREATE TABLE " +
            DbContract.TABLE_NAME_GUARD + "(" +
            DbContract.COLUMN_NAME_GUARDID + " INTEGER PRIMARY KEY," +
            DbContract.COLUMN_NAME_GUARDPASSWORD + " TEXT," +
            DbContract.COLUMN_NAME_GUARDFORNAME + " TEXT," +
            DbContract.COLUMN_NAME_GUARDSURNAME + " TEXT," +
            DbContract.COLUMN_NAME_GUARDROUTEIDLIST + " TEXT," +
            DbContract.COLUMN_NAME_GUARDSTARTTIMELIST + " TEXT" + " )";
    private static boolean guardWithRoutes = false;

    private static final String SQL_DELETE_GUARD_ENTRIES = "DROP TABLE IF EXISTS " + DbContract.TABLE_NAME_GUARD;

    public DatabaseGuard(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_GUARD_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_GUARD_ENTRIES);
        onCreate(db);
    }

    /**
     * Add a guard to the last column of the Database
     *
     * @param guard
     */
    public void addGuard(Guard guard) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.COLUMN_NAME_GUARDPASSWORD, guard.getUserPassword());
        values.put(DbContract.COLUMN_NAME_GUARDFORNAME, guard.getForename());
        values.put(DbContract.COLUMN_NAME_GUARDSURNAME, guard.getSurname());
        values.put(DbContract.COLUMN_NAME_GUARDROUTEIDLIST, guard.getTimeListString());
        values.put(DbContract.COLUMN_NAME_GUARDSTARTTIMELIST, guard.getRouteIdListString());
        db.insert(DbContract.TABLE_NAME_GUARD, null, values);
        db.close();
    }

    /**
     * Updates the Routes from a guard.
     * The Routes are saved as Strings of Waypoint ids and Time Ids to save them as String.
     *
     * @param guard
     */
    public void addGuardRoute(Guard guard) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("UPDATE " + DbContract.TABLE_NAME_GUARD
                + " SET " +
                DbContract.COLUMN_NAME_GUARDROUTEIDLIST + " = '" + guard.getRouteIdListString() + "', " +
                DbContract.COLUMN_NAME_GUARDSTARTTIMELIST + " = '" + guard.getTimeListString() + "' WHERE " +
                DbContract.COLUMN_NAME_GUARDID + " = " + guard.getUserId(), null);
        c.moveToFirst();
        c.close();
    }

    /**
     * Edit a guard without the GuardRoute List.
     *
     * @param guard
     */
    public void editGuard(Guard guard) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("UPDATE " + DbContract.TABLE_NAME_GUARD
                + " SET " +
                DbContract.COLUMN_NAME_GUARDFORNAME + " = '" + guard.getForename() + "', " +
                DbContract.COLUMN_NAME_GUARDSURNAME + " = '" + guard.getSurname() + "', " +
                DbContract.COLUMN_NAME_GUARDPASSWORD + " = '" + guard.getUserPassword() + "' WHERE " +
                DbContract.COLUMN_NAME_GUARDID + " = " + guard.getUserId(), null);
        c.moveToFirst();
        c.close();
    }

    /**
     * Gets a Guard by his position in the database.
     *
     * @param position
     * @return
     */
    public Guard getGuard(int position) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + DbContract.TABLE_NAME_GUARD + " order by "
                + DbContract.COLUMN_NAME_GUARDID + " asc limit 1 offset " + position, null);
        c.moveToFirst();
        Guard guard = new Guard();
        addGuardInfos(guard, c);
        c.close();
        return guard;
    }

    /**
     * To add the routes to a guard
     *
     * @param guard
     * @return guard with Routes
     */
    public Guard getGuardWithRoutes(Guard guard){
        this.guardWithRoutes = true;
        return this.getGuardById(Integer.parseInt(guard.getUserId()));
    }

    private void addGuardInfos(Guard guard, Cursor c) {
        guard.setUserPassword(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_GUARDPASSWORD)));
        guard.setForename(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_GUARDFORNAME)));
        guard.setSurname(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_GUARDSURNAME)));
        guard.setUserId(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_GUARDID)));
        if(guardWithRoutes){
            guard.setRouteIdString(DbContract.stringIntoArrayList(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_GUARDROUTEIDLIST))));
            guard.setRouteTimeString(DbContract.stringIntoArrayList(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_GUARDSTARTTIMELIST))));
            guardWithRoutes = false;
        }
    }

    public Guard getGuardById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + DbContract.TABLE_NAME_GUARD + " where "
                + DbContract.COLUMN_NAME_GUARDID + "=" + id, null);
        c.moveToFirst();
        Guard guard = new Guard();
        addGuardInfos(guard, c);
        c.close();
        return guard;
    }

    public ArrayList<Guard> getAllGuards() {
        ArrayList<Guard> guardList = new ArrayList<>();
        for (int i = 0; i < this.getGuardCount(); i++) {
            guardList.add(getGuard(i));
        }
        return guardList;
    }

    public void deleteGuard(Guard guard) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] guardId = new String[]{String.valueOf(guard.getUserId())};
        db.delete(DbContract.TABLE_NAME_GUARD, DbContract.COLUMN_NAME_GUARDID + "=?", guardId);
        db.close();
    }

    public int getGuardCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT  * FROM " + DbContract.TABLE_NAME_GUARD, null);
        int count = c.getCount();
        c.close();
        return count;
    }
}
