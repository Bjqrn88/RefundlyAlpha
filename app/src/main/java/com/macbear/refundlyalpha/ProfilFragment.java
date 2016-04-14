package com.macbear.refundlyalpha;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class ProfilFragment extends Fragment implements View.OnClickListener {

    ImageView profilePicture;
    EditText profilUsername, profilAddressRoad, profilAddressPostalCode, profilAddressCity;
    Button profilCancel, profilSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profil, container, false);

        // Profile picture
        profilePicture = (ImageView) root.findViewById(R.id.profilPict);
        profilePicture.setOnClickListener(this);
        // Username
        profilUsername = (EditText) root.findViewById(R.id.profilUsernameField);
        // Address text fields for profile
        profilAddressRoad = (EditText) root.findViewById(R.id.profilAddressRoad);
        profilAddressPostalCode = (EditText) root.findViewById(R.id.profilAddressPostalcode);
        profilAddressCity = (EditText) root.findViewById(R.id.profilAddressCity);
        // Cancel and Save button
        profilCancel = (Button) root.findViewById(R.id.profilCancelButton);
        profilCancel.setOnClickListener(this);
        profilSave = (Button) root.findViewById(R.id.profilSaveButton);
        profilSave.setOnClickListener(this);


        return root;
    }

    @Override
    public void onClick(View view) {
        if (view == profilePicture){

        }

        if (view == profilCancel){

        }

        if (view == profilSave){

        }
    }
}
