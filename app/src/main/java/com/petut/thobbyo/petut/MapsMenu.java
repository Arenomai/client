package com.petut.thobbyo.petut;

import android.os.Bundle;
import android.app.Activity;

import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.MapboxAccountManager;

public class MapsMenu extends Activity implements OnMapReadyCallback {

    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapboxAccountManager.start(this, "pk.eyJ1IjoiZWxlbWVudHciLCJhIjoiY2l1aTRwcGZjMDAyazJ2cWx5" +
                "amxqM25ueiJ9.EBJG9EXzpFBf6gSBbyvWZw");

        setContentView(R.layout.activity_maps_menu);

        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                // Customize map with markers, polylines, etc.

            }
        });
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        /* LatLng sydney = new LatLng(-34, 151);
        mMapView.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMapView.moveCamera(CameraUpdateFactory.newLatLng(sydney)); */
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}
