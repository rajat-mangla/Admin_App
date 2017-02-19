package com.example.rajatiit.admin_app.dataclasses;

import com.example.rajatiit.admin_app.dataclasses.users.TeacherDetail;

import java.util.ArrayList;

/**
 * Created by RajatIIT on 10-02-2017.
 */


public class DepartmentDetail {
    private String name;

    public DepartmentDetail() {}

    DepartmentDetail(String name){
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
