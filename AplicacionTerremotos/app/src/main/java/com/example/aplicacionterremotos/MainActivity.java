package com.example.aplicacionterremotos;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import org.osmdroid.config.Configuration;
import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.overlay.Marker;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private MapView map = null;
    private EarthquakeData earthquakeData;
    private Handler handler;
    private Spinner spinnerDays;
    private int selectedDays = 30;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        Configuration.getInstance().setUserAgentValue(getPackageName());

        setContentView(R.layout.activity_main);

        spinnerDays = findViewById(R.id.spinner_days);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDays.setAdapter(adapter);
        spinnerDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        selectedDays = 30;
                        break;
                    case 1:
                        selectedDays = 2;
                        break;
                    case 2:
                        selectedDays = 3;
                        break;
                }
                updateEarthquakes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(5.0);
        GeoPoint startPoint = new GeoPoint(23.6345, -102.5528); // Centro de México
        mapController.setCenter(startPoint);

        earthquakeData = new EarthquakeData();
        handler = new Handler(Looper.getMainLooper());

        requestPermissionsIfNecessary(new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        });

        updateEarthquakes();
    }

    private void updateEarthquakes() {
        earthquakeData.getEarthquakes(selectedDays, new EarthquakeData.EarthquakeCallback() {
            @Override
            public void onEarthquakesReceived(List<Earthquake> earthquakes) {
                runOnUiThread(() -> {
                    map.getOverlays().clear();
                    for (Earthquake quake : earthquakes) {
                        Marker marker = new Marker(map);
                        marker.setPosition(new GeoPoint(quake.latitude, quake.longitude));
                        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                        marker.setTitle("Mag: " + quake.magnitude + " - " + quake.place);

                        int color = getColorForMagnitude(quake.magnitude);
                        Drawable icon = ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_marker);
                        if (icon != null) {
                            icon.setTint(color);
                            marker.setIcon(icon);
                        }

                        map.getOverlays().add(marker);
                    }
                    map.invalidate();
                });
            }

            @Override
            public void onError(String error) {
                // Manejar el error aquí
            }
        });
        handler.postDelayed(this::updateEarthquakes, 5 * 60 * 1000);
    }



    private int getColorForMagnitude(double magnitude) {
        if (magnitude < 3.0) {
            return ContextCompat.getColor(this, android.R.color.holo_green_light);
        } else if (magnitude < 4.0) {
            return ContextCompat.getColor(this, android.R.color.holo_orange_light);
        } else if (magnitude < 5.0) {
            return ContextCompat.getColor(this, android.R.color.holo_orange_dark);
        } else if (magnitude < 6.0) {
            return ContextCompat.getColor(this, android.R.color.holo_red_light);
        } else if (magnitude < 7.0) {
            return ContextCompat.getColor(this, R.color.purple);
        } else if (magnitude < 8.0) {
            return ContextCompat.getColor(this, R.color.pink);
        } else {
            return ContextCompat.getColor(this, android.R.color.black);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permissions[i]);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    PERMISSION_REQUEST_CODE);
        }
    }
}