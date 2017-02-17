package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// EventsDatabase Class Created By Jordan Zimmitti 1-30-17//
public class EventsDatabase {

    // Used For Logging Database Version Changes//
    private static final String TAG = "database";

    // Row Names//
    public static final String KEY_ROW_ID_NUMBER = "_id";
    public static final String KEY_NAME_USER = "nameUser";
    public static final String KEY_NAME_EVENT = "nameEvent";
    public static final String KEY_DATE = "date";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_TIME_START = "timeStart";
    public static final String KEY_TIME_END = "timeEnd";
    public static final String KEY_TIME_TOTAL = "timeTotal";
    public static final String KEY_TIME_TOTAL_ADDED = "timeTotalAdded";
    public static final String KEY_PEOPLE_IN_CHARGE = "peopleInCharge";
    public static final String KEY_PHONE_NUMBER = "phoneNumber";
    public static final String KEY_NOTES = "notes";
    public static final String KEY_SIGNATURE = "signature";

    // Put All Rows Into A String//
    public static final String[] ALL_KEYS = new String[] {KEY_ROW_ID_NUMBER, KEY_NAME_USER, KEY_NAME_EVENT, KEY_DATE, KEY_LOCATION, KEY_TIME_START, KEY_TIME_END, KEY_TIME_TOTAL, KEY_TIME_TOTAL_ADDED, KEY_PEOPLE_IN_CHARGE, KEY_PHONE_NUMBER, KEY_NOTES};

    public static final String[] CSV_EXPORT = new String[] {KEY_NAME_EVENT, KEY_DATE, KEY_LOCATION, KEY_TIME_START, KEY_TIME_END, KEY_TIME_TOTAL, KEY_PHONE_NUMBER, KEY_SIGNATURE};

    // Column Numbers For Each Row Name//
    public static final int COL_NAME_USER = 1;
    public static final int COL_NAME_EVENT = 2;
    public static final int COL_DATE = 3;
    public static final int COL_LOCATION = 4;
    public static final int COL_TIME_START = 5;
    public static final int COL_TIME_END = 6;
    public static final int COL_TIME_TOTAL = 7;
    public static final int COL_TIME_TOTAL_ADDED = 8;
    public static final int COL_PEOPLE_IN_CHARGE = 9;
    public static final int COL_PHONE_NUMBER = 10;
    public static final int COL_NOTES = 11;
    public static final int COL_SIGNATURE = 12;

    // DataBase info//
    public static final String DATABASE_NAME = "events_database";
    public static final String DATABASE_TABLE = "new_events";
    public static final int DATABASE_VERSION = 1; // The version number must be incremented each time a change to DB structure occurs.

    //SQL Statement To Create Database//
    private static final String DATABASE_CREATE_SQL
            = "CREATE TABLE " + DATABASE_TABLE
            + " (" + KEY_ROW_ID_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME_USER + " TEXT NOT NULL,"
            + KEY_NAME_EVENT + " TEXT NOT NULL,"
            + KEY_DATE + " TEXT NOT NULL,"
            + KEY_LOCATION + " TEXT NOT NULL,"
            + KEY_TIME_START + " TEXT NOT NULL,"
            + KEY_TIME_END + " TEXT NOT NULL,"
            + KEY_TIME_TOTAL + " TEXT NOT NULL,"
            + KEY_TIME_TOTAL_ADDED + " TEXT NOT NULL,"
            + KEY_PEOPLE_IN_CHARGE + " TEXT NOT NULL,"
            + KEY_PHONE_NUMBER + " TEXT NOT NULL,"
            + KEY_NOTES + " TEXT NOT NULL,"
            + KEY_SIGNATURE + " TEXT NOT NULL"
            + ");";

    // Define Variable DatabaseHelper dbHelper//
    public static DatabaseHelper dbHelper;

    // Define Variable SQLiteDatabase db//
    public static SQLiteDatabase db;

    // Define Variable int totalTimeAdded//
    public static int totalTimeAdded;

    // Call Upon Database Helper//
    public EventsDatabase(Context ctx) {

        // Link dbHelper To DatabaseHelper//
        dbHelper = new EventsDatabase.DatabaseHelper(ctx);
    }

    // Open The Database Connection//
    public EventsDatabase open() {

        // Call DatabaseHelper To Open Database//
        db = dbHelper.getWritableDatabase();

        // Kill Code//
        return this;
    }

