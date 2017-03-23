package com.example.rajatiit.admin_app.activities.intefaces.teacherInterface;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.UiAutomation;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.users.TeacherDetail;
import com.example.rajatiit.admin_app.activities.intefaces.SpinnerHandler;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;

/**
 * Created by rajat on 17/2/17.
 */

public class AddEditTeacherDialog extends DialogFragment implements AdapterView.OnItemSelectedListener{

    // FLAG To check if its a Edit Dialog
    private boolean isEditView;

    // Object to show existing teacher Details
    TeacherDetail teacherDetail;

    // view for the dialog
    View view;

    // Department Name For Teacher getting from spinner ...
    String departmentName;


    // Interface For Passing Values to Teacher Interface ...
    public interface TeacherDetailsPasser {
        void passAddDialogDetail(TeacherDetail teacherDetail);
        void passEditDialogDetail(TeacherDetail teacherDetail,String uniqueCode);
    }
    private TeacherDetailsPasser detailsPasser;



    // Method For Creating the Dialog using AlertDialog.Builder
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.add_edit_teacher,null);

        // checkbox to show password
        CheckBox showPassword = (CheckBox) view.findViewById(R.id.add_edit_teacher_showpassword);
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EditText editText = (EditText) view.findViewById(R.id.add_edit_teacher_password);
                if (isChecked){
                    editText.setTransformationMethod(null);
                }
                else {
                    editText.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        if (getFragmentManager().findFragmentByTag(Integer.toString(R.string.ADD_DIALOG))!=null){
            isEditView=false;
            return addDialogBuilder(builder);
        }
        else {
            isEditView=true;
            return editDialogBuilder(builder);
        }
    }


    // Builder For Add Dialog
    private Dialog addDialogBuilder(AlertDialog.Builder builder){

        TextView departmentName = (TextView) view.findViewById(R.id.add_edit_teacher_departmentName);
        departmentName.setVisibility(View.GONE);

        builder.setView(view)
                .setTitle("Add Teacher")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing here will overide in onshow() dialog method
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),"Canceled",Toast.LENGTH_SHORT).show();
                    }
                });

        // Method for Setting the Department Spinner
        setDepartmentSpinner();

        return builder.create();
    }

    // Builder For Edit Dialog
    private Dialog editDialogBuilder(AlertDialog.Builder builder){

        Spinner departmentSpinner = (Spinner) view.findViewById(R.id.add_edit_teacher_SelectDepartment);
        departmentSpinner.setVisibility(View.GONE);

        builder.setView(view)
                .setTitle("Edit Teacher")
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing here will overide in onshow() dialog method
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),"Canceled",Toast.LENGTH_SHORT).show();
                    }
                });

        teacherDetail = (TeacherDetail) getArguments().getSerializable(Integer.toString(R.string.TEACHER_DATA));
        showDetails();

        return builder.create();
    }


    // Method to show existing details in for a teacher In  @Edit Dialog
    private void showDetails(){
        EditText firstNameText = (EditText) view.findViewById(R.id.add_edit_teacher_firstname);
        EditText lastNameText = (EditText) view.findViewById(R.id.add_edit_teacher_lastname);
        TextView departmentName = (TextView) view.findViewById(R.id.add_edit_teacher_departmentName);
        EditText passwordText = (EditText) view.findViewById(R.id.add_edit_teacher_password);
        EditText confirmPasswordText = (EditText) view.findViewById(R.id.add_edit_teacher_confirmPassword);
        firstNameText.setText(teacherDetail.getFirstName());
        lastNameText.setText(teacherDetail.getLastName());
        departmentName.setText(teacherDetail.getDepartmentName());
        passwordText.setText(teacherDetail.getPassword());
        confirmPasswordText.setText(teacherDetail.getPassword());
    }

    /*
      Method Used For Checking Errors When Positive Button is Clicked
      By Using SetOnClick Listener
    */
    @Override
    public void onStart() {
        super.onStart();
        final AlertDialog alertDialog = (AlertDialog) getDialog();

        Button positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isError()){
                    Toast.makeText(getActivity(),"Please Enter Full Details",Toast.LENGTH_SHORT).show();
                }
                else {
                    detailsPasser = (TeacherDetailsPasser) getActivity();
                    if (isEditView){
                        String uniqueIdentifier = teacherDetail.getUniqueCode();
                        // getting details
                        getDetails();
                        teacherDetail.generateUniqueCode();
                        if (!teacherDetail.getUniqueCode().equals(uniqueIdentifier)){
                            if (ifExists()){
                                Toast.makeText(getActivity(),"Teacher Already Exists",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                dismiss();
                                detailsPasser.passEditDialogDetail(teacherDetail, uniqueIdentifier);
                            }
                        }
                        else{
                            dismiss();
                            detailsPasser.passEditDialogDetail(teacherDetail, uniqueIdentifier);
                        }
                    }
                    else {
                        teacherDetail = new TeacherDetail();
                        getDetails();
                        teacherDetail.setDepartmentName(departmentName);
                        teacherDetail.generateUniqueCode();
                        if (ifExists()){
                            Toast.makeText(getActivity(),"Teacher Already Exists",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            dismiss();
                            detailsPasser.passAddDialogDetail(teacherDetail);
                        }
                    }
                }
            }
        });

    }

    // method to check if teacher already exists
    private boolean ifExists(){
        int len = UserStorage.noOfTeachers();
        for (int i=0;i<len;i++){
            if (teacherDetail.getUniqueCode().equals(UserStorage.getTeacherDetail(i).getUniqueCode())){
                return true;
            }
        }
        return false;
    }

    // Handles all the errors Related to adding information
    private boolean isError(){
        EditText firstNameText = (EditText) view.findViewById(R.id.add_edit_teacher_firstname);
        EditText lastNameText = (EditText) view.findViewById(R.id.add_edit_teacher_lastname);
        EditText passwordText = (EditText) view.findViewById(R.id.add_edit_teacher_password);
        if (editTextEmpty(firstNameText) || editTextEmpty(lastNameText) || editTextEmpty(passwordText)){
            // ToDo : Handle Remaining Errors
            return true;
        }
        else {
            return false;
        }
    }

    // METHODD to check empty edit_texts
    private boolean editTextEmpty(EditText editText){
        return editText.getEditableText().toString().equals("");
    }

    // Method to add the Teacher Details TO A Teacher object from a Dialog
    private void getDetails(){
        EditText firstNameText = (EditText) view.findViewById(R.id.add_edit_teacher_firstname);
        EditText lastNameText = (EditText) view.findViewById(R.id.add_edit_teacher_lastname);
        EditText passwordText = (EditText) view.findViewById(R.id.add_edit_teacher_password);

        teacherDetail.setFirstName(firstNameText.getEditableText().toString());
        teacherDetail.setLastName(lastNameText.getEditableText().toString());
        teacherDetail.setPassword(passwordText.getEditableText().toString());
    }

    // Below Are Spinner Related Methods ...
    private void setDepartmentSpinner(){
        Spinner departmentSpinner = (Spinner) view.findViewById(R.id.add_edit_teacher_SelectDepartment);
        departmentSpinner.setOnItemSelectedListener(this);
        SpinnerHandler departmentSpiner = new SpinnerHandler();
        departmentSpiner.setDepartmentSpinner(getActivity(),departmentSpinner);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        departmentName = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        departmentName = "false";
    }
}


