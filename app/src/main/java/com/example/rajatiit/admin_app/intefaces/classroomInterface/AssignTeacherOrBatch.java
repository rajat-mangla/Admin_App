package com.example.rajatiit.admin_app.intefaces.classroomInterface;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.users.BatchDetail;
import com.example.rajatiit.admin_app.dataclasses.users.TeacherDetail;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;
import com.example.rajatiit.admin_app.intefaces.batchInterface.CustomBatchListAdapter;
import com.example.rajatiit.admin_app.intefaces.teacherInterface.CustomTeacherListAdapter;

import java.util.ArrayList;

public class AssignTeacherOrBatch extends AppCompatActivity {

    // Tag to pass and retrieve TEACHER ID
    public static final String TEACHER_ID = "TEACHER_ID";

    // Tag to pass and retrieve BATCH ID
    public static final String BATCH_ID = "BATCH_ID";

    // to identify if assign teacher clicked or batch
    private boolean isAssignTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data_interface);

        ListView listView = (ListView) findViewById(R.id.displayDataList);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.addDetail);
        floatingActionButton.setVisibility(View.GONE);

        isAssignTeacher = getIntent().getExtras().getBoolean(AddEditCourseDialog.ASSIGN_TEACHER);
        String departmentName = getIntent().getExtras().getString(ClassroomInterface.DEPARTMENT_NAME);

        if (isAssignTeacher){
            ArrayList<TeacherDetail> teacherDetails = new ArrayList<>();
            Integer totalTeachers = UserStorage.noOfTeachers();
            for (int i=0;i<totalTeachers;i++){
                if (departmentName.equals(UserStorage.getTeacherDetail(i).getDepartmentName())){
                    teacherDetails.add(UserStorage.getTeacherDetail(i));
                }
            }
            CustomTeacherListAdapter customTeacherListAdapter = new CustomTeacherListAdapter(getBaseContext(),
                    R.layout.activity_display_data_interface,teacherDetails);
            listView.setAdapter(customTeacherListAdapter);
        }
        else {
            ArrayList<BatchDetail> batchDetails = new ArrayList<>();
            Integer totalBatches = UserStorage.noOfBatches();
            for (int i=0;i<totalBatches;i++){
                if (departmentName.equals(UserStorage.getBatchDetail(i).getDepartmentName())){
                    batchDetails.add(UserStorage.getBatchDetail(i));
                }
            }
            CustomBatchListAdapter customBatchListAdapter = new CustomBatchListAdapter(getBaseContext(),
                    R.layout.activity_display_data_interface,batchDetails);
            listView.setAdapter(customBatchListAdapter);
        }
    }

    private void onClick(ListView listView){
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : PASS DETAILS BACK TO DIALOG .
            }
        });
    }
}
