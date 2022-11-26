package com.jihyun.mobilesoftwareproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {
    private String[] category = {"Breakfast", "Lunch", "Dinner", "Snack"};
    private TextView categoryText;
    private AlertDialog categoryDialog;

    private TextView timeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        TextView Curr_date = findViewById(R.id.Curr_date);
        String curr;
        Intent intent = getIntent();
        curr = intent.getStringExtra("select_date");
        Curr_date.setText(curr + " 식단 추가");

        ImageButton save_button = findViewById(R.id.save_button);
        save_button.setOnClickListener(new OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(EditActivity.this, DetailActivity.class);
                //Intent intent = new Intent(EditActivity.this, MainActivity.class);
                //TextView Curr_date = findViewById(R.id.Curr_date);
                //intent.putExtra("now_date", Curr_date.getText().toString());
                startActivity(intent);
            }
        });

        // 식사 유형
        categoryText = (TextView) findViewById(R.id.textView5);
        categoryText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryDialog.show();
            }
        });

        categoryDialog = new AlertDialog.Builder(EditActivity.this)
                .setItems(category, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        categoryText.setText("  " + category[i]);
                    }
                })
                .setTitle("식사 유형을 선택해주세요")
                .setPositiveButton("확인",null)
                .setNegativeButton("취소",null)
                .create();

        // 식사 시간
        timeText = (TextView) findViewById(R.id.textView6);
        timeText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog MyTimePicker;
                MyTimePicker = new TimePickerDialog(EditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String state = "AM";

                        if (selectedHour > 12) {
                            selectedHour -= 12;
                            state = "PM";
                        }

                        timeText.setText("  " + state + " " + selectedHour + " : " + selectedMinute);
                    }
                }, hour, minute, false);
                MyTimePicker.setTitle("식사 시간을 설정하세요");
                MyTimePicker.show();
            }
        });
    }

}