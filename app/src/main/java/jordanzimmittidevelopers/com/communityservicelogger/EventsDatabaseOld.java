package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EventsDatabaseOld {

    // Used For Logging Database Version Changes//
	private static final String TAG = "Database";
			
	// Row Names//
	public static final String KEY_ROW_ID_NUMBER = "_id";
    public static final String KEY_NAME = "name";
	public static final String KEY_DATE = "date";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_STARTTIME = "starttime";
    public static final String KEY_ENDTIME = "endtime";
    public static final String KEY_TOTALTIME = "totaltime";
    public static final String KEY_TOTALTIME_ADDED = "totaltime_added";
    public static final String KEY_DATE_EVENT_ADDED = "date_event_added";
    public static final String KEY_SIGNATURE = "signature";

    // Put All Rows Into A String//
	public static final String[] ALL_KEYS = new String[] {KEY_ROW_ID_NUMBER, KEY_NAME, KEY_DATE, KEY_LOCATION, KEY_STARTTIME, KEY_ENDTIME, KEY_TOTALTIME, KEY_TOTALTIME_ADDED, KEY_DATE_EVENT_ADDED};
    public static final String[] CSV_EXPORT = new String[] {KEY_NAME, KEY_DATE, KEY_LOCATION, KEY_STARTTIME, KEY_ENDTIME, KEY_TOTALTIME, KEY_SIGNATURE};
	
	// Column Numbers For Each Row Name//
	public static final int COL_NAME = 1;
	public static final int COL_DATE = 2;
    public static final int COL_LOCATION = 3;
    public static final int COL_STARTTIME = 4;
    public static final int COL_ENDTIME = 5;
    public static final int COL_TOTALTIME = 6;
    public static final int COL_TOTALTIME_ADDED = 7;

    // DataBase info//
    public static final String DATABASE_NAME = "community_service_Database";
    public static final String DATABASE_TABLE = "events";
    public static final int DATABASE_VERSION = 2; // The version number must be incremented each time a change to DB structure occurs.

	//SQL Statement To Create Database//
	private static final String DATABASE_CREATE_SQL
            = "CREATE TABLE " + DATABASE_TABLE
			+ " (" + KEY_ROW_ID_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_NAME + " TEXT NOT NULL,"
            + KEY_DATE + " TEXT NOT NULL,"
            + KEY_LOCATION + " TEXT NOT NULL,"
            + KEY_STARTTIME + " TEXT NOT NULL,"
            + KEY_ENDTIME + " TEXT NOT NULL,"
            + KEY_TOTALTIME + " TEXT NOT NULL,"
            + KEY_TOTALTIME_ADDED + " TEXT NOT NULL,"
            + KEY_DATE_EVENT_ADDED + " TEXT NOT NULL,"
            + KEY_SIGNATURE + " TEXT NOT NULL"
			+ ");";

    // Define Variable DatabaseHelper db_helper//
    public static DatabaseHelper db_helper;

    // Define Variable SQLiteDatabase db//
    public static SQLiteDatabase db;

    // Integer TotalTimeAdded/
    public static int TotalTimeAdded;

    // Call Upon Database Helper//
	public EventsDatabaseOld(Context ctx) {

        // Link DB_Helper To Database_Helper//
        db_helper = new DatabaseHelper(ctx);
	}
	
	// Open The Database Connection//
	public EventsDatabaseOld open() {

        // Call DatabaseHelper To Open Database//
        db = db_helper.getWritableDatabase();
		return this;
	}
	
	// Close The Database Connection//
	public void close() {

        // Calls DatabaseHelper To Close Database//
        db_helper.close();
	}
	
	// Add A New Set Of Values To Be Inserted Into The Database//
	public long insertRow (String name, String date, String location, String starttime, String endtime, String totaltime, String totaltime_added, String signature, String date_event_added) {

        // Gets All The New Values//
        ContentValues initialValues = new ContentValues();

        // ALl New Values Being Added//
		initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_DATE, date);
        initialValues.put(KEY_LOCATION, location);
        initialValues.put(KEY_STARTTIME, starttime);
        initialValues.put(KEY_ENDTIME, endtime);
        initialValues.put(KEY_TOTALTIME, totaltime);
        initialValues.put(KEY_TOTALTIME_ADDED, totaltime_added);
        initialValues.put(KEY_SIGNATURE, signature);
        initialValues.put(KEY_DATE_EVENT_ADDED, date_event_added);

        // Inserts The Value Data Into The Database//
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

    // Change an existing row to be equal to new data.
    public boolean updateRow(String rowId, String name, String date, String location, String starttime, String endtime, String totaltime, String totaltime_added, String signature, String date_event_added) {

        // Get Current Row By ID Number//
        String where = KEY_ROW_ID_NUMBER + "=" + rowId;

        // Gets New Values For Row//
        ContentValues newValues = new ContentValues();

        // Add New Values
        newValues.put(KEY_NAME, name);
        newValues.put(KEY_NAME, name);
        newValues.put(KEY_DATE, date);
        newValues.put(KEY_LOCATION, location);
        newValues.put(KEY_STARTTIME, starttime);
        newValues.put(KEY_ENDTIME, endtime);
        newValues.put(KEY_TOTALTIME, totaltime);
        newValues.put(KEY_TOTALTIME_ADDED, totaltime_added);
        newValues.put(KEY_SIGNATURE, signature);
        newValues.put(KEY_DATE_EVENT_ADDED, date_event_added);

        // Inserts The New Value Data Into The Database//
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }

    // Delete a row from the database, by rowId (primary key)//
	public boolean deleteRow(long rowId) {

        // Get Specific Row ID//
        String where = KEY_ROW_ID_NUMBER + "=" + rowId;

        // Delete Row//
		return db.delete(DATABASE_TABLE, where, null) != 0;
	}

    // Delete Everything From Database//
	public void deleteAll() {

        // Get All Rows//
		Cursor c = getAllRows();

        // Delete Row One By One//
		long rowId = c.getColumnIndexOrThrow(KEY_ROW_ID_NUMBER);
		if (c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId));				
			} while (c.moveToNext());
		}
		c.close();
	}
	
	// Return all data in the database//
	public Cursor getAllRows() {
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, null, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

    // Return all data in the database Ordered By Name//
    public Cursor getAllRowsByName() {
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, null, null, null, null, KEY_NAME + " COLLATE NOCASE" + " ASC", null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Return all data in the database Ordered By Date//
    public Cursor getAllRowsByDate() {
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, null, null, null, null, KEY_DATE + " COLLATE NOCASE" + " ASC", null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Return all data in the database Odored By Location//
    public Cursor getAllRowsByLocation() {
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, null, null, null, null, KEY_LOCATION + " COLLATE NOCASE" + " ASC", null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

	// Get a specific row (by rowId)
	public Cursor getRow(String rowId) {
		String where = KEY_ROW_ID_NUMBER + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

    // Get All Community Service Times//
    public Cursor total_time_added() {

        // Set Total Time Added To Zero//
        TotalTimeAdded = 0;

        // Query Through All Rows//
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, null, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        assert c != null;
        while (!c.isAfterLast()) {

            // Get Total Time Added//
            TotalTimeAdded  = TotalTimeAdded + c.getInt(COL_TOTALTIME_ADDED);
            c.moveToNext();
        }

        return c;
    }

    // Helps Make Database Work (Remember Don't Touch)//
	private static class DatabaseHelper extends SQLiteOpenHelper{
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

