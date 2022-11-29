package com.jihyun.mobilesoftwareproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MenuDatabase extends SQLiteOpenHelper {

    private static MenuDatabase instance;
    public static synchronized MenuDatabase getInstance(Context context){
        if (instance == null) {
            instance = new MenuDatabase(context, "Menu", null, 1);
        }
        return instance;
    }

    public static final int VERSION = 1;
    public static final String DB_NAME = "Menu.db";
    public static final String TABLE_NAME = "menu";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_MNN = "mnn";
    public static final String COLUMN_KCAL = "kcal";
    public static final String COLUMN_REVIEW = "review";

    public static final String SQL_CREATE_MENU = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DATE + " TEXT, " + COLUMN_TYPE + " TEXT, "
            + COLUMN_TIME + " TEXT, " + COLUMN_MNN + " TEXT, " + COLUMN_KCAL + " TEXT, " + COLUMN_REVIEW + " TEXT" + ");";

    private MenuDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MENU);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > 1) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME);
        }
    }
}
