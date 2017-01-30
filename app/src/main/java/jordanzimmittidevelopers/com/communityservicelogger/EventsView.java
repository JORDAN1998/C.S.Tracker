package jordanzimmittidevelopers.com.communityservicelogger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

// EventsView Class Created By Jordan Zimmitti 1-29-17//
public class EventsView extends AppCompatActivity {

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Starts UI For Activity//
        setContentView(R.layout.events_view_ui);
    }

    // What Happens When Fab Btn Is Clicked//
    public void onClickFab(View view) {
    }
}
