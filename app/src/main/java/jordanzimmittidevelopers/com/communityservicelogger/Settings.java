package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

// Settings Class Created By Jordan Zimmitti 2-19-17//
public class Settings extends AppCompatActivity {

    //<editor-fold desc="Variables">

    // Define Variable Vibrator vibe//
    private Vibrator vibe;

    //</editor-fold>

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Starts UI For Activity//
        setContentView(R.layout.settings_ui);

        // Initiate instantiateWidgets Method//
        instantiateWidgets();
    }

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable Vibrator vibe//
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    // What Happens When ThemePicker Is Clicked//
    public void onClickThemes(View view) {

        // Vibrate For 50m//
        vibe.vibrate(50);

        // Define and Instantiate Variable Intent themes//
        Intent themes = new Intent(this, ThemePicker.class);

        // Start Activity themes//
        startActivity(themes);

        // Custom Transition//
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }
}
