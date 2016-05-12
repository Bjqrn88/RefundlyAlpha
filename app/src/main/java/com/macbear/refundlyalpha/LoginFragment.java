package com.macbear.refundlyalpha;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        View rod = i.inflate(R.layout.fragment_login, container, false);
        final TextView textView = (TextView)rod.findViewById(R.id.textView);
        loginButton = (LoginButton) rod.findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");
        // If using in a fragment
        loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker mProfileTracker;
            Profile profile;

            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                if(Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile3, Profile profile2) {
                            // profile2 is the new profile
                             profile= profile2;
                            textView.setText("Hej " + profile.getFirstName());
                            mProfileTracker.stopTracking();
                        }
                    };
                    mProfileTracker.startTracking();
                }
                else {
                    profile = Profile.getCurrentProfile();
                    textView.setText("Hej " + profile.getFirstName());
                }

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        return rod;
    }
    public  void onActivityResult(int requestcode, int resoultcode,Intent data ){
        super.onActivityResult(requestcode,resoultcode,data);
        callbackManager.onActivityResult(requestcode, resoultcode, data);

    }
}
