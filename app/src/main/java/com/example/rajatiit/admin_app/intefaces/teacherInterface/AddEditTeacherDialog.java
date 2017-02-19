package com.example.rajatiit.admin_app.intefaces.teacherInterface;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.users.TeacherDetail;
import com.example.rajatiit.admin_app.intefaces.DepartmentSpinner;

/**
 * Created by rajat on 17/2/17.
 */

public class AddEditTeacherDialog extends DialogFragment implements AdapterView.OnItemSelectedListener{


    // fragment tag for finding if its a ADD DIALOG
    private final String ADD_DIALOG = "Add_Dialog";

    // tag for finding arguements when its a EDIT DIALOG
    private final String TEACHER_DATA = "TeacherDetails";

    // FLAG To check if its a Edit Dialog
    private boolean isEditView;

    // Object to show existing teacher Details
    TeacherDetail teacherDetail;

    // view for the dialog
    View view;

    // Department Name For Teacher getting from spinner ...
    String departmentName;

    /*
        Interface For Passing Values to Teacher Interface ...
    */
    public interface TeacherDetailsPasser {
        public void passAddDialogDetail(TeacherDetail teacherDetail);
        public void passEditDialogDetail(TeacherDetail teacherDetail,String uniqueCode);
    }
    private TeacherDetailsPasser detailsPasser;


    /*
    Method For Creating the Dialog using AlertDialog.Builder
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.layout_teacher_add_edit,null);

        if (getFragmentManager().findFragmentByTag(ADD_DIALOG)!=null){
            isEditView=false;
            return addDialogBuilder(builder);
        }
        else {
            isEditView=true;
            return editDialogBuilder(builder);
        }
    }


    /*
     Builder For Add Dialog
     */
    private Dialog addDialogBuilder(AlertDialog.Builder builder){
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


    /*
    Builder For Edit Dialog
     */
    private Dialog editDialogBuilder(AlertDialog.Builder builder){
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

        teacherDetail = (TeacherDetail) getArguments().getSerializable(TEACHER_DATA);
        showDetails();
        // Method for Setting the Department Spinner
        setDepartmentSpinner();
        return builder.create();
    }

    /*
      Method to show existing details in for a teacher In Edit Dialog
    */
    private void showDetails(){
        EditText firstNameText = (EditText) view.findViewById(R.id.firstname);
        EditText lastNameText = (EditText) view.findViewById(R.id.lastname);
        EditText passwordText = (EditText) view.findViewById(R.id.teacherPassword);
        EditText confirmPasswordText = (EditText) view.findViewById(R.id.teacherConfirmPassword);
        firstNameText.setText(teacherDetail.getFirstName());
        lastNameText.setText(teacherDetail.getLastName());
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
                        getDetails();
                        dismiss();
                        detailsPasser.passEditDialogDetail(teacherDetail,uniqueIdentifier);
                    }
                    else {
                        teacherDetail = new TeacherDetail();
                        getDetails();
                        dismiss();
                        detailsPasser.passAddDialogDetail(teacherDetail);
                    }
                }
            }
        });

    }

    /*
      Handles all the errors Related to adding information
    */
    private boolean isError(){
        EditText firstNameText = (EditText) view.findViewById(R.id.firstname);
        EditText lastNameText = (EditText) view.findViewById(R.id.lastname);
        EditText passwordText = (EditText) view.findViewById(R.id.teacherPassword);
        if (editTextEmpty(firstNameText) || editTextEmpty(lastNameText) || editTextEmpty(passwordText)){
            // ToDo : Handle Remaining Errors
            return true;
        }
        else {
            return false;
        }
    }
    private boolean editTextEmpty(EditText editText){
        return editText.getText().toString().equals("");
    }


    /*
     Method to add the Teacher Details TO A Teacher object from a Dialog
     */
    private void getDetails(){
        EditText firstNameText = (EditText) view.findViewById(R.id.firstname);
        EditText lastNameText = (EditText) view.findViewById(R.id.lastname);
        EditText passwordText = (EditText) view.findViewById(R.id.teacherPassword);

        teacherDetail.setFirstName(firstNameText.getEditableText().toString());
        teacherDetail.setLastName(lastNameText.getEditableText().toString());
        teacherDetail.setPassword(passwordText.getEditableText().toString());
        teacherDetail.setDepartmentName(departmentName);
        teacherDetail.generateUniqueCode();
    }

    /*
    Below Are Spinner Related Methods ...
     */

    private void setDepartmentSpinner(){
        Spinner departmentSpinner = (Spinner) view.findViewById(R.id.departments);
        departmentSpinner.setOnItemSelectedListener(this);
        DepartmentSpinner departmentSpiner = new DepartmentSpinner(departmentSpinner);
        departmentSpiner.setSpinner(getActivity());

        if (isEditView){
            departmentSpiner.showDefaultDepartment(teacherDetail.getDepartmentName());
        }
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

