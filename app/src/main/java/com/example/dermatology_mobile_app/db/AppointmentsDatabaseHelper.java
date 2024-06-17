package com.example.dermatology_mobile_app.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppointmentsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "appointments.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_APPOINTMENTS = "appointments";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SERVICE = "service";
    public static final String COLUMN_COST = "cost";

    public AppointmentsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_APPOINTMENTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SERVICE + " TEXT NOT NULL UNIQUE, " +
                COLUMN_COST + " INTEGER NOT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_APPOINTMENTS + " ADD COLUMN " + COLUMN_COST + " INTEGER DEFAULT 300;");
        }
    }

    public long addAppointment(String service, int cost) {
        if (!isAppointmentExists(service)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_SERVICE, service);
            values.put(COLUMN_COST, cost); // adding cost
            return db.insert(TABLE_APPOINTMENTS, null, values);
        } else {
            return -1; // Indicate that the appointment already exists
        }
    }

    @SuppressLint("Range")
    public int getServiceCost(String service) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_APPOINTMENTS,
                new String[]{COLUMN_COST},
                COLUMN_SERVICE + " = ?",
                new String[]{service},
                null, null, null);

        int cost = 0;
        if (cursor.moveToFirst()) {
            cost = cursor.getInt(cursor.getColumnIndex(COLUMN_COST));
        }
        cursor.close();
        return cost;
    }
    public boolean isAppointmentExists(String service) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_APPOINTMENTS,
                new String[]{COLUMN_SERVICE},
                COLUMN_SERVICE + " = ?",
                new String[]{service},
                null, null, null);

        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public Cursor getAllAppointments() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_APPOINTMENTS, null, null, null, null, null, null);
    }

    public void deleteAllAppointments() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_APPOINTMENTS, null, null);
    }

    public void deleteAppointment(String service) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_APPOINTMENTS, COLUMN_SERVICE + " = ?", new String[]{service});
    }
}