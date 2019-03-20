package com.internship.ipda3.semicolon.sofra.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.internship.ipda3.semicolon.sofra.R;
import com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.RestaurantDetailsFragment;
import com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.RestaurantFragment;
import com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.navigations.ItemsFragment;
import com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.navigations.UserOrderFragment;
import com.internship.ipda3.semicolon.sofra.ui.fragment.homeCycle.navigations.offers.OffersListFragment;
import com.internship.ipda3.semicolon.sofra.ui.fragment.userCycle.LoginFragment;

import static com.internship.ipda3.semicolon.sofra.Constants.SharedPreferenceKeys.API_TOKEN;
import static com.internship.ipda3.semicolon.sofra.Constants.keys.USER_TYPE;
import static com.internship.ipda3.semicolon.sofra.util.HelperMethod.replace;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadIntegerData;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.LoadStringData;
import static com.internship.ipda3.semicolon.sofra.local.SharedPreferencesManager.remove;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int userType;
    private String apiToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userType = LoadIntegerData(this, USER_TYPE);
        apiToken = LoadStringData(this, API_TOKEN);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (userType == 1) {
            //replace restaurants fragment
            replace(R.id.home_activity_frame, new RestaurantFragment(), getSupportFragmentManager());
        } else if (userType == 2) {
            //replace restaurant details
            replace(R.id.home_activity_frame, new RestaurantDetailsFragment(), getSupportFragmentManager());
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        Menu menu = navigationView.getMenu();

        if (userType == 1) {
            menu.findItem(R.id.nav_commission).setVisible(false);
            fab.hide();
            //TODO inflate user menu item to navigation view...
        } else {
            menu.findItem(R.id.nav_my_items).setTitle(getString(R.string.my_food));
            menu.findItem(R.id.nav_notification).setTitle(getString(R.string.order_notifications));
            menu.findItem(R.id.nav_offers).setTitle(getString(R.string.my_offers));
            //TODO inflate restaurant menu item to navigation view...
        }
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        remove(this, USER_TYPE);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_my_items:
                //TODO if user, replace items fragment and display user's order
                //TODO if restaurant, replace the same fragment and display restaurant's items/foods
                if (userType == 1) {
                    if (apiToken == null) {
                        showLoginAlertDialog();
                    } else {
                        replace(R.id.home_activity_frame, new UserOrderFragment(), getSupportFragmentManager());


                    }
                } else if (userType == 2) {
                    replace(R.id.home_activity_frame, new ItemsFragment(), getSupportFragmentManager());
                }
                break;
            case R.id.nav_notification:
                //TODO if user, replace user orders fragment
                //TODO if restaurant, replace restaurant's notification.
                replace(R.id.home_activity_frame, new UserOrderFragment(), getSupportFragmentManager());
                break;
            case R.id.nav_offers:
                //TODO if user, replace new offers list fragment
                replace(R.id.home_activity_frame, new OffersListFragment(), getSupportFragmentManager());


        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showLoginAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.login_text))
                .setMessage(getString(R.string.login_string))
                .setPositiveButton(getString(R.string.login_button_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        replace(R.id.home_activity_frame, new LoginFragment(), getSupportFragmentManager());
                    }
                }).setNegativeButton(getString(R.string.dismiss), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }
}
