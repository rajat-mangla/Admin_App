package com.example.rajatiit.admin_app.intefaces;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.Institute;

import java.util.ArrayList;

/**
 * Created by rajat on 17/2/17.
 */

public class SpinnerHandler {

    public SpinnerHandler() {
    }

    public static void setDepartmentSpinner(Context context,Spinner departmentSpinner){

        Institute institute = new Institute();
        // setting the aaray adapter for spinner ...
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item,institute.getDepartments());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        departmentSpinner.setAdapter(spinnerAdapter);
    }

    public static void setYearSpinner(Context context,Spinner yearSpinner){
        ArrayList<String> years = new ArrayList<>();
        years.add(Integer.toString(R.string.First_Year));
        years.add(Integer.toString(R.string.Second_Year));
        years.add(Integer.toString(R.string.Third_Year));
        years.add(Integer.toString(R.string.Fourth_Year));
        ArrayAdapter<String> yearsAdapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item,years);
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearsAdapter);
    }

    public static void setNumLecturesSpinner(Context context, Spinner numLecturesSpinner){
        ArrayList<Integer> numLectures = new ArrayList<>();
        // adding the num of lectures ...
        numLectures.add(3);
        numLectures.add(4);
        numLectures.add(5);
        ArrayAdapter<Integer>numLecturesAdapter=new ArrayAdapter<>(context,android.R.layout.simple_spinner_item,numLectures);
        numLecturesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numLecturesSpinner.setAdapter(numLecturesAdapter);
    }

    public static void setRoomCapacitySpinner(Context context, Spinner roomCapacitySpinner){
        ArrayList<Integer> roomCapacity = new ArrayList<>();
        // adding the num of lectures ...
        roomCapacity.add(40);
        roomCapacity.add(80);
        roomCapacity.add(120);
        ArrayAdapter<Integer>roomCapacityAdapter=new ArrayAdapter<>(context,android.R.layout.simple_spinner_item,roomCapacity);
        roomCapacityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomCapacitySpinner.setAdapter(roomCapacityAdapter);
    }

}
