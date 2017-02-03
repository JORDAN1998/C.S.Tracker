package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
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
            eventsDatabase.insertRow(passedVar, name, date, location, timeStart, timeEnd, timeTotal, timeTotalAdded, "", "", "");

            // Move To Next Row//
            c.moveToNext();
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

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable ListView usersListView//
        eventsListView = (ListView) findViewById(R.id.eventsListView);

        // Instantiate Variable Vibrator vibe//
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    // What Happens When Fab Btn Is Clicked//
    public void onClickFab(View view) {
    }

    // Method To Populate ListView//
    private void populateListView() {

    }
}
