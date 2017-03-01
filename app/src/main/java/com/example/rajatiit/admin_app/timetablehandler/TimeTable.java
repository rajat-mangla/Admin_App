package com.example.rajatiit.admin_app.timetablehandler;

import android.util.Log;

import com.example.rajatiit.admin_app.dataclasses.Institute;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by rajat on 28/2/17.
 */

public class TimeTable {

    @Exclude
    public static final String TIME_TABLE_REF = "Time Table";

    // it will displayed in a listview
    private static ArrayList<SlotDetails> totalSlots;

    public TimeTable() {
        if (totalSlots == null){
            totalSlots = new ArrayList<>();
        }
    }

    public void generateTimeSlots(){
        fillSlots();
        assignRooms();
    }

    public ArrayList<SlotDetails> getTotalSlots() {
        return totalSlots;
    }

    public void fillSlots() {
        Log.i("timetable",Integer.toString(totalSlots.size()));
        if (totalSlots.size() == 0){
            totalSlots.add(new SlotDetails("Slot A"));
            totalSlots.add(new SlotDetails("Slot B"));
            totalSlots.add(new SlotDetails("Slot C"));
            totalSlots.add(new SlotDetails("Slot D"));
            totalSlots.add(new SlotDetails("Slot E"));
            totalSlots.add(new SlotDetails("Slot F"));
        }

        for (int groupIndex = 0; groupIndex < Institute.totalNoOfClassrooms(); groupIndex++) {

            Log.i("checking ",Integer.toString(Institute.totalNoOfClassrooms()));

            for (int slotIndex = 0; slotIndex < totalSlots.size(); slotIndex++) {

                // checking if it is feasible to add the classroom
                if (isFeasible(groupIndex, slotIndex)) {
                    totalSlots.get(slotIndex).addClassroom(Institute.getClassroomDetail(groupIndex));
                    break;
                }
            }
            Collections.sort(totalSlots, new Comparator<SlotDetails>() {
                @Override
                public int compare(SlotDetails o1, SlotDetails o2) {
                    return o1.totalClassrooms() - o2.totalClassrooms();
                }
            });
        }
    }

    public boolean isFeasible(int groupIndex, int slotIndex) {
        for (int existingGroupIndex = 0; existingGroupIndex < totalSlots.get(slotIndex).totalClassrooms(); existingGroupIndex++) {

            // checking if batch is same
            if (totalSlots.get(slotIndex).getClassroomDetail(existingGroupIndex).getBatchId() == Institute.getClassroomDetail(groupIndex).getBatchId()) {
                return false;
            }
            /*if (totalSlots.get(slotIndex).classrooms.get(existingGroupIndex).getCourse().getCode() == classrooms.get(groupIndex).getCourse().getCode()) {
                return false;
            }*/
            if (totalSlots.get(slotIndex).getClassroomDetail(existingGroupIndex).getTeacherId() == Institute.getClassroomDetail(groupIndex).getTeacherId()) {
                return false;
            }
        }
        return true;
    }

    public void assignRooms() {
        for (int slotIndex = 0; slotIndex < totalSlots.size(); slotIndex++) {

            boolean roomOccupied[] = new boolean[Institute.totalNoOfRooms()];

            for (int classroomIndex = 0; classroomIndex < totalSlots.get(slotIndex).totalClassrooms(); classroomIndex++) {
                if (totalSlots.get(slotIndex).getClassroomDetail(classroomIndex).getCourseDetail().isProjectorRequired()) {
                    for (int roomIndex = 0; roomIndex < Institute.totalNoOfRooms(); roomIndex++) {
                        if (Institute.getRoomDetail(roomIndex).getProjector() && !roomOccupied[roomIndex]) {
                            roomOccupied[roomIndex] = true;
                            totalSlots.get(slotIndex).getClassroomDetail(classroomIndex).setRoomId(roomIndex);
                            break;
                        }
                    }
                } else {
                    for (int roomIndex = 0; roomIndex < Institute.totalNoOfRooms(); roomIndex++) {
                        if (!roomOccupied[roomIndex]) {
                            roomOccupied[roomIndex] = true;
                            totalSlots.get(slotIndex).getClassroomDetail(classroomIndex).setRoomId(roomIndex);
                            break;
                        }
                    }
                }
            }
        }
    }
}
