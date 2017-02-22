package jordanzimmittidevelopers.com.communityservicelogger;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

// ThemePicker Class Created By Jordan Zimmitti 2-19-17//
public class ThemePicker extends AppCompatActivity {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Define Static Variable Int GET_LOCATION//
    public static int GET_LOCATION;

    // Define Variable SwitchCompat nightModeSwitch//
    private SwitchCompat nightModeSwitch;

    // Define Variable Vibrator vibe//
    private Vibrator vibe;

    //</editor-fold>

    //<editor-fold desc="Shared Preference">

    // Name Of Preference And What Its Saving The Integer To//
    private static final String THEME_PICKER = "theme_picker";

    // Apply Theme Red //
    private final static int THEME_RED = 1;

    // Apply Theme Orange //
    private final static int THEME_ORANGE = 2;

    // Apply Theme Yellow //
    private final static int THEME_YELLOW = 3;

    // Apply Theme Green //
    private final static int THEME_GREEN = 4;

    // Apply Theme Blue //
    private final static int THEME_BLUE = 5;

    // Apply Theme Indigo //
    private final static int THEME_INDIGO = 6;

    // Apply Theme Violet //
    private final static int THEME_VIOLET = 7;

    // Apply Theme Pink //
    private final static int THEME_PINK = 8;


    // Name Of Preference And What Its Saving The Integer To//
    private static final String SWITCH_STATE = "switch_state";

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

        // Initiate userTheme Method//
        userTheme(this);

        // Starts UI For Activity//
        setContentView(R.layout.theme_picker_ui);

