package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        float zoom = 16f;

        LatLng centerMap = new LatLng(41.403389, 2.174028);

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(centerMap, zoom));

        LatLng centerMark = new LatLng(41.403389, 2.174028);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(centerMark);
        markerOptions.title("Access DB Location");

        BitmapDrawable bitmapDraw = (BitmapDrawable)ContextCompat.getDrawable(getApplicationContext(), R.drawable.rx_location);
        Bitmap smallMarker = Bitmap.createScaledBitmap(bitmapDraw.getBitmap(), 100, 100, false);

        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

        googleMap.addMarker(markerOptions);

        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.custom_map));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //IF YOU WANT TO  USE AN XML
    private Bitmap drawableToBitmap(Drawable drawable){
        try {
            Bitmap bitmap;

            bitmap = Bitmap.createBitmap(25, 25, Bitmap.Config.RGB_565);

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;

        } catch (OutOfMemoryError e) {
            // Handle the error
            return null;
        }
    }
}