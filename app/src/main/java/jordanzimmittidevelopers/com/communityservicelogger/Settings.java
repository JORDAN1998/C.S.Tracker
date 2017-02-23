package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

// Settings Class Created By Jordan Zimmitti 2-19-17//
public class Settings extends AppCompatActivity {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Define Variable Vibrator vibe//
    private Vibrator vibe;

    // Define SwitchCompat usersModeSwitch//
    private SwitchCompat usersModeSwitch;

    //</editor-fold>

    //<editor-fold desc="Shared Preference">

    // Name Of Preference And What Its Saving The Integer To//
    private static final String SWITCH_STATE = "user_switch_state";

    // Apply Switch Checked //
    private final static int UNCHECKED = 1;

    // Apply Switch Un-Checked //
    private final static int CHECKED = 2;

    //</editor-fold>

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
        setContentView(R.layout.settings_ui);

        // Define And Instantiate RelativeLayout relativeLayout//
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.settings_ui);

        // Night Mode Theme Extension Options//
        pickTheme.activityNightModeExtension(this, relativeLayout);

        // Initiate instantiateWidgets Method//
        instantiateWidgets();

        // Initiate onCheckedChanged Method//
        onCheckedChanged();
    }

    //Controls Back Button Functions//
    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        switch (keyCode) {

            // What Happens When Back Button Is Pressed//
            case KeyEvent.KEYCODE_BACK:

                // Define and Instantiate Variable Intent UsersView//
                Intent usersView = new Intent(this, UsersView.class);

                // Start Activity UsersView//
                startActivity(usersView);

                // Custom Transition//
                overridePendingTransition(R.anim.slid_in, R.anim.slid_out);

                // Kill Code//
                return false;
            default:
                return false;
        }
    }

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable SwitchCompat usersModeSwitch//
        usersModeSwitch = (SwitchCompat) findViewById(R.id.usersModeSwitch);

        // Initiate switchPreference Method/
        switchPreference();

        // Instantiate Variable Vibrator vibe//
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    // Method That Runs When Switch Is Changed//
    private void onCheckedChanged() {

        // What Happens When Switch Is Changed On Or Off//
        usersModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // What Happens If Switch Is Checked//
                if(isChecked) {

                    // Saves Switch State To Shared Preference//
                    SharedPreferences settings = getSharedPreferences(SWITCH_STATE, MODE_PRIVATE);
                    SharedPreferences.Editor edit;
                    edit = settings.edit();

                    // Clear Saved Value//
                    edit.clear();

                    // Put New Value Into Shared Preference//
                    edit.putInt("switch_state", CHECKED);

                    // Save Value//
                    edit.apply();

                } else {

                    // Saves Switch State To Shared Preference//
                    SharedPreferences settings = getSharedPreferences(SWITCH_STATE, MODE_PRIVATE);
                    SharedPreferences.Editor edit;
                    edit = settings.edit();

                    // Clear Saved Value//
                    edit.clear();

                    // Put New Value Into Shared Preference//
                    edit.putInt("switch_state", UNCHECKED);

                    // Save Value//
                    edit.apply();

                    // Creates Dialog//
                    new MaterialDialog.Builder(Settings.this)

                            // Title Of Dialog//
                            .title("Warning")

                            // Content Of Dialog//
                            .content("Turning off users will get rid of all users but the one you choose; this cannot be undone. After clicking Ok, click the user profile that you want to save for single user mode")

                            // Positive Text Name For Button//
                            .positiveText("Yes")

                            // What Happens When Positive Button Is Pressed//
                            .onPositive(new MaterialDialog.SingleButtonCallback() {

                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    // Vibrates For 50 Mill//
                                    vibe.vibrate(50);



                                }
                            }).show();
                }
            }
        });
    }

    // Method To Apply Switch Preference//
    private void switchPreference() {

        // Default Switch To Checked//
        usersModeSwitch.setChecked(true);

        // Define And Instantiate Variable SharedPreferences switchState//
        SharedPreferences switchState = getSharedPreferences(SWITCH_STATE, MODE_PRIVATE);

        // What Happens When Switch Is Un-Checked//
        if (switchState.getInt("switch_state", 0) == 1) {

            // Un-Check Switch//
            usersModeSwitch.setChecked(false);
        }

        // What Happens When Switch Is Checked//
        if (switchState.getInt("switch_state", 0) == 2) {

            // Check Switch//
            usersModeSwitch.setChecked(true);
        }
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
