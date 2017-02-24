package com.example.rajatiit.admin_app.dataclasses.users;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

/**
 * Created by rajat on 19/2/17.
 */

public class UserStorage {
    @Exclude
    public static String USER_STORAGE_REF = "UserStorage";

    private static ArrayList<TeacherDetail> teacherDetails;
    private static ArrayList<BatchDetail> batchDetails;

    public UserStorage() {
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
    public ArrayList<TeacherDetail> getTeacherDetails() {
        return teacherDetails;
    }

    public void setTeacherDetails(ArrayList<TeacherDetail> teacherDetails) {
        UserStorage.teacherDetails = teacherDetails;
    }

    public ArrayList<BatchDetail> getBatchDetails() {
        return batchDetails;
    }

    public void setBatchDetails(ArrayList<BatchDetail> batchDetails) {
        UserStorage.batchDetails = batchDetails;
    }
}
