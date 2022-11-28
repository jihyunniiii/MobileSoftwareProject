package com.jihyun.mobilesoftwareproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class mapActivity extends AppCompatActivity{

    private ImageButton checkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        String curr;
        Intent intent = getIntent();
        curr = intent.getStringExtra("select_date");

        // 장소
        checkButton = findViewById(R.id.map_check);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mapActivity.this, EditActivity.class);
                intent.putExtra("select_date", curr);
                startActivity(intent);
            }
        });
    }
}