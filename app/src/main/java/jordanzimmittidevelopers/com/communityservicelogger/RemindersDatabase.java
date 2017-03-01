package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// EventsDatabase Class Created By Jordan Zimmitti 1-30-17//
public class RemindersDatabase {

    // Used For Logging Database Version Changes//
    private static final String TAG = "database";

    // Row Names//
    public static final String KEY_ROW_ID_NUMBER = "_id";
    public static final String KEY_NAME_REMINDER = "nameReminder";
    public static final String KEY_DATE = "date";
    public static final String KEY_LOCATION = "location";

    // Put All Rows Into A String//
    public static final String[] ALL_KEYS = new String[] {KEY_ROW_ID_NUMBER, KEY_NAME_REMINDER, KEY_DATE, KEY_LOCATION};


    // Column Numbers For Each Row Name//
    public static final int COL_NAME_REMINDER = 1;
    public static final int COL_DATE = 2;
    public static final int COL_LOCATION = 3;

    // DataBase info//
    public static final String DATABASE_NAME = "reminders_database";
    public static final String DATABASE_TABLE = "reminders";
    public static final int DATABASE_VERSION = 1; // The version number must be incremented each time a change to DB structure occurs.

    //SQL Statement To Create Database//
    private static final String DATABASE_CREATE_SQL
            = "CREATE TABLE " + DATABASE_TABLE
            + " (" + KEY_ROW_ID_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME_REMINDER + " TEXT NOT NULL,"
            + KEY_DATE + " TEXT NOT NULL,"
            + KEY_LOCATION + " TEXT NOT NULL"
            + ");";

    // Define Variable DatabaseHelper dbHelper//
    public static DatabaseHelper dbHelper;

    // Define Variable SQLiteDatabase db//
    public static SQLiteDatabase db;

    // Call Upon Database Helper//
    public RemindersDatabase(Context ctx) {

        // Link dbHelper To DatabaseHelper//
        dbHelper = new RemindersDatabase.DatabaseHelper(ctx);
    }

    // Open The Database Connection//
    public RemindersDatabase open() {

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
    public long insertRow(String nameReminder, String date, String location) {

        // Gets All The New Values//
        ContentValues initialValues = new ContentValues();

        // ALl New Values Being Added//
        initialValues.put(KEY_NAME_REMINDER, nameReminder);
        initialValues.put(KEY_DATE, date);
        initialValues.put(KEY_LOCATION, location);

        // Inserts The Value Data Into The Database//
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    // Change An Existing Row To Be Equal To New Data//
    public boolean updateRow(String id, String nameReminder, String date, String location) {

        // Get Current Row By ID Number//
        String where = KEY_ROW_ID_NUMBER + "=" + id;

        // Gets New Values For Row//
        ContentValues newValues = new ContentValues();

        // Add New Values
        newValues.put(KEY_NAME_REMINDER, nameReminder);
        newValues.put(KEY_DATE, date);
        newValues.put(KEY_LOCATION, location);

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
        Cursor c = getAllRowsDate();

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
    public Cursor getAllRowsDate() {

        // Query Database For All Rows//
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, null, null, null, null, RemindersDatabase.KEY_DATE + " ASC", null);

        // What Happens When c Doesn't Equal Null//
        if (c != null) {

            // Move To First Row//
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
