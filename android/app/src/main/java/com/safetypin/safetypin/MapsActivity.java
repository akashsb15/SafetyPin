package com.safetypin.safetypin;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, PlaceSelectionListener, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private MarkerCache<Marker> userLocation = new MarkerCache<Marker>(1);
    private MarkerCache<Marker> userDestination= new MarkerCache<Marker>(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        PlaceAutocompleteFragment autoCompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autoCompleteFragment.setOnPlaceSelectedListener(this);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);

        LocationListener locationListener = new LocationListener() {
        final ArrayList<Marker> previous = new ArrayList<>();
            @Override
            public void onLocationChanged(Location location) {
//                Toast.makeText(getApplicationContext(), "" + location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                LatLng newLocation = new LatLng(location.getLatitude(), location.getLongitude());

                Marker m = mMap.addMarker(new MarkerOptions().position(newLocation).title("Current Location"));
                userLocation.add(m);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 15.0f));
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
        };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 5, locationListener);

////        Add a marker in Sydney, Australia, and move the camera.
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        LatLng ny = new LatLng(40.7128, -74.0059);
//        mMap.addMarker(new MarkerOptions().position(ny).title("Marker in NYC"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }

    @Override
    public void onPlaceSelected(Place place) {
        LatLng destination = place.getLatLng();
        Marker m = mMap.addMarker(new MarkerOptions().position(destination).title("Destination").draggable(true));
        userDestination.add(m);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination,13.5f));
    }

    @Override
    public void onError(Status status) {
        Log.e("Autocompletefragment failed", "onError: Status = " + status.toString());

        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMapClick(LatLng destination) {
        Marker m = mMap.addMarker(new MarkerOptions().position(destination).title("Destination").draggable(true));
        userDestination.add(m);
    }
}