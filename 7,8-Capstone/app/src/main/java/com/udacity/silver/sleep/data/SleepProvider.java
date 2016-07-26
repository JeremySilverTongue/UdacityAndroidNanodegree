package com.udacity.silver.sleep.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import timber.log.Timber;

/**
 * Created by silver on 7/25/16.
 */

public class SleepProvider extends ContentProvider {


    static final int SLEEP = 100;
    static final int SLEEP_FOR_DAY = 101;

    static UriMatcher uriMatcher = buildUriMatcher();
    private SleepDbHelper dbHelper;

    static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(SleepContract.AUTHORITY, SleepContract.PATH_SLEEP, SLEEP);
        matcher.addURI(SleepContract.AUTHORITY, SleepContract.PATH_SLEEP_FOR_DATE, SLEEP_FOR_DAY);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new SleepDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor returnCursor;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        switch (uriMatcher.match(uri)) {
            case SLEEP:
                returnCursor = db.query(
                        SleepContract.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case SLEEP_FOR_DAY:
                returnCursor = db.query(
                        SleepContract.TABLE_NAME,
                        projection,
                        SleepContract.COLUMN_DAY + " = ?",
                        new String[]{SleepContract.getDayFromUri(uri)},
                        null,
                        null,
                        sortOrder
                );

                break;
            default:
                throw new UnsupportedOperationException("Unknown URI:" + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        if (db.isOpen()) {
            db.close();
        }

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri returnUri;

        switch (uriMatcher.match(uri)) {
            case SLEEP:
                Timber.d("Inserting: %s", values.toString());
                db.insert(
                        SleepContract.TABLE_NAME,
                        null,
                        values
                );
                returnUri = SleepContract.SLEEP_URI;
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI:" + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        if (db.isOpen()) {
            db.close();
        }

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted;

        if (null == selection) selection = "1";
        switch (uriMatcher.match(uri)) {
            case SLEEP:
                rowsDeleted = db.delete(
                        SleepContract.TABLE_NAME,
                        selection,
                        selectionArgs
                );

                break;

            case SLEEP_FOR_DAY:
                String day = SleepContract.getDayFromUri(uri);
                rowsDeleted = db.delete(
                        SleepContract.TABLE_NAME,
                        "\"?\" = \"?\"",
                        new String[]{day, SleepContract.COLUMN_DAY}
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI:" + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
