package jordanzimmittidevelopers.com.communityservicelogger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

// SettingsSyncData Class Created By Jordan Zimmitti 09-25-17//
public class SettingsSyncData extends AppCompatActivity {

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initiate applyTheme Method//
        applyTheme();
    }

    // Method That Applies Theme By User Preference//
    private void applyTheme() {

        // Define And Instantiate Variable SettingsThemePicker pickTheme//
        SettingsThemePicker pickTheme = new SettingsThemePicker();

        // Set Theme Based On User Preference//
        pickTheme.userTheme(this);

        // Starts UI For Activity//
        setContentView(R.layout.settings_sync_data_ui);

        // Define And Instantiate RelativeLayout relativeLayout//
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.settings_ui);

        // Night Mode Theme Extension Options//
        pickTheme.activityNightModeExtension(this, relativeLayout);
    }
}
