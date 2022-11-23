package com.jihyun.mobilesoftwareproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Clickevent {

    TextView monthYear;
    TextView choose_date;
    LocalDate selectedDate;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monthYear = findViewById(R.id.monthYear);
        ImageButton pre_but = findViewById(R.id.pre_but);
        ImageButton next_but = findViewById(R.id.next_but);
        recyclerView = findViewById(R.id.recyclerView);
        selectedDate = LocalDate.now();
        setMonthView();
        Click_date(String.valueOf(selectedDate.getDayOfMonth()));

        pre_but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                selectedDate = selectedDate.minusMonths(1);
                setMonthView();
            }
        });

       next_but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                selectedDate = selectedDate.plusMonths(1);
                setMonthView();
            }
        });

        ImageButton input_button = findViewById(R.id.input_button);
        input_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String Curr_date = choose_date.getText().toString();
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("select_date", Curr_date);
                startActivity(intent);
            }
        });
    }
    private String printDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy\nMM");
        return date.format(formatter);
    }

    private String printDate2(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월");
        return date.format(formatter);
    }


    private void setMonthView(){
        monthYear.setText(printDate(selectedDate));
        String content = monthYear.getText().toString();
        SpannableString spannableString = new SpannableString(content);
        String word = String.valueOf(monthYear);
        spannableString.setSpan(new RelativeSizeSpan(2.5f), 5, 7, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        monthYear.setText(spannableString);

        ArrayList<String> dayarray = date_arr(selectedDate);
        DateDecision decision = new DateDecision(dayarray, MainActivity.this);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 7);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(decision);
    }

    private ArrayList<String> date_arr(LocalDate date){
        ArrayList<String> dayarray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int Last_date = yearMonth.lengthOfMonth();
        LocalDate First_date = selectedDate.withDayOfMonth(1);
        int First_day = First_date.getDayOfWeek().getValue();
        for(int i = 1; i < 42; i++){
            if(i <= First_day || i > Last_date + First_day){
                dayarray.add(null);
            }
            else
            {
                dayarray.add(String.valueOf(i - First_day));
            }
        }
        return dayarray;
    }

    @Override
    public void Click_date(String day) {
        int year = selectedDate.getYear();
        int monthValue = selectedDate.getMonthValue();
        LocalDate selectedDate2 = LocalDate.of(year, monthValue, Integer.parseInt(day));
        DayOfWeek dayOfWeek = selectedDate2.getDayOfWeek();
        String Date= printDate2(selectedDate) + " " + day + "일 " + dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
        choose_date = findViewById(R.id.choose_date);
        choose_date.setText(Date);
    }
}