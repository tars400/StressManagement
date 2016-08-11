package com.project.stress.util;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.project.stress.models.History;

import java.util.ArrayList;
import java.util.List;

public class HistorySQLiteHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "HistorySQLiteHelper";

    private Context mContext;

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    public static final String DATABASE_NAME = "DATABASE_HISTORY";
    // Table Name
    public static final String TABLE_NAME = "TABLE_HISTORY";

    private static final String TAG_HISTORY_NAME = "name";
    private static final String TAG_HISTORY_DATE = "date";
    private static final String TAG_HISTORY_CLUSTER = "cluster";


    public HistorySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CHANNEL_TABLE = "CREATE TABLE " + TABLE_NAME + "( "
                + "name TEXT, " + "date TEXT, "
                + "cluster INTEGER " + ")";

        db.execSQL(CREATE_CHANNEL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        // db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);

        // create fresh table
        this.onCreate(db);
    }

    public void addToHistory(History history) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TAG_HISTORY_NAME, history.getName());
        values.put(TAG_HISTORY_DATE, history.getDate());
        values.put(TAG_HISTORY_CLUSTER, history.getCluster());

        db.insert(TABLE_NAME, null, values);
        db.close();

        Log.d(LOG_TAG, "Added item to history!");
    }

    public List<History> getHistory() {
        List<History> allItems = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        History history = null;
        if (cursor.moveToFirst()) {
            do {
                history = new History();

                history.setName(cursor.getString(0));
                history.setDate(cursor.getString(1));
                history.setCluster(cursor.getInt(2));

                allItems.add(history);
            } while (cursor.moveToNext());
        }
        cursor.close();

        Log.d(LOG_TAG, "Fetched history");

        return allItems;
    }

    public void deleteHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
        db.close();
    }

}
