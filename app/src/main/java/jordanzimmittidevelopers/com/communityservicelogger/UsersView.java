package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import de.hdodenhof.circleimageview.CircleImageView;

// UsersView Class Created By Jordan Zimmitti 1-21-17//
public class UsersView extends AppCompatActivity {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Define Variable UsersDatabase usersDatabase//
    private UsersDatabase usersDatabase;

    // Define Variable Vibrator Vibe//
    private Vibrator vibe;

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

        // Initiate InstantiateWidgets Method//
        instantiateWidgets();

        // Initiate databaseOpen Method//
        databaseOpen();

        // Initiate populateListView//
        populateListView();
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

        // Instantiate Variable ListView usersListView//
        usersListView = (ListView) findViewById(R.id.usersListView);

        // Instantiate Variable Vibrator vibe//
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
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
    }

    // Method To Populate ListView//
    private void populateListView() {

        // Gets All Rows Added To Database//
        final Cursor cursor = usersDatabase.getAllRows();

        // Puts Rows Stored On Database Into A String Shown//
        final String[] fromFieldNames = new String[]{UsersDatabase.KEY_NAMES, UsersDatabase.KEY_AGE, UsersDatabase.KEY_ORGANIZATION, UsersDatabase.KEY_NAME_LETTER};

        // Takes String From Database And Sends It To Whatever Layout Widget You Want, Will Show Up In The Order String Is Made In//
        int[] toViewIDs = new int[]{R.id.usersViewName, R.id.usersViewAge, R.id.usersViewOrganization, R.id.usersViewNameLetter};

        // Creates ListView Adapter Which Allows ListView Items To Be Seen//
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.users_view_design_ui, cursor, fromFieldNames, toViewIDs, 0) {

            // Access users_view_design_ui Widgets//
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                // Get Cursor Position//
                cursor.moveToPosition(position);

                // Get Row Of Database//
                final View row = super.getView(position, convertView, parent);

                // Define And Instantiate Variable CardView cardView//
                CardView cardView = (CardView) row.findViewById(R.id.cardView);

                // Set Background Color For cardView//
                cardView.setCardBackgroundColor(ContextCompat.getColor(UsersView.this, R.color.red));

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
