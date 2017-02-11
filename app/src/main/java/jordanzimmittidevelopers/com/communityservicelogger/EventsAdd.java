package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

// EventsAdd Class Created By Jordan Zimmitti 2-09-17//
public class EventsAdd extends AppCompatActivity {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Define Variable Vibrator Vibe//
    private Vibrator vibe;

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

        // Instantiate Variable MaterialEditText eventsAddDate//
        eventsAddDate = (TextView) findViewById(R.id.eventsAddDate);

        // Instantiate Variable MaterialEditText eventsAddTimeEnd//
        eventsAddTimeEnd = (TextView) findViewById(R.id.eventsAddTimeEnd);

        // Instantiate Variable MaterialEditText eventsAddTimeStart//
        eventsAddTimeStart = (TextView) findViewById(R.id.eventsAddTimeStart);

        // Instantiate Variable MaterialEditText eventsAddTimeTotal//
        eventsAddTimeTotal = (TextView) findViewById(R.id.eventsAddTimeTotal);

        // Instantiate Variable Vibrator vibe//
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    // What Happens When eventsAddDate Is Clicked//
    public void onClickDate(View view) {

        MaterialDialog
    }

    // What Happens When eventsAddTimeStart Is Clicked//
    public void onClickTimeStart(View view) {
    }

    // What Happens When eventsAddTimeEnd Is Clicked//
    public void onClickTimeEnd(View view) {
    }

    // What Happens When eventsAddTimeTotal Is Clicked//
    public void onClickTimeTotal(View view) {
    }
}
