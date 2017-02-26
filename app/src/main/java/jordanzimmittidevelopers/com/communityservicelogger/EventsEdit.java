package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// EventsView Class Created By Jordan Zimmitti 2-12-17//
public class EventsEdit extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Define Variable EventsDatabase eventsDatabase//
    private EventsDatabase eventsDatabase;

    // Define Variable int timeTotalAdded//
    private int timeTotalAdded = 0;

    // Define Variable Vibrator Vibe//
    private Vibrator vibe;

    //</editor-fold>

    //<editor-fold desc="Strings">

    // Puts Name Of Last Clicked ListView Item Into String//
    public static final String EVENTS_ADD_NAME_USER = null;

    // Define Variable String eventAddNameUser / String Of Name Value//
    private String eventsViewNameUser = null;

    // Define Variable String eventsEditNameString//
    private String eventsEditNameString;

    // Define Variable String eventsEditLocationString//
    private String eventsEditLocationString;

    // Define Variable String eventsEditPeopleInChargeString//
    private String eventsEditPeopleInChargeString;

    // Define Variable String eventsEditPhoneNumberString//
    private String eventsEditPhoneNumberString;

    // Define Variable String eventsEditNotesString//
    private String eventsEditNotesString;

    // Define Variable String eventsEditDateString//
    private String eventsEditDateString;

    // Define Variable String eventsEditTimeStartString//
    private String eventsEditTimeStartString;

    // Define Variable String eventsEditTimeEndString//
    private String eventsEditTimeEndString;

    // Define Variable String eventsEditTimeTotalString//
    private String eventsEditTimeTotalString;

    // Define Variable String itemId / String Of Id Value//
    private String itemId = null;

    // Define Variable String Military Time Start //
    private String militaryTimeStart;

    // Define Variable String Military Time Start//
    private String militaryTimeEnd;

    // Define Variable String usersViewNameUser / String Of Name Value//
    private String usersViewNameUser = null;

    //</editor-fold>

    //<editor-fold desc="Widgets">

    //<editor-fold desc="MaterialEditText">

    // Define Variable MaterialEditText eventsEditName//
    private MaterialEditText eventsEditName;

    // Define Variable MaterialEditText eventsEditLocation//
    private MaterialEditText eventsEditLocation;

    // Define Variable MaterialEditText eventsEditPeopleInCharge//
    private MaterialEditText eventsEditPeopleInCharge;

    // Define Variable MaterialEditText eventsEditPhoneNumber//
    private MaterialEditText eventsEditPhoneNumber;

    // Define Variable MaterialEditText eventsEditNotes//
    private MaterialEditText eventsEditNotes;

    //</editor-fold>

    //<editor-fold desc="TextViews">

    // Define Variable TextView eventsEditDate//
    private TextView eventsEditDate;

    // Define Variable TextView eventsEditTimeStart//
    private TextView eventsEditTimeStart;

    // Define Variable TextView eventsEditTimeEnd//
    private TextView eventsEditTimeEnd;

    // Define Variable TextView eventsEditTimeTotal//
    private TextView eventsEditTimeTotal;

    //</editor-fold>

    //</editor-fold>

    //</editor-fold>

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initiate applyTheme Method//
        applyTheme();

        // Initiate eventsDatabase Open Method//
        eventsDatabaseOpen();

        // initiate getDatabaseValues Method//
        getDatabaseValues();

        // Initiate InstantiateWidgets Method//
        instantiateWidgets();
    }

    // Creates Menu And All Its Components//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflates The Menu / This Adds Items To The Action Bar If It Is Present//
        getMenuInflater().inflate(R.menu.events_edit_menu, menu);

        // Kill Code//
        return true;
    }

    // What Happens When User Picks Date//
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        // Define And Instantiate String dateString//
        String dateString = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;

        // Set eventsEditDate Text//
        eventsEditDate.setText(dateString);
    }

    // What Happens When User Picks Time (Not Used Due To Multiple Time Pickers)//
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

    }

    // What Happens When Menu Buttons Are Clicked//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Figures Out What Menu Button Was Pressed//
        int id = item.getItemId();

        // What Happens When eventsEditSave Is Pressed//
        if (id == R.id.eventsEditSave) {

            // Initiate eventSave Method//
            eventUpdate();

            // Kill Code//
            return true;
        }

        // What Happens When eventsEditCopy Is Pressed//
        if (id == R.id.eventsEditCopy) {

            // Initiate eventCopy Method//
            eventCopy();

            // Kill Code//
            return true;
        }

        // Kill Code//
        return super.onOptionsItemSelected(item);
    }

    // Method That Applies Theme By User Preference//
    private void applyTheme() {

        // Define And Instantiate Variable ThemePicker pickTheme//
        ThemePicker pickTheme = new ThemePicker();

        // Set Theme Based On User Preference//
        pickTheme.userTheme(this);

        // Starts UI For Activity//
        setContentView(R.layout.events_edit_ui);

        // Define And Instantiate Variable RelativeLayout relativeLayout//
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.events_edit_ui);

        // Night Mode Theme Extension Options//
        pickTheme.activityNightModeExtension(this, relativeLayout);
    }

    // Method That Copies Event//
    private void eventCopy() {

        // Vibrates For 50m//
        vibe.vibrate(50);

        // What Happens When Required Fields Are Left Blank//
        if (TextUtils.isEmpty(eventsEditName.getText().toString()) || (eventsEditDate.getText().toString().equals("mm/dd/yyyy")) || (TextUtils.isEmpty(eventsEditLocation.getText().toString())) || (eventsEditTimeStart.getText().toString().equals("0:00")) || (eventsEditTimeEnd.getText().toString().equals("0:00")) || (eventsEditTimeTotal.getText().toString().equals("0:00"))) {

            // Create Dialog//
            new MaterialDialog.Builder(EventsEdit.this)

                    // Title Of Dialog//
                    .title("Warning")

                    // Content Of Dialog//
                    .content("You can't save the event without filling out all required fields: Name, Date, Location, Start End & Total Time")

                    // Negative Text Name For Button//
                    .negativeText("Edit")

                    .show();
        } else {

            // Define Variable String workingNameUser//
            String workingNameUser;

            // What Happens When eventsViewNameUser Doesn't Equal Null//
            if (eventsViewNameUser != null) {

                // Set workingNameUser Equal To eventsAddNameUser//
                workingNameUser = eventsViewNameUser;

            } else {

                // Set workingNameUser Equal To eventsAddNameUser//
                workingNameUser = usersViewNameUser;
            }

            try {

                // Initiate calculateTimeTotal Method//
                calculateTimeTotal();

            } catch (ParseException e) { e.printStackTrace(); }

            // Inserts Values Into Database//
            eventsDatabase.insertRow(eventsEditName.getText().toString(), workingNameUser, eventsEditDate.getText().toString(), eventsEditLocation.getText().toString(), eventsEditTimeStart.getText().toString(), eventsEditTimeEnd.getText().toString(), eventsEditTimeTotal.getText().toString(), String.valueOf(timeTotalAdded), eventsEditPeopleInCharge.getText().toString(), eventsEditPhoneNumber.getText().toString(), eventsEditNotes.getText().toString(), "");

            // Define and Instantiate Variable Intent EventsView//
            Intent eventsView = new Intent(this, EventsView.class);

            // Get Id Of Item Clicked In userListView//
            eventsView.putExtra(EVENTS_ADD_NAME_USER, workingNameUser);

            // Start Activity EventsView//
            startActivity(eventsView);

            // Custom Transition//
            overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
        }
    }

    // Method That Calculates Total Time//
    private void calculateTimeTotal() throws ParseException {

            // Define Variable Date timeStartValue//
            Date timeStartValue;

            // Define Variable Date timeEndValue//
            Date timeEndValue;

            // Creates Simple Date Format For Military Time//
            SimpleDateFormat militaryTimeFormat = new SimpleDateFormat("HH:mm");

            // Creates Simple Date Format For Standard Time//
            SimpleDateFormat standardTimeFormat = new SimpleDateFormat("hh:mm a");

            // Gets Value From eventsEditTimeEnd And Parses It For Standard Time//
            Date standardTimeEnd = standardTimeFormat.parse(eventsEditTimeEnd.getText().toString());

            // Gets Value From =eventsEditTimeStart And Parses It For Standard Time//
            Date standardTimeStart = standardTimeFormat.parse(eventsEditTimeStart.getText().toString());

            // Convert Standard Time Into Military Time//
            militaryTimeEnd = militaryTimeFormat.format(standardTimeEnd);

            // Convert Standard Time Into Military Time//
            militaryTimeStart = militaryTimeFormat.format(standardTimeStart);

            // Puts Military Time Start Into Time Format//
            timeStartValue = militaryTimeFormat.parse(militaryTimeStart);

            // Puts Military Time Start Into Time Format//
            timeEndValue = militaryTimeFormat.parse(militaryTimeEnd);

            // Subtract End Time And Start Time In Military Time And Then Puts It Into Standard Time//
            long difference = timeEndValue.getTime() - timeStartValue.getTime();
            int days = (int) (difference / (1000 * 60 * 60 * 24));
            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            timeTotalAdded = hours * 60;
            timeTotalAdded = timeTotalAdded + min;
            String timeDifference = (hours < 0 ? "0" + -hours : hours) + ":" + (min < 10 ? "0" + min : min);

            // Set Time Difference//
            eventsEditTimeTotal.setText(timeDifference);
    }

    // Method To Open Events Database//
    private void eventsDatabaseOpen() {

        // Instantiate Variable EventsDatabase eventsDatabase//
        eventsDatabase = new EventsDatabase(this);

        // Open Database//
        eventsDatabase.open();
    }

    // Method To Save Event//
    private void eventUpdate() {

        // Vibrates For 50m//
        vibe.vibrate(50);

        // What Happens When Required Fields Are Left Blank//
        if (TextUtils.isEmpty(eventsEditName.getText().toString()) || (eventsEditDate.getText().toString().equals("mm/dd/yyyy")) || (TextUtils.isEmpty(eventsEditLocation.getText().toString())) || (eventsEditTimeStart.getText().toString().equals("0:00")) || (eventsEditTimeEnd.getText().toString().equals("0:00")) || (eventsEditTimeTotal.getText().toString().equals("0:00"))) {

            // Create Dialog//
            new MaterialDialog.Builder(EventsEdit.this)

                    // Title Of Dialog//
                    .title("Warning")

                    // Content Of Dialog//
                    .content("You can't save the event without filling out all required fields: Name, Date, Location, Start End & Total Time")

                    // Negative Text Name For Button//
                    .negativeText("Edit")

                    .show();
        } else {

            // Define Variable String workingNameUser//
            String workingNameUser;

            // What Happens When eventsViewNameUser Doesn't Equal Null//
            if (eventsViewNameUser != null) {

                // Set workingNameUser Equal To eventsAddNameUser//
                workingNameUser = eventsViewNameUser;

            } else {

                // Set workingNameUser Equal To eventsAddNameUser//
                workingNameUser = usersViewNameUser;
            }

            // Inserts Values Into Database//
            eventsDatabase.updateRow(itemId, eventsEditName.getText().toString(), workingNameUser, eventsEditDate.getText().toString(), eventsEditLocation.getText().toString(), eventsEditTimeStart.getText().toString(), eventsEditTimeEnd.getText().toString(), eventsEditTimeTotal.getText().toString(), String.valueOf(timeTotalAdded), eventsEditPeopleInCharge.getText().toString(), eventsEditPhoneNumber.getText().toString(), eventsEditNotes.getText().toString(), "");

            // Define and Instantiate Variable Intent EventsView//
            Intent eventsView = new Intent(this, EventsView.class);

            // Get Id Of Item Clicked In userListView//
            eventsView.putExtra(EVENTS_ADD_NAME_USER, workingNameUser);

            // Start Activity EventsView//
            startActivity(eventsView);

            // Custom Transition//
            overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
        }
    }

    // Method To Get Values Stored In Database//
    private void getDatabaseValues() {

        // Gets Item Id Of Last Clicked ListView Item
        itemId = getIntent().getStringExtra(EventsView.KEY_ROW_ID_NUMBER);

        // Define And Instantiate Variable Cursor cursor / Get Database Row//
        Cursor cursor = eventsDatabase.getRow(itemId);

        // Instantiate Variable String eventsEditDateString//
        eventsEditDateString = cursor.getString(EventsDatabase.COL_DATE);

        // Instantiate Variable String eventsEditLocationString//
        eventsEditLocationString = cursor.getString(EventsDatabase.COL_LOCATION);

        // Instantiate Variable String eventsEditNameString//
        eventsEditNameString = cursor.getString(EventsDatabase.COL_NAME_EVENT);

        // Instantiate Variable String eventsEditNotesString//
        eventsEditNotesString = cursor.getString(EventsDatabase.COL_NOTES);

        // Instantiate Variable String eventsEditPeopleInChargeString//
        eventsEditPeopleInChargeString = cursor.getString(EventsDatabase.COL_PEOPLE_IN_CHARGE);

        // Instantiate Variable String eventsEditPhoneNumberString//
        eventsEditPhoneNumberString = cursor.getString(EventsDatabase.COL_PHONE_NUMBER);

        // Instantiate Variable String eventsEditTimeEndString//
        eventsEditTimeEndString = cursor.getString(EventsDatabase.COL_TIME_END);

        // Instantiate Variable String eventsEditTimeStartString//
        eventsEditTimeStartString = cursor.getString(EventsDatabase.COL_TIME_START);

        // Instantiate Variable String eventsEditTimeTotalString//
        eventsEditTimeTotalString = cursor.getString(EventsDatabase.COL_TIME_TOTAL);
    }

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable TextView eventsEditDate//
        eventsEditDate = (TextView) findViewById(R.id.eventsEditDate);

        // Set Text Equal To Value Stored In Database//
        eventsEditDate.setText(eventsEditDateString);


        // Instantiate Variable MaterialEditText eventsEditLocation//
        eventsEditLocation = (MaterialEditText) findViewById(R.id.eventsEditLocation);

        // Set Text Equal To Value Stored In Database//
        eventsEditLocation.setText(eventsEditLocationString);


        // Instantiate Variable MaterialEditText eventsEditName//
        eventsEditName = (MaterialEditText) findViewById(R.id.eventsEditName);

        // Set Text Equal To Value Stored In Database//
        eventsEditName.setText(eventsEditNameString);


        // Instantiate Variable MaterialEditText eventsEditNotes//
        eventsEditNotes = (MaterialEditText) findViewById(R.id.eventsEditNotes);

        // Set Text Equal To Value Stored In Database//
        eventsEditNotes.setText(eventsEditNotesString);


        // Instantiate Variable MaterialEditText eventsEditNotes//
        eventsEditPeopleInCharge = (MaterialEditText) findViewById(R.id.eventsEditPeopleInCharge);

        // Set Text Equal To Value Stored In Database//
        eventsEditPeopleInCharge.setText(eventsEditPeopleInChargeString);


        // Instantiate Variable MaterialEditText eventsEditPhoneNumber//
        eventsEditPhoneNumber = (MaterialEditText) findViewById(R.id.eventsEditPhoneNumber);

        // Set Text Equal To Value Stored In Database//
        eventsEditPhoneNumber.setText(eventsEditPhoneNumberString);


        // Instantiate Variable TextView eventsEditTimeEnd//
        eventsEditTimeEnd = (TextView) findViewById(R.id.eventsEditTimeEnd);

        // Set Text Equal To Value Stored In Database//
        eventsEditTimeEnd.setText(eventsEditTimeEndString);


        // Instantiate Variable TextView eventsEditTimeStart//
        eventsEditTimeStart = (TextView) findViewById(R.id.eventsEditTimeStart);

        // Set Text Equal To Value Stored In Database//
        eventsEditTimeStart.setText(eventsEditTimeStartString);


        // Instantiate Variable TextView eventsEditTimeTotal//
        eventsEditTimeTotal = (TextView) findViewById(R.id.eventsEditTimeTotal);

        // Set Text Equal To Value Stored In Database//
        eventsEditTimeTotal.setText(eventsEditTimeTotalString);


        // Gets Name Of Last Clicked ListView Item//
        eventsViewNameUser = getIntent().getStringExtra(EventsView.EVENTS_ADD_NAME_USER);

        // Gets Name Of Last Clicked ListView Item//
        usersViewNameUser = getIntent().getStringExtra(EventsView.USERS_VIEW_NAME_USER);

        // Instantiate Variable Vibrator vibe//
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    // What Happens When eventsAddDate Is Clicked//
    public void onClickDate(View view) {

        // Vibrate For 50m//
        vibe.vibrate(50);

        // Define And Instantiate Variable Calendar calendar//
        java.util.Calendar calendar = java.util.Calendar.getInstance();

        // Define And Instantiate Variable DatePickerDialog datePickerDialog//
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(EventsEdit.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        // Show datePickerDialog//
        datePickerDialog.show(getFragmentManager(), "datePickerDialog");

        // Set Version Of datePickerDialog//
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
    }

    // What Happens When eventsAddTimeStart Is Clicked//
    public void onClickTimeStart(View view) {

        // Vibrate For 50m//
        vibe.vibrate(50);

        // Define And Instantiate Variable Calendar calendar//
        java.util.Calendar calendar = java.util.Calendar.getInstance();

        // Creates New Time Picker Dialog//
        TimePickerDialog timePickerDialogTimeStart = TimePickerDialog.newInstance(EventsEdit.this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

        // What Happens When eventsEditTimeStart Doesn't Equal 0:00 //
        if (!eventsEditTimeStart.getText().toString().equals("0:00")) {

            // Define And Instantiate Variable String timeStart//
            String timeStart = eventsEditTimeStart.getText().toString();

            // Define And Instantiate Variable String[] splitTimeStart//
            String[] splitTimeStart = timeStart.split(":");

            // Define And Instantiate Variable String hour / Get Hour //
            String hour = splitTimeStart[0];

            // Define And Instantiate Variable String minute / Get minute //
            String minute = splitTimeStart[1];

            // Get Rid Of All Other Text//
            minute = minute.replace("AM","");
            minute = minute.replace("PM","");
            minute = minute.replace(" ", "");

            // Set Hour As Int//
            int hourInt = Integer.parseInt(hour);

            // Set Minute As Int//
            int minuteInt = Integer.parseInt(minute);

            // Get Current Time Set By User//
            timePickerDialogTimeStart.setStartTime(hourInt, minuteInt);
        }

        // Show datePickerDialog//
        timePickerDialogTimeStart.show(getFragmentManager(), "timePickerDialogTimeStart");

        // Set Version Of datePickerDialog//
        timePickerDialogTimeStart.setVersion(TimePickerDialog.Version.VERSION_2);

        //<editor-fold desc="OnTimeSetListener">

        // What Happens When user Picks Time//
        timePickerDialogTimeStart.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener()  {

            // What Happens When Time Is Set//
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second)  {

                // Define And Instantiate Variable int militaryHour//
                int militaryHour = hourOfDay;

                // Define Variable String amOrPm//
                String amOrPm;

                // Define Variable String min//
                String min;

                // Define Variable String timeStartString//
                String timeStartString;

                // What Happens If militaryHour Equals Zero//
                if (militaryHour == 0) {

                    // Sets hour to 24//
                    militaryHour = 24;
                }

                // What Happens When hourOfDay Is Bigger Than Twelve//
                if (hourOfDay > 12) {

                    // Subtract Twelve//
                    hourOfDay -= 12;

                    // Set Equal To PM//
                    amOrPm = "PM";

                }

                // What Happens When hourOfDay Equals Zero//
                else if (hourOfDay == 0) {

                    // Add Twelve//
                    hourOfDay += 12;

                    // Set Equal To AM//
                    amOrPm = "AM";
                }

                // What Happens When hourOfDay Equals Twelve//
                else if (hourOfDay == 12) {

                    // Set Equal To PM//
                    amOrPm = "PM";
                }

                // What Happens For Everything Else//
                else {

                    // Set Equal To AM//
                    amOrPm = "AM";
                }

                // What Happens When minute Is Less Than Ten//
                if (minute < 10) {

                    // Add A zero To The Minute//
                    min = "0" + minute;

                } else {

                    // Set min Equal To minute//
                    min = String.valueOf(minute);
                }

                // Set Military Time Hour And Minute//
                militaryTimeStart = String.valueOf(militaryHour) + ":" + minute;

                // Set Standard Time Hour And Minute//
                timeStartString = String.valueOf(hourOfDay) + ':' + min + " " + amOrPm;

                // Set eventsAddTimeStart Text//
                eventsEditTimeStart.setText(timeStartString);

                try {

                    // Initiate calculateTimeTotal Method//
                    calculateTimeTotal();

                } catch (ParseException e) { e.printStackTrace(); }
            }
        });

        //</editor-fold>
    }

    // What Happens When eventsAddTimeEnd Is Clicked//
    public void onClickTimeEnd(View view) {

        // Vibrates For 50m//
        vibe.vibrate(50);

        // Define And Instantiate Variable Calendar calendar//
        java.util.Calendar calendar = java.util.Calendar.getInstance();

        // Creates New Time Picker Dialog//
        TimePickerDialog timePickerDialogTimeEnd = TimePickerDialog.newInstance(EventsEdit.this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

        // What Happens When eventsEditTimeEnd Doesn't Equal 0:00 //
        if (!eventsEditTimeEnd.getText().toString().equals("0:00")) {

            // Define And Instantiate Variable String timeEnd//
            String timeEnd = eventsEditTimeEnd.getText().toString();

            // Define And Instantiate Variable String[] splitTimeEnd//
            String[] splitTimeEnd = timeEnd.split(":");

            // Define And Instantiate Variable String hour / Get Hour //
            String hour = splitTimeEnd[0];

            // Define And Instantiate Variable String minute / Get minute //
            String minute = splitTimeEnd[1];

            // Get Rid Of All Other Text//
            minute = minute.replace("AM","");
            minute = minute.replace("PM","");
            minute = minute.replace(" ", "");

            // Set Hour As Int//
            int hourInt = Integer.parseInt(hour);

            // Set Minute As Int//
            int minuteInt = Integer.parseInt(minute);

            // Get Current Time Set By User//
            timePickerDialogTimeEnd.setStartTime(hourInt, minuteInt);
        }

        // Show datePickerDialog//
        timePickerDialogTimeEnd.show(getFragmentManager(), "timePickerDialogTimeStart");

        // Set Version Of datePickerDialog//
        timePickerDialogTimeEnd.setVersion(TimePickerDialog.Version.VERSION_2);

        //<editor-fold desc="OnTimeSetListener">

        // What Happens When user Picks Time//
        timePickerDialogTimeEnd.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {

            // What Happens When Time Is Set//
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

                // Define And Instantiate Variable int militaryHour//
                int militaryHour = hourOfDay;

                // Define Variable String amOrPm//
                String amOrPm;

                // Define Variable String min//
                String min;

                // Define Variable String timeStartString//
                String timeEndString;

                // What Happens If militaryHour Equals Zero//
                if (militaryHour == 0) {

                    // Sets hour to 24//
                    militaryHour = 24;
                }

                // What Happens When hourOfDay Is Bigger Than Twelve//
                if (hourOfDay > 12) {

                    // Subtract Twelve//
                    hourOfDay -= 12;

                    // Set Equal To PM//
                    amOrPm = "PM";

                }

                // What Happens When hourOfDay Equals Zero//
                else if (hourOfDay == 0) {

                    // Add Twelve//
                    hourOfDay += 12;

                    // Set Equal To AM//
                    amOrPm = "AM";
                }

                // What Happens When hourOfDay Equals Twelve//
                else if (hourOfDay == 12) {

                    // Set Equal To PM//
                    amOrPm = "PM";
                }

                // What Happens For Everything Else//
                else {

                    // Set Equal To AM//
                    amOrPm = "AM";
                }

                // What Happens When minute Is Less Than Ten//
                if (minute < 10) {

                    // Add A zero To The Minute//
                    min = "0" + minute;

                } else {

                    // Set min Equal To minute//
                    min = String.valueOf(minute);
                }

                // Set Military Time Hour And Minute//
                militaryTimeEnd = String.valueOf(militaryHour) + ":" + minute;

                // Set Standard Time Hour And Minute//
                timeEndString = String.valueOf(hourOfDay) + ':' + min + " " + amOrPm;

                // Set eventsAddTimeStart Text//
                eventsEditTimeEnd.setText(timeEndString);

                try {

                    // Initiate calculateTimeTotal Method//
                    calculateTimeTotal();

                } catch (ParseException e) { e.printStackTrace(); }
            }
        });

        //</editor-fold>
    }
}
