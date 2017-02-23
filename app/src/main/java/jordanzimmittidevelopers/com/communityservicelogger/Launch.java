package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class Launch extends AppCompatActivity {

    // What Happens When Activity Starts//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent(this, UsersView.class);
        startActivity(i);
        overridePendingTransition(R.anim.slid_in, R.anim.slid_out);
    }
}
