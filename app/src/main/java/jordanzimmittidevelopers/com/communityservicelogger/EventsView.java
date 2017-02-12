package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

// EventsView Class Created By Jordan Zimmitti 1-29-17//
public class EventsView extends AppCompatActivity {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Define Variable Cursor cursor//
    Cursor cursor;

    // Define Variable EventsDatabase eventsDatabase//
    private EventsDatabase eventsDatabase;

    // Define Variable EventsDatabaseOld eventsDatabaseOld//
    private EventsDatabaseOld eventsDatabaseOld;

    // Define Variable Vibrator vibe//
    private Vibrator vibe;

    //</editor-fold>

    //<editor-fold desc="String">

    // Puts Name Of Last Clicked ListView Item Into String//
    public static final String EVENTS_ADD_NAME_USER = null;

    // Define Variable String eventAddNameUser / String Of Name Value//
    private String eventAddNameUser = null;

    // Puts Id Of Last Clicked ListView Item Into String//
    public final static String KEY_ROW_ID_NUMBER = EventsDatabase.KEY_ROW_ID_NUMBER;

    // Puts Name Of Last Clicked ListView Item Into String//
    public static final String USERS_VIEW_NAME_USER = null;

    // Define Variable String usersViewNameUser / String Of Name Value//
    private String usersViewNameUser = null;

    //</editor-fold>

    //<editor-fold desc="Widgets">

    // Define Variable ListView eventsListViews//
    private ListView eventsListView;

    //</editor-fold>

    //</editor-fold>

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initiate getTitle Method//
        getName();

        // Starts UI For Activity//
        setContentView(R.layout.events_view_ui);

        // Initiate eventsDatabase Open Method//
        eventsDatabaseOpen();

        // Initiate eventsDatabaseOld Open Method//
        eventsDatabaseOldOpen();

        // Initiate InstantiateWidgets Method//
        instantiateWidgets();

        // Initiate listViewItemClick Method//
        listViewItemClick();

        // Initiate addToNewDatabase Method//
        addToNewDatabase();

