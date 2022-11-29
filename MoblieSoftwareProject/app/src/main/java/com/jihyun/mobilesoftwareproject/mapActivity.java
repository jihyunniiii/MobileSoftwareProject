package com.jihyun.mobilesoftwareproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Locale;

public class mapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private ImageButton checkButton;
    private GoogleMap mMap;
    private String address = null;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //String curr;
        //Intent intent = getIntent();
        //curr = intent.getStringExtra("select_date");
        geocoder = new Geocoder(this, Locale.KOREA);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // 확인 버튼
        checkButton = findViewById(R.id.map_check);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(mapActivity.this, EditActivity.class);
                //intent.putExtra("select_date", curr);
                //intent.putExtra("address", address);
                Intent intent = new Intent();
                intent.putExtra("result", address);
                setResult(RESULT_OK, intent);
                //startActivity(intent);
                finish();
            }
        });
    }
    MarkerOptions markerOptions = new MarkerOptions();

    @Override
    public void onMapReady (GoogleMap googleMap) {
        mMap = googleMap;
        LatLng start = new LatLng(37.55201, 126.99150);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 13));

        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng point) {
        mMap.clear();

        Double latitude = point.latitude;
        Double longitude = point.longitude;

        // 주소로 변환
        new Thread(() -> {
            List<Address> addlist = null;
            try {
                addlist = geocoder.getFromLocation(latitude, longitude, 10);
            } catch (IOException e) {
            }

            if (addlist != null) {
                if (addlist.size() == 0)
                {
                    address = "해당되는 주소 정보가 없습니다";
                }
                else
                {
                    address = addlist.get(0).getAddressLine(0);
                }
            }
        }).start();

        markerOptions.title("식사 위치");
        markerOptions.snippet(address);
        markerOptions.position(new LatLng(latitude, longitude));

        mMap.addMarker(markerOptions);
    }
}
