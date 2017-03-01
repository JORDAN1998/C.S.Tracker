package jordanzimmittidevelopers.com.communityservicelogger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

// UsersNavigationDrawer Class Created By Jordan Zimmitti 2-09-17//
public class UsersNavigationDrawer extends Fragment {

    //<editor-fold desc="Variables">

    // Define Variable DrawerLayout NavigationDrawerLayout//
    private DrawerLayout navigationDrawerLayout;

    // Define Variable View ContainerView//
    private View navigationDrawerToggle;

    //</editor-fold>

    // Lets Navigation Bar Be Seen In App//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate Custom NavigationDrawer Layout//
        return inflater.inflate(R.layout.navigation_drawer_ui, container, false);
    }

    // Sets Up Navigation Drawer//
    public void setUp( DrawerLayout drawerLayout, int navigationDrawer) {

        // Gets Navigation Bar Toggle Id From Main Activity//
        navigationDrawerToggle = getActivity().findViewById(navigationDrawer);

        // Sets Navigation Drawer Layout Value To Drawer Layout//
        navigationDrawerLayout = drawerLayout;

        // Toggles Navigation Drawer Open And Closed//
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.drawer_open, R.string.drawer_close) {

            // What Happens When Navigation Drawer Is Open//
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                // Opens Drawer/
                getActivity().invalidateOptionsMenu();
            }

            // What Happens When Navigation Drawer Is Closed//
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                // Close Drawer/
                getActivity().invalidateOptionsMenu();
            }
        };

        // Sets Open - Close Listener For Navigation Drawer//
        navigationDrawerLayout.addDrawerListener(mDrawerToggle);

        // Syncs Navigation Drawer With Home As Up Button//
        mDrawerToggle.syncState();
    }

    // Add New Items//
    public void addItems(final Context context , final String[] items) {

        // Sets Items In ListView//
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, android.R.id.text1, items);

        // Instantiate RecyclerView recyclerView//
        ListView navListView = (ListView) getActivity().findViewById(R.id.navListView);

        // Sets ListView Adapter//
        navListView.setAdapter(adapter);

        // Set Item Click Listener//
        //<editor-fold desc="setItemClickListener">

        // What Happens On ListView Item Click//
        navListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Define Variable Vibrator vibe//
                Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

                // Get Specific Item//
                String item = adapter.getItem(position);

                // Make Sure item Isn't Null//
                assert item != null;

                switch (item) {

                    // If Item Equals Reminders//
                    case "Reminders": {

                        // Vibrate For 50 Mil//
                        vibe.vibrate(50);

                        // Define and Instantiate Variable Intent remindersView//
                        Intent remindersView = new Intent(context, RemindersView.class);

                        // Start Activity RemindersView//
                        startActivity(remindersView);

                        // Custom Transition//
                        getActivity().overridePendingTransition(R.anim.slid_in, R.anim.slid_out);

                        break;
                    }

                    // If Item Equals Settings//
                    case "Settings": {

                        // Vibrate For 50 Mil//
                        vibe.vibrate(50);

                        // Define and Instantiate Variable Intent settings//
                        Intent settings = new Intent(context, Settings.class);

                        // Start Activity Settings//
                        startActivity(settings);

                        // Custom Transition//
                        getActivity().overridePendingTransition(R.anim.slid_in, R.anim.slid_out);

                        break;
                    }

                    // If Item Equals About//
                    case "About": {

                        // Vibrate For 50 Mil//
                        vibe.vibrate(50);

                        // Define and Instantiate Variable Intent settingsAbout//
                        Intent settingsAbout = new Intent(context, SettingsAbout.class);

                        // Start Activity Settings//
                        startActivity(settingsAbout);

                        // Custom Transition//
                        getActivity().overridePendingTransition(R.anim.slid_in, R.anim.slid_out);

                        break;
                    }
                }
            }
        });

        //</editor-fold>
    }

    // Open NavigationDrawer & Change Button//
    public void buttonToggle() {

        // What Happens When Navigation Drawer Is Open//
        if(navigationDrawerLayout.isDrawerOpen(GravityCompat.START)) {

            // Close Drawer & Change Button//
            navigationDrawerLayout.closeDrawer(navigationDrawerToggle);

        } else {

            // Open Drawer & Change Button//
            navigationDrawerLayout.openDrawer(navigationDrawerToggle);
        }
    }

    // Show Arrow In Toolbar//
    public void showHamburgerIcon(Boolean trueOrFalse) {

        if (trueOrFalse) {

            // Sets Home As Up True//
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        } else {

            // Sets Home As Up True//
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }
}