        // Initiate populateListView//
        populateListView();
    }

    //Controls Back Button Functions//
    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        switch (keyCode) {

            // What Happens When Back Button Is Pressed//
            case KeyEvent.KEYCODE_BACK:

                // Define and Instantiate Variable Intent UsersView//
                Intent usersView = new Intent(this, UsersView.class);

                // Start Activity UsersView//
                startActivity(usersView);

                // Custom Transition//
                overridePendingTransition(R.anim.slid_in, R.anim.slid_out);

                // Kill Code//
                return false;
            default:
                return false;
        }
    }

    // Method To Take Old Database Values And Add Them Into The New Database//
    private void addToNewDatabase() {

        // Define String Variable FIRST_TIME//
        final String firstTimeTrueOrFalse = "firstTime";

        // Save Preferences//
        SharedPreferences firstTime = getSharedPreferences(firstTimeTrueOrFalse, 0);

        // What Happens When Activity Runs The First Time//
        if (firstTime.getBoolean("firstTime", true)) {

            // Query Through All Rows//
            Cursor c = 	EventsDatabaseOld.db.query(true, EventsDatabaseOld.DATABASE_TABLE, EventsDatabaseOld.ALL_KEYS, null, null, null, null, null, null);

            // Move To First Value//
            if (c != null) {
                c.moveToFirst();
            }

            // Get Position In Database//
            assert c != null;
            while (!c.isAfterLast()) {

                // Define And Instantiate Variable String name//
                String name = c.getString(EventsDatabaseOld.COL_NAME);

                // Define And Instantiate Variable String date//
                String date = c.getString(EventsDatabaseOld.COL_DATE);

                // Define And Instantiate Variable String location//
                String location = c.getString(EventsDatabaseOld.COL_LOCATION);

                // Define And Instantiate Variable String timeStart//
                String timeStart = c.getString(EventsDatabaseOld.COL_STARTTIME);

                // Define And Instantiate Variable String timeEnd//
                String timeEnd = c.getString(EventsDatabaseOld.COL_ENDTIME);

                // Define And Instantiate Variable String timeTotal//
                String timeTotal = c.getString(EventsDatabaseOld.COL_TOTALTIME);

                // Define And Instantiate Variable String timeTotalAdded//
                String timeTotalAdded = c.getString(EventsDatabaseOld.COL_TOTALTIME_ADDED);

                // Insert Old Values Into Database//
                eventsDatabase.insertRow(usersViewNameUser, name, date, location, timeStart, timeEnd, timeTotal, timeTotalAdded, "", "", "","");

                // Move To Next Row//
                c.moveToNext();
            }

            // Close Cursor//
            c.close();

            // Close eventsDatabaseOld//
            eventsDatabaseOld.close();

            // Record The Fact That The Activity Has Been Started At Least Once//
            firstTime.edit().putBoolean("firstTime", false).apply();
        }
    }

    // Method To Open Events Database//
    private void eventsDatabaseOpen() {

        // Instantiate Variable EventsDatabase eventsDatabase//
        eventsDatabase = new EventsDatabase(this);

        // Open Database//
        eventsDatabase.open();
    }

    // Method To Open Old Events Database//
    private void eventsDatabaseOldOpen() {

        // Instantiate Variable EventsDatabaseOld eventsDatabaseOld//
        eventsDatabaseOld = new EventsDatabaseOld(this);

        // Open Database//
        eventsDatabaseOld.open();
    }

    // Method To Get User Name//
    private void getName() {

        // Gets Name Of Last Clicked List View Item//
        usersViewNameUser = getIntent().getStringExtra(UsersView.USERS_VIEW_NAME_USER);

        // Gets Name Of Last Clicked List View Item//
        eventAddNameUser = getIntent().getStringExtra(EVENTS_ADD_NAME_USER);

        // What Happens When usersViewNameUser Doesn't Equals Null//
        if (usersViewNameUser != null) {

            // Set Title Equal To usersViewNameUser//
            setTitle(usersViewNameUser);

        } else {

            // Set Title Equal To eventAddNameUser//
            setTitle(eventAddNameUser);
        }
    }

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable ListView usersListView//
        eventsListView = (ListView) findViewById(R.id.eventsListView);

        // Instantiate Variable Vibrator vibe//
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    // Method To Set eventsListView OnItemClickListener//
    public void listViewItemClick() {

        // What Happens When ListView Item is Clicked//
        eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Vibrates For 50 Mill//
                vibe.vibrate(50);

                // Define and Instantiate Variable Intent EventsView//
                Intent eventsExtraInformation = new Intent(EventsView.this, EventsExtraInformation.class);

                // Get Name Of Item Clicked In userListView//
                eventsExtraInformation.putExtra(KEY_ROW_ID_NUMBER, String.valueOf(id));

                // Start Activity EventsExtraInformation//
                startActivity(eventsExtraInformation);

                // Custom Transition//
                overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
            }
        });
    }

    // What Happens When Fab Btn Is Clicked//
    public void onClickFab(View view) {

        // Vibrate For 50m//
        vibe.vibrate(50);

        // Define and Instantiate Variable Intent EventsAdd//
        Intent eventsAdd = new Intent(this, EventsAdd.class);

        // Get Name From eventAddNameUser//
        eventsAdd.putExtra(EVENTS_ADD_NAME_USER, eventAddNameUser);

        // Get Name From usersViewNameUser//
        eventsAdd.putExtra(USERS_VIEW_NAME_USER, usersViewNameUser);

        // Start Activity EventsAdd//
        startActivity(eventsAdd);

        // Custom Transition//
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);

        // Close usersDatabase//
        eventsDatabase.close();
    }

    // Method To Populate ListView//
    private void populateListView() {

        // Gets All Rows Added To Database//
        cursor = eventsDatabase.getAllRowsOldestToNewest();

        // Puts Rows Stored On Database Into A String Shown//
        final String[] fromFieldNames = new String[]{EventsDatabase.KEY_NAME_EVENT, EventsDatabase.KEY_DATE, EventsDatabase.KEY_LOCATION, EventsDatabase.KEY_TIME_START, EventsDatabase.KEY_TIME_END, EventsDatabase.KEY_TIME_TOTAL};

        // Takes String From Database And Sends It To Whatever Layout Widget You Want, Will Show Up In The Order String Is Made In//
        int[] toViewIDs = new int[]{R.id.eventsName, R.id.eventsDate, R.id.eventsLocation, R.id.eventsTimeStart, R.id.eventsTimeEnd, R.id.eventsTimeTotal};

        // Make Above Cursor Final//
        final Cursor finalCursor = cursor;

        // Creates ListView Adapter Which Allows ListView Items To Be Seen//
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.events_view_design_ui, finalCursor, fromFieldNames, toViewIDs, 0);

        // Sets Up Adapter Made Earlier / Shows Content From Database//
        eventsListView.setAdapter(simpleCursorAdapter);
    }
}
