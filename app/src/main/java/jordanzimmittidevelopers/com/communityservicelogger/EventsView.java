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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

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

    // Puts Id Of Last Clicked ListView Item Into String//
    public static final String KEY_ROW_ID_NUMBER = EventsDatabase.KEY_ROW_ID_NUMBER;


    // Puts Name Of Last Clicked ListView Item Into String//
    public static final String EVENTS_ADD_NAME_USER = null;

    // Define Variable String eventAddNameUser / String Of Name Value//
    private String eventsAddNameUser = null;


    // Define Variable String eventsExtraInfNameUser//
    private String eventsExtraInfNameUser = null;


    // Puts Name Of Last Clicked ListView Item Into String//
    public static final String USERS_VIEW_NAME_USER = null;

    // Define Variable String usersViewNameUser / String Of Name Value//
    private String usersViewNameUser = null;


    // Puts Name Of Last Clicked ListView Item Into String//
    public static final String WORKING_NAME_USER = null;

    // Define Variable String workingNameUser//
    private String workingNameUser;

    //</editor-fold>

    //<editor-fold desc="Shared Preference">

    // Saves Sort Preference To String//
    public static final String SORT_TYPE = "sort_type";

    // Apply Sort Preference//
    public static int SORT_BY;

    // Sort By Name//
    public final static int SORT_BY_NAME = 1;

    // Sort By Date//
    public final static int SORT_BY_DATE = 2;

    // Sort By Location//
    public final static int SORT_BY_LOCATION = 3;

    // Sort By Newest To Oldest//
    public final static int SORT_BY_NEWEST_TO_OLDEST = 4;

    // Sort By Newest To Oldest//
    public final static int SORT_BY_OLDEST_TO_NEWEST = 5;

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

        // Initiate listViewLongItemClick Method//
        listViewItemLongClick();

        // Initiate addToNewDatabase Method//
        addToNewDatabase();

        // Initiate populateListView//
        populateListView(workingNameUser);
    }

    // Creates Menu And All Its Components//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflates The Menu / This Adds Items To The Action Bar If It Is Present//
        getMenuInflater().inflate(R.menu.events_view_menu, menu);

        // Kill Code//
        return true;
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

    // What Happens When Menu Buttons Are Clicked//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Figures Out What Menu Button Was Pressed//
        int id = item.getItemId();

        // What Happens When home Is Pressed//
        if (id == android.R.id.home) {

            // Vibrate For 50m//
            vibe.vibrate(50);
        }

        // What Happens When eventsSortBy Is Pressed//
        if (id == R.id.eventsSortBy) {

            // Initiate Method orderBy//
            sortBy();

            // Kill Code//
            return true;
        }

        // Kill Code//
        return super.onOptionsItemSelected(item);
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
        eventsAddNameUser = getIntent().getStringExtra(EVENTS_ADD_NAME_USER);

        // Gets Name Of Last Clicked List View Item//
        eventsExtraInfNameUser = getIntent().getStringExtra(EventsExtraInformation.EVENTS_EXTRA_INF_NAME_USER);

        // Gets Name Of Last Clicked List View Item//
        usersViewNameUser = getIntent().getStringExtra(UsersView.USERS_VIEW_NAME_USER);

        // What Happens When eventsAddNameUser Doesn't Equal Null//
        if (eventsAddNameUser != null) {

            // Set Title Equal To eventAddNameUser//
            setTitle(eventsAddNameUser);

            // Set workingNameUser Equal To eventsAddNameUser//
            workingNameUser = eventsAddNameUser;
        }

        // What Happens When eventsExtraInfNameUser Doesn't Equals Null//
        else if (eventsExtraInfNameUser != null) {

            // Set Title Equal To eventsExtraInfNameUser//
            setTitle(eventsExtraInfNameUser);

            // Set workingNameUser Equal To eventsExtraInfNameUser//
            workingNameUser = eventsExtraInfNameUser;
        }

        // What Happens When usersViewNameUser Doesn't Equals Null//
        else if (usersViewNameUser != null) {

            // Set Title Equal To usersViewNameUser//
            setTitle(usersViewNameUser);

            // Set workingNameUser Equal To usersViewNameUser//
            workingNameUser = usersViewNameUser;
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

    // Method That Runs When ListView Item Is Long Clicked//
    public void listViewItemLongClick() {

        eventsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {

                // Create Dialog//
                new MaterialDialog.Builder(EventsView.this)

                        // Title Of Dialog//
                        .title("What Would You Like To Do")

                        // Content Of Dialog//
                        .content("Cancel popup, edit user, or delete user")

                        // Positive Text Name For Button//
                        .positiveText("Delete")

                        // Negative Text Name For Button//
                        .negativeText("Edit")

                        // Neutral Text Name For Button//
                        .neutralText("Cancel")

                        // What Happens When Positive Button Is Pressed//
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                // Vibrates For 50 Mill//
                                vibe.vibrate(50);

                                // Deletes Specific Item In ListView//
                                eventsDatabase.deleteRow(id);

                                // populates ListView//
                                populateListView(workingNameUser);

                                // Restart EventsView Class//
                                Intent i = new Intent(EventsView.this, EventsView.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                i.putExtra(WORKING_NAME_USER, workingNameUser);
                                startActivityForResult(i, 0);
                                overridePendingTransition(0, 0); //0 for no animation;
                            }
                        })

                        //What Happens When Negative Button Is Pressed//
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                // Vibrates For 50 Mill//
                                vibe.vibrate(50);

                                // Define and Instantiate Variable Intent UsersEdit//
                                Intent usersEdit = new Intent(EventsView.this, EventsEdit.class);

                                // Get Id Of Item Clicked In userListView//
                                usersEdit.putExtra(KEY_ROW_ID_NUMBER, String.valueOf(id));

                                // Get Name From eventAddNameUser//
                                usersEdit.putExtra(EVENTS_ADD_NAME_USER, eventsAddNameUser);

                                // Get Name From usersViewNameUser//
                                usersEdit.putExtra(USERS_VIEW_NAME_USER, usersViewNameUser);

                                // Start Activity UsersAdd//
                                startActivity(usersEdit);

                                // Custom Transition//
                                overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
                            }
                        })

                        // What Happens When Neutral Button Is Pressed//
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                // Vibrates For 50 Mill//
                                vibe.vibrate(50);
                            }

                        }).show();

                // Kill Code//
                return true;
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
        eventsAdd.putExtra(EVENTS_ADD_NAME_USER, eventsAddNameUser);

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
    private void populateListView(String workingNameUser) {

        // Gets Rows In Database Based On Name Of User//
        cursor = EventsDatabase.db.rawQuery("SELECT * FROM new_events where nameUser LIKE '%" + workingNameUser + "%'" + "ORDER BY " + EventsDatabase.KEY_NAME_EVENT + " COLLATE NOCASE" + " ASC", null);

        // What Happens If searchCursor Has Data//
        if (cursor != null) {

            // Move Database To Next Value//
            cursor.moveToFirst();

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

    // Method To Sort Events By User Preference//
    private void sortBy() {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Title String//
        String dialogTitle = "Sort Users By...";

        // Order By... Strings//
        final String[] orderByString = {"Sort By Name", "Sort By Date", "Sort By Location", "Sort By Newest To Oldest", "Sort By Oldest To Newest"};

        // Positive Btn String//
        String positiveBtn = "Ok";

        new MaterialDialog.Builder(this)
                .title(dialogTitle)
                .items((CharSequence[]) orderByString)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {

                        // Sets Sort Preference By Name//
                        if (i == 0) {

                            // Vibrates For 50 Mill//
                            vibe.vibrate(50);

                            // Saves Sort Preference Of Events//
                            SharedPreferences settings = getSharedPreferences(SORT_TYPE, MODE_PRIVATE);
                            SharedPreferences.Editor edit;
                            edit = settings.edit();

                            // Sets Sort For Events//
                            SORT_BY = SORT_BY_NAME;
                            edit.clear();
                            edit.putInt("Custom_Order_By", SORT_BY);
                            edit.apply();

                            // Define and Instantiate Variable Intent EventsView//
                            Intent eventsView = new Intent(EventsView.this, EventsView.class);

                            // Start Activity EventsView//
                            startActivity(eventsView);

                            // Custom Transition//
                            overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
                        }

                        // Sets Sort Preference By Newest To Oldest//
                        if (i == 1) {

                            // Vibrates For 50 Mill//
                            vibe.vibrate(50);

                            // Saves Sort Preference Of Events//
                            SharedPreferences settings = getSharedPreferences(SORT_TYPE, MODE_PRIVATE);
                            SharedPreferences.Editor edit;
                            edit = settings.edit();

                            // Saves Sort Preference Of Events//
                            SORT_BY = SORT_BY_DATE;
                            edit.clear();
                            edit.putInt("Custom_Order_By", SORT_BY);
                            edit.apply();

                            // Define and Instantiate Variable Intent eventsView//
                            Intent eventsView = new Intent(EventsView.this, EventsView.class);

                            // Start Activity EventsView//
                            startActivity(eventsView);

                            // Custom Transition//
                            overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
                        }

                        // Sets Sort Preference By Oldest To Newest//
                        if (i == 2) {

                            // Vibrates For 50 Mill//
                            vibe.vibrate(50);

                            // Saves Sort Preference Of Events//
                            SharedPreferences settings = getSharedPreferences(SORT_TYPE, MODE_PRIVATE);
                            SharedPreferences.Editor edit;
                            edit = settings.edit();

                            // Sets Save Preference For Events//
                            SORT_BY = SORT_BY_LOCATION;
                            edit.clear();
                            edit.putInt("Custom_Order_By", SORT_BY);
                            edit.apply();

                            // Define and Instantiate Variable Intent eventsView//
                            Intent eventsView = new Intent(EventsView.this, EventsView.class);

                            // Start Activity UsersView//
                            startActivity(eventsView);

                            // Custom Transition//
                            overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
                        }

                        // Sets Sort Preference By Oldest To Newest//
                        if (i == 3) {

                            // Vibrates For 50 Mill//
                            vibe.vibrate(50);

                            // Saves Sort Preference Of Events//
                            SharedPreferences settings = getSharedPreferences(SORT_TYPE, MODE_PRIVATE);
                            SharedPreferences.Editor edit;
                            edit = settings.edit();

                            // Sets Save Preference For Events//
                            SORT_BY = SORT_BY_NEWEST_TO_OLDEST;
                            edit.clear();
                            edit.putInt("Custom_Order_By", SORT_BY);
                            edit.apply();

                            // Define and Instantiate Variable Intent eventsView//
                            Intent eventsView = new Intent(EventsView.this, EventsView.class);

                            // Start Activity EventsView//
                            startActivity(eventsView);

                            // Custom Transition//
                            overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
                        }

                        // Sets Sort Preference By Oldest To Newest//
                        if (i == 4) {

                            // Vibrates For 50 Mill//
                            vibe.vibrate(50);

                            // Saves Sort Preference Of Events//
                            SharedPreferences settings = getSharedPreferences(SORT_TYPE, MODE_PRIVATE);
                            SharedPreferences.Editor edit;
                            edit = settings.edit();

                            // Sets Save Preference For Events//
                            SORT_BY = SORT_BY_OLDEST_TO_NEWEST;
                            edit.clear();
                            edit.putInt("Custom_Order_By", SORT_BY);
                            edit.apply();

                            // Define and Instantiate Variable Intent eventsView//
                            Intent eventsView = new Intent(EventsView.this, EventsView.class);

                            // Start Activity EventsView//
                            startActivity(eventsView);

                            // Custom Transition//
                            overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
                        }

                        // Kill Code//
                        return false;
                    }
                })
                .positiveText(positiveBtn)
                .show();
    }

}
