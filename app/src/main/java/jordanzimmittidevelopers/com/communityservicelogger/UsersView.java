package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

// UsersView Class Created By Jordan Zimmitti 1-21-17//
public class UsersView extends AppCompatActivity {

    // Define Variable Vibrator vibe//
    Vibrator vibe;

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Starts UI For Activity//
        setContentView(R.layout.users_ui);

        // Instantiate Variable Vibrator vibe//
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    // What Happens When Fab Btn Is Clicked//
    public void onClickFab(View view) {

        // Start Activity UsersAdd//
        Intent UsersAdd = new Intent(this, UsersAdd.class);
        startActivity(UsersAdd);
    }
}