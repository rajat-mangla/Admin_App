package com.example.rajatiit.admin_app.dataclasses;

import com.example.rajatiit.admin_app.dataclasses.insti.Classroom;
import com.example.rajatiit.admin_app.dataclasses.insti.RoomDetail;
import com.example.rajatiit.admin_app.timetablehandler.TimeTable;
import com.example.rajatiit.admin_app.dataclasses.insti.Institute;
import com.example.rajatiit.admin_app.dataclasses.users.BatchDetail;
import com.example.rajatiit.admin_app.dataclasses.users.TeacherDetail;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by RajatIIT on 10-02-2017.
 */

public class Database {

    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static void sendTimeTable(TimeTable timeTable){
        DatabaseReference reference = database.getReference(TimeTable.TIME_TABLE_REF);
        reference.setValue(timeTable);
    }

    public static void updateUsers(UserStorage userStorage){
        DatabaseReference reference = database.getReference(UserStorage.USER_STORAGE_REF);
        reference.setValue(userStorage);
    }

    public static void updateInstitute(Institute institute){
        DatabaseReference reference = database.getReference(Institute.INSTITUTE_REF);
        reference.setValue(institute);
    }

    public static void sendTeacherInfo(TeacherDetail teacherDetail){
        DatabaseReference reference = database.getReference(UserStorage.USER_STORAGE_REF);
        reference = reference.child(UserStorage.TEACHER_DATA_REF);
        reference = reference.child(Integer.toString(teacherDetail.getTeacherId()));
        reference.setValue(teacherDetail);
    }

    public static void sendTeacherDetails(){
        DatabaseReference reference = database.getReference(UserStorage.USER_STORAGE_REF);
        reference = reference.child(UserStorage.TEACHER_DATA_REF);
        reference.setValue(new UserStorage().getTeacherDetails());
    }

    public static void deleteTeacherInfo(){
        DatabaseReference reference = database.getReference(UserStorage.USER_STORAGE_REF);
        reference = reference.child(UserStorage.TEACHER_DATA_REF);
        reference.setValue(new UserStorage().getTeacherDetails());
    }

    public static void sendBatchInfo(BatchDetail batchDetail){
        DatabaseReference reference = database.getReference(UserStorage.USER_STORAGE_REF);
        reference = reference.child(UserStorage.BATCH_DATA_REF);
        reference = reference.child(Integer.toString(batchDetail.getBatchId()));
        reference.setValue(batchDetail);
    }

    public static void sendBatchDetails(){
        DatabaseReference reference = database.getReference(UserStorage.USER_STORAGE_REF);
        reference = reference.child(UserStorage.BATCH_DATA_REF);
        reference.setValue(new UserStorage().getBatchDetails());
    }

    public static void deleteBatchInfo(){
        DatabaseReference reference = database.getReference(UserStorage.USER_STORAGE_REF);
        reference = reference.child(UserStorage.BATCH_DATA_REF);
        reference.setValue(new UserStorage().getBatchDetails());
    }

    public static void sendRoomInfo(RoomDetail roomDetail, int roomPosition){
        DatabaseReference reference = database.getReference(Institute.INSTITUTE_REF);
        reference = reference.child(Institute.ROOM_DATA_REF);
        reference = reference.child(Integer.toString(roomPosition));
        reference.setValue(roomDetail);
    }

    public static void deleteRoomInfo(){
        DatabaseReference reference = database.getReference(Institute.INSTITUTE_REF);
        reference = reference.child(Institute.ROOM_DATA_REF);
        reference.setValue(new Institute().getRoomDetails());
    }

    public static void sendClassroomInfo(Classroom classroom){
        DatabaseReference reference = database.getReference(Institute.INSTITUTE_REF);
        reference = reference.child(Institute.CLASSROOM_DATA_REF);
        reference = reference.child(Integer.toString(classroom.getClassroomId()));
        reference.setValue(classroom);
    }

    public static void deleteClassroomInfo(){
        DatabaseReference reference = database.getReference(Institute.INSTITUTE_REF);
        reference = reference.child(Institute.CLASSROOM_DATA_REF);
        reference.setValue(new Institute().getClassrooms());
    }
}
