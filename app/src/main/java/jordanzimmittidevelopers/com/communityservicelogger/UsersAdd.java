package jordanzimmittidevelopers.com.communityservicelogger;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

// UsersAdd Class Created By Jordan Zimmitti 1-21-17//
public class UsersAdd extends AppCompatActivity {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Define Variable Vibrator Vibe//
    private Vibrator vibe;

    //</editor-fold>

    //<editor-fold desc="Widgets">

    //<editor-fold desc="MaterialEditText">

    // Define Variable MaterialEditText usersAddAge//
    private MaterialEditText usersAddAge;

    // Define Variable MaterialEditText usersAddName//
    private MaterialEditText usersAddName;

    // Define Variable MaterialEditText usersAddOrganization//
    private MaterialEditText usersAddOrganization;

    //</editor-fold>

    //<editor-fold desc="TextViews">

    // Define Variable TextView nameLetter//
    private TextView nameLetter;

    //</editor-fold>

    //</editor-fold>

    //</editor-fold>

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Starts UI For Activity//
        setContentView(R.layout.users_add_ui);

        // Instantiate Variable MaterialEditText usersAddAge//
        usersAddAge = (MaterialEditText) findViewById(R.id.usersAddAge);

        // Instantiate Variable MaterialEditText usersAddName//
        usersAddName = (MaterialEditText) findViewById(R.id.usersAddName);

        // Instantiate Variable MaterialEditText usersAddOrganization//
        usersAddOrganization = (MaterialEditText) findViewById(R.id.usersAddOrganization);
    }

    // Creates Menu And All Its Components//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflates The Menu / This Adds Items To The Action Bar If It Is Present//
        getMenuInflater().inflate(R.menu.users_add_menu, menu);

        // Kill Code//
        return true;
    }

    // What Happens When Menu Buttons Are Clicked//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Figures Out What Menu Button Was Pressed//
        int id = item.getItemId();

        // What Happens When usersAddSave Is Pressed//
        if (id == R.id.usersAddSave) {


            // Kill Code//
            return true;
        }

        // Kill Code//
        return super.onOptionsItemSelected(item);
    }

    // What Happens When Circle Image Is Clicked//
    public void onClickCircleImage(View view) {
    }
}
