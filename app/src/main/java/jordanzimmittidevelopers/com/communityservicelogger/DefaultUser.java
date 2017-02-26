package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import de.hdodenhof.circleimageview.CircleImageView;

import static jordanzimmittidevelopers.com.communityservicelogger.UsersDatabase.KEY_NAMES;

// DefaultUser Class Created By Jordan Zimmitti 2-26-17//
public class DefaultUser extends AppCompatActivity {

    //<editor-fold desc="Extra">

    // Define Variable Cursor cursor//
    private Cursor cursor;

    // Define Variable UsersDatabase usersDatabase//
    private UsersDatabase usersDatabase;

    // Define Variable Vibrator vibe//
    private Vibrator vibe;

    //</editor-fold>

    //<editor-fold desc="Widgets">

    // Define Variable ListView defaultUserListView//
    private ListView defaultUserListView;

    //</editor-fold>

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initiate applyTheme Method//
        applyTheme();

        // Initiate instantiateWidgets Method//
        instantiateWidgets();

        // Initiate usersDatabaseOpen Method//
        usersDatabaseOpen();

        // Initiate populateListView Method//
        populateListView();
    }

    // Method That Applies Theme By User Preference//
    private void applyTheme() {

        // Define And Instantiate Variable ThemePicker pickTheme//
        ThemePicker pickTheme = new ThemePicker();

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

    // Method To Populate ListView//
    private void populateListView() {

        // Gets All Rows Added To Database From Name//
        cursor = usersDatabase.getAllRowsName();

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
