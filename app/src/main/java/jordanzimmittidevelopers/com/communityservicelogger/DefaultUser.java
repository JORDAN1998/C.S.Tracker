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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import de.hdodenhof.circleimageview.CircleImageView;

import static jordanzimmittidevelopers.com.communityservicelogger.UsersDatabase.KEY_NAMES;

// DefaultUser Class Created By Jordan Zimmitti 2-26-17//
public class DefaultUser extends AppCompatActivity {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Define Variable UsersDatabase usersDatabase//
    private UsersDatabase usersDatabase;

    // Define Variable Vibrator vibe//
    private Vibrator vibe;

    //</editor-fold>

    //<editor-fold desc="Shared Preference">

    // Name Of Preference And What Its Saving The Integer To//
    private static final String USER_MODE_NAME = "user_mode_name";

    // Apply Sort By Name//
    private final static String USER_NAME = "name of user";


    // Name Of Preference And What Its Saving The Integer To//
    private static final String SWITCH_STATE = "user_switch_state";

    // Apply Switch Un-Checked //
    private final static int CHECKED = 1;

    //</editor-fold>

    //<editor-fold desc="Widgets">

    // Define Variable ListView defaultUserListView//
    private ListView defaultUserListView;

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

        // Initiate listViewItemClick Method//
        listViewItemClick();

        // Initiate usersDatabaseOpen Method//
        usersDatabaseOpen();

        // Initiate populateListView Method//
        populateListView();
    }

    //Controls Back Button Functions//
    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        switch (keyCode) {

            // What Happens When Back Button Is Pressed//
            case KeyEvent.KEYCODE_BACK:

                // Define And Instantiate Variable SharedPreferences userSwitchState//
                SharedPreferences userSwitchState = getSharedPreferences(SWITCH_STATE, MODE_PRIVATE);

                // Clear Saved Value//
                userSwitchState.edit().clear().apply();

                // Save New Value Into Shared Preference//
                userSwitchState.edit().putInt(SWITCH_STATE, CHECKED).apply();

                // Define and Instantiate Variable Intent settings//
                Intent settings = new Intent(this, Settings.class);

                // Start Activity Settings//
                startActivity(settings);

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

        // Starts UI For Activity//
        setContentView(R.layout.default_user_ui);

        // Define And Instantiate RelativeLayout relativeLayout//
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.default_user_ui);

        // Night Mode Theme Extension Options//
        pickTheme.activityNightModeExtension(this, relativeLayout);
    }

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable ListView usersListView//
        defaultUserListView = (ListView) findViewById(R.id.defaultUserListView);

        // Instantiate Variable Vibrator vibe//
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    // Method To Set usersListView OnItemClickListener//
    public void listViewItemClick() {

        // What Happens When ListView Item is Clicked//
        defaultUserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {

                // Vibrates For 50 Mill//
                vibe.vibrate(50);

                // Create Dialog//
                new MaterialDialog.Builder(DefaultUser.this)

                        // Title Of Dialog//
                        .title("Are You Sure?")

                        // Content Of Dialog//
                        .content("Is this the user you want to save? All other users will be deleted but this one.")

                        // Positive Text Name For Button//
                        .positiveText("Ok")

                        // Negative Text Name For Button//
                        .negativeText("Cancel")

                        // What Happens When Positive Button Is Pressed//
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                // Vibrates For 50 Mill//
                                vibe.vibrate(50);

                                // Gets Row//
                                Cursor itemCursor = usersDatabase.getRow(String.valueOf(id));

                                // Define And Instantiate Variable String userName//
                                String userName = itemCursor.getString(UsersDatabase.COL_NAME);

                                // Query Database For All Rows//
                                Cursor usersCursor = UsersDatabase.db.query(true, UsersDatabase.DATABASE_TABLE, UsersDatabase.ALL_KEYS, null, null, null, null, KEY_NAMES + " COLLATE NOCASE" + " ASC", null);

                                // What Happens When usersCursor Doesn't Equal Null//
                                if (usersCursor != null) {

                                    // Move To First Row//
                                    usersCursor.moveToFirst();
                                }

                                // What Happens If There Is Another Row//
                                while (!usersCursor.isAfterLast()) {

                                    if (!userName.equals(usersCursor.getString(UsersDatabase.COL_NAME))) {

                                        // Define And Instantiate Variable Long userId//
                                        Long userId = usersCursor.getLong(usersCursor.getColumnIndex("_id"));

                                        // Define And Instantiate Variable EventsDatabase eventsDatabase//
                                        EventsDatabase eventsDatabase = new EventsDatabase(DefaultUser.this);

                                        // Open Database//
                                        eventsDatabase.open();

                                        // Delete All Events Of Specified User//
                                        eventsDatabase.deleteAllUserEvents(usersCursor.getString(UsersDatabase.COL_NAME));

                                        // Deletes Specific Item In ListView//
                                        usersDatabase.deleteRow(userId);
                                    }

                                    // Move To Next Row//
                                    usersCursor.moveToNext();
                                }

                                // Define And Instantiate Variable SharedPreferences userModeName//
                                SharedPreferences userModeName = getSharedPreferences(USER_MODE_NAME, MODE_PRIVATE);

                                // Clear Saved Value//
                                userModeName.edit().clear().apply();

                                // Save New Value Into Shared Preference//
                                userModeName.edit().putString(USER_NAME, userName).apply();

                                // Define And Instantiate Variable SettingsThemePicker pickTheme//
                                SettingsThemePicker pickTheme = new SettingsThemePicker();

                                // Fixes Back Button B/C Of Icon Color//
                                pickTheme.BackButtonFix(DefaultUser.this);
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
        });
    }

    // Method To Populate ListView//
    private void populateListView() {

        // Gets All Rows Added To Database From Name//
        final Cursor cursor = usersDatabase.getAllRowsName();

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
                            cardView.setCardBackgroundColor(ContextCompat.getColor(DefaultUser.this, R.color.grey));

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
                usersDatabase.getImage(DefaultUser.this, byteImage, usersViewCircleImage);

                // Kill Code//
                return row;
            }
        };

        // Sets Up Adapter Made Earlier / Shows Content From Database//
        defaultUserListView.setAdapter(simpleCursorAdapter);
    }

    // Method That Opens Database//
    private void usersDatabaseOpen() {

        // Instantiate Variable UsersDatabase usersDatabase//
        usersDatabase = new UsersDatabase(this);

        // Open Database//
        usersDatabase.open();
    }
}
