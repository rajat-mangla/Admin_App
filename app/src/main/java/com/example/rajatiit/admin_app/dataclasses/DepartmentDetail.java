package com.example.rajatiit.admin_app.dataclasses;

import java.util.ArrayList;

/**
 * Created by RajatIIT on 10-02-2017.
 */


public class DepartmentDetail {
    private ArrayList<TeacherDetail> dataArray;
    private final String name;

    public String getName() {
        return name;
    }

    DepartmentDetail(String name){
        if (dataArray == null){
            dataArray = new ArrayList<TeacherDetail>();
            TeacherDetail teacherDetail = new TeacherDetail();
            teacherDetail.setFirstName("Madarchod");
            dataArray.add(teacherDetail);


        }
        this.name = name;
    }
    public void addDetail(TeacherDetail teacherDetail){
        dataArray.add(teacherDetail);
    }

    public ArrayList<TeacherDetail> getDataArray() {
        return dataArray;
    }

    public void removeDetail(int index){
        dataArray.remove(index);
    }

    public TeacherDetail getTeacherDetail(int index){
        return dataArray.get(index);
    }

    public int noOfTeachers(){
        return dataArray.size();
    }
}
