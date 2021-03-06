package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import static jordanzimmittidevelopers.com.communityservicelogger.UsersDatabase.getBytes;

// UsersAdd Class Created By Jordan Zimmitti 1-21-17//
public class UsersAdd extends AppCompatActivity {

    //<editor-fold desc="Variables">

    //<editor-fold desc="Extra">

    // Define static Variable Int SELECT_PICTURE//
    private static final int SELECT_PICTURE = 0;

    // Define Variable UsersDatabase usersDatabase//
    private UsersDatabase usersDatabase;

    // Define Variable Vibrator Vibe//
    private Vibrator vibe;

    //</editor-fold>

    //<editor-fold desc="Widgets">

    //<editor-fold desc="MaterialEditText">

    // Define Variable MaterialEditText usersAddAge//
    private MaterialEditText usersAddAge;

    // Define Variable MaterialEditText usersAddName//
    private MaterialEditText usersAddName;

    // Define Variable MaterialEditText usersAddGrade//
    private MaterialEditText usersAddGrade;

    // Define Variable MaterialEditText usersAddOrganization//
    private MaterialEditText usersAddOrganization;

    //</editor-fold>

    //<editor-fold desc="TextViews">

    // Define Variable TextView nameLetter//
    private TextView usersAddNameLetter;

    //</editor-fold>

    //<editor-fold desc="ImageViews">

    // Define Variable ImageView circleImage//
    private ImageView usersAddCircleImage;

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

            // Hide Name Letter//
            usersAddNameLetter.setVisibility(View.INVISIBLE);

