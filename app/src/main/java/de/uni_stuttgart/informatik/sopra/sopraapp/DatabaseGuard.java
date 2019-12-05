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
            DbContract.COLUMN_NAME_GUARDID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DbContract.COLUMN_NAME_GUARDPASSWORD + " TEXT," +
            DbContract.COLUMN_NAME_GUARDFORNAME + " TEXT," +
            DbContract.COLUMN_NAME_GUARDSURNAME + " TEXT" + " )";

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
        db.insert(DbContract.TABLE_NAME_GUARD, null, values);
        db.close();
    }

    public Guard getGuard(int position) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + DbContract.TABLE_NAME_GUARD + " order by "
                + DbContract.COLUMN_NAME_GUARDID + " asc limit 1 offset " + position, null);
        c.moveToFirst();
        Guard guard = new Guard();
        addGuardInfos(guard,c);
        c.close();
        return guard;
    }
    private void addGuardInfos(Guard guard, Cursor c){
        guard.setUserPassword(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_GUARDPASSWORD)));
        guard.setForename(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_GUARDFORNAME)));
        guard.setSurname(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_GUARDSURNAME)));
        guard.setUserId(c.getString(c.getColumnIndex(DbContract.COLUMN_NAME_GUARDID)));
    }
    public Guard getGuardById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + DbContract.TABLE_NAME_GUARD + " where "
                + DbContract.COLUMN_NAME_GUARDID + "=" + id, null);
        c.moveToFirst();
        Guard guard = new Guard();
        addGuardInfos(guard,c);
        c.close();
        return guard;
    }

    public ArrayList<Guard> getAllGuards() {
        ArrayList<Guard> guardList = new ArrayList<Guard>();
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