    // Close The Database Connection//
    public void close() {

        // Calls DatabaseHelper To Close Database//
        dbHelper.close();
    }

    // Add A New Set Of Values To Be Inserted Into The Database//
    public long insertRow(String nameEvent, String nameUser, String date, String location, String timeStart, String timeEnd, String timeTotal, String timeTotalAdded, String peopleInCharge, String phoneNumber, String notes, String signature) {

        // Gets All The New Values//
        ContentValues initialValues = new ContentValues();

        // ALl New Values Being Added//
        initialValues.put(KEY_NAME_EVENT, nameEvent);
        initialValues.put(KEY_NAME_USER, nameUser);
        initialValues.put(KEY_DATE, date);
        initialValues.put(KEY_LOCATION, location);
        initialValues.put(KEY_TIME_START, timeStart);
        initialValues.put(KEY_TIME_END, timeEnd);
        initialValues.put(KEY_TIME_TOTAL, timeTotal);
        initialValues.put(KEY_TIME_TOTAL_ADDED, timeTotalAdded);
        initialValues.put(KEY_PEOPLE_IN_CHARGE, peopleInCharge);
        initialValues.put(KEY_PHONE_NUMBER, phoneNumber);
        initialValues.put(KEY_NOTES, notes);
        initialValues.put(KEY_SIGNATURE, signature);

        // Inserts The Value Data Into The Database//
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    // Change An Existing Row To Be Equal To New Data//
    public boolean updateRow(String id, String nameEvent, String nameUser, String date, String location, String timeStart, String timeEnd, String timeTotal, String timeTotalAdded, String peopleInCharge, String phoneNumber, String notes, String signature) {

        // Get Current Row By ID Number//
        String where = KEY_ROW_ID_NUMBER + "=" + id;

        // Gets New Values For Row//
        ContentValues newValues = new ContentValues();

        // Add New Values
        newValues.put(KEY_NAME_EVENT, nameEvent);
        newValues.put(KEY_NAME_USER, nameUser);
        newValues.put(KEY_DATE, date);
        newValues.put(KEY_LOCATION, location);
        newValues.put(KEY_TIME_START, timeStart);
        newValues.put(KEY_TIME_END, timeEnd);
        newValues.put(KEY_TIME_TOTAL, timeTotal);
        newValues.put(KEY_TIME_TOTAL_ADDED, timeTotalAdded);
        newValues.put(KEY_PEOPLE_IN_CHARGE, peopleInCharge);
        newValues.put(KEY_PHONE_NUMBER, phoneNumber);
        newValues.put(KEY_NOTES, notes);
        newValues.put(KEY_SIGNATURE, signature);

        // Inserts The New Value Data Into The Database//
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }

    // Delete A Row From The Database By Id //
    public boolean deleteRow(long id) {

        // Get Specific Row ID//
        String where = KEY_ROW_ID_NUMBER + "=" + id;

        // Delete Row//
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    // Delete Everything From Database//
    public void deleteAll() {

        // Get All Rows//
        Cursor c = getAllRowsOldestToNewest();

        // Delete Row One By One//
        long rowId = c.getColumnIndexOrThrow(KEY_ROW_ID_NUMBER);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }

        // Close Cursor//
        c.close();
    }

    // Get All Rows In The Database From Oldest To Newest//
    public Cursor getAllRowsOldestToNewest() {

        // Query Database For All Rows//
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, null, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }

        // Kill Code//
        return c;
    }

    // Get A Specific Row By Id//
    public Cursor getRow(String Id) {

        // Get Row Id//
        String where = KEY_ROW_ID_NUMBER + "=" + Id;

        // Query Row//
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }

    // Get All Community Service Times//
    public Cursor totalTimeAdded() {

        // Set Total Time Added To Zero//
        totalTimeAdded = 0;

        // Query Through All Rows//
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, null, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        assert c != null;
        while (!c.isAfterLast()) {

            // Get Total Time Added//
            totalTimeAdded  = totalTimeAdded + c.getInt(COL_TIME_TOTAL_ADDED);
            c.moveToNext();
        }

        // Kill Code//
        return c;
    }

    // Helps Make Database Work (Remember Don't Touch)//
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

            // Recreate new database:
            onCreate(_db);
        }
    }
}
