package com.jihyun.mobilesoftwareproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        TextView Curr_date = findViewById(R.id.Curr_date);
        String curr;
        Intent intent = getIntent();
        curr = intent.getStringExtra("select_date");
        Curr_date.setText(curr + " 식단 추가");

        ImageButton input_button = findViewById(R.id.save_button);
        input_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                TextView Curr_date = findViewById(R.id.Curr_date);
                intent.putExtra("now_date", Curr_date.getText().toString());
                startActivity(intent);
            }
        });
    }
}