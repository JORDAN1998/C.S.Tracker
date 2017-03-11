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
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

// RemindersView Class Created By Jordan Zimmitti 2-27-17//
public class RemindersView extends AppCompatActivity {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Puts Id Of Last Clicked ListView Item Into String//
    public final static String LIST_VIEW_ITEM_ID = null;

    // Define Variable Cursor cursor//
    private Cursor cursor;

    // Define Variable EventsDatabase eventsDatabase//
    private EventsDatabase eventsDatabase;

    // Define Variable RemindersDatabase remindersDatabase//
    private RemindersDatabase remindersDatabase;

    // Define Variable String workingNameUser//
    private String workingNameUser;

    // Define Variable Vibrator vibe//
    private Vibrator vibe;

    //</editor-fold>

    //<editor-fold desc="Shared Preference">

    // Name Of Preference And What Its Saving The Integer To//
    private static final String USER_MODE_NAME = "user_mode_name";

    // Apply Sort By Name//
    private final static String USER_NAME = "name of user";

    //</editor-fold>

    //<editor-fold desc="Widgets">

    // Define Variable ListView eventsListViews//
    private ListView remindersListView;

    //</editor-fold>

    //</editor-fold>

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initiate applyTheme Method//
        applyTheme();

        // Initiate getName Method//
        getName();

        // Initiate instantiateWidgets Method//
        instantiateWidgets();

        // Initiate listViewItemLongClick Method//
        listViewItemLongClick();

        // Initiate remindersDatabaseOpen Method//
        remindersDatabaseOpen();

