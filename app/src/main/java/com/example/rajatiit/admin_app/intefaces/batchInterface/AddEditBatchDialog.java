package com.example.rajatiit.admin_app.intefaces.batchInterface;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.users.BatchDetail;
import com.example.rajatiit.admin_app.dataclasses.users.TeacherDetail;
import com.example.rajatiit.admin_app.intefaces.SpinnerHandler;
import com.example.rajatiit.admin_app.intefaces.teacherInterface.AddEditTeacherDialog;

/**
 * Created by rajat on 22/2/17.
 */

public class AddEditBatchDialog extends DialogFragment implements AdapterView.OnItemSelectedListener{

    // Flag to check if its a editDialog
    private boolean isEditView;

    // view to display
    private View view;

    // getting the department name
    private String departmentName;

    // getting the batch year
    private String batchYear;


    // Interface For Passing Values to Batch Interface ...
    public interface BatchDetailsPasser {
        void passAddDialogDetail(BatchDetail batchDetail);
        void passEditDialogDetail(BatchDetail batchDetail);
    }
    private BatchDetailsPasser batchDetailsPasser;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        batchDetailsPasser = (BatchDetailsPasser) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_batch_add_edit,null);
        return addDialog(builder);
    }

    // method to create ADD_DIALOG BUILDER
    private Dialog addDialog(AlertDialog.Builder builder){
        builder.setView(view)
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // override in show method
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // nothing to do in this
                    }
                });

        // setting the spinners for the add dialog
        setSpinners();
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        AlertDialog dialog = (AlertDialog) getDialog();
        Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (handleErrors()){
                    dismiss();
                    batchDetailsPasser.passAddDialogDetail(getDetails());
                }
            }
        });
    }

    // method to handle all the errors ...
    private boolean handleErrors(){
        EditText password = (EditText) view.findViewById(R.id.password);
        EditText confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        EditText numStudents = (EditText) view.findViewById(R.id.batchCapacity);
        if (editTextEmpty(password) || editTextEmpty(confirmPassword) || editTextEmpty(numStudents)){
            Toast.makeText(getActivity(),"Please Enter Full Details",Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            if (password.getText().toString().equals(confirmPassword.getText().toString())){
                return true;
            }
            else {
                Toast.makeText(getActivity(),"Password and Confirm Password doesn't match",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    // METHOD to check empty edit_texts
    private boolean editTextEmpty(EditText editText){
        return editText.getText().toString().equals("");
    }

    // getting all the details from the dialog front end ...
    private BatchDetail getDetails(){
        EditText password = (EditText) view.findViewById(R.id.password);
        EditText numStudents = (EditText) view.findViewById(R.id.batchCapacity);
        int totalStudents = Integer.parseInt(numStudents.getText().toString());
        return new BatchDetail(batchYear,departmentName,password.getEditableText().toString(),totalStudents);
    }


    // Method for setting up the spinners ....
    public void setSpinners(){
        Spinner departmentSpinner = (Spinner) view.findViewById(R.id.departments);
        Spinner yearSpinner = (Spinner) view.findViewById(R.id.selectYear);

        departmentSpinner.setOnItemSelectedListener(this);
        yearSpinner.setOnItemSelectedListener(this);

        SpinnerHandler spinnerHandler = new SpinnerHandler();
        spinnerHandler.setDepartmentSpinner(getActivity(),departmentSpinner);
        spinnerHandler.setYearSpinner(getActivity(),yearSpinner);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if (spinner.getId()==R.id.departments){
            departmentName = (String)parent.getItemAtPosition(position);
        }
        else {
            batchYear = (String)parent.getItemAtPosition(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
