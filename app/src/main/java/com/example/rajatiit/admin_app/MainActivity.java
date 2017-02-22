package com.example.rajatiit.admin_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;
import com.example.rajatiit.admin_app.intefaces.batchInterface.BatchInterface;
import com.example.rajatiit.admin_app.intefaces.classroomInterface.ClassroomInterface;
import com.example.rajatiit.admin_app.intefaces.teacherInterface.TeacherInterface;
import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        buttonClicks(this);
        getAllUsersData();
    }

    private void buttonClicks(final Context context){
        Button teacherButton = (Button) findViewById(R.id.teachers);
        teacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,TeacherInterface.class);
                startActivity(intent);
            }
        });
        Button batchButton = (Button) findViewById(R.id.batches);
        batchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,BatchInterface.class);
                startActivity(intent);
            }
        });
        Button courseButton = (Button)findViewById(R.id.courses);
        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClassroomInterface.class);
                startActivity(intent);
            }
        });
    }

    private void getAllUsersData(){
        DatabaseReference reference = FirebaseClass.getDatabase().getReference("UserStorage");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserStorage userStorage = dataSnapshot.getValue(UserStorage.class);
                Toast.makeText(getBaseContext(),"connecting ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getBaseContext(),"Error in connecting ",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
