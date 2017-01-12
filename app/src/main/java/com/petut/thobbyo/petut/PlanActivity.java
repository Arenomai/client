package com.petut.thobbyo.petut;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationListener;
import com.mapbox.mapboxsdk.location.LocationServices;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.petut.thobbyo.petut.Armes.Arme;
import com.petut.thobbyo.petut.Armes.Attaque.EpeeBois;
import com.petut.thobbyo.petut.Armes.Attaque.HacheFer;
import com.petut.thobbyo.petut.Armes.Attaque.HacheOr;
import com.petut.thobbyo.petut.Armes.Defense.BouclierBois;
import com.petut.thobbyo.petut.Armes.ObjetPlan;
import com.petut.thobbyo.petut.jeuDeCarte.GameView;

import java.util.ArrayList;

import static com.petut.thobbyo.petut.R.id.markerViewContainer;
import static com.petut.thobbyo.petut.R.id.view;
import static java.security.AccessController.getContext;

public class PlanActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mMapView;
    private MapboxMap map;
    private FloatingActionButton floatingActionButton;
    private LocationServices locationServices;

    private static final int PERMISSIONS_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapboxAccountManager.start(this, "pk.eyJ1IjoiZWxlbWVudHciLCJhIjoiY2l1aTRwcGZjMDAyazJ2cWx5" +
                "amxqM25ueiJ9.EBJG9EXzpFBf6gSBbyvWZw");

        setContentView(R.layout.activity_plan);

        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        // Positionnement GPS librement inspiré : https://www.mapbox.com/android-sdk/examples/user-location/

        locationServices = LocationServices.getLocationServices(PlanActivity.this);

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final MapboxMap mapboxMap) {
                map = mapboxMap;

                toggleGps(true);

                final ArrayList<ObjetPlan> listeObjets = new ArrayList<>();

                // POUR LE SERVEUR : Donner les objets autour du joueur à la position actuelle

                listeObjets.add(new ObjetPlan(new HacheFer(), 45, 4, false));
                listeObjets.add(new ObjetPlan(new BouclierBois(), 45, 5, false));
                listeObjets.add(new ObjetPlan(new HacheOr(), 44, 4, true));

                for(int i=0 ; i<listeObjets.size() ; i++) {
                    ObjetPlan obj = listeObjets.get(i);
                    MarkerViewOptions marqueurObj = new MarkerViewOptions().position(new LatLng(obj.getLatitude(), obj.getLongitude()));
                    marqueurObj.title(obj.getObjet().getTitre());
                    marqueurObj.snippet(obj.getObjet().getTitre());
                    marqueurObj.getMarker().setId(i);
                    mapboxMap.addMarker(marqueurObj);
                }

                mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {

                        ObjetPlan obj = listeObjets.get((int) marker.getId());
                        if(obj.getSeMerite() == true) {

                            Intent intent = new Intent(PlanActivity.this, GameActivity.class);
                            PlanActivity.this.startActivity(intent);

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Objet récupéré : "+obj.getObjet().getTitre(), Toast.LENGTH_SHORT).show();
                            mapboxMap.removeMarker(marker);

                            // POUR LE SERVEUR : Dire au serveur quel objet a été récupéré dans l'inventaire
                        }
                        return true;
                    }
                });

                if(locationServices.getLastLocation() != null) {
                    CameraPosition position = new CameraPosition.Builder()
                            .target(new LatLng(locationServices.getLastLocation()))
                            .zoom(15) // Sets the zoom
                            .tilt(30) // Set the camera tilt
                            .build(); // Creates a CameraPosition from the builder

                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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

    @Override
    public void onMapReady(MapboxMap mapboxMap) {

    }


    private void toggleGps(boolean enableGps) {
        if (enableGps) {
            // Check if user has granted location permission
            if (!locationServices.areLocationPermissionsGranted()) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_LOCATION);
            } else {
                enableLocation(true);
            }
        } else {
            enableLocation(false);
        }
    }

    private void enableLocation(boolean enabled) {
        if (enabled) {
            // If we have the last location of the user, we can move the camera to that position.
            Location lastLocation = locationServices.getLastLocation();
            if (lastLocation != null) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation), 16));
            }

            locationServices.addLocationListener(new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        // Move the map camera to where the user location is and then remove the
                        // listener so the camera isn't constantly updating when the user location
                        // changes. When the user disables and then enables the location again, this
                        // listener is registered again and will adjust the camera once again.
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location), 16));
                        locationServices.removeLocationListener(this);
                    }
                }
            });
        }
        // Enable or disable the location layer on the map
        map.setMyLocationEnabled(enabled);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableLocation(true);
            }
        }
    }
}
