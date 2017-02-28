package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

// Settings Class Created By Jordan Zimmitti 2-19-17//
public class DefaultActivity extends AppCompatActivity {

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initiate defaultActivity Method//
        defaultActivity();
    }

    // Method That Sets Default Activity//
    private void defaultActivity() {

        // Instantiate Variable SharedPreferences userSwitchState//
        SharedPreferences userSwitchState = getSharedPreferences("user_switch_state", MODE_PRIVATE);

        // What Happens When Switch Is Un-Checked//
        if (userSwitchState.getInt("user_switch_state", -1) == 0) {

            // Define And Instantiate Variable Intent eventsView//
            Intent eventsView = new Intent(this, EventsView.class);

            // Start Activity EventsView//
            startActivity(eventsView);

            // Custom Transition//
            overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
        }

        // What Happens When Switch Is Checked//
        else if (userSwitchState.getInt("user_switch_state", -1) == 1) {

            // Define And Instantiate Variable Intent usersView//
            Intent usersView = new Intent(this, UsersView.class);

            // Start Activity UsersView//
            startActivity(usersView);

            // Custom Transition//
            overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
        }

        // What Happens When No User Preference Is Saved//
        else {

            // Define And Instantiate Variable Intent usersView//
            Intent usersView = new Intent(this, UsersView.class);

            // Start Activity UsersView//
            startActivity(usersView);

            // Custom Transition//
            overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
        }
    }
}
