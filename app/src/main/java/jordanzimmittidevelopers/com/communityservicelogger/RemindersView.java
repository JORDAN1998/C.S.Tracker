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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

// RemindersView Class Created By Jordan Zimmitti 2-27-17//
public class RemindersView extends AppCompatActivity {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Define Variable Cursor cursor//
    private Cursor cursor;

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

        // Initiate instantiateWidgets Method//
        instantiateWidgets();

        // Initiate remindersDatabaseOpen Method//
        remindersDatabaseOpen();

        // Initiate populateListViewMethod//
        populateListView();
    }

    //Controls Back Button Functions//
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

    // Method That Applies Theme By User Preference//
    private void applyTheme() {

        // Define And Instantiate Variable SettingsThemePicker pickTheme//
        SettingsThemePicker pickTheme = new SettingsThemePicker();

        // Set Theme Based On User Preference//
        pickTheme.userTheme(this);

        // Initiate getTitle Method//
        getName();

        // Starts UI For Activity//
        setContentView(R.layout.reminders_view_ui);

        // Define And Instantiate RelativeLayout relativeLayout//
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.reminders_view_ui);

        // Night Mode Theme Extension Options//
        pickTheme.activityNightModeExtension(this, relativeLayout);
    }

    // Method To Get User Name//
    private void getName() {

        // Define And Instantiate Variable SharedPreferences userModeName//
        SharedPreferences userModeName = getSharedPreferences(USER_MODE_NAME, MODE_PRIVATE);

        // Set workingNameUser Equal To defaultUserModeName//
        workingNameUser = userModeName.getString(USER_NAME, "");
    }

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable ListView remindersListView//
        remindersListView = (ListView) findViewById(R.id.remindersListView);

        // Instantiate Variable Vibrator vibe//
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    // What Happens When Fab Btn Is Clicked//
    public void onClickFab(View view) {

        // Vibrate For 50m//
        vibe.vibrate(50);

        // Define and Instantiate Variable Intent EventsAdd//
        Intent eventsAdd = new Intent(this, EventsAdd.class);

        // Start Activity EventsAdd//
        startActivity(eventsAdd);

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
        remindersListView.setAdapter(simpleCursorAdapter);
    }
}
