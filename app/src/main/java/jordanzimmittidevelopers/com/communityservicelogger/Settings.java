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

    // Define Variable SharedPreferences userSwitchState//
    private SharedPreferences userSwitchState;

    // Name Of Preference And What Its Saving The Integer To//
    private static final String SWITCH_STATE = "user_switch_state";

    // Apply Switch Checked //
    private final static int UNCHECKED = 0;

    // Apply Switch Un-Checked //
    private final static int CHECKED = 1;

    //</editor-fold>

    //</editor-fold>

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initiate applyTheme Method//
        applyTheme();

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
        setContentView(R.layout.settings_ui);

        // Define And Instantiate RelativeLayout relativeLayout//
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.settings_ui);

        // Night Mode Theme Extension Options//
        pickTheme.activityNightModeExtension(this, relativeLayout);
    }

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable SwitchCompat usersModeSwitch//
        usersModeSwitch = (SwitchCompat) findViewById(R.id.usersModeSwitch);

        // Instantiate Variable SharedPreferences userSwitchState//
        userSwitchState = getSharedPreferences(SWITCH_STATE, MODE_PRIVATE);

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

                    // Vibrate For 50m//
                    vibe.vibrate(50);

                    // Clear Saved Value//
                    userSwitchState.edit().clear().apply();

                    // Save New Value Into Shared Preference//
                    userSwitchState.edit().putInt(SWITCH_STATE, CHECKED).apply();

                } else {

                    // Vibrate For 50m//
                    vibe.vibrate(50);

                    // Clear Saved Value//
                    userSwitchState.edit().clear().apply();

                    // Save New Value Into Shared Preference//
                    userSwitchState.edit().putInt(SWITCH_STATE, UNCHECKED).apply();

                    // Creates Dialog//
                    new MaterialDialog.Builder(Settings.this)

                            // Title Of Dialog//
                            .title("Warning")

                            // Content Of Dialog//
                            .content("Turning off users will get rid of all users but the one you choose; this cannot be undone. After clicking Ok, click the user profile that you want to save for single user mode")

                            // Positive Text Name For Button//
                            .positiveText("Ok")

                            // Negative Text Name For Button//
                            .negativeText("Cancel")

                            // What Happens When Positive Button Is Pressed//
                            .onPositive(new MaterialDialog.SingleButtonCallback() {

                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    // Vibrates For 50 Mill//
                                    vibe.vibrate(50);

                                    // Define and Instantiate Variable Intent settings//
                                    Intent settings = new Intent(Settings.this, DefaultUser.class);

                                    // Start Activity DefaultActivity//
                                    startActivity(settings);

                                    // Custom Transition//
                                    overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
                                }
                            })

                            // What Happens When Negative Button Is Pressed//
                            .onNegative(new MaterialDialog.SingleButtonCallback() {

                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    // Vibrates For 50 Mill//
                                    vibe.vibrate(50);

                                    // Check Switch//
                                    usersModeSwitch.setChecked(true);

                                }
                            }).show();
                }
            }
        });
    }

    // Method To Apply Switch Preference//
    private void switchPreference() {

        // What Happens When Switch Is Un-Checked//
        if (userSwitchState.getInt(SWITCH_STATE, -1) == 0) {

            // Un-Check Switch//
            usersModeSwitch.setChecked(false);
        }

        // What Happens When Switch Is Checked//
        else if (userSwitchState.getInt(SWITCH_STATE, -1) == 1) {

            // Check Switch//
            usersModeSwitch.setChecked(true);
        }

        // What Happens When No User Preference Is Saved//
        else {

            // Default Switch To Checked//
            usersModeSwitch.setChecked(true);
        }
    }

    // What Happens When SettingsThemePicker Is Clicked//
    public void onClickThemes(View view) {

        // Vibrate For 50m//
        vibe.vibrate(50);

        // Define and Instantiate Variable Intent themes//
        Intent themes = new Intent(this, SettingsThemePicker.class);

        // Start Activity themes//
        startActivity(themes);

        // Custom Transition//
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }
}
