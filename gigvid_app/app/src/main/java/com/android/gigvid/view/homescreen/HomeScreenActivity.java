package com.android.gigvid.view.homescreen;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.android.gigvid.R;
import com.android.gigvid.utils.sharedPref.SharedPrefUtils;
import com.android.gigvid.view.loginsignup.UserAuthActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

public class HomeScreenActivity extends AppCompatActivity {
    private BottomNavigationView navView;
    private MaterialToolbar topAppBar;
    private MenuItem searchMenuItem;
    private MenuItem logoutMenuItem;
    private SearchView searchView;
    private View activityView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        initializeUI();
    }

    /**
     * Method: Initialize UI
     */
    private void initializeUI() {
        activityView = findViewById(android.R.id.content);
        navView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.app_logo_drawable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);

        searchMenuItem = menu.findItem(R.id.search);
        logoutMenuItem = menu.findItem(R.id.logout);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // TODO: Launch Home Fragment
                return true;
            case R.id.logout:
                onLogoutMenuItemClicked();
                return true;
            case R.id.search:
                onSearchMenuItemClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * Method: Click Listener for Logout Menu Button
     */
    private void onLogoutMenuItemClicked(){
        logoutMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                launchLogoutAlertDialog();
                return false;
            }
        });
    }

    /**
     * Method: Launch Search option
     */
    private void onSearchMenuItemClicked() {
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Snackbar.make(activityView, "Searching: " + query, Snackbar.LENGTH_SHORT).show();
                searchMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    /**
     * Method: Launch Logout Alert Dialog
     */
    private void launchLogoutAlertDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("LOGOUT")
                .setMessage("Are you sure you want to Logout?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Snackbar.make(activityView, "Operation Cancelled", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                })
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPrefUtils.clearPrefOnLogout();
                        redirectToUserAuthActivity();
                    }
                })
                .show();
    }

    /**
     * Method: Launch UserAuthActivity and open Login fragment
     */
    private void redirectToUserAuthActivity() {
        Intent loginActivity = new Intent(getApplicationContext(), UserAuthActivity.class);
        startActivity(loginActivity);
        finish();
    }

}