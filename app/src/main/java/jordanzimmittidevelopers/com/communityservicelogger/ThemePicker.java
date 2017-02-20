package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

// ThemePicker Class Created By Jordan Zimmitti 2-19-17//
public class ThemePicker extends AppCompatActivity {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

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

    //</editor-fold>

    //</editor-fold>

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initiate userTheme Method//
        userTheme(this);

        // Starts UI For Activity//
        setContentView(R.layout.themes_ui);

        // Initiate instantiateWidgets Method//
        instantiateWidgets();
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

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable Vibrator vibe//
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
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

    // Method That Sets Theme Based On User Preference//
    public void userTheme(Context context) {

        // Define And Instantiate Variable SharedPreferences theme//
        SharedPreferences theme = context.getSharedPreferences(THEME_PICKER, MODE_PRIVATE);

        // What Happens When User Wants Theme Red//
        if (theme.getInt("theme_picker", 0) == 1) {

            // Apply Theme Red//
            context.setTheme(R.style.RedTheme);
        }

        // What Happens When User Wants Theme Orange//
        if (theme.getInt("theme_picker", 0) == 2) {

            // Apply Theme Orange//
            context.setTheme(R.style.OrangeTheme);
        }

        // What Happens When User Wants Theme Yellow//
        if (theme.getInt("theme_picker", 0) == 3) {

            // Apply Theme Yellow//
            context.setTheme(R.style.YellowTheme);
        }

        // What Happens When User Wants Theme Green//
        if (theme.getInt("theme_picker", 0) == 4) {

            // Apply Theme Green//
            context.setTheme(R.style.GreenTheme);
        }

        // What Happens When User Wants Theme Blue//
        if (theme.getInt("theme_picker", 0) == 5) {

            // Apply Theme Blue//
            context.setTheme(R.style.BlueTheme);
        }

        // What Happens When User Wants Theme Indigo//
        if (theme.getInt("theme_picker", 0) == 6) {

            // Apply Theme Indigo//
            context.setTheme(R.style.IndigoTheme);
        }

        // What Happens When User Wants Theme Violet//
        if (theme.getInt("theme_picker", 0) == 7) {

            // Apply Theme Violet//
            context.setTheme(R.style.VioletTheme);
        }

        // What Happens When User Wants Theme Pink//
        if (theme.getInt("theme_picker", 0) == 8) {

            // Apply Theme Pink//
            context.setTheme(R.style.PinkTheme);
        }
    }
}
