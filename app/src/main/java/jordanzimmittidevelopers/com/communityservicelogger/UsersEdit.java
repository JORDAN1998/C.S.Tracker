package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import de.hdodenhof.circleimageview.CircleImageView;

// UsersEdit Class Created By Jordan Zimmitti 1-28-17//
public class UsersEdit extends AppCompatActivity {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Define Variable byte[] byteImage//
    byte[] byteImage;

    // Define Variable Cursor cursor//
    Cursor cursor;

    // Define Variable UsersDatabase usersDatabase//
    private UsersDatabase usersDatabase;

    // Define Variable Vibrator vibe//
    private Vibrator vibe;

    //</editor-fold>

    //<editor-fold desc="String">

    // Define Variable String passedVar / String Of Id Values//
    private String passedVar = null;

    // Define Variable String userEditNameString//
    private String usersEditNameString;

    // Define Variable String userEditNameLetterString//
    private String usersEditNameLetterString;

    // Define Variable String userEditAgeString//
    private String usersEditAgeString;

    // Define Variable String userEditOrganizationString//
    private String usersEditOrganizationString;

    //</editor-fold>

    //<editor-fold desc="Widgets">

    //<editor-fold desc="MaterialEditText">

    // Define Variable MaterialEditText usersAddAge//
    private MaterialEditText usersEditAge;

    // Define Variable MaterialEditText usersAddName//
    private MaterialEditText usersEditName;

    // Define Variable MaterialEditText usersAddOrganization//
    private MaterialEditText usersEditOrganization;

    //</editor-fold>

    //<editor-fold desc="TextViews">

    // Define Variable TextView nameLetter//
    private TextView usersEditNameLetter;

    //</editor-fold>

    //<editor-fold desc="ImageViews">

    // Define Variable ImageView circleImage//
    private CircleImageView usersEditCircleImage;

    //</editor-fold>

    //</editor-fold>

    //</editor-fold>

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Starts UI For Activity//
        setContentView(R.layout.users_edit_ui);

        // Initiate databaseOpen Method//
        databaseOpen();

        // Initiate getDatabaseValues Method//
        getDatabaseValues();

        // Initiate InstantiateWidgets Method//
        instantiateWidgets();
    }

    // Method That Opens Database//
    private void databaseOpen() {

        // Instantiate Variable UsersDatabase usersDatabase//
        usersDatabase = new UsersDatabase(this);

        // Open Database//
        usersDatabase.open();
    }

    // Method To get Database Values//
    private void getDatabaseValues() {

        // Gets Id Of Last Clicked List View Item//
        passedVar = getIntent().getStringExtra(UsersView.key_row_id_number);

        // Gets Row//
        cursor = usersDatabase.getRow(passedVar);

        // Define And Instantiate Variable Byte byteImage//
        byteImage = cursor.getBlob(UsersDatabase.COL_IMAGE);

        // Instantiate Variable String userEditAgeString//
        usersEditAgeString = cursor.getString(UsersDatabase.COL_AGE);

        // Instantiate Variable String userEditNameString//
        usersEditNameString = cursor.getString(UsersDatabase.COL_NAME);

        // Instantiate Variable String userEditNameLetterString//
        usersEditNameLetterString = cursor.getString(UsersDatabase.COL_NAME_LETTER);

        // Instantiate Variable String userEditOrganization//
        usersEditOrganizationString = cursor.getString(UsersDatabase.COL_ORGANIZATION);
    }

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable MaterialEditText usersEditAge//
        usersEditAge = (MaterialEditText) findViewById(R.id.usersEditAge);

        // Set Text Equal To usersEditAgeString//
        usersEditAge.setText(usersEditAgeString);


        // Instantiate Variable CircleImageView usersViewCircleImageView//
        usersEditCircleImage = (CircleImageView) findViewById(R.id.usersEditCircleImage);


        // Instantiate Variable MaterialEditText usersEditName//
        usersEditName = (MaterialEditText) findViewById(R.id.usersEditName);

        // Set Text Equal To usersEditNameString//
        usersEditName.setText(usersEditNameString);


        // Instantiate Variable TextView usersEditNameLetter//
        usersEditNameLetter = (TextView) findViewById(R.id.usersEditNameLetter);

        //<editor-fold desc="Show or Don't Show Name Letter">

        // What Happens If There Is no Name Letter
        if (usersEditNameLetterString.equals("")) {

            // Get Image From Database And Display It In ListView//
            usersDatabase.getImage(UsersEdit.this, byteImage, usersEditCircleImage);

            // Set usersEditNameLetter To Invisible//
            usersEditNameLetter.setVisibility(View.INVISIBLE);

        } else {

            // Set Text Equal To usersEditNameLetterString//
            usersEditNameLetter.setText(usersEditNameLetterString);
        }

        //</editor-fold>


        // Instantiate Variable MaterialEditText usersEditOrganization//
        usersEditOrganization = (MaterialEditText) findViewById(R.id.usersEditOrganization);

        // Set Text Equal To usersEditOrganizationString//
        usersEditOrganization.setText(usersEditOrganizationString);


        // Instantiate Variable Vibrator vibe//
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }
}
