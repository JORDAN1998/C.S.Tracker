package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import de.hdodenhof.circleimageview.CircleImageView;

import static jordanzimmittidevelopers.com.communityservicelogger.UsersDatabase.getBytes;

// UsersEdit Class Created By Jordan Zimmitti 1-28-17//
public class UsersEdit extends AppCompatActivity {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Define static Variable Int SELECT_PICTURE//
    private static final int SELECT_PICTURE = 0;

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

    // What Happens When User Grabs Picture//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // What Happens When Picture Was Picked Successfully//
        if (requestCode == SELECT_PICTURE && data!=null) {

            // Define And Instantiate Variable Uri selectedImage / Get Image Selected//
            Uri selectedImage = data.getData();

            usersEditNameLetter.setVisibility(View.INVISIBLE);

            // Show Image Selected By User//
            usersEditCircleImage.setImageURI(selectedImage);
        }
    }

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

    // Creates Menu And All Its Components//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflates The Menu / This Adds Items To The Action Bar If It Is Present//
        getMenuInflater().inflate(R.menu.users_edit_menu, menu);

        // Kill Code//
        return true;
    }

    //Controls Back Button Functions//
    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        switch (keyCode) {

            // What Happens When Back Button Is Pressed//
            case KeyEvent.KEYCODE_BACK:

                // Define and Instantiate Variable Intent UsersView//
                Intent usersView = new Intent(this, UsersView.class);

                // Start Activity UsersView//
                startActivity(usersView);

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

        // What Happens When usersAddSave Is Pressed//
        if (id == R.id.usersEditSave) {

            // Initiate userSave Method//
            userSave();

            // Kill Code//
            return true;
        }

        // Kill Code//
        return super.onOptionsItemSelected(item);
    }

    // What Happens When usersViewCircleImage Is Clicked//
    public void onClickCircleImage(View view) {

        // Vibrate For 50mm//
        vibe.vibrate(50);

        // Define And Instantiate Variable Intent picture / Let User Pick A Picture//
        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        // Allow User To Pick The Picture They Want//
        startActivityForResult(picture, SELECT_PICTURE);
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


        // Instantiate Variable CircleImageView usersViewCircleImage//
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

    // Method That Save User Profile//
    private void userSave() {

        // Vibrate For 50m//
        vibe.vibrate(50);

        // What Happens When All EditTexts Are Filled Out//
        if (!usersEditName.getText().toString().isEmpty() && !usersEditAge.getText().toString().isEmpty() && !usersEditOrganization.getText().toString().isEmpty()) {

            // Define and Instantiate Variable BitmapDrawable bitmapDrawable//
            BitmapDrawable bitmapDrawable = (BitmapDrawable) usersEditCircleImage.getDrawable();

            // Define And Instantiate Variable Bitmap bitmap / Convert Drawable To Bitmap//
            Bitmap bitmap = bitmapDrawable.getBitmap();

            // What Happens If nameLetter Is Invisible Or Visible//
            if (usersEditNameLetter.getVisibility() == View.INVISIBLE) {

                // Update Values Without usersEditNameLetter Into Database//
                usersDatabase.updateRow(passedVar, usersEditName.getText().toString(), usersEditAge.getText().toString(), usersEditOrganization.getText().toString(), "", getBytes(bitmap));

                // Close Database When Finished/
                usersDatabase.close();

            } else {

                // Update Values With usersEditNameLetter Into Database//
                usersDatabase.updateRow(passedVar, usersEditName.getText().toString(), usersEditAge.getText().toString(), usersEditOrganization.getText().toString(), usersEditNameLetter.getText().toString(), getBytes(bitmap));

                // Close Database When Finished/
                usersDatabase.close();
            }

            // Define and Instantiate Variable Intent UsersView//
            Intent usersView = new Intent(this, UsersView.class);

            // Start Activity UsersView//
            startActivity(usersView);

            // Custom Transition//
            overridePendingTransition(R.anim.slid_in, R.anim.slid_out);

        } else {

            Toast.makeText(getApplicationContext(), "Not All Filled Out", Toast.LENGTH_LONG).show();
        }
    }
}
