package com.example.rajatiit.admin_app.dataclasses.users;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by RajatIIT on 09-02-2017.
 */

public class TeacherDetail implements Serializable{
    private int teacherId;
    private String firstName;
    private String lastName;
    private String password;
    private String departmentName;
    private String uniqueCode;
    private ArrayList<Integer> classroomIds;

    public TeacherDetail(){
        classroomIds = new ArrayList<>();
        teacherId = -1;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
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

    public void generateUniqueCode(){
        uniqueCode = firstName+lastName+departmentName;
    }

    public ArrayList<Integer> getClassroomIds() {
        return classroomIds;
    }

}
