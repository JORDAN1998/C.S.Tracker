package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import static jordanzimmittidevelopers.com.communityservicelogger.R.id.eventsDate;

// EventsView Class Created By Jordan Zimmitti 1-29-17//
public class EventsView extends AppCompatActivity {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Define Variable Cursor cursor//
    private Cursor cursor;

    // Define Variable EventsDatabase eventsDatabase//
    private EventsDatabase eventsDatabase;

    // Define Variable Vibrator vibe//
    private Vibrator vibe;

    //</editor-fold>

    //<editor-fold desc="Navigation Drawer Variables">

    // Define Variable UsersNavigationDrawer usersNavigationDrawer//
    private UsersNavigationDrawer eventsNavigationDrawer;

    // UsersNavigationDrawer Items//
    private String[] items = new String[] {"Reminders", "Settings", "About"};

    //</editor-fold>

    //<editor-fold desc="String">

    // Puts Id Of Last Clicked ListView Item Into String//
    public static final String KEY_ROW_ID_NUMBER = EventsDatabase.KEY_ROW_ID_NUMBER;

    // Define Variable String workingNameUser//
    private String workingNameUser;

    //</editor-fold>

    //<editor-fold desc="Shared Preference">

    // Define Variable SharedPreferences eventsSortType//
    private SharedPreferences eventsSortType;

    // Name Of Preference And What Its Saving The Integer To//
    private static final String EVENT_SORT_TYPE = "event_sort_type";

    // Sort By Name//
    private final static int SORT_BY_NAME = 0;

    // Sort By Date//
    private final static int SORT_BY_DATE = 1;

    // Sort By Location//
    private final static int SORT_BY_LOCATION = 2;

    // Sort By Newest To Oldest//
    private final static int SORT_BY_NEWEST_TO_OLDEST = 3;

    // Sort By Newest To Oldest//
    private final static int SORT_BY_OLDEST_TO_NEWEST = 4;


    // Name Of Preference And What Its Saving The Integer To//
    private static final String USER_MODE_NAME = "user_mode_name";

    // Apply Sort By Name//
    private final static String USER_NAME = "name of user";

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

        // Initiate applyTheme Method//
        applyTheme();

        // Initiate eventsDatabase Open Method//
        eventsDatabaseOpen();

        // Initiate eventsDatabaseOld Open Method//
        eventsDatabaseOldOpen();

        // Initiate addToNewDatabase Method//
        addToNewDatabase();

        // Initiate InstantiateWidgets Method//
        instantiateWidgets();

        // Initiate listViewItemClick Method//
        listViewItemClick();

        // Initiate listViewLongItemClick Method//
        listViewItemLongClick();

        // Initiate navigationDrawer Method//
        navigationDrawer();

