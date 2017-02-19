package com.example.rajatiit.admin_app.dataclasses.users;

/**
 * Created by rajat on 16/2/17.
 */

public class BatchDetail {
    private String userName;
    private int year;
    private String department;
    private String password;

    public BatchDetail(){

    }
    public BatchDetail(int year,String department,String password){
        setDepartment(department);
        setYear(year);
        setPassword(password);
        userName = department + Integer.toString(year);
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
