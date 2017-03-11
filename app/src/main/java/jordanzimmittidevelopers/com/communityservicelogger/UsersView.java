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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import de.hdodenhof.circleimageview.CircleImageView;

import static jordanzimmittidevelopers.com.communityservicelogger.UsersDatabase.KEY_NAMES;

// UsersView Class Created By Jordan Zimmitti 1-21-17//
public class UsersView extends AppCompatActivity {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Puts Id Of Last Clicked ListView Item Into String//
    public final static String LIST_VIEW_ITEM_ID = null;

    // Define Variable Cursor cursor//
    private Cursor cursor;

    // Define Variable UsersDatabase usersDatabase//
    private UsersDatabase usersDatabase;

    // Define Variable Vibrator vibe//
    private Vibrator vibe;

    //</editor-fold>

    //<editor-fold desc="Navigation Drawer Variables">

    // Define Variable UsersNavigationDrawer usersNavigationDrawer//
    private UsersNavigationDrawer usersNavigationDrawer;

    // UsersNavigationDrawer Items//
    private String[] items = new String[] {"Settings", "About"};

    //</editor-fold>

    //<editor-fold desc="Shared Preference">

    // Define Variable SharedPreference userSortType//
    private SharedPreferences userSortType;

    // Name Of Preference And What Its Saving The Integer To//
    private static final String USER_SORT_TYPE = "user_sort_type";

    // Apply Sort By Name//
    private final static int SORT_BY_NAME = 0;

    // Apply Sort By Newest To Oldest//
    private final static int SORT_BY_NEWEST_TO_OLDEST = 1;

    // Apply Sort By Newest To Oldest//
    private final static int SORT_BY_OLDEST_TO_NEWEST = 2;


    // Name Of Preference And What Its Saving The Integer To//
    private static final String USER_MODE_NAME = "user_mode_name";

    // Apply Sort By Name//
    private final static String USER_NAME = "name of user";

    //</editor-fold>

    //<editor-fold desc="Widgets">

    // Define Variable ListView usersListViews//
    private ListView usersListView;

    //</editor-fold>

    //</editor-fold>

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initiate applyTheme Method//
        applyTheme();

        // Initiate firstRun Method//
        firstRun();

        // Initiate InstantiateWidgets Method//
        instantiateWidgets();

        // Initiate listViewItemClick Method//
        listViewItemClick();

        // Initiate listViewItemLongClick Method//
        listViewItemLongClick();

        // Initiate navigationDrawer Method//
        navigationDrawer();

        // Initiate usersDatabaseOpen Method//
        usersDatabaseOpen();

