package com.example.nijkap01.demoapp;
import android.Manifest;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
/**
 * Created by nijkap01 on 8/29/2017.
 */

public class LoadTrackerMap extends FragmentActivity implements OnMapReadyCallback {

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    double lat;
    double lg;
    LatLng california;
    LatLng calsub ;
    MarkerOptions californiaMarkerOptions;
    MarkerOptions calSububsMarkerOptions;
    Marker calMarker;
    private GoogleMap mMap;
    Tracker tracker;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    public void changeLocation(double newlat,double newlong)
    {
        calsub = new LatLng(newlat, newlong);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                calMarker.setPosition(calsub);
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Timer timer =new Timer();
        GPSTracker gps = new GPSTracker(this);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if(gps.canGetLocation()) {
            lat = gps.getLatitude(); // returns latitude
            lg = gps.getLongitude();
            california= new LatLng(lat, lg);
            calsub = new LatLng(lat - 2, lg - .002);
            californiaMarkerOptions =new MarkerOptions().position(california).title("Marker in California");
            calSububsMarkerOptions = new MarkerOptions().position(calsub).title("Marker in Sub urbs").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)).draggable(true);
            mMap.addMarker(californiaMarkerOptions);
            calMarker= mMap.addMarker(calSububsMarkerOptions);
            GetSMSThread timerTask = new GetSMSThread(this,calMarker) {

                @Override
                public void run() {
                    lat =lat-0.2;
                    lg=lg;
                    changeLocation(lat,lg);
                }
            };
            timer.scheduleAtFixedRate(timerTask,500,1000);
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(california));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(california));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(calsub));
            //calsub = new LatLng(lat - 20, lg - .002);
            //calMarker.setPosition(calsub);
        }
        else{
            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(-31, 151);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));}
    }
    public class GetSMSThread extends TimerTask {
        Context context;
        Marker marker;
        public GetSMSThread (Context context,Marker callMarker){
            this.context = context;
            this.marker = callMarker;
        }
        @Override
        public void run() {
            //make sure you dont use the context in a background thread
        }
    }

}