        // Define And Instantiate RelativeLayout relativeLayout//
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.theme_picker_ui);

        // Night Mode Theme Extension Options//
        activityNightModeExtension(this, relativeLayout);

        // Initiate getLocationPermission Method//
        getLocationPermission();

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

                // Define and Instantiate Variable Intent settings//
                Intent settings = new Intent(this, Settings.class);

                // Start Activity settings//
                startActivity(settings);

                // Custom Transition//
                overridePendingTransition(R.anim.slid_in, R.anim.slid_out);

                // Kill Code//
                return false;
            default:
                return false;
        }
    }

    // Method For Toggling On And Off Night Mode For Activity//
    private void activityNightMode(Context context) {

        // Find Night Mode Automatically//
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);

        // Checks Whether app Is In Night Mode Or Not//
        int currentNightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        // Checks All Scenarios//
        switch (currentNightMode) {

            // Night Mode Is Not Active, We're In Day Time//
            case Configuration.UI_MODE_NIGHT_NO: {

                // Initiate themeColors Method//
                themeColors(context);

                // Kill Code//
                break;
            }

            // Night Mode Is Active, We're At Night!//
            case Configuration.UI_MODE_NIGHT_YES: {

                // Set Dark Theme//
                context.setTheme(R.style.NightTheme);

                // Kill Code//
                break;
            }

            // We Don't Know What Mode We're In, Assume Notnight//
            case Configuration.UI_MODE_NIGHT_UNDEFINED: {

                // Initiate themeColors Method//
                themeColors(context);

                // Kill Code//
                break;
            }
        }
    }

    // Method For Black Background In Night Mode//
    public void activityNightModeExtension(Context context, RelativeLayout relativeLayout) {

        // Define And Instantiate Variable SharedPreferences switchState//
        SharedPreferences switchState = context.getSharedPreferences(SWITCH_STATE, MODE_PRIVATE);

        // What Happens When Switch Is Checked//
        if (switchState.getInt("switch_state", 0) == 2) {

            // Find Night Mode Automatically//
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);

            // Checks Whether app Is In Night Mode Or Not//
            int currentNightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

            // Checks All Scenarios//
            switch (currentNightMode) {

                // Night Mode Is Active, We're At Night!//
                case Configuration.UI_MODE_NIGHT_YES: {

                    // Set Black Background To Overall Night Theme//
                    relativeLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.black));

                    // Kill Code//
                    break;
                }
            }
        }
    }

    // Method To Gain Access To Location Permission//
    private void getLocationPermission() {

        // Get Current Activity//
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Checks If App Has Access To Specified Permission//
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                // Ask For Permission//
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        GET_LOCATION);


            }
        }
    }

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable SwitchCompat nightModeSwitch//
        nightModeSwitch = (SwitchCompat) findViewById(R.id.nightModeSwitch);

        // Initiate switchPreference Method/
        switchPreference();

        // Instantiate Variable Vibrator vibe//
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    // Method That Runs When Switch Is Changed//
    private void onCheckedChanged() {

        // What Happens When Switch Is Changed On Or Off//
        nightModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // What Happens If Switch Is Checked//
                if(isChecked) {

                    // Saves Switch State To Shared Preference//
                    SharedPreferences settings = getSharedPreferences(SWITCH_STATE, MODE_PRIVATE);
                    SharedPreferences.Editor edit;
                    edit = settings.edit();
                    Intent intent = getIntent();

                    // Clear Saved Value//
                    edit.clear();

                    // Put New Value Into Shared Preference//
                    edit.putInt("switch_state", CHECKED);

                    // Save Value//
                    edit.apply();

                    // Initiate activityNightModeMethod//
                    activityNightMode(ThemePicker.this);

                    // Restart The Activity//
                    finish();
                    startActivity(intent);
                    overridePendingTransition(R.anim.slid_in, R.anim.slid_out);

                } else {

                    // Saves Switch State To Shared Preference//
                    SharedPreferences settings = getSharedPreferences(SWITCH_STATE, MODE_PRIVATE);
                    SharedPreferences.Editor edit;
                    edit = settings.edit();
                    Intent intent = getIntent();

                    // Clear Saved Value//
                    edit.clear();

                    // Put New Value Into Shared Preference//
                    edit.putInt("switch_state", UNCHECKED);

                    // Save Value//
                    edit.apply();

                    // Initiate userTheme Method//
                    userTheme(ThemePicker.this);

                    // Restart The Activity//
                    finish();
                    startActivity(intent);
                    overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
                }

            }
        });
    }

    // What Happens When fabRed Is Clicked//
    public void onClickFabRed(View view) {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Saves Theme To Shared Preference//
        SharedPreferences settings = getSharedPreferences(THEME_PICKER, MODE_PRIVATE);
        SharedPreferences.Editor edit;
        edit = settings.edit();
        Intent intent = getIntent();

        // Clear Saved Value//
        edit.clear();

        // Put New Value Into Shared Preference//
        edit.putInt("theme_picker", THEME_RED);

        // Save Value//
        edit.apply();

        // Restart The Activity//
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }

    // What Happens When fabOrange Is Clicked//
    public void onClickFabOrange(View view) {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Saves Theme To Shared Preference//
        SharedPreferences settings = getSharedPreferences(THEME_PICKER, MODE_PRIVATE);
        SharedPreferences.Editor edit;
        edit = settings.edit();
        Intent intent = getIntent();

        // Clear Saved Value//
        edit.clear();

        // Put New Value Into Shared Preference//
        edit.putInt("theme_picker", THEME_ORANGE);

        // Save Value//
        edit.apply();

        // Restart The Activity//
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }

    // What Happens When fabYellow Is Clicked//
    public void onClickFabYellow(View view) {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Saves Theme To Shared Preference//
        SharedPreferences settings = getSharedPreferences(THEME_PICKER, MODE_PRIVATE);
        SharedPreferences.Editor edit;
        edit = settings.edit();
        Intent intent = getIntent();

        // Clear Saved Value//
        edit.clear();

        // Put New Value Into Shared Preference//
        edit.putInt("theme_picker", THEME_YELLOW);

        // Save Value//
        edit.apply();

        // Restart The Activity//
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }

    // What Happens When fabGreen Is Clicked//
    public void onClickFabGreen(View view) {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Saves Theme To Shared Preference//
        SharedPreferences settings = getSharedPreferences(THEME_PICKER, MODE_PRIVATE);
        SharedPreferences.Editor edit;
        edit = settings.edit();
        Intent intent = getIntent();

        // Clear Saved Value//
        edit.clear();

        // Put New Value Into Shared Preference//
        edit.putInt("theme_picker", THEME_GREEN);

        // Save Value//
        edit.apply();

        // Restart The Activity//
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }

    // What Happens When fabBlue Is Clicked//
    public void onClickFabBlue(View view) {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Saves Theme To Shared Preference//
        SharedPreferences settings = getSharedPreferences(THEME_PICKER, MODE_PRIVATE);
        SharedPreferences.Editor edit;
        edit = settings.edit();
        Intent intent = getIntent();

        // Clear Saved Value//
        edit.clear();

        // Put New Value Into Shared Preference//
        edit.putInt("theme_picker", THEME_BLUE);

        // Save Value//
        edit.apply();

        // Restart The Activity//
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }

    // What Happens When fabIndigo Is Clicked//
    public void onClickFabIndigo(View view) {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Saves Theme To Shared Preference//
        SharedPreferences settings = getSharedPreferences(THEME_PICKER, MODE_PRIVATE);
        SharedPreferences.Editor edit;
        edit = settings.edit();
        Intent intent = getIntent();

        // Clear Saved Value//
        edit.clear();

        // Put New Value Into Shared Preference//
        edit.putInt("theme_picker", THEME_INDIGO);

        // Save Value//
        edit.apply();

        // Restart The Activity//
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }

    // What Happens When fabViolet Is Clicked//
    public void onClickFabViolet(View view) {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Saves Theme To Shared Preference//
        SharedPreferences settings = getSharedPreferences(THEME_PICKER, MODE_PRIVATE);
        SharedPreferences.Editor edit;
        edit = settings.edit();
        Intent intent = getIntent();

        // Clear Saved Value//
        edit.clear();

        // Put New Value Into Shared Preference//
        edit.putInt("theme_picker", THEME_VIOLET);

        // Save Value//
        edit.apply();

        // Restart The Activity//
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }

    // What Happens When fabPink Is Clicked//
    public void onClickFabPink(View view) {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Saves Theme To Shared Preference//
        SharedPreferences settings = getSharedPreferences(THEME_PICKER, MODE_PRIVATE);
        SharedPreferences.Editor edit;
        edit = settings.edit();
        Intent intent = getIntent();

        // Clear Saved Value//
        edit.clear();

        // Put New Value Into Shared Preference//
        edit.putInt("theme_picker", THEME_PINK);

        // Save Value//
        edit.apply();

        // Restart The Activity//
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }

    // Method To Set Switch Preference//
    private void switchPreference() {

        // Define And Instantiate Variable SharedPreferences switchState//
        SharedPreferences switchState = getSharedPreferences(SWITCH_STATE, MODE_PRIVATE);

        // What Happens When Switch Is Un-Checked//
        if (switchState.getInt("switch_state", 0) == 1) {

            // Un-Check Switch//
            nightModeSwitch.setChecked(false);
        }

        // What Happens When Switch Is Checked//
        if (switchState.getInt("switch_state", 0) == 2) {

            // Check Switch//
            nightModeSwitch.setChecked(true);
        }
    }

    // Method That Sets Theme Based On User Preference//
    public void userTheme(Context context) {

        // Define And Instantiate Variable SharedPreferences switchState//
        SharedPreferences switchState = context.getSharedPreferences(SWITCH_STATE, MODE_PRIVATE);

        // What Happens When Switch Is Un-Checked//
        if (switchState.getInt("switch_state", 0) == 1) {

            // Initiate themeColors Method//
            themeColors(context);
        }

        // What Happens When Switch Is Checked//
        else if (switchState.getInt("switch_state", 0) == 2) {

            // Initiate activityNightMode Method//
            activityNightMode(context);
        }

        // What Happens If Switch Has No Saved State//
        else {

            // Initiate themeColors Method//
            themeColors(context);
        }
    }

    // Method That Chooses User Specified Theme Color//
    private void themeColors(Context context) {

        // Define And Instantiate Variable SharedPreferences theme//
        SharedPreferences themePicker = context.getSharedPreferences(THEME_PICKER, MODE_PRIVATE);

        // What Happens When User Wants Theme Red//
        if (themePicker.getInt("theme_picker", 0) == 1) {

            // Apply Theme Red//
            context.setTheme(R.style.RedTheme);
        }

        // What Happens When User Wants Theme Orange//
        if (themePicker.getInt("theme_picker", 0) == 2) {

            // Apply Theme Orange//
            context.setTheme(R.style.OrangeTheme);
        }

        // What Happens When User Wants Theme Yellow//
        if (themePicker.getInt("theme_picker", 0) == 3) {

            // Apply Theme Yellow//
            context.setTheme(R.style.YellowTheme);
        }

        // What Happens When User Wants Theme Green//
        if (themePicker.getInt("theme_picker", 0) == 4) {

            // Apply Theme Green//
            context.setTheme(R.style.GreenTheme);
        }

        // What Happens When User Wants Theme Blue//
        if (themePicker.getInt("theme_picker", 0) == 5) {

            // Apply Theme Blue//
            context.setTheme(R.style.BlueTheme);
        }

        // What Happens When User Wants Theme Indigo//
        if (themePicker.getInt("theme_picker", 0) == 6) {

            // Apply Theme Indigo//
            context.setTheme(R.style.IndigoTheme);
        }

        // What Happens When User Wants Theme Violet//
        if (themePicker.getInt("theme_picker", 0) == 7) {

            // Apply Theme Violet//
            context.setTheme(R.style.VioletTheme);
        }

        // What Happens When User Wants Theme Pink//
        if (themePicker.getInt("theme_picker", 0) == 8) {

            // Apply Theme Pink//
            context.setTheme(R.style.PinkTheme);
        }
    }
}
