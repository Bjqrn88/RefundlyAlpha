package com.macbear.refundlyalpha;


import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
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
import com.google.android.gms.maps.model.MarkerOptions;

public class PostFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, OnMapReadyCallback,
        GoogleMap.OnMapClickListener{

    Button post;
    EditText road, postalCode, commentField;
    SeekBar valueEstimate;
    int value;
    GoogleMap map;
    MarkerOptions mMaker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_post, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.mapPost);

        mapFragment.getMapAsync(this);

        mMaker = new MarkerOptions();


        // Post refund button
        post = (Button) root.findViewById(R.id.postButton);
        post.setOnClickListener(this);

        // Text fields
        road = (EditText) root.findViewById(R.id.addressRoad);
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
        map.setOnMapClickListener(this);
        map.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager)
                getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
        LatLng myCoords = new LatLng(location.getLatitude(), location.getLongitude());

        mMaker.position(myCoords).title("My Refunds");
        map.addMarker(mMaker);

        //map.addMarker(new MarkerOptions().position(myCoords).title(("My location")));
        float zoomLevel = 16;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myCoords, zoomLevel));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        map.clear();
        map.addMarker(mMaker.position(latLng));
    }
}
