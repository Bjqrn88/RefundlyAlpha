package com.macbear.refundlyalpha;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.macbear.refundlyalpha.Realm.ProfilInformation;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginFragment extends Fragment {
    LoginButton loginButton;


    CallbackManager callbackManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        //LoginButton loginButton = (LoginButton) rod.findViewById(R.id.usersettings_fragment_login_button);
        //loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() { ... });
    }
    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        final View rod = i.inflate(R.layout.fragment_login, container, false);
        final TextView textView = (TextView)rod.findViewById(R.id.textView);
        loginButton = (LoginButton) rod.findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");
        // If using in a fragment
        loginButton.setFragment(this);
        // Other app specific specialization
        // Callback registration

        final DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker mProfileTracker;
            Profile profile;

            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile3, Profile profile2) {
                            // profile2 is the new profile
                            profile = profile2;
                            profile.setCurrentProfile((profile2));
                            textView.setText("Hej " + profile.getFirstName());
                            SharedPreferences sharedPreferences =
                                    PreferenceManager.getDefaultSharedPreferences(getActivity());
                            sharedPreferences.edit().putString("firstName", profile.getFirstName());
                            sharedPreferences.edit().putString("lastName", profile.getLastName());
                            sharedPreferences.edit().putString("id", profile.getId());
                            addProfile();
                            mProfileTracker.stopTracking();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.beginTransaction()
                                .replace(R.id.frameholder, new PostFragment())
                                .commit();
                        }
                    };
                    mProfileTracker.startTracking();
                } else {
                    profile = Profile.getCurrentProfile();
                    textView.setText("Hej " + profile.getFirstName());
                    SharedPreferences sharedPreferences =
                            PreferenceManager.getDefaultSharedPreferences(getActivity());
                    sharedPreferences.edit().putString("firstName", profile.getFirstName());
                    sharedPreferences.edit().putString("lastName", profile.getLastName());
                    sharedPreferences.edit().putString("id", profile.getId());
                    addProfile();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frameholder, new PostFragment())
                            .commit();
                }
            }

            @Override
            public void onCancel() {
                textView.setText("Weird, we had an error on login ");
            }

            @Override
            public void onError(FacebookException exception) {
                textView.setText("Weird, we had an error on login "+ exception.toString());
            }
        });
        return rod;
    }
    public  void onActivityResult(int requestcode, int resoultcode,Intent data ){
        super.onActivityResult(requestcode, resoultcode, data);
        callbackManager.onActivityResult(requestcode, resoultcode, data);

    }

    private void addProfile(){
        new AsyncTask(){
            protected Object doInBackground(Object... arg0) {
                Realm realm = Realm.getDefaultInstance();
                RealmResults<ProfilInformation> profiler = realm.where(ProfilInformation.class).equalTo("profileID", Profile.getCurrentProfile().getId()).findAll();
                if(profiler.size()==0){
                    ProfilInformation profil = new ProfilInformation();
                    profil.setUsername(Profile.getCurrentProfile().getFirstName()+" "+Profile.getCurrentProfile().getLastName());
                    profil.setProfileID(Profile.getCurrentProfile().getId());
                    profil.setPhone("");
                    profil.setAdress("");
                    profil.setCollector(false);
                    profil.setPostNumber("");
                    realm.beginTransaction();
                    realm.copyToRealm(profil);
                    realm.commitTransaction();
                }

                return null;
            }
        }.execute();
    }
}
