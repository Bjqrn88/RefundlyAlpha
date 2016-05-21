package com.macbear.refundlyalpha;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.baasbox.android.BaasBox;
import com.baasbox.android.BaasHandler;
import com.baasbox.android.BaasResult;
import com.baasbox.android.BaasUser;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private Realm realm;
    private RealmConfiguration realmConfig;
    private BaasBox client;
    private SyncRealm sync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BaasBox.Builder b =
                new BaasBox.Builder(this);
        client = b.setApiDomain("ec2-54-93-73-245.eu-central-1.compute.amazonaws.com")
                .setPort(9000)
                .setAppCode("1234567890")
                .init();

        realmConfig = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        realm = Realm.getInstance(realmConfig);

        BaasUser user = BaasUser.withUserName("android")
                .setPassword("bjqrn");
        user.login(new BaasHandler<BaasUser>() {
            @Override
            public void handle(BaasResult<BaasUser> result) {
                if(result.isSuccess()) {
                    Log.d("LOG", "The user is currently logged in: "+result.value());
                } else {
                    Log.e("LOG","Show error",result.error());
                }
            }
        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sync = new SyncRealm();
        sync.sync();



        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null){
                    Profile.setCurrentProfile(null);
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frameholder, new LoginFragment())
                            .commit();
                }
                else{
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                }
            }
        };
        if(AccessToken.getCurrentAccessToken()!=null){
            SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(this);
            int i = sharedPreferences.getInt("last",R.id.nav_post);
            onNavigationItemSelected(i);
        }
        else{
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frameholder, new LoginFragment())
                    .commit();
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (item.getItemId()) {
            case R.id.nav_post:

                sharedPreferences.edit().putInt("last", item.getItemId()).commit();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameholder, new PostFragment())
                        .commit();
                break;
            case R.id.nav_home:
                sharedPreferences.edit().putInt("last", item.getItemId()).commit();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameholder, new HomeFragment())
                        .commit();
                break;
            case R.id.nav_collect:/*
                fragmentManager.beginTransaction()
                        .replace(R.id.frameholder, new CollectFragment())
                        .commit();*/
                Context context = getApplicationContext();
                CharSequence text = "Under Udvikling";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                break;
            case R.id.nav_map:
                sharedPreferences.edit().putInt("last", item.getItemId()).commit();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameholder, new MapFragment())
                        .commit();
                break;
            /*case R.id.nav_manage:
                break;*/
            case R.id.nav_profile:
                sharedPreferences.edit().putInt("last", item.getItemId()).commit();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameholder, new ProfilFragment())
                        .commit();
                break;
            /*case R.id.nav_login:
                fragmentManager.beginTransaction()
                        .replace(R.id.frameholder, new LoginFragment())
                        .commit();*/
            case R.id.nav_send:
                LoginManager.getInstance().logOut();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public boolean onNavigationItemSelected(int id ) {
        // Handle navigation view item clicks here.

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (id) {
            case R.id.nav_post:

                sharedPreferences.edit().putInt("last", id).commit();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameholder, new PostFragment())
                        .commit();
                break;
            case R.id.nav_home:
                sharedPreferences.edit().putInt("last", id).commit();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameholder, new HomeFragment())
                        .commit();
                break;
            case R.id.nav_collect:/*
                fragmentManager.beginTransaction()
                        .replace(R.id.frameholder, new CollectFragment())
                        .commit();*/
                Context context = getApplicationContext();
                CharSequence text = "Under Udvikling";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                break;
            case R.id.nav_map:
                sharedPreferences.edit().putInt("last", id).commit();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameholder, new MapFragment())
                        .commit();
                break;
            /*case R.id.nav_manage:
                break;*/
            case R.id.nav_profile:
                sharedPreferences.edit().putInt("last", id).commit();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameholder, new ProfilFragment())
                        .commit();
                break;
            /*case R.id.nav_login:
                fragmentManager.beginTransaction()
                        .replace(R.id.frameholder, new LoginFragment())
                        .commit();*/
            case R.id.nav_send:
                LoginManager.getInstance().logOut();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