        // Initiate populateListViewMethod//
        populateListView();
    }

    //What Happens When Back Button Is Pressed//
    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        switch (keyCode) {

            // What Happens When Back Button Is Pressed//
            case KeyEvent.KEYCODE_BACK:

                // Define and Instantiate Variable Intent EventsView//
                Intent eventsView = new Intent(this, EventsView.class);

                // Start Activity EventsView//
                startActivity(eventsView);

                // Custom Transition//
                overridePendingTransition(R.anim.slid_in, R.anim.slid_out);

                // Kill Code//
                return false;
            default:
                return false;
        }
    }

    // Method That Adds Reminder To Events//
    private void addToEvents(Long rowId) {

        // Instantiate Variable Cursor cursor / Query RemindersDatabase//
        cursor = RemindersDatabase.db.query(true, RemindersDatabase.DATABASE_TABLE, RemindersDatabase.ALL_KEYS, String.valueOf(rowId), null, null, null, null, null);

        // What Happens When cursor Doesn't Equal Null//
        if (cursor != null) {

            // Move To First Row//
            cursor.moveToFirst();
        }

        // Define And Instantiate Variable String date / Get Date Value From database//
        String date = cursor.getString(RemindersDatabase.COL_DATE);

        // Define And Instantiate Variable String location / Get Location Value From database//
        String location = cursor.getString(RemindersDatabase.COL_LOCATION);

        // Define And Instantiate Variable String name / Get Name Value From database//
        String name = cursor.getString(RemindersDatabase.COL_NAME_REMINDER);

        // Initiate eventsDatabaseOpen Method//
        eventsDatabaseOpen();

        // Insert Values Into eventsDatabase//
        eventsDatabase.insertRow(name, workingNameUser, date, location, "0:00", "0:00", "0:00", "0:00", String.valueOf(""), "", "", "");

        // Delete Row In RemindersDatabase//
        remindersDatabase.deleteRow(rowId);

        // Close Cursor//
        cursor.close();

        // Define and Instantiate Variable Intent EventsView//
        Intent eventsView = new Intent(this, EventsView.class);

        // Start Activity EventsView//
        startActivity(eventsView);

        // Custom Transition//
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }

    // Method That Applies Theme By User Preference//
    private void applyTheme() {

        // Define And Instantiate Variable SettingsThemePicker pickTheme//
        SettingsThemePicker pickTheme = new SettingsThemePicker();

        // Set Theme Based On User Preference//
        pickTheme.userTheme(this);

        // Starts UI For Activity//
        setContentView(R.layout.reminders_view_ui);

        // Define And Instantiate RelativeLayout relativeLayout//
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.reminders_view_ui);

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

    // Method To Get User Name//
    private void getName() {

        // Define And Instantiate Variable SharedPreferences userModeName//
        SharedPreferences userModeName = getSharedPreferences(USER_MODE_NAME, MODE_PRIVATE);

        // Set workingNameUser Equal To userModeName//
        workingNameUser = userModeName.getString(USER_NAME, "");
    }

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable ListView remindersListView//
        remindersListView = (ListView) findViewById(R.id.remindersListView);

        // Instantiate Variable Vibrator vibe//
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    // Method That Runs When ListView Item Is Long Clicked//
    public void listViewItemLongClick() {

        remindersListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {

                // Create Dialog//
                new MaterialDialog.Builder(RemindersView.this)

                        // Title Of Dialog//
                        .title("What Would You Like To Do")

                        // Content Of Dialog//
                        .content("edit user, delete user, or more options")

                        // Positive Text Name For Button//
                        .positiveText("Delete")

                        // Negative Text Name For Button//
                        .negativeText("Edit")

                        // Neutral Text Name For Button//
                        .neutralText("More")

                        // What Happens When Positive Button Is Pressed//
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                // Vibrates For 50 Mill//
                                vibe.vibrate(50);

                                // Deletes Specific Item In ListView//
                                remindersDatabase.deleteRow(id);

                                // Initiate populateListView Method//
                                populateListView();

                                // Restart EventsView Class//
                                Intent i = new Intent(RemindersView.this, RemindersView.class);
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

                                // Define and Instantiate Variable Intent RemindersEdit//
                                Intent remindersEdit = new Intent(RemindersView.this, RemindersEdit.class);

                                // Get Name Of Item Clicked In remindersListView//
                               remindersEdit.putExtra(LIST_VIEW_ITEM_ID, String.valueOf(id));

                                // Start Activity RemindersEdit//
                                startActivity(remindersEdit);

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

                                // Create Dialog//
                                new MaterialDialog.Builder(RemindersView.this)

                                        // Title Of Dialog//
                                        .title("More Options")

                                        // Content Of Dialog//
                                        .content("Pick the option you want")

                                        // Positive Text Name For Button//
                                        .positiveText("Add To Events")

                                        // Negative Text Name For Button//
                                        .negativeText("Cancel")

                                        // What Happens When Positive Button Is Pressed//
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                                // Vibrates For 50 Mill//
                                                vibe.vibrate(50);

                                                // Initiate addToEvents Method//
                                                addToEvents(id);
                                            }
                                        })

                                        //What Happens When Negative Button Is Pressed//
                                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                                // Vibrates For 50 Mill//
                                                vibe.vibrate(50);

                                            }
                                        }).show();
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

        // Define and Instantiate Variable Intent remindersAdd//
        Intent remindersAdd = new Intent(this, RemindersAdd.class);

        // Start Activity RemindersAdd//
        startActivity(remindersAdd);

        // Custom Transition//
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);

        // Close usersDatabase//
        remindersDatabase.close();
    }

    // Method To Open Reminders Database//
    private void remindersDatabaseOpen() {

        // Instantiate Variable RemindersDatabase remindersDatabase//
        remindersDatabase = new RemindersDatabase(this);

        // Open Database//
        remindersDatabase.open();
    }

    // Method To Populate ListView//
    private void populateListView() {

        // Instantiate Variable Cursor cursor / Get All Rows By Date//
        cursor = remindersDatabase.getAllRowsDate();

        // Move Database To Next Value//
        cursor.moveToFirst();

        // Puts Rows Stored On Database Into A String Shown//
        final String[] fromFieldNames = new String[]{RemindersDatabase.KEY_NAME_REMINDER, RemindersDatabase.KEY_DATE, RemindersDatabase.KEY_LOCATION};

        // Takes String From Database And Sends It To Whatever Layout Widget You Want, Will Show Up In The Order String Is Made In//
        int[] toViewIDs = new int[]{R.id.remindersName, R.id.remindersDate, R.id.remindersLocation};

        // Creates ListView Adapter Which Allows ListView Items To Be Seen//
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.reminders_view_design_ui, cursor, fromFieldNames, toViewIDs, 0) {

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
                            cardView.setCardBackgroundColor(ContextCompat.getColor(RemindersView.this, R.color.grey));

                            // Kill Code//
                            break;
                        }
                    }
                }

                //</editor-fold>

                //<editor-fold desc="Normal Date">

                // Define And Instantiate Variable String reverseDate//
                String reverseDate = cursor.getString(RemindersDatabase.COL_DATE);

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

                // Define And Instantiate Variable TextView remindersDate//
                TextView remindersDate = (TextView) row.findViewById(R.id.remindersDate);

                // Set eventsDate Text Equal To date//
                remindersDate.setText(date);

                //</editor-fold>

                // Kill Code//
                return row;
            }
        };

        // Sets Up Adapter Made Earlier / Shows Content From Database//
        remindersListView.setAdapter(simpleCursorAdapter);
    }
}
