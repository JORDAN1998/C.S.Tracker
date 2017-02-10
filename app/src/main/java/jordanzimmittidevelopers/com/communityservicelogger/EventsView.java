package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

// EventsView Class Created By Jordan Zimmitti 1-29-17//
public class EventsView extends AppCompatActivity {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Define Variable Cursor cursor//
    Cursor cursor;

    // Define Variable EventsDatabaseOld eventsDatabase//
    private EventsDatabase eventsDatabase;

    // Define Variable EventsDatabaseOld eventsDatabaseOld//
    private EventsDatabaseOld eventsDatabaseOld;

    // Define Variable Vibrator vibe//
    private Vibrator vibe;

    //</editor-fold>

    //<editor-fold desc="String">

    // Define Variable String passedVar / String Of Name Values//
    private String passedVar = null;

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

        // Gets Name Of Last Clicked List View Item//
        passedVar = getIntent().getStringExtra(UsersView.KEY_NAMES);

        // Set Title Equal To PassedVAr
        setTitle(passedVar);

        // Starts UI For Activity//
        setContentView(R.layout.events_view_ui);

        // Initiate eventsDatabase Open Method//
        eventsDatabaseOpen();

        // Initiate eventsDatabaseOld Open Method//
        eventsDatabaseOldOpen();

        // Initiate InstantiateWidgets Method//
        instantiateWidgets();

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
            eventsDatabase.insertRow(passedVar, name, date, location, timeStart, timeEnd, timeTotal, timeTotalAdded, "", "", "","");

            // Move To Next Row//
            c.moveToNext();
        }

        // Close Cursor//
        c.close();

        // Close eventsDatabaseOld//
        eventsDatabaseOld.close();
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

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable ListView usersListView//
        eventsListView = (ListView) findViewById(R.id.eventsListView);

        // Instantiate Variable Vibrator vibe//
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    // What Happens When Fab Btn Is Clicked//
    public void onClickFab(View view) {

        // Vibrate For 50m//
        vibe.vibrate(50);

        // Define and Instantiate Variable Intent EventsAdd//
        Intent EventsAdd = new Intent(this, EventsAdd.class);

        // Start Activity EventsAdd//
        startActivity(EventsAdd);

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
