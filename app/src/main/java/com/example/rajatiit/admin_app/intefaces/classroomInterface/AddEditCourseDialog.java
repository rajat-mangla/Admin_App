package com.example.rajatiit.admin_app.intefaces.classroomInterface;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;

/**
 * Created by rajat on 23/2/17.
 */

/*
    **** IMPORTANT  WE HAVE ASSUMED THAT LECTURES SHOULD BE BETWEEN  3 TO 5 IN A WEEK
 */

public class AddEditCourseDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {

    // Flag to check if its a editDialog
    private boolean isEditView;

    // CLASSROOM OBJECT TO SET OR MAKE CHANGES
    private Classroom classroom;

    // REQUEST CODE TO IDENTIFY IF IT'S A ASSIGN TEACHER OR BATCH CALLBACK FOR  onActivityResult  METHOD
    private final Integer ASSIGN_TEACHER_REQUESTCODE = 2;

    // Tag to identify if ASSIGN_TEACHER_BUTTON is clicked
    public static final String ASSIGN_TEACHER = "ASSIGN_TEACHER";

    // To get the no. of lectures ...
    private Integer numOfLectures;

    // to store in classroom class ...
    private Integer teacherId;
    private Integer batchId;

    private View view;


    public interface classroomDetailspasser {
        void passAddDialogDetails(Classroom classroom);

