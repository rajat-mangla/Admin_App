package com.example.rajatiit.admin_app.timetablehandler;

import com.example.rajatiit.admin_app.dataclasses.Classroom;

import java.util.ArrayList;

/**
 * Created by rajat on 28/2/17.
 */

// it will be displayed in gridview
public class SlotDetails {
    private ArrayList<Classroom> classrooms;
    private String name;

    public SlotDetails() {
        classrooms = new ArrayList<>();
    }

    public SlotDetails(String name) {
        this.name = name;
        classrooms = new ArrayList<>();
    }

    public ArrayList<Classroom> getClassrooms() {
        return classrooms;
    }

    public Classroom getClassroomDetail(int index){
        return classrooms.get(index);
    }

    public void addClassroom(Classroom classroom){
        classrooms.add(classroom);
    }

    public int totalClassrooms() {
        return classrooms.size();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}