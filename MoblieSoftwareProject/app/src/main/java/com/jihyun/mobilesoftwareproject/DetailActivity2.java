package com.jihyun.mobilesoftwareproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class DetailActivity2 extends AppCompatActivity {

    private MenuDatabase menuDatabase;
    public static final String TABLE_NAME = "menu";
    SQLiteDatabase database;

    TextView type_text;
    TextView time_text;
    TextView name_text;
    TextView num_text;
    TextView review_text;
    TextView date_text;
    ImageButton checkbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);
        Intent intent = getIntent();
        int id;
        String date;
        id = intent.getExtras().getInt("id");
        date = intent.getExtras().getString("date");
        menuDatabase = MenuDatabase.getInstance(this);
        database = menuDatabase.getWritableDatabase();
        type_text = findViewById(R.id.type_text);
        time_text = findViewById(R.id.time_text);
        name_text = findViewById(R.id.name_text);
        num_text = findViewById(R.id.num_text);
        review_text = findViewById(R.id.review_text);
        date_text = findViewById(R.id.date_text);
        checkbutton = findViewById(R.id.check_button);
        date_text.setText(date);
        getAlldata(TABLE_NAME, id);
        checkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DetailActivity2.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }

    private void getAlldata(String t_name, int id)
    {
        if (database != null)
        {
            String sql = "SELECT type, time, name, num, kcal, review FROM " + t_name + " WHERE id = " + id;
            Cursor cursor = database.rawQuery(sql, null);
            for(int i = 0; i < cursor.getCount(); i++)
            {
                cursor.moveToNext();
                String type = cursor.getString(0);
                String time = cursor.getString(1);
                String name = cursor.getString(2);
                String num = cursor.getString(3);
                String kcal = cursor.getString(4);
                String review = cursor.getString(5);
                type_text.setText(type);
                time_text.setText(time);
                name_text.setText(name);
                num_text.setText("음식 하나당 칼로리 : " + kcal + "kcal  /  먹은 양 : " + num + "개");
                review_text.setText(review);
            }
        }
    }
}