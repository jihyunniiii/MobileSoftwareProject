package com.jihyun.mobilesoftwareproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {
    private String[] category = {"Breakfast", "Lunch", "Dinner", "Snack"};
    private TextView categoryText;
    private AlertDialog categoryDialog;

    private String[] category2;
    private TextView categoryText2;
    private AlertDialog categoryDialog2;

    private TextView timeText;
    private Uri imageUri;
    private ImageView inputImageView;
    private TextView imageText;


    //데이터베이스 임시구현
    private MenuDatabase menuDatabase;
    public static final String TABLE_NAME = "menu";
    SQLiteDatabase database;

    private MenuDatabase2 menuDatabase2;
    public static final String TABLE_NAME2 = "menu2";
    SQLiteDatabase database2;

    TextView type_text;
    TextView time_text;
    TextView mn_text;
    EditText num_text;
    EditText review_text;
    int kcal_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        TextView Curr_date = findViewById(R.id.Curr_date);
        String curr;
        Intent intent = getIntent();
        curr = intent.getStringExtra("select_date");
        Curr_date.setText(curr + " 식단 추가");
        menuDatabase = MenuDatabase.getInstance(this);
        database = menuDatabase.getWritableDatabase();

        menuDatabase2 = MenuDatabase2.getInstance(this);
        database2 = menuDatabase2.getWritableDatabase();

        type_text = findViewById(R.id.textView5);
        time_text = findViewById(R.id.textView6);
        mn_text = findViewById(R.id.textView7);
        num_text = findViewById(R.id.textView3);
        review_text = findViewById(R.id.textView11);

        // 데이터 저장.
        ImageButton save_button = findViewById(R.id.save_button);
        save_button.setOnClickListener(new OnClickListener(){
            public void onClick(View view){
                String type = type_text.getText().toString().trim();
                String time = time_text.getText().toString().trim();
                String mn = mn_text.getText().toString().trim();
                String num = num_text.getText().toString().trim();
                String review = review_text.getText().toString().trim();
                getkcal(TABLE_NAME2, mn);
                //{name, date, type, time, num, review}
                insertmenu(mn, curr, type, time, num, review, kcal_size);
                Intent intent = new Intent(EditActivity.this, DetailActivity.class);
                //Intent intent = new Intent(EditActivity.this, MainActivity.class);
                intent.putExtra("now_date", curr);
                startActivity(intent);
            }
        });

        getAllmenu(TABLE_NAME2);
        //메뉴 선택
        categoryText2 = (TextView) findViewById(R.id.textView7);
        categoryText2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryDialog2.show();
            }
        });

        categoryDialog2 = new AlertDialog.Builder(EditActivity.this)
                .setItems(category2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        categoryText2.setText(category2[i]);
                    }
                })
                .setTitle("메뉴를 선택해주세요")
                .setPositiveButton("확인",null)
                .setNegativeButton("취소",null)
                .create();

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
                        categoryText.setText(category[i]);
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
                        timeText.setText(state + " " + selectedHour + " : " + selectedMinute);
                    }
                }, hour, minute, false);
                MyTimePicker.setTitle("식사 시간을 설정하세요");
                MyTimePicker.show();
            }
        });

        // 이미지
        imageText = (TextView) findViewById(R.id.textView2);
        inputImageView = (ImageView) findViewById(R.id.inputimageview);
        imageText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityResult.launch(intent);
            }
        });


    }

    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            inputImageView.setImageBitmap(bitmap);
                            imageText.setText(getPath(imageUri));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    public String getPath(Uri uri) {
        String path;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null){
            path = uri.getPath();
        }
        else
        {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            path = cursor.getString(index);
            cursor.close();
        }
        return path;
    }

    public void insertmenu(String name, String date, String type, String time, String num, String review, int kcal) {
        if (database != null) {
            String sql = "INSERT INTO menu(name, date, type, time, num, review, kcal) VALUES(?, ?, ?, ?, ?, ?, ?)";
            Object[] params = {name, date, type, time, num, review, kcal};
            database.execSQL(sql, params);
        }
    }

    public void getAllmenu(String t_name){
        if (database2 != null){
            String sql = "SELECT name FROM " + t_name;
            Cursor cursor  = database2.rawQuery(sql, null);
            category2 = new String[cursor.getCount()];
            for(int i = 0; i < cursor.getCount(); i++)
            {
                cursor.moveToNext();
                String data = cursor.getString(0);
                category2[i] = data;
            }
            cursor.close();
        }
    }

    public void getkcal(String t_name, String name) {
        if (database2 != null)
        {
            String sql = "SELECT kcal FROM " + t_name + " WHERE name = \"" + name + "\"";
            Cursor cursor = database2.rawQuery(sql, null);
            for(int i = 0; i < cursor.getCount(); i++)
            {
                cursor.moveToNext();
                int data = cursor.getInt(0);
                kcal_size = data;
            }
        }
    }
/*
    public void deletemenu(String mnn){
        if (database != null) {
            String sql = "DELETE FROM menu WHERE mnn=?";
            Object[] params = {mnn};
            database.execSQL(sql, params);
        }
    }
 */
}