            // Show Image Selected By User//
            usersAddCircleImage.setImageURI(selectedImage);
        }
    }

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initiate applyTheme Method//
        applyTheme();

        // Initiate InstantiateWidgets Method//
        instantiateWidgets();

        // Initiate usersDatabaseOpen Method//
        usersDatabaseOpen();

        // Initiate circleImageLongClick Method//
        onLongClickCircleImage();
    }

    // Creates Menu And All Its Components//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflates The Menu / This Adds Items To The Action Bar If It Is Present//
        getMenuInflater().inflate(R.menu.users_add_menu, menu);

        // Kill Code//
        return true;
    }

    //What Happens When Back Button Is Pressed//
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
        if (id == R.id.usersAddSave) {

            // Initiate userSave Method//
            userSave();

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
        setContentView(R.layout.users_add_ui);

        // Define And Instantiate RelativeLayout relativeLayout//
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.users_add_ui);

        // Night Mode Theme Extension Options//
        pickTheme.activityNightModeExtension(this, relativeLayout);

    }

    // Method That Instantiates Widgets//
    private void instantiateWidgets() {

        // Instantiate Variable MaterialEditText usersAddAge//
        usersAddAge = (MaterialEditText) findViewById(R.id.usersAddAge);


        // Instantiate Variable ImageView circleImage//
        usersAddCircleImage = (ImageView) findViewById(R.id.usersAddCircleImage);


        // Instantiate Variable MaterialEditText usersAddGrade//
        usersAddGrade = (MaterialEditText) findViewById(R.id.usersAddGrade);


        // Instantiate Variable MaterialEditText usersAddName//
        usersAddName = (MaterialEditText) findViewById(R.id.usersAddName);

        // Tells App To Watch The Text Being Added To usersAddName//
        usersAddName.addTextChangedListener(new TextWatcher() {

            // What Happens Before text Is Changed//
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // What Happens When Text is Changing//
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                // Define And Instantiate Variable String usersAddNameText / Put usersAddNameText Into String//
                String usersAddNameText = String.valueOf(charSequence);

                // What Happens If charSequence Is Empty Or Starts With A Space//
                if (!usersAddNameText.equals("")) {

                    // Define And Instantiate Variable char charFirstCharacter / Get First Letter Of Text Being Added//
                    char charFirstCharacter = usersAddName.getText().toString().charAt(0);

                    // Define And Instantiate Variable String firstLetter / Put First Character Into String//
                    String firstLetter = String.valueOf(charFirstCharacter);

                    // Set That Letter To nameLetter textView//
                    usersAddNameLetter.setText(firstLetter);
                }
            }

            // What Happens When Text Is Finished Changing//
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        // Instantiate Variable TextView nameLetter//
        usersAddNameLetter = (TextView) findViewById(R.id.usersAddNameLetter);


        // Instantiate Variable MaterialEditText usersAddOrganization//
        usersAddOrganization = (MaterialEditText) findViewById(R.id.usersAddOrganization);


        // Instantiate Variable Vibrator vibe//
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    // Method That Handles When Circle Image Is Clicked//
    public void onClickCircleImage(View view) {

        // Vibrate For 50mm//
        vibe.vibrate(50);

        // Define And Instantiate Variable Intent picture / Let User Pick A Picture//
        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        // Allow User To Pick The Picture They Want//
        startActivityForResult(picture, SELECT_PICTURE);
    }

    // Method That Runs When circleImage Is Long Clicked//
    private void onLongClickCircleImage() {

        // What Happens When User Long Clicks circleImage//
        usersAddCircleImage.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {

                // Set circleImage To White//
                usersAddCircleImage.setImageResource(R.drawable.white);

                // Make nameLetter Visible//
                usersAddNameLetter.setVisibility(View.VISIBLE);

                // Define And Instantiate Variable char charFirstCharacter / Get First Letter Of Text Being Added//
                char charFirstCharacter = usersAddName.getText().toString().charAt(0);

                // Define And Instantiate Variable String firstLetter / Put First Character Into String//
                String firstLetter = String.valueOf(charFirstCharacter);

                // Set That Letter To nameLetter textView//
                usersAddNameLetter.setText(firstLetter);

                // Kill Code//
                return true;
            }
        });
    }

    // Method That Opens Database//
    private void usersDatabaseOpen() {

        // Instantiate Variable UsersDatabase usersDatabase//
        usersDatabase = new UsersDatabase(this);

        // Open Database//
        usersDatabase.open();
    }

    // Method That Save User Profile//
    private void userSave() {

        // Vibrate For 50m//
        vibe.vibrate(50);

        // What Happens When All EditTexts Are Filled Out//
        if (!usersAddName.getText().toString().equals("") && !usersAddAge.getText().toString().equals("") && !usersAddOrganization.getText().toString().equals("")) {

            // Define and Instantiate Variable BitmapDrawable bitmapDrawable//
            BitmapDrawable bitmapDrawable = (BitmapDrawable) usersAddCircleImage.getDrawable();

            // Define And Instantiate Variable Bitmap bitmap / Convert Drawable To Bitmap//
            Bitmap bitmap = bitmapDrawable.getBitmap();

            // What Happens If nameLetter Is Invisible Or Visible//
            if (usersAddNameLetter.getVisibility() == View.INVISIBLE) {

                // Insert Values Without nameLetter Into Database//
                usersDatabase.insertRow(usersAddName.getText().toString(), usersAddAge.getText().toString(), usersAddGrade.getText().toString(), usersAddOrganization.getText().toString(), "", getBytes(bitmap));

                // Close Database When Finished/
                usersDatabase.close();

            } else {

                // Insert Values With nameLetter Into Database//
                usersDatabase.insertRow(usersAddName.getText().toString(), usersAddAge.getText().toString(), usersAddGrade.getText().toString(), usersAddOrganization.getText().toString(), usersAddNameLetter.getText().toString(), getBytes(bitmap));

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

            // Create Dialog//
            new MaterialDialog.Builder(UsersAdd.this)

                    // Title Of Dialog//
                    .title("Warning")

                    // Content Of Dialog//
                    .content("Make sure all text fields are filled out")

                    // Negative Text Name For Button//
                    .negativeText("Cancel")

                    // Show Material Dialog//
                    .show();
        }
    }
}
