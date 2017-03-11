package jordanzimmittidevelopers.com.communityservicelogger;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
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

// SettingsThemePicker Class Created By Jordan Zimmitti 2-19-17//
public class SettingsThemePicker extends AppCompatActivity {

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

    // Define Variable SharedPreference themePicker//
    private SharedPreferences themePicker;

    // Name Of Preference And What Its Saving The Integer To//
    private static final String THEME_PICKER = "theme_picker";

    // Apply Theme Red //
    private final static int THEME_RED = 0;

    // Apply Theme Orange //
    private final static int THEME_ORANGE = 1;

    // Apply Theme Yellow //
    private final static int THEME_YELLOW = 2;

    // Apply Theme Green //
    private final static int THEME_GREEN = 3;

    // Apply Theme Blue //
    private final static int THEME_BLUE = 4;

    // Apply Theme Indigo //
    private final static int THEME_INDIGO = 5;

    // Apply Theme Violet //
    private final static int THEME_VIOLET = 6;

    // Apply Theme Pink //
    private final static int THEME_PINK = 7;


    // Define Variable SharedPreference nightModeSwitchState//
    private SharedPreferences nightModeSwitchState;

    // Name Of Preference And What Its Saving The Integer To//
    private static final String SWITCH_STATE = "night_mode_switch_state";

    // Apply Switch Checked //
    private final static int UNCHECKED = 0;

    // Apply Switch Un-Checked //
    private final static int CHECKED = 1;

    //</editor-fold>

    //<editor-fold desc="ComponentName">

    // Define Variable ComponentName iconBlue//
    private ComponentName iconBlue;

    // Define Variable ComponentName iconGreen//
    private ComponentName iconGreen;

    // Define Variable ComponentName iconIndigo//
    private ComponentName iconIndigo;

    // Define Variable ComponentName iconOrange//
    private ComponentName iconOrange;

    // Define Variable ComponentName iconPink//
    private ComponentName iconPink;

    // Define Variable ComponentName iconRed//
    private ComponentName iconRed;

    // Define Variable ComponentName iconViolet//
    private ComponentName iconViolet;

    // Define Variable ComponentName iconYellow//
    private ComponentName iconYellow;

    //</editor-fold>

    //</editor-fold>

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initiate applyTheme Method//
        applyTheme();

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

    // Method That Applies Theme By User Preference//
    private void applyTheme() {

        // Initiate userTheme Method//
        userTheme(this);

        // Starts UI For Activity//
        setContentView(R.layout.settings_theme_picker_ui);

        // Define And Instantiate RelativeLayout relativeLayout//
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.theme_picker_ui);

