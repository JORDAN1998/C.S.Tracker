package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;

// UsersEdit Class Created By Jordan Zimmitti 1-28-17//
public class UsersEdit extends AppCompatActivity {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Define Variable Cursor cursor//
    Cursor cursor;

    // Define Variable UsersDatabase usersDatabase//
    private UsersDatabase usersDatabase;

    // Define Variable Vibrator vibe//
    private Vibrator vibe;

    //</editor-fold>

    //<editor-fold desc="String">

    // String Of Id Values//
    private String passedVar = null;

    //</editor-fold>

    //</editor-fold>

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Starts UI For Activity//
        setContentView(R.layout.users_edit_ui);

        // Initiate databaseOpen Method//
        databaseOpen();

        // Initiate getDatabaseValues Method//
        getDatabaseValues();

        // Initiate InstantiateWidgets Method//
        instantiateWidgets();
    }

    // Method That Opens Database//
    private void databaseOpen() {

        // Instantiate Variable UsersDatabase usersDatabase//
        usersDatabase = new UsersDatabase(this);

        // Open Database//
        usersDatabase.open();
    }

    // Method To get Database Values//
    private void getDatabaseValues() {

        // Gets Id Of Last Clicked List View Item//
        passedVar = getIntent().getStringExtra(UsersView.key_row_id_number);

        // Gets Row//
        cursor = usersDatabase.getRow(passedVar);
    }

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable Vibrator vibe//
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        vibe.vibrate(50);
    }
}
