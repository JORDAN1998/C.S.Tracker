package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Calendar;

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
    public void insertRow(String nameEvent, String nameUser, String date, String location, String timeStart, String timeEnd, String timeTotal, String timeTotalAdded, String peopleInCharge, String phoneNumber, String notes, String signature) {

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
        db.insert(DATABASE_TABLE, null, initialValues);
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

    // Delete All Events For Specific User//
    public void deleteAllUserEvents(String workingNameUser) {

        // Gets Rows In Database Based On Name Of User//
        Cursor c = db.rawQuery("SELECT * FROM new_events where nameUser LIKE '%" + workingNameUser + "%'", null);

        // What Happens When c Doesn't Equal Null//
        if (c != null) {

            // Move To First Row//
            c.moveToFirst();
        }

        // What Happens If There Is Another Row//
        while (!c.isAfterLast()) {

            // Define Variable int id / Get Row Id//
            int id = c.getInt(c.getColumnIndex(KEY_ROW_ID_NUMBER));

            // Get Specific Row ID//
            String where = KEY_ROW_ID_NUMBER + "=" + id;

            // Delete Row//
            db.delete(DATABASE_TABLE, where, null);

            // Move To Next Row//
            c.moveToNext();
        }
    }

    // Get All Rows In The Database From Oldest To Newest//
    public Cursor getAllRowsOldestToNewest() {

        // Query Database For All Rows//
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, null, null, null, null, null, null);

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

    // Get All Community Service Times//
    public void totalTimeAdded(String workingNameUser) {

        // Set Total Time Added To Zero//
        totalTimeAdded = 0;

        // Gets Rows In Database Based On Name Of User//
        Cursor c = db.rawQuery("SELECT * FROM new_events where nameUser LIKE '%" + workingNameUser + "%'", null);

        // What Happens When c Doesn't Equal Null//
        if (c != null) {

            // Move To First Row//
            c.moveToFirst();
        }

        // What Happens If There Is Another Row//
        while (!c.isAfterLast()) {

            // Get Total Time Added//
            totalTimeAdded  = totalTimeAdded + c.getInt(COL_TIME_TOTAL_ADDED);

            // Move To Next Row//
            c.moveToNext();
        }
    }

    // Method That Backup All Events//
    public void backupEvents(Context context) {

        //<editor-fold desc="Date Time">

        // Define And Instantiate Variable Calendar calendar//
        Calendar calendar = Calendar.getInstance();

        // Define And Instantiate Variable int month//
        int month = calendar.get(Calendar.MONTH) + 1;

        // Define And Instantiate Variable int day//
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Define And Instantiate Variable int year//
        int year = calendar.get(Calendar.YEAR);

        // Define And Instantiate Variable int hour//
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        // Define And Instantiate Variable int minute//
        int minute = calendar.get(Calendar.MINUTE);

        // Define And Instantiate Variable int second//
        int second = calendar.get(Calendar.SECOND);

        // Define Variable String zeroHandle//
        String zeroHandle;

        //</editor-fold>

        // What Happens When Minute Is Less Than Ten//
        if (minute < 10) {

            // Adds Zero Handle Before Single Digit//
            zeroHandle = "0" + minute;
        }

        // What Happens When Minute Is Greater Than Ten//
        else {

            // Adds No Zero Handle To Minute//
            zeroHandle = String.valueOf(minute);
        }

        // Attempt To Backup The File//
        try {

            // What Happens When App Can Write To The SD Card//
            if (Environment.getExternalStorageDirectory().canWrite()) {

                // Define And Instantiate Variable File localFolder / Get localFolder File Location//
                File localFolder = new File(Environment.getExternalStorageDirectory() + "/C.S. Tracker/Events Backup/");

                // Define And Instantiate Variable File mainDatabase / Get mainDatabase File Location//
                File mainDatabase = new File(Environment.getDataDirectory() + "//data//jordanzimmittidevelopers.com.communityservicelogger//databases//events_database");

                // What Happens When There is a File In The localFolder//
                if (!(localFolder.listFiles().length == 0)) {

                    // List All The Files//
                    String[] children = localFolder.list();

                    // Delete All Files//
                    for (String aChildren : children) {

                        // Delete File//
                        new File(Environment.getExternalStorageDirectory() + "/C.S. Tracker/Events Backup/", aChildren).delete();
                    }
                }

                // What Happens When mainDatabase Exists//
                if (mainDatabase.exists()) {

                    // Define And Instantiate Variable FileChannel mainDatabaseDirectory//
                    FileChannel mainDatabaseDirectory = new FileInputStream(mainDatabase).getChannel();

                    // Define And Instantiate Variable FileChannel backupDatabaseDirectory//
                    FileChannel backupDatabaseDirectory = new FileOutputStream(Environment.getExternalStorageDirectory() + "/C.S. Tracker/Events Backup/" + "Events.db" + " " + month + "-" + day + "-" + year + " " + hour + ":" + zeroHandle + ":" + second + " ").getChannel();

                    // Transfer Data From mainDatabaseDirectory To backupDatabaseDirectory//
                    backupDatabaseDirectory.transferFrom(mainDatabaseDirectory, 0, mainDatabaseDirectory.size());

                    // Close Src Transfer//
                    mainDatabaseDirectory.close();

                    // Close Dest Transfer//
                    backupDatabaseDirectory.close();
                }
            }
        }

        // What Happens When The Code Fails//
        catch (Exception ignored) {Toast.makeText(context, "Failed To Backup: You Must Have At Least One Event To Backup Database To Drive", Toast.LENGTH_SHORT).show();}
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
