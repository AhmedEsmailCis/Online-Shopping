package com.example.ahmed_esmail;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

public class LocationGPSListener implements LocationListener {
    private Context AvtivityContext;
    public LocationGPSListener(Context Act)
    {
        AvtivityContext=Act;
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(AvtivityContext, "GPS Disabled", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(AvtivityContext, "GPS Enabled", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(AvtivityContext, location.getLatitude()+", "+location.getLongitude(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }
}

