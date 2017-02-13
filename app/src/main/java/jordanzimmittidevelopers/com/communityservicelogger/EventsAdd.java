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
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// EventsAdd Class Created By Jordan Zimmitti 2-09-17//
public class EventsAdd extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

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

    // Define Variable String Military Time Start //
    private String militaryTimeStart;

    // Define Variable String Military Time Start//
    private String militaryTimeEnd;

    // Define Variable String usersViewNameUser / String Of Name Value//
    private String usersViewNameUser = null;

    //</editor-fold>

    //<editor-fold desc="Widgets">

    //<editor-fold desc="MaterialEditText">

    // Define Variable MaterialEditText eventsAddName//
    private MaterialEditText eventsAddName;

    // Define Variable MaterialEditText eventsAddLocation//
    private MaterialEditText eventsAddLocation;

    // Define Variable MaterialEditText eventsAddPeopleInCharge//
    private MaterialEditText eventsAddPeopleInCharge;

    // Define Variable MaterialEditText eventsAddPhoneNumber//
    private MaterialEditText eventsAddPhoneNumber;

    // Define Variable MaterialEditText eventsAddNotes//
    private MaterialEditText eventsAddNotes;

    //</editor-fold>

    //<editor-fold desc="TextViews">

    // Define Variable TextView eventsAddDate//
    private TextView eventsAddDate;

    // Define Variable TextView eventsAddTimeStart//
    private TextView eventsAddTimeStart;

    // Define Variable TextView eventsAddTimeEnd//
    private TextView eventsAddTimeEnd;

    // Define Variable TextView eventsAddTimeTotal//
    private TextView eventsAddTimeTotal;

    //</editor-fold>

    //</editor-fold>

    //</editor-fold>

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Starts UI For Activity//
        setContentView(R.layout.events_add_ui);

        // Initiate eventsDatabase Open Method//
        eventsDatabaseOpen();

        // Initiate InstantiateWidgets Method//
        instantiateWidgets();
    }

    // Creates Menu And All Its Components//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflates The Menu / This Adds Items To The Action Bar If It Is Present//
        getMenuInflater().inflate(R.menu.events_add_menu, menu);

        // Kill Code//
        return true;
    }

    // What Happens When User Picks Date//
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        // Define And Instantiate String dateString//
        String dateString = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;

        // Set eventsAddDate Text//
        eventsAddDate.setText(dateString);
    }

    //Controls Back Button Functions//
    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        switch (keyCode) {

            // What Happens When Back Button Is Pressed//
            case KeyEvent.KEYCODE_BACK:

                // Define Variable String workingNameUser//
                String workingNameUser;

                // Set workingNameUser Equal To eventsViewNameUser//
                if (eventsViewNameUser != null) {

                    // Set workingNameUser Equal To eventsViewNameUser//
                    workingNameUser = eventsViewNameUser;

                } else {

                    // Set workingNameUser Equal To usersViewNameUser//
                    workingNameUser = usersViewNameUser;
                }

                // Define and Instantiate Variable Intent EventsView//
                Intent eventsView = new Intent(this, EventsView.class);

                // Get Id Of Item Clicked In userListView//
                eventsView.putExtra(EVENTS_ADD_NAME_USER, workingNameUser);

                // Start Activity EventsView//
                startActivity(eventsView);

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

        // What Happens When eventsAddSave Is Pressed//
        if (id == R.id.eventsAddSave) {

            // Initiate eventSave Method//
            eventSave();

            // Kill Code//
            return true;
        }

        // Kill Code//
        return super.onOptionsItemSelected(item);
    }

    // What Happens When User Picks Time (Not Used Due To Multiple Time Pickers)//
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

    }

    // Method That Calculates Total Time//
    private void calculateTimeTotal() throws ParseException {

        // What Happens When militaryTimeStart And militaryTimeEnd Have Values//
        if (militaryTimeStart != null & militaryTimeEnd != null) {

            // Define Variable Date timeStartValue//
            Date timeStartValue;

            // Define Variable Date timeEndValue//
            Date timeEndValue;

            // Creates Simple Date Format For Military Time//
            SimpleDateFormat militaryTimeFormat = new SimpleDateFormat("HH:mm");

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
            eventsAddTimeTotal.setText(timeDifference);
        }
    }

    // Method To Open Events Database//
    private void eventsDatabaseOpen() {

        // Instantiate Variable EventsDatabase eventsDatabase//
        eventsDatabase = new EventsDatabase(this);

        // Open Database//
        eventsDatabase.open();
    }

    // Method To Save Event//
    private void eventSave() {

        // Vibrates For 50m//
        vibe.vibrate(50);

        // What Happens When Required Fields Are Left Blank//
        if (TextUtils.isEmpty(eventsAddName.getText().toString()) || (eventsAddDate.getText().toString().equals("mm/dd/yyyy")) || (TextUtils.isEmpty(eventsAddLocation.getText().toString())) || (eventsAddTimeStart.getText().toString().equals("0:00")) || (eventsAddTimeEnd.getText().toString().equals("0:00")) || (eventsAddTimeTotal.getText().toString().equals("0:00"))) {

            // Create Dialog//
            new MaterialDialog.Builder(EventsAdd.this)

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
            eventsDatabase.insertRow(eventsAddName.getText().toString(), workingNameUser, eventsAddDate.getText().toString(), eventsAddLocation.getText().toString(), eventsAddTimeStart.getText().toString(), eventsAddTimeEnd.getText().toString(), eventsAddTimeTotal.getText().toString(), String.valueOf(timeTotalAdded), eventsAddPeopleInCharge.getText().toString(), eventsAddPhoneNumber.getText().toString(), eventsAddNotes.getText().toString(), "");

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

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable MaterialEditText eventsAddLocation//
        eventsAddLocation = (MaterialEditText) findViewById(R.id.eventsAddLocation);

        // Instantiate Variable MaterialEditText eventsAddName//
        eventsAddName = (MaterialEditText) findViewById(R.id.eventsAddName);

        // Instantiate Variable MaterialEditText eventsAddNotes//
        eventsAddNotes = (MaterialEditText) findViewById(R.id.eventsAddNotes);

        // Instantiate Variable MaterialEditText eventsAddNotes//
        eventsAddPeopleInCharge = (MaterialEditText) findViewById(R.id.eventsAddPeopleInCharge);

        // Instantiate Variable MaterialEditText eventsAddPhoneNumber//
        eventsAddPhoneNumber = (MaterialEditText) findViewById(R.id.eventsAddPhoneNumber);

        // Instantiate Variable TextView eventsAddDate//
        eventsAddDate = (TextView) findViewById(R.id.eventsAddDate);

        // Instantiate Variable TextView eventsAddTimeEnd//
        eventsAddTimeEnd = (TextView) findViewById(R.id.eventsAddTimeEnd);

        // Instantiate Variable TextView eventsAddTimeStart//
        eventsAddTimeStart = (TextView) findViewById(R.id.eventsAddTimeStart);

        // Instantiate Variable TextView eventsAddTimeTotal//
        eventsAddTimeTotal = (TextView) findViewById(R.id.eventsAddTimeTotal);

        // Gets Name Of Last Clicked List View Item//
        eventsViewNameUser = getIntent().getStringExtra(EventsView.EVENTS_ADD_NAME_USER);

        // Gets Name Of Last Clicked List View Item//
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
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(EventsAdd.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

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
        TimePickerDialog timePickerDialogTimeStart = TimePickerDialog.newInstance(EventsAdd.this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

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
                eventsAddTimeStart.setText(timeStartString);

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
        TimePickerDialog timePickerDialogTimeEnd = TimePickerDialog.newInstance(EventsAdd.this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

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
                eventsAddTimeEnd.setText(timeEndString);

                try {

                    // Initiate calculateTimeTotal Method//
                    calculateTimeTotal();

                } catch (ParseException e) { e.printStackTrace(); }
            }
        });

        //</editor-fold>
    }
}
