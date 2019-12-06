package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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

    public void addGuardRoute(Guard guard) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("UPDATE " + DbContract.TABLE_NAME_GUARD
                + " SET " +
                DbContract.COLUMN_NAME_GUARDSTARTTIMELIST + " = '" + guard.getTimeListString() + "', " +
                DbContract.COLUMN_NAME_GUARDROUTEIDLIST + " = '" + guard.getRouteIdListString() + "' WHERE " +
                DbContract.COLUMN_NAME_GUARDID + " = " + guard.getUserId(), null);
        c.moveToFirst();
        c.close();
    }

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

    private void addGuardInfos(Guard guard, Cursor c) {
        guard.setUserPassword(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_GUARDPASSWORD)));
        guard.setForename(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_GUARDFORNAME)));
        guard.setSurname(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_GUARDSURNAME)));
        guard.setUserId(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_GUARDID)));
        if (!c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_GUARDROUTEIDLIST)).equals("") &&
                !c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_ROUTETIMELIST)).equals("")) {
            guard.setRouteIdString(DbContract.stringIntoArrayList(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_GUARDROUTEIDLIST))));
            guard.setRouteTimeString(DbContract.stringIntoArrayList(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_ROUTETIMELIST))));
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
        for (int i = 0; i <= this.getGuardCount() - 1; i++) {
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
