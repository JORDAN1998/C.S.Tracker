package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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

    // Define Variable int timeTotalAdded//
    int timeTotalAdded = 0;

    // Define Variable Vibrator Vibe//
    private Vibrator vibe;

    //</editor-fold>

    //<editor-fold desc="Strings">

    // Military Start Time Int//
    String militaryTimeStart;

    //Military End Time Int//
    String militaryTimeEnd;

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

        // Initiate InstantiateWidgets Method//
        instantiateWidgets();
    }

    // What Happens When User Picks Date//
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        // Define And Instantiate String dateString//
        String dateString = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;

        // Set eventsAddDate Text//
        eventsAddDate.setText(dateString);
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
            Date timeStartValue = null;

            // Define Variable Date timeEndValue//
            Date timeEndValue = null;

            // Creates Simple Date Format//
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

            // Puts Military Time Start Into Time Format//
            timeStartValue = simpleDateFormat.parse(militaryTimeStart);

            // Puts Military Time Start Into Time Format//
            timeEndValue = simpleDateFormat.parse(militaryTimeEnd);

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
