package com.example.ahmed_esmail;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText AddressTxt;
    FloatingActionButton DetermineLocation;
    LocationGPSListener GPSfunction;
    LocationManager LocManger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        final String CustId=getIntent().getExtras().getString("ID");
        //---------------------------------------------------------
        Button Okay=(Button)findViewById(R.id.button7);
        //----------------------------------------------------------
        DetermineLocation=(FloatingActionButton)findViewById(R.id.floatingActionButton2);
        AddressTxt=(EditText)findViewById(R.id.CurentLocation_editText);
        GPSfunction=new LocationGPSListener(getApplicationContext());
        LocManger=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //----------------------------------------------------------
        try
        {
            LocManger.requestLocationUpdates(LocationManager.GPS_PROVIDER,20000,0,GPSfunction);
        }
        catch (SecurityException ex)
        {
            Toast.makeText(getApplicationContext(), "you are not allowed to access the current location", Toast.LENGTH_SHORT).show();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //---------------------------------------------------------------------------------------------
        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent T0_Submission=new Intent(MapsActivity.this,submissionActivity.class);
                T0_Submission.putExtra("ID",CustId);
                T0_Submission.putExtra("Address",AddressTxt.getText().toString());
                startActivity(T0_Submission);
            }
        });
        //---------------------------------------------------------------------------------------------
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960,31.235711600),8));
        DetermineLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                Location loc=null;
                Geocoder geo=new Geocoder(getApplicationContext());
                List<Address>addressList;
                try {
                    loc=LocManger.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                catch (SecurityException ex)
                {
                    Toast.makeText(getApplicationContext(), "you didn't allow to access the current location", Toast.LENGTH_SHORT).show();
                }
                if(loc!=null)
                {
                    LatLng myposition=new LatLng(loc.getLatitude(),loc.getLongitude());
                    try
                    {
                        addressList=geo.getFromLocation(myposition.latitude,myposition.longitude,1);
                        if(!addressList.isEmpty())
                        {
                            String Address="";
                            for(int i=0;i<=addressList.get(0).getMaxAddressLineIndex();i++)
                                Address+=addressList.get(0).getAddressLine(i);
                            mMap.addMarker(new MarkerOptions().position(myposition).title("Your Location").snippet(Address)).setDraggable(true);
                            AddressTxt.setText(Address);
                        }

                    }
                    catch (IOException ex)
                    {
                        mMap.addMarker(new MarkerOptions().position(myposition).title("Your Location"));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myposition,15));
                }
                else
                    Toast.makeText(getApplicationContext(), "Please wait until your position is determined", Toast.LENGTH_SHORT).show();


            }
        });
        //---------------------------------------------------------------------------------------
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

                Geocoder geo=new Geocoder(getApplicationContext());
                List<Address>addressList;
                try
                {
                    addressList=geo.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);
                    if(!addressList.isEmpty())
                    {
                        String Address="";
                        for(int i=0;i<=addressList.get(0).getMaxAddressLineIndex();i++)
                            Address+=addressList.get(0).getAddressLine(i);
                        AddressTxt.setText(Address);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "No Address in this Location", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (IOException ex)
                {
                    Toast.makeText(getApplicationContext(), "Can't get the address ..Check the network.", Toast.LENGTH_SHORT).show();
                }

        }
        });
    }
}
