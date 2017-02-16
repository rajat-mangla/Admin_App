package com.example.rajatiit.admin_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rajatiit.admin_app.teacherInterface.TeacherInterface;
import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        buttonClicks(this);

    }

    private void buttonClicks(final Context context){
        Button button = (Button) findViewById(R.id.teachers);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,TeacherInterface.class);
                startActivity(intent);
            }
        });
    }
}
