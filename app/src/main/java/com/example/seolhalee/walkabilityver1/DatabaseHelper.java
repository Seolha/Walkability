package com.example.seolhalee.walkabilityver1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Seolha Lee on 2018-02-08.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    //declare variables
    public static final String DATABASE_NAME = "Location.db";
    public static final String TABLE_NAME = "Location_table";
    public static final String col_1 = "ID";
    public static final String col_2 = "SPEED";
    public static final String col_3 = "LONGITUDE";
    public static final String col_4 = "LATITUDE";
    public static final String col_5 = "DATETIME";
    public static final String col_6 = "CROSSWALK";


    //default constructor. database created with this.
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    //what happens to this database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, SPEED FLOAT, LONGITUDE DOUBLE, LATITUDE DOUBLE, DATETIME DOUBLE,CROSSWALK BOOLEAN)"); //execute a credit(string) I pass inside the db
        //autoincrement was deleted.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS  " + TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData (float speed, double lon, double lat, double time){
        SQLiteDatabase db = this.getWritableDatabase(); //check database creation
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2, speed);
        contentValues.put(col_3, lon);
        contentValues.put(col_4, lat);
        contentValues.put(col_5, time);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
}
