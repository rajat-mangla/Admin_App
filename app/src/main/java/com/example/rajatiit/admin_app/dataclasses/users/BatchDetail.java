package com.example.rajatiit.admin_app.dataclasses.users;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rajat on 16/2/17.
 */

public class BatchDetail implements Serializable{
    private int batchId;
    private String userName;
    private String year;
    private String departmentName;
    private String password;
    private int numStudents;
    private ArrayList<Integer> classroomIds;

    public BatchDetail(){
        classroomIds = new ArrayList<>();
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getNumStudents() {
        return numStudents;
    }

    public void setNumStudents(int numStudents) {
        this.numStudents = numStudents;
    }

    public ArrayList<Integer> getClassroomIds() {
        return classroomIds;
    }

    public int totalNoOfClassrooms(){
        return classroomIds.size();
    }

}
