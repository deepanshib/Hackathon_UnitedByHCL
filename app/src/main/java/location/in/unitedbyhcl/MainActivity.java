package location.in.unitedbyhcl;

import android.app.Activity;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static final int RC_SIGN_IN = 1;
    public String username,userid;
    public MainActivity mainActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new Layout1_CHAT()).commit();


        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                if (user != null) {
                                        // User is signed in
                                                username = user.getDisplayName();
                                                userid = user.getEmail();
                                                Toast.makeText(MainActivity.this, "You're now signed in. Welcome.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // User is signed out
                                                startActivityForResult(
                                                                AuthUI.getInstance()
                                                                                .createSignInIntentBuilder()
                                                                .setIsSmartLockEnabled(false)
                                                                .setProviders(
                                                                        AuthUI.EMAIL_PROVIDER,
                                                                        AuthUI.GOOGLE_PROVIDER)
                                                                .build(),
                                                        RC_SIGN_IN);
                                    }
                            }
        };
       // NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        View header = navigationView.getHeaderView(0);
//        TextView headerUserName = (TextView) header.findViewById(R.id.name);
//
//        headerUserName.setText("user@gmail.com");
        //  NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.username);
        nav_user.setText(username);
        TextView nav_userid = (TextView)hView.findViewById(R.id.userid);
        nav_user.setText(userid);


    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if (requestCode == RC_SIGN_IN) {
                        if (resultCode == RESULT_OK) {
                                // Sign-in succeeded, set up the UI
                                        Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
                            } else if (resultCode == RESULT_CANCELED) {
                                // Sign in was canceled by the user, finish the activity
                                        Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                    }
            }

    @Override
    protected void onResume() {
              super.onResume();
               mFirebaseAuth.addAuthStateListener(mAuthStateListener);
            }

            @Override
    protected void onPause() {
                super.onPause();
                if (mAuthStateListener != null) {
                        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
                    }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.signout) {
            AuthUI.getInstance().signOut(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_Chat) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new Layout1_CHAT()).commit();
        } else if (id == R.id.nav_events) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new Layout2_EventsNearBy()).commit();
        } else if (id == R.id.nav_createevent) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new Layout3_CreateEvents()).commit();
            //break;
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
