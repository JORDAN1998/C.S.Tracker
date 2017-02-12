package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import de.hdodenhof.circleimageview.CircleImageView;

import static jordanzimmittidevelopers.com.communityservicelogger.UsersDatabase.KEY_NAMES;

// UsersView Class Created By Jordan Zimmitti 1-21-17//
public class UsersView extends AppCompatActivity {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Puts Id Of Last Clicked ListView Item Into String//
    public final static String KEY_ROW_ID_NUMBER = UsersDatabase.KEY_ROW_ID_NUMBER;

    // Puts Name Of Last Clicked ListView Item Into String//
    public final static String USERS_VIEW_NAME_USER = null;

    // Define Variable UsersDatabase usersDatabase//
    private UsersDatabase usersDatabase;

    // Define Variable Vibrator vibe//
    private Vibrator vibe;

    //</editor-fold>

    //<editor-fold desc="Navigation Drawer Variables">

    // Define Variable NavigationDrawer navigationDrawer//
    private UsersNavigationDrawer usersNavigationDrawer;

    // NavigationDrawer Items//
    private String[] items = new String[] {"Reminders", "Settings"};

    //</editor-fold>

    //<editor-fold desc="Shared Preference">

    // Saves Sort Preference To String//
    public static final String SORT_TYPE = "sort_type";

    // Apply Sort Preference//
    public static int SORT_BY;

    // Sort By Name//
    public final static int SORT_BY_NAME = 1;

    // Sort By Newest To Oldest//
    public final static int SORT_BY_NEWEST_TO_OLDEST = 2;

    // Sort By Newest To Oldest//
    public final static int SORT_BY_OLDEST_TO_NEWEST = 3;

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

        // Set Title To Users//
        setTitle("Users");

        // Starts UI For Activity//
        setContentView(R.layout.users_view_ui);

        // Initiate databaseOpen Method//
        databaseOpen();

        // Initiate InstantiateWidgets Method//
        instantiateWidgets();

        // Initiate listViewItemClick Method//
        listViewItemClick();

        // Initiate listViewItemLongClick Method//
        listViewItemLongClick();

