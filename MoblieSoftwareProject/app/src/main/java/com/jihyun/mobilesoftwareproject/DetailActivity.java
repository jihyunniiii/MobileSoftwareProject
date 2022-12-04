package com.jihyun.mobilesoftwareproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private MenuDatabase menuDatabase;
    public static final String TABLE_NAME = "menu";
    SQLiteDatabase database;

    TextView date_text;
    TextView type_;
    TextView time_;
    TextView num_;
    TextView review_;
    TextView name_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        date_text = findViewById(R.id.date_text);
        String curr;
        Intent intent = getIntent();
        curr = intent.getStringExtra("now_date");
        date_text.setText(curr);

        type_ = findViewById(R.id.type_text);
        time_ = findViewById(R.id.time_text);
        num_ = findViewById(R.id.num_text);
        review_ = findViewById(R.id.review_text);
        name_ = findViewById(R.id.name_text);
        menuDatabase = MenuDatabase.getInstance(this);
        database = menuDatabase.getWritableDatabase();
        selectMenu(TABLE_NAME);
        ImageButton check_button = findViewById(R.id.check_button);
        check_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    public void selectMenu(String t_name){
        if (database != null) {
            type_.setText("");
            time_.setText("");
            num_.setText("");
            review_.setText("");
            name_.setText("");

            String sql = "SELECT type, time, num, review, name FROM " + t_name;
            Cursor cursor  = database.rawQuery(sql, null);
            for(int i = 0; i < cursor.getCount(); i++)
            {
                cursor.moveToNext();
                String type = cursor.getString(0);
                String time = cursor.getString(1);
                String num = cursor.getString(2);
                String review = cursor.getString(3);
                String name = cursor.getString(4);
                type_.setText(type);
                time_.setText(time);
                num_.setText(num);
                review_.setText(review);
                name_.setText(name);
            }
            cursor.close();
        }
    }
}