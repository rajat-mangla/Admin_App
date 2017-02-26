package com.example.rajatiit.admin_app.dataclasses;


import com.example.rajatiit.admin_app.intefaces.classroomInterface.AddEditCourseDialog;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;

/**
 * Created by RajatIIT on 10-02-2017.
 */


public class Institute {
    @Exclude
    public static String INSTITUTE_REF = "Institute";

    @Exclude
    private static String name = "Indian Institute Of Technology Jodhpur";

    @Exclude
    private static ArrayList<String> departments;

    private static ArrayList<Classroom> classrooms;
    private static ArrayList<RoomDetail> roomDetails;

    public static void setRoomDetail(RoomDetail roomDetail){
        roomDetails.add(roomDetail);
    }

    public static int totalNoOfClassrooms(){
        return classrooms.size();
    }

    public Institute() {
        if (departments == null) {
            departments = new ArrayList<>();
            departments.add("CSE");
            departments.add("ME");
            departments.add("EE");
        }
        if (classrooms==null){
            classrooms = new ArrayList<>();
        }
        if (roomDetails == null){
            roomDetails = new ArrayList<>();
        }
    }

    public ArrayList<String> getDepartments() {
        return departments;
    }

    /*public void setDepartments(ArrayList<String> departments) {
        Institute.departments = departments;
    }*/

    public ArrayList<Classroom> getClassrooms() {
        return classrooms;
    }

    /*public void setClassrooms(ArrayList<Classroom> classrooms) {
        Institute.classrooms = classrooms;
    }*/

    public ArrayList<RoomDetail> getRoomDetails() {
        return roomDetails;
    }
}
