package com.example.nijkap01.demoapp;

import android.provider.BaseColumns;

/**
 * Created by nijkap01 on 9/11/2017.
 */

public final class UsersInformation {
    private UsersInformation() {}

    /* Inner class that defines the table contents */
    public static class User implements BaseColumns {
        public static final String TABLE_NAME = "Users";
        public static final String COLUMN_NAME_PHONENUMBER = "PhoneNumber";
        public static final String COLUMN_NAME_NAME = "UserName";
        public static final String COLUMN_NAME_DEVICEID = "DeviceID";
    }
}
