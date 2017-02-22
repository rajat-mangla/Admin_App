package com.example.rajatiit.admin_app.dataclasses.users;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by RajatIIT on 09-02-2017.
 */

public class TeacherDetail implements Serializable{
    private String firstName;
    private String lastName;
    private String password;
    private String department;
    private String uniqueCode;
    private ArrayList<Integer> classroomIds;
    public TeacherDetail(){
        classroomIds = new ArrayList<>();
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

    public void setDepartment(String department) {
        this.department = department;
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

    public String getDepartment() {
        return department;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public void generateUniqueCode(){
        uniqueCode = firstName+lastName+ department;
    }

    public ArrayList<Integer> getClassroomIds() {
        return classroomIds;
    }

    public void setClassroomIds(ArrayList<Integer> classroomIds) {
        this.classroomIds = classroomIds;
    }
}
