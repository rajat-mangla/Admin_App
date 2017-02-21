package com.example.rajatiit.admin_app.dataclasses;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;

/**
 * Created by RajatIIT on 10-02-2017.
 */

@JsonSerialize()
public class Institute {
    private static String name = "Indian Institute Of Technology Jodhpur";
    private static ArrayList<String> departments;

    public Institute() {
        if (departments == null) {
            departments = new ArrayList<>();
            departments.add("CSE");
            departments.add("ME");
            departments.add("EE");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Institute.name = name;
    }

    public ArrayList<String> getDepartments() {
        return departments;
    }

    public void setDepartments(ArrayList<String> departments) {
        Institute.departments = departments;
    }
}
