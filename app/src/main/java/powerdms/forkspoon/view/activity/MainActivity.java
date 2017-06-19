package powerdms.forkspoon.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import powerdms.forkspoon.R;
import powerdms.forkspoon.view.fragment.FavoriteRestaurantFragment;
import powerdms.forkspoon.view.fragment.NearbyRestaurantFragment;
import powerdms.forkspoon.view.fragment.RestaurantFragment;

public class MainActivity extends RuntimePermissionsActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RestaurantFragment frag_restaurant;
    NearbyRestaurantFragment frag_nearby;
    FavoriteRestaurantFragment frag_favorite;

    public String[] permissionsRequired = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_PERMISSION_SETTING = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        handlePermissions();
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            frag_nearby = NearbyRestaurantFragment.newInstance();
            displayContentView(frag_nearby);
        }
    }

    private void handlePermissions() {
        requestAppPermissions(permissionsRequired, R.string.runtime_permissions
                , REQUEST_PERMISSION_SETTING);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_category) {

            PackageManager pm = this.getPackageManager();
            int hasFineLocation = pm.checkPermission(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    this.getPackageName());

            if (hasFineLocation == PackageManager.PERMISSION_GRANTED) {
                frag_restaurant = RestaurantFragment.newInstance(1);
                displayContentView(frag_restaurant);
            }

        } else if (id == R.id.nav_nearby) {

            PackageManager pm = this.getPackageManager();
            int hasFineLocation = pm.checkPermission(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    this.getPackageName());

            if (hasFineLocation == PackageManager.PERMISSION_GRANTED) {
                frag_nearby = NearbyRestaurantFragment.newInstance();
                displayContentView(frag_nearby);
            }
        } else if (id == R.id.nav_cuisine) {

            PackageManager pm = this.getPackageManager();
            int hasFineLocation = pm.checkPermission(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    this.getPackageName());

            if (hasFineLocation == PackageManager.PERMISSION_GRANTED) {
                frag_restaurant = RestaurantFragment.newInstance(2);
                displayContentView(frag_restaurant);
            }
        } else if (id == R.id.nav_favorites) {
            frag_favorite = FavoriteRestaurantFragment.newInstance();
            displayContentView(frag_favorite);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayContentView(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_content, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }
}
