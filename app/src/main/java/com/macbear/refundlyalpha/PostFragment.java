package com.macbear.refundlyalpha;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class PostFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, OnMapReadyCallback {

    Button post;
    EditText road, city, postalCode, commentField;
    SeekBar valueEstimate;
    int value;
    GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_post, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.mapPost);

        mapFragment.getMapAsync(this);


        // Post refund button
        post = (Button) root.findViewById(R.id.postButton);
        post.setOnClickListener(this);

        // Text fields
        road = (EditText) root.findViewById(R.id.addressRoad);
        city = (EditText) root.findViewById(R.id.addressTown);
        postalCode = (EditText) root.findViewById(R.id.addressPostalCode);
        commentField = (EditText) root.findViewById(R.id.comment);

        // Set value of refund
        valueEstimate = (SeekBar) root.findViewById(R.id.value);
        valueEstimate.setOnSeekBarChangeListener(this);
        // Setting the max value og seeker to 100
        valueEstimate.setMax(100);

        // initiate value to 0
        value = 0;

        return root;
    }


    @Override
    public void onClick(View view) {
        road.setText(""+value);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        Log.d("Seekbar","Value: "+i);
        value = i;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng kgsLyngby = new LatLng(55.771212, 12.502021);


        map.moveCamera(CameraUpdateFactory.newLatLngZoom(kgsLyngby, 12f));
    }
}
