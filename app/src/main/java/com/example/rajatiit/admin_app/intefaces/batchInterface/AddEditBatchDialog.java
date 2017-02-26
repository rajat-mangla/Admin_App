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
import com.example.rajatiit.admin_app.intefaces.SpinnerHandler;

/**
 * Created by rajat on 22/2/17.
 */

public class AddEditBatchDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {

    // Flag to check if its a editDialog
    private boolean isEditView;

    // view to display
    private View view;

    // getting the department name
    private String departmentName;

    // getting the batch year
    private String batchYear;

    // For passing and getting details of a particular ...
    private BatchDetail batchDetail;

    // Interface For Passing Values to Batch Interface ...
    public interface BatchDetailsPasser {
        void passAddDialogDetail(BatchDetail batchDetail);

        void passEditDialogDetail();
    }

    private BatchDetailsPasser batchDetailsPasser;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.add_edit_batch, null);

        if (getFragmentManager().findFragmentByTag(BatchInterface.ADD_DIALOG)!=null){
            isEditView=false;
            return addDialogBuilder(builder);
        }
        else {
            isEditView=true;
            return editDialogBuilder(builder);
        }
    }

    // method to create ADD_DIALOG BUILDER
    private Dialog addDialogBuilder(AlertDialog.Builder builder) {
        TextView textView = (TextView) view.findViewById(R.id.add_edit_batch_batchYear);
        textView.setVisibility(View.GONE);
        textView = (TextView) view.findViewById(R.id.add_edit_batch_departmentName);
        textView.setVisibility(View.GONE);

        builder.setView(view)
                .setTitle("Add Batch")
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

    // Builder For Edit Dialog
    private Dialog editDialogBuilder(AlertDialog.Builder builder) {
        Spinner spinner = (Spinner) view.findViewById(R.id.add_edit_batch_selectYear);
        spinner.setVisibility(View.GONE);
        spinner = (Spinner) view.findViewById(R.id.add_edit_batch_selectDepartment);
        spinner.setVisibility(View.GONE);

        builder.setView(view)
                .setTitle("Edit Batch")
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
        batchDetail = (BatchDetail) getArguments().getSerializable(BatchInterface.BATCH_DATA);
        showDetails();

        return builder.create();
    }

    // Method to show existing details in for a teacher In  @Edit Dialog
    private void showDetails(){
        TextView batchYear = (TextView) view.findViewById(R.id.add_edit_batch_batchYear);
        TextView departmentName = (TextView) view.findViewById(R.id.add_edit_batch_departmentName);
        EditText numStudents = (EditText) view.findViewById(R.id.add_edit_batch_batchCapacity);
        EditText passwordText = (EditText) view.findViewById(R.id.add_edit_batch_password);
        EditText confirmPasswordText = (EditText) view.findViewById(R.id.add_edit_batch_confirmPassword);
        // showing the details ...
        batchYear.setText(batchDetail.getYear());
        departmentName.setText(batchDetail.getDepartmentName());
        numStudents.setText(Integer.toString(batchDetail.getNumStudents()));
        passwordText.setText(batchDetail.getPassword());
        confirmPasswordText.setText(batchDetail.getPassword());
    }

    @Override
    public void onStart() {
        super.onStart();
        AlertDialog dialog = (AlertDialog) getDialog();
        Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (handleErrors()) {
                    batchDetailsPasser = (BatchDetailsPasser) getActivity();
                    if (isEditView){
                        getDetails();
                        dismiss();
                        batchDetailsPasser.passEditDialogDetail();
                    }
                    else {
                        // storing the details entered by User for add dialog
                        batchDetail = new BatchDetail();
                        batchDetail.setDepartmentName(departmentName);
                        batchDetail.setYear(batchYear);
                        batchDetail.setUserName(batchYear+" "+departmentName);
                        getDetails();
                        dismiss();
                        batchDetailsPasser.passAddDialogDetail(batchDetail);
                    }
                }
            }
        });
    }

    // method to handle all the errors ...
    private boolean handleErrors() {
        EditText password = (EditText) view.findViewById(R.id.add_edit_batch_password);
        EditText confirmPassword = (EditText) view.findViewById(R.id.add_edit_batch_confirmPassword);
        EditText numStudents = (EditText) view.findViewById(R.id.add_edit_batch_batchCapacity);
        if (editTextEmpty(password) || editTextEmpty(confirmPassword) || editTextEmpty(numStudents)) {
            Toast.makeText(getActivity(), "Please Enter Full Details", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                return true;
            } else {
                Toast.makeText(getActivity(), "Password and Confirm Password doesn't match", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    // METHOD to check empty edit_texts
    private boolean editTextEmpty(EditText editText) {
        return editText.getText().toString().equals("");
    }

    // GETTING all the details from the dialog front end ...
    private void getDetails() {
        EditText password = (EditText) view.findViewById(R.id.add_edit_batch_password);
        EditText numStudents = (EditText) view.findViewById(R.id.add_edit_batch_batchCapacity);
        int totalStudents = Integer.parseInt(numStudents.getText().toString());
        batchDetail.setPassword(password.getEditableText().toString());
        batchDetail.setNumStudents(totalStudents);
    }


    // Method for setting up the spinners ....
    public void setSpinners() {
        Spinner departmentSpinner = (Spinner) view.findViewById(R.id.add_edit_batch_selectDepartment);
        Spinner yearSpinner = (Spinner) view.findViewById(R.id.add_edit_batch_selectYear);

        departmentSpinner.setOnItemSelectedListener(this);
        yearSpinner.setOnItemSelectedListener(this);

        SpinnerHandler.setDepartmentSpinner(getActivity(), departmentSpinner);
        SpinnerHandler.setYearSpinner(getActivity(), yearSpinner);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.add_edit_batch_selectDepartment) {
            departmentName = (String) parent.getItemAtPosition(position);
        } else {
            batchYear = (String) parent.getItemAtPosition(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
