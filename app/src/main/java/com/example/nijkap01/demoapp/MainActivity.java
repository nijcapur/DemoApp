package com.example.nijkap01.demoapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by nijkap01 on 9/11/2017.
 */

public class MainActivity extends Activity implements View.OnClickListener {
    String deviceId="";
    UserInformationDbHelper mDbHelper=null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        mDbHelper = new UserInformationDbHelper(this);
        deviceId= android.os.Build.ID;
       Cursor cursor = ReadFromDatabase(mDbHelper,deviceId);
        if(cursor.getCount() > 0)
            setContentView(R.layout.dashboard_activity);
        else {
            setContentView(R.layout.registration_activity);
            final Button btn = (Button) findViewById(R.id.btn_link_signup);
             btn.setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View v) {
        UserInformationDbHelper mDbHelper = new UserInformationDbHelper(this);
        final EditText phoneNumber = (EditText) findViewById(R.id.login_input_PhoneNumber);
        final EditText userName = (EditText) findViewById(R.id.login_input_PhoneNumber);
        InsertUserInfo(mDbHelper,phoneNumber.getText().toString(),deviceId,userName.getText().toString());
        setContentView(R.layout.dashboard_activity);
    }
    private void InsertUserInfo(UserInformationDbHelper mDbHelper,String phoneNumber,String deviceId,String userName)
    {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(UsersInformation.User.COLUMN_NAME_PHONENUMBER, phoneNumber);
        values.put(UsersInformation.User.COLUMN_NAME_DEVICEID, deviceId);
        values.put(UsersInformation.User.COLUMN_NAME_NAME, userName);
// Which row to update, based on the title


        db.insert(UsersInformation.User.TABLE_NAME, null, values);
    }
    private Cursor ReadFromDatabase(UserInformationDbHelper mDbHelper,String deviceId)
    {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                UsersInformation.User._ID,
                UsersInformation.User.COLUMN_NAME_PHONENUMBER,
                UsersInformation.User.COLUMN_NAME_DEVICEID
        };

// Filter results WHERE "title" = 'My Title'
        String selection = UsersInformation.User.COLUMN_NAME_PHONENUMBER + " = ?";
        String[] selectionArgs = { deviceId };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                UsersInformation.User.COLUMN_NAME_PHONENUMBER + " DESC";

        Cursor cursor = db.query(
                UsersInformation.User.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        return cursor;
    }
}
