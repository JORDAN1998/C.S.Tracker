package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RelativeLayout;

// RemindersView Class Created By Jordan Zimmitti 2-27-17//
public class RemindersView extends AppCompatActivity {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Define Variable Cursor cursor//
    private Cursor cursor;

    // Define Variable EventsDatabase eventsDatabase//
    private EventsDatabase eventsDatabase;

    // Define Variable EventsDatabaseOld eventsDatabaseOld//
    private EventsDatabaseOld eventsDatabaseOld;

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


    // Puts Name Of Last Clicked ListView Item Into String//
    public static final String EVENTS_ADD_NAME_USER = null;

    // Define Variable String eventsAddNameUser / String Of Name Value//
    private String remindersAddNameUser = null;


    // Puts Name Of Last Clicked ListView Item Into String//
    public static final String EVENTS_VIEW_NAME_USER = null;

    // Define Variable String eventsViewNameUser / String Of Name Value//
    private String eventsViewNameUser = null;


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
    private static final String DEFAULT_USER_MODE_NAME = "default_user_mode_name";

    // Apply Sort By Name//
    private final static String DEFAULT_USER_NAME = "default name for single user";

    //</editor-fold>

    //<editor-fold desc="Widgets">

    // Define Variable ListView eventsListViews//
    private ListView eventsListView;

    // Define Variable MenuItem timeTotalAdded//
    private MenuItem timeTotalAdded;

    //</editor-fold>

    //</editor-fold>

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initiate applyTheme Method//
        applyTheme();
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

        // Gets Name Of Last Clicked List View Item//
        eventsAddNameUser = getIntent().getStringExtra(EVENTS_ADD_NAME_USER);

        // Gets Name Of Last Clicked List View Item//
        eventsExtraInfNameUser = getIntent().getStringExtra(EventsExtraInformation.EVENTS_EXTRA_INF_NAME_USER);

        // Gets Name Of Last Clicked List View Item//
        eventsViewNameUser = getIntent().getStringExtra(EVENTS_VIEW_NAME_USER);

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

        // What Happens When eventsViewNameUser Doesn't Equals Null//
        else if (eventsViewNameUser != null) {

            // Set Title Equal To usersViewNameUser//
            setTitle(eventsViewNameUser);

            // Set workingNameUser Equal To usersViewNameUser//
            workingNameUser = usersViewNameUser;
        }

        // What Happens When usersViewNameUser Doesn't Equals Null//
        else if (usersViewNameUser != null) {

            // Set Title Equal To usersViewNameUser//
            setTitle(usersViewNameUser);

            // Set workingNameUser Equal To usersViewNameUser//
            workingNameUser = usersViewNameUser;
        }

        else {

            // Define And Instantiate Variable SharedPreference defaultUserModeName//
            SharedPreferences defaultUserModeName = getSharedPreferences(DEFAULT_USER_MODE_NAME, MODE_PRIVATE);

            // Set workingNameUser Equal To defaultUserModeName//
            workingNameUser = defaultUserModeName.getString(DEFAULT_USER_NAME, "");

            // Set Title Equal To eventAddNameUser//
            setTitle(workingNameUser);
        }
    }
}
