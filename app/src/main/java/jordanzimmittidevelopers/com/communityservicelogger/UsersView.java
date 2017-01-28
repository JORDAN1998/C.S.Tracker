package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;

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

    //<editor-fold desc="Shared Preference">

    // Saves Order To String//
    public static final String ORDER_TYPE = "order_type";

    // Apply Order//
    public static int ORDER_BY;

    // Order By Name//
    public final static int ORDER_BY_NAME = 1;

    // Order By Newest To Oldest//
    public final static int ORDER_BY_NEWEST_TO_OLDEST = 2;

    // Order By Newest To Oldest//
    public final static int ORDER_BY_OLDEST_TO_NEWEST = 3;

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

        // Initiate populateListView//
        populateListView();
    }

    // Creates Menu And All Its Components//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflates The Menu / This Adds Items To The Action Bar If It Is Present//
        getMenuInflater().inflate(R.menu.users_view_menu, menu);

        // Kill Code//
        return true;
    }

    // What Happens When Menu Buttons Are Clicked//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Figures Out What Menu Button Was Pressed//
        int id = item.getItemId();

        // What Happens When usersAddSave Is Pressed//
        if (id == R.id.usersOrderBy) {

            // Initiate Method orderBy//
            orderBy();

            // Kill Code//
            return true;
        }

        // Kill Code//
        return super.onOptionsItemSelected(item);
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

    // Method To Set ListView OnItemClickListener//
    public void listViewItemClick() {

        // What Happens When ListView Item is Clicked//
        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Vibrates For 50 Mill//
                vibe.vibrate(50);
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
    }

    // Method To Order Users By//
    private void orderBy() {

            // Vibrates For 50 Mill//
            vibe.vibrate(50);

            // Title String//
            String dialogTitle = "Order Events By...";

            // Order By... Strings//
            final String[] orderByString = {"Order By Name", "Order By Newest To Oldest", "Order By Oldest To Newest"};

            // Positive Btn String//
            String positiveBtn = "Ok";

            new MaterialDialog.Builder(this)
                    .title(dialogTitle)
                    .items((CharSequence[]) orderByString)
                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {

                            // Sets Order By Name//
                            if (i == 0) {

                                // Vibrates For 50 Mill//
                                vibe.vibrate(50);

                                // Saves Order Of Users//
                                SharedPreferences settings = getSharedPreferences(ORDER_TYPE, MODE_PRIVATE);
                                SharedPreferences.Editor edit;
                                edit = settings.edit();

                                // Vibrates For 50 Mill//
                                vibe.vibrate(50);

                                // Sets Order For Users//
                                ORDER_BY = ORDER_BY_NAME;
                                edit.clear();
                                edit.putInt("Custom_Order_By", ORDER_BY);
                                edit.apply();

                                // Define and Instantiate Variable Intent UsersView//
                                Intent usersView = new Intent(UsersView.this, UsersView.class);

                                // Start Activity UsersView//
                                startActivity(usersView);

                                // Custom Transition//
                                overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
                            }

                            // Sets Order By Newest To Oldest//
                            if (i == 1) {

                                // Vibrates For 50 Mill//
                                vibe.vibrate(50);

                                // Saves Order Of Users//
                                SharedPreferences settings = getSharedPreferences(ORDER_TYPE, MODE_PRIVATE);
                                SharedPreferences.Editor edit;
                                edit = settings.edit();

                                // Vibrates For 50 Mill//
                                vibe.vibrate(50);

                                // Sets Order For Users//
                                ORDER_BY = ORDER_BY_NEWEST_TO_OLDEST;
                                edit.clear();
                                edit.putInt("Custom_Order_By", ORDER_BY);
                                edit.apply();

                                // Define and Instantiate Variable Intent UsersView//
                                Intent usersView = new Intent(UsersView.this, UsersView.class);

                                // Start Activity UsersView//
                                startActivity(usersView);

                                // Custom Transition//
                                overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
                            }

                            // Sets Order By Oldest To Newest//
                            if (i == 2) {

                                // Vibrates For 50 Mill//
                                vibe.vibrate(50);

                                // Saves Order Of Users//
                                SharedPreferences settings = getSharedPreferences(ORDER_TYPE, MODE_PRIVATE);
                                SharedPreferences.Editor edit;
                                edit = settings.edit();

                                // Vibrates For 50 Mill//
                                vibe.vibrate(50);

                                // Sets Order For Users//
                                ORDER_BY = ORDER_BY_OLDEST_TO_NEWEST;
                                edit.clear();
                                edit.putInt("Custom_Order_By", ORDER_BY);
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

    // Method To Populate ListView//
    private void populateListView() {

        // Define And Instantiate Variable SharedPreferences order//
        SharedPreferences order = getSharedPreferences(ORDER_TYPE, MODE_PRIVATE);

        // Define Variable Cursor cursor//
        Cursor cursor = null;

        //<editor-fold desc="User Order Save Preference">

        // What Happens When User Wants Database Ordered By Name//
        if (order.getInt("Custom_Order_By", 0) == 1) {

            // Gets All Rows Added To Database From Name//
            cursor = usersDatabase.getAllRowsName();

        }

        // What Happens When User Wants Database Ordered By Newest To Oldest//
        else if (order.getInt("Custom_Order_By", 0) == 2) {

                // Gets All Rows Added To Database From Name//
                cursor = usersDatabase.getAllRowsNewestToOldest();

            }

        // What Happens When User Wants Database Ordered By Oldest To Newest//
         else if (order.getInt("Custom_Order_By", 0) == 3) {

            // Gets All Rows Added To Database From Oldest To Newest//
            cursor = usersDatabase.getAllRowsOldestToNewest();

        } else {

            // Gets All Rows Added To Database From Oldest To Newest//
            cursor = usersDatabase.getAllRowsOldestToNewest();
        }

        //</editor-fold>

        // Puts Rows Stored On Database Into A String Shown//
        final String[] fromFieldNames = new String[]{UsersDatabase.KEY_NAMES, UsersDatabase.KEY_AGE, UsersDatabase.KEY_ORGANIZATION, UsersDatabase.KEY_NAME_LETTER};

        // Takes String From Database And Sends It To Whatever Layout Widget You Want, Will Show Up In The Order String Is Made In//
        int[] toViewIDs = new int[]{R.id.usersViewName, R.id.usersViewAge, R.id.usersViewOrganization, R.id.usersViewNameLetter};

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
}