        // Night Mode Theme Extension Options//
        activityNightModeExtension(this, relativeLayout);
    }

    // Method For Toggling On And Off Night Mode For Activity//
    private void activityNightMode(Context context) {

        // Find Night Mode Automatically//
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);

        // Checks Whether The App Is In Night Mode Or Not//
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

            // We Don't Know What Mode We're In, Assume Not Night//
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

        // Instantiate Variable SharedPreference nightModeSwitchState//
        nightModeSwitchState = context.getSharedPreferences(SWITCH_STATE, MODE_PRIVATE);

        // What Happens When Switch Is Checked//
        if (nightModeSwitchState.getInt(SWITCH_STATE, -1) == 1) {

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

    // Method That Fixes Back Button B/C Of Icon Color//
    public void BackButtonFix(Context context) {

        // Initiate instantiateIconColorActivities Method//
        instantiateIconColorActivities(context);

        // Define And Instantiate Variable Intent defaultColorActivity//
        Intent defaultColorActivity = new Intent();

        // Instantiate Variable SharedPreferences themePicker//
        themePicker = context.getSharedPreferences(THEME_PICKER, MODE_PRIVATE);

        // What Happens When User Wants Theme Red//
        if (themePicker.getInt(THEME_PICKER, -1) == 0) {

            // Set defaultColorActivity To iconRed//
            defaultColorActivity.setComponent(iconRed);

            // Start Activity defaultColorActivity//
            context.startActivity(defaultColorActivity);

            // Custom Transition//
            ((Activity) context).overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
        }

        // What Happens When User Wants Theme Orange//
        else if (themePicker.getInt(THEME_PICKER, -1) == 1) {

            // Set defaultColorActivity To iconOrange//
            defaultColorActivity.setComponent(iconOrange);

            // Start Activity defaultColorActivity//
            context.startActivity(defaultColorActivity);

            // Custom Transition//
            ((Activity) context).overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
        }

        // What Happens When User Wants Theme Yellow//
        else if (themePicker.getInt(THEME_PICKER, -1) == 2) {

            // Set defaultColorActivity To iconYellow//
            defaultColorActivity.setComponent(iconYellow);

            // Start Activity defaultColorActivity//
            context.startActivity(defaultColorActivity);

            // Custom Transition//
            ((Activity) context).overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
        }

        // What Happens When User Wants Theme Green//
        else if (themePicker.getInt(THEME_PICKER, -1) == 3) {

            // Set defaultColorActivity To iconGreen//
            defaultColorActivity.setComponent(iconGreen);

            // Start Activity defaultColorActivity//
            context.startActivity(defaultColorActivity);

            // Custom Transition//
            ((Activity) context).overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
        }

        // What Happens When User Wants Theme Blue//
        else if (themePicker.getInt(THEME_PICKER, -1) == 4) {

            // Set defaultColorActivity To iconBlue//
            defaultColorActivity.setComponent(iconBlue);

            // Start Activity defaultColorActivity//
            context.startActivity(defaultColorActivity);

            // Custom Transition//
            ((Activity) context).overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
        }

        // What Happens When User Wants Theme Indigo//
        else if (themePicker.getInt(THEME_PICKER, -1) == 5) {

            // Set defaultColorActivity To iconIndigo//
            defaultColorActivity.setComponent(iconIndigo);

            // Start Activity defaultColorActivity//
            context.startActivity(defaultColorActivity);

            // Custom Transition//
            ((Activity) context).overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
        }

        // What Happens When User Wants Theme Violet//
        else if (themePicker.getInt(THEME_PICKER, -1) == 6) {

            // Set defaultColorActivity To iconViolet//
            defaultColorActivity.setComponent(iconViolet);

            // Start Activity defaultColorActivity//
            context.startActivity(defaultColorActivity);

            // Custom Transition//
            ((Activity) context).overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
        }

        // What Happens When User Wants Theme Pink//
        else if (themePicker.getInt(THEME_PICKER, -1) == 7) {

            // Set defaultColorActivity To iconPink//
            defaultColorActivity.setComponent(iconPink);

            // Start Activity defaultColorActivity//
            context.startActivity(defaultColorActivity);

            // Custom Transition//
            ((Activity) context).overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
        }

        // What Happens When No User Theme is Specified//
        else {

            // Define and Instantiate Variable Intent DefaultActivity//
            Intent defaultActivity = new Intent(this, DefaultActivity.class);

            // Start Activity DefaultActivity//
            context.startActivity(defaultActivity);

            // Custom Transition//
            ((Activity) context).overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
        }
    }

    // Method To Gain Access To Location Permission//
    private void getLocationPermission() {

        // Get Current Activity//
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // What Happens If User Did Not Give Permission For App To Use Location//
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Ask For Permission//
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GET_LOCATION);
            }
        }
    }

    // Method That Changes App Icon Based On Theme//
    public void iconColor() {

        // Initiate instantiateIconColorActivities Method//
        instantiateIconColorActivities(this);

        // Define And Instantiate Variable PackageManager packageManager//
        PackageManager packageManager = getPackageManager();

        // Instantiate Variable SharedPreferences themePicker//
        themePicker = getSharedPreferences(THEME_PICKER, MODE_PRIVATE);

        // What Happens When User Wants Theme Red//
        if (themePicker.getInt(THEME_PICKER, -1) == 0) {

            // Hide Blue Application Icon//
            packageManager.setComponentEnabledSetting(iconBlue, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Green Application Icon//
            packageManager.setComponentEnabledSetting(iconGreen, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Indigo Application Icon//
            packageManager.setComponentEnabledSetting(iconIndigo, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Orange Application Icon//
            packageManager.setComponentEnabledSetting(iconOrange, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Pink Application Icon//
            packageManager.setComponentEnabledSetting(iconPink, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Un-Hide Red Application Icon//
            packageManager.setComponentEnabledSetting(iconRed, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

            // Hide Violet Application Icon//
            packageManager.setComponentEnabledSetting(iconViolet, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Yellow Application Icon//
            packageManager.setComponentEnabledSetting(iconYellow, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }

        // What Happens When User Wants Theme Orange//
        if (themePicker.getInt(THEME_PICKER, -1) == 1) {

            // Hide Blue Application Icon//
            packageManager.setComponentEnabledSetting(iconBlue, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Green Application Icon//
            packageManager.setComponentEnabledSetting(iconGreen, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Indigo Application Icon//
            packageManager.setComponentEnabledSetting(iconIndigo, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Un-Hide Orange Application Icon//
            packageManager.setComponentEnabledSetting(iconOrange, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

            // Hide Pink Application Icon//
            packageManager.setComponentEnabledSetting(iconPink, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Red Application Icon//
            packageManager.setComponentEnabledSetting(iconRed, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Violet Application Icon//
            packageManager.setComponentEnabledSetting(iconViolet, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Yellow Application Icon//
            packageManager.setComponentEnabledSetting(iconYellow, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }

        // What Happens When User Wants Theme Yellow//
        if (themePicker.getInt(THEME_PICKER, -1) == 2) {

            // Hide Blue Application Icon//
            packageManager.setComponentEnabledSetting(iconBlue, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Green Application Icon//
            packageManager.setComponentEnabledSetting(iconGreen, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Indigo Application Icon//
            packageManager.setComponentEnabledSetting(iconIndigo, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Orange Application Icon//
            packageManager.setComponentEnabledSetting(iconOrange, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Pink Application Icon//
            packageManager.setComponentEnabledSetting(iconPink, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Red Application Icon//
            packageManager.setComponentEnabledSetting(iconRed, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Violet Application Icon//
            packageManager.setComponentEnabledSetting(iconViolet, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Un-Hide Yellow Application Icon//
            packageManager.setComponentEnabledSetting(iconYellow, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        }

        // What Happens When User Wants Theme Green//
        if (themePicker.getInt(THEME_PICKER, -1) == 3) {

            // Hide Blue Application Icon//
            packageManager.setComponentEnabledSetting(iconBlue, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Un-Hide Green Application Icon//
            packageManager.setComponentEnabledSetting(iconGreen, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

            // Hide Indigo Application Icon//
            packageManager.setComponentEnabledSetting(iconIndigo, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Orange Application Icon//
            packageManager.setComponentEnabledSetting(iconOrange, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Pink Application Icon//
            packageManager.setComponentEnabledSetting(iconPink, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Red Application Icon//
            packageManager.setComponentEnabledSetting(iconRed, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Violet Application Icon//
            packageManager.setComponentEnabledSetting(iconViolet, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Yellow Application Icon//
            packageManager.setComponentEnabledSetting(iconYellow, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }

        // What Happens When User Wants Theme Blue//
        if (themePicker.getInt(THEME_PICKER, -1) == 4) {

            // Un-Hide Blue Application Icon//
            packageManager.setComponentEnabledSetting(iconBlue, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

            // Hide Green Application Icon//
            packageManager.setComponentEnabledSetting(iconGreen, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Indigo Application Icon//
            packageManager.setComponentEnabledSetting(iconIndigo, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Orange Application Icon//
            packageManager.setComponentEnabledSetting(iconOrange, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Pink Application Icon//
            packageManager.setComponentEnabledSetting(iconPink, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Red Application Icon//
            packageManager.setComponentEnabledSetting(iconRed, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Violet Application Icon//
            packageManager.setComponentEnabledSetting(iconViolet, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Yellow Application Icon//
            packageManager.setComponentEnabledSetting(iconYellow, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }

        // What Happens When User Wants Theme Indigo//
        if (themePicker.getInt(THEME_PICKER, -1) == 5) {

            // Hide Blue Application Icon//
            packageManager.setComponentEnabledSetting(iconBlue, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Green Application Icon//
            packageManager.setComponentEnabledSetting(iconGreen, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Un-Hide Indigo Application Icon//
            packageManager.setComponentEnabledSetting(iconIndigo, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

            // Hide Orange Application Icon//
            packageManager.setComponentEnabledSetting(iconOrange, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Pink Application Icon//
            packageManager.setComponentEnabledSetting(iconPink, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Red Application Icon//
            packageManager.setComponentEnabledSetting(iconRed, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Violet Application Icon//
            packageManager.setComponentEnabledSetting(iconViolet, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Yellow Application Icon//
            packageManager.setComponentEnabledSetting(iconYellow, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }

        // What Happens When User Wants Theme Violet//
        if (themePicker.getInt(THEME_PICKER, -1) == 6) {

            // Hide Blue Application Icon//
            packageManager.setComponentEnabledSetting(iconBlue, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Green Application Icon//
            packageManager.setComponentEnabledSetting(iconGreen, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Indigo Application Icon//
            packageManager.setComponentEnabledSetting(iconIndigo, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Orange Application Icon//
            packageManager.setComponentEnabledSetting(iconOrange, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Pink Application Icon//
            packageManager.setComponentEnabledSetting(iconPink, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Red Application Icon//
            packageManager.setComponentEnabledSetting(iconRed, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Un-Hide Violet Application Icon//
            packageManager.setComponentEnabledSetting(iconViolet, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

            // Hide Yellow Application Icon//
            packageManager.setComponentEnabledSetting(iconYellow, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }

        // What Happens When User Wants Theme Pink//
        if (themePicker.getInt(THEME_PICKER, -1) == 7) {

            // Hide Blue Application Icon//
            packageManager.setComponentEnabledSetting(iconBlue, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Green Application Icon//
            packageManager.setComponentEnabledSetting(iconGreen, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Indigo Application Icon//
            packageManager.setComponentEnabledSetting(iconIndigo, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Orange Application Icon//
            packageManager.setComponentEnabledSetting(iconOrange, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Pink Application Icon//
            packageManager.setComponentEnabledSetting(iconPink, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

            // Hide Red Application Icon//
            packageManager.setComponentEnabledSetting(iconRed, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Violet Application Icon//
            packageManager.setComponentEnabledSetting(iconViolet, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            // Hide Yellow Application Icon//
            packageManager.setComponentEnabledSetting(iconYellow, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }
    }

    // Method That Instantiates All Activities For Icon Color//
    private void instantiateIconColorActivities(Context context) {

        // And Instantiate Variable ComponentName iconBlue//
        iconBlue = new ComponentName(context, "jordanzimmittidevelopers.com.communityservicelogger.DefaultActivityBlue");

        // Instantiate Variable ComponentName iconGreen//
        iconGreen = new ComponentName(context, "jordanzimmittidevelopers.com.communityservicelogger.DefaultActivityGreen");

        // Instantiate Variable ComponentName iconIndigo//
        iconIndigo = new ComponentName(context, "jordanzimmittidevelopers.com.communityservicelogger.DefaultActivityIndigo");

        // Instantiate Variable ComponentName iconOrange//
        iconOrange = new ComponentName(context, "jordanzimmittidevelopers.com.communityservicelogger.DefaultActivityOrange");

        // Instantiate Variable ComponentName iconBlue//
        iconPink = new ComponentName(context, "jordanzimmittidevelopers.com.communityservicelogger.DefaultActivityPink");

        // Instantiate Variable ComponentName iconRed//
        iconRed = new ComponentName(context, "jordanzimmittidevelopers.com.communityservicelogger.DefaultActivity");

        // Instantiate Variable ComponentName iconViolet//
        iconViolet = new ComponentName(context, "jordanzimmittidevelopers.com.communityservicelogger.DefaultActivityViolet");

        // Instantiate Variable ComponentName iconYellow//
        iconYellow = new ComponentName(context, "jordanzimmittidevelopers.com.communityservicelogger.DefaultActivityYellow");
    }

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable SwitchCompat nightModeSwitch//
        nightModeSwitch = (SwitchCompat) findViewById(R.id.nightModeSwitch);

        // Initiate switchPreference Method/
        switchPreference();

        // Instantiate Variable SharedPreference nightModeSwitchState//
        nightModeSwitchState = getSharedPreferences(SWITCH_STATE, MODE_PRIVATE);

        // Instantiate Variable SharedPreferences themePicker//
        themePicker = getSharedPreferences(THEME_PICKER, MODE_PRIVATE);

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

                    // Clear Saved Value//
                    nightModeSwitchState.edit().clear().apply();

                    // Save New Value Into Shared Preference//
                    nightModeSwitchState.edit().putInt(SWITCH_STATE, CHECKED).apply();

                    // Initiate userTheme Method//
                    userTheme(SettingsThemePicker.this);

                    // Restart The Activity//
                    finish();
                    Intent intent = getIntent();
                    startActivity(intent);
                    overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
                }

                // What Happens When Switch Is Not Checked//
                else {

                    // Clear Saved Value//
                    nightModeSwitchState.edit().clear().apply();

                    // Save New Value Into Shared Preference//
                    nightModeSwitchState.edit().putInt(SWITCH_STATE, UNCHECKED).apply();

                    // Initiate userTheme Method//
                    userTheme(SettingsThemePicker.this);

                    // Restart The Activity//
                    finish();
                    Intent intent = getIntent();
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

        // Clear Saved Value//
        themePicker.edit().clear().apply();

        // Save New Value Into Shared Preference//
        themePicker.edit().putInt(THEME_PICKER, THEME_RED).apply();

        // Initiate iconColor Method//
        iconColor();

        // Restart The Activity//
        finish();
        Intent intent = getIntent();
        startActivity(intent);
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }

    // What Happens When fabOrange Is Clicked//
    public void onClickFabOrange(View view) {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Clear Saved Value//
        themePicker.edit().clear().apply();

        // Save New Value Into Shared Preference//
        themePicker.edit().putInt(THEME_PICKER, THEME_ORANGE).apply();

        // Initiate iconColor Method//
        iconColor();

        // Restart The Activity//
        finish();
        Intent intent = getIntent();
        startActivity(intent);
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }

    // What Happens When fabYellow Is Clicked//
    public void onClickFabYellow(View view) {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Clear Saved Value//
        themePicker.edit().clear().apply();

        // Save New Value Into Shared Preference//
        themePicker.edit().putInt(THEME_PICKER, THEME_YELLOW).apply();

        // Initiate iconColor Method//
        iconColor();

        // Restart The Activity//
        finish();
        Intent intent = getIntent();
        startActivity(intent);
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }

    // What Happens When fabGreen Is Clicked//
    public void onClickFabGreen(View view) {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Clear Saved Value//
        themePicker.edit().clear().apply();

        // Save New Value Into Shared Preference//
        themePicker.edit().putInt(THEME_PICKER, THEME_GREEN).apply();

        // Initiate iconColor Method//
        iconColor();

        // Restart The Activity//
        finish();
        Intent intent = getIntent();
        startActivity(intent);
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }

    // What Happens When fabBlue Is Clicked//
    public void onClickFabBlue(View view) {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Clear Saved Value//
        themePicker.edit().clear().apply();

        // Save New Value Into Shared Preference//
        themePicker.edit().putInt(THEME_PICKER, THEME_BLUE).apply();

        // Initiate iconColor Method//
        iconColor();

        // Restart The Activity//
        finish();
        Intent intent = getIntent();
        startActivity(intent);
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }

    // What Happens When fabIndigo Is Clicked//
    public void onClickFabIndigo(View view) {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Clear Saved Value//
        themePicker.edit().clear().apply();

        // Save New Value Into Shared Preference//
        themePicker.edit().putInt(THEME_PICKER, THEME_INDIGO).apply();

        // Initiate iconColor Method//
        iconColor();

        // Restart The Activity//
        finish();
        Intent intent = getIntent();
        startActivity(intent);
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }

    // What Happens When fabViolet Is Clicked//
    public void onClickFabViolet(View view) {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Clear Saved Value//
        themePicker.edit().clear().apply();

        // Save New Value Into Shared Preference//
        themePicker.edit().putInt(THEME_PICKER, THEME_VIOLET).apply();

        // Initiate iconColor Method//
        iconColor();

        // Restart The Activity//
        finish();
        Intent intent = getIntent();
        startActivity(intent);
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }

    // What Happens When fabPink Is Clicked//
    public void onClickFabPink(View view) {

        // Vibrates For 50 Mill//
        vibe.vibrate(50);

        // Clear Saved Value//
        themePicker.edit().clear().apply();

        // Save New Value Into Shared Preference//
        themePicker.edit().putInt(THEME_PICKER, THEME_PINK).apply();

        // Initiate iconColor Method//
        iconColor();

        // Restart The Activity//
        finish();
        Intent intent = getIntent();
        startActivity(intent);
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }

    // Method To Set Switch Preference//
    private void switchPreference() {

        // What Happens When Switch Is Un-Checked//
        if (nightModeSwitchState.getInt(SWITCH_STATE, -1) == 0) {

            // Un-Check Switch//
            nightModeSwitch.setChecked(false);
        }

        // What Happens When Switch Is Checked//
        if (nightModeSwitchState.getInt(SWITCH_STATE, -1) == 1) {

            // Check Switch//
            nightModeSwitch.setChecked(true);
        }
    }

    // Method That Sets Theme Based On User Preference//
    public void userTheme(Context context) {

        // Instantiate Variable SharedPreference nightModeSwitchState//
        nightModeSwitchState = context.getSharedPreferences(SWITCH_STATE, MODE_PRIVATE);

        // What Happens When Switch Is Un-Checked//
        if (nightModeSwitchState.getInt(SWITCH_STATE, -1) == 0) {

            // Initiate themeColors Method//
            themeColors(context);
        }

        // What Happens When Switch Is Checked//
        else if (nightModeSwitchState.getInt(SWITCH_STATE, -1) == 1) {

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

        // Instantiate Variable SharedPreferences themePicker//
        themePicker = context.getSharedPreferences(THEME_PICKER, MODE_PRIVATE);

        // What Happens When User Wants Theme Red//
        if (themePicker.getInt(THEME_PICKER, -1) == 0) {

            // Apply Theme Red//
            context.setTheme(R.style.RedTheme);
        }

        // What Happens When User Wants Theme Orange//
        if (themePicker.getInt(THEME_PICKER, -1) == 1) {

            // Apply Theme Orange//
            context.setTheme(R.style.OrangeTheme);
        }

        // What Happens When User Wants Theme Yellow//
        if (themePicker.getInt(THEME_PICKER, -1) == 2) {

            // Apply Theme Yellow//
            context.setTheme(R.style.YellowTheme);
        }

        // What Happens When User Wants Theme Green//
        if (themePicker.getInt(THEME_PICKER, -1) == 3) {

            // Apply Theme Green//
            context.setTheme(R.style.GreenTheme);
        }

        // What Happens When User Wants Theme Blue//
        if (themePicker.getInt(THEME_PICKER, -1) == 4) {

            // Apply Theme Blue//
            context.setTheme(R.style.BlueTheme);
        }

        // What Happens When User Wants Theme Indigo//
        if (themePicker.getInt(THEME_PICKER, -1) == 5) {

            // Apply Theme Indigo//
            context.setTheme(R.style.IndigoTheme);
        }

        // What Happens When User Wants Theme Violet//
        if (themePicker.getInt(THEME_PICKER, -1) == 6) {

            // Apply Theme Violet//
            context.setTheme(R.style.VioletTheme);
        }

        // What Happens When User Wants Theme Pink//
        if (themePicker.getInt(THEME_PICKER, -1) == 7) {

            // Apply Theme Pink//
            context.setTheme(R.style.PinkTheme);
        }
    }
}
