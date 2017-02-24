package com.example.rajatiit.admin_app.intefaces.classroomInterface;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.Classroom;
import com.example.rajatiit.admin_app.dataclasses.Institute;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;
import com.example.rajatiit.admin_app.intefaces.SpinnerHandler;

/**
 * Created by rajat on 23/2/17.
 */

/*
    **** IMPORTANT  WE HAVE ASSUMED THAT LECTURES SHOULD BE BETWEEN  3 TO 5 IN A WEEK
 */

public class AddEditCourseDialog extends DialogFragment implements AdapterView.OnItemSelectedListener{

    // Flag to check if its a editDialog
    private boolean isEditView;

    // CLASSROOM OBJECT TO SET OR MAKE CHANGES
    private Classroom classroom;

    // REQUEST CODE TO IDENTIFY IF IT'S A ASSIGN TEACHER OR BATCH CALLBACK FOR  onActivityResult  METHOD
    private final Integer ASSIGN_TEACHER_REQUESTCODE = 0;

    // Tag to identify if ASSIGN_TEACHER_BUTTON is clicked
    public static final String ASSIGN_TEACHER = "ASSIGN_TEACHER";

    private Integer numOfLectures;

    View view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.add_edit_course,null);
        builder.setView(view);
        setSpinners();

        if (getFragmentManager().findFragmentByTag(ClassroomInterface.ADD_DIALOG)!=null){
            isEditView = false;
            return addDialogBuilder(builder);
        }
        else {
            isEditView = true;
            return editDialogBuilder(builder);
        }
    }

    // Builder For Add Dialog
    private Dialog addDialogBuilder(AlertDialog.Builder builder){
        builder.setTitle("Add Course")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Nothing here
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // DO Nothing
                    }
                });
        return builder.create();
    }

    // Builder For Edit Dialog
    private Dialog editDialogBuilder(AlertDialog.Builder builder) {
        builder.setTitle("Edit Course")
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing here will overide in onshow() dialog method
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
        classroom = (Classroom) getArguments().getSerializable(ClassroomInterface.CLASSROOM_DATA);
        showDetails();

        return builder.create();
    }

    private void showDetails(){
        TextView courseDepartmentName = (TextView) view.findViewById(R.id.add_edit_course_departmentName);
        EditText courseName = (EditText) view.findViewById(R.id.add_edit_course_courseName);
        TextView teacherAssigned = (TextView) view.findViewById(R.id.add_edit_course_assignTeacher);
        TextView batchAssigned = (TextView) view.findViewById(R.id.add_edit_course_assignBatch);
        Spinner numLecturesSpinner = (Spinner) view.findViewById(R.id.add_edit_course_numLectureSpinner);

        if (isEditView){
            courseDepartmentName.setText(classroom.getCourseDetail().getDepartmentName());
        }
        else {
            courseDepartmentName.setText(getArguments().getCharSequence(ClassroomInterface.DEPARTMENT_NAME));
        }
        courseName.setText(classroom.getCourseDetail().getName());

        // getting the teacher and batch name through id's
        String teacherName = UserStorage.getTeacherDetail(classroom.getTeacherId()).getFirstName();
        teacherName += UserStorage.getTeacherDetail(classroom.getTeacherId()).getLastName();
        String batchName = UserStorage.getBatchDetail(classroom.getBatchId()).getUserName();

        teacherAssigned.setText(teacherName);
        batchAssigned.setText(batchName);
        numLecturesSpinner.setSelection(classroom.getCourseDetail().getNumLectures()-3);
    }

    @Override
    public void onStart() {
        super.onStart();
        assignButtonClicked();

    }

    private void assignButtonClicked(){
        Button assignTeacherButton = (Button) view.findViewById(R.id.course_add_edit_assignTeacher);
        assignTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AssignTeacherOrBatch.class);
                if (isEditView){
                    intent.putExtra(ClassroomInterface.DEPARTMENT_NAME,classroom.getCourseDetail().getDepartmentName());
                }
                else {
                    intent.putExtra(ClassroomInterface.DEPARTMENT_NAME,
                            getArguments().getCharSequence(ClassroomInterface.DEPARTMENT_NAME));
                }
                // to identify assign teacher button clicked
                intent.putExtra(ASSIGN_TEACHER,true);
                startActivityForResult(intent,ASSIGN_TEACHER_REQUESTCODE);
            }
        });

        Button assignBatchButton = (Button) view.findViewById(R.id.add_edit_course_assignBatch);
        assignBatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AssignTeacherOrBatch.class);
                if (isEditView){
                    intent.putExtra(ClassroomInterface.DEPARTMENT_NAME,classroom.getCourseDetail().getDepartmentName());
                }
                else {
                    intent.putExtra(ClassroomInterface.DEPARTMENT_NAME,
                            getArguments().getCharSequence(ClassroomInterface.DEPARTMENT_NAME));
                }
                intent.putExtra(ASSIGN_TEACHER,false);
                startActivityForResult(intent,ASSIGN_TEACHER_REQUESTCODE-1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ASSIGN_TEACHER_REQUESTCODE){
            if (isEditView){
                Integer perviousTeacherId = classroom.getTeacherId();
                Integer newTeacherId = data.getExtras().getInt(AssignTeacherOrBatch.TEACHER_ID);
            }
            else {
                Integer teacherId = data.getExtras().getInt(AssignTeacherOrBatch.TEACHER_ID);
            }
        }
        else {
            if (isEditView){
                Integer perviousBatchId = classroom.getBatchId();
                Integer newBatchId = data.getExtras().getInt(AssignTeacherOrBatch.BATCH_ID);
            }
            else {
                Integer batchId = data.getExtras().getInt(AssignTeacherOrBatch.BATCH_ID);
            }
        }
    }

    /*
            * setting the spinners
        */
    public void setSpinners(){
        Spinner numLecturesSpinner = (Spinner) view.findViewById(R.id.add_edit_course_numLectureSpinner);
        numLecturesSpinner.setOnItemSelectedListener(this);
        SpinnerHandler.setNumLecturesSpinner(getActivity(),numLecturesSpinner);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        numOfLectures = (Integer) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
