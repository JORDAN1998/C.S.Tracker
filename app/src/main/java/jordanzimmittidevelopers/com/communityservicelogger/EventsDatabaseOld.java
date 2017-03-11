package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
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

