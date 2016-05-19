package com.macbear.refundlyalpha;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by MacBear on 12/05/16.
 */
public class GetLocation implements LocationListener {

    private static final String TAG = "GetLocation";

    private Context mContext;
    private Location mLocation;

    public GetLocation(Context mContext) {
        this.mContext = mContext;
    }

    public LatLng getCurrentLocation() {
        LocationManager locationManager = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (mLocation == null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20000, 0, this);
        }

        LatLng myCoords = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());

        return myCoords;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged: YES");
        this.mLocation = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
