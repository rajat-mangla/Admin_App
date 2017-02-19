package com.example.rajatiit.admin_app.dataclasses;


import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;

/**
 * Created by RajatIIT on 10-02-2017.
 */

@JsonSerialize()
public class Institute {
    private static String name = "Indian Institute Of Technology Jodhpur";
    private static ArrayList<DepartmentDetail> departmentDetails;

    public Institute() {
        if (departmentDetails == null) {
            departmentDetails = new ArrayList<DepartmentDetail>();

            DepartmentDetail departmentDetail1 = new DepartmentDetail("CSE");
            departmentDetails.add(departmentDetail1);

            DepartmentDetail departmentDetail2 = new DepartmentDetail("ME");
            departmentDetails.add(departmentDetail2);

            DepartmentDetail departmentDetail3 = new DepartmentDetail("EE");
            departmentDetails.add(departmentDetail3);

        }
    }

    public static String getName() {
        return name;
    }

    public static ArrayList<DepartmentDetail> getDepartmentDetails() {
        return departmentDetails;
    }

    public static void setDepartmentDetail(DepartmentDetail departmentDetail) {
        Institute.departmentDetails.add(departmentDetail);
    }

    public static DepartmentDetail getDepartmentDetail(int index) {
        return Institute.departmentDetails.get(index);
    }

    public static int getTotalDepartments() {
        return departmentDetails.size();
    }

}