        // Initiate sortByPreference Method//
        sortByPreference();
    }

    // Creates Menu And All Its Components//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflates The Menu / This Adds Items To The Action Bar If It Is Present//
        getMenuInflater().inflate(R.menu.users_view_menu, menu);

        // Initiate searchUser Method//
        searchUser(menu);

        // Kill Code//
        return true;
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
            usersNavigationDrawer.buttonToggle();

            // Kill Code//
            return true;
        }

        // What Happens When usersSortBy Is Pressed//
        if (id == R.id.usersSortBy) {

            // Vibrates For 50 Mill//
            vibe.vibrate(50);

            // Initiate Method sortBy//
            sortBy();

            // Kill Code//
            return true;
        }

        // Kill Code//
        return super.onOptionsItemSelected(item);
    }

    //What Happens When Back Button Is Pressed//
    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        switch (keyCode) {

            // What Happens When Back Button Is Pressed//
            case KeyEvent.KEYCODE_BACK:

                // Create Dialog//
                new MaterialDialog.Builder(UsersView.this)

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
                                usersDatabase.close();

                                // Intent To Kill app//
                                Intent killApp = new Intent(Intent.ACTION_MAIN);
                                killApp.addCategory(Intent.CATEGORY_HOME);
                                killApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(killApp);

                            }
                        }).show();

                // Kill Code//
                return false;
            default:
                return false;
        }
    }

    // Method That Applies Theme By User Preference//
    private void applyTheme() {

        // Define And Instantiate Variable SettingsThemePicker pickTheme//
        SettingsThemePicker pickTheme = new SettingsThemePicker();

        // Set Theme Based On User Preference//
        pickTheme.userTheme(this);

        // Starts UI For Activity//
        setContentView(R.layout.users_view_ui);

        // Define And Instantiate RelativeLayout relativeLayout//
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.users_view_ui);

        // Night Mode Theme Extension Options//
        pickTheme.activityNightModeExtension(this, relativeLayout);
    }

    // Method That Runs When App Is First Opened//
    private void firstRun() {

        // Define And Instantiate Variable SharedPreferences usersFirstTime//
        SharedPreferences usersFirstTime = getSharedPreferences("APPS_FIRST_RUN", MODE_PRIVATE);

        // What Happens When Activity Runs The First Time//
        if (usersFirstTime.getBoolean("APPS_FIRST_RUN", true)) {

            // Create Dialog//
            new MaterialDialog.Builder(UsersView.this)

                    // Title Of Dialog//
                    .title("Welcome To The New C.S.T")

                    // Content Of Dialog//
                    .content("Don't Worry, your events aren't gone. Please create a default user profile. This profile should be your name and your information. Once you create one click on it and all your events will show up. After, you can add more users if you want to log more than one person's community service hours. You can disable multiple user mode in settings as well!")

                    // Negative Text Name For Button//
                    .negativeText("Ok")

                    //What Happens When Negative Button Is Pressed//
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            // Vibrates For 50 Mill//
                            vibe.vibrate(50);

                        }
                    }).show();
        }

        // Record The Fact That The Activity Has Been Started At Least Once//
        usersFirstTime.edit().putBoolean("APPS_FIRST_RUN", false).apply();
    }

    // Method That Gets The Name Of The User Being Clicked On//
    private void getName(long id) {

        // Instantiate Variable Cursor cursor / Get Row From Item Clicked In usersListView//
        cursor = usersDatabase.getRow(String.valueOf(id));

        // Define And Instantiate Variable SharedPreferences userModeName//
        SharedPreferences userModeName = getSharedPreferences(USER_MODE_NAME, MODE_PRIVATE);

        // Clear Saved Value//
        userModeName.edit().clear().apply();

        // Save New Value Into Shared Preference//
        userModeName.edit().putString(USER_NAME, cursor.getString(UsersDatabase.COL_NAME)).apply();
    }

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable ListView usersListView//
        usersListView = (ListView) findViewById(R.id.usersListView);

        // Instantiate NavigationDrawer navigationDrawer//
        usersNavigationDrawer = (UsersNavigationDrawer) getSupportFragmentManager().findFragmentById(R.id.usersNavigationDrawer);

        // Instantiate Variable SharedPreference userSortType//
        userSortType = getSharedPreferences(USER_SORT_TYPE, MODE_PRIVATE);

        // Instantiate Variable Vibrator vibe//
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    // Method To Set usersListView OnItemClickListener//
    public void listViewItemClick() {

        // What Happens When ListView Item is Clicked//
        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Vibrates For 50 Mill//
                vibe.vibrate(50);

                // Define and Instantiate Variable Intent EventsView//
                Intent eventsView = new Intent(UsersView.this, EventsView.class);

                // Initiate getName Method//
                getName(id);

                // Start Activity EventsView//
                startActivity(eventsView);

                // Custom Transition//
                overridePendingTransition(R.anim.slid_in, R.anim.slid_out);

                // Close usersDatabase//
                usersDatabase.close();
            }
        });
    }

    // Method That Runs When ListView Item Is Long Clicked//
    public void listViewItemLongClick() {

        usersListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {

                // Create Dialog//
                new MaterialDialog.Builder(UsersView.this)

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

                                // Gets Row From UsersDatabase//
                                cursor = usersDatabase.getRow(String.valueOf(id));

                                // Define And Instantiate Variable EventsDatabase eventsDatabase//
                                EventsDatabase eventsDatabase = new EventsDatabase(UsersView.this);

                                // Open Database//
                                eventsDatabase.open();

                                // Delete All Events Of Specified User//
                                eventsDatabase.deleteAllUserEvents(cursor.getString(UsersDatabase.COL_NAME));

                                // Deletes Specific Item In ListView//
                                usersDatabase.deleteRow(id);

                                // Populates ListView Based On User Sort Preference//
                                sortByPreference();

                                // Restart UsersView Class//
                                Intent i = new Intent(UsersView.this, UsersView.class);
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
                                Intent usersEdit = new Intent(UsersView.this, UsersEdit.class);

                                // Get Id Of Item Clicked In userListView//
                                usersEdit.putExtra(LIST_VIEW_ITEM_ID, String.valueOf(id));

                                // Start Activity UsersAdd//
                                startActivity(usersEdit);

                                // Custom Transition//
                                overridePendingTransition(R.anim.slid_in, R.anim.slid_out);

                                // Close usersDatabase//
                                usersDatabase.close();
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

        // Sets Up NavigationDrawer//
        usersNavigationDrawer.setUp((DrawerLayout) findViewById(R.id.usersDrawerLayout), R.id.usersNavigationDrawer);

        // Add NavigationDrawer Items//
        usersNavigationDrawer.addItems(this, items);

        // Show Hamburger Icon//
        usersNavigationDrawer.showHamburgerIcon(true);
    }

    // What Happens When Fab Button Is Clicked//
    public void onClickFab(View view) {

        // Vibrate For 50m//
        vibe.vibrate(50);

        // Define and Instantiate Variable Intent UsersAdd//
        Intent UsersAdd = new Intent(this, UsersAdd.class);

        // Start Activity UsersAdd//
        startActivity(UsersAdd);

        // Custom Transition//
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);

        // Close usersDatabase//
        usersDatabase.close();
    }

    // Method To Populate ListView//
    private void populateListView(final Cursor cursor) {

        // Puts Rows Stored On Database Into A String Shown//
        final String[] fromFieldNames = new String[]{KEY_NAMES, UsersDatabase.KEY_AGE, UsersDatabase.KEY_GRADE, UsersDatabase.KEY_ORGANIZATION, UsersDatabase.KEY_NAME_LETTER};

        // Takes String From Database And Sends It To Whatever Layout Widget You Want, Will Show Up In The Order String Is Made In//
        int[] toViewIDs = new int[]{R.id.usersViewName, R.id.usersViewAge, R.id.usersViewGrade, R.id.usersViewOrganization, R.id.usersViewNameLetter};

        // Creates ListView Adapter Which Allows ListView Items To Be Seen//
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.users_view_design_ui, cursor, fromFieldNames, toViewIDs, 0) {

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
                if (switchState.getInt("night_mode_switch_state", 0) == 1) {

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
                            cardView.setCardBackgroundColor(ContextCompat.getColor(UsersView.this, R.color.grey));

                            // Kill Code//
                            break;
                        }
                    }
                }

                //</editor-fold>

                // Define And Instantiate Variable CircleImageView usersViewCircleImageView//
                CircleImageView usersViewCircleImage = (CircleImageView) row.findViewById(R.id.usersViewCircleImage);

                // Define And Instantiate Variable Byte byteImage//
                byte[] byteImage = cursor.getBlob(UsersDatabase.COL_IMAGE);

                // Get Image From Database And Display It In ListView//
                usersDatabase.getImage(UsersView.this, byteImage, usersViewCircleImage);

                // Kill Code//
                return row;
            }
        };

        // Sets Up Adapter Made Earlier / Shows Content From Database//
        usersListView.setAdapter(simpleCursorAdapter);
    }

    // Method That Searches For Specific User//
    private void searchUser(Menu menu) {

        // Define And Instantiate SearchView usersSearch//
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.usersSearch));

        // Set Query Hint For User//
        searchView.setQueryHint("Search Users");

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

                    // Query Database For What User Searched//
                    cursor = UsersDatabase.db.rawQuery("SELECT * FROM users where names LIKE '%" + searchText + "%'", null);

                    // What Happens If searchCursor Has Data//
                    if (cursor != null) {

                        // Move Database To Next Value//
                        cursor.moveToFirst();

                        // Puts Rows Stored On Database Into A String Shown//
                        final String[] fromFieldNames = new String[]{KEY_NAMES, UsersDatabase.KEY_AGE, UsersDatabase.KEY_ORGANIZATION, UsersDatabase.KEY_NAME_LETTER};

                        // Takes String From Database And Sends It To Whatever Layout Widget You Want, Will Show Up In The Order String Is Made In//
                        int[] toViewIDs = new int[]{R.id.usersViewName, R.id.usersViewAge, R.id.usersViewOrganization, R.id.usersViewNameLetter};

                        // Creates ListView Adapter Which Allows ListView Items To Be Seen//
                        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(UsersView.this, R.layout.users_view_design_ui, cursor, fromFieldNames, toViewIDs, 0) {

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
                                            cardView.setCardBackgroundColor(ContextCompat.getColor(UsersView.this, R.color.grey));

                                            // Kill Code//
                                            break;
                                        }
                                    }
                                }

                                //</editor-fold>

                                // Define And Instantiate Variable CircleImageView usersViewCircleImageView//
                                CircleImageView usersViewCircleImage = (CircleImageView) row.findViewById(R.id.usersViewCircleImage);

                                // Define And Instantiate Variable Byte byteImage//
                                byte[] byteImage = cursor.getBlob(UsersDatabase.COL_IMAGE);

                                // Get Image From Database And Display It In ListView//
                                usersDatabase.getImage(UsersView.this, byteImage, usersViewCircleImage);

                                // Kill Code//
                                return row;
                            }
                        };

                        // Sets Up Adapter Made Earlier / Shows Content From Database//
                        usersListView.setAdapter(simpleCursorAdapter);
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

                // Go Back To Original ListView//
                sortByPreference();

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

        // Creates Dialog//
        new MaterialDialog.Builder(this)

                // Title Of Dialog//
                .title("Sort Users By...")

                // Items Of Dialog//
                .items("Sort By Name", "Sort By Newest To Oldest", "Sort By Oldest To Newest")

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
                            userSortType.edit().clear().apply();

                            // Save New Value Into Shared Preference//
                            userSortType.edit().putInt(USER_SORT_TYPE, SORT_BY_NAME).apply();

                            // Define and Instantiate Variable Intent UsersView//
                            Intent usersView = new Intent(UsersView.this, UsersView.class);

                            // Start Activity UsersView//
                            startActivity(usersView);

                            // Custom Transition//
                            overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
                        }

                        // Sets Sort Preference By Newest To Oldest//
                        if (i == 1) {

                            // Vibrates For 50 Mill//
                            vibe.vibrate(50);

                            // Clear Saved Value//
                            userSortType.edit().clear().apply();

                            // Save New Value Into Shared Preference//
                            userSortType.edit().putInt(USER_SORT_TYPE, SORT_BY_NEWEST_TO_OLDEST).apply();

                            // Define and Instantiate Variable Intent UsersView//
                            Intent usersView = new Intent(UsersView.this, UsersView.class);

                            // Start Activity UsersView//
                            startActivity(usersView);

                            // Custom Transition//
                            overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
                        }

                        // Sets Sort Preference By Oldest To Newest//
                        if (i == 2) {

                            // Vibrates For 50 Mill//
                            vibe.vibrate(50);

                            // Clear Saved Value//
                            userSortType.edit().clear().apply();

                            // Save New Value Into Shared Preference//
                            userSortType.edit().putInt(USER_SORT_TYPE, SORT_BY_OLDEST_TO_NEWEST).apply();

                            // Define and Instantiate Variable Intent UsersView//
                            Intent usersView = new Intent(UsersView.this, UsersView.class);

                            // Start Activity UsersView//
                            startActivity(usersView);

                            // Custom Transition//
                            overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
                        }

                        // Kill Code//
                        return false;

                    }
                }).show();
    }

    // Method To Apply Sort By User Preference//
    private void sortByPreference() {

        // What Happens When User Wants Database Sorted By Name//
        if (userSortType.getInt(USER_SORT_TYPE, -1) == 0) {

            // Gets All Rows Added To Database From Name//
            cursor = usersDatabase.getAllRowsName();

            // Initiate populateListView Method//
            populateListView(cursor);
        }

        // What Happens When User Wants Database Sorted By Newest To Oldest//
        else if (userSortType.getInt(USER_SORT_TYPE, -1) == 1) {

            // Gets All Rows Added To Database From Name//
            cursor = usersDatabase.getAllRowsNewestToOldest();

            // Initiate populateListView Method//
            populateListView(cursor);
        }

        // What Happens When User Wants Database Sorted By Oldest To Newest//
        else if (userSortType.getInt(USER_SORT_TYPE, -1) == 2) {

            // Gets All Rows Added To Database From Oldest To Newest//
            cursor = usersDatabase.getAllRowsOldestToNewest();

            // Initiate populateListView Method//
            populateListView(cursor);
        }

        // What Happens When User Has No Preference//
        else {

            // Gets All Rows Added To Database From Oldest To Newest//
            cursor = usersDatabase.getAllRowsOldestToNewest();

            // Initiate populateListView Method//
            populateListView(cursor);
        }
    }

    // Method That Opens Database//
    private void usersDatabaseOpen() {

        // Instantiate Variable UsersDatabase usersDatabase//
        usersDatabase = new UsersDatabase(this);

        // Open Database//
        usersDatabase.open();
    }
}