        void passEditDialogDetails(Classroom classroom);
    }
    public classroomDetailspasser detailsPasser;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.add_edit_course, null);
        builder.setView(view);
        setSpinners();

        if (getFragmentManager().findFragmentByTag(ClassroomInterface.ADD_DIALOG) != null) {
            isEditView = false;
            return addDialogBuilder(builder);
        } else {
            isEditView = true;
            return editDialogBuilder(builder);
        }
    }

    // Builder For Add Dialog
    private Dialog addDialogBuilder(AlertDialog.Builder builder) {
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
        showDetails();
        teacherId = -1;
        batchId = -1;
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
        teacherId = classroom.getTeacherId();
        batchId = classroom.getBatchId();
        showDetails();
        return builder.create();
    }

    private void showDetails() {
        TextView courseDepartmentName = (TextView) view.findViewById(R.id.add_edit_course_departmentName);
        if (isEditView) {
            EditText courseName = (EditText) view.findViewById(R.id.add_edit_course_courseName);
            Spinner numLecturesSpinner = (Spinner) view.findViewById(R.id.add_edit_course_numLectureSpinner);

            courseDepartmentName.setText(classroom.getCourseDetail().getDepartmentName());
            courseName.setText(classroom.getCourseDetail().getName());
            showTeacherName(teacherId);
            showBatchName(batchId);
            numLecturesSpinner.setSelection(classroom.getCourseDetail().getNumLectures() - 3);
        } else {
            courseDepartmentName.setText(getArguments().getCharSequence(ClassroomInterface.DEPARTMENT_NAME));
        }
    }
    private void showTeacherName(int teacherId) {
        TextView teacherAssigned = (TextView) view.findViewById(R.id.add_edit_course_showTeacher);
        String teacherName = UserStorage.getTeacherDetail(teacherId).getFirstName();
        teacherName += UserStorage.getTeacherDetail(teacherId).getLastName();
        teacherAssigned.setText(teacherName);
    }

    private void showBatchName(int batchId) {
        TextView batchAssigned = (TextView) view.findViewById(R.id.add_edit_course_showBatch);
        String batchName = UserStorage.getBatchDetail(batchId).getUserName();
        batchAssigned.setText(batchName);
    }

    @Override
    public void onStart() {
        super.onStart();
        // methods when assignButtons are Clicked ....
        assignButtonClicked();

        AlertDialog dialog = (AlertDialog) getDialog();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (areErrorsHandled()) {
                    detailsPasser = (classroomDetailspasser) getActivity();
                    getEnteredDetails();
                    if (isEditView){
                        detailsPasser.passEditDialogDetails(classroom);
                    }else {
                        detailsPasser.passAddDialogDetails(classroom);
                    }
                    dismiss();
                }
            }
        });
    }

    private void assignButtonClicked() {
        Button assignTeacherButton = (Button) view.findViewById(R.id.add_edit_course_assignTeacher);

        assignTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AssignTeacherOrBatch.class);
                if (isEditView) {
                    intent.putExtra(ClassroomInterface.DEPARTMENT_NAME, classroom.getCourseDetail().getDepartmentName());
                } else {
                    intent.putExtra(ClassroomInterface.DEPARTMENT_NAME,
                            getArguments().getCharSequence(ClassroomInterface.DEPARTMENT_NAME));
                }
                // to identify assign teacher button clicked
                intent.putExtra(ASSIGN_TEACHER, true);
                startActivityForResult(intent, ASSIGN_TEACHER_REQUESTCODE);
            }
        });

        Button assignBatchButton = (Button) view.findViewById(R.id.add_edit_course_assignBatch);

        assignBatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AssignTeacherOrBatch.class);
                if (isEditView) {
                    intent.putExtra(ClassroomInterface.DEPARTMENT_NAME, classroom.getCourseDetail().getDepartmentName());
                } else {
                    intent.putExtra(ClassroomInterface.DEPARTMENT_NAME,
                            getArguments().getCharSequence(ClassroomInterface.DEPARTMENT_NAME));
                }
                intent.putExtra(ASSIGN_TEACHER, false);
                startActivityForResult(intent, ASSIGN_TEACHER_REQUESTCODE-2);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // Assign teacher button clicked
            Log.i("checking the app : ","the result code" + Integer.toString(requestCode));
            if (requestCode == ASSIGN_TEACHER_REQUESTCODE) {
                teacherId = data.getExtras().getInt(AssignTeacherOrBatch.TEACHER_ID);
                showTeacherName(teacherId);
            }
            // Assign Batch button clicked
            else {
                batchId = data.getExtras().getInt(AssignTeacherOrBatch.BATCH_ID);
                Log.i("checking the app : ","the batch_id" + Integer.toString(batchId));
                showBatchName(batchId);
            }
        }
    }

    // checks if all the errors are Handled ....
    private boolean areErrorsHandled() {
        EditText courseNameText = (EditText) view.findViewById(R.id.add_edit_course_courseName);
        if (teacherId == -1 || batchId == -1 || courseNameText.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Enter Full Details", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // getting the entered details ..
    private void getEnteredDetails() {
        TextView courseDepartmentName = (TextView) view.findViewById(R.id.add_edit_course_departmentName);
        EditText courseName = (EditText) view.findViewById(R.id.add_edit_course_courseName);
        if (isEditView) {
            handleIds();
        } else {
            classroom = new Classroom();
        }
        classroom.getCourseDetail().setDepartmentName(courseDepartmentName.getText().toString());
        classroom.getCourseDetail().setName(courseName.getText().toString());
        classroom.getCourseDetail().setNumLectures(numOfLectures);
        classroom.setTeacherId(teacherId);
        classroom.setBatchId(batchId);
    }

    private void handleIds() {
        {
            ArrayList<Integer> classroomIds = UserStorage.getTeacherDetail(classroom.getTeacherId()).getClassroomIds();
            for (int i = 0; i < classroomIds.size(); i++) {
                if (classroom.getClassroomId() == classroomIds.get(i)) {
                    UserStorage.getTeacherDetail(classroom.getTeacherId()).getClassroomIds().remove(i);
                }
            }
        }
        {
            ArrayList<Integer> classroomIds = UserStorage.getBatchDetail(classroom.getBatchId()).getClassroomIds();
            for (int i = 0; i < classroomIds.size(); i++) {
                if (classroom.getClassroomId() == classroomIds.get(i)) {
                    UserStorage.getBatchDetail(classroom.getBatchId()).getClassroomIds().remove(i);
                }
            }
        }
    }

    /*
      * setting the spinners
     */
    public void setSpinners() {
        Spinner numLecturesSpinner = (Spinner) view.findViewById(R.id.add_edit_course_numLectureSpinner);
        numLecturesSpinner.setOnItemSelectedListener(this);
        SpinnerHandler.setNumLecturesSpinner(getActivity(), numLecturesSpinner);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        numOfLectures = (Integer) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
