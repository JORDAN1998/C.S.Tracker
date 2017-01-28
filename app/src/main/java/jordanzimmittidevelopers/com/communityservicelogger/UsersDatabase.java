package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

// UsersDatabase Class Created By Jordan Zimmitti 1-22-17//
public class UsersDatabase {

    // Used For Logging Database Version Changes//
    private static final String TAG = "database";

    // Row Names//
    public static final String KEY_ROW_ID_NUMBER = "_id";
    public static final String KEY_NAMES = "names";
    public static final String KEY_AGE = "age";
    public static final String KEY_ORGANIZATION = "name";
    public static final String KEY_NAME_LETTER = "name_letter";
    public static final String KEY_IMAGE = "image";

    // Put All Rows Into A String//
    public static final String[] ALL_KEYS = new String[] { KEY_ROW_ID_NUMBER, KEY_NAMES, KEY_AGE, KEY_ORGANIZATION, KEY_NAME_LETTER, KEY_IMAGE};

    // Column Numbers For Each Row Name//
    public static final int COL_NAME = 1;
    public static final int COL_AGE = 2;
    public static final int COL_ORGANIZATION = 3;
    public static final int COL_NAME_LETTER = 4;
    public static final int COL_IMAGE = 5;

    // DataBase info//
    public static final String DATABASE_NAME = "users_database";
    public static final String DATABASE_TABLE = "users";
    public static final int DATABASE_VERSION = 1; // The version number must be incremented each time a change to DB structure occurs.

    //SQL Statement To Create Database//
    private static final String DATABASE_CREATE_SQL = "CREATE TABLE " + DATABASE_TABLE
            + " (" + KEY_ROW_ID_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAMES + " TEXT NOT NULL,"
            + KEY_AGE + " TEXT NOT NULL,"
            + KEY_ORGANIZATION + " TEXT NOT NULL,"
            + KEY_NAME_LETTER + " TEXT NOT NULL,"
            + KEY_IMAGE + " TEXT NOT NULL"
            + ");";


    // Define Variable DatabaseHelper dbHelper//
    public static DatabaseHelper dbHelper;

    // Define Variable SQLiteDatabase db//
    public static SQLiteDatabase db;

    // Call Upon Database Helper//
    public UsersDatabase(Context ctx) {

        // Link dbHelper To DatabaseHelper//
        dbHelper = new DatabaseHelper(ctx);
    }

    // Open The Database Connection//
    public UsersDatabase open() {

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
    public long insertRow(String names, String age, String organization, String nameLetter, byte[] image) {

        // Gets All The New Values//
        ContentValues initialValues = new ContentValues();

        // ALl New Values Being Added//
        initialValues.put(KEY_NAMES, names);
        initialValues.put(KEY_AGE, age);
        initialValues.put(KEY_ORGANIZATION, organization);
        initialValues.put(KEY_NAME_LETTER, nameLetter);
        initialValues.put(KEY_IMAGE, image);

        // Inserts The Value Data Into The Database//
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    // Change An Existing Row To Be Equal To New Data//
    public boolean updateRow(long id, String names, String age, String organization, String nameLetter, byte[] image) {

        // Get Current Row By ID Number//
        String where = KEY_ROW_ID_NUMBER + "=" + id;

        // Gets New Values For Row//
        ContentValues newValues = new ContentValues();

        // Add New Values
        newValues.put(KEY_NAMES, names);
        newValues.put(KEY_AGE, age);
        newValues.put(KEY_ORGANIZATION, organization);
        newValues.put(KEY_NAME_LETTER, nameLetter);
        newValues.put(KEY_IMAGE, image);

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
        Cursor c = getAllRows();

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

    // Get All Rows In The Database//
    public Cursor getAllRows() {

        // Query Database For All Rows//
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, null, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }

        // Kill Code//
        return c;
    }

    // Get A Specific Row By Id//
    public Cursor getRow(long Id) {

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

    // Convert From Bitmap To Byte Array//
    public static byte[] getBytes(Bitmap bitmap) {

        // Define And Instantiate Variable ByteArrayOutputStream stream//
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        // Convert Bitmap To Byte//
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);

        // Kill Code//
        return stream.toByteArray();
    }

    // Convert From Byte Array To Bitmap//
    public void getImage(Context context, final byte[] image, final CircleImageView circleImageView) {

        // Get Image From Database And Show It In ListView//
        Glide.with(context).load(image).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(circleImageView);
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
