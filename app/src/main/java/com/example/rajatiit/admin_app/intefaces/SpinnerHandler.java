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

public class SpinnerHandler {

    private Institute institute;

    public SpinnerHandler() {
        institute = new Institute();
    }

    public void setDepartmentSpinner(Context context,Spinner departmentSpinner){

        ArrayList<String> departments = new ArrayList<>();

        for (int i = 0; i<institute.getDepartments().size(); i++){
            // Adding Name of the Departments ...
            departments.add(institute.getDepartments().get(i));
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item,departments);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        departmentSpinner.setAdapter(spinnerAdapter);
    }

    public void setYearSpinner(Context context,Spinner yearSpinner){
        ArrayList<String> years = new ArrayList<>();
        years.add("First Year");
        years.add("Second Year");
        years.add("Third Year");
        years.add("Fourth Year");
        ArrayAdapter<String> yearsAdapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item,years);
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearsAdapter);
    }
}
