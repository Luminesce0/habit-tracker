package com.omegaspockatari.habittracker;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HabitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);
    }

    private void insertHabit() {
        /** Acquire data repository in write mode */
        SQLiteDatabase db =
    }
}
