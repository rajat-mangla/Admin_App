package com.example.rajatiit.admin_app;

import com.example.rajatiit.admin_app.dataclasses.Classroom;
import com.example.rajatiit.admin_app.dataclasses.Institute;

import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by rajat on 28/2/17.
 */

public class TimeTable {

    public TimeTable() {
    }

    public void generateTimeSlots(){
        fillSlots();
        assignRooms();
    }


    // it will displayed in a listview
    private static ArrayList<Slot> totalSlots = new ArrayList<Slot>();

    public ArrayList<Slot> getTotalSlots() {
        return totalSlots;
    }


    // it will be displayed in gridview
    public class Slot {
        public Slot() {
        }

        public Slot(String name) {

            this.name = name;
        }

        ArrayList<Classroom> classrooms = new ArrayList<Classroom>();
        int totalClassrooms;
        String name;
        public ArrayList<Classroom> getClassrooms() {
            return classrooms;
        }

        public int getTotalClassrooms() {
            return totalClassrooms;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

    public void fillSlots() {
        totalSlots.add(new Slot("Slot A"));
        totalSlots.add(new Slot("Slot B"));
        totalSlots.add(new Slot("Slot C"));
        totalSlots.add(new Slot("Slot D"));
        totalSlots.add(new Slot("Slot E"));
        totalSlots.add(new Slot("Slot F"));

        for (int groupIndex = 0; groupIndex < Institute.totalNoOfClassrooms(); groupIndex++) {
            for (int slotIndex = 0; slotIndex < totalSlots.size(); slotIndex++) {

                // checking if it is feasible to add the classroom
                if (isFeasible(groupIndex, slotIndex)) {
                    totalSlots.get(slotIndex).classrooms.add(Institute.getClassroomDetail(groupIndex));
                    totalSlots.get(slotIndex).totalClassrooms++;
                    break;
                }
            }
            Collections.sort(totalSlots, new Comparator<Slot>() {
                @Override
                public int compare(Slot o1, Slot o2) {
                    return o1.totalClassrooms - o2.totalClassrooms;
                }
            });
        }
    }

    public boolean isFeasible(int groupIndex, int slotIndex) {
        for (int existingGroupIndex = 0; existingGroupIndex < totalSlots.get(slotIndex).totalClassrooms; existingGroupIndex++) {

            // checking if batch is same
            if (totalSlots.get(slotIndex).classrooms.get(existingGroupIndex).getBatchId() == Institute.getClassroomDetail(groupIndex).getBatchId()) {
                return false;
            }
            /*if (totalSlots.get(slotIndex).classrooms.get(existingGroupIndex).getCourse().getCode() == classrooms.get(groupIndex).getCourse().getCode()) {
                return false;
            }*/
            if (totalSlots.get(slotIndex).classrooms.get(existingGroupIndex).getTeacherId() == Institute.getClassroomDetail(groupIndex).getTeacherId()) {
                return false;
            }
        }
        return true;
    }

    public void assignRooms() {
        for (int slotIndex = 0; slotIndex < totalSlots.size(); slotIndex++) {

            boolean roomOccupied[] = new boolean[Institute.totalNoOfRooms()];

            for (int classroomIndex = 0; classroomIndex < totalSlots.get(slotIndex).totalClassrooms; classroomIndex++) {
                if (totalSlots.get(slotIndex).classrooms.get(classroomIndex).getCourseDetail().isProjectorRequired()) {
                    for (int roomIndex = 0; roomIndex < Institute.totalNoOfRooms(); roomIndex++) {
                        if (Institute.getRoomDetail(roomIndex).getProjector() && !roomOccupied[roomIndex]) {
                            roomOccupied[roomIndex] = true;
                            totalSlots.get(slotIndex).classrooms.get(classroomIndex).setRoomId(roomIndex);
                            break;
                        }
                    }
                } else {
                    for (int roomIndex = 0; roomIndex < Institute.totalNoOfRooms(); roomIndex++) {
                        if (!roomOccupied[roomIndex]) {
                            roomOccupied[roomIndex] = true;
                            totalSlots.get(slotIndex).classrooms.get(classroomIndex).setRoomId(roomIndex);
                            break;
                        }
                    }
                }
            }
        }
    }

}
