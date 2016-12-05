package com.petut.thobbyo.petut;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.petut.thobbyo.petut.jeuDeCarte.GameView;

public class PlanActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mMapView;

    double longitude = 0.0;
    double latitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapboxAccountManager.start(this, "pk.eyJ1IjoiZWxlbWVudHciLCJhIjoiY2l1aTRwcGZjMDAyazJ2cWx5" +
                "amxqM25ueiJ9.EBJG9EXzpFBf6gSBbyvWZw");

        setContentView(R.layout.activity_plan);

        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                MarkerViewOptions marqueurIci = new MarkerViewOptions().position(new LatLng(latitude, longitude));

                MarkerViewOptions marqueurFourviere = new MarkerViewOptions().position(new LatLng(45.76228, 4.82169));
                marqueurFourviere.title("Fourvière");
                marqueurFourviere.snippet("La tour métallique de Fourvière, le plus haut édifice de la ville en altitude.");

                mapboxMap.addMarker(marqueurFourviere);
                mapboxMap.addMarker(marqueurIci);

                mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        if(marker.getTitle() == "Fourvière") {
                            GameView game = new GameView(PlanActivity.this);
                            setContentView(game);
                        }
                        return true;
                    }
                });
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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
