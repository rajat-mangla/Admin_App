package com.example.rajatiit.admin_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (sp.getBoolean(Login.ADMIN_LOGIN_CHECK, false)) {
            Intent intent = new Intent(this, AdminMainActivity.class);
            startActivity(intent);
            finish();
        }
        else if (sp.getBoolean(Login.TEACHER_LOGIN_CHECK, false)) {
            Intent intent = new Intent(this, TeacherMainActivity.class);
            startActivity(intent);
            finish();
        }
        else if (sp.getBoolean(Login.BATCH_LOGIN_CHECK, false)) {
            Intent intent = new Intent(this, BatchMainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }

    }
}