        // Initiate populateListView//
        populateListView();
    }

    // Creates Menu And All Its Components//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflates The Menu / This Adds Items To The Action Bar If It Is Present//
        getMenuInflater().inflate(R.menu.users_view_menu, menu);

        // Define And Instantiate Search View searchView//
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

                    // Initiate Search Title Method//
                    searchTitle(searchText);
                }

                // Kill Code//
                return true;
            }
        });

        // Runs When SearchView Is Closed//
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                // Repopulate Original ListView//
                populateListView();

                // Close Search Box//
                searchView.onActionViewCollapsed();

                // Kill Code//
                return true;
            }
        });

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
        }

        // What Happens When usersAddSave Is Pressed//
        if (id == R.id.usersSortBy) {

            // Initiate Method orderBy//
            sortBy();

            // Kill Code//
            return true;
        }

        // Kill Code//
        return super.onOptionsItemSelected(item);
    }

    //Controls Back Button Functions//
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

    // Method That Opens Database//
    private void databaseOpen() {

        // Instantiate Variable UsersDatabase usersDatabase//
        usersDatabase = new UsersDatabase(this);

        // Open Database//
        usersDatabase.open();
    }

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate / Set Up Navigation Drawer//
        //<editor-fold desc="Navigation Drawer">

        // Instantiate NavigationDrawer navigationDrawer//
        usersNavigationDrawer = (UsersNavigationDrawer) getSupportFragmentManager().findFragmentById(R.id.noteNavigationDrawer);

        // Sets Up NavigationDrawer//
        usersNavigationDrawer.setUp((DrawerLayout) findViewById(R.id.usersDrawerLayout), R.id.noteNavigationDrawer);

        // Add NavigationDrawer Items//
        usersNavigationDrawer.addItems(this, items);

        // Show Hamburger Icon//
        usersNavigationDrawer.showHamburgerIcon(true);

        //</editor-fold>

        // Instantiate Variable ListView usersListView//
        usersListView = (ListView) findViewById(R.id.usersListView);

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

                // Gets Row//
                Cursor cursor = usersDatabase.getRow(String.valueOf(id));

                // Get Name Of Item Clicked In userListView//
                eventsView.putExtra(USERS_VIEW_NAME_USER, cursor.getString(UsersDatabase.COL_NAME));

                // Start Activity EventsView//
                startActivity(eventsView);

                // Custom Transition//
                overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
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

                                // Deletes Specific ListView//
                                usersDatabase.deleteRow(id);

                                // populates ListView//
                                populateListView();

                                // Restart Note Class//
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

    // What Happens When Fab Btn Is Clicked//
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
    private void populateListView() {

        // Define And Instantiate Variable SharedPreferences order//
        SharedPreferences sort = getSharedPreferences(SORT_TYPE, MODE_PRIVATE);

        // Define Variable Cursor cursor//
        Cursor cursor;

        //<editor-fold desc="User Order Save Preference">

        // What Happens When User Wants Database Sorted By Name//
        if (sort.getInt("Custom_Order_By", 0) == 1) {

            // Gets All Rows Added To Database From Name//
            cursor = usersDatabase.getAllRowsName();

        }

        // What Happens When User Wants Database Sorted By Newest To Oldest//
        else if (sort.getInt("Custom_Order_By", 0) == 2) {

                // Gets All Rows Added To Database From Name//
                cursor = usersDatabase.getAllRowsNewestToOldest();

            }

        // What Happens When User Wants Database Sorted By Oldest To Newest//
         else if (sort.getInt("Custom_Order_By", 0) == 3) {

            // Gets All Rows Added To Database From Oldest To Newest//
            cursor = usersDatabase.getAllRowsOldestToNewest();

        } else {

            // Gets All Rows Added To Database From Oldest To Newest//
            cursor = usersDatabase.getAllRowsOldestToNewest();
        }

        //</editor-fold>

        // Puts Rows Stored On Database Into A String Shown//
        final String[] fromFieldNames = new String[]{KEY_NAMES, UsersDatabase.KEY_AGE, UsersDatabase.KEY_GRADE, UsersDatabase.KEY_ORGANIZATION, UsersDatabase.KEY_NAME_LETTER};

        // Takes String From Database And Sends It To Whatever Layout Widget You Want, Will Show Up In The Order String Is Made In//
        int[] toViewIDs = new int[]{R.id.usersViewName, R.id.usersViewAge, R.id.usersViewGrade, R.id.usersViewOrganization, R.id.usersViewNameLetter};

        // Make Above Cursor Final//
        final Cursor finalCursor = cursor;

        // Creates ListView Adapter Which Allows ListView Items To Be Seen//
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.users_view_design_ui, finalCursor, fromFieldNames, toViewIDs, 0) {

            // Access users_view_design_ui Widgets//
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                // Get Cursor Position//
                finalCursor.moveToPosition(position);

                // Get Row Of Database//
                final View row = super.getView(position, convertView, parent);

                // Define And Instantiate Variable CardView cardView//
                CardView cardView = (CardView) row.findViewById(R.id.cardView);

                // Set Background Color For cardView//
                cardView.setCardBackgroundColor(ContextCompat.getColor(UsersView.this, R.color.red));

                // Define And Instantiate Variable CircleImageView usersViewCircleImageView//
                CircleImageView usersViewCircleImage = (CircleImageView) row.findViewById(R.id.usersViewCircleImage);

                // Define And Instantiate Variable Byte byteImage//
                byte[] byteImage = finalCursor.getBlob(UsersDatabase.COL_IMAGE);

                // Get Image From Database And Display It In ListView//
                usersDatabase.getImage(UsersView.this, byteImage, usersViewCircleImage);

                // Kill Code//
                return row;
            }
        };

        // Sets Up Adapter Made Earlier / Shows Content From Database//
        usersListView.setAdapter(simpleCursorAdapter);
    }

    // Method That Searches Database By Title//
    public void searchTitle(String names) {

        // What And How The Database Is Searching//
        String query = "SELECT * FROM users where names LIKE '%" + names + "%'";

        // Query Database For What User Searched//
        Cursor searchCursor = UsersDatabase.db.rawQuery(query, null);

        // What Happens If searchCursor Has Data//
        if (searchCursor != null) {

            // Move Database To Next Value//
            searchCursor.moveToFirst();

            // Puts Rows Stored On Database Into A String Shown//
            final String[] fromFieldNames = new String[]{KEY_NAMES, UsersDatabase.KEY_AGE, UsersDatabase.KEY_ORGANIZATION, UsersDatabase.KEY_NAME_LETTER};

            // Takes String From Database And Sends It To Whatever Layout Widget You Want, Will Show Up In The Order String Is Made In//
            int[] toViewIDs = new int[]{R.id.usersViewName, R.id.usersViewAge, R.id.usersViewOrganization, R.id.usersViewNameLetter};

            // Make Above Cursor Final//
            final Cursor finalCursor = searchCursor;

            // Creates ListView Adapter Which Allows ListView Items To Be Seen//
            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.users_view_design_ui, finalCursor, fromFieldNames, toViewIDs, 0) {

                // Access users_view_design_ui Widgets//
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    // Get Cursor Position//
                    finalCursor.moveToPosition(position);

                    // Get Row Of Database//
                    final View row = super.getView(position, convertView, parent);

                    // Define And Instantiate Variable CardView cardView//
                    CardView cardView = (CardView) row.findViewById(R.id.cardView);

                    // Set Background Color For cardView//
                    cardView.setCardBackgroundColor(ContextCompat.getColor(UsersView.this, R.color.red));

                    // Define And Instantiate Variable CircleImageView usersViewCircleImageView//
                    CircleImageView usersViewCircleImage = (CircleImageView) row.findViewById(R.id.usersViewCircleImage);

                    // Define And Instantiate Variable Byte byteImage//
                    byte[] byteImage = finalCursor.getBlob(UsersDatabase.COL_IMAGE);

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

    // Method To Sort Users By User Preference//
    private void sortBy() {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Title String//
        String dialogTitle = "Sort Users By...";

        // Order By... Strings//
        final String[] orderByString = {"Sort By Name", "Sort By Newest To Oldest", "Sort By Oldest To Newest"};

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

                            // Saves Sort Preference Of Users//
                            SharedPreferences settings = getSharedPreferences(SORT_TYPE, MODE_PRIVATE);
                            SharedPreferences.Editor edit;
                            edit = settings.edit();

                            // Vibrates For 50 Mill//
                            vibe.vibrate(50);

                            // Sets Sort For Users//
                            SORT_BY = SORT_BY_NAME;
                            edit.clear();
                            edit.putInt("Custom_Order_By", SORT_BY);
                            edit.apply();

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

                            // Saves Sort Preference Of Users//
                            SharedPreferences settings = getSharedPreferences(SORT_TYPE, MODE_PRIVATE);
                            SharedPreferences.Editor edit;
                            edit = settings.edit();

                            // Vibrates For 50 Mill//
                            vibe.vibrate(50);

                            // Saves Sort Preference Of Users//
                            SORT_BY = SORT_BY_NEWEST_TO_OLDEST;
                            edit.clear();
                            edit.putInt("Custom_Order_By", SORT_BY);
                            edit.apply();

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

                            // Saves Sort Preference Of Users//
                            SharedPreferences settings = getSharedPreferences(SORT_TYPE, MODE_PRIVATE);
                            SharedPreferences.Editor edit;
                            edit = settings.edit();

                            // Vibrates For 50 Mill//
                            vibe.vibrate(50);

                            // Sets Save Preference For Users//
                            SORT_BY = SORT_BY_OLDEST_TO_NEWEST;
                            edit.clear();
                            edit.putInt("Custom_Order_By", SORT_BY);
                            edit.apply();

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
                })
                .positiveText(positiveBtn)
                .show();
    }
}
