package com.example.nutrifit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "nutrifit.db";
    //private static final String USERTABLE = "users";

    public DBHelper(Context context) {

        super(context, "nutrifit.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        //String usertable = "CREATE TABLE " +USERTABLE+ "(userid INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, date_of_birth TEXT, password TEXT, goal INTEGER, exercise INTEGER, gender TEXT, height REAL, weight REAL)";
        //MyDB.execSQL(usertable);
        MyDB.execSQL("CREATE TABLE users(userid INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, date_of_birth TEXT, password TEXT, goal INTEGER, exercise INTEGER, gender TEXT, height REAL, weight REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("DROP TABLE IF EXISTS email");
        //onCreate(MyDB);
    }

    /*
    public Boolean insertData(String name, String email, String date_of_birth, String password, int goal, int exercise, String gender, float height, float weight) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("date_of_birth", date_of_birth);
        contentValues.put("password", password);
        contentValues.put("goal", goal);
        contentValues.put("exercise", exercise);
        contentValues.put("gender", gender);
        contentValues.put("height", height);
        contentValues.put("weight", weight);
        long result = MyDB.insert("users", null, contentValues);

        return result != -1;

    }*/

    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE email = ?", new String[] {email});
        return cursor.getCount() > 0;
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[] {email, password});
        return cursor.getCount() > 0;
    }
/*
    public boolean getUserName(String email, String pass){
        //HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM users WHERE email" + " = " + "'"+email+"'" + " and " + "password" + " = " + "'"+pass+"'";

        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return  new UserDetails(cursor.getString(cursor.getColumnIndex()),true);
        }
        cursor.close();
        MyDB.close();

        return false;
    }*/
}
