package com.jihyun.mobilesoftwareproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    //사용자가 입력하는 데이터베이스, 메뉴 이름, 아점저, 시간, 메뉴 수량 정보를 담고 있음. 지도 정보와 사진 정보를 추가적으로 구현해야함
    private MenuDatabase menuDatabase;
    public static final String TABLE_NAME = "menu";
    SQLiteDatabase database;

    //메뉴 이름과 칼로리 정보를 담고 있는 데이터베이스
    private MenuDatabase2 menuDatabase2;
    public static final String TABLE_NAME2 = "menu2";
    SQLiteDatabase database2;

    TextView monthYear;
    TextView choose_date;
    LocalDate selectedDate;
    RecyclerView recyclerView;
    RecyclerView mRecyclerView;
    MenuRecyclerAdapter mRecyclerAdapter;
    ArrayList<Menudata> menudata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_menu);
        mRecyclerAdapter = new MenuRecyclerAdapter();
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        menudata = new ArrayList<>();

        menuDatabase = MenuDatabase.getInstance(this);
        database = menuDatabase.getWritableDatabase();

        menuDatabase2 = MenuDatabase2.getInstance(this);
        database2 = menuDatabase2.getWritableDatabase();

        //메뉴 이름과 칼로리 정보 임의로 넣기
        database2.execSQL("DELETE FROM " + TABLE_NAME2);
        insertmenu2("쌀밥", "300");
        insertmenu2("흑미밥", "300");
        insertmenu2("짜장면", "700");
        insertmenu2("라면", "520");
        insertmenu2("햄버거", "400");
        insertmenu2("단팥크림빵", "388");

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
        String Curr_date = choose_date.getText().toString();

        addtext(TABLE_NAME, TABLE_NAME2, Curr_date);
        mRecyclerAdapter.setmenulist(menudata);

        input_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent map_intent = new Intent(MainActivity.this, EditActivity.class);
                map_intent.putExtra("select_date", Curr_date);
                startActivity(map_intent);
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
        if(day != null)
        {
            int year = selectedDate.getYear();
            int monthValue = selectedDate.getMonthValue();
            LocalDate selectedDate2 = LocalDate.of(year, monthValue, Integer.parseInt(day));
            DayOfWeek dayOfWeek = selectedDate2.getDayOfWeek();
            String Date= printDate2(selectedDate) + " " + day + "일 " + dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
            choose_date = findViewById(R.id.choose_date);
            choose_date.setText(Date);
        }
    }

    private void addtext(String t_name, String t_name2, String date){
        if (database != null) {
            String sql = "SELECT type, time, name, num FROM " + t_name + " WHERE date = \"" + date + "\"";
            Cursor cursor = database.rawQuery(sql, null);
            for(int i = 0; i < cursor.getCount(); i++)
            {
                cursor.moveToNext();
                String type = cursor.getString(0);
                String time = cursor.getString(1);
                String name = cursor.getString(2);
                String num = cursor.getString(3);
                menudata.add(new Menudata(type, time + " / " + name));
                //이 다음에 메뉴하고 kcal, 그거는 데이터베이스 추가해서.
                //이 때 kcal은 mn 값에 곱해서 출력해야함.
                //총 칼로리 값도 여기서 계산해서 바꾸면 됨.
            }
            cursor.close();
        }
    }

    private void insertmenu2(String name, String kcal) {
        if (database2 != null) {
            String sql = "INSERT INTO menu2(name, kcal) VALUES(?, ?)";
            Object[] params = {name, kcal};
            database2.execSQL(sql, params);
        }
    }
}