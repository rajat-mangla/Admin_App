package com.example.rajatiit.admin_app.intefaces;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.rajatiit.admin_app.dataclasses.Institute;

import java.util.ArrayList;

/**
 * Created by rajat on 17/2/17.
 */

public class DepartmentSpinner {

    private Institute institute;
    private ArrayList<String> departments;
    private Spinner departmentSpinner;
    private ArrayAdapter<String> spinnerAdapter;



    public DepartmentSpinner(Spinner spinner) {
        institute = new Institute();
        departmentSpinner = spinner;
    }

    public void setSpinner(Context context){

        departments = new ArrayList<String>();

        for (int i=0;i<institute.getDepartmentDetails().size();i++){
            // Adding Name of the Departments ...
            departments.add(institute.getDepartmentDetail(i).getName());
        }

        spinnerAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,departments);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentSpinner.setAdapter(spinnerAdapter);
    }

    public void showDefaultDepartment(String departmentName){
        int position = spinnerAdapter.getPosition(departmentName);
        Log.i("position",Integer.toString(position));
        if (position!=-1){
            departmentSpinner.setSelection(position);
        }
    }
}
