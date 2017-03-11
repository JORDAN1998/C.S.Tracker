package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

// RemindersAdd Class Created By Jordan Zimmitti 2-28-17//
public class RemindersAdd extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Define Variable RemindersDatabase remindersDatabase//
    private RemindersDatabase remindersDatabase;

    // Define Variable Vibrator vibe//
    private Vibrator vibe;

    //</editor-fold>

    //<editor-fold desc="Strings">

    // Define Variable String reverseDateString//
    private String reverseDateString;

    //</editor-fold>

    //<editor-fold desc="Widgets">

    //<editor-fold desc="MaterialEditText">

    // Define Variable MaterialEditText remindersAddName//
    private MaterialEditText remindersAddName;

    // Define Variable MaterialEditText remindersAddLocation//
    private MaterialEditText remindersAddLocation;

    //</editor-fold>

    //<editor-fold desc="TextViews">

    // Define Variable TextView remindersAddDate//
    private TextView remindersAddDate;

    //</editor-fold>

    //</editor-fold>

    //</editor-fold>

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initiate applyTheme Method//
        applyTheme();

        // Initiate InstantiateWidgets Method//
        instantiateWidgets();

        // Initiate remindersDatabaseOpen Method//
        remindersDatabaseOpen();
    }

    // Creates Menu And All Its Components//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflates The Menu / This Adds Items To The Action Bar If It Is Present//
        getMenuInflater().inflate(R.menu.reminders_add_menu, menu);

        // Kill Code//
        return true;
    }

    // What Happens When User Picks Date//
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        // Define And Instantiate Variable String dateString//
        String dateString = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;

        // What Happens If dayOfMonth Is Less Then Zero//
        if (dayOfMonth < 10) {

            // Define And Instantiate Variable String day//
            String day = "0" + dayOfMonth;

            // Instantiate Variable String reverseDateString//
            reverseDateString = year + "/" + (monthOfYear + 1) + "/" + day;

        }

        // What Happens When dayOfMonth Is Bigger Then Zero//
        else {

            // Instantiate Variable String reverseDateString//
            reverseDateString = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
        }

        // Set eventsAddDate Text//
        remindersAddDate.setText(dateString);
    }

    //Controls Back Button Functions//
    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        switch (keyCode) {

            // What Happens When Back Button Is Pressed//
            case KeyEvent.KEYCODE_BACK:

                // Define and Instantiate Variable Intent RemindersView//
                Intent remindersView = new Intent(this, RemindersView.class);

                // Start Activity RemindersView//
                startActivity(remindersView);

                // Custom Transition//
                overridePendingTransition(R.anim.slid_in, R.anim.slid_out);

                // Kill Code//
                return false;
            default:
                return false;
        }
    }

    // What Happens When Menu Buttons Are Clicked//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Figures Out What Menu Button Was Pressed//
        int id = item.getItemId();

        // What Happens When remindersAddSave Is Pressed//
        if (id == R.id.remindersAddSave) {

            // Initiate reminderSave Method//
            reminderSave();

            // Kill Code//
            return true;
        }

        // Kill Code//
        return super.onOptionsItemSelected(item);
    }

    // Method That Applies Theme By User Preference//
    private void applyTheme() {

        // Define And Instantiate Variable SettingsThemePicker pickTheme//
        SettingsThemePicker pickTheme = new SettingsThemePicker();

        // Set Theme Based On User Preference//
        pickTheme.userTheme(this);

        // Starts UI For Activity//
        setContentView(R.layout.reminders_add_ui);

        // Define And Instantiate RelativeLayout relativeLayout//
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.events_add_ui);

        // Night Mode Theme Extension Options//
        pickTheme.activityNightModeExtension(this, relativeLayout);
    }

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable TextView eventsAddDate//
        remindersAddDate = (TextView) findViewById(R.id.remindersAddDate);

        // Instantiate Variable MaterialEditText eventsAddName//
        remindersAddName = (MaterialEditText) findViewById(R.id.remindersAddName);

        // Instantiate Variable MaterialEditText eventsAddLocation//
        remindersAddLocation = (MaterialEditText) findViewById(R.id.remindersAddLocation);

        // Instantiate Variable Vibrator vibe//
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    // What Happens When remindersAddDate Is Clicked//
    public void onClickDate(View view) {

        // Vibrate For 50m//
        vibe.vibrate(50);

        // Define And Instantiate Variable Calendar calendar//
        java.util.Calendar calendar = java.util.Calendar.getInstance();

        // Define And Instantiate Variable DatePickerDialog datePickerDialog//
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(RemindersAdd.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        // Show datePickerDialog//
        datePickerDialog.show(getFragmentManager(), "datePickerDialog");

        // Set Version Of datePickerDialog//
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
    }

    // Method To Open Reminders Database//
    private void remindersDatabaseOpen() {

        // Instantiate Variable RemindersDatabase remindersDatabase//
        remindersDatabase = new RemindersDatabase(this);

        // Open Database//
        remindersDatabase.open();
    }

    // Method To Save Reminder//
    private void reminderSave() {

        // Vibrates For 50m//
        vibe.vibrate(50);

        // What Happens When Required Fields Are Left Blank//
        if (TextUtils.isEmpty(remindersAddName.getText().toString()) || (remindersAddDate.getText().toString().equals("mm/dd/yyyy")) || (TextUtils.isEmpty(remindersAddLocation.getText().toString()))) {

            // Create Dialog//
            new MaterialDialog.Builder(RemindersAdd.this)

                    // Title Of Dialog//
                    .title("Warning")

                    // Content Of Dialog//
                    .content("You can't save the event without filling out all required fields: Name, Date, & Location")

                    // Negative Text Name For Button//
                    .negativeText("Ok")

                    .show();
        } else {

            // Inserts Values Into Database//
            remindersDatabase.insertRow(remindersAddName.getText().toString(), reverseDateString, remindersAddLocation.getText().toString());

            // Define and Instantiate Variable Intent RemindersView//
            Intent remindersView = new Intent(this, RemindersView.class);

            // Start Activity RemindersView//
            startActivity(remindersView);

            // Custom Transition//
            overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
        }
    }
}
