package com.example.rajatiit.admin_app.dataclasses.users;

/**
 * Created by rajat on 16/2/17.
 */

public class BatchDetail {
    private String userName;
    private String year;
    private String departmentName;
    private String password;
    private int numStudents;

    public BatchDetail(){

    }

    public BatchDetail(String year, String departmentName, String password, int numStudents) {
        this.year = year;
        this.departmentName = departmentName;
        this.password = password;
        this.numStudents = numStudents;
        userName = departmentName +year+ Integer.toString(numStudents);
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
}
