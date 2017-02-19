package com.example.rajatiit.admin_app.dataclasses.users;

import java.io.Serializable;

/**
 * Created by RajatIIT on 09-02-2017.
 */

public class TeacherDetail implements Serializable{
    private String firstName;
    private String lastName;
    private String password;
    private String departmentName;
    private String uniqueCode;
    public TeacherDetail(){

    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPassword() {

        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public void generateUniqueCode(){
        uniqueCode = firstName+lastName+departmentName+password;
    }
}