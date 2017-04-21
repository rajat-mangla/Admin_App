package com.example.rajatiit.admin_app.dataclasses.users;

import com.example.rajatiit.admin_app.dataclasses.Database;
import com.example.rajatiit.admin_app.dataclasses.insti.Institute;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;

/**
 * Created by rajat on 19/2/17.
 */

public class UserStorage {
    @Exclude
    public static String USER_STORAGE_REF = "UserStorage";

    @Exclude
    public static String TEACHER_DATA_REF = "teacherDetails";

    @Exclude
    public static String BATCH_DATA_REF = "batchDetails";


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

    public static void addTeacherDetail(TeacherDetail teacherDetail){
        teacherDetails.add(teacherDetail);
    }

    public static void deleteTeacherDetail(int teacherPosition){
        teacherDetails.remove(teacherPosition);

        // IMPORTANT PART IN REMOVING THE TEACHER TO HANDLE THE TEACHER ID'S BELOW THE TEACHER DELETED

        for (int i=teacherPosition;i<teacherDetails.size();i++){

            teacherDetails.get(i).setTeacherId(i);

            // Variable to get total no of classrooms for a particular Teacher
            int tempLen = teacherDetails.get(i).getClassroomIds().size();

            if (tempLen > 0){
                for (int j=0 ; j<tempLen ; j++){
                    Institute.getClassroomDetail(j).setTeacherId(i);
                }
            }
        }
        Database.updateInstitute(new Institute());
    }

    public static TeacherDetail getTeacherDetail(int index){
        return teacherDetails.get(index);
    }

    public static int noOfTeachers(){
        return teacherDetails.size();
    }

    public static void addBatchDetail(BatchDetail batchDetail){
        batchDetails.add(batchDetail);
    }

    public static BatchDetail getBatchDetail(int index){
        return batchDetails.get(index);
    }

    public static void deleteBatchDetail(int batchPosition){
        batchDetails.remove(batchPosition);

        // IMPORTANT PART IN REMOVING THE TEACHER TO HANDLE THE TEACHER ID'S BELOW THE TEACHER DELETED

        for (int i=batchPosition;i<batchDetails.size();i++){

            batchDetails.get(i).setBatchId(i);

            // Variable to get total no of classrooms for a particular Teacher
            int tempLen = batchDetails.get(i).getClassroomIds().size();

            if (tempLen > 0){
                for (int j=0 ; j<tempLen ; j++){
                    Institute.getClassroomDetail(j).setBatchId(i);
                }
            }
        }
        Database.updateInstitute(new Institute());
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
