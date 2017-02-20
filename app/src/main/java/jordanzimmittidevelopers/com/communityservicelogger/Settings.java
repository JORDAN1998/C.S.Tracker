package jordanzimmittidevelopers.com.communityservicelogger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

// Settings Class Created By Jordan Zimmitti 2-19-17//
public class Settings extends AppCompatActivity {

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Starts UI For Activity//
        setContentView(R.layout.settings_ui);
    }

    // What Happens When Themes Is Clicked//
    public void onClickThemes(View view) {
    }
}
