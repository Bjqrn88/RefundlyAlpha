package com.macbear.refundlyalpha;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.facebook.Profile;
import com.macbear.refundlyalpha.Realm.ProfilInformation;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class ProfilFragment extends Fragment implements View.OnClickListener {

    ImageView profilePicture;
    EditText profilUsername, profilAddressRoad, profilAddressPostalCode, profilPhoneNumber;
    Button profilCancel, profilSave;
    CheckBox profilCollector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_profil, container, false);

        // Profile picture
        profilePicture = (ImageView) root.findViewById(R.id.profilPict);
        profilePicture.setOnClickListener(this);
        // Username
        profilUsername = (EditText) root.findViewById(R.id.profilUserNameField);
        // Address text fields for profile
        profilAddressRoad = (EditText) root.findViewById(R.id.profilAddressRoad);
        profilAddressPostalCode = (EditText) root.findViewById(R.id.profilAddressPostalcode);
        profilPhoneNumber = (EditText) root.findViewById(R.id.profilPhoneNumber);
        // Collector
        profilCollector = (CheckBox) root.findViewById(R.id.profilCheckBox);
        // Cancel and Save button
        profilCancel = (Button) root.findViewById(R.id.profilCancelButton);
        profilCancel.setOnClickListener(this);
        profilSave = (Button) root.findViewById(R.id.profilSaveButton);
        profilSave.setOnClickListener(this);


        if(Profile.getCurrentProfile()!=null){
            Realm realm = Realm.getDefaultInstance();
            RealmResults<ProfilInformation> profiler = realm.where(ProfilInformation.class).equalTo("profileID", Profile.getCurrentProfile().getId()).findAll();
            if(profiler.size()!=0){
                ProfilInformation temp = profiler.get(0);
                profilUsername.setText(temp.getUsername());
                profilAddressRoad.setText(temp.getAdress());
                profilAddressPostalCode.setText(temp.getPostNumber());
                profilCollector.setChecked(temp.getCollector());
                profilPhoneNumber.setText(temp.getPhone());
            }
            else {
                profilUsername.setText(Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getLastName());
            }
        }

        return root;
    }

    @Override
    public void onClick(View view) {
        if (view == profilCancel){
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frameholder, new ProfilFragment())
                    .commit();
        }

        if (view == profilSave){

            final String username = (profilUsername.getText().toString());
            final String adress = (profilAddressRoad.getText().toString());
            final boolean collector = (profilCollector.isChecked());
            final String postalCode = (profilAddressPostalCode.getText().toString());
            final String phone = (profilPhoneNumber.getText().toString());
            final String id = (Profile.getCurrentProfile().getId());

            // Get a Realm instance for this thread
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {
                    RealmResults<ProfilInformation> profiler = bgRealm.where(ProfilInformation.class).equalTo("profileID", Profile.getCurrentProfile().getId()).findAll();
                    System.out.println(profiler.size());
                    final ProfilInformation  profil = profiler.get(0);
                    profil.setAdress(adress);
                    profil.setCollector(collector);
                    profil.setPostNumber(postalCode);
                    profil.setPhone(phone);
                    profil.setProfileID(id);
                    profil.setUsername(username);
                    bgRealm.copyToRealm(profil);

                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    System.out.println("Write Sucsess");
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    System.out.println("Write failure");
                    error.printStackTrace();

                }
            });
        }
    }
}
