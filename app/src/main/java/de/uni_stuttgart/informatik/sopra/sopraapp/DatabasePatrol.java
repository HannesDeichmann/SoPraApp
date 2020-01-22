package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


/**
 * DatabaseGuard saves all patrols in the database. It handles the update/add and delete methods.
 * The Database transforms a patrol in a String.
 *
 * @author Gabriel Bonnet 3410781
 * @version 22.01.2020
 */
public class DatabasePatrol extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Patrol" + "Data.db";
    public static final String SQL_CREATE_PATROL_ENTRIES = "CREATE TABLE " +
            DbContract.TABLE_NAME_PATROL + "(" +
            DbContract.COLUMN_NAME_PATROLID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DbContract.COLUMN_NAME_PATROL + " TEXT" + " )";

    private static final String SQL_DELETE_PATROL_ENTRIES = "DROP TABLE IF EXISTS " + DbContract.TABLE_NAME_PATROL;

    public DatabasePatrol(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PATROL_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PATROL_ENTRIES);
        onCreate(db);
    }

    // Format: ";GuardName;GuardRoute;RouteTime;ActuallTime
    public void addNewPatrol(String patrol){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.COLUMN_NAME_PATROL, patrol);
        db.insert(DbContract.TABLE_NAME_PATROL, null, values);
        db.close();
    }


    /**
     * Updates the actual
     * @param patrol
     */
    public void updatePatrolString(String patrol) {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer actuallPosition = Integer.valueOf(this.getPatrolCount()) -1;
        Cursor c = db.rawQuery("select * from " + DbContract.TABLE_NAME_PATROL + " order by "
                + DbContract.COLUMN_NAME_PATROLID + " asc limit 1 offset " + actuallPosition.toString(), null);
        c.moveToFirst();
        String oldInformation = c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_PATROL));
        String patrolId = c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_PATROLID));
        c.close();
        Cursor c2 = db.rawQuery("UPDATE " + DbContract.TABLE_NAME_PATROL
                + " SET " +
                DbContract.COLUMN_NAME_PATROL + " = '" + oldInformation + patrol + "' WHERE " +
                DbContract.COLUMN_NAME_PATROLID + " = " + patrolId, null);
        c2.moveToFirst();
        c2.close();

    }

    public String getPatrol(int position) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + DbContract.TABLE_NAME_PATROL + " order by "
                + DbContract.COLUMN_NAME_PATROLID + " asc limit 1 offset " + position, null);
        c.moveToFirst();
        String patrol = c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_PATROLID));
        patrol += c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_PATROL));
        c.close();
        return patrol;
    }

    public ArrayList<String> getAllPatrols() {
        ArrayList<String> allPatrols = new ArrayList<>();
        for (int i = 0; i < this.getPatrolCount(); i++) {
            allPatrols.add(this.getPatrol(i));
        }
        return allPatrols;
    }

    public void deletePatrol(Integer position) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] patrolId = new String[]{String.valueOf(getPatrol(position).split(";")[0])};
        db.delete(DbContract.TABLE_NAME_PATROL, DbContract.COLUMN_NAME_PATROLID + "=?", patrolId);
        db.close();
    }

    public int getPatrolCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT  * FROM " + DbContract.TABLE_NAME_PATROL, null);
        int count = c.getCount();
        c.close();
        return count;
    }
}
