package jordanzimmittidevelopers.com.communityservicelogger;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

// EventsView Class Created By Jordan Zimmitti 1-29-17//
public class EventsView extends AppCompatActivity {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Define Variable Cursor cursor//
    Cursor cursor;

    // Define Variable Vibrator vibe//
    private Vibrator vibe;

    //</editor-fold>

    //<editor-fold desc="String">

    // Define Variable String passedVar / String Of Name Values//
    private String passedVar = null;

    //</editor-fold>

    //<editor-fold desc="Widgets">

    //</editor-fold>

    //</editor-fold>

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Gets Name Of Last Clicked List View Item//
        passedVar = getIntent().getStringExtra(UsersView.KEY_NAMES);

        // Set Title Equal To PassedVAr
        setTitle(passedVar);

        // Starts UI For Activity//
        setContentView(R.layout.events_view_ui);
    }

    // What Happens When Fab Btn Is Clicked//
    public void onClickFab(View view) {
    }
}
