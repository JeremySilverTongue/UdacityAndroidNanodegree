package com.udacity.silver.sleep.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SleepDbHelper extends SQLiteOpenHelper {

    private static final String NAME = "Sleep.db";
    private static final int VERSION = 1;

    public SleepDbHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tableCreation =
                "CREATE TABLE " + SleepContract.TABLE_NAME + " (" +
                        SleepContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        SleepContract.COLUMN_DAY + " INTEGER NOT NULL, " +
                        SleepContract.COLUMN_SLEEP + " INTEGER NOT NULL, " +
                        SleepContract.COLUMN_WAKE + " INTEGER NOT NULL, " +
                        SleepContract.COLUMN_DURATION + " REAL NOT NULL, " +
                        " UNIQUE (" + SleepContract.COLUMN_DAY + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(tableCreation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + SleepContract.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
