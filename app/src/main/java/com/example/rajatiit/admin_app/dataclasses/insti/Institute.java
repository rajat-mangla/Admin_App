package com.example.rajatiit.admin_app.dataclasses.insti;


import com.google.firebase.database.Exclude;

import java.util.ArrayList;

/**
 * Created by RajatIIT on 10-02-2017.
 */


public class Institute {
    @Exclude
    public static String INSTITUTE_REF = "Institute";

    @Exclude
    public static String ROOM_DATA_REF = "roomDetails";

    @Exclude
    public static String CLASSROOM_DATA_REF = "classrooms";

    @Exclude
    private static String name = "Indian Institute Of Technology Jodhpur";

    @Exclude
    private static ArrayList<String> departments;

    @Exclude
    private static ArrayList<String> batchYears;

    private static ArrayList<Classroom> classrooms;
    private static ArrayList<RoomDetail> roomDetails;

    public Institute() {
        if (departments == null) {
            departments = new ArrayList<>();
            departments.add("CSE");
            departments.add("ME");
            departments.add("EE");
        }
        if (batchYears == null){
            batchYears = new ArrayList<>();
            batchYears.add("First Year");
            batchYears.add("Second Year");
            batchYears.add("Third Year");
            batchYears.add("Fourth Year");
        }
        if (classrooms==null){
            classrooms = new ArrayList<>();
        }
        if (roomDetails == null){
            roomDetails = new ArrayList<>();
        }
    }

    public static ArrayList<String> getBatchYears(){return batchYears;}
    public static ArrayList<String> getDepartments() {
        return departments;
    }

    public static void addClassroomDetail(Classroom classroom){
        classrooms.add(classroom);
    }

    public static Classroom getClassroomDetail(int index){
        return classrooms.get(index);
    }

    public static void deleteClassroomDetail(int classroomPosition){
        classrooms.remove(classroomPosition);
    }

    public static int totalNoOfClassrooms(){return classrooms.size();}

    public static void addRoomDetail(RoomDetail roomDetail){
        roomDetails.add(roomDetail);
    }

    public static RoomDetail getRoomDetail(Integer index){
        return roomDetails.get(index);
    }

    public static void deleteRoomDetail(int roomPosition){
        roomDetails.remove(roomPosition);
    }

    public static int totalNoOfRooms(){return roomDetails.size();}

    public ArrayList<Classroom> getClassrooms() {
        return classrooms;
    }

    public ArrayList<RoomDetail> getRoomDetails() {
        return roomDetails;
    }
}
