package com.udnmobile.fragment.fragmentlifecycle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener, LocationListener {

    private final static String TAG = "MapsActivity";

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private GoogleMap mMap;

    private GoogleApiClient mGoogleApiClient;

    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        init();

        initHandler();

    }

    private void init() {

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void initHandler() {

    }

    @Override
    protected void onStart() {
        super.onStart();

        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected void placeMarkerOnMap(LatLng location){
        MarkerOptions markerOptions = new MarkerOptions().position(location);

        //加上marker icon
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.pin_b)));

        mMap.addMarker(markerOptions);
    }

    /**
     * Starting with Android 6.0, Permissions are classified into two categories: normal and dangerous categories.
     * Permissions that request access to the user’s private information such as the user’s CONTACTS, CALENDAR, LOCATION.
     * The code will checks if the app has been granted the ACCESS_FINE_LOCATION permission. If it hasn’t, then request it from the user.
     */
    private void setUpMap() {

        //android 6.0 permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        //Getting Current Location
        //enables the my-location layer draws a light blue dot on the user’s location
        mMap.setMyLocationEnabled(true);

        //determines the availability of location data on the device.
        LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);

        if(null != locationAvailability && locationAvailability.isLocationAvailable()){
            //gives you the most recent location currently available
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if(mLastLocation != null){
                LatLng currentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                //add pin at user's location
                placeMarkerOnMap(currentLocation);

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(40.73, -73.99);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //Automatically centered the marker on the screen, newLatLngZoom() help to zoom level up to 12.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));

        //enable Zoom control
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //declare Activity as the callback triggered when the user clicks a marker on this map.
        mMap.setOnMarkerClickListener(this);

        //LatLng UDN = new LatLng(25.061248, 121.640778);
        //mMap.addMarker(new MarkerOptions().position(UDN).title("UDN聯合報"));

        /*//定位功能setting,會出現紅色底線,但能正常編譯
        mMap.setMyLocationEnabled(true);
        //右下角的放大縮小功能
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //左上角的指南針,要兩指旋轉才會出現
        mMap.getUiSettings().setCompassEnabled(true);
        //右下角的導覽及開啟Google Map的功能
        mMap.getUiSettings().setMapToolbarEnabled(true);

        Log.d(TAG, "最高放大層級:" + mMap.getMaxZoomLevel());
        Log.d(TAG, "最低放大層級:" + mMap.getMinZoomLevel());

        //
        mMap.moveCamera(CameraUpdateFactory.newLatLng(UDN));
        //放大地圖到16倍大
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));*/
    }

    @Override
    public void onConnected(Bundle bundle) {
        setUpMap();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
