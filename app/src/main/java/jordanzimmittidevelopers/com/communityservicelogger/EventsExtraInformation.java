package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

// EventsView Class Created By Jordan Zimmitti 2-11-17//
public class EventsExtraInformation extends AppCompatActivity {

    //<editor-fold desc="Variables">

    // Define Variable EventsDatabase eventsDatabase//
    private EventsDatabase eventsDatabase;

    // Puts Name Of Last Clicked ListView Item Into String//
    public static final String EVENTS_EXTRA_INF_NAME_USER = null;

    // Define Variable String eventsExtraInfNameUser//
    private String eventsExtraInfNameUser = null;

    //</editor-fold>

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Define And Instantiate Variable ThemePicker pickTheme//
        ThemePicker pickTheme = new ThemePicker();

        // Set Theme Based On User Preference//
        pickTheme.userTheme(this);

        // Starts UI For Activity//
        setContentView(R.layout.events_extra_information_ui);

        // Define And Instantiate RelativeLayout relativeLayout//
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.events_extra_information_ui);

        // Night Mode Theme Extension Options//
        pickTheme.activityNightModeExtension(this, relativeLayout);

        // Initiate eventsDatabase Open Method//
        eventsDatabaseOpen();

        // Initiate getItemIdValues Method//
        getItemIdValues();
    }

    //Controls Back Button Functions//
    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        switch (keyCode) {

            // What Happens When Back Button Is Pressed//
            case KeyEvent.KEYCODE_BACK:

                // Define and Instantiate Variable Intent EventsView//
                Intent eventsView = new Intent(this, EventsView.class);

                // Get Name From eventAddNameUser//
                eventsView.putExtra(EVENTS_EXTRA_INF_NAME_USER, eventsExtraInfNameUser);

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

    // Method To Get Extra Inf Values From Database//
    private void getItemIdValues() {

        // Define And Instantiate Variable String itemId / Gets Id Of Last Clicked List View Item//
        String itemId = getIntent().getStringExtra(EventsView.KEY_ROW_ID_NUMBER);

        // Define And Instantiate Variable Cursor cursor / Gets Row From itemId//
        Cursor cursor = eventsDatabase.getRow(itemId);

        // Instantiate Variable String name / Get Name Value Stored In Database//
        eventsExtraInfNameUser = cursor.getString(EventsDatabase.COL_NAME_USER);

        // Define And Instantiate Variable TextView eventsInfPeopleInCharge//
        TextView eventsInfPeopleInCharge = (TextView) findViewById(R.id.eventsInfPeopleInCharge);

        // Sets Text Equal To Value Stored In Database//
        eventsInfPeopleInCharge.setText(cursor.getString(EventsDatabase.COL_PEOPLE_IN_CHARGE));

        // Define And Instantiate Variable TextView eventsInfPhoneNumber//
        TextView eventsInfPhoneNumber = (TextView) findViewById(R.id.eventsInfPhoneNumber);

        // Sets Text Equal To Value Stored In Database//
        eventsInfPhoneNumber.setText(cursor.getString(EventsDatabase.COL_PHONE_NUMBER));

        // Define And Instantiate Variable TextView eventsInfNotes//
        TextView eventsInfNotes = (TextView) findViewById(R.id.eventsInfNotes);

        // Sets Text Equal To Value Stored In Database//
        eventsInfNotes.setText(cursor.getString(EventsDatabase.COL_NOTES));
    }

    // Method To Open Events Database//
    private void eventsDatabaseOpen() {

        // Instantiate Variable EventsDatabase eventsDatabase//
        eventsDatabase = new EventsDatabase(this);

        // Open Database//
        eventsDatabase.open();
    }
}
