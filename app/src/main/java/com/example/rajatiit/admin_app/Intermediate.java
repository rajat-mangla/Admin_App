package com.example.rajatiit.admin_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.rajatiit.admin_app.activities.userActivities.adminActivity.AdminMainActivity;
import com.example.rajatiit.admin_app.activities.userActivities.batchActivity.BatchMainActivity;
import com.example.rajatiit.admin_app.dataclasses.Database;
import com.example.rajatiit.admin_app.dataclasses.insti.Institute;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;
import com.example.rajatiit.admin_app.activities.userActivities.teacheractivity.TeacherMainActivity;
import com.example.rajatiit.admin_app.timetablehandler.TimeTable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Intermediate extends AppCompatActivity {
    /*public static BatchDetail batchDetail;
    public static TeacherDetail teacherDetail;*/

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_individual_slot);

        try {
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }
        catch (NullPointerException e){
            Log.e("In Login Activity","No Action Bar Found");
        }

        progressDialog = ProgressDialog.show(this,getResources().getString(R.string.GETTING_DATA),"Please Wait");
        new Thread(new Runnable() {
            @Override
            public void run() {
                getAllUsersData();
                getInstituteData();
                getTimeTable();
            }
        }).start();

    }

    private void nextActivity(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if(sp.getBoolean(Login.BATCH_LOGIN_CHECK, false)){
            Intent intentBatch = new Intent(this, BatchMainActivity.class);
            startActivity(intentBatch);
            finish();
        }
        else if(sp.getBoolean(Login.TEACHER_LOGIN_CHECK, false)){
            Intent intentTeacher = new Intent(this, TeacherMainActivity.class);
            startActivity(intentTeacher);
            finish();
        }
        else {
            Intent intentAdmin = new Intent(this,AdminMainActivity.class);
            startActivity(intentAdmin);
            finish();
        }
    }

    private void getAllUsersData(){
        DatabaseReference reference = Database.database.getReference(UserStorage.USER_STORAGE_REF);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserStorage userStorage = dataSnapshot.getValue(UserStorage.class);
                Toast.makeText(getBaseContext(),"Getting Users Data",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getBaseContext(),"Error in connecting ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getInstituteData(){
        DatabaseReference reference = Database.database.getReference(Institute.INSTITUTE_REF);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Institute institute = dataSnapshot.getValue(Institute.class);
                Toast.makeText(getBaseContext(),"Getting Institute Data",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getBaseContext(),"Error in connecting ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTimeTable(){
        DatabaseReference reference = Database.database.getReference(TimeTable.TIME_TABLE_REF);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TimeTable timeTable = dataSnapshot.getValue(TimeTable.class);
                Toast.makeText(getBaseContext(),"Getting Time Table",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                nextActivity();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
