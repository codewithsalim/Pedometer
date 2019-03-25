package com.example.saleemshaikh.walk;

/**
 * Created by saleemshaikh on 24/03/19.
 */



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    private static DatabaseHandler sInstance;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserData";
    private static final String TABLE_STEPS = "steps";
    private static final String KEY_DATE = "date";
    private static final String KEY_COUNT = "count";



    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance  
    }

    public static synchronized DatabaseHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.

        if (sInstance == null) {
            sInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    // Creating Tables  
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_STEPS + "("
                + KEY_DATE + " STRING PRIMARY KEY," + KEY_COUNT + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database  
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed  
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STEPS);

        // Create tables again  
        onCreate(db);
    }

    // code to add the new contact  
    void add(StepData obj) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, obj.getDate()); // Date
        values.put(KEY_COUNT, obj.getSteps()); // Step count

        // Inserting Row  
        db.insert(TABLE_STEPS, null, values);
        //2nd argument is String containing nullColumnHack  
        db.close(); // Closing database connection  
    }

    // code to get the single row  
    StepData getStepData(String date) {

        Log.d("Date", "Reading function called");
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STEPS, new String[] { KEY_DATE,
                        KEY_COUNT }, KEY_DATE + "=?",
                new String[] { date }, null, null, null, null);

        int cursor_count =  cursor.getCount();
        Log.d("Date", "cursor count is "+String.valueOf(cursor_count));
        if (cursor_count == 0 ){
            Log.d("Date", "Returning null");
            return null;
        }


        cursor.moveToFirst();

        StepData obj = new StepData(cursor.getString(0), Integer.parseInt(cursor.getString(1)));
            // return object
        return obj;


    }

    // code to get all tuples in a list view
    public List<StepData> getAllStepData() {
        List<StepData> List = new ArrayList<StepData>();
        // Select All Query  
        String selectQuery = "SELECT  * FROM " + TABLE_STEPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list  
        if (cursor.moveToFirst()) {
            do {
                StepData obj = new StepData();
                obj.setDate(cursor.getString(0));
                obj.setSteps(Integer.parseInt(cursor.getString(1)));

                // Adding contact to list  
                List.add(obj);
            } while (cursor.moveToNext());
        }

        // return contact list  
        return List;
    }

    // code to update the single tuple
    public int updateStepData(StepData obj) {
        Log.d("Date","Update is called");
        Log.d("Date", "Before update steps = "+String.valueOf(obj.getSteps()));
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COUNT, obj.getSteps());


        // updating row  
        return db.update(TABLE_STEPS, values, KEY_DATE + " = ?",
                new String[] { obj.getDate() });
    }

 

}  