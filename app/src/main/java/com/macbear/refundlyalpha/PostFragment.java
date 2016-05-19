package com.macbear.refundlyalpha;


import android.content.Context;
import android.location.Criteria;
import android.location.Geocoder;
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

import com.baasbox.android.BaasDocument;
import com.baasbox.android.BaasHandler;
import com.baasbox.android.BaasResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.macbear.refundlyalpha.Realm.PostInfomation;

import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class PostFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, OnMapReadyCallback,
        GoogleMap.OnMapClickListener{
    Realm realm;
    Button post;
    EditText address, postalCode, commentField;
    SeekBar valueEstimate;
    int size;
    GoogleMap map;
    MarkerOptions mMaker;
    Geocoder mGeocoder;

    MapFragment mapFragment = new MapFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_post, container, false);

        realm = Realm.getDefaultInstance();

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.mapPost);

        mapFragment.getMapAsync(this);

        mMaker = new MarkerOptions();
        mGeocoder = new Geocoder(this.getContext(), Locale.getDefault());


        // Post refund button
        post = (Button) root.findViewById(R.id.postButton);
        post.setOnClickListener(this);

        // Text fields
        address = (EditText) root.findViewById(R.id.addressRoad);
        postalCode = (EditText) root.findViewById(R.id.addressPostalCode);
        commentField = (EditText) root.findViewById(R.id.comment);

        // Set size of refund
        valueEstimate = (SeekBar) root.findViewById(R.id.value);
        valueEstimate.setOnSeekBarChangeListener(this);
        // Setting the max size og seeker to 100
        valueEstimate.setMax(100);

        // initiate size to 0
        size = 0;

        return root;
    }


    @Override
    public void onClick(View view) {
        final Date timeStamp = new Date();
        final int nextId;
        int nextId1;
        try {
            nextId1 = (realm.where(PostInfomation.class).max("postId").intValue() + 1);
        }catch (NullPointerException e){
            Log.d("PostFragment", "onClick: Error when setting nextid: "+e.getLocalizedMessage());
            nextId1 = 0;
        }
        nextId = nextId1;
        Log.d("PostFragment", "onClick: nextid = "+nextId);
        realm.executeTransactionAsync(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {

                PostInfomation post = new PostInfomation();
                post.setPostId(nextId);
                post.setAddress(address.getText().toString());
                post.setPostNumber(postalCode.getText().toString());
                post.setLat(mMaker.getPosition().latitude);
                post.setLnt(mMaker.getPosition().longitude);
                post.setCollectorID("");
                post.setPostProfileID("KimPossible321");
                post.setComment(commentField.getText().toString());
                post.setSize(size);
                post.setTimestamp(timeStamp);
                realm.copyToRealmOrUpdate(post);
            }
        });

        BaasDocument doc = new BaasDocument("PostInformation");
        doc.put("posterProfileID","KimPossible123")
                .put("postId", nextId)
                .put("address",address.getText().toString())
                .put("postalCode",postalCode.getText().toString())
                .put("lat", mMaker.getPosition().latitude)
                .put("lnt", mMaker.getPosition().longitude)
                .put("collectorID","")
                .put("comment",commentField.getText().toString())
                .put("size", size)
                .put("timeStamp", timeStamp.toString());
        doc.save(new BaasHandler<BaasDocument>() {
            @Override
            public void handle(BaasResult<BaasDocument> res) {
                if(res.isSuccess()) {
                    Log.d("LOG","Saved: "+res.value());
                } else {
                    Log.d("LOG","Error: "+res.value());
                }
            }
        });

        mapFragment.setCoords(new LatLng(mMaker.getPosition().latitude, mMaker.getPosition().longitude));

        getFragmentManager().beginTransaction()
                .replace(R.id.frameholder, new MapFragment())
                .commit();

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        size = i;
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

        mMaker.position(myCoords).title("Comment: "+commentField.getText().toString());
        map.addMarker(mMaker);

        float zoomLevel = 16;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myCoords, zoomLevel));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        map.clear();
        map.addMarker(mMaker.position(latLng).title("Comment: "+commentField.getText().toString()));
    }

}
