package com.udacity.silver.stockhawk.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.udacity.silver.stockhawk.data.Contract.Quote;


public class DbHelper extends SQLiteOpenHelper {


    static final String NAME = "StockHawk.db";
    private static final int VERSION = 1;


    public DbHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE ").append(Quote.TABLE_NAME).append(" (");
        builder.append(Quote._ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        builder.append(Quote.COLUMN_SYMBOL).append(" TEXT NOT NULL, ");
        builder.append(Quote.COLUMN_PRICE).append(" REAL NOT NULL, ");
        builder.append(Quote.COLUMN_ABSOLUTE_CHANGE).append(" REAL NOT NULL, ");
        builder.append(Quote.COLUMN_PERCENTAGE_CHANGE).append(" REAL NOT NULL, ");
        builder.append(Quote.COLUMN_HISTORY).append(" TEXT NOT NULL, ");

        builder.append("UNIQUE (").append(Quote.COLUMN_SYMBOL).append(") ON CONFLICT REPLACE);");

        db.execSQL(builder.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + Quote.TABLE_NAME);

        onCreate(db);
    }
}
