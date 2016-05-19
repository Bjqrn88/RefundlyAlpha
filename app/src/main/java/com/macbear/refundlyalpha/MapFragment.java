package com.macbear.refundlyalpha;


import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.macbear.refundlyalpha.Realm.PostInfomation;

import io.realm.Realm;
import io.realm.RealmResults;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap map;
    Realm realm;
    LatLng myCoords;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        realm = Realm.getDefaultInstance();

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);

        addMakers();

        if (myCoords == null) {
            LocationManager locationManager = (LocationManager)
                    getActivity().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            myCoords = new LatLng(location.getLatitude(), location.getLongitude());
        }
        //map.addMarker(new MarkerOptions().position(myCoords).title(("My location")));
        float zoomLevel = 14;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myCoords, zoomLevel));
    }


    public void addMakers(){
        RealmResults<PostInfomation> results = realm.where(PostInfomation.class).findAll();

        for (PostInfomation post:results) {
            map.addMarker(new MarkerOptions().position(new LatLng(post.getLat(), post.getLnt())).title(post.getComment()));
        }
    }


    public void setCoords(LatLng coords){
        myCoords = coords;
    }
}
