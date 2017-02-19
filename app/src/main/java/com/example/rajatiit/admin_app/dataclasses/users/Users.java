package com.example.rajatiit.admin_app.dataclasses.users;

import android.app.AlertDialog;

import java.util.ArrayList;

/**
 * Created by rajat on 19/2/17.
 */

public class Users {
    private static ArrayList<TeacherDetail> teacherDetails;
    private static ArrayList<BatchDetail> batchDetails;

    public Users() {
        if (teacherDetails == null){
            teacherDetails = new ArrayList<>();
        }
        if (batchDetails == null){
            batchDetails = new ArrayList<>();
        }
    }

    public static boolean addTeacherDetail(TeacherDetail teacherDetail){
        teacherDetails.add(teacherDetail);

        // TODO : UPDATE IN DATABASE ALSO
        return true;
    }

    public static TeacherDetail getTeacherDetail(int index){
        return teacherDetails.get(index);
    }

    public static int noOfTeachers(){
        return teacherDetails.size();
    }

    public static boolean addBatchDetail(BatchDetail batchDetail){
        batchDetails.add(batchDetail);

        // TODO : UPDATE IN DATABASE ALSO
        return true;
    }

    public static BatchDetail getBatchDetail(int index){
        return batchDetails.get(index);
    }

    public static int noOfBatches(){
        return batchDetails.size();
    }


    /*
    Getter and Setter Methods ....
     */
    public static ArrayList<TeacherDetail> getTeacherDetails() {
        return teacherDetails;
    }

    public static void setTeacherDetails(ArrayList<TeacherDetail> teacherDetails) {
        Users.teacherDetails = teacherDetails;
    }

    public static ArrayList<BatchDetail> getBatchDetails() {
        return batchDetails;
    }

    public static void setBatchDetails(ArrayList<BatchDetail> batchDetails) {
        Users.batchDetails = batchDetails;
    }
}
