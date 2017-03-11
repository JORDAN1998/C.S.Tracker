package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;

public class SettingsAbout extends AppCompatActivity {

    // Define Variable Vibrator Vibe//
    Vibrator vibe;

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initiate applyTheme Method//
        applyTheme();

        // Initiate instantiateWidgets Method//
        instantiateWidgets();
    }

    //What Happens When Back Button Is Pressed//
    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        switch (keyCode) {

            // What Happens When Back Button Is Pressed//
            case KeyEvent.KEYCODE_BACK:

                // Define And Instantiate Variable SettingsThemePicker pickTheme//
                SettingsThemePicker pickTheme = new SettingsThemePicker();

                // Fixes Back Button B/C Of Icon Color//
                pickTheme.BackButtonFix(this);

                // Kill Code//
                return false;
            default:
                return false;
        }
    }

    // Method That Applies Theme By User Preference//
    private void applyTheme() {

        // Define And Instantiate Variable SettingsThemePicker pickTheme//
        SettingsThemePicker pickTheme = new SettingsThemePicker();

        // Set Theme Based On User Preference//
        pickTheme.userTheme(this);

        // Starts UI For Activity//
        setContentView(R.layout.settings_about_ui);

        // Define And Instantiate RelativeLayout relativeLayout//
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.settings_ui);

        // Night Mode Theme Extension Options//
        pickTheme.activityNightModeExtension(this, relativeLayout);
    }

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable Vibrator vibe//
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    // What Happens When version Is Clicked//
    public void onClickVersion(View view) {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Creates Dialog//
        new MaterialDialog.Builder(this)

                // Title Of Dialog//
                .title("Change Log (V. 5.0.0)")

                // Content Of Dialog//
                .content(R.string.changelog)

                // Positive Text Name For Button//
                .negativeText("Ok").show();
    }

    // What Happens When rateInPlayStore Is Clicked//
    public void onClickRateInPlayStore(View view) {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Send To App On Google Play//
        String myUrl ="https://play.google.com/store/apps/details?id=jordanzimmittidevelopers.com.communityservicelogger&hl=en";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(myUrl)));
    }

    // What Happens When feedback Is Clicked//
    public void onClickFeedback(View view) {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Send User E-Mail Desired App/
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        String[] recipients = new String[]{"jordanzimmitti@gmail.com", "",};
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Community Service Tracker Feedback");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "What I Like:                   What I Don't Like:");
        emailIntent.setType("text/plain");
        startActivity(emailIntent);
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }
}
