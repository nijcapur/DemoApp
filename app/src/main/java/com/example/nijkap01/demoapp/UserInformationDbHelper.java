package com.example.nijkap01.demoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nijkap01 on 9/11/2017.
 */

public class UserInformationDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UserInfo.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UsersInformation.User.TABLE_NAME + " (" +
                    UsersInformation.User._ID + " INTEGER PRIMARY KEY," +
                    UsersInformation.User.COLUMN_NAME_PHONENUMBER + " TEXT," +
                    UsersInformation.User.COLUMN_NAME_DEVICEID + " TEXT," +
                    UsersInformation.User.COLUMN_NAME_NAME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UsersInformation.User.TABLE_NAME;

    public UserInformationDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
