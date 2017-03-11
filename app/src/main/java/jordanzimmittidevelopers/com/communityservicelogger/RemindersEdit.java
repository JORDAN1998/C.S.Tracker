package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

// RemindersAdd Class Created By Jordan Zimmitti 3-01-17//
public class RemindersEdit extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Define Variable Cursor cursor//
    private Cursor cursor;

    // Define Variable RemindersDatabase remindersDatabase//
    private RemindersDatabase remindersDatabase;

    // Define Variable Vibrator vibe//
    private Vibrator vibe;

    //</editor-fold>

    //<editor-fold desc="Strings">

    // Define Variable String itemId / String Of Id Value//
    private String itemId = null;

    // Define Variable String remindersEditNameString//
    private String remindersEditNameString;

    // Define Variable String remindersEditLocationString//
    private String remindersEditLocationString;

    // Define Variable String remindersEditDateString//
    private String remindersEditDateString;

    // Define Variable String reverseDateString//
    private String reverseDateString;

    //</editor-fold>

    //<editor-fold desc="Widgets">

    //<editor-fold desc="MaterialEditText">

    // Define Variable MaterialEditText remindersEditLocation//
    private MaterialEditText remindersEditLocation;

    // Define Variable MaterialEditText remindersEditName//
    private MaterialEditText remindersEditName;

    //</editor-fold>

    //<editor-fold desc="TextViews">

    // Define Variable TextView remindersEditDate//
    private TextView remindersEditDate;

    //</editor-fold>

    //</editor-fold>

    //</editor-fold>
    
    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initiate applyTheme Method//
        applyTheme();

        // Initiate remindersDatabaseOpen Method//
        remindersDatabaseOpen();

        // initiate getDatabaseValues Method//
        getDatabaseValues();

        // Initiate InstantiateWidgets Method//
        instantiateWidgets();
    }

    // Creates Menu And All Its Components//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflates The Menu / This Adds Items To The Action Bar If It Is Present//
        getMenuInflater().inflate(R.menu.reminders_edit_menu, menu);

        // Kill Code//
        return true;
    }

    // What Happens When User Picks Date//
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        // Define And Instantiate String dateString//
        String dateString = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;

        // What Happens If dayOfMonth Is Less Then Zero//
        if (dayOfMonth < 10) {

            String day = "0" + dayOfMonth;

            // Instantiate Variable String reverseDateString//
            reverseDateString = year + "/" + (monthOfYear + 1) + "/" + day;

        }

        // What Happens When dayOfMonth Is Bigger Then Zero//
        else {

            // Instantiate Variable String reverseDateString//
            reverseDateString = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
        }

        // Set eventsEditDate Text//
        remindersEditDate.setText(dateString);
    }

    //What Happens When Back Button Is Pressed//
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
        if (id == R.id.remindersEditSave) {

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
        setContentView(R.layout.reminders_edit);

        // Define And Instantiate RelativeLayout relativeLayout//
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.events_add_ui);

        // Night Mode Theme Extension Options//
        pickTheme.activityNightModeExtension(this, relativeLayout);
    }

    // Method To Get Values Stored In Database//
    private void getDatabaseValues() {

        // Gets Item Id Of Last Clicked ListView Item
        itemId = getIntent().getStringExtra(RemindersView.LIST_VIEW_ITEM_ID);

        // Instantiate Variable Cursor cursor / Get Database Row//
        cursor = remindersDatabase.getRow(itemId);

        // Instantiate Variable String remindersEditDateString//
        remindersEditDateString = cursor.getString(RemindersDatabase.COL_DATE);

        // Instantiate Variable String remindersEditLocationString//
        remindersEditLocationString = cursor.getString(RemindersDatabase.COL_LOCATION);

        // Instantiate Variable String remindersEditNameString//
        remindersEditNameString = cursor.getString(RemindersDatabase.COL_NAME_REMINDER);
    }

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable TextView remindersEditDate//
        remindersEditDate = (TextView) findViewById(R.id.remindersEditDate);

        //<editor-fold desc="Normal Date">

        // Set reverseDateString Equal To remindersEditDateString//
        reverseDateString = remindersEditDateString;

        // Define And Instantiate Variable String[] splitReverseDate//
        String[] splitReverseDate = reverseDateString.split("/");

        // Define And Instantiate Variable String year//
        String year = splitReverseDate[0];

        // Define And Instantiate Variable String mon//
        String month = splitReverseDate[1];

        // Define And Instantiate Variable String day//
        String day = splitReverseDate[2];

        // Define And Instantiate Variable String date//
        String date = month + "/" + day + "/" + year;

        //</editor-fold>

        // Set Text Equal To Value Stored In Database//
        remindersEditDate.setText(date);


        // Instantiate Variable MaterialEditText remindersEditLocation//
        remindersEditLocation = (MaterialEditText) findViewById(R.id.remindersEditLocation);

        // Set Text Equal To Value Stored In Database//
        remindersEditLocation.setText(remindersEditLocationString);


        // Instantiate Variable MaterialEditText remindersEditName//
        remindersEditName = (MaterialEditText) findViewById(R.id.remindersEditName);

        // Set Text Equal To Value Stored In Database//
        remindersEditName.setText(remindersEditNameString);


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
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(RemindersEdit.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

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
        if (TextUtils.isEmpty(remindersEditName.getText().toString()) || (remindersEditDate.getText().toString().equals("mm/dd/yyyy")) || (TextUtils.isEmpty(remindersEditLocation.getText().toString()))) {

            // Create Dialog//
            new MaterialDialog.Builder(RemindersEdit.this)

                    // Title Of Dialog//
                    .title("Warning")

                    // Content Of Dialog//
                    .content("You can't save the event without filling out all required fields: Name, Date, & Location")

                    // Negative Text Name For Button//
                    .negativeText("Ok")

                    .show();
        } else {

            // Inserts Values Into Database//
            remindersDatabase.updateRow(itemId, remindersEditName.getText().toString(), reverseDateString, remindersEditLocation.getText().toString());

            // Define and Instantiate Variable Intent RemindersView//
            Intent remindersView = new Intent(this, RemindersView.class);

            // Start Activity RemindersView//
            startActivity(remindersView);

            // Custom Transition//
            overridePendingTransition(R.anim.slid_in, R.anim.slid_out);

            // Close Cursor//
            cursor.close();
        }
    }
}