        // Initiate sortByPreference Method//
        sortByPreference(workingNameUser);
    }

    // Creates Menu And All Its Components//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflates The Menu / This Adds Items To The Action Bar If It Is Present//
        getMenuInflater().inflate(R.menu.events_view_menu, menu);

        // Initiate searchUser Method//
        searchEvent(menu);

        // Kill Code//
        return true;
    }

    //What Happens When Back Button Is Pressed//
    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        switch (keyCode) {

            // What Happens When Back Button Is Pressed//
            case KeyEvent.KEYCODE_BACK:

                // Instantiate Variable SharedPreferences userSwitchState//
                SharedPreferences userSwitchState = getSharedPreferences("user_switch_state", MODE_PRIVATE);

                // What Happens When Switch Is Un-Checked//
                if (userSwitchState.getInt("user_switch_state", -1) == 0) {

                    // Create Dialog//
                    new MaterialDialog.Builder(EventsView.this)

                            // Title Of Dialog//
                            .title("Exit")

                            // Content Of Dialog//
                            .content("Would you like to exit the app?")

                            // Positive Text Name For Button//
                            .positiveText("Yes")

                            // Negative Text Name For Button//
                            .negativeText("No")

                            // What Happens When Positive Button Is Pressed//
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    // Vibrates For 50 Mill//
                                    vibe.vibrate(50);

                                    // Close usersDatabase//
                                    eventsDatabase.close();

                                    // Intent To Kill app//
                                    Intent killApp = new Intent(Intent.ACTION_MAIN);
                                    killApp.addCategory(Intent.CATEGORY_HOME);
                                    killApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(killApp);
                                }
                            })

                            // What Happens When Negative Button Is Pressed//
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    // Vibrates For 50 Mill//
                                    vibe.vibrate(50);
                                }

                            }).show();
                }

                // What Happens When Switch Is Checked//
                else if (userSwitchState.getInt("user_switch_state", -1) == 1) {

                    // Define And Instantiate Variable SettingsThemePicker pickTheme//
                    SettingsThemePicker pickTheme = new SettingsThemePicker();

                    // Fixes Back Button B/C Of Icon Color//
                    pickTheme.BackButtonFix(this);
                }

                // What Happens When No User Preference Is Saved//
                else {

                    // Define And Instantiate Variable SettingsThemePicker pickTheme//
                    SettingsThemePicker pickTheme = new SettingsThemePicker();

                    // Fixes Back Button B/C Of Icon Color//
                    pickTheme.BackButtonFix(this);
                }

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

            // Toggle Navigation Drawer//
            eventsNavigationDrawer.buttonToggle();

            // Kill Code//
            return true;
        }

        // What Happens When eventsSortBy Is Pressed//
        if (id == R.id.eventsSortBy) {

            // Vibrates For 50 Mill//
            vibe.vibrate(50);

            // Initiate Method orderBy//
            sortBy();

            // Kill Code//
            return true;
        }

        // Kill Code//
        return super.onOptionsItemSelected(item);
    }

    // Prepares Options Menu//
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // Get Total Added Time//
        eventsDatabase.totalTimeAdded(workingNameUser);

        // Instantiate Variable MenuItem timeTotalAdded//
        MenuItem timeTotalAdded = menu.findItem(R.id.timeTotalAdded);

        // Convert Minutes Into Hours//
        int hours = EventsDatabase.totalTimeAdded / 60;

        //Convert Hours Into Minutes//
        int minutes = EventsDatabase.totalTimeAdded % 60;

        // If Minutes Is Less Than Ten Add A Zero//
        if (minutes < 10) {

            // Define And Instantiate Variable String timeTotal//
            String timeTotal = hours + ":" + "0" + minutes;

            // Set Text Equal To timeTotal//
            timeTotalAdded.setTitle(String.valueOf(timeTotal));
        }

        // Don't Add A Zero//
        else {

            // Define And Instantiate Variable String timeTotal//
            String timeTotal = hours + ":" + minutes;

            // Set Text Equal To timeTotal//
            timeTotalAdded.setTitle(String.valueOf(timeTotal));
        }

        return super.onPrepareOptionsMenu(menu);
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
            Cursor c = EventsDatabaseOld.db.query(true, EventsDatabaseOld.DATABASE_TABLE, EventsDatabaseOld.ALL_KEYS, null, null, null, null, null, null);

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
                String normalDate = c.getString(EventsDatabaseOld.COL_DATE);

                //<editor-fold desc="Reverse Date">

                // Define And Instantiate Variable String[] splitReverseDate//
                String[] splitReverseDate = normalDate.split("/");

                // Define And Instantiate Variable int month//
                int month = Integer.parseInt(splitReverseDate[0]);

                // Define And Instantiate Variable int day//
                int dayOfMonth = Integer.parseInt(splitReverseDate[1]);

                // Define And Instantiate Variable int year//
                int year = Integer.parseInt(splitReverseDate[2]);

                // Define Variable String date//
                String date;

                // What Happens If dayOfMonth Is Less Then Zero//
                if (dayOfMonth < 10) {

                    // Define And Instantiate Variable String day//
                    String day = "0" + dayOfMonth;

                    //Instantiate Variable String date//
                    date = year + "/" + month + "/" + day;
                }

                // What Happens When dayOfMonth Is Bigger Then Zero//
                else {

                    //Instantiate Variable String date//
                    date = year + "/" + month + "/" + dayOfMonth;
                }

                //</editor-fold>

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
                eventsDatabase.insertRow(name, workingNameUser, date, location, timeStart, timeEnd, timeTotal, timeTotalAdded, "", "", "", "");

                // Move To Next Row//
                c.moveToNext();
            }

            // Record The Fact That The Activity Has Been Started At Least Once//
            firstTime.edit().putBoolean("firstTime", false).apply();
        }
    }

    // Method That Applies Theme By User Preference//
    private void applyTheme() {

        // Define And Instantiate Variable SettingsThemePicker pickTheme//
        SettingsThemePicker pickTheme = new SettingsThemePicker();

        // Set Theme Based On User Preference//
        pickTheme.userTheme(this);

        // Initiate getTitle Method//
        getName();

        // Starts UI For Activity//
        setContentView(R.layout.events_view_ui);

        // Define And Instantiate RelativeLayout relativeLayout//
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.events_view_ui);

        // Night Mode Theme Extension Options//
        pickTheme.activityNightModeExtension(this, relativeLayout);
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

        // Define And Instantiate Variable EventsDatabaseOld eventsDatabaseOld//
        EventsDatabaseOld eventsDatabaseOld = new EventsDatabaseOld(this);

        // Open Database//
        eventsDatabaseOld.open();
    }

    // Method To Get User Name//
    private void getName() {

        // Define And Instantiate Variable SharedPreferences userModeName//
        SharedPreferences userModeName = getSharedPreferences(USER_MODE_NAME, MODE_PRIVATE);

        // Set workingNameUser Equal To defaultUserModeName//
        workingNameUser = userModeName.getString(USER_NAME, "");

        // Set Title Equal To userModeName//
        setTitle(workingNameUser);
    }

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable ListView eventsListView//
        eventsListView = (ListView) findViewById(R.id.eventsListView);

        // Instantiate Variable SharedPreference eventsSortType//
        eventsSortType = getSharedPreferences(EVENT_SORT_TYPE, MODE_PRIVATE);

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

                                // Initiate sortByPreference Method//
                                sortByPreference(workingNameUser);

                                // Restart EventsView Class//
                                Intent i = new Intent(EventsView.this, EventsView.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
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

                                // Get Name Of Item Clicked In userListView//
                                usersEdit.putExtra(KEY_ROW_ID_NUMBER, String.valueOf(id));

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

    // Method That Controls The Navigation Drawer//
    private void navigationDrawer() {

        // Instantiate Variable SharedPreferences userSwitchState//
        SharedPreferences userSwitchState = getSharedPreferences("user_switch_state", MODE_PRIVATE);

        // What Happens When Switch Is Un-Checked//
        if (userSwitchState.getInt("user_switch_state", -1) == 0) {

            // Instantiate NavigationDrawer navigationDrawer//
            eventsNavigationDrawer = (UsersNavigationDrawer) getSupportFragmentManager().findFragmentById(R.id.eventsNavigationDrawer);

            // Sets Up NavigationDrawer//
            eventsNavigationDrawer.setUp((DrawerLayout) findViewById(R.id.eventsDrawerLayout), R.id.eventsNavigationDrawer);

            // Add NavigationDrawer Items//
            eventsNavigationDrawer.addItems(this, items);

            // Show Hamburger Icon//
            eventsNavigationDrawer.showHamburgerIcon(true);
        }

        else {

            // Define And Instantiate Variable DrawerLayout drawerLayout//
            DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.eventsDrawerLayout);

            // Stop Navigation Drawer From Sliding Open//
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    // What Happens When Fab Btn Is Clicked//
    public void onClickFab(View view) {

        // Vibrate For 50m//
        vibe.vibrate(50);

        // Define and Instantiate Variable Intent eventsAdd//
        Intent eventsAdd = new Intent(this, EventsAdd.class);

        // Start Activity EventsAdd//
        startActivity(eventsAdd);

        // Custom Transition//
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);

        // Close usersDatabase//
        eventsDatabase.close();
    }

    // Method To Populate ListView//
    private void populateListView(final Cursor cursor) {

        // What Happens If searchCursor Has Data//
        if (cursor != null) {

            // Move Database To Next Value//
            cursor.moveToFirst();

            // Puts Rows Stored On Database Into A String Shown//
            final String[] fromFieldNames = new String[]{EventsDatabase.KEY_NAME_EVENT, EventsDatabase.KEY_DATE, EventsDatabase.KEY_LOCATION, EventsDatabase.KEY_TIME_START, EventsDatabase.KEY_TIME_END, EventsDatabase.KEY_TIME_TOTAL};

            // Takes String From Database And Sends It To Whatever Layout Widget You Want, Will Show Up In The Order String Is Made In//
            int[] toViewIDs = new int[]{R.id.eventsName, eventsDate, R.id.eventsLocation, R.id.eventsTimeStart, R.id.eventsTimeEnd, R.id.eventsTimeTotal};

            // Creates ListView Adapter Which Allows ListView Items To Be Seen//
            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.events_view_design_ui, cursor, fromFieldNames, toViewIDs, 0) {

                // Access users_view_design_ui Widgets//
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    // Get Cursor Position//
                    cursor.moveToPosition(position);

                    // Get Row Of Database//
                    final View row = super.getView(position, convertView, parent);

                    //<editor-fold desc="Night Mode">

                    // Define And Instantiate Variable SharedPreferences switchState//
                    SharedPreferences switchState = getSharedPreferences("night_mode_switch_state", MODE_PRIVATE);

                    // What Happens When Night Mode Switch Is Checked//
                    if (switchState.getInt("night_mode_switch_state", -1) == 1) {

                        // Find Night Mode Automatically//
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);

                        // Checks Whether app Is In Night Mode Or Not//
                        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

                        // Checks All Scenarios//
                        switch (currentNightMode) {

                            // Night Mode Is Active, We're At Night!//
                            case Configuration.UI_MODE_NIGHT_YES: {

                                // Define And Instantiate Variable CardView cardView//
                                CardView cardView = (CardView) row.findViewById(R.id.cardView);

                                // Set Card Background Color To Gray//
                                cardView.setCardBackgroundColor(ContextCompat.getColor(EventsView.this, R.color.grey));

                                // Kill Code//
                                break;
                            }
                        }
                    }

                    //</editor-fold>

                    //<editor-fold desc="Normal Date">

                    // Define And Instantiate Variable String reverseDate//
                    String reverseDate = cursor.getString(EventsDatabase.COL_DATE);

                    // Define And Instantiate Variable String[] splitReverseDate//
                    String[] splitReverseDate = reverseDate.split("/");

                    // Define And Instantiate Variable String year//
                    String year = splitReverseDate[0];

                    // Define And Instantiate Variable String mon//
                    String month = splitReverseDate[1];

                    // Define And Instantiate Variable String day//
                    String day = splitReverseDate[2];

                    // Define And Instantiate Variable String date//
                    String date = month + "/" + day + "/" + year;

                    // Define And Instantiate Variable TextView eventsDate//
                    TextView eventsDate = (TextView) row.findViewById(R.id.eventsDate);

                    // Set eventsDate Text Equal To date//
                    eventsDate.setText(date);

                    //</editor-fold>

                    // Kill Code//
                    return row;
                }
            };

            // Sets Up Adapter Made Earlier / Shows Content From Database//
            eventsListView.setAdapter(simpleCursorAdapter);
        }
    }

    // Method That Searches For Specific User//
    private void searchEvent(Menu menu) {

        // Define And Instantiate SearchView eventsSearch//
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.eventsSearch));

        // Set Query Hint For User//
        searchView.setQueryHint("Search Events");

        // Runs When Text Is Being Entered Into Search Box//
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            // Runs When user Submits Text //
            @Override
            public boolean onQueryTextSubmit(String query) {

                // Kill Code//
                return false;
            }

            // Runs Each Time User Adds Letter In Search Box//
            @Override
            public boolean onQueryTextChange(String searchText) {

                // Checks If Search Is Empty//
                if (!searchText.isEmpty()) {

                    // Gets Rows In Database Based On Name Of User//
                    Cursor searchCursor = EventsDatabase.db.rawQuery("SELECT * FROM new_events where nameEvent LIKE '%" + searchText + "%' and nameUser LIKE '%" + workingNameUser + "%'" + "ORDER BY " + EventsDatabase.KEY_NAME_EVENT + " COLLATE NOCASE" + " ASC", null);

                    // What Happens If searchCursor Has Data//
                    if (searchCursor != null) {

                        // Move Database To Next Value//
                        searchCursor.moveToFirst();

                        // Puts Rows Stored On Database Into A String Shown//
                        final String[] fromFieldNames = new String[]{EventsDatabase.KEY_NAME_EVENT, EventsDatabase.KEY_DATE, EventsDatabase.KEY_LOCATION, EventsDatabase.KEY_TIME_START, EventsDatabase.KEY_TIME_END, EventsDatabase.KEY_TIME_TOTAL};

                        // Takes String From Database And Sends It To Whatever Layout Widget You Want, Will Show Up In The Order String Is Made In//
                        int[] toViewIDs = new int[]{R.id.eventsName, eventsDate, R.id.eventsLocation, R.id.eventsTimeStart, R.id.eventsTimeEnd, R.id.eventsTimeTotal};

                        // Make Above Cursor Final//
                        final Cursor finalCursor = searchCursor;

                        // Creates ListView Adapter Which Allows ListView Items To Be Seen//
                        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(EventsView.this, R.layout.events_view_design_ui, finalCursor, fromFieldNames, toViewIDs, 0) {

                            // Access users_view_design_ui Widgets//
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {

                                // Get Cursor Position//
                                cursor.moveToPosition(position);

                                // Get Row Of Database//
                                final View row = super.getView(position, convertView, parent);

                                //<editor-fold desc="Night Mode">

                                // Define And Instantiate Variable SharedPreferences switchState//
                                SharedPreferences switchState = getSharedPreferences("switch_state", MODE_PRIVATE);

                                // What Happens When Night Mode Switch Is Checked//
                                if (switchState.getInt("switch_state", 0) == 1) {

                                    // Find Night Mode Automatically//
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);

                                    // Checks Whether app Is In Night Mode Or Not//
                                    int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

                                    // Checks All Scenarios//
                                    switch (currentNightMode) {

                                        // Night Mode Is Active, We're At Night!//
                                        case Configuration.UI_MODE_NIGHT_YES: {

                                            // Define And Instantiate Variable CardView cardView//
                                            CardView cardView = (CardView) row.findViewById(R.id.cardView);

                                            // Set Card Background Color To Gray//
                                            cardView.setCardBackgroundColor(ContextCompat.getColor(EventsView.this, R.color.grey));

                                            // Kill Code//
                                            break;
                                        }
                                    }
                                }

                                //</editor-fold>

                                // Kill Code//
                                return row;
                            }
                        };

                        // Sets Up Adapter Made Earlier / Shows Content From Database//
                        eventsListView.setAdapter(simpleCursorAdapter);
                    }
                }

                // Kill Code//
                return true;
            }
        });

        //<editor-fold desc="Runs When SearchView Is Closed">

        // Runs When SearchView Is Closed//
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                // Initiate sortByPreference Method//
                sortByPreference(workingNameUser);

                // Close Search Box//
                searchView.onActionViewCollapsed();

                // Kill Code//
                return true;
            }
        });

        //</editor-fold>

    }

    // Method That Lets User Pick Their Sort Preference//
    private void sortBy() {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Creates Dialog//
        new MaterialDialog.Builder(this)

                // Title Of Dialog//
                .title("Sort Events By...")

                // Items Of Dialog//
                .items("Sort By Name", "Sort By Date", "Sort By Location", "Sort By Newest To Oldest", "Sort By Oldest To Newest")

                // Positive Text Name For Button//
                .positiveText("Ok")

                // What Happens When Item Is Clicked//
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {

                        // Sets Sort Preference By Name//
                        if (i == 0) {

                            // Vibrates For 50 Mill//
                            vibe.vibrate(50);

                            // Clear Saved Value//
                            eventsSortType.edit().clear().apply();

                            // Save New Value Into Shared Preference//
                            eventsSortType.edit().putInt(EVENT_SORT_TYPE, SORT_BY_NAME).apply();

                            // Define and Instantiate Variable Intent EventsView//
                            Intent eventsView = new Intent(EventsView.this, EventsView.class);

                            // Start Activity EventsView//
                            startActivity(eventsView);

                            // Custom Transition//
                            overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
                        }

                        // Sets Sort Preference By Date//
                        if (i == 1) {

                            // Vibrates For 50 Mill//
                            vibe.vibrate(50);

                            // Clear Saved Value//
                            eventsSortType.edit().clear().apply();

                            // Save New Value Into Shared Preference//
                            eventsSortType.edit().putInt(EVENT_SORT_TYPE, SORT_BY_DATE).apply();

                            // Define and Instantiate Variable Intent eventsView//
                            Intent eventsView = new Intent(EventsView.this, EventsView.class);

                            // Start Activity EventsView//
                            startActivity(eventsView);

                            // Custom Transition//
                            overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
                        }

                        // Sets Sort Preference By Location//
                        if (i == 2) {

                            // Vibrates For 50 Mill//
                            vibe.vibrate(50);

                            // Clear Saved Value//
                            eventsSortType.edit().clear().apply();

                            // Save New Value Into Shared Preference//
                            eventsSortType.edit().putInt(EVENT_SORT_TYPE, SORT_BY_LOCATION).apply();

                            // Define and Instantiate Variable Intent eventsView//
                            Intent eventsView = new Intent(EventsView.this, EventsView.class);

                            // Start Activity UsersView//
                            startActivity(eventsView);

                            // Custom Transition//
                            overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
                        }

                        // Sets Sort Preference By Newest To Oldest//
                        if (i == 3) {

                            // Vibrates For 50 Mill//
                            vibe.vibrate(50);

                            // Clear Saved Value//
                            eventsSortType.edit().clear().apply();

                            // Save New Value Into Shared Preference//
                            eventsSortType.edit().putInt(EVENT_SORT_TYPE, SORT_BY_NEWEST_TO_OLDEST).apply();

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

                            // Clear Saved Value//
                            eventsSortType.edit().clear().apply();

                            // Save New Value Into Shared Preference//
                            eventsSortType.edit().putInt(EVENT_SORT_TYPE, SORT_BY_OLDEST_TO_NEWEST).apply();

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
                }).show();
    }

    // Method To Apply Sort By User Preference//
    private void sortByPreference(String workingNameUser) {

        // What Happens When User Wants Database Sorted By Name//
        if (eventsSortType.getInt(EVENT_SORT_TYPE, -1) == 0) {

            // Gets Rows In Database Based On Name Of User//
            cursor = EventsDatabase.db.rawQuery("SELECT * FROM new_events where nameUser LIKE '%" + workingNameUser + "%'" + "ORDER BY " + EventsDatabase.KEY_NAME_EVENT + " COLLATE NOCASE" + "," + EventsDatabase.KEY_DATE  + " ASC", null);

            // Initiate populateListView Method//
            populateListView(cursor);
        }

        // What Happens When User Wants Database Sorted By Date//
        else if (eventsSortType.getInt(EVENT_SORT_TYPE, -1) == 1) {

            // Gets Rows In Database Based On Date Of User//
            cursor = EventsDatabase.db.rawQuery("SELECT * FROM new_events where nameUser LIKE '%" + workingNameUser + "%'" + "ORDER BY " + EventsDatabase.KEY_DATE + "," + EventsDatabase.KEY_NAME_EVENT + " COLLATE NOCASE" + "," + EventsDatabase.KEY_LOCATION + " COLLATE NOCASE" + " ASC", null);

            // Initiate populateListView Method//
            populateListView(cursor);
        }

        // What Happens When User Wants Database Sorted By Location//
        else if (eventsSortType.getInt(EVENT_SORT_TYPE, -1) == 2) {

            // Gets Rows In Database Based On Date Of User//
            cursor = EventsDatabase.db.rawQuery("SELECT * FROM new_events where nameUser LIKE '%" + workingNameUser + "%'" + "ORDER BY " + EventsDatabase.KEY_LOCATION + "," + EventsDatabase.KEY_DATE + " COLLATE NOCASE" + " ASC", null);

            // Initiate populateListView Method//
            populateListView(cursor);
        }

        // What Happens When User Wants Database Sorted By Newest To Oldest//
        else if (eventsSortType.getInt(EVENT_SORT_TYPE, -1) == 3) {

            // Gets Rows In Database Based On Date Of User//
            cursor = EventsDatabase.db.rawQuery("SELECT * FROM new_events where nameUser LIKE '%" + workingNameUser + "%'" + "ORDER BY " + EventsDatabase.KEY_ROW_ID_NUMBER + " DESC", null);

            // Initiate populateListView Method//
            populateListView(cursor);
        }

        // What Happens When User Does Not Have A Preference//
        else {

            // Gets Rows In Database Based On Date Of User//
            cursor = EventsDatabase.db.rawQuery("SELECT * FROM new_events where nameUser LIKE '%" + workingNameUser + "%'", null);

            // Initiate populateListView Method//
            populateListView(cursor);
        }
    }
}
