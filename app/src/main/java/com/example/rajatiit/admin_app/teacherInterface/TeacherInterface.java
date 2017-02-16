package com.example.rajatiit.admin_app.teacherInterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.example.rajatiit.admin_app.R;

public class TeacherInterface extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_interface);

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.teacherList);
        CustomTeacherInterfaceListAdapter customTeacherInterfaceListAdapter = new CustomTeacherInterfaceListAdapter(this);
        expandableListView.setAdapter(customTeacherInterfaceListAdapter);


    }
}
