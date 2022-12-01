package com.jihyun.mobilesoftwareproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Geocoder geocoder;
    private GoogleMap mMap;

    private TextView date_text;
    private ImageView food_image;
    private TextView type_text;
    private TextView time_text;
    private TextView name_text;
    private TextView number_text;
    private TextView kcal_text;
    private TextView place_text;
    private TextView review_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        geocoder = new Geocoder(this, Locale.KOREA);
        date_text = findViewById(R.id.date_text);
        food_image = findViewById(R.id.food_image);
        type_text = findViewById(R.id.type_text);
        time_text = findViewById(R.id.time_text);
        name_text = findViewById(R.id.name_text);
        number_text = findViewById(R.id.number_text);
        kcal_text = findViewById(R.id.kcal_text);
        place_text = findViewById(R.id.place_text);
        review_text = findViewById(R.id.review_text);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.output_map);
        mapFragment.getMapAsync(this);

        ImageButton check_button = findViewById(R.id.check_button);
        check_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    MarkerOptions markerOptions = new MarkerOptions();

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng start = new LatLng(37.55201, 126.99150);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 13));

    }
